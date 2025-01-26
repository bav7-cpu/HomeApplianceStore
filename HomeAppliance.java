//21348140
package Model;

/* the class creates the constructors, getters and setters for the appliances
 * 
 */
public class HomeAppliance {
    private int id;
    private String sku; //stock keeping unit ( it is a code that is unique for each product)
    private String description;
    private String category;
    private int price;
    private int warranty;
    private int stock;

    public HomeAppliance(int id, String sku, String description, String category, int price, int warranty, int stock) {
        this.id = id;
        this.sku = sku;
        this.description = description;
        this.category = category;
        this.price = price;
        this.warranty = warranty;
        this.stock = stock;
    }
    public int getId() {
        return id;
    }
    public String getSku() {
        return sku;
    }
    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
    public int getPrice() {
        return price;
    }
    public int getWarranty() {
        return warranty;
    }
    public int getStock() {return stock;}

    public void setId(int id) {
        this.id = id;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }
    public void setStock(int stock) {this.stock = stock;}

    public void listProduct(int id) {
        System.out.println("Listing");
    }
    public void searchProduct(int id) {
        System.out.println("Searching");
    }
    public void addProduct(int id) {
    	System.out.println("Adding");}
    
    public void updateProduct(int id) {
        System.out.println("Updating");
    }
    public void deleteProduct(int id) {
        System.out.println("Deleting");
    }
    public int size() {
        return 0;
    }

    // will just turn everything into a string
    public String toString() {
        return 		"id=" + id +
                ", sku='" + sku + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price + '\'' +
                ", warranty=" + warranty + '\'' +
                ", stock =" + stock +
                '}';
    }

}
