package com.example.week4_fragments;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity3 extends AppCompatActivity implements OnUpdatePressedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.container1, new MainFragment3(), "THIRD")
                .commit();

        fm.beginTransaction()
                .add(R.id.container2, new MainFragment2(), "SECOND")
                .commit();
    }

    @Override
    public void onUpdatePressed(){
        String text = ((EditText) findViewById(R.id.label)).getText().toString();

    //  MainFragment2 fragment2 = (MainFragment2) getSupportFragmentManager().findFragmentByTag("SECOND");
    //  fragment2.updateUi(text);

        MainFragment3 fragment3 = (MainFragment3) getSupportFragmentManager().findFragmentByTag("THIRD");
        fragment3.updateUi(text);
    }

    @Override
    public void sendText(String text){
        MainFragment2 fragment2 = (MainFragment2) getSupportFragmentManager().findFragmentByTag("SECOND");
        fragment2.updateUi(text);
    }

}