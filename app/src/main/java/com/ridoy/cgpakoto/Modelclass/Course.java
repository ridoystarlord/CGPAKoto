package com.ridoy.cgpakoto.Modelclass;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String courseName;
    private int courseCredit;
    private Double courseSGPA;
    private int semesterId;

    public Course(String courseName, int courseCredit, Double courseSGPA, int semesterId) {
        this.courseName = courseName;
        this.courseCredit = courseCredit;
        this.courseSGPA = courseSGPA;
        this.semesterId = semesterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getCourseSGPA() {
        return courseSGPA;
    }

    public void setCourseSGPA(Double courseSGPA) {
        this.courseSGPA = courseSGPA;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }
}
