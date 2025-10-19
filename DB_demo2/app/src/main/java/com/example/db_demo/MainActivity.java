package com.example.db_demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.db_demo.room.Name;
import com.example.db_demo.room.NameViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText nameText;
    private ListView listView;
    private ArrayAdapter<Name> adapter;
    private List<Name> names;
    private NameViewModel nameViewModel;

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

        // Initialize ViewModel
        nameViewModel = new ViewModelProvider(this).get(NameViewModel.class);

        initUi();
    }

    private void initUi() {
        names = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showUpdateOrDeleteNameDialog(names.get(position));
            return true;
        });

        nameText = findViewById(R.id.name);
        findViewById(R.id.addBtn).setOnClickListener(this);
        findViewById(R.id.showBtn).setOnClickListener(this);
    }

    private void showUpdateOrDeleteNameDialog(Name name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete")
                .setMessage("Do you want to edit or delete " + name.getName() + "?")
                .setPositiveButton("Edit", (d, i) -> {
                    String newName = nameText.getText().toString().trim();
                    if (!newName.isBlank()) {
                        name.setName(newName);
                        if (nameViewModel.update(name)) {
                            Toast.makeText(this, "Updated to " + newName, Toast.LENGTH_SHORT).show();
                            showNames();
                        } else {
                            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Delete", (d, i) -> {
                    if (nameViewModel.delete(name)) {
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                        showNames();
                    }
                })
                .setNeutralButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.addBtn) {
            addName();
        } else if (id == R.id.showBtn) {
            showNames();
        }
    }

    private void addName() {
        String nameStr = nameText.getText().toString().trim();
        if (nameStr.isBlank()) {
            nameText.setError("Name field is mandatory");
            return;
        }

        Name name = new Name(nameStr);
        if (nameViewModel.insert(name)) {
            Toast.makeText(this, "Name added", Toast.LENGTH_SHORT).show();
            showNames();
        } else {
            Toast.makeText(this, "Insert failed", Toast.LENGTH_SHORT).show();
        }

        nameText.setText("");
    }

    private void showNames() {
        names.clear();
        names.addAll(nameViewModel.getAllNames());
        adapter.notifyDataSetChanged();

        Log.i(TAG, "showNames: ");
    }
}
