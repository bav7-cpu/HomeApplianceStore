//212348140
package Model;
import java.util.ArrayList;

/* the class creates the constructors, getters and setters for the admin
 * 
 */
public class Admin extends ArrayList<Admin> {
    private int userID;
    private String username;
    private String password;

    public Admin(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }
    public int getuserID(){
        return userID;
    }

    public String getusername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public void setuserID(int userID) {
        this.userID = userID;
    }
    public void setusername(String username) {
        this.username = username;
    }

    public void setpassword(String password) {
        this.password = password;
    }


    // Method to get the size (could be implemented with a list of addresses)
    public int size() {
        return 0;  // This should return the actual size if there is a collection of addresses.
    }

    @Override
    public String toString() {
        return "Admin{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}