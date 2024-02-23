package com.example.webapplication.controller;

import com.example.webapplication.entity.Student;
import com.example.webapplication.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //handle method
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/students_form")
    public String createStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "students_form";

    }

    @GetMapping("/students/students_form")
    public String createStudentForm2(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "students_form";
    }

    @PostMapping("/students")
    public String addStudent(@ModelAttribute("student") Student student, @RequestParam(name = "action", required = false) String action) {
        if ("submit".equals(action)) {
            // Add the student only when the "Submit" button is clicked
            studentService.addStudent(student);
        }
        return "redirect:/students";
    }


    //update request handler

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable int id, Model model){
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";
    }
    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable int id,
                                @ModelAttribute("student") Student student,
                                @RequestParam(name = "action", required = false) String action,
                                Model model) {

        if ("submit".equals(action)) {
            // get student from database by id
            Student existingStudent = studentService.getStudentById(id);
            existingStudent.setId(id);
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setPassword(student.getPassword());

            // save updated student object
            studentService.updateStudent(existingStudent);
        }

        return "redirect:/students";
    }


    //handler method to handle delete requests
    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable int id){
        studentService.deleteStudentById(id);
        return  "redirect:/students";

    }
}
