package com.taimoor.dictionary.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taimoor.dictionary.R;

public class MeaningsViewHolder extends RecyclerView.ViewHolder {

    public TextView partsOfSpeech, synonyms, antonyms;
    public RecyclerView recyclerView_definition;

    public MeaningsViewHolder(@NonNull View itemView) {
        super(itemView);

        partsOfSpeech = itemView.findViewById(R.id.textView_patsOfSpeech);
        recyclerView_definition = itemView.findViewById(R.id.recycler_definitions);
        synonyms = itemView.findViewById(R.id.textView_synonyms);
        antonyms = itemView.findViewById(R.id.textView_antonyms);

    }
}
