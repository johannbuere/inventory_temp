
package form;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.ImageIcon;
import model.Card_Model;


public class Sales_Form extends javax.swing.JPanel {


    public Sales_Form() {
        initComponents();
        updateCards();
    }

    private void updateCards() {
        try {
            HashMap<String, ProductData> inventory = loadInventory("src/reception/inventory.txt");
            OrderSummary summary = processOrders("src/reception/orders.txt", inventory);

            // Card 1: Top-Selling Product
            card1.setData(new Card_Model(
                new ImageIcon(getClass().getResource("/images/stock.png")),
                "Top-Selling Product",
                summary.topProductName,
                "Sold " + summary.topProductIncrease + " More"
            ));

            // Card 2: Total Revenue
            card2.setData(new Card_Model(
                new ImageIcon(getClass().getResource("/images/profit.png")),
                "Total Revenue",
                "$" + String.format("%.2f", summary.totalRevenue),
                "Earned Today"
            ));

            // Card 3: Amount Sold
            card3.setData(new Card_Model(
                new ImageIcon(getClass().getResource("/images/sold.png")),
                "Amount Sold",
                summary.totalItemsSold + " Products",
                "Products Sold Today"
            ));

            // Card 4: Customers Served
            card4.setData(new Card_Model(
                new ImageIcon(getClass().getResource("/images/customer.png")),
                "Customers Served",
                String.valueOf(summary.customersServed),
                "Within the Day"
            ));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private HashMap<String, ProductData> loadInventory(String fileName) throws IOException {
        HashMap<String, ProductData> inventory = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String productId = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    double ourPrice = Double.parseDouble(parts[3]);
                    inventory.put(productId, new ProductData(productId, name, price, ourPrice));
                }
            }
        }
        return inventory;
    }

    private OrderSummary processOrders(String fileName, HashMap<String, ProductData> inventory) throws IOException {
        OrderSummary summary = new OrderSummary();
        HashMap<String, Integer> productQuantities = new HashMap<>(); // To track total quantities sold per product

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.matches("\\d+")) { // If it's an OrderKey
                    summary.customersServed++;
                } else if (line.contains(",")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        String productId = parts[0];
                        String productName = parts[1];
                        double ourPrice = Double.parseDouble(parts[2]);
                        int quantity = Integer.parseInt(parts[3]);

                        ProductData product = inventory.get(productId);
                        if (product != null) {
                            double profit = (product.price - ourPrice) * quantity;
                            summary.totalRevenue += profit;
                            summary.totalItemsSold += quantity;

                            // Update product quantities
                            productQuantities.put(productId, productQuantities.getOrDefault(productId, 0) + quantity);

                            // Determine the top-selling product
                            int currentProductQuantity = productQuantities.get(productId);
                            if (currentProductQuantity > summary.topProductQuantity) {
                                summary.topProductQuantity = currentProductQuantity;
                                summary.topProductName = productName;
                                summary.topProductIncrease = String.format("%.1f%%", 
                                    (currentProductQuantity * 100.0) / Math.max(1, summary.totalItemsSold));
                            }
                        }
                    }
                }
            }
        }
        return summary;
    }


    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pane = new javax.swing.JLayeredPane();
        card1 = new sales.Card();
        card2 = new sales.Card();
        card3 = new sales.Card();
        panelBorder1 = new homepage.PanelBorder();
        card4 = new sales.Card();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(237, 249, 255));
        setPreferredSize(new java.awt.Dimension(900, 600));

        pane.setPreferredSize(new java.awt.Dimension(900, 200));
        pane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        card1.setColor1(new java.awt.Color(186, 15, 82));
        card1.setColor2(new java.awt.Color(186, 15, 82));
        card1.setPreferredSize(new java.awt.Dimension(250, 180));
        pane.add(card1);

        card2.setColor1(new java.awt.Color(82, 186, 15));
        card2.setColor2(new java.awt.Color(82, 186, 15));
        card2.setPreferredSize(new java.awt.Dimension(250, 180));
        pane.add(card2);

        card3.setColor1(new java.awt.Color(15, 82, 186));
        card3.setColor2(new java.awt.Color(40, 159, 255));
        card3.setPreferredSize(new java.awt.Dimension(250, 180));
        pane.add(card3);

        panelBorder1.setBackground(new java.awt.Color(237, 249, 255));

        card4.setColor1(new java.awt.Color(186, 99, 15));
        card4.setColor2(new java.awt.Color(238, 137, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/scrambIm.png"))); // NOI18N

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(pane, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    
    
    private static class ProductData {
        String productId;
        String name;
        double price;
        double ourPrice;

        public ProductData(String productId, String name, double price, double ourPrice) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.ourPrice = ourPrice;
        }
    }

    private static class OrderSummary {
        double totalRevenue = 0;
        int totalItemsSold = 0;
        int customersServed = 0;
        String topProductName = "";
        int topProductQuantity = 0;
        String topProductIncrease = "0";
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private sales.Card card1;
    private sales.Card card2;
    private sales.Card card3;
    private sales.Card card4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane pane;
    private homepage.PanelBorder panelBorder1;
    // End of variables declaration//GEN-END:variables
}
