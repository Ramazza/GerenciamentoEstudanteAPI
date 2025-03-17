package com.GerenciamentoEstudanteAPI.service;

import com.GerenciamentoEstudanteAPI.model.Student;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    private final ExcelService excelService;
    private final List<Student> students = new ArrayList<>();

    public StudentService(ExcelService excelService) {
        this.excelService = excelService;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void loadStudentsFromExcel() {
        students.clear();
        students.addAll(excelService.readStudentsFromExcel());
    }

    public void saveStudentsToExcel() {
        Set<Student> uniqueStudents = new HashSet<>(students);
        excelService.writeStudentsToExcel(new ArrayList<>(uniqueStudents));
    }
}
