import java.awt.*;
import javax.swing.*;

public class FileImportUI extends JFrame {
    private String studentFilePath;
    private String staffFilePath;
    private RegistrationController controller;
    
    private JTextField studentFileField;
    private JTextField staffFileField;
    private JButton browseStudentButton;
    private JButton browseStaffButton;
    private JButton importStudentButton;
    private JButton importStaffButton;
    private JTextArea statusArea;
    
    public FileImportUI() {
        this.controller = new RegistrationController();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        setTitle("File Import System - Students & Staff");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        studentFileField = new JTextField(30);
        staffFileField = new JTextField(30);
        studentFileField.setEditable(false);
        staffFileField.setEditable(false);
        
        browseStudentButton = new JButton("Browse...");
        browseStaffButton = new JButton("Browse...");
        importStudentButton = new JButton("Import Students");
        importStaffButton = new JButton("Import Staff");
        
        statusArea = new JTextArea(8, 50);
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("User Registration File Import");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Student File:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(studentFileField, gbc);
        gbc.gridx = 2;
        mainPanel.add(browseStudentButton, gbc);
        gbc.gridx = 3;
        mainPanel.add(importStudentButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Staff File:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(staffFileField, gbc);
        gbc.gridx = 2;
        mainPanel.add(browseStaffButton, gbc);
        gbc.gridx = 3;
        mainPanel.add(importStaffButton, gbc);
        
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));
        statusPanel.add(new JScrollPane(statusArea), BorderLayout.CENTER);
        
        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }
    
    private void setupEventHandlers() {
        browseStudentButton.addActionListener(e -> {
            String path = selectFile();
            if (path != null) {
                studentFilePath = path;
                studentFileField.setText(path);
                statusArea.append("Student file selected: " + path + "\n");
            }
        });
        
        browseStaffButton.addActionListener(e -> {
            String path = selectFile();
            if (path != null) {
                staffFilePath = path;
                staffFileField.setText(path);
                statusArea.append("Staff file selected: " + path + "\n");
            }
        });
        
        importStudentButton.addActionListener(e -> inputStudents(studentFilePath));
        importStaffButton.addActionListener(e -> inputStaff(staffFilePath));
    }
    
    private String selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select CSV File");
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    
    public void inputStudents(String path) {
        if (path == null || path.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please select a student file first", 
                "No File Selected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        statusArea.append("\n=== Importing Students ===\n");
        
        try {
            controller.importStudents(path);
            statusArea.append("Students imported successfully!\n");
            JOptionPane.showMessageDialog(this, 
                "Students imported successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            statusArea.append("ERROR: " + e.getMessage() + "\n");
            JOptionPane.showMessageDialog(this, 
                "Failed to import students: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void inputStaff(String path) {
        if (path == null || path.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please select a staff file first", 
                "No File Selected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        statusArea.append("\n=== Importing Staff ===\n");
        
        try {
            controller.importStaff(path);
            statusArea.append("Staff imported successfully!\n");
            JOptionPane.showMessageDialog(this, 
                "Staff imported successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            statusArea.append("ERROR: " + e.getMessage() + "\n");
            JOptionPane.showMessageDialog(this, 
                "Failed to import staff: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileImportUI ui = new FileImportUI();
            ui.setVisible(true);
        });
    }
}