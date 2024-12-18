package reception;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class ViewOrdersFrame{
    private JFrame viewOrdersFrame;
    private JButton backButton;
    private JButton logoutButton;
    private JComboBox<String> orderIdComboBox;
    private JTable ordersTable;
    private JLabel totalPriceLabel;
    private DefaultTableModel tableModel;

    public ViewOrdersFrame(){
        //main frame of the view orders setup
        viewOrdersFrame = new JFrame("View Orders");
        viewOrdersFrame.setSize(800, 500);
        viewOrdersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewOrdersFrame.setResizable(false);
        viewOrdersFrame.setLayout(new BorderLayout());

        //header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0x0054B4));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        //title label 
        JLabel titleLabel = new JLabel("View Orders");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        //buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(0x0054B4));
        backButton = createStyledButton("Back");
        logoutButton = createStyledButton("Log out");
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        //center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //order key find and select
        JPanel orderIdPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel orderIdLabel = new JLabel("Select Order Key");
        orderIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        orderIdComboBox = new JComboBox<>();
        orderIdComboBox.setPreferredSize(new Dimension(150, 25));
        orderIdPanel.add(orderIdLabel);
        orderIdPanel.add(orderIdComboBox);
        centerPanel.add(orderIdPanel, BorderLayout.NORTH);

        //table for orders
        String[] columnNames = {"Product ID", "Product Name", "Price", "Quantity", "Company"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ordersTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(ordersTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0x0054B4), 2));
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        //total price label
        totalPriceLabel = new JLabel("Total Price: P0.0");
        totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        centerPanel.add(totalPriceLabel, BorderLayout.SOUTH);

        //add panels to main frame of view orders
        viewOrdersFrame.add(headerPanel, BorderLayout.NORTH);
        viewOrdersFrame.add(centerPanel, BorderLayout.CENTER);

        //load order keys
        loadOrderKeys();

        //button actions 
        backButton.addActionListener(click -> goBack());
        orderIdComboBox.addActionListener(click -> loadOrderDetails());

        //make the main frame visible
        viewOrdersFrame.setVisible(true);
        
        //logout
        logoutButton.addActionListener(click -> logout());
    }
    
    //method for styling buttons
    private JButton createStyledButton(String text){
        JButton button = new JButton(text);
        button.setBackground(new Color(0x5085C1));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return button;
    }

    //method to go back
    private void goBack(){
        viewOrdersFrame.dispose();
        new ReceptionistDashboard();
    }

    //method to load order keys from the file
    private void loadOrderKeys(){
        try(BufferedReader reader = new BufferedReader(new FileReader("src/reception/orders.txt"))){
            String line;

            while((line = reader.readLine()) != null){
                if(line.trim().matches("\\d+")){  
                    orderIdComboBox.addItem(line.trim());
                }
            }
        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(viewOrdersFrame, "Error reading orders file.");
        }
    }

    //load order details base sa order key
    private void loadOrderDetails(){
        String selectedOrderKey = (String) orderIdComboBox.getSelectedItem();
        if (selectedOrderKey == null) return;

        tableModel.setRowCount(0);              //clear table
        double totalPrice = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader("src/reception/orders.txt"))){
            String line;
            boolean isOrderFound = false;

            while((line = reader.readLine()) != null){
                line = line.trim();

                //if key matches, read order details
                if(line.equals(selectedOrderKey)){
                    isOrderFound = true;
                    continue;
                }

                //stops at total price
                if(isOrderFound && line.matches("\\d+\\.\\d+")){
                    totalPrice = Double.parseDouble(line);
                    break;
                }

                //add rows to table with the updated columns
                if(isOrderFound && line.contains(",")){
                    String[] parts = line.split(",");

                    if(parts.length == 5){ 
                        tableModel.addRow(new Object[]{
                                parts[0], parts[1], parts[2], parts[3], parts[4]
                        });
                    }
                }
            }

            //update total price 
            totalPriceLabel.setText("Total Price: P" + totalPrice);

        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(viewOrdersFrame, "Error loading order details.");
        }
    }

    private void logout() {
        viewOrdersFrame.dispose(); // Close ReceptionistDashboard
        new login.Login().setVisible(true); // Open Login screen
    }
}
