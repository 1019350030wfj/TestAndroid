package com.jayden.testandroid.aidl;

import com.jayden.testandroid.test_aidl.Student;

import java.util.List;

/**
 * Created by Jayden on 2016/2/17.
 */
public interface IMyService {
    List<Student> getStudent();
    void addStudent(Student student);
}
