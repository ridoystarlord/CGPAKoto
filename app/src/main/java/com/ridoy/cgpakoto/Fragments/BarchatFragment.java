package com.ridoy.cgpakoto.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ridoy.cgpakoto.MainActivity;
import com.ridoy.cgpakoto.Modelclass.Semester;
import com.ridoy.cgpakoto.R;
import com.ridoy.cgpakoto.Repository.GradeRepository;
import com.ridoy.cgpakoto.databinding.FragmentBarchatBinding;

import java.util.ArrayList;
import java.util.List;


public class BarchatFragment extends Fragment {


    public BarchatFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    BarChart barChart;
    List<BarEntry> barEntryList;
    List<Semester> barchatItemsList;
    List<String> labelname;
    GradeRepository gradeRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_barchat, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRetainInstance(true);
        gradeRepository = new GradeRepository(getActivity().getApplication());
        barChart=view.findViewById(R.id.barchat);
        barEntryList=new ArrayList<>();
        barchatItemsList=new ArrayList<>();
        labelname=new ArrayList<>();
        barchatItemsList=gradeRepository.getAllSemesters();

        if (barchatItemsList.isEmpty()){
            new AlertDialog.Builder(getActivity())
                    .setTitle("No Semester Added Yet")
                    .setMessage("Please, Add Semester Data to Check Barchat Graph of Your CGPA")
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
            barEntryList.clear();
            labelname.clear();

            for (int i=0;i<barchatItemsList.size();i++){
                String semesterName=barchatItemsList.get(i).getSemesterName();
                double semesterCGPA=barchatItemsList.get(i).getSemesterCGPA();
                barEntryList.add(new BarEntry(i, (float) semesterCGPA));
                labelname.add(semesterName);
            }

            BarDataSet barDataSet=new BarDataSet(barEntryList,"Semester");
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            Description description=new Description();
            description.setText("Result");
            barChart.setDescription(description);

            BarData barData=new BarData(barDataSet);
            barChart.setData(barData);

            XAxis xAxis=barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labelname));
            xAxis.setPosition(XAxis.XAxisPosition.TOP);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            xAxis.setGranularity(1f);
            xAxis.setLabelCount(labelname.size());
            xAxis.setLabelRotationAngle(0);
            barChart.animateY(500);
            barChart.invalidate();
        }



        return view;
    }
}