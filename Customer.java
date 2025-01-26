//21348140
//This class will set the properties/constructors of the customer details
package Model;


/* the class creates the constructors, getters and setters for the customer
 * 
 */
public class Customer {
    private int customerID;
    private String businessName;
    private Address address;
    private String telephoneNumber;

public Customer(int customerID, String businessName, Address address, String telephoneNumber) {
    this.customerID = customerID;
    this.businessName = businessName;
    this.address = address;
    this.telephoneNumber = telephoneNumber;
}

    public static void add(Customer customer) {
    }

    public int getCustomerID(){
        return customerID;
}
    public String getBusinessName() {
    return businessName;
}
    public Address getAddress() {
        return address;
}
    public String getTelephoneNumber() {
        return telephoneNumber;
}


    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setTelephoneNumbery(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    
    public int size() {
        return 0;
    }

    // Turn everything into a string
    public String toString() {
        return 	"\n customerID=" + customerID +
                ",\n businessName='" + businessName + '\'' +
                ",\n address='" + address + '\'' +
                ",\n telephoneNumber='" + telephoneNumber + '\'' +
                '}';
    }
}

