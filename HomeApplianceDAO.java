//21348140
package DAO;
/*
 * This class connects to the appliance DB table
 *  there are methods which can give the admin the ability to:
 *  	find all products
 *  	delete a product from the DB
 *  add a product to the DB
 *  update a products properties
 *  or if in the menu system find a specific product by its id
 */
import java.sql.*;
import java.util.ArrayList;

import Model.HomeAppliance;


public class HomeApplianceDAO {
    public HomeApplianceDAO() {}

    private static Connection getDBConnection() {	//sets up the database connection
        Connection dbConnection = null;
        try {
            Class.forName("org.sqlite.JDBC");	//SQLite JDBC
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            String dbURL = "jdbc:sqlite:HomeAppliance.sqlite";	//the SQL database, SQLite
            dbConnection = DriverManager.getConnection(dbURL);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    //This method will allow for the user in the menu console or admin in the webserver to find all the appliance within the system
    public ArrayList<HomeAppliance> findAllProducts() throws SQLException {
    	System.out.println("Finding all products...");
    	ArrayList<HomeAppliance> products = new ArrayList<>();
    	String query = "SELECT * FROM appliance";

    	try (
            // for the database connection
            Connection dbConnection = getDBConnection();
            PreparedStatement statement = dbConnection.prepareStatement(query);
            ResultSet result = statement.executeQuery()
    		) {
       
    		while (result.next()) {
    			int id = result.getInt("id"); 
    			String sku = result.getString("sku");
    			String description = result.getString("description");
    			String category = result.getString("category");
    			int price = result.getInt("price");
    			int warranty = result.getInt("warranty");
    			int stock = result.getInt("stock");
    			
    			products.add(new HomeAppliance(id, sku, description, category, price, warranty, stock));
        }
    } catch (SQLException e) {
        System.out.println("Error fetching all products: " + e.getMessage());
    }

    return products;

}
    //This method will allow for the user in the menu console to find a specific appliance within the system
    public HomeAppliance findProduct(int id) throws SQLException {
        HomeAppliance product = null;
        String query = "SELECT * FROM appliance WHERE id = ?";
        try (
                Connection dbConnection = getDBConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query)
        	) {
            	statement.setInt(1, id); // Set the ID parameter
            	ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id1 = result.getInt("id");
                String sku = result.getString("sku");
                String description = result.getString("description");
                String category = result.getString("category");
                int price = result.getInt("price");
                int warranty = result.getInt("Warranty");
                int stock = result.getInt("stock");
                product = new HomeAppliance(id1, sku, description, category, price, warranty, stock);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product: " + e.getMessage());
        }
        return product;
    }

    //This method will allow for the user in the menu console or admin in the webserver to delete an appliance within the system
    public Boolean deleteProduct(int id) throws SQLException{
        System.out.println("Deleting product");

        		String query = "DELETE FROM appliance WHERE ID = ?";
        		int result = 0; // set as 0 because no code had ran in this method yet,
        try (
                Connection dbConnection = getDBConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query)
        	) {
            // Set the parameter id to become 1
            statement.setInt(1, id);

            System.out.println("Executing query: " + query);
            result = statement.executeUpdate(); //the result becomes the updated statement
        } catch (SQLException e) {
            System.out.println("Error deleting product with ID " + id + ": " + e.getMessage());
            return false;
        }

        // If one or more rows were deleted it will return true
        return result > 0;
    }

    //This method will allow for the user in the menu console or admin in the webserver to update an appliance within the system
    public Boolean updateItem(HomeAppliance homeappliance) throws SQLException{
        //there is a way to make the SQL query less prone to SQL injections (https://bobby-tables.com/java) but I cannot figure out how to go about it.
        // Although I do know it would include "?" for each part property of the appliance
        	String query= "UPDATE appliance " +
                "SET SKU = '" + homeappliance.getSku() + "'," +
                "Description = '"	+ homeappliance.getDescription() + "'," +
                "Category= '" + homeappliance.getCategory() + "'," +
                "Price= '" + homeappliance.getPrice() + " '," +
                "Warranty= " + homeappliance.getWarranty() + "," +
                "Stock= " + homeappliance.getStock() +" " +
                "WHERE ID = " + homeappliance.getId();

        try (
                Connection dbConnection = getDBConnection();
                PreparedStatement statement= dbConnection.prepareStatement(query) // again the prepared statement will just prepare the SQL query used earlier
        )  {


            System.out.println("Executing query: " + query);	// literally executes the query above
            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {	// catches the SQLException throws above and if it fails it will print and return what is below
            System.out.println("Error updating select product with ID" + e.getMessage());
            return false; // Return false if an error occurs
        }	// otherwise it will return true

    }

    //This method will allow for the user in the menu console or admin in the webserver to add an appliance into the system
    public boolean addProduct(HomeAppliance insert)  throws SQLException{
        String query = "INSERT INTO appliance (ID, SKU, Description, Category, Price, Warranty, Stock) " +
                "VALUES (" +
                insert.getId() + ", " +
                "'" + insert.getSku().replace("'", "''") + "', " +
                "'" + insert.getDescription().replace("'", "''") + "', " +
                "'" + insert.getCategory().replace("'", "''") + "', " +
                insert.getPrice() + ", " +
                insert.getWarranty() + ", " +
                insert.getStock() + " " +");";
        try (
                Connection dbConnection = getDBConnection();
                Statement statement = dbConnection.createStatement()
        )  {
            System.out.println("Executing query: " + query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error adding product with this ID" + e.getMessage());
            return false;
        }
        return true;
    }

}
