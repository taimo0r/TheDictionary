package com.taimoor.dictionary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taimoor.dictionary.Models.Definition;
import com.taimoor.dictionary.Models.Meaning;
import com.taimoor.dictionary.R;
import com.taimoor.dictionary.ViewHolders.DefinitionViewHolder;

import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionViewHolder> {

    private Context context;
    private List<Definition> definitionList;
    StringBuilder synonyms, antonyms;

    public DefinitionAdapter(Context context, List<Definition> definitionList, StringBuilder synonyms, StringBuilder antonyms) {
        this.context = context;
        this.definitionList = definitionList;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefinitionViewHolder(LayoutInflater.from(context).inflate(R.layout.definitions_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        holder.definitions.setText("Definition: " + definitionList.get(position).getDefinition());
        holder.example.setText("Example: " + definitionList.get(position).getExample());

        holder.synonyms.setText(synonyms);
        holder.antonyms.setText(antonyms);
        holder.synonyms.setSelected(true);
        holder.antonyms.setSelected(true);
    }


    @Override
    public int getItemCount() {
        return definitionList.size();
    }
}
