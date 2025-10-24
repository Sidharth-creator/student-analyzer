# Student Academic Performance Analyzer

A Java-based desktop application for tracking and analyzing student academic performance. The application uses a graphical user interface (GUI) to provide a user-friendly experience, with data stored in a MySQL database. It features interactive graphs and filterable tables to visualize student growth and detailed marks.

## Features

- **Graphical User Interface:** Modern and intuitive UI built with Java Swing and the Nimbus Look and Feel.
- **Database Integration:** Securely stores and retrieves all student data from a MySQL database.
- **Add Exam Sessions:** Easily add new student records, including marks for multiple subjects and exam types (internals, semester).
- **Detailed Marks View:** A filterable table that displays a student's marks, allowing you to view scores for specific exams (e.g., "Internal 1", "Semester Exam") or all marks at once.
- **Interactive Performance Graph:** A powerful visualization tool with two modes:
  1.  **Overall Performance:** Shows the trend of total marks for all subjects and the SGPA across different semesters.
  2.  **Intra-Semester Progress:** Select a specific subject to see a graph of the student's progress from Internal 1 to the final Semester Exam, with each semester represented as a separate line for easy comparison.

---

## Prerequisites

Before you begin, ensure you have the following installed on your system:

1.  **Java Development Kit (JDK):** Version 8 or higher.
2.  **MySQL Community Server:** The database used to store the application data.

All required Java libraries (`JFreeChart`, `MySQL Connector/J`) are included in the `lib/` directory.

---

## Installation and Setup

Follow these steps to get the application running on your local machine.

### Step 1: Get the Code

Clone this repository or download the source code to a folder on your computer.

### Step 2: Set up MySQL Server

1.  **Install MySQL:** If you haven't already, [download and install MySQL Community Server](https://dev.mysql.com/downloads/mysql/). The easiest way is to use the "MySQL Installer for Windows".
2.  **Remember Your Password:** During installation, you will be prompted to create a password for the `root` user. **Take note of this password.**
3.  **Start the Server:** Ensure the MySQL server is running. You can check this in the Windows `services.msc` panel.

The application is designed to automatically create the necessary `college` database and `marks` table on its first connection, so no manual database setup is required.

### Step 3: Configure the Database Password

You must tell the Java application which password to use to connect to your database.

1.  Open the file: `src/academic/data/DataManager.java`.
2.  Find the following line (around line 19):
    ```java
    PASSWORD = "1234"; // CHANGE THIS
    ```
3.  Replace `"1234"` with the actual MySQL `root` password you created in the previous step.
4.  Save the file.

### Step 4: Compile the Application

A batch script is provided to compile all the necessary Java files.

- In the project's root directory, double-click the `compile-gui.bat` file.

This will create a `classes/` directory containing all the compiled `.class` files.

### Step 5: Run the Application

- In the project's root directory, double-click the `run-gui.bat` file.

The application will start, and you will be greeted with the initial student information dialog.

---

## How to Use the Application

1.  **Initial Dialog:** The first window prompts you for a Student ID.
    - **Analyze a Student:** Enter an existing Student ID and click "Analyze". This will open the main analysis window.
    - **Add Data:** Click "Add Exam Session" to open a form where you can enter a new record for a student. To see graph trends, a student needs at least two exam sessions (e.g., Year 1/Semester 1 and Year 1/Semester 2).
2.  **Detailed Marks Tab:** This is the first tab you'll see. It shows a detailed table of a student's marks. Use the dropdown menu at the top to filter the results (e.g., show only "Semester Exam" marks).
3.  **Performance Graph Tab:** This tab provides visual insights.
    - Use the dropdown menu to select either "Overall Performance" or a specific subject.
    - If you select a subject, the graph will show the student's progress within each semester, from internals to the final exam.

---

## Code Logic and Libraries

### Project Structure

The source code is organized into a clean package structure within the `src/` directory:

- `academic/ui/`: Contains all the GUI components (windows, panels, dialogs).
- `academic/data/`: Manages all database interactions.
- `academic/models/`: Contains the data model class (`ExamSession`).

### Code Breakdown

- **`StudentInfoDialog.java`:** The entry point of the application. This simple dialog handles the initial user input.
- **`MainFrame.java`:** The main analysis window. It holds the tabbed pane and is created only after valid student data is fetched.
- **`AddExamDialog.java`:** A form for inputting and saving a new exam session record to the database.
- **`MarksTablePanel.java`:** A custom panel that contains the `JTable` and the filter logic for displaying detailed marks.
- **`GraphPanel.java`:** Manages the `JFreeChart` object. It contains all the logic for creating and dynamically updating the graph based on the user's filter selection.
- **`DataManager.java`:** The central hub for all database operations. It contains methods for adding and retrieving student data using JDBC. It's the only class that directly communicates with the MySQL database.
- **`ExamSession.java`:** A simple Java object (POJO) that represents the data for a single student's exam session (i.e., one row from the `marks` table).

### Libraries Used

- **Java Swing:** The core library used for building the desktop GUI. Part of the standard Java JDK.
- **JFreeChart:** A powerful open-source library used for creating the line graphs. The required files (`jfreechart-1.5.4.jar` and `jcommon-1.0.24.jar`) are included in the `lib/` directory.
- **MySQL Connector/J:** The official JDBC driver for connecting to a MySQL database. The required file (`mysql-connector-j-9.5.0.jar`) is included in the `lib/` directory.

---

## Importing into Eclipse IDE

Follow these steps to import and run the project using the Eclipse IDE.

### Step 1: Import the Project

1.  Open Eclipse and go to **File -> Import...**.
2.  Expand the **General** folder and select **Existing Projects into Workspace**. Click **Next**.
3.  Click the **Browse...** button next to "Select root directory" and navigate to the project's root folder.
4.  Ensure the project is checked in the "Projects" list and click **Finish**.

### Step 2: Configure the Build Path

After importing, you must add the required libraries to the build path.

1.  In the "Package Explorer," **right-click on the project**.
2.  Select **Build Path -> Configure Build Path...**.
3.  In the new window, go to the **Libraries** tab.
4.  Click **Add JARs...**.
5.  Navigate into the project's `lib` folder.
6.  Select all the `.jar` files and click **OK**.
7.  Click **Apply and Close**. The project should now be error-free.

### Step 3: Run the Application

1.  In the "Package Explorer," navigate to `src/academic/ui/StudentInfoDialog.java`.
2.  **Right-click on the `StudentInfoDialog.java` file.**
3.  Select **Run As -> Java Application**.

The application will now launch.
