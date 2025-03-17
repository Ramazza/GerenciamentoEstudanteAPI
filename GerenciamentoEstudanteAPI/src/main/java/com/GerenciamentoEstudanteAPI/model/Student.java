package com.GerenciamentoEstudanteAPI.model;

public class Student {

    String name;
    int age;
    String course;

    public Student (String name, int age, String course) {
        this.name = name;
        this.age = age;
        this.course = course;
    }

    //Getter and Setter

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setCourse(String course) { this.course = course; }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age" + age +
                ", course='" + course + '\'' +
                '}';
    }
}
