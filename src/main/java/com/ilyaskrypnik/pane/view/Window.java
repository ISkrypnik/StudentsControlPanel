package com.ilyaskrypnik.pane.view;

import com.ilyaskrypnik.pane.controller.StudentController;
import com.ilyaskrypnik.pane.domain.Student;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Window {

    private static final Logger LOG = LoggerFactory.getLogger(Window.class);

    private final StudentController studentController;
    private final Display display;
    private final Shell shell;

    public Window(StudentController studentController) {
        this.studentController = studentController;
        this.display = new Display();
        this.shell = new Shell(display);
    }

    public void createWindow() {
        shell.setText("Students Control Panel");
        shell.setSize(650, 540);
        shell.setMinimumSize(650, 540);
        shell.setMaximized(false);
        adjustShellToCenter();

        GridLayout gridLayout = new GridLayout(6, true);
        gridLayout.verticalSpacing = 8;
        shell.setLayout(gridLayout);


        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        final Text firstName = createInputRow("First name", gridData);
        final Text lastName = createInputRow("Last name", gridData);
        final Text groupNumber = createInputRow("Group number", gridData);

        Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL);
        addButtons(firstName, lastName, groupNumber, table);
        addTable(table);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }

    private Text createInputRow(String labelText, GridData gridData) {
        Label label = new Label(shell, SWT.NONE);
        label.setText(labelText + ":");
        final Text textField = new Text(shell, SWT.BORDER);
        textField.setBounds(40, 50, 70, 70);
        gridData.horizontalSpan = 5;
        textField.setLayoutData(gridData);

        return textField;
    }

    private void addButtons(Text firstName, Text lastName, Text groupNumber, Table table) {
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.horizontalSpan = 2;

        final Button saveButton = new Button(shell, SWT.PUSH);
        configureButton(saveButton, "Save", gridData, 40, 100);

        final Button deleteButton = new Button(shell, SWT.PUSH);
        configureButton(deleteButton, "Delete", gridData, 110, 100);

        final Button updateButton = new Button(shell, SWT.PUSH);
        configureButton(updateButton, "Edit", gridData, 180, 100);

        saveButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                Student student = new Student();
                boolean areParamsValid = validateInputParameters(student, firstName.getText(), lastName.getText(),
                        groupNumber.getText());

                if (areParamsValid) {
                    int[] selectionIndices = table.getSelectionIndices();
                    if (selectionIndices.length != 0) {
                        TableItem item = table.getItem(selectionIndices[0]);
                        long studentId = Long.parseLong(item.getText(0));
                        student.setId(studentId);

                        if (studentController.updateStudent(student) != null) {
                            item.setText(1, firstName.getText());
                            item.setText(2, lastName.getText());
                            item.setText(3, groupNumber.getText());
                        }
                        clearInputForm(firstName, lastName, groupNumber);
                    } else {
                        Student newOne = studentController.createNewStudent(student);
                        if (newOne != null) {
                            addTableItem(table, newOne);
                            clearInputForm(firstName, lastName, groupNumber);
                        }
                    }
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                //
            }
        });

        deleteButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int[] selectionIndices = table.getSelectionIndices();
                if (selectionIndices.length != 0) {
                    TableItem item = table.getItem(selectionIndices[0]);
                    String studentId = item.getText(0);
                    studentController.deleteStudent(Long.parseLong(studentId));

                    table.remove(selectionIndices[0]);
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                //
            }
        });

        updateButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int[] selectionIndices = table.getSelectionIndices();
                if (selectionIndices.length != 0) {
                    TableItem item = table.getItem(selectionIndices[0]);
                    firstName.setText(item.getText(1));
                    lastName.setText(item.getText(2));
                    groupNumber.setText(item.getText(3));
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                //
            }
        });
    }

    private void configureButton(Button button, String buttonText, GridData gridData, int posx, int posy) {
        button.setBounds(posx, posy, 70, 50);
        button.setText(buttonText);
        button.setLayoutData(gridData);
    }

    private void clearInputForm(Text firstName, Text lastName, Text groupNumber) {
        firstName.setText("");
        lastName.setText("");
        groupNumber.setText("");
    }

    private void addTable(Table table) {
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        gridData.horizontalSpan = 8;

        table.setHeaderVisible(true);
        String[] titles = {"Id", "First name", "Last name", "Group number"};

        for (String title : titles) {
            TableColumn column = new TableColumn(table, SWT.NULL);
            column.setText(title);
        }

        List<Student> allStudents = studentController.getAllStudents();
        for (Student student : allStudents) {
            addTableItem(table, student);
        }

        for (int i = 0; i < titles.length; i++) {
            table.getColumn(i).pack();
        }

        table.setBounds(40, 50, 900, 500);
        table.setLayoutData(gridData);
    }

    private void addTableItem(Table table, Student student) {
        TableItem item = new TableItem(table, SWT.NULL);
        item.setText(0, String.valueOf(student.getId()));
        item.setText(1, student.getFirstName());
        item.setText(2, student.getLastName());
        item.setText(3, String.valueOf(student.getGroupNumber()));
    }

    private boolean validateInputParameters(Student student, String firstName, String lastName, String groupNumber) {
        if (!firstName.isEmpty() && !lastName.isEmpty() && !groupNumber.isEmpty()) {
            try {
                long gNumber = Long.parseLong(groupNumber);
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setGroupNumber(gNumber);
                return true;
            } catch (NumberFormatException e) {
                LOG.error("Wrong group number.");
                return false;
            }
        }
        return false;
    }

    private void adjustShellToCenter() {
        Monitor primary = display.getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = shell.getBounds();

        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;

        shell.setLocation(x, y);
    }
}