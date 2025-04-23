# 🎓 Student Course Tracker System

A Java-based console application that helps **students** enroll in courses and **admins** manage course offerings, student details, and grades. The system uses **MySQL** for data storage and follows proper **OOP design** with DAO architecture for clean database interaction.

---

## 📌 Features

### 👨‍🎓 Student Panel
- Register or Login using Roll Number and Password
- Enroll in available courses
- View enrolled courses
- View grades (if assigned)

### 🧑‍💼 Admin Panel
- Secure login using Employee ID and Password
- Add, update, or remove courses
- View all registered students
- Assign grades to students for enrolled courses

### 🗃️ Database
- Uses **MySQL** with properly normalized tables
- Enum fields for course type and grading
- Logging of user activities

---

## 🧱 Tech Stack

- **Java** (OOP, Exception Handling, File I/O)
- **MySQL** (Database operations with JDBC)
- **DAO Design Pattern**
- **Maven** (for dependency management, if integrated)
- **Git** (for version control)

---

## 🗂️ Project Structure

```
src/
├── courses/         # Course model, CourseType enum, DAO
├── enrollment/      # Grade DAO and related operations
├── users/           # Admin and Student classes, User base class, DAOs
├── util/            # DBUtil class for connection setup
└── main/            # Entry point and menu logic
```

---

## 🛠️ Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/Rohitkr0406/Student-Course-Tracker-System.git
```

### 2. Configure MySQL
- Create a database called `student_course_tracker`.
- Run the SQL schema provided in the repo to set up tables.

### 3. Update DB Credentials
- Open `DBUtil.java` and set your MySQL username & password.

### 4. Compile and Run
- Use any Java IDE (like IntelliJ, Eclipse) or CLI to run `Main.java`.

---

## 📄 Database Schema (Simplified)

- **students(roll_no, name, age, contact, password)**
- **admins(emp_id, name, password)**
- **courses(course_id, name, capacity, type)**
- **enrollments(roll_no, course_id, grade)**
- **logs(log_id, user_id, timestamp)**

---

## 📸 Sample Screens

> *(Add screenshots of student login, course list, grade display, etc. here if you want)*

---

## 🤝 Contributing

Want to contribute or report a bug?  
Feel free to open an issue or a pull request!

---

## 📬 Contact

**Author:** Rohit Kumar  
**GitHub:** [@Rohitkr0406](https://github.com/Rohitkr0406)

---

## 📝 License

This project is open-source and available under the [MIT License](LICENSE).
