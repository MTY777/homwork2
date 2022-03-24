package com.example.homwork2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homwork2.Interfaces.OnClickListener;
import com.example.homwork2.databinding.ItemNewsBinding;
import com.example.homwork2.models.News;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@SuppressLint("NotifyDataSetChanged")
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<News> list;
    private OnClickListener onClickListener;

    public NewsAdapter() {
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
    holder.bind(list.get(position));
    if (position % 2 == 0){
        holder.binding.title.setBackgroundColor(Color.GRAY);
    }else{
        holder.binding.title.setBackgroundColor(Color.BLACK);
    }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0, news);
        notifyItemInserted(list.indexOf(news));
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public News getItem(int position) {
        return list.get(position);
    }


    public void updateItem(News news, int position) {
        list.set(position, news);
        notifyItemChanged(position);
    }


    class NewsViewHolder extends RecyclerView.ViewHolder {
        ItemNewsBinding binding;

        public NewsViewHolder(@NonNull ItemNewsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick(getAdapterPosition());


                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onClickListener.onItemLongClick(getAdapterPosition());
                    new AlertDialog.Builder(view.getContext()).setTitle("Удаление")
                            .setMessage("Вы точно хотите удалить?")
                            .setNegativeButton("нет", null)
                            .setPositiveButton("да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(view.getContext(), "Delete", Toast.LENGTH_LONG).show();
                                    list.remove(getAdapterPosition());
                                    notifyDataSetChanged();

                                }
                            }).show();
                    return true;
                }
            });
        }

        public void bind(News news) {
            binding.title.setText(news.getTitle());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss, dd MMM yyyy", Locale.ROOT);
            String date = String.valueOf(simpleDateFormat.format(news.getCreatedAt()));
            binding.tvTime.setText(date);
        }
    }

    }

