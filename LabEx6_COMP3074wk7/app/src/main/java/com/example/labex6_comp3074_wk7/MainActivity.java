package com.example.labex6_comp3074_wk7;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.labex6_comp3074_wk7.room.StudentDb;
import com.example.labex6_comp3074_wk7.room.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, gradeInput;
    private TextView studentList;
    private StudentDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.name_input);
        gradeInput = findViewById(R.id.grade_input);
        studentList = findViewById(R.id.student_list);
        Button saveButton = findViewById(R.id.save_button);

        db = StudentDb.getDatabase(this); // get Room database instance

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String gradeStr = gradeInput.getText().toString();

            if (!name.isEmpty() && !gradeStr.isEmpty()) {
                int grade = Integer.parseInt(gradeStr);
                db.studentDao().insert(new Student(name, grade)); // Save to DB
                displayStudents(); // Show updated list
                nameInput.setText("");
                gradeInput.setText("");
            }
        });

        displayStudents(); // Load existing students on start
    }

    private void displayStudents() {
        List<Student> students = db.studentDao().getAllStudents();
        StringBuilder sb = new StringBuilder("Students:\n");
        for (Student s : students) {
            sb.append(s.getName()).append(" - ").append(s.getGrade()).append("\n");
        }
        studentList.setText(sb.toString());
    }
}