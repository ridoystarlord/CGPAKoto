package com.ridoy.cgpakoto.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.ridoy.cgpakoto.DAO.CourseDao;
import com.ridoy.cgpakoto.DAO.SemesterDao;
import com.ridoy.cgpakoto.Database.GradeDatabase;
import com.ridoy.cgpakoto.Modelclass.Course;
import com.ridoy.cgpakoto.Modelclass.Semester;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GradeRepository {
    private CourseDao courseDao;
    private SemesterDao semesterDao;
    List<Semester> semesterList = new ArrayList<>();
    List<Course> courseList = new ArrayList<>();

    public GradeRepository(Application application) {
        GradeDatabase database = GradeDatabase.getDatabase(application);
        courseDao = database.courseDao();
        semesterDao = database.semesterDao();
    }

    //Semester

    public void insertSemester(Semester semester) {
        new InsertSingleSemesterTask(semesterDao).execute(semester);
    }
    public List<Semester> getAllSemesters() {
        try {
            semesterList = new getAllSemesterTask(semesterDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return semesterList;
    }
    public void updateSemesterCGPA(Semester semester) {
        new updateSemesterCGPATask(semesterDao).execute(semester);
    }
    public void deleteAllsemester(){
        new deleteSemesterTask(semesterDao).execute();
    }


    //Course

    public void insertCourseList(List<Course> courseList) {
        new courseListTask(courseDao).execute(courseList);
    }
    public void deleteallcoursebysemesterid(int semesterid){
        new deleteallcoursebysemesteridTask(courseDao).execute(semesterid);
    }
    public List<Course> getAllCourses(int semesterid) {
        try {
            courseList = new getAllCoursesTask(courseDao).execute(semesterid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public List<Course> showAllCourses() {
        try {
            courseList = new showAllCoursesTask(courseDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return courseList;
    }
    public void deleteAllcourse(){
        new deleteAllcourseTask(courseDao).execute();
    }


    //background
    //semester
    private static class InsertSingleSemesterTask extends AsyncTask<Semester,Void,Void> {
        SemesterDao dao;
        public InsertSingleSemesterTask(SemesterDao semesterDao) {
        dao=semesterDao;
        }

        @Override
        protected Void doInBackground(Semester... semesters) {
            dao.insertSemester(semesters[0]);
            return null;
        }
    }

    private static class getAllSemesterTask extends AsyncTask<Void,Void,List<Semester>>{

        SemesterDao dao;

        public getAllSemesterTask(SemesterDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<Semester> doInBackground(Void... voids) {
            return dao.getAllSemester();
        }
    }
    private static class updateSemesterCGPATask extends AsyncTask<Semester,Void,Void>{
        SemesterDao dao;

        public updateSemesterCGPATask(SemesterDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Semester... semesters) {
            dao.updateSemesterCGPA(semesters[0].getSemesterCGPA(),semesters[0].getId());
            return null;
        }
    }
    private static  class deleteSemesterTask extends AsyncTask<Void,Void,Void>{

        SemesterDao dao;

        public deleteSemesterTask(SemesterDao semesterDao) {
            dao=semesterDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllSemester();
            return null;
        }
    }

    //Course
    private static  class courseListTask extends AsyncTask<List<Course>,Void,Void>{

        CourseDao dao;

        public courseListTask(CourseDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(List<Course>... lists) {
            dao.InsertCourseList(lists[0]);
            return null;
        }
    }
    private static class deleteallcoursebysemesteridTask extends AsyncTask<Integer,Void,Void>{

        CourseDao dao;

        public deleteallcoursebysemesteridTask(CourseDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            dao.deleteAllCoursebysemesterId(integers[0]);
            return null;
        }
    }

    private static class getAllCoursesTask extends AsyncTask<Integer,Void,List<Course>>{

        CourseDao dao;

        public getAllCoursesTask(CourseDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<Course> doInBackground(Integer... integers) {
            return dao.getCoursesBySemesterId(integers[0]);
        }
    }
    private static class showAllCoursesTask extends AsyncTask<Void,Void,List<Course>>{

        CourseDao dao;

        public showAllCoursesTask(CourseDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<Course> doInBackground(Void... voids) {
            return dao.showAllCourse();
        }
    }
    private static  class deleteAllcourseTask extends AsyncTask<Void,Void,Void>{

        CourseDao dao;

        public deleteAllcourseTask(CourseDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllCourse();
            return null;
        }
    }

}
