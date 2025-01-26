//21348140
package Model;

/* the class creates the constructors, getters and setters for the basket
 * 
 */
public class Basket {
private int basketID;
private HomeAppliance id;
private int quantity;

        public Basket(int basketID, HomeAppliance id, int quantity) {
            this.basketID = basketID;
            this.id = id;
            this.quantity = quantity;
        }

    public int getBasketID() {return basketID;}
    public HomeAppliance getId() {return id;}
    public int getQuantity() {return quantity;}

    public void setBasketID(int basketID) {this.basketID = basketID;}
    public void setId(HomeAppliance id) {this.id = id;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public String toString() {
        return 		"basketID=" + basketID +
                ", id;\n='" + id + '\'' +
                ", quantity='" + quantity +
                '}';
    }
}