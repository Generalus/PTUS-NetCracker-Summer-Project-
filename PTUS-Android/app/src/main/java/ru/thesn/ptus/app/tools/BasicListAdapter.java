package ru.thesn.ptus.app.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.*;
import ru.thesn.ptus.app.R;

import java.util.ArrayList;
import java.util.List;

public class BasicListAdapter extends AbstractListAdapter<BasicListAdapter.Entity, BasicListAdapter.ViewHolder> {


    private final LayoutInflater mInflater;
    private       OnItemClickListener mOnItemClickListener;

    public BasicListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(
                mInflater.inflate(R.layout.section_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bind(mData.get(position));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public static class Entity {
        private final String id;


        public Entity(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entity entity = (Entity) o;

            if (id != null ? !id.equals(entity.id) : entity.id != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextView;
        private final CardView cardView;
        private Entity   mEntity;
        private final View view;


        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.label);
            cardView = (CardView) v.findViewById(R.id.cv);
            view = v;

            cardView.setCardBackgroundColor(Color.TRANSPARENT);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mEntity);
                    }
                }
            });
        }

        public void bind(final Entity entity) {

        }

    }

    public interface OnItemClickListener {
        void onItemClick(Entity entity);
    }

    public BasicListAdapter.Entity getEntityByID(String id){
        for(BasicListAdapter.Entity entity: mData){
            if (entity.getId().equals(id)) return entity;
        }
        return null;
    }
}