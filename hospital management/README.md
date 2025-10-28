# Hospital Management System

A comprehensive Java-based hospital management system with a graphical user interface built using Swing.

## Features

### 🎨 Modern & Attractive GUI
- **Beautiful Design** - Modern flat design with gradient headers and professional styling
- **Emoji Icons** - Intuitive emoji icons throughout the interface for better user experience
- **Color-coded Interface** - Different color schemes for each section (blue for patients, green for doctors, purple for appointments)
- **Responsive Layout** - Split-pane layouts that adapt to window resizing
- **Hover Effects** - Interactive buttons with hover animations
- **Status-based Coloring** - Appointment table rows colored by status (scheduled/completed/cancelled)

### 👥 Patient Management
- Add, update, and delete patient records with enhanced forms
- Store comprehensive patient information with medical history
- Modern table with alternating row colors and professional styling
- Form validation with user-friendly error messages
- Search functionality for quick patient lookup

### 👨‍⚕️ Doctor Management
- Manage doctor profiles with specialization and consultation details
- Professional directory-style layout
- Enhanced form fields with emoji labels
- Fee management with currency formatting
- Schedule tracking for doctor availability

### 📅 Appointment Management
- Intuitive appointment scheduling interface
- Visual status indicators with color coding
- Date and time picker functionality
- Patient-doctor relationship management
- Notes system for appointment details
- Status filtering capabilities

## Project Structure

```
HospitalManagement/
├── src/
│   ├── models/
│   │   ├── Patient.java          # Patient data model
│   │   ├── Doctor.java           # Doctor data model
│   │   └── Appointment.java      # Appointment data model
│   ├── gui/
│   │   ├── MainFrame.java        # Main application window
│   │   ├── PatientPanel.java     # Patient management interface
│   │   ├── DoctorPanel.java      # Doctor management interface
│   │   └── AppointmentPanel.java # Appointment management interface
│   ├── database/
│   │   └── DatabaseManager.java  # In-memory database operations
│   └── HospitalManagementApp.java # Main application entry point
└── README.md
```

## How to Run

1. **Compile the Java files:**
   ```bash
   javac -d . src/**/*.java src/*.java
   ```

2. **Run the application:**
   ```bash
   java HospitalManagementApp
   ```

## System Requirements

- Java 8 or higher
- Any operating system that supports Java (Windows, macOS, Linux)

## Usage

1. **Launch the application** - The main window will open with three tabs
2. **Manage Patients** - Use the Patients tab to add, edit, or delete patient records
3. **Manage Doctors** - Use the Doctors tab to manage doctor information
4. **Schedule Appointments** - Use the Appointments tab to create and manage appointments

### Adding Records
- Fill in the form fields in any tab
- Click "Add" button to create new records
- All required fields must be filled

### Editing Records
- Select a record from the table
- The form will populate with the selected record's data
- Modify the information and click "Update"

### Deleting Records
- Select a record from the table
- Click "Delete" and confirm the action

## Enhanced Features

- **🎨 Modern UI Design** - Professional gradient headers, emoji icons, and color-coded sections
- **📊 Smart Data Visualization** - Status-based row coloring and alternating table rows
- **✅ Advanced Form Validation** - Real-time validation with user-friendly error messages
- **🔍 Search & Filter** - Quick search functionality and status-based filtering
- **💫 Interactive Elements** - Hover effects, styled buttons, and smooth transitions
- **📱 Responsive Layout** - Split-pane design that adapts to different screen sizes
- **🎯 Intuitive Navigation** - Tabbed interface with emoji icons for easy identification
- **💾 Fast Data Operations** - In-memory database with sample data for immediate testing
- **🖥️ Cross-platform** - Runs on Windows, macOS, and Linux with consistent appearance

## Sample Data

The application comes with pre-loaded sample data:
- 3 sample doctors with different specializations
- 2 sample patients
- Ready to create appointments immediately

## Technical Details

- **GUI Framework:** Java Swing
- **Data Storage:** In-memory collections (ArrayList)
- **Architecture:** MVC pattern with separate model, view, and data management layers
- **Date/Time Handling:** Java 8 LocalDateTime API

This hospital management system provides a solid foundation for managing basic hospital operations and can be extended with additional features like database persistence, reporting, and more advanced scheduling capabilities.