package com.example.db_demo;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DBHelper dbHelper;

    private EditText nameText;

    private ListView listView;

    private ArrayAdapter<Name> adapter;

    private List<Name> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);
        initUi();
    }

    private void initUi() {

        names = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                names
        );

        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            // TODO show a dialog to delete and update the selected name
            return true;
        });

        nameText = findViewById(R.id.name);
        findViewById(R.id.addBtn).setOnClickListener(this);
        findViewById(R.id.showBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.addBtn) {
            // TODO add names
        } else if (id == R.id.showBtn){
            // TODO show names
        }
    }


    private class Name {

        private int id;

        private String name;


        public Name(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}