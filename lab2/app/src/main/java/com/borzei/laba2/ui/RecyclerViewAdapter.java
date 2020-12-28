package com.borzei.laba2.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.borzei.laba2.R;
import com.borzei.laba2.data.Util;
import com.borzei.laba2.data.pojo.DetailModel;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<DetailModel> list;
    private Context context;
    private Fragment fragment;


    public RecyclerViewAdapter(Fragment fragment) {
        this.fragment = fragment;
        list = new ArrayList<>();
    }

    public void addData(List<DetailModel> data) {
        Log.d("TAGG",String.valueOf(data)+String.valueOf(list));
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(List<DetailModel> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_title);
            imageView = itemView.findViewById(R.id.item_photo);
            descriptionTextView = itemView.findViewById(R.id.item_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            FragmentTransaction transaction =  fragment.getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.host, OpenedFragment.newInstance(list.get(this.getAdapterPosition()).getId()));
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false));
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DetailModel item = list.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getOverview());
        Glide.with(context)
                .load(Util.IMG_BASE_URL + item.getPosterPath())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}