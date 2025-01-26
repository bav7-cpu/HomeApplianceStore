//21348140
package Model;

/* the class creates the constructors, getters and setters for the address
 * 
 */
public class Address {
    private String addressLine0;
    private String addressLine1;
    private String addressLine2;
    private String country;
    private String postCode;

    // constructors that pass through wheneven Address is used
    public Address( String addressLine0, String addressLine1, String addressLine2, String country, String postCode) {
    	//making it specific to the address of the customer
        this.addressLine0 = addressLine0;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.postCode = postCode;
    }
    
    //gets the sepcific 
    public String getaddressLine0() {
        return addressLine0;
    }

    public String getaddressLine1() {
        return addressLine1;
    }

    public String getaddressLine2() {
        return addressLine2;
    }

    public String getcountry() {
        return country;
    }

    public String getpostCode() {
        return postCode;
    }
//
//    public void setaddressID(int addressID) {
//        this.addressID = addressID;
//    }
    public void setaddressLine0(String addressLine0) {
        this.addressLine0 = addressLine0;
    }

    public void setaddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setaddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public void setpostCode(String postCode) {
        this.postCode = postCode;
    }

    // Method to list all addresses (This could be improved with actual implementation)
    public void listAddresses(int id) {
        System.out.println("Listing addresses for ID: " + id);
    }

    // Method to search an address by ID (This could be improved with actual implementation)
    public void searchAddress(int id) {
        System.out.println("Searching address with ID: " + id);
    }

    // Method to add an address (This could be improved with actual implementation)
    public void addAddress(int id) {
        System.out.println("Adding address with ID: " + id);
    }

    // Method to update an address by ID (This could be improved with actual implementation)
    public void updateAddress(int id) {
        System.out.println("Updating address with ID: " + id);
    }

    // Method to delete an address by ID (This could be improved with actual implementation)
    public void deleteAddress(int id) {
        System.out.println("Deleting address with ID: " + id);
    }

    // Method to get the size (could be implemented with a list of addresses)
    public int size() {
        return 0;  // This should return the actual size if there is a collection of addresses.
    }

    @Override
    public String toString() {
        return
                "\n addressLine0='" + addressLine0 + '\'' +
                ",\n addressLine1='" + addressLine1 + '\'' +
                ",\n addressLine2='" + addressLine2 + '\'' +
                ",\n country='" + country + '\'' +
                ",\n postCode='" + postCode + '\'';
    }

    public Address[] split(String s) {
        return new Address[0];
    }
}

