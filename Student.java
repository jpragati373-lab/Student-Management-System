public class Student {
    private final int studentId;
    private final String name;
    private final int age;
    private final String course;
    private final double marks;

    public Student(int studentId, String name, int age, String course, double marks) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be a positive number.");
        }
        if (name == null || name.trim().isEmpty() || name.contains("|")
                || name.contains("\n") || name.contains("\r")) {
            throw new IllegalArgumentException("Name must not be blank or contain |.");
        }
        if (age < 1 || age > 120) {
            throw new IllegalArgumentException("Age must be between 1 and 120.");
        }
        if (course == null || course.trim().isEmpty() || course.contains("|")
                || course.contains("\n") || course.contains("\r")) {
            throw new IllegalArgumentException("Course must not be blank or contain |.");
        }
        if (!Double.isFinite(marks) || marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
        }

        this.studentId = studentId;
        this.name = name.trim();
        this.age = age;
        this.course = course.trim();
        this.marks = marks;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCourse() {
        return course;
    }

    public double getMarks() {
        return marks;
    }
}
