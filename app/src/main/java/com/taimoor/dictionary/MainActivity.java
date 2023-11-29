package com.taimoor.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.CompletionInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.taimoor.dictionary.Adapters.MeaningAdapter;
import com.taimoor.dictionary.Adapters.PhoneticsAdapter;
import com.taimoor.dictionary.Database.AppDatabase;
import com.taimoor.dictionary.Database.SearchedWords;
import com.taimoor.dictionary.Models.APIResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView word;
    RecyclerView phonetics, meanings;
    ProgressDialog progressDialog;
    PhoneticsAdapter phoneticsAdapter;
    MeaningAdapter meaningAdapter;
    ImageView historyBtn;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayList<String> allWords;
    AppDatabase database;
    String query = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //searchView = findViewById(R.id.search_view);
        word = findViewById(R.id.textView_word);
        phonetics = findViewById(R.id.recycler_phonetics);
        meanings = findViewById(R.id.recycler_meanings);
        historyBtn = findViewById(R.id.history_btn);
        autoCompleteTextView = findViewById(R.id.autoCompleteTxt);

        database = AppDatabase.getInstance(MainActivity.this);
        allWords = (ArrayList<String>) database.wordsDao().getWords();

        ArrayAdapter<String> autocomplete = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, allWords);
        autoCompleteTextView.setAdapter(autocomplete);

        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Loading");
        progressDialog.show();

        RequestManager manager = new RequestManager(MainActivity.this);
        manager.getWordMeaning(listener, "Hello");

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                query = null;
                query = autoCompleteTextView.getText().toString();
                progressDialog.setTitle("Fetching Response for " + query);
                progressDialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getWordMeaning(listener, query);
            }
        });

        autoCompleteTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                query = null;
                query = autoCompleteTextView.getText().toString();
                if (!query.equals("")) {
                    if (i == KeyEvent.KEYCODE_ENTER) {
                        progressDialog.setTitle("Fetching Response for " + query);
                        progressDialog.show();
                        RequestManager manager = new RequestManager(MainActivity.this);
                        manager.getWordMeaning(listener, query);
                        saveWord(query);
                        return true;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a word to search!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return false;
            }
        });

        if (!isConnected(this)) {
            showCustomDialog();
            return;
        }
    }

    private void saveWord(String query) {
        SearchedWords words = new SearchedWords();
        words.Word = query;
        database.wordsDao().SaveWord(words);
    }

    private final onFetchDataListener listener = new onFetchDataListener() {
        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            progressDialog.dismiss();
            if (apiResponse == null) {
                Toast.makeText(MainActivity.this, "No Such Word Found", Toast.LENGTH_SHORT).show();
                return;
            }
            showData(apiResponse);
        }

        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(APIResponse apiResponse) {
        word.setText("Word: " + apiResponse.getWord());
        phonetics.setHasFixedSize(true);
        phonetics.setLayoutManager(new GridLayoutManager(this, 1));
        phoneticsAdapter = new PhoneticsAdapter(MainActivity.this, apiResponse.getPhonetics());
        phonetics.setAdapter(phoneticsAdapter);

        meanings.setHasFixedSize(true);
        meanings.setLayoutManager(new GridLayoutManager(this, 1));
        meaningAdapter = new MeaningAdapter(MainActivity.this, apiResponse.getMeanings());
        meanings.setAdapter(meaningAdapter);
    }

    public void History(View view) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    //Show Internet connection dialog box
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Please check your internet connection before logging in")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Connect to internet to load wallpapers", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }

    //Check if the internet is connected or not.
    private boolean isConnected(MainActivity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

}
