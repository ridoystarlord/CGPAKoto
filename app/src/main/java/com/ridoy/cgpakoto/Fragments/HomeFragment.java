package com.ridoy.cgpakoto.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ridoy.cgpakoto.Adapter.SemesterAdapter;
import com.ridoy.cgpakoto.Database.GradeDatabase;
import com.ridoy.cgpakoto.Modelclass.Semester;
import com.ridoy.cgpakoto.R;
import com.ridoy.cgpakoto.Repository.GradeRepository;
import com.ridoy.cgpakoto.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentHomeBinding fragmentHomeBinding;
    GradeRepository repository;
    SemesterAdapter adapter;
    List<Semester> semesterList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding=FragmentHomeBinding.inflate(inflater,container,false);
        repository= new GradeRepository(getActivity().getApplication());

        semesterList=new ArrayList<>();
        semesterList=repository.getAllSemesters();

        adapter=new SemesterAdapter(getContext(),semesterList);
        fragmentHomeBinding.semesterRV.setAdapter(adapter);
        fragmentHomeBinding.semesterRV.setHasFixedSize(true);

        fragmentHomeBinding.addfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.semester_user_input_dialog);

                EditText semesterName_ET = dialog.findViewById(R.id.semesterName_ET);
                EditText semesterCredit_ET = dialog.findViewById(R.id.semesterCredit_ET);
                Button createSemester_btn = dialog.findViewById(R.id.createSemester_btn);

                createSemester_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (semesterName_ET.getText().toString().isEmpty()) {
                            semesterName_ET.setError("Please Enter Semester Name");
                            semesterName_ET.requestFocus();
                            return;
                        }
                        if (semesterCredit_ET.getText().toString().isEmpty()) {
                            semesterCredit_ET.setError("Please Enter Semester Total Credit");
                            semesterCredit_ET.requestFocus();
                            return;
                        }
                        insertSemester(semesterName_ET.getText().toString(),semesterCredit_ET.getText().toString());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        return fragmentHomeBinding.getRoot();
    }

    private void insertSemester(String name, String credit) {
        Semester semester = new Semester(name, Integer.parseInt(credit), 0.0);
        semesterList.add(semester);
        repository.insertSemester(semester);
        adapter.notifyDataSetChanged();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}