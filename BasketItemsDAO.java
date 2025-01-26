//21348140
package DAO;


import java.sql.*;
import java.util.ArrayList;

import Model.HomeAppliance;

/*
 * This class will connect to the basket table in the DB
 * it has methods to let the user add, update and delete items from the basket
 */
public class BasketItemsDAO {

    // connects to the DB
    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:HomeAppliance.sqlite"; 
            dbConnection = DriverManager.getConnection(dbURL);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        return dbConnection;
    }

    //  the user can add an item to the basket through this method.
    public boolean addItemToBasket(int basketID, HomeAppliance appliance, int quantity) {
        String checkStockQuery = "SELECT stock FROM appliance WHERE id = ?";
        String addToBasketQuery = "INSERT INTO basket_items (basketID, id, quantity) VALUES (?, ?, ?)";
        try (
                Connection dbConnection = getDBConnection();
                PreparedStatement checkStockStmt = dbConnection.prepareStatement(checkStockQuery);
                PreparedStatement addToBasketStmt = dbConnection.prepareStatement(addToBasketQuery)
        ) {
            checkStockStmt.setInt(1, appliance.getId());
            ResultSet result = checkStockStmt.executeQuery();
            if (result.next() && result.getInt("stock") >= quantity) {
                addToBasketStmt.setInt(1, basketID);
                addToBasketStmt.setInt(2, appliance.getId());
                addToBasketStmt.setInt(3, quantity);
                return addToBasketStmt.executeUpdate() > 0;
            } else {
                System.out.println("Insufficient stock for appliance ID: " + appliance.getId());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error adding item to basket: " + e.getMessage());
            return false;
        }
    }

    // the user can update the amount of an item in their basket
    public boolean updateItemQuantity(int basketItemID, int quantity) {
        String query = "UPDATE basket_items SET quantity = ? WHERE basketItemID = ?";
        try (
                Connection dbConnection = getDBConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query)
        ) {
            statement.setInt(1, quantity);
            statement.setInt(2, basketItemID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating item quantity: " + e.getMessage());
            return false;
        }
    }

    // the user can remove an item in their basket
    public boolean removeItemFromBasket(int basketItemID) {
        String query = "DELETE FROM basket_items WHERE basketItemID = ?";
        try (
                Connection dbConnection = getDBConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query)
        ) {
            statement.setInt(1, basketItemID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error removing item from basket: " + e.getMessage());
            return false;
        }
    }

    // gather all the items in the basket
    public ArrayList<String> getBasketItems(int basketID) {
        ArrayList<String> items = new ArrayList<>();
        String query = "SELECT * FROM basket_items WHERE basketID = ?";
        try (
                Connection dbConnection = getDBConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query)
        ) {
            statement.setInt(1, basketID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String item = "BasketItemID: " + resultSet.getInt("basketItemID") +
                        ", ApplianceID: " + resultSet.getInt("id") +
                        ", Quantity: " + resultSet.getInt("quantity");
                items.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching basket items: " + e.getMessage());
        }
        return items;
    }
}
