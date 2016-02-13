package ru.thesn.ptus.app.udm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class SQLExecutor {

    public static int executeSqlScript(Context context, SQLiteDatabase db, String assetFilename, boolean transactional) {
        int count = 0;
        try {
            byte[] bytes = readAsset(context, assetFilename);
            String sql = new String(bytes, "UTF-8");
            String[] lines = sql.split(";(\\s)*");
            if (transactional) {
                count = executeSqlStatementsInTx(db, lines);
            } else {
                count = executeSqlStatements(db, lines);
            }
            Log.i("Dev_", "Executed " + count + " statements from SQL script '" + assetFilename + "'");
        }catch (IOException e){
            e.printStackTrace();
        }
        return count;
    }

    public static int executeSqlStatements(SQLiteDatabase db, String[] statements) {
        int count = 0;
        for (String line : statements) {
            line = line.trim();
            if (line.length() > 0) {
                db.execSQL(line);
                count++;
            }
        }
        return count;
    }

    public static int executeSqlStatementsInTx(SQLiteDatabase db, String[] statements) {
        db.beginTransaction();
        try {
            int count = executeSqlStatements(db, statements);
            db.setTransactionSuccessful();
            return count;
        } finally {
            db.endTransaction();
        }
    }

    public static byte[] readAsset(Context context, String filename) throws IOException {
        InputStream in = context.getResources().getAssets().open(filename);
        try {
            return readAllBytes(in);
        } finally {
            in.close();
        }
    }

    public static int copyAllBytes(InputStream in, OutputStream out) throws IOException {
        int byteCount = 0;
        byte[] buffer = new byte[4096];
        while (true) {
            int read = in.read(buffer);
            if (read == -1) {
                break;
            }
            out.write(buffer, 0, read);
            byteCount += read;
        }
        return byteCount;
    }

    public static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copyAllBytes(in, out);
        return out.toByteArray();
    }
}
