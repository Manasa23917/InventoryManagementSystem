package inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class InventoryGUI {

    static String url = "jdbc:mysql://localhost:3306/inventory_db";
    static String user = "root";
    static String password = System.getenv("MYSQL_PASSWORD");

    static DefaultTableModel model;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Inventory Management System");
        frame.setSize(950, 680);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("INVENTORY MANAGEMENT SYSTEM");
        title.setBounds(320, 20, 350, 30);
        frame.add(title);

        JLabel idLabel = new JLabel("Product ID:");
        idLabel.setBounds(30, 70, 100, 30);
        frame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(130, 70, 180, 30);
        frame.add(idField);

        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setBounds(30, 110, 100, 30);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(130, 110, 180, 30);
        frame.add(nameField);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(30, 150, 100, 30);
        frame.add(categoryLabel);

        JTextField categoryField = new JTextField();
        categoryField.setBounds(130, 150, 180, 30);
        frame.add(categoryField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 190, 100, 30);
        frame.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(130, 190, 180, 30);
        frame.add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(30, 230, 100, 30);
        frame.add(quantityLabel);

        JTextField quantityField = new JTextField();
        quantityField.setBounds(130, 230, 180, 30);
        frame.add(quantityField);

        JLabel sellLabel = new JLabel("Sell Quantity:");
        sellLabel.setBounds(30, 270, 100, 30);
        frame.add(sellLabel);

        JTextField sellField = new JTextField();
        sellField.setBounds(130, 270, 180, 30);
        frame.add(sellField);

        JButton addButton = new JButton("Add Product");
        addButton.setBounds(350, 70, 130, 35);
        frame.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(500, 70, 120, 35);
        frame.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(640, 70, 120, 35);
        frame.add(deleteButton);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(350, 115, 130, 35);
        frame.add(searchButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(500, 115, 120, 35);
        frame.add(refreshButton);

        JButton sellButton = new JButton("Sell Product");
        sellButton.setBounds(640, 115, 120, 35);
        frame.add(sellButton);

        String[] columns = {
                "ID", "Name", "Category", "Price", "Quantity"
        };

        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 330, 850, 250);
        frame.add(scrollPane);

        // ADD PRODUCT
        addButton.addActionListener(e -> {

            try {

                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String category = categoryField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                Connection con =
                        DriverManager.getConnection(url, user, password);

                String sql =
                        "INSERT INTO products VALUES (?, ?, ?, ?, ?)";

                PreparedStatement pst =
                        con.prepareStatement(sql);

                pst.setInt(1, id);
                pst.setString(2, name);
                pst.setString(3, category);
                pst.setDouble(4, price);
                pst.setInt(5, quantity);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(
                        frame,
                        "Product Added Successfully!"
                );

                con.close();

                loadProducts();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Error: " + ex.getMessage()
                );
            }
        });

        // UPDATE PRODUCT
        updateButton.addActionListener(e -> {

            try {

                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String category = categoryField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                Connection con =
                        DriverManager.getConnection(url, user, password);

                String sql =
                        "UPDATE products SET name=?, category=?, price=?, quantity=? WHERE id=?";

                PreparedStatement pst =
                        con.prepareStatement(sql);

                pst.setString(1, name);
                pst.setString(2, category);
                pst.setDouble(3, price);
                pst.setInt(4, quantity);
                pst.setInt(5, id);

                int rows = pst.executeUpdate();

                if (rows > 0) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Product Updated Successfully!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Product ID not found!"
                    );
                }

                con.close();

                loadProducts();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Error: " + ex.getMessage()
                );
            }
        });

        // DELETE PRODUCT
        deleteButton.addActionListener(e -> {

            try {

                int id = Integer.parseInt(idField.getText());

                int choice = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to delete this product?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {

                    Connection con =
                            DriverManager.getConnection(
                                    url, user, password);

                    String sql =
                            "DELETE FROM products WHERE id=?";

                    PreparedStatement pst =
                            con.prepareStatement(sql);

                    pst.setInt(1, id);

                    int rows = pst.executeUpdate();

                    if (rows > 0) {

                        JOptionPane.showMessageDialog(
                                frame,
                                "Product Deleted Successfully!"
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                frame,
                                "Product ID not found!"
                        );
                    }

                    con.close();

                    loadProducts();
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Error: " + ex.getMessage()
                );
            }
        });

        // SEARCH PRODUCT
        searchButton.addActionListener(e -> {

            try {

                String search = idField.getText();

                model.setRowCount(0);

                Connection con =
                        DriverManager.getConnection(url, user, password);

                String sql =
                        "SELECT * FROM products WHERE id=? OR name LIKE ?";

                PreparedStatement pst =
                        con.prepareStatement(sql);

                pst.setString(1, search);
                pst.setString(2, "%" + search + "%");

                ResultSet rs = pst.executeQuery();

                while (rs.next()) {

                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("quantity")
                    });
                }

                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Error: " + ex.getMessage()
                );
            }
        });

        // SELL PRODUCT
        sellButton.addActionListener(e -> {

            try {

                int id = Integer.parseInt(idField.getText());
                int sellQuantity =
                        Integer.parseInt(sellField.getText());

                Connection con =
                        DriverManager.getConnection(
                                url, user, password);

                String checkSql =
                        "SELECT quantity FROM products WHERE id=?";

                PreparedStatement check =
                        con.prepareStatement(checkSql);

                check.setInt(1, id);

                ResultSet rs = check.executeQuery();

                if (rs.next()) {

                    int currentStock =
                            rs.getInt("quantity");

                    if (sellQuantity <= 0) {

                        JOptionPane.showMessageDialog(
                                frame,
                                "Enter a valid sell quantity!"
                        );

                    } else if (sellQuantity > currentStock) {

                        JOptionPane.showMessageDialog(
                                frame,
                                "Insufficient stock! Available: "
                                        + currentStock
                        );

                    } else {

                        int newStock =
                                currentStock - sellQuantity;

                        String updateSql =
                                "UPDATE products SET quantity=? WHERE id=?";

                        PreparedStatement update =
                                con.prepareStatement(updateSql);

                        update.setInt(1, newStock);
                        update.setInt(2, id);

                        update.executeUpdate();

                        JOptionPane.showMessageDialog(
                                frame,
                                "Product Sold Successfully!\n"
                                        + "Remaining Stock: "
                                        + newStock
                        );

                        sellField.setText("");

                        loadProducts();
                    }

                } else {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Product ID not found!"
                    );
                }

                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Error: " + ex.getMessage()
                );
            }
        });

        // REFRESH
        refreshButton.addActionListener(e -> loadProducts());

        // TABLE ROW SELECTION
        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                idField.setText(
                        model.getValueAt(row, 0).toString());

                nameField.setText(
                        model.getValueAt(row, 1).toString());

                categoryField.setText(
                        model.getValueAt(row, 2).toString());

                priceField.setText(
                        model.getValueAt(row, 3).toString());

                quantityField.setText(
                        model.getValueAt(row, 4).toString());
            }
        });

        loadProducts();

        frame.setVisible(true);
    }

    static void loadProducts() {

        model.setRowCount(0);

        try {

            Connection con =
                    DriverManager.getConnection(
                            url, user, password);

            String sql = "SELECT * FROM products";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                });
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
