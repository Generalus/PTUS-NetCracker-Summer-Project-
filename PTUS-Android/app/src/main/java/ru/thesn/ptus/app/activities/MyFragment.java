package ru.thesn.ptus.app.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.thesn.ptus.app.MyApplication;
import ru.thesn.ptus.app.R;
import ru.thesn.ptus.app.activities.EditActivity;
import ru.thesn.ptus.app.tools.BasicListAdapter;
import ru.thesn.ptus.app.tools.UtilsScreen;

import java.util.List;


public class MyFragment extends Fragment {

    private List<BasicListAdapter.Entity> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_main, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}