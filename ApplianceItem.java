//21348140
package Model;



public class ApplianceItem {
    private int id;
    private HomeAppliance homeAppliance;
    private int warrantyYears;
    private String brand;
    private String model;

    public ApplianceItem(int id, HomeAppliance homeAppliance, int warrantyYears, String brand, String model) {
        this.id = id;
        this.homeAppliance = homeAppliance;
        this.warrantyYears = warrantyYears;
        this.brand = brand;
        this.model = model;


    }
    public int getid() {
        return id;
    }
    public HomeAppliance gethomeAppliance() {
        return homeAppliance;
    }
    public int getwarrantyYears() {
        return warrantyYears;
    }
    public String getbrand() {
        return brand;
    }
    public String getmodel() {
        return model;
    }


    public void setid(int id) {
        this.id = id;
    }
    public void sethomeAppliance(HomeAppliance homeAppliance) {
        this.homeAppliance = homeAppliance;
    }
    public void setwarrantyYears(int warrantyYears) {
        this.warrantyYears = warrantyYears;
    }
    public void setbrand(String brand) {
        this.brand = brand;
    }
    public void setmodel(String model) {
        this.model = model;
    }


    public void listProduct(int id) {
        System.out.println("Listing");
    }
    public void searchProduct(int id) {
        System.out.println("Searching");
    }
    public void addProduct(int id) {
        System.out.println("Adding");
    }
    public void updateProduct(int id) {
        System.out.println("Updating");
    }
    public void deleteProduct(int id) {
        System.out.println("Deleting");
    }
    public int size() {
        return 0;
    }

    public String toString() {
        return 		"id=" + id +
                ", homeAppliance='" + homeAppliance + '\'' +
                ", warrantyYears='" + warrantyYears + '\'' +
                ", brand=" + brand + '\'' +
                ", model=" + model +
                '}';
    }

}
