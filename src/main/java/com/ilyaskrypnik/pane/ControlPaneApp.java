package com.ilyaskrypnik.pane;

import com.ilyaskrypnik.pane.controller.StudentController;
import com.ilyaskrypnik.pane.service.StudentService;
import com.ilyaskrypnik.pane.view.Window;

public class ControlPaneApp {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        StudentController studentController = new StudentController(studentService);
        Window window = new Window(studentController);
        window.createWindow();
    }
}
