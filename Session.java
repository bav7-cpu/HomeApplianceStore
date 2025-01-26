//21348140
package Model;

/* the class creates the constructors, getters and setters for the session
 * 
 */
public class Session {
    private final int userId;
    private final String userName;
    private final String password;

    public Session(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
