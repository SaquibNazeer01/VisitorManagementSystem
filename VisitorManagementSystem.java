import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.UUID;

public class VisitorManagementSystem extends JFrame {

    private JTextField nameField, emailField, phoneField, ageField, idCardNumberField, roomNumberField, searchField;
    private JComboBox<String> idCardTypeComboBox;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addVisitorButton, clearButton, searchButton, deleteButton, checkInButton, checkOutButton;
    private JSpinner checkInSpinner, checkOutSpinner;

    public VisitorManagementSystem() {
        setTitle("Visitor Management System");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255));
        getContentPane().add(mainPanel);

        // Header
        JLabel header = new JLabel("Visitor Management System", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(new Color(70, 130, 180));
        mainPanel.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(10, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        // Fields and labels
        addFormRow(formPanel, "Name:", nameField = createStyledTextField());
        addFormRow(formPanel, "Age:", ageField = createStyledTextField());
        addFormRow(formPanel, "Email:", emailField = createStyledTextField());
        addFormRow(formPanel, "Phone:", phoneField = createStyledTextField());

        // ID Card Type dropdown
        JLabel idCardTypeLabel = new JLabel("ID Card Type:");
        idCardTypeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        String[] idCardTypes = { "Student ID Card", "Aadhaar", "Driving License", "PAN Card", "Other" };
        idCardTypeComboBox = new JComboBox<>(idCardTypes);
        idCardTypeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(idCardTypeLabel);
        formPanel.add(idCardTypeComboBox);

        addFormRow(formPanel, "ID Card Number:", idCardNumberField = createStyledTextField());
        addFormRow(formPanel, "Room Number:", roomNumberField = createStyledTextField());

        // Check-in
        JLabel checkInLabel = new JLabel("Check-In:");
        checkInLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        SpinnerDateModel checkInModel = new SpinnerDateModel();
        checkInSpinner = new JSpinner(checkInModel);
        checkInSpinner.setEditor(new JSpinner.DateEditor(checkInSpinner, "yyyy-MM-dd HH:mm"));
        styleSpinner(checkInSpinner);
        formPanel.add(checkInLabel);
        formPanel.add(checkInSpinner);

        // Check-out
        JLabel checkOutLabel = new JLabel("Check-Out:");
        checkOutLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        SpinnerDateModel checkOutModel = new SpinnerDateModel();
        checkOutSpinner = new JSpinner(checkOutModel);
        checkOutSpinner.setEditor(new JSpinner.DateEditor(checkOutSpinner, "yyyy-MM-dd HH:mm"));
        styleSpinner(checkOutSpinner);
        formPanel.add(checkOutLabel);
        formPanel.add(checkOutSpinner);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        searchField = new JTextField(15);
        styleTextField(searchField);
        searchButton = createButton("Search", new Color(33, 150, 243), e -> searchVisitor());
        deleteButton = createButton("Delete", new Color(255, 69, 0), e -> deleteVisitor());
        clearButton = createButton("Clear", new Color(255, 193, 7), e -> clearForm());
        addVisitorButton = createButton("Add Visitor", new Color(0, 150, 136), e -> addVisitor());
        checkInButton = createButton("Check In", new Color(70, 130, 180), e -> checkIn());
        checkOutButton = createButton("Check Out", new Color(70, 130, 180), e -> checkOut());

        buttonPanel.add(new JLabel("Search by ID/Name/Token:"));
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(addVisitorButton);
        buttonPanel.add(checkInButton);
        buttonPanel.add(checkOutButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Table panel
        String[] columns = { "Token No.", "Name", "Age", "Email", "Phone", "ID Card Type", "ID Card Number", "Room No", "Check-In", "Check-Out" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Set visible
        setVisible(true);
    }

    private void addFormRow(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label);
        panel.add(textField);
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(10);
        styleTextField(textField);
        return textField;
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(120, 25));
        textField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinner.setPreferredSize(new Dimension(150, 25));
    }

    private JButton createButton(String text, Color color, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.addActionListener(actionListener);
        return button;
    }

    private void addVisitor() {
        String tokenNumber = UUID.randomUUID().toString().substring(0, 8); // Generate unique token
        String name = nameField.getText();
        String age = ageField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String idCardType = (String) idCardTypeComboBox.getSelectedItem();
        String idCardNumber = idCardNumberField.getText();
        String roomNumber = roomNumberField.getText();
        String checkIn = checkInSpinner.getValue().toString();
        String checkOut = checkOutSpinner.getValue().toString();

        if (!name.isEmpty() && !age.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !idCardNumber.isEmpty()) {
            tableModel.addRow(new Object[] { tokenNumber, name, age, email, phone, idCardType, idCardNumber, roomNumber, checkIn, checkOut });
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        nameField.setText("");
        ageField.setText("");
        emailField.setText("");
        phoneField.setText("");
        idCardNumberField.setText("");
        roomNumberField.setText("");
        checkInSpinner.setValue(new java.util.Date());
        checkOutSpinner.setValue(new java.util.Date());
        searchField.setText("");
    }

    private void searchVisitor() {
        String searchQuery = searchField.getText().trim().toLowerCase();

        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search query.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            String tokenNumber = tableModel.getValueAt(row, 0).toString().toLowerCase();
            String name = tableModel.getValueAt(row, 1).toString().toLowerCase();

            if (tokenNumber.contains(searchQuery) || name.contains(searchQuery)) {
                table.setRowSelectionInterval(row, row);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "No visitor found with the given query.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteVisitor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a visitor to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkIn() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Update the Check-In column with current date/time
            tableModel.setValueAt(new java.util.Date().toString(), selectedRow, 8);
            JOptionPane.showMessageDialog(this, "Visitor Checked In!", "Check-In", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a visitor to check in.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkOut() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Update the Check-Out column with current date/time
            tableModel.setValueAt(new java.util.Date().toString(), selectedRow, 9);
            JOptionPane.showMessageDialog(this, "Visitor Checked Out!", "Check-Out", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a visitor to check out.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new VisitorManagementSystem();
    }
}
