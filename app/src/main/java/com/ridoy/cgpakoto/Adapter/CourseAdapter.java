package com.ridoy.cgpakoto.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ridoy.cgpakoto.Modelclass.Course;
import com.ridoy.cgpakoto.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    Context context;
    List<Course> courseList;

    public CourseAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.semester_layout,parent,false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course=courseList.get(position);
        holder.courseName.setText(course.getCourseName());
        holder.courseCredit.setText("Credit: "+String.valueOf(course.getCourseCredit()));
        holder.courseSGPA.setText(String.valueOf(course.getCourseSGPA()));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseName,courseCredit,courseSGPA;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName=itemView.findViewById(R.id.semesterName_TV);
            courseCredit=itemView.findViewById(R.id.semesterCredit_TV);
            courseSGPA=itemView.findViewById(R.id.semesterCGPA_TV);
        }
    }
}
