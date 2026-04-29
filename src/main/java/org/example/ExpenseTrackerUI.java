package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseTrackerUI extends JFrame {
    private ExpenseTracker tracker;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    public ExpenseTrackerUI() {
        tracker = new ExpenseTracker();
        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Description", "Amount"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel with better layout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        JTextField descField = new JTextField(15);
        inputPanel.add(descField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        JTextField amtField = new JTextField(7);
        inputPanel.add(amtField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton addButton = new JButton("Add Expense");
        inputPanel.add(addButton, gbc);
        add(inputPanel, BorderLayout.NORTH);

        // Total panel
        JPanel totalPanel = new JPanel();
        totalLabel = new JLabel("Total: R$0.00");
        totalPanel.add(totalLabel);
        add(totalPanel, BorderLayout.SOUTH);

        // Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String desc = descField.getText().trim();
                String amtText = amtField.getText().trim();
                if (desc.isEmpty() || amtText.isEmpty()) {
                    JOptionPane.showMessageDialog(ExpenseTrackerUI.this, "Please enter description and amount.");
                    return;
                }
                try {
                    double amt = Double.parseDouble(amtText);
                    tracker.addExpense(desc, amt);
                    tableModel.addRow(new Object[]{desc, String.format("R$%.2f", amt)});
                    updateTotal();
                    descField.setText("");
                    amtField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ExpenseTrackerUI.this, "Invalid amount.");
                }
            }
        });
    }

    private void updateTotal() {
        totalLabel.setText(String.format("Total: R$%.2f", tracker.getTotal()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExpenseTrackerUI().setVisible(true);
        });
    }
}
