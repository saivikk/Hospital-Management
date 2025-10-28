package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private PatientPanel patientPanel;
    private DoctorPanel doctorPanel;
    private AppointmentPanel appointmentPanel;
    
    public MainFrame() {
        initializeComponents();
        setupLayout();
        setupFrame();
    }
    
    private void initializeComponents() {
        // Create modern styled tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(248, 249, 250));
        tabbedPane.setForeground(new Color(52, 58, 64));
        
        patientPanel = new PatientPanel();
        doctorPanel = new DoctorPanel();
        appointmentPanel = new AppointmentPanel();
    }
    
    private void setupLayout() {
        // Create attractive header panel with gradient effect
        JPanel headerPanel = createHeaderPanel();
        
        // Style the tabbed pane with custom colors
        tabbedPane.addTab("👥 Patients", patientPanel);
        tabbedPane.addTab("👨‍⚕️ Doctors", doctorPanel);
        tabbedPane.addTab("📅 Appointments", appointmentPanel);
        
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create main content panel with padding
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(248, 249, 250));
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add components to frame
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(41, 128, 185),
                    0, getHeight(), new Color(52, 152, 219)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setLayout(new BorderLayout());
        
        // Hospital icon and title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        titlePanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel("🏥");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        
        JLabel titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);
        
        // Add current time/date panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setOpaque(false);
        
        JLabel dateLabel = new JLabel("Welcome to HMS");
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(new Color(236, 240, 241));
        dateLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
        
        infoPanel.add(dateLabel);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(infoPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(52, 58, 64));
        footerPanel.setPreferredSize(new Dimension(0, 30));
        
        JLabel footerLabel = new JLabel("© 2024 Hospital Management System - All Rights Reserved");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(173, 181, 189));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        footerPanel.add(footerLabel, BorderLayout.CENTER);
        
        return footerPanel;
    }
    
    private void setupFrame() {
        setTitle("Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 700));
        
        // Set modern look and feel
        try {
            // Use system look and feel
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            // Customize UI colors
            UIManager.put("TabbedPane.selected", new Color(52, 152, 219));
            UIManager.put("TabbedPane.contentAreaColor", new Color(248, 249, 250));
            
        } catch (Exception e) {
            // Fall back to default look and feel
            e.printStackTrace();
        }
        
        // Set application icon
        setIconImage(createAppIcon());
        
        getContentPane().setBackground(new Color(248, 249, 250));
    }
    
    private Image createAppIcon() {
        // Create a simple hospital icon
        BufferedImage icon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = icon.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw hospital cross
        g2d.setColor(new Color(52, 152, 219));
        g2d.fillRoundRect(2, 2, 28, 28, 8, 8);
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(12, 8, 8, 16);  // Vertical bar
        g2d.fillRect(8, 12, 16, 8);  // Horizontal bar
        
        g2d.dispose();
        return icon;
    }
    
    public void refreshAllPanels() {
        patientPanel.refreshTable();
        doctorPanel.refreshTable();
        appointmentPanel.refreshTable();
    }
}