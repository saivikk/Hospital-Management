package gui;

import database.DatabaseManager;
import models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentPanel extends JPanel {
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private JComboBox<Patient> patientCombo;
    private JComboBox<Doctor> doctorCombo;
    private JTextField dateField, timeField;
    private JComboBox<String> statusCombo;
    private JTextArea notesArea;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private DatabaseManager dbManager;
    private int selectedAppointmentId = -1;
    
    public AppointmentPanel() {
        dbManager = DatabaseManager.getInstance();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        refreshTable();
        loadComboBoxes();
    }
    
    private void initializeComponents() {
        // Enhanced table setup
        String[] columnNames = {"ID", "Patient", "Doctor", "Date & Time", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        appointmentTable = new JTable(tableModel);
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentTable.setRowHeight(30);
        appointmentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        appointmentTable.setGridColor(new Color(230, 230, 230));
        appointmentTable.setSelectionBackground(new Color(52, 152, 219, 50));
        appointmentTable.setSelectionForeground(new Color(52, 58, 64));
        
        // Style table header
        JTableHeader header = appointmentTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(156, 39, 176));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 35));
        
        // Alternate row colors with status-based coloring
        appointmentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (column == 4) { // Status column
                        String status = (String) value;
                        switch (status) {
                            case "SCHEDULED":
                                c.setBackground(new Color(217, 237, 247));
                                break;
                            case "COMPLETED":
                                c.setBackground(new Color(223, 240, 216));
                                break;
                            case "CANCELLED":
                                c.setBackground(new Color(248, 215, 218));
                                break;
                            default:
                                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                        }
                    } else {
                        c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                    }
                }
                return c;
            }
        });
        
        // Enhanced form fields
        patientCombo = new JComboBox<>();
        doctorCombo = new JComboBox<>();
        styleComboBox(patientCombo);
        styleComboBox(doctorCombo);
        
        dateField = createStyledTextField("2024-12-25", 20);
        timeField = createStyledTextField("10:00", 20);
        
        statusCombo = new JComboBox<>(new String[]{"SCHEDULED", "COMPLETED", "CANCELLED"});
        styleComboBox(statusCombo);
        
        notesArea = new JTextArea(4, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        notesArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            new EmptyBorder(8, 8, 8, 8)
        ));
        
        // Enhanced buttons
        addButton = createStyledButton("➕ Schedule Appointment", new Color(40, 167, 69));
        updateButton = createStyledButton("✏️ Update Appointment", new Color(255, 193, 7));
        deleteButton = createStyledButton("🗑️ Cancel Appointment", new Color(220, 53, 69));
        clearButton = createStyledButton("🔄 Clear Form", new Color(108, 117, 125));
        
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    private JTextField createStyledTextField(String text, int columns) {
        JTextField field = new JTextField(text, columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            new EmptyBorder(8, 12, 8, 12)
        ));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 35));
        return field;
    }
    
    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, 35));
        comboBox.setBackground(Color.WHITE);
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Enhanced table panel
        JPanel tablePanel = createTablePanel();
        
        // Enhanced form panel
        JPanel formPanel = createFormPanel();
        
        // Split pane for better layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, formPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(null);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(248, 249, 250));
        
        // Title and filter panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        
        JLabel titleLabel = new JLabel("📅 Appointment Schedule");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 58, 64));
        
        // Status filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setBackground(new Color(248, 249, 250));
        
        JLabel filterLabel = new JLabel("Filter by Status:");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"All", "SCHEDULED", "COMPLETED", "CANCELLED"});
        styleComboBox(filterCombo);
        
        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(filterPanel, BorderLayout.EAST);
        
        // Table with enhanced styling
        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
        tableScrollPane.getViewport().setBackground(Color.WHITE);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        
        // Form panel with modern styling
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(),
            "📅 Appointment Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            new Color(52, 58, 64)
        );
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                titledBorder
            ),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Create form in two columns
        // Left column
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(createLabel("👤 Patient:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(patientCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("👨‍⚕️ Doctor:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(doctorCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("📅 Date:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(dateField, gbc);
        
        // Right column
        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.insets = new Insets(8, 20, 8, 8);
        formPanel.add(createLabel("🕒 Time:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(timeField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("📊 Status:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(statusCombo, gbc);
        
        // Notes span full width
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.insets = new Insets(8, 8, 8, 8);
        formPanel.add(createLabel("📝 Notes:"), gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        formPanel.add(new JScrollPane(notesArea), gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weighty = 0;
        formPanel.add(buttonPanel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        return mainPanel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(52, 58, 64));
        return label;
    }
    
    private void setupEventListeners() {
        // Table selection listener
        appointmentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = appointmentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedAppointmentId = (Integer) tableModel.getValueAt(selectedRow, 0);
                    loadAppointmentData(selectedAppointmentId);
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    selectedAppointmentId = -1;
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });
        
        // Button listeners
        addButton.addActionListener(e -> addAppointment());
        updateButton.addActionListener(e -> updateAppointment());
        deleteButton.addActionListener(e -> deleteAppointment());
        clearButton.addActionListener(e -> clearForm());
    }
    
    private void addAppointment() {
        if (validateForm()) {
            Appointment appointment = createAppointmentFromForm();
            dbManager.addAppointment(appointment);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Appointment added successfully!");
        }
    }
    
    private void updateAppointment() {
        if (selectedAppointmentId != -1 && validateForm()) {
            Appointment appointment = createAppointmentFromForm();
            appointment.setAppointmentId(selectedAppointmentId);
            dbManager.updateAppointment(appointment);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Appointment updated successfully!");
        }
    }
    
    private void deleteAppointment() {
        if (selectedAppointmentId != -1) {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this appointment?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                dbManager.deleteAppointment(selectedAppointmentId);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Appointment deleted successfully!");
            }
        }
    }
    
    private Appointment createAppointmentFromForm() {
        Appointment appointment = new Appointment();
        Patient selectedPatient = (Patient) patientCombo.getSelectedItem();
        Doctor selectedDoctor = (Doctor) doctorCombo.getSelectedItem();
        
        if (selectedPatient != null) {
            appointment.setPatientId(selectedPatient.getPatientId());
        }
        if (selectedDoctor != null) {
            appointment.setDoctorId(selectedDoctor.getDoctorId());
        }
        
        try {
            String dateTimeStr = dateField.getText().trim() + "T" + timeField.getText().trim() + ":00";
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
            appointment.setAppointmentDateTime(dateTime);
        } catch (Exception e) {
            // This should be caught by validation
        }
        
        appointment.setStatus((String) statusCombo.getSelectedItem());
        appointment.setNotes(notesArea.getText().trim());
        
        return appointment;
    }
    
    private void loadAppointmentData(int appointmentId) {
        Appointment appointment = dbManager.getAppointmentById(appointmentId);
        if (appointment != null) {
            // Set patient combo
            Patient patient = dbManager.getPatientById(appointment.getPatientId());
            if (patient != null) {
                patientCombo.setSelectedItem(patient);
            }
            
            // Set doctor combo
            Doctor doctor = dbManager.getDoctorById(appointment.getDoctorId());
            if (doctor != null) {
                doctorCombo.setSelectedItem(doctor);
            }
            
            // Set date and time
            LocalDateTime dateTime = appointment.getAppointmentDateTime();
            dateField.setText(dateTime.toLocalDate().toString());
            timeField.setText(dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            
            statusCombo.setSelectedItem(appointment.getStatus());
            notesArea.setText(appointment.getNotes());
        }
    }
    
    private boolean validateForm() {
        if (patientCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a patient.");
            return false;
        }
        if (doctorCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a doctor.");
            return false;
        }
        try {
            String dateTimeStr = dateField.getText().trim() + "T" + timeField.getText().trim() + ":00";
            LocalDateTime.parse(dateTimeStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter valid date (YYYY-MM-DD) and time (HH:MM).");
            return false;
        }
        return true;
    }
    
    private void clearForm() {
        patientCombo.setSelectedIndex(-1);
        doctorCombo.setSelectedIndex(-1);
        dateField.setText("2024-12-25");
        timeField.setText("10:00");
        statusCombo.setSelectedIndex(0);
        notesArea.setText("");
        selectedAppointmentId = -1;
        appointmentTable.clearSelection();
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    private void loadComboBoxes() {
        // Load patients
        patientCombo.removeAllItems();
        List<Patient> patients = dbManager.getAllPatients();
        for (Patient patient : patients) {
            patientCombo.addItem(patient);
        }
        
        // Load doctors
        doctorCombo.removeAllItems();
        List<Doctor> doctors = dbManager.getAllDoctors();
        for (Doctor doctor : doctors) {
            doctorCombo.addItem(doctor);
        }
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Appointment> appointments = dbManager.getAllAppointments();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        for (Appointment appointment : appointments) {
            Patient patient = dbManager.getPatientById(appointment.getPatientId());
            Doctor doctor = dbManager.getDoctorById(appointment.getDoctorId());
            
            Object[] row = {
                appointment.getAppointmentId(),
                patient != null ? patient.getName() : "Unknown",
                doctor != null ? doctor.getName() : "Unknown",
                appointment.getAppointmentDateTime().format(formatter),
                appointment.getStatus()
            };
            tableModel.addRow(row);
        }
        
        // Refresh combo boxes as well
        loadComboBoxes();
    }
}