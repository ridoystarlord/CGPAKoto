package com.ridoy.cgpakoto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ridoy.cgpakoto.CoursesActivity;
import com.ridoy.cgpakoto.Modelclass.Semester;
import com.ridoy.cgpakoto.R;

import java.util.List;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder> {

    Context context;
    List<Semester> semesterList;

    public SemesterAdapter(Context context, List<Semester> semesterList) {
        this.context = context;
        this.semesterList = semesterList;
    }

    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.semester_layout,parent,false);
        return new SemesterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        Semester semester=semesterList.get(position);
        holder.semesterName.setText(semester.getSemesterName());
        holder.semesterCredit.setText("Credit: "+semester.getSemesterCredit());
        double tempcgpa=semester.getSemesterCGPA();
        holder.semesterCGPA.setText(String.format("%.2f", tempcgpa));
        holder.setData(semester.getId());
    }

    @Override
    public int getItemCount() {
        return semesterList.size();
    }

    public class SemesterViewHolder extends RecyclerView.ViewHolder {
        TextView semesterName,semesterCredit,semesterCGPA;
        public SemesterViewHolder(@NonNull View itemView) {
            super(itemView);
            semesterName=itemView.findViewById(R.id.semesterName_TV);
            semesterCredit=itemView.findViewById(R.id.semesterCredit_TV);
            semesterCGPA=itemView.findViewById(R.id.semesterCGPA_TV);
        }
        private void setData(int semesterId){
            Intent intent=new Intent(context,CoursesActivity.class);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("SemesterId",semesterId);
                    context.startActivity(intent);
                }
            });

        }

    }
}
