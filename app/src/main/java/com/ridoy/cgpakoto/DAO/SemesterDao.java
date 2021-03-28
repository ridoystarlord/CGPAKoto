package com.ridoy.cgpakoto.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ridoy.cgpakoto.Modelclass.Semester;

import java.util.List;
@Dao
public interface SemesterDao {
    @Insert
    void insertSemester(Semester semester);

    @Delete
    void deleteSemester(Semester semester);

    @Query("UPDATE Semester SET semesterCGPA=:cgpa WHERE id=:id")
    void updateSemesterCGPA(Double cgpa, int id);

    @Query("SELECT * FROM Semester ORDER BY id ASC")
    List<Semester> getAllSemester();

    @Query("DELETE FROM Semester")
    void deleteAllSemester();
}
