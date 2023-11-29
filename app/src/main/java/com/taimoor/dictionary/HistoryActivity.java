package com.taimoor.dictionary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.taimoor.dictionary.Adapters.HistoryRecyclerAdapter;
import com.taimoor.dictionary.Database.AppDatabase;
import com.taimoor.dictionary.Database.SearchedWords;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryRecyclerAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    AlertDialog alertDialog;
    Button cancelBtn, deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("History");
            //set back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.history_recyclerView);
        refreshLayout = findViewById(R.id.refreshLayout);

        initializeRecycler();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializeRecycler();
                refreshLayout.setRefreshing(false);
            }
        });

    }

    private void initializeRecycler() {
        AppDatabase database = AppDatabase.getInstance(HistoryActivity.this);
        List<SearchedWords> words = database.wordsDao().getAllWords();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HistoryRecyclerAdapter(this, words);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.delete_history);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                clearHistoryDialog();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void clearHistoryDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(HistoryActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.clear_history_dialog, null);

        cancelBtn = (Button) dialogView.findViewById(R.id.cancel_btn);
        deleteBtn = (Button) dialogView.findViewById(R.id.delete_btn);

        alert.setView(dialogView);
        alertDialog = alert.create();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteHistory();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void deleteHistory() {
        AppDatabase database = AppDatabase.getInstance(HistoryActivity.this);
        database.wordsDao().deleteAll();

        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}