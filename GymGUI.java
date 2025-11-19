import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * GymGUI - A class to implement graphical user interface for the gym management system
 * This GUI allows management of regular and premium gym members with various features
 * including adding members, tracking attendance, upgrading plans, and calculating discounts.
 * 
 * @author Saimon Oli
 * @version 1.1
 */
public class GymGUI extends JFrame {
    // ArrayList to store gym members
    private ArrayList<GymMember> members;
    
    // Text fields for entering member details
    private JTextField idField, nameField, locationField, phoneField, emailField;
    private JTextField dobField, startDateField, referralField, paidAmountField;
    private JTextField removalReasonField, trainerNameField;
    
    // Non-editable text fields for displaying calculated values
    private JTextField regularPlanPriceField, premiumChargeField, discountAmountField;
    
    // Radio buttons for gender selection
    private JRadioButton maleButton, femaleButton;
    
    // Combo box for plan selection
    private JComboBox<String> planComboBox;
    
    // Text area for displaying output
    private JTextArea outputArea;
    
    // Date format for validation
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    
    /**
     * Constructor for objects of class GymGUI
     * Sets up the user interface components and initializes data structures
     */
    public GymGUI() {
        // Initialize the ArrayList to store gym members
        members = new ArrayList<>();
        
        // Set up the frame
        setTitle("Gym Management System");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true); 
        
        // Main content panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Input panel for member details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 0, 0));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add input fields for member details with appropriate labels
        inputPanel.add(new JLabel("Member ID:"));
        idField = new JTextField(10);
        inputPanel.add(idField);
        
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Location:"));
        locationField = new JTextField(20);
        inputPanel.add(locationField);
        
        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField(15);
        inputPanel.add(phoneField);
        
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        inputPanel.add(emailField);
        
        // Gender selection with radio buttons
        inputPanel.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        inputPanel.add(genderPanel);
        inputPanel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        dobField = new JTextField(10);
        inputPanel.add(dobField);
        
        inputPanel.add(new JLabel("Membership Start Date (yyyy-MM-dd):"));
        startDateField = new JTextField(10);
        inputPanel.add(startDateField);
        
        inputPanel.add(new JLabel("Referral Source:"));
        referralField = new JTextField(20);
        inputPanel.add(referralField);
        
        // Plan selection with combo box
        inputPanel.add(new JLabel("Plan (for Regular Members):"));
        planComboBox = new JComboBox<>(new String[]{"basic", "standard", "Premium"});
        inputPanel.add(planComboBox);
        inputPanel.add(new JLabel("Paid Amount (for Premium Members):"));
        paidAmountField = new JTextField(10);
        inputPanel.add(paidAmountField);
        
        inputPanel.add(new JLabel("Removal Reason:"));
        removalReasonField = new JTextField(20);
        inputPanel.add(removalReasonField);
        
        inputPanel.add(new JLabel("Trainer's Name (for Premium Members):"));
        trainerNameField = new JTextField(20);
        inputPanel.add(trainerNameField);
        
        // Non-editable fields for calculated values
        inputPanel.add(new JLabel("Regular Plan Price:"));
        regularPlanPriceField = new JTextField(10);
        regularPlanPriceField.setEditable(false);
        regularPlanPriceField.setText("6500.0");
        inputPanel.add(regularPlanPriceField);
        
        inputPanel.add(new JLabel("Premium Plan Charge:"));
        premiumChargeField = new JTextField(10);
        premiumChargeField.setEditable(false);
        premiumChargeField.setText("25000.0");
        inputPanel.add(premiumChargeField);
        
        inputPanel.add(new JLabel("Discount Amount:"));
        discountAmountField = new JTextField(10);
        discountAmountField.setEditable(false);
        inputPanel.add(discountAmountField);
        
        // Add input panel to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        
        // Output area for displaying results
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel for actions
        JPanel buttonPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add buttons for different actions
        JButton addRegularButton = new JButton("Add Regular Member");
        JButton addPremiumButton = new JButton("Add Premium Member");
        JButton activateButton = new JButton("Activate Membership");
        JButton deactivateButton = new JButton("Deactivate Membership");
        JButton markAttendanceButton = new JButton("Mark Attendance");
        JButton upgradePlanButton = new JButton("Upgrade Plan");
        JButton calculateDiscountButton = new JButton("Calculate Discount");
        JButton revertRegularButton = new JButton("Revert Regular Member");
        JButton revertPremiumButton = new JButton("Revert Premium Member");
        JButton payDueButton = new JButton("Pay Due Amount");
        JButton displayButton = new JButton("Display");
        JButton clearButton = new JButton("Clear");
        JButton saveButton = new JButton("Save to File");
        JButton readButton = new JButton("Read from File");
        
        // Add action listeners for buttons
        addRegularButton.addActionListener(e -> addRegularMember());
        addPremiumButton.addActionListener(e -> addPremiumMember());
        activateButton.addActionListener(e -> activateMembership());
        deactivateButton.addActionListener(e -> deactivateMembership());
        markAttendanceButton.addActionListener(e -> markAttendance());
        upgradePlanButton.addActionListener(e -> upgradePlan());
        calculateDiscountButton.addActionListener(e -> calculateDiscount());
        revertRegularButton.addActionListener(e -> revertRegularMember());
        revertPremiumButton.addActionListener(e -> revertPremiumMember());
        payDueButton.addActionListener(e -> payDueAmount());
        displayButton.addActionListener(e -> displayMembers());
        clearButton.addActionListener(e -> clearFields());
        saveButton.addActionListener(e -> saveToFile());
        readButton.addActionListener(e -> readFromFile());
        
