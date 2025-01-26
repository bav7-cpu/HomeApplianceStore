//21348140
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import DAO.CustomerDAO;
import DAO.HomeApplianceDAO;
import Model.Address;
import Model.Customer;
import Model.HomeAppliance;

//This class allows for a user to access the system within the menu console
//They will select 1 of the 11 options and the method for that case will run
public class MenuConsole {
    private static HomeApplianceDAO dao = new HomeApplianceDAO();
    private static CustomerDAO cus = new CustomerDAO();
    public static void main(String[] args) {
        MenuConsole menu = new MenuConsole();
        menu.displayMenu();
    }

    void displayMenu() {

        Scanner in = new Scanner(System.in);

        HomeAppliance homeAppliance = new HomeAppliance(0, null, null, null, 0, 0,0);
        // homeAppliance object will have these parameters
        Customer customerData = new Customer(0, null, null, null);
        // customerData object will have these parameters
        String selection;
        do {
            System.out.println("--------------------");
            System.out.println("The Home Appliance Store");
            System.out.println("Choose from these options");
            System.out.println("--------------------");
            System.out.println("[1] List all products");
            System.out.println("[2] Search for product by ID");
            System.out.println("[3] Add a new product");
            System.out.println("[4] Update a product by ID");
            System.out.println("[5] Delete a product by ID");
            System.out.println("[6] List all customers");
            System.out.println("[7] Search for customer by ID");
            System.out.println("[8] Add a new customer");
            System.out.println("[9] Update a customer by ID");
            System.out.println("[10] Delete a customer by ID");
            System.out.println("[11] Exit");


            System.out.println("Enter your choice");
            selection = in.nextLine();

            switch (selection) {
                //This method will list all products within the system
                case "1":
                    try {
                        ArrayList<HomeAppliance> appliances = dao.findAllProducts();
                        if (appliances.isEmpty()) {
                            System.out.println("No appliances found.");
                        } else {
                            System.out.println("\nAll Appliances:");
                            for (HomeAppliance appliance : appliances) {
                                System.out.println(appliance);
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Error listing appliances: " + e.getMessage());
                        break;
                    }
                    break;
                    //This method will allow for the user to find a specific product by its ID
                case "2":
                    System.out.println("\nSearch for a product by ID");
                    int searchID = in.nextInt();
                    System.out.println("Finding product with ID: " + searchID);
                    HomeAppliance appliance = null;
                    try {
                        appliance = dao.findProduct(searchID);// initially I tried to use an arraylist but it didn't make sense to, so I changed it
                        // this line of code will instead just attempt to find the specific product
                    } catch (SQLException e) {
                        System.out.println("Error fetching product: " + e.getMessage());
                    }

                    if (appliance != null) {
                        System.out.println("Product Found: " + appliance);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                    //This method will allow for the user to add a new products into the system
                case "3":
                    System.out.println("\nAdd a new product");
                    System.out.print("Enter Product ID: ");
                    int addID = in.nextInt();
                    in.nextLine(); // Clear the input buffer

                    try {
                        // Check if a product with the given ID already exists
                        HomeAppliance existingProduct = dao.findProduct(addID);
                        if (existingProduct != null) {
                            System.out.println("There is already a product with this ID. Please try again.");
                        } else {
                            // Prompt user for product details
                            System.out.print("Please enter the product SKU (stock keeping unit): ");
                            String addSku = in.nextLine();

                            System.out.print("Please enter a description of the product: ");
                            String addDescription = in.nextLine();

                            System.out.print("Please enter the category of the product: ");
                            String addCategory = in.nextLine();

                            System.out.print("Please enter a price for the product: ");
                            int addPrice = in.nextInt();
                            in.nextLine();

                            System.out.print("Please enter a warranty for the product: ");
                            int addWarranty = in.nextInt();
                            in.nextLine();

                            System.out.print("Please enter the stock quantity for the product: ");
                            int addStock = in.nextInt();
                            in.nextLine(); // Clear the input buffer

                            // Create the new product
                            HomeAppliance newHomeAppliance = new HomeAppliance(addID, addSku, addDescription, addCategory, addPrice, addWarranty, addStock);

                            // Add the product to the database
                            if (dao.addProduct(newHomeAppliance)) {
                                System.out.println("Product added successfully: " + newHomeAppliance);
                            } else {
                                System.out.println("Failed to add the product. Please try again.");
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Error adding product: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("An unexpected error occurred: " + e.getMessage());
                    }
                    break;
                // this method will allow for the user to update a specific product by its ID
                // when they do this the selected product's details will popup
                case "4":
                    System.out.println("\nUpdate a product by ID");
                    System.out.print("Enter the Product ID to update: ");
                    int updateID = in.nextInt();
                    in.nextLine(); // Clear the input buffer

                    HomeAppliance UpdateProduct = null;

                    try {
                        // Fetch the existing product to check if it exists
                        UpdateProduct = dao.findProduct(updateID);
                    } catch (SQLException e) {
                        System.out.println("Error fetching product: " + e.getMessage());
                    }

                    if (UpdateProduct == null) {
                        System.out.println("Product with ID " + updateID + " not found.");
                    } else {
                        System.out.println("Current Product Details: " + UpdateProduct);

                        // Prompt the user for updated values
                        System.out.print("Enter new SKU, retype old SKU if you do not want to change this part of the product (current: " + UpdateProduct.getSku() + "): ");
                        String newSku = in.nextLine();


                        System.out.print("Enter new Description, retype old Description if you do not want to change this part of the product (current: " + UpdateProduct.getDescription() + "): ");
                        String newDescription = in.nextLine();


                        System.out.print("Enter new Category, retype old Category if you do not want to change this part of the product (current: " + UpdateProduct.getCategory() + "): ");
                        String newCategory = in.nextLine();


                        System.out.print("Enter new Price, retype old Price if you do not want to change this part of the product (current: " + UpdateProduct.getPrice() + "): ");
                        int newPrice = in.nextInt();
                        in.nextLine();

                        System.out.print("Enter new Warranty, retype old Warranty if you do not want to change this part of the product (current: " + UpdateProduct.getWarranty() + "): ");
                        int newWarranty = in.nextInt();
                        in.nextLine();

                        System.out.print("Enter new Stock, retype old Stock if you do not want to change this part of the product (current: " + UpdateProduct.getStock() + "): ");
                        int newStock = in.nextInt();
                        in.nextLine();

                        // object for the updated version of the product
                        // it includes the new SKU ETC
                        HomeAppliance updatedProduct = new HomeAppliance(updateID, newSku, newDescription, newCategory, newPrice, newWarranty, newStock);

                        // Call the main.DAO method to update the product
                        try {
                            if (dao.updateItem(updatedProduct)) {
                                System.out.println("Product updated successfully.");
                            } else {
                                System.out.println("Failed to update the product.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error updating product: " + e.getMessage());
                        }
                    }
                    break;
                // this method will allow for a user to delete a product from the system by selecting it's id
                // when they do this the selected product's details will popup
                case "5":
                    System.out.println("\nDelete a product by ID");
                    System.out.print("Enter the ID of the product you want to delete from the database: ");
                    int deleteID = in.nextInt();
                    in.nextLine();

                    try {
                        boolean deleted = dao.deleteProduct(deleteID);
                        if (deleted) {
                            System.out.println("Product with ID " + deleteID + " deleted successfully.");
                        } else {
                            System.out.println("Product with ID " + deleteID + " not found or could not be deleted.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error deleting product: " + e.getMessage());
                    }
                    break;
                //This method will list all customers within the system
                case "6":
                    try {
                        ArrayList<Customer> customer = cus.findAllCustomers();
                        if (customer.isEmpty()) {
                            System.out.println("No customer found.");
                        } else {
                            System.out.println("\nAll Customers:");
                            for (Customer customer1 : customer) {
                                System.out.println(customer1);
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Error listing customers: " + e.getMessage());
                        break;
                    }
                    break;
                //This method will allow for the user to find a specific customer by their ID
                    case "7":
                    System.out.println("\nSearch for a customer by ID");
                    int cusID = in.nextInt();
                    System.out.println("Finding customer with ID: " + cusID);
                    Customer cus1 = null;
                    try {
                        cus1 = cus.findCustomer(cusID);// initially I tried to use an arraylist but it didn't make sense to, so I changed it
                        // this line of code will instead just attempt to find the specific product
                    } catch (SQLException e) {
                        System.out.println("Error fetching customer: " + e.getMessage());
                    }

                    if (cus1 != null) {
                        System.out.println("main.Model.Customer Found: " + cus1);
                    } else {
                        System.out.println("main.Model.Customer not found. Enter a valid customer ID");
                    }
                    break;
                //This method will allow for the user to add a new customer into the system
                case "8":
                    System.out.println("\nAdd customers");
                    System.out.print("Enter customer ID: ");
                    int addCusID = in.nextInt();
                    in.nextLine();
//                    boolean idExists1 = false;    // needed to prevent appliances with same id as appliances that already exist from being added
//                    main.Model.Customer newcustomer = null;
                    try {
                        Customer existingCustomer = cus.findCustomer(addCusID);
                        if (existingCustomer != null) {
                            System.out.println("There is already a customer with this ID. Please try again.");
                        } else {
                            System.out.print("Please enter the main.Model.Customer Business Name: ");
                            String addBusinessName = in.nextLine();
                            // Prompt user for product details
                            System.out.print("Please enter the main.Model.Customer addressLine0: ");
                            String addAddressLine0 = in.nextLine();

                            System.out.print("Please enter the main.Model.Customer addressLine1: ");
                            String addAddressLine1 = in.nextLine();

                            System.out.print("Please enter the main.Model.Customer addressLine2: ");
                            String addAddressLine2 = in.nextLine();

                            System.out.print("Please enter the main.Model.Customer Country: ");
                            String addCountry = in.nextLine();

                            System.out.print("Please enter the main.Model.Customer PostalCode: ");
                            String addPostCode = in.nextLine();

                            System.out.print("Please enter the main.Model.Customer Telephone Number: ");
                            String addTelephoneNumber = in.nextLine();

                            Address addAddress = new Address(addAddressLine0, addAddressLine1, addAddressLine2, addCountry, addPostCode);

                            Customer newCustomer = new Customer(addCusID, addBusinessName, addAddress, addTelephoneNumber);

                            //main.Model.Customer newCustomerWithoutAddress = new main.Model.Customer(addCusID, addBusinessName, addTelephoneNumber);

                            // Create the new main.Model.Customer
//                            main.Model.Customer newCustomer = new main.Model.Customer(addCusID, addAddressLine0, addAddressLine1, addAddressLine2, addCountry, addPostalCode, addTelephoneNumber);

                            // Add the main.Model.Customer to the database
                            if (cus.addCustomer(newCustomer)) {
                                System.out.println("main.Model.Customer added successfully: " + newCustomer);
                            } else {
                                System.out.println("Failed to add the customer. Please try again.");
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Error adding customer: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("An unexpected error occurred: " + e.getMessage());
                    }
                    break;
                // this method will allow for the user to update a specific customers details by its ID
                // when they do this the selected customer's details will popup
                case "9":
                    System.out.println("\nUpdate a customer by ID");
                    System.out.print("Enter the customer ID to update: ");
                    int updateCusID = in.nextInt();
                    in.nextLine(); // Clear the input buffer

                    Customer UpdateCustomer = null;

                    try {
                        // Fetch the existing product to check if it exists
                        UpdateCustomer = cus.findCustomer(updateCusID);
                    } catch (SQLException e) {
                        System.out.println("Error fetching product: " + e.getMessage());
                    }

                    if (UpdateCustomer == null) {
                        System.out.println("main.Model.Customer with ID " + updateCusID + " not found.");
                    } else {
                        System.out.println("Current customer Details: " + UpdateCustomer);

                        // Prompt the user for updated values
                        System.out.print("Enter new business name, retype old business name if you do not want to change this part of the customer (current: " + UpdateCustomer.getBusinessName() + "): ");
                        String newBusinessName = in.nextLine();


                        System.out.println("Please enter the new customer address details:");

                        System.out.println("main.Model.Address Line 0: ");
                        String addressLine0 = in.nextLine();

                        System.out.println("main.Model.Address Line 1: ");
                        String addressLine1 = in.nextLine();

                        System.out.println("main.Model.Address Line 2: ");
                        String addressLine2 = in.nextLine();

                        System.out.println("Country: ");
                        String country = in.nextLine();

                        System.out.println("Post Code: ");
                        String postCode = in.nextLine();
                        System.out.print("Enter new Telephone Number, retype old Telehpone Number if you do not want to change this part of the product (current: " + UpdateCustomer.getTelephoneNumber() + "): ");
                        String newTelephoneNumber = in.nextLine();


                        Address newAddress = new Address(addressLine0, addressLine1, addressLine2, country, postCode);

                        // object for the updated version of the product
                        // it includes the new SKU ETC
                        Customer updatecustomer = new Customer(updateCusID, newBusinessName, newAddress, newTelephoneNumber);

                        // Call the main.DAO method to update the product
                        try {
                            if (cus.updateCustomer(updatecustomer)) {
                                System.out.println("main.Model.Customer updated successfully.");
                            } else {
                                System.out.println("Failed to update the customer.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error updating customer: " + e.getMessage());
                        }
                    }
                    break;
                // this method will allow for a user to delete a customer from the system by selecting it's id
                // when they do this the selected customer's details will popup
                case "10":
                    System.out.println("\nDelete a customer by ID");
                    System.out.print("Enter the ID of the customer you want to delete from the database: ");
                    int deletecusID = in.nextInt();
                    in.nextLine(); // Clear the buffer

                    try {
                        // Attempt to delete the customer
                        boolean deleteCustomer = cus.deleteCustomer(deletecusID);
                        if (deleteCustomer) {
                            System.out.println("main.Model.Customer with ID " + deletecusID + " deleted successfully.");
                        } else {
                            System.out.println("main.Model.Customer with ID " + deletecusID + " not found or could not be deleted.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error deleting customer: " + e.getMessage());
                    }
                    break;

            }
        }while (!selection.equals("11")) ;

        in.close();

    }}