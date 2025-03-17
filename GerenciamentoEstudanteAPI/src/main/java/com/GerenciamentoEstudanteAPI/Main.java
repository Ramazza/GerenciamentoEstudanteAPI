package com.GerenciamentoEstudanteAPI;

import com.GerenciamentoEstudanteAPI.model.Student;
import com.GerenciamentoEstudanteAPI.service.ExcelService;
import com.GerenciamentoEstudanteAPI.service.StudentService;

public class Main {

	public static void main(String[] args) {

		ExcelService excelService = new ExcelService();
		StudentService studentService = new StudentService(excelService);

		studentService.loadStudentsFromExcel();

		studentService.addStudent(new Student("Rodrigo Perez", 20, "Engenharia"));

		System.out.println("Estudantes na memória:");
		studentService.getAllStudents().forEach(System.out::println);

		studentService.saveStudentsToExcel();  // Salva no Excel
		System.out.println("\nDados salvos no Excel!");

	}
}

