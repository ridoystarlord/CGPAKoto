package com.ridoy.cgpakoto.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ridoy.cgpakoto.Adapter.CourseAdapter;
import com.ridoy.cgpakoto.MainActivity;
import com.ridoy.cgpakoto.Modelclass.Course;
import com.ridoy.cgpakoto.R;
import com.ridoy.cgpakoto.Repository.GradeRepository;

import java.util.ArrayList;
import java.util.List;


public class AllcoursesFragment extends Fragment {


    public AllcoursesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    List<Course> allcourseList;
    GradeRepository repository;
    CourseAdapter adapter;
    RecyclerView allcourses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allcourses, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRetainInstance(true);
        repository = new GradeRepository(getActivity().getApplication());
        allcourseList = new ArrayList<>();
        allcourses=view.findViewById(R.id.allcourses_RV);

        allcourseList = repository.showAllCourses();

        if (allcourseList.isEmpty()){
            new AlertDialog.Builder(getActivity())
                    .setTitle("No Courses Added Yet")
                    .setMessage("Please, Add Semester and Courses to Check all Courses Information")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_alert)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                    }).show();
        }else {

            adapter = new CourseAdapter(getContext(), allcourseList);
            allcourses.setHasFixedSize(true);
            allcourses.setAdapter(adapter);
        }
        return view;
    }
}