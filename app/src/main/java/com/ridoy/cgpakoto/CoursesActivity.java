package com.ridoy.cgpakoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ridoy.cgpakoto.Adapter.CourseAdapter;
import com.ridoy.cgpakoto.Modelclass.Course;
import com.ridoy.cgpakoto.Modelclass.Semester;
import com.ridoy.cgpakoto.Repository.GradeRepository;
import com.ridoy.cgpakoto.databinding.ActivityCoursesBinding;

import java.util.ArrayList;
import java.util.List;

public class CoursesActivity extends AppCompatActivity {

    ActivityCoursesBinding activityCoursesBinding;
    List<Course> courseList;
    CourseAdapter adapter;
    Double creditandsgpa = 0.0;
    int totalcredit = 0;
    GradeRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCoursesBinding = ActivityCoursesBinding.inflate(getLayoutInflater());
        setContentView(activityCoursesBinding.getRoot());


        repository = new GradeRepository(getApplication());
        courseList = new ArrayList<>();

        courseList = repository.getAllCourses(getIntent().getIntExtra("SemesterId", 0));

        adapter = new CourseAdapter(this, courseList);
        activityCoursesBinding.coursesRV.setAdapter(adapter);
        activityCoursesBinding.coursesRV.setHasFixedSize(true);

        if (courseList.size()>0){
            calculateCGPAFromList(courseList);
        }

        activityCoursesBinding.addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCGPA(activityCoursesBinding.courseCreditET.getText().toString(),
                        activityCoursesBinding.courseSGPAET.getText().toString());
            }
        });

        activityCoursesBinding.savefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCourseList(courseList);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteSingleCourse(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(activityCoursesBinding.coursesRV);

    }

    private void deleteSingleCourse(int adapterPosition) {

        Course course=courseList.get(adapterPosition);
        courseList.remove(course);
        adapter.notifyDataSetChanged();
        repository.deleteallcoursebysemesterid(getIntent().getIntExtra("SemesterId", 0));
        repository.insertCourseList(courseList);
        creditandsgpa = 0.0;
        totalcredit = 0;
        if (courseList.size()>0){
            calculateCGPAFromList(courseList);
        }else {
            activityCoursesBinding.cgpaTV.setText("0.0");
        }
    }

    private void calculateCGPAFromList(List<Course> courseList) {

        for (int i=0;i<courseList.size();i++){
            Course course=courseList.get(i);
            totalcredit+=course.getCourseCredit();
            Double pcreditandsgpa=(course.getCourseSGPA()*course.getCourseCredit());
            creditandsgpa+=pcreditandsgpa;
        }
        Double cgpa=creditandsgpa/totalcredit;
        updatesemesterCGPA(cgpa);
        activityCoursesBinding.cgpaTV.setText(String.format("CGPA: %.2f", cgpa));
    }

    private void insertCourseList(List<Course> courseList) {
        repository.deleteallcoursebysemesterid(getIntent().getIntExtra("SemesterId", 0));
        repository.insertCourseList(courseList);
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    private void calculateCGPA(String credit, String Sgpa) {
        int creditvalue = Integer.parseInt(credit);
        Double sgpavalue = Double.parseDouble(Sgpa);
        Double pcreditandsgpa = creditvalue * sgpavalue;

        totalcredit += creditvalue;
        creditandsgpa += pcreditandsgpa;

        Double CGPA = creditandsgpa / totalcredit;
        activityCoursesBinding.cgpaTV.setText(String.format("CGPA: %.2f", CGPA));
        updatesemesterCGPA(CGPA);
        Course course = new Course(activityCoursesBinding.courseNameET.getText().toString(),
                Integer.parseInt(activityCoursesBinding.courseCreditET.getText().toString()),
                Double.parseDouble(activityCoursesBinding.courseSGPAET.getText().toString()),
                getIntent().getIntExtra("SemesterId", 0)
        );
        courseList.add(course);
        adapter.notifyDataSetChanged();
        activityCoursesBinding.courseNameET.setText("");
        activityCoursesBinding.courseCreditET.setText("");
        activityCoursesBinding.courseSGPAET.setText("");
    }

    private void updatesemesterCGPA(Double cgpa) {
        Semester semester=new Semester();
        semester.setId(getIntent().getIntExtra("SemesterId", 0));
        semester.setSemesterCGPA(cgpa);
        repository.updateSemesterCGPA(semester);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        startActivity(new Intent(CoursesActivity.this,MainActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clearcourses:
                repository.deleteAllcourse();
                courseList.clear();
                updatesemesterCGPA(0.0);
                activityCoursesBinding.cgpaTV.setText("CGPA: 0.0");
                adapter.notifyDataSetChanged();
            default:
        }
        return true;
    }
}