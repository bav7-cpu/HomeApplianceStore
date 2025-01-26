//21348140
import com.sun.net.httpserver.HttpServer;

import Filter.FilterProducts;
import Handler.AddCustomersHandler;
import Handler.AddHandler;
import Handler.AdminHandler;
import Handler.AdminLoginHandler;
import Handler.BasketHandler;
import Handler.CheckoutHandler;
import Handler.ClearBasketHandler;
import Handler.DeleteCustomerHandler;
import Handler.DeleteHandler;
import Handler.DisplayAdminHandler;
import Handler.DisplayCustomersHandler;
import Handler.DisplayHandler;
import Handler.LogoutHandler;
import Handler.ProcessAddHandler;
import Handler.ProcessCustomerAddHandler;
import Handler.RootHandler;
import Handler.UpdateCustomerHandler;
import Handler.UpdateHandler;

import java.io.IOException;
import java.net.InetSocketAddress;


class Main {
    static final private int PORT = 8080;

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT),0);
        server.setExecutor(null); // Default executor
        server.createContext("/",  new RootHandler());
//        server.createContext("/addtobasket",new AddToBasketHandler());
        server.createContext("/Login",  new AdminLoginHandler());
        server.createContext("/addcustomers", new AddCustomersHandler());
        server.createContext("/add", new AddHandler());
        server.createContext("/AdminHandler", new AdminHandler());
        server.createContext("/basket", new BasketHandler());
        server.createContext("/clearBasket", new ClearBasketHandler());
        server.createContext("/checkout", new CheckoutHandler());
        server.createContext("/display", new DisplayAdminHandler());
        server.createContext("/appliances", new DisplayHandler());
        server.createContext("/DisplayCustomer", new DisplayCustomersHandler());
        server.createContext("/delete", new DeleteHandler());
        server.createContext("/deleteCustomer", new DeleteCustomerHandler());
        server.createContext("/FilterProducts", new FilterProducts());
        server.createContext("/processAddProduct", new ProcessAddHandler());
        server.createContext("/processAddCustomer", new ProcessCustomerAddHandler());
        server.createContext("/edit", new UpdateHandler());
        server.createContext("/editCustomer", new UpdateCustomerHandler());
        server.createContext("/Logout", new LogoutHandler());
        //server.createContext("/processLogin", new ProcessLoginHandler());

        server.start();
        System.out.println("The server is listening on port " + PORT);


    }


}