# Student Management System

A beginner-friendly Java console application for managing student records. The program uses classes, objects, an `ArrayList`, input validation, and file handling. Student records are saved in `students.txt` in the program's current working directory and loaded the next time the program starts.

## Features

- Add a student with an ID, name, age, course, and marks.
- View all saved student records.
- Search for a student by ID.
- Update a student's details.
- Delete a student.
- Prevent duplicate student IDs.
- Validate IDs, ages, marks, and required text fields.
- Save changes to `students.txt` and reload them on startup.
- Handle a first run without a data file and report file read/write errors.

## Requirements

- JDK 21 installed.
- Visual Studio Code with a Java extension pack (recommended).
- A terminal opened in this project folder.

## Compile and run

In the VS Code terminal, make sure the current directory is the folder containing `Main.java`, then run these exact commands:

```powershell
javac Main.java Student.java StudentManager.java
java Main
```

The program creates `students.txt` in the current working directory the first time you add a student. Keep running the program from the same folder to use the same student data.

## Test each menu option

1. **Add Student** — choose `1`, enter an ID such as `101`, a name, an age from 1 to 120, a course, and marks from 0 to 100. Try entering the same ID again to confirm duplicates are rejected.
2. **View All Students** — choose `2` and confirm the student appears in the list.
3. **Search Student by ID** — choose `3`, enter `101`, and confirm the details are shown. Try an unused ID too.
4. **Update Student Details** — choose `4`, enter `101`, and provide new details. Choose `2` afterward to confirm the updated information.
5. **Delete Student** — choose `5`, enter `101`, then choose `2` or `3` to confirm the record is gone.
6. **Exit and persistence** — add a record, choose `6`, start the program again with `java Main`, and choose `2` to confirm the record was loaded from `students.txt`.

You can also test validation by entering text where a number is requested, an age outside 1-120, marks outside 0-100, or a blank name/course. The program should ask again rather than accept those values.
