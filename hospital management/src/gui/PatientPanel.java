package gui;

import database.DatabaseManager;
import models.Patient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientPanel extends JPanel {
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, ageField, phoneField, addressField;
    private JComboBox<String> genderCombo;
    private JTextArea medicalHistoryArea;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private DatabaseManager dbManager;
    private int selectedPatientId = -1;
    
    public PatientPanel() {
        dbManager = DatabaseManager.getInstance();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        refreshTable();
    }
    
    private void initializeComponents() {
        // Enhanced table setup
        String[] columnNames = {"ID", "Name", "Age", "Gender", "Phone", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        patientTable = new JTable(tableModel);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.setRowHeight(30);
        patientTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        patientTable.setGridColor(new Color(230, 230, 230));
        patientTable.setSelectionBackground(new Color(52, 152, 219, 50));
        patientTable.setSelectionForeground(new Color(52, 58, 64));
        
        // Style table header
        JTableHeader header = patientTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 35));
        
        // Alternate row colors
        patientTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        ageField = createStyledTextField(20);
        phoneField = createStyledTextField(20);
        addressField = createStyledTextField(20);
        
        genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        styleComboBox(genderCombo);
        
        medicalHistoryArea = new JTextArea(4, 20);
        medicalHistoryArea.setLineWrap(true);
        medicalHistoryArea.setWrapStyleWord(true);
        medicalHistoryArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        medicalHistoryArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            new EmptyBorder(8, 8, 8, 8)
        ));
        
        // Enhanced buttons
        addButton = createStyledButton("➕ Add Patient", new Color(40, 167, 69));
        updateButton = createStyledButton("✏️ Update Patient", new Color(255, 193, 7));
        deleteButton = createStyledButton("🗑️ Delete Patient", new Color(220, 53, 69));
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
        
        // Enhanced table panel with search
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
        
        // Title and search panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        
        JLabel titleLabel = new JLabel("👥 Patient Records");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 58, 64));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(248, 249, 250));
        
        JTextField searchField = createStyledTextField(15);
        searchField.setToolTipText("Search patients...");
        JButton searchButton = createStyledButton("🔍 Search", new Color(52, 152, 219));
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);
        
        // Table with enhanced styling
        JScrollPane tableScrollPane = new JScrollPane(patientTable);
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
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(),
            "📝 Patient Information",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            new Color(52, 58, 64)
        );
        formPanel.setBorder(BorderFactory.createCompoundBorder(titledBorder, new EmptyBorder(10, 10, 10, 10)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Create form in two columns
        // Left column
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(createLabel("👤 Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("🎂 Age:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(ageField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("⚧ Gender:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(genderCombo, gbc);
        
        // Right column
        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.insets = new Insets(8, 20, 8, 8);
        formPanel.add(createLabel("📞 Phone:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(phoneField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(createLabel("🏠 Address:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(addressField, gbc);
        
        // Medical history spans full width
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.insets = new Insets(8, 8, 8, 8);
        formPanel.add(createLabel("📋 Medical History:"), gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        formPanel.add(new JScrollPane(medicalHistoryArea), gbc);
        
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
        patientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = patientTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedPatientId = (Integer) tableModel.getValueAt(selectedRow, 0);
                    loadPatientData(selectedPatientId);
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    selectedPatientId = -1;
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });
        
        // Button listeners
        addButton.addActionListener(e -> addPatient());
        updateButton.addActionListener(e -> updatePatient());
        deleteButton.addActionListener(e -> deletePatient());
        clearButton.addActionListener(e -> clearForm());
    }
    
    private void addPatient() {
        if (validateForm()) {
            Patient patient = createPatientFromForm();
            dbManager.addPatient(patient);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Patient added successfully!");
        }
    }
    
    private void updatePatient() {
        if (selectedPatientId != -1 && validateForm()) {
            Patient patient = createPatientFromForm();
            patient.setPatientId(selectedPatientId);
            dbManager.updatePatient(patient);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Patient updated successfully!");
        }
    }
    
    private void deletePatient() {
        if (selectedPatientId != -1) {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this patient?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                dbManager.deletePatient(selectedPatientId);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Patient deleted successfully!");
            }
        }
    }
    
    private Patient createPatientFromForm() {
        Patient patient = new Patient();
        patient.setName(nameField.getText().trim());
        patient.setAge(Integer.parseInt(ageField.getText().trim()));
        patient.setGender((String) genderCombo.getSelectedItem());
        patient.setPhone(phoneField.getText().trim());
        patient.setAddress(addressField.getText().trim());
        patient.setMedicalHistory(medicalHistoryArea.getText().trim());
        return patient;
    }
    
    private void loadPatientData(int patientId) {
        Patient patient = dbManager.getPatientById(patientId);
        if (patient != null) {
            nameField.setText(patient.getName());
            ageField.setText(String.valueOf(patient.getAge()));
            genderCombo.setSelectedItem(patient.getGender());
            phoneField.setText(patient.getPhone());
            addressField.setText(patient.getAddress());
            medicalHistoryArea.setText(patient.getMedicalHistory());
        }
    }
    
    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter patient name.");
            return false;
        }
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            if (age <= 0 || age > 150) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age (1-150).");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age.");
            return false;
        }
        if (phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter phone number.");
            return false;
        }
        return true;
    }
    
    private void clearForm() {
        nameField.setText("");
        ageField.setText("");
        genderCombo.setSelectedIndex(0);
        phoneField.setText("");
        addressField.setText("");
        medicalHistoryArea.setText("");
        selectedPatientId = -1;
        patientTable.clearSelection();
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Patient> patients = dbManager.getAllPatients();
        for (Patient patient : patients) {
            Object[] row = {
                patient.getPatientId(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getPhone(),
                patient.getAddress()
            };
            tableModel.addRow(row);
        }
    }
}