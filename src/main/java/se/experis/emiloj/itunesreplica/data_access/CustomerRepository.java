package se.experis.emiloj.itunesreplica.data_access;

import se.experis.emiloj.itunesreplica.models.Customer;
import org.sqlite.jdbc4.JDBC4PreparedStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class CustomerRepository {

    private String URL ="jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private Connection conn = null;

    public ArrayList<Customer> getAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT CustomerId, FirstName, LastName,Country,PostalCode, Phone FROM customer");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                customers.add( new Customer(
                        set.getInt("CustomerId"),
                        set.getString("FirstName"),
                        set.getString("LastName"),
                        set.getString("Country"),
                        set.getString("PostalCode"),
                        set.getString("Phone")
                ));
            }
            System.out.println("Get all went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        return customers;
    }

    public Boolean addCustomer(Customer customer){
        Boolean success = false;
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("INSERT INTO customer(FirstName,LastName,Company,Address,City,State,Country,PostalCode,Phone,Fax,Email,SupportRepId)" +
                            " VALUES(?,?,'Experis','Street 2','Stockholm','Sodermanland',?,?,?,'74567','first.last@mail.com',8)");
            prep.setString(1,customer.getFirstName());
            prep.setString(2,customer.getLastName());
            prep.setString(3,customer.getCountry());
            prep.setString(4,customer.getPostalCode());
            prep.setString(5,customer.getPhoneNumber());

            int result = prep.executeUpdate();
            success = (result != 0); // if res = 1; true   //Ändra till status code int

            System.out.println("Add went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return success;
    }

    public boolean updateCustomer(Customer customer) {
        Boolean success = false;

        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("UPDATE customer SET FirstName=?, LastName=?, Country=?, PostalCode=?, Phone=?  WHERE CustomerId = ?");
            prep.setString(1, customer.getFirstName());
            prep.setString(2, customer.getLastName());
            prep.setString(3, customer.getCountry());
            prep.setString(4, customer.getPostalCode());
            prep.setString(5, customer.getPhoneNumber());
            prep.setInt(6, customer.getCustomerId());

            int result = prep.executeUpdate();
            success = (result != 0);

            System.out.println("Update went well!");

        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }

        return success;
    }

    public TreeMap<String, Integer> getNumberOfCostumersPerCountry() {
        var customer = getAllCustomers();
        var map = new TreeMap<String, Integer>();


        for (int i = 0; i<customer.size(); i++) {
            int count = 1;
            if (map.get(customer.get(i).getCountry()) == null) {
                map.put(customer.get(i).getCountry(), 1);
            } else {
                map.put(customer.get(i).getCountry(), map.get(customer.get(i).getCountry()+1));
            }
        }


//        try {
//            conn = DriverManager.getConnection(URL);
//            PreparedStatement prep =
//                    conn.prepareStatement("SELECT Country FROM customer");
//
//            ResultSet set = prep.executeQuery();
//
//            while(set.next()) {
//
//            }
//
//
//        } catch (Exception exception) {
//            System.out.println(exception.toString());
//        }
//        finally {
//            try{
//                conn.close();
//            } catch (Exception exception){
//                System.out.println(exception.toString());
//            }
//        }

        return map;
    }
}
