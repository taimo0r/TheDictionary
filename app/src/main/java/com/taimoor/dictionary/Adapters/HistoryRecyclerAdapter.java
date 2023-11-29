package com.taimoor.dictionary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taimoor.dictionary.Database.SearchedWords;
import com.taimoor.dictionary.R;

import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {

    Context context;
    List<SearchedWords> wordsList;

    public HistoryRecyclerAdapter(Context context, List<SearchedWords> words) {
        this.context = context;
        this.wordsList = words;
    }

    @NonNull
    @Override
    public HistoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerAdapter.ViewHolder holder, int position) {

        SearchedWords words = wordsList.get(position);
        holder.word.setText(words.Word);

    }

    @Override
    public int getItemCount() {
        return wordsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView word;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.word);
        }
    }
}
