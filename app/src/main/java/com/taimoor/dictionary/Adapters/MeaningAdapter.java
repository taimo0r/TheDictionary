package com.taimoor.dictionary.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taimoor.dictionary.Models.Meaning;
import com.taimoor.dictionary.Models.Phonetic;
import com.taimoor.dictionary.R;
import com.taimoor.dictionary.ViewHolders.MeaningsViewHolder;

import java.util.List;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningsViewHolder> {

    private Context context;
    protected List<Meaning> meaningList;

    public MeaningAdapter(Context context, List<Meaning> meaningList) {
        this.context = context;
        this.meaningList = meaningList;
    }

    @NonNull
    @Override
    public MeaningsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeaningsViewHolder(LayoutInflater.from(context).inflate(R.layout.meanings_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningsViewHolder holder, int position) {
        holder.partsOfSpeech.setText("Parts of Speech: " + meaningList.get(position).getPartOfSpeech());

        StringBuilder synonyms = new StringBuilder();
        StringBuilder antonyms = new StringBuilder();

        synonyms.append(meaningList.get(position).getSynonyms());
        antonyms.append(meaningList.get(position).getAntonyms());


        holder.recyclerView_definition.setHasFixedSize(true);
        holder.recyclerView_definition.setLayoutManager(new GridLayoutManager(context, 1));
        DefinitionAdapter definitionAdapter = new DefinitionAdapter(context, meaningList.get(position).getDefinitions(),synonyms,antonyms);
        holder.recyclerView_definition.setAdapter(definitionAdapter);


    }

    @Override
    public int getItemCount() {
        return meaningList.size();
    }
}
