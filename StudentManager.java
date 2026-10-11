import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class StudentManager {
    private final ArrayList<Student> students = new ArrayList<>();
    private final Path dataFile = Paths.get("students.txt");

    public StudentManager() throws IOException {
        loadStudents();
    }

    // A missing data file is normal the first time the program is run.
    private void loadStudents() throws IOException {
        if (!Files.exists(dataFile)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(dataFile, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] fields = line.split("\\|", -1);

                try {
                    if (fields.length != 5) {
                        throw new IllegalArgumentException("Expected five fields.");
                    }

                    Student student = new Student(
                            Integer.parseInt(fields[0]),
                            fields[1],
                            Integer.parseInt(fields[2]),
                            fields[3],
                            Double.parseDouble(fields[4]));

                    if (findStudentById(student.getStudentId()) != null) {
                        throw new IllegalArgumentException("Duplicate student ID.");
                    }
                    students.add(student);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid record on line " + lineNumber
                            + " of students.txt: " + e.getMessage());
                }
            }
        }
    }

    public boolean addStudent(Student student) throws IOException {
        if (findStudentById(student.getStudentId()) != null) {
            return false;
        }

        ArrayList<Student> updatedStudents = new ArrayList<>(students);
        updatedStudents.add(student);
        saveStudents(updatedStudents);
        students.add(student);
        return true;
    }

    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student findStudentById(int studentId) {
        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null;
    }

    public boolean updateStudent(Student updatedStudent) throws IOException {
        int studentIndex = findStudentIndex(updatedStudent.getStudentId());
        if (studentIndex == -1) {
            return false;
        }

        ArrayList<Student> updatedStudents = new ArrayList<>(students);
        updatedStudents.set(studentIndex, updatedStudent);
        saveStudents(updatedStudents);
        students.set(studentIndex, updatedStudent);
        return true;
    }

    public boolean deleteStudent(int studentId) throws IOException {
        int studentIndex = findStudentIndex(studentId);
        if (studentIndex == -1) {
            return false;
        }

        ArrayList<Student> updatedStudents = new ArrayList<>(students);
        updatedStudents.remove(studentIndex);
        saveStudents(updatedStudents);
        students.remove(studentIndex);
        return true;
    }

    private int findStudentIndex(int studentId) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId() == studentId) {
                return i;
            }
        }
        return -1;
    }

    // Save the new list before changing the in-memory list, so a write error
    // does not make the current session appear to have saved the change.
    private void saveStudents(ArrayList<Student> studentsToSave) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                dataFile,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {
            for (Student student : studentsToSave) {
                writer.write(student.getStudentId() + "|"
                        + student.getName() + "|"
                        + student.getAge() + "|"
                        + student.getCourse() + "|"
                        + student.getMarks());
                writer.newLine();
            }
        }
    }
}
