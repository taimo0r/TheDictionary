package com.taimoor.dictionary.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taimoor.dictionary.R;

public class DefinitionViewHolder extends RecyclerView.ViewHolder {

    public TextView definitions, example, synonyms, antonyms;
    public DefinitionViewHolder(@NonNull View itemView) {
        super(itemView);

        definitions = itemView.findViewById(R.id.textView_definition);
        example = itemView.findViewById(R.id.textView_Example);
        synonyms = itemView.findViewById(R.id.textView_synonyms);
        antonyms = itemView.findViewById(R.id.textView_antonyms);
    }
}
