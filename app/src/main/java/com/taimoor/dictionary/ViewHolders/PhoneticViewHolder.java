package com.taimoor.dictionary.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taimoor.dictionary.R;

public class PhoneticViewHolder extends RecyclerView.ViewHolder {

    public TextView phonetics;
    public ImageButton audioBtn;

    public PhoneticViewHolder(@NonNull View itemView) {
        super(itemView);
        phonetics = itemView.findViewById(R.id.textView_phonetic);
        audioBtn = itemView.findViewById(R.id.imageBtn_audio);
    }
}
