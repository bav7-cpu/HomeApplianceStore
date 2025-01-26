//21348140
package DAO;
/*
 * This class connects to the customer DB table
 *  there are methods which can give the admin the ability to:
 *  	find all customers
 *  	delete a customer from the DB
 *  add a customer to the DB
 *  update a customers properties
 *  or if in the menu system find a specific customer by their id
 */

import java.sql.*;
import java.util.ArrayList;

import Model.Address;
import Model.Customer;
public class CustomerDAO {
    public CustomerDAO() {}
//    private AddressDAO addressDAO = new AddressDAO();

    private static Connection getDBConnection() {	//sets up the database connection
        Connection dbConnection = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            String dbURL = "jdbc:sqlite:HomeAppliance.sqlite";	//the SQL database
            dbConnection = DriverManager.getConnection(dbURL);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    //This method will allow for the user in the menu console or admin in the webserver to find all the customers within the system
    public ArrayList<Customer> findAllCustomers() throws SQLException {
        System.out.println("Finding all customers...");
        ArrayList<Customer> customer = new ArrayList<>();
        String query = "SELECT * FROM customer";

        try (
                Connection dbConnection = getDBConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
                ResultSet result = statement.executeQuery()
        ) {
            //  iterates through the result set
            while (result.next()) {
                int customerID = result.getInt("customerID");
                String businessName = result.getString("businessName");
                Address address = new Address( //created an object "address" so the address details can be passed through
                        result.getString("addressLine0"),
                        result.getString("addressLine1"),
                        result.getString("addressLine2"),
                        result.getString("country"),
                        result.getString("postCode")
                );
                String telephoneNumber = result.getString("telephoneNumber");


                // Add the address object to the list
                customer.add(new Customer(customerID, businessName, address, telephoneNumber));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all customer: " + e.getMessage());
        }

        return customer;

    }

    //This method will allow for the user in the menu console find a specific customers within the system

    public Customer findCustomer(int id) throws SQLException {
        Customer customer = null;
        String query = "SELECT * FROM customer WHERE CustomerID =?";
        try (
            Connection dbConnection = getDBConnection();
            PreparedStatement statement = dbConnection.prepareStatement(query);
//            ResultSet result = statement.executeQuery();
        ){
            statement.setInt(1, id); // Set the ID parameter
            ResultSet result = statement.executeQuery();
            while (result.next()) {

                int customerID = result.getInt("customerID");
                String businessName = result.getString("businessName");
                Address address = new Address(
                        result.getString("addressLine0"),
                        result.getString("addressLine1"),
                        result.getString("addressLine2"),
                        result.getString("country"),
                        result.getString("postCode")
                );
                String telephoneNumber = result.getString("telephoneNumber");

                customer = new Customer(customerID,businessName,address,telephoneNumber);

            }
        } catch (SQLException e) {
            System.out.println("Error finding customer: " + e.getMessage());

        }
        return customer;
    }

    //This method will allow for the user in the menu console or admin in the webserver to delete a  customer within the system
    public Boolean deleteCustomer(int customerid) throws SQLException{
        System.out.println("Deleting customer");
        String query = "DELETE FROM customer WHERE customerID = ?";
        int result = 0;
        try (
                Connection dbConnection = getDBConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query)
        ) {
            // Set the parameter customerid to become 1
            statement.setInt(1, customerid);

            System.out.println("Executing query: " + query);
            result = statement.executeUpdate(); //the result becomes the updated statement
            System.out.println("Rows affected: " + result);
        } catch (SQLException e) {
            System.out.println("Error deleting customer with ID " + customerid + ": " + e.getMessage());
            return false; 
        }

        return result > 0;
    }


    //This method will allow for the user in the menu console or admin in the webserver to update a customer within the system
    public Boolean updateCustomer(Customer customer) throws SQLException{
        String query= "UPDATE customer " +
                "SET businessName = '"	+ customer.getBusinessName() + "'," +
                "addressLine0 ='" + customer.getAddress().getaddressLine0() + "'," +
                "addressLine1 ='" + customer.getAddress().getaddressLine1() + "'," +
                "addressLine2='" + customer.getAddress().getaddressLine2() + "'," +
                "Country='" + customer.getAddress().getcountry() + "'," +
                "Postcode = '"	+ customer.getAddress().getpostCode() + "'," +
                "telephoneNumber= '" + customer.getTelephoneNumber() + "' " +
                "WHERE customerID = " + customer.getCustomerID();
        try (
              Connection dbConnection = getDBConnection();
                PreparedStatement statement= dbConnection.prepareStatement(query) // again the prepared statement will just prepare the SQL query used earlier
        )  {

            System.out.println("Executing query: " + query);	// literally executes the query above
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {	// catches the SQLException throws above and if it fails it will print and return what is below
            System.out.println("Error updating select product with ID" + e.getMessage());
            return false; 
        }
        	

    }

    //This method will allow for the user in the menu console or admin in the webserver to add a customer to the system
    public boolean addCustomer(Customer insert)  throws SQLException{

        Address address = insert.getAddress();
        String query = "INSERT INTO customer " +
                "VALUES (" +
                insert.getCustomerID() + ", " +
                "'" + insert.getBusinessName().replace("'", "''") + "', " +
                "'" + (address != null ? address.getaddressLine0().replace("'", "''") : "") + "', " +
                "'" + (address != null ? address.getaddressLine1().replace("'", "''") : "") + "', " +
                "'" + (address != null ? address.getaddressLine2().replace("'", "''") : "") + "', " +
                "'" + (address != null ? address.getcountry().replace("'", "''") : "") + "', " +
                "'" + (address != null ? address.getpostCode().replace("'", "''") : "") + "', " +
                "'" + insert.getTelephoneNumber().replace("'", "''") + "');";

        try (
                Connection dbConnection = getDBConnection();
                Statement statement = dbConnection.createStatement()
        )  {
            System.out.println("Executing query: " + query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error adding customer with this ID" + e.getMessage());
            return false;
        }
        return true;
    }

}
