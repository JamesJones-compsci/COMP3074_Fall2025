package com.example.week4_fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

interface OnUpdatePressedListener {
    void onUpdatePressed();
    void sendText(String text);
}

public class MainFragment3 extends Fragment {

    private static final String TAG = "MainFragment3";

    private OnUpdatePressedListener listener;
    private TextView textView;

    private EditText label;

    public MainFragment3() { }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");

        // This is the place when the fragment is attached to its activity
        if (context instanceof OnUpdatePressedListener){
            listener = (OnUpdatePressedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + "must implement the OnUpdatePressedListener interface");
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // TODO return the layout for the fragment
        // return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_main3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");

        textView = view.findViewById(R.id.text3);
        label = view.findViewById(R.id.label);
        Button updateBtn = view.findViewById(R.id.updatebtn);
        updateBtn.setOnClickListener((v) -> {
            listener.onUpdatePressed();
            listener.sendText(label.getText().toString());
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }


    public void updateUi(String text){
        textView.setText(text);
    }


}
