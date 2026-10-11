import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        StudentManager studentManager;
        try {
            studentManager = new StudentManager();
        } catch (IOException e) {
            System.out.println("Could not read students.txt: " + e.getMessage());
            System.out.println("Check the file and folder permissions, then start the program again.");
            return;
        }

        System.out.println("Welcome to the Student Management System!");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Enter your choice (1-6): ", 1, 6);

            switch (choice) {
                case 1:
                    addStudent(studentManager);
                    break;
                case 2:
                    viewAllStudents(studentManager);
                    break;
                case 3:
                    searchStudent(studentManager);
                    break;
                case 4:
                    updateStudent(studentManager);
                    break;
                case 5:
                    deleteStudent(studentManager);
                    break;
                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    // readInt keeps the choice within the menu range.
                    break;
            }
        }
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("========== Student Management System ==========");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student Details");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
    }

    private static void addStudent(StudentManager manager) {
        System.out.println("\n--- Add Student ---");
        int studentId = readInt("Student ID (positive number): ", 1, Integer.MAX_VALUE);

        if (manager.findStudentById(studentId) != null) {
            System.out.println("That student ID already exists. No student was added.");
            return;
        }

        Student student = readStudentDetails(studentId);
        try {
            if (manager.addStudent(student)) {
                System.out.println("Student added and saved successfully.");
            } else {
                System.out.println("That student ID already exists. No student was added.");
            }
        } catch (IOException e) {
            showSaveError(e);
        }
    }

    private static void viewAllStudents(StudentManager manager) {
        System.out.println("\n--- All Students ---");
        ArrayList<Student> students = manager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("There are no student records yet.");
            return;
        }

        System.out.printf("%-12s %-24s %-8s %-24s %-8s%n",
                "Student ID", "Name", "Age", "Course", "Marks");
        System.out.println("--------------------------------------------------------------------------");
        for (Student student : students) {
            System.out.printf("%-12d %-24s %-8d %-24s %-8.2f%n",
                    student.getStudentId(),
                    student.getName(),
                    student.getAge(),
                    student.getCourse(),
                    student.getMarks());
        }
    }

    private static void searchStudent(StudentManager manager) {
        System.out.println("\n--- Search Student ---");
        int studentId = readInt("Enter the student ID to search for: ", 1, Integer.MAX_VALUE);
        Student student = manager.findStudentById(studentId);
        if (student == null) {
            System.out.println("No student was found with ID " + studentId + ".");
        } else {
            printStudent(student);
        }
    }

    private static void updateStudent(StudentManager manager) {
        System.out.println("\n--- Update Student Details ---");
        int studentId = readInt("Enter the student ID to update: ", 1, Integer.MAX_VALUE);

        if (manager.findStudentById(studentId) == null) {
            System.out.println("No student was found with ID " + studentId + ".");
            return;
        }

        System.out.println("Enter the new details for this student:");
        Student updatedStudent = readStudentDetails(studentId);
        try {
            if (manager.updateStudent(updatedStudent)) {
                System.out.println("Student details updated and saved successfully.");
            } else {
                System.out.println("The student could not be found. No details were changed.");
            }
        } catch (IOException e) {
            showSaveError(e);
        }
    }

    private static void deleteStudent(StudentManager manager) {
        System.out.println("\n--- Delete Student ---");
        int studentId = readInt("Enter the student ID to delete: ", 1, Integer.MAX_VALUE);
        try {
            if (manager.deleteStudent(studentId)) {
                System.out.println("Student deleted and the file was updated.");
            } else {
                System.out.println("No student was found with ID " + studentId + ".");
            }
        } catch (IOException e) {
            showSaveError(e);
        }
    }

    private static Student readStudentDetails(int studentId) {
        String name = readText("Name: ");
        int age = readInt("Age (1-120): ", 1, 120);
        String course = readText("Course: ");
        double marks = readMarks("Marks (0-100): ");
        return new Student(studentId, name, age, course, marks);
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = SCANNER.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println("This value cannot be blank. Please try again.");
            } else if (value.contains("|")) {
                System.out.println("The | character is not allowed because it separates saved fields.");
            } else {
                return value;
            }
        }
    }

    private static int readInt(String prompt, int minimum, int maximum) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < minimum || value > maximum) {
                    System.out.println("Enter a whole number from " + minimum + " to " + maximum + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readMarks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                double marks = Double.parseDouble(input);
                if (!Double.isFinite(marks) || marks < 0 || marks > 100) {
                    System.out.println("Marks must be a number from 0 to 100.");
                } else {
                    return marks;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid marks as a number, such as 85 or 85.5.");
            }
        }
    }

    private static void printStudent(Student student) {
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Name:       " + student.getName());
        System.out.println("Age:        " + student.getAge());
        System.out.println("Course:     " + student.getCourse());
        System.out.printf("Marks:      %.2f%n", student.getMarks());
    }

    private static void showSaveError(IOException e) {
        System.out.println("Could not save the change to students.txt: " + e.getMessage());
        System.out.println("The change was not applied in this session. Check folder/file permissions.");
    }
}
