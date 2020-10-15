package se.experis.emiloj.itunesreplica.data_access;

import se.experis.emiloj.itunesreplica.models.Customer;
import org.sqlite.jdbc4.JDBC4PreparedStatement;

import java.sql.*;
import java.util.ArrayList;
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
            //prep.setInt(1,customer.getCustomerId());
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
}
