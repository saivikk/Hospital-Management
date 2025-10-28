package gui;

import database.DatabaseManager;
import models.Doctor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class DoctorPanel extends JPanel {
    private JTable doctorTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, specializationField, phoneField, emailField, scheduleField, feeField;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private DatabaseManager dbManager;
    private int selectedDoctorId = -1;
    
    public DoctorPanel() {
        dbManager = DatabaseManager.getInstance();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        refreshTable();
    }
    
    private void initializeComponents() {
        // Enhanced table setup
        String[] columnNames = {"ID", "Name", "Specialization", "Phone", "Email", "Fee"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        doctorTable = new JTable(tableModel);
        doctorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        doctorTable.setRowHeight(30);
        doctorTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        doctorTable.setGridColor(new Color(230, 230, 230));
        doctorTable.setSelectionBackground(new Color(52, 152, 219, 50));
        doctorTable.setSelectionForeground(new Color(52, 58, 64));
        
        // Style table header
        JTableHeader header = doctorTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(40, 167, 69));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 35));
        
        // Alternate row colors
        doctorTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                }
                return c;
            }
        });
        
        // Enhanced form fields
        nameField = createStyledTextField(20);
        specializationField = createStyledTextField(20);
        phoneField = createStyledTextField(20);
        emailField = createStyledTextField(20);
        scheduleField = createStyledTextField(20);
        feeField = createStyledTextField(20);
        
        // Enhanced buttons
        addButton = createStyledButton("➕ Add Doctor", new Color(40, 167, 69));
        updateButton = createStyledButton("✏️ Update Doctor", new Color(255, 193, 7));
        deleteButton = createStyledButton("🗑️ Delete Doctor", new Color(220, 53, 69));
        clearButton = createStyledButton("🔄 Clear Form", new Color(108, 117, 125));
        
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            new EmptyBorder(8, 12, 8, 12)
        ));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 35));
        return field;
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
        
        // Title panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        
        JLabel titleLabel = new JLabel("👨‍⚕️ Doctor Directory");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 58, 64));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Table with enhanced styling
        JScrollPane tableScrollPane = new JScrollPane(doctorTable);
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
            "👨‍⚕️ Doctor Information",
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
        formPanel.add(createLabel("👤 Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("🩺 Specialization:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(specializationField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("📞 Phone:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(phoneField, gbc);
        
        // Right column
        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.insets = new Insets(8, 20, 8, 8);
        formPanel.add(createLabel("📧 Email:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(emailField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("🕒 Schedule:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(scheduleField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("💰 Fee:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(feeField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 8, 8, 8);
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
        doctorTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = doctorTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedDoctorId = (Integer) tableModel.getValueAt(selectedRow, 0);
                    loadDoctorData(selectedDoctorId);
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    selectedDoctorId = -1;
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });
        
        // Button listeners
        addButton.addActionListener(e -> addDoctor());
        updateButton.addActionListener(e -> updateDoctor());
        deleteButton.addActionListener(e -> deleteDoctor());
        clearButton.addActionListener(e -> clearForm());
    }
    
    private void addDoctor() {
        if (validateForm()) {
            Doctor doctor = createDoctorFromForm();
            dbManager.addDoctor(doctor);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Doctor added successfully!");
        }
    }
    
    private void updateDoctor() {
        if (selectedDoctorId != -1 && validateForm()) {
            Doctor doctor = createDoctorFromForm();
            doctor.setDoctorId(selectedDoctorId);
            dbManager.updateDoctor(doctor);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Doctor updated successfully!");
        }
    }
    
    private void deleteDoctor() {
        if (selectedDoctorId != -1) {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this doctor?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                dbManager.deleteDoctor(selectedDoctorId);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Doctor deleted successfully!");
            }
        }
    }
    
    private Doctor createDoctorFromForm() {
        Doctor doctor = new Doctor();
        doctor.setName(nameField.getText().trim());
        doctor.setSpecialization(specializationField.getText().trim());
        doctor.setPhone(phoneField.getText().trim());
        doctor.setEmail(emailField.getText().trim());
        doctor.setSchedule(scheduleField.getText().trim());
        doctor.setConsultationFee(Double.parseDouble(feeField.getText().trim()));
        return doctor;
    }
    
    private void loadDoctorData(int doctorId) {
        Doctor doctor = dbManager.getDoctorById(doctorId);
        if (doctor != null) {
            nameField.setText(doctor.getName());
            specializationField.setText(doctor.getSpecialization());
            phoneField.setText(doctor.getPhone());
            emailField.setText(doctor.getEmail());
            scheduleField.setText(doctor.getSchedule());
            feeField.setText(String.valueOf(doctor.getConsultationFee()));
        }
    }
    
    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter doctor name.");
            return false;
        }
        if (specializationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter specialization.");
            return false;
        }
        if (phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter phone number.");
            return false;
        }
        try {
            double fee = Double.parseDouble(feeField.getText().trim());
            if (fee < 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid consultation fee.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid consultation fee.");
            return false;
        }
        return true;
    }
    
    private void clearForm() {
        nameField.setText("");
        specializationField.setText("");
        phoneField.setText("");
        emailField.setText("");
        scheduleField.setText("");
        feeField.setText("");
        selectedDoctorId = -1;
        doctorTable.clearSelection();
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Doctor> doctors = dbManager.getAllDoctors();
        for (Doctor doctor : doctors) {
            Object[] row = {
                doctor.getDoctorId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getPhone(),
                doctor.getEmail(),
                "$" + doctor.getConsultationFee()
            };
            tableModel.addRow(row);
        }
    }
}