package com.GerenciamentoEstudanteAPI.service;

import com.GerenciamentoEstudanteAPI.model.Student;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ExcelService {

    private static final String FILE_PATH = "Alunos.xlsx";

    public List<Student> readStudentsFromExcel() {

        List<Student> students = new ArrayList<>();

        try {
            File file = new File(FILE_PATH); // Agora lendo diretamente do sistema de arquivos
            if (!file.exists()) {
                throw new RuntimeException("Arquivo " + FILE_PATH + " não foi encontrado");
            }

            FileInputStream fis = new FileInputStream(file);
            Workbook wb = new XSSFWorkbook(fis);
            Sheet sheet = wb.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            boolean isFirstRow = true;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (isFirstRow) { // Ignorar cabeçalho
                    isFirstRow = false;
                    continue;
                }

                String name = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                int age = (int) row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                String course = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();

                students.add(new Student(name, age, course));
            }

            wb.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public void writeStudentsToExcel(List<Student> students) {
        File file = new File(FILE_PATH);
        Workbook workbook;
        Sheet sheet;

        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Estudantes");

                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Nome");
                headerRow.createCell(1).setCellValue("Idade");
                headerRow.createCell(2).setCellValue("Curso");
            }

            int rowNum = sheet.getLastRowNum() + 1;

            // 🔹 Armazena nomes dos alunos já no Excel para evitar reescrita
            Set<String> existingStudentNames = new HashSet<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                existingStudentNames.add(row.getCell(0).getStringCellValue());
            }

            // 🔹 Adiciona apenas novos alunos que não estão no Excel
            for (Student student : students) {
                if (!existingStudentNames.contains(student.getName())) { // Verifica se já existe no Excel
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(student.getName());
                    row.createCell(1).setCellValue(student.getAge());
                    row.createCell(2).setCellValue(student.getCourse());
                }
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

            workbook.close();
            System.out.println("Arquivo Excel atualizado com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