        // Add buttons to button panel
        buttonPanel.add(addRegularButton);
        buttonPanel.add(addPremiumButton);
        buttonPanel.add(activateButton);
        buttonPanel.add(deactivateButton);
        buttonPanel.add(markAttendanceButton);
        buttonPanel.add(upgradePlanButton);
        buttonPanel.add(calculateDiscountButton);
        buttonPanel.add(revertRegularButton);
        buttonPanel.add(revertPremiumButton);
        buttonPanel.add(payDueButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(readButton);
        
        // Add button panel to main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Make the frame visible
        setVisible(true);
        
        // Initialize plan price based on default selection
        updatePlanPrice();
        
        // Add item listener to update plan price when selection changes
        planComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updatePlanPrice();
            }
        });
    }
    
    /**
     * Updates the regular plan price field based on selected plan
     */
    private void updatePlanPrice() {
        String selectedPlan = (String) planComboBox.getSelectedItem();
        try {
            // Create a temporary RegularMember to get the plan price
            RegularMember tempMember = new RegularMember(0, "", "", "", "", "", "", "", "");
            double price = tempMember.getPlanPrice(selectedPlan);
            regularPlanPriceField.setText(String.valueOf(price));
        } catch (Exception ex) {
            showError("Error getting plan price: " + ex.getMessage());
        }
    }
    
    /**
     * Validates and adds a new regular member to the system
     */
    private void addRegularMember() {
       try {
            // Validate input fields
            int id = validateId();
            validateRequiredFields();
            validateDateFormat(dobField.getText(), "Date of Birth");
            validateDateFormat(startDateField.getText(), "Membership Start Date");
            
            // Get the selected gender
            String gender = getSelectedGender();
            
            // Create and add a new RegularMember
            RegularMember member = new RegularMember(
                id,
                nameField.getText(),
                locationField.getText(),
                phoneField.getText(),
                emailField.getText(),
                gender,
                dobField.getText(),
                startDateField.getText(),
                referralField.getText()
            );
            
            // Set the plan
            String selectedPlan = (String) planComboBox.getSelectedItem();
            member.upgradePlan(selectedPlan);
            
            // Add the member to the list
            members.add(member);
            
            // Show success message
            showMessage("Regular member added successfully!");
            outputArea.setText("Regular member added successfully!\n");
            member.display();
            
            // Clear the input fields
            clearFields();
        } catch (Exception ex) {
            showError("Error adding Regular Member: " + ex.getMessage());
        }
    }
    
    /**
     * Validates and adds a new premium member to the system
     */
    private void addPremiumMember() {
        try {
            // Validate input fields
            int id = validateId();
            validateRequiredFields();
            validateDateFormat(dobField.getText(), "Date of Birth");
            validateDateFormat(startDateField.getText(), "Membership Start Date");
            
            // Validate trainer name
            if (trainerNameField.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Trainer name is required for Premium Members");
            }
            
            // Get the selected gender
            String gender = getSelectedGender();
            
            // Create and add a new PremiumMember
            PremiumMember member = new PremiumMember(
                id,
                nameField.getText(),
                locationField.getText(),
                phoneField.getText(),
                emailField.getText(),
                gender,
                dobField.getText(),
                startDateField.getText(),
                trainerNameField.getText()
            );
            
            // Process initial payment if provided
            if (!paidAmountField.getText().trim().isEmpty()) {
                try {
                    double paidAmount = Double.parseDouble(paidAmountField.getText().trim());
                    String paymentResult = member.payDueAmount(paidAmount);
                    showMessage(paymentResult);
                    outputArea.setText(paymentResult + "\n");
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Invalid paid amount format");
                }
            }
            
            // Add the member to the list
            members.add(member);
            
            // Show success message
            showMessage("Premium member added successfully!");
            outputArea.append("Premium member added successfully!\n");
            member.display();
            
            // Clear the input fields
            clearFields();
        } catch (Exception ex) {
            showError("Error adding Premium Member: " + ex.getMessage());
        }
    }
    
    /**
     * Activates the membership for a member with the given ID
     */
    private void activateMembership() {
        try {
            // Get the member with the given ID
            int id = validateId();
            GymMember member = findMemberById(id);
            
            // Activate the membership
            member.activateMembership();
            
            // Show success message
            showMessage("Membership activated successfully for member with ID: " + id);
            outputArea.setText("Membership activated successfully for member with ID: " + id + "\n");
            member.display();
        } catch (Exception ex) {
            showError("Error activating membership: " + ex.getMessage());
        }
    }
    
    /**
     * Deactivates the membership for a member with the given ID
     */
    private void deactivateMembership() {
        try {
            // Get the member with the given ID
            int id = validateId();
            GymMember member = findMemberById(id);
            
            // Deactivate the membership
            member.deactivateMembership();
            
            // Show success message
            showMessage("Membership deactivated successfully for member with ID: " + id);
            outputArea.setText("Membership deactivated successfully for member with ID: " + id + "\n");
            member.display();
        } catch (Exception ex) {
            showError("Error deactivating membership: " + ex.getMessage());
        }
    }
    
    /**
     * Marks attendance for a member with the given ID
     */
    private void markAttendance() {
        try {
            // Get the member with the given ID
            int id = validateId();
            GymMember member = findMemberById(id);
            
            // Check if membership is active
            if (!member.isActiveStatus()) {
                showError("Cannot mark attendance for inactive membership");
                throw new IllegalStateException("Cannot mark attendance for inactive membership");
            }
            
            // Mark attendance
            member.markAttendance();
            
            // Show success message
            showMessage("Attendance marked successfully for member with ID: " + id);
            outputArea.setText("Attendance marked successfully for member with ID: " + id + "\n");
            outputArea.append("Current attendance: " + member.getAttendance() + "\n");
            outputArea.append("Loyalty points: " + member.getLoyaltyPoints() + "\n");
            
            // Show upgrade eligibility for RegularMember
            if (member instanceof RegularMember) {
                RegularMember regularMember = (RegularMember) member;
                outputArea.append("Eligible for upgrade: " + regularMember.isEligibleForUpgrade() + "\n");
            }
        } catch (Exception ex) {
            showError("Error marking attendance: " + ex.getMessage());
        }
    }
    
    /**
     * Upgrades the plan for a regular member with the given ID
     */
    private void upgradePlan() {
        try {
            // Get the member with the given ID
            int id = validateId();
            GymMember member = findMemberById(id);
            
            // Check if member is a RegularMember
            if (!(member instanceof RegularMember)) {
                showError("Member with ID " + id + " is not a Regular Member");
                throw new IllegalArgumentException("Member with ID " + id + " is not a Regular Member");
            }
            
            RegularMember regularMember = (RegularMember) member;
            
            // Get the selected plan
            String newPlan = (String) planComboBox.getSelectedItem();
            
            // Upgrade the plan
            String result = regularMember.upgradePlan(newPlan);
            
            // Show result
            showMessage(result);
            outputArea.setText(result + "\n");
            regularMember.display();
        } catch (Exception ex) {
            showError("Error upgrading plan: " + ex.getMessage());
        }
    }
    
    /**
     * Calculates discount for a premium member with the given ID
     */
    private void calculateDiscount() {
        try {
            // Get the member with the given ID
            int id = validateId();
            GymMember member = findMemberById(id);
            
            // Check if member is a PremiumMember
            if (!(member instanceof PremiumMember)) {
                showError("Member with ID " + id + " is not a Premium Member");
                throw new IllegalArgumentException("Member with ID " + id + " is not a Premium Member");
            }
            
            PremiumMember premiumMember = (PremiumMember) member;
            
            // Calculate discount
            premiumMember.calculateDiscount();
            
            // Update discount amount field
            discountAmountField.setText(String.valueOf(premiumMember.getDiscountAmount()));
            
            // Show result
            showMessage("Discount calculated for member with ID: " + id);
            outputArea.setText("Discount calculated for member with ID: " + id + "\n");
            outputArea.append("Discount amount: " + premiumMember.getDiscountAmount() + "\n");
            premiumMember.display();
        } catch (Exception ex) {
            showError("Error calculating discount: " + ex.getMessage());
        }
    }
    
    /**
     * Reverts a regular member with the given ID
     */
    private void revertRegularMember() {
        try {
            // Get the member with the given ID
            int id = validateId();
            GymMember member = findMemberById(id);
            
            // Check if member is a RegularMember
            if (!(member instanceof RegularMember)) {
                showError("Member with ID " + id + " is not a Regular Member");
                throw new IllegalArgumentException("Member with ID " + id + " is not a Regular Member");
            }
            
            RegularMember regularMember = (RegularMember) member;
            
            // Get removal reason
            String reason = removalReasonField.getText().trim();
            if (reason.isEmpty()) {
                showError("Removal reason is required");
                throw new IllegalArgumentException("Removal reason is required");
            }
            
            // Revert the member
            regularMember.revertRegularMember(reason);
            
            // Show result
            showMessage("Regular member reverted successfully for ID: " + id);
            outputArea.setText("Regular member reverted successfully for ID: " + id + "\n");
            regularMember.display();
        } catch (Exception ex) {
            showError("Error reverting Regular Member: " + ex.getMessage());
        }
    }
    
    /**
     * Reverts a premium member with the given ID
     */
    private void revertPremiumMember() {
        try {
            // Get the member with the given ID
            int id = validateId();
            GymMember member = findMemberById(id);
            
            // Check if member is a PremiumMember
            if (!(member instanceof PremiumMember)) {
                showError("Member with ID " + id + " is not a Premium Member");
                throw new IllegalArgumentException("Member with ID " + id + " is not a Premium Member");
            }
            
            PremiumMember premiumMember = (PremiumMember) member;
            
            // Revert the member
            premiumMember.revertPremiumMember();
            
            // Show result
            showMessage("Premium member reverted successfully for ID: " + id);
            outputArea.setText("Premium member reverted successfully for ID: " + id + "\n");
            premiumMember.display();
        } catch (Exception ex) {
            showError("Error reverting Premium Member: " + ex.getMessage());
        }
    }
    
    /**
     * Processes payment for a premium member with the given ID
     */
    private void payDueAmount() {
        try {
            // Get the member with the given ID
            int id = validateId();
            GymMember member = findMemberById(id);
            
            // Check if member is a PremiumMember
            if (!(member instanceof PremiumMember)) {
                showError("Member with ID " + id + " is not a Premium Member");
                throw new IllegalArgumentException("Member with ID " + id + " is not a Premium Member");
            }
            
            PremiumMember premiumMember = (PremiumMember) member;
            
            // Get payment amount
            if (paidAmountField.getText().trim().isEmpty()) {
                showError("Payment amount is required");
                throw new IllegalArgumentException("Payment amount is required");
            }
            
            double amount;
            try {
                amount = Double.parseDouble(paidAmountField.getText().trim());
                if (amount <= 0) {
                    showError("Payment amount must be positive");
                    throw new IllegalArgumentException("Payment amount must be positive");
                }
            } catch (NumberFormatException ex) {
                showError("Invalid payment amount format");
                throw new IllegalArgumentException("Invalid payment amount format");
            }
            
            // Process payment
            String result = premiumMember.payDueAmount(amount);
            
            // Show result
            showMessage(result);
            outputArea.setText(result + "\n");
            premiumMember.display();
        } catch (Exception ex) {
            showError("Error processing payment: " + ex.getMessage());
        }
    }
    
    /**
     * Displays all members in the system
     */
    private void displayMembers() {
        try {
            if (members.isEmpty()) {
                outputArea.setText("No members found in the system.\n");
                return;
            }
            
            // Display all members
            outputArea.setText("All Members in the System:\n");
            outputArea.append("----------------------\n");
            
            int regularCount = 0;
            int premiumCount = 0;
            int activeCount = 0;
            
            for (GymMember member : members) {
                if (member instanceof RegularMember) {
                    regularCount++;
                } else if (member instanceof PremiumMember) {
                    premiumCount++;
                }
                
                if (member.isActiveStatus()) {
                    activeCount++;
                }
                
                outputArea.append("\nMember ID: " + member.getId() + "\n");
                outputArea.append("Name: " + member.getName() + "\n");
                outputArea.append("Type: " + (member instanceof RegularMember ? "Regular" : "Premium") + "\n");
                outputArea.append("Active: " + member.isActiveStatus() + "\n");
                outputArea.append("----------------------\n");
            }
            
            // Display summary
            outputArea.append("\nSummary:\n");
            outputArea.append("Total Members: " + members.size() + "\n");
            outputArea.append("Regular Members: " + regularCount + "\n");
            outputArea.append("Premium Members: " + premiumCount + "\n");
            outputArea.append("Active Members: " + activeCount + "\n");
        } catch (Exception ex) {
            outputArea.setText("Error displaying members: " + ex.getMessage());
        }
    }
    
    /**
     * Clears all input fields
     */
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        phoneField.setText("");
        emailField.setText("");
        dobField.setText("");
        startDateField.setText("");
        referralField.setText("");
        paidAmountField.setText("");
        removalReasonField.setText("");
        trainerNameField.setText("");
        
        // Clear gender selection
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.clearSelection();
        
        // Reset plan combo box
        planComboBox.setSelectedIndex(0);
        
        // Clear discount amount field
        discountAmountField.setText("");
    }
    
    /**
     * Saves all members to a file
     */
    private void saveToFile() {
        try {
            // Check if there are any members
            if (members.isEmpty()) {
                showMessage("No members to save.");
                return;
            }
            
            // Choose file to save
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Members");
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                // Add .ser extension if not present
                if (!file.getName().toLowerCase().endsWith(".ser")) {
                    file = new File(file.getAbsolutePath() + ".ser");
                }
                
                // Save members to file
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(members);
                    showMessage("Members saved to file: " + file.getAbsolutePath());
                }
            }
        } catch (IOException ex) {
            showError("Error saving to file: " + ex.getMessage());
        }
    }
    
    /**
     * Reads members from a file
     */
    @SuppressWarnings("unchecked")
    private void readFromFile() {
        try {
            // Choose file to read
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load Members");
            
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                // Read members from file
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    members = (ArrayList<GymMember>) ois.readObject();
                    showMessage("Members loaded from file: " + file.getAbsolutePath());
                    displayMembers();
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            showError("Error reading from file: " + ex.getMessage());
        }
    }
    
    /**
     * Finds a member by ID
     * 
     * @param id The ID to search for
     * @return The member with the given ID
     * @throws IllegalArgumentException If no member with the given ID is found
     */
    private GymMember findMemberById(int id) throws IllegalArgumentException {
        for (GymMember member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        throw new IllegalArgumentException("No member found with ID: " + id);
    }
    
    /**
     * Validates the ID field
     * 
     * @return The validated ID
     * @throws IllegalArgumentException If the ID field is empty or not a valid integer
     */
    private int validateId() throws IllegalArgumentException {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            showError("Member ID is required");
            throw new IllegalArgumentException("Member ID is required");
        }
        
        try {
            return Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            showError("Member ID must be a valid integer");
            throw new IllegalArgumentException("Member ID must be a valid integer");
        }
    }
    
    /**
     * Validates required fields
     * 
     * @throws IllegalArgumentException If any required field is empty
     */
    private void validateRequiredFields() throws IllegalArgumentException {
        if (nameField.getText().trim().isEmpty()) {
            showError("Name is required");
            throw new IllegalArgumentException("Name is required");
        }
        if (locationField.getText().trim().isEmpty()) {
            showError("Location is required");
            throw new IllegalArgumentException("Location is required");
        }
        if (phoneField.getText().trim().isEmpty()) {
            showError("Phone is required");
            throw new IllegalArgumentException("Phone is required");
        }
        if (emailField.getText().trim().isEmpty()) {
            showError("Email is required");
            throw new IllegalArgumentException("Email is required");
        }
        if (dobField.getText().trim().isEmpty()) {
            showError("Date of Birth is required");
            throw new IllegalArgumentException("Date of Birth is required");
        }
        if (startDateField.getText().trim().isEmpty()) {
            showError("Membership Start Date is required");
            throw new IllegalArgumentException("Membership Start Date is required");
        }
        if (!maleButton.isSelected() && !femaleButton.isSelected()) {
            showError("Gender selection is required");
            throw new IllegalArgumentException("Gender selection is required");
        }
    }
    
    /**
     * Gets the selected gender
     * 
     * @return The selected gender
     * @throws IllegalArgumentException If no gender is selected
     */
    private String getSelectedGender() throws IllegalArgumentException {
        if (maleButton.isSelected()) {
            return "male";
        } else if (femaleButton.isSelected()) {
            return "female";
        } else {
            showError("No gender selected");
            throw new IllegalArgumentException("No gender selected");
        }
    }
    
    /**
     * Validates date format
     * 
     * @param date The date string to validate
     * @param fieldName The name of the field for error messages
     * @throws IllegalArgumentException If the date format is invalid
     */
    private void validateDateFormat(String date, String fieldName) throws IllegalArgumentException {
        if (date.trim().isEmpty()) {
            showError(fieldName + " is required");
            throw new IllegalArgumentException(fieldName + " is required");
        }
        
        try {
            // Set lenient to false to enforce strict date validation
            dateFormat.setLenient(false);
            Date parsedDate = dateFormat.parse(date);
            
            // Check if the parsed date is in the future for DOB
            if (fieldName.equals("Date of Birth")) {
                Date today = new Date();
                if (parsedDate.after(today)) {
                    showError("Date of Birth cannot be in the future");
                    throw new IllegalArgumentException("Date of Birth cannot be in the future");
                }
            }
        } catch (ParseException ex) {
            // Only show error in outputArea, not in dialog, to avoid double message
            outputArea.setText("Error: " + fieldName + " must be in format yyyy-MM-dd");
            throw new IllegalArgumentException(fieldName + " must be in format yyyy-MM-dd");
        }
    }
    
    /**
     * Shows an error message
     * 
     * @param message The error message to display
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        outputArea.setText("Error: " + message);
    }
    
    /**
     * Shows an information message
     * 
     * @param message The message to display
     */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
        outputArea.setText(message);
    }
    
    /**
     * The main method to start the application
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Use invokeLater to ensure thread safety with Swing components
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set system look and feel for better UI appearance
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    // If setting look and feel fails, continue with default
                    System.err.println("Error setting look and feel: " + e.getMessage());
                }
                
                // Create and show the GUI
                new GymGUI();
            }
        });
    }
}