package com.ridoy.cgpakoto.Modelclass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Semester {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String semesterName;
    private int semesterCredit;

    @ColumnInfo(name = "semesterCGPA")
    private double semesterCGPA;

    public Semester() {
    }

    public Semester(String semesterName, int semesterCredit, double semesterCGPA) {
        this.semesterName = semesterName;
        this.semesterCredit = semesterCredit;
        this.semesterCGPA = semesterCGPA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public int getSemesterCredit() {
        return semesterCredit;
    }

    public void setSemesterCredit(int semesterCredit) {
        this.semesterCredit = semesterCredit;
    }

    public double getSemesterCGPA() {
        return semesterCGPA;
    }

    public void setSemesterCGPA(double semesterCGPA) {
        this.semesterCGPA = semesterCGPA;
    }
}
