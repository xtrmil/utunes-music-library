package se.experis.emiloj.itunesreplica.data_access;

import se.experis.emiloj.itunesreplica.models.Customer;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

public class CustomerRepository {

    private String URL ="jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private Connection conn = null;

    public ArrayList<Customer> getAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT CustomerId, FirstName, LastName, Country, PostalCode, Phone FROM customer");
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

    public LinkedHashMap<String, Integer> getNumberOfCustomersPerCountry() {
        var customer = getAllCustomers(); // create arraylist containing all customers
        var map = new HashMap<String, Double>();

        for (int i = 0; i<customer.size(); i++) {
            if (!map.containsKey(customer.get(i).getCountry())) { // check if the country has no value in the map, if so set value to 1
                map.put(customer.get(i).getCountry(), 1.0);
            } else { // else if key exists, add 1 to the linked value
                map.put(customer.get(i).getCountry(), map.get(customer.get(i).getCountry())+1);
            }
        }
        var sortedMap = new LinkedHashMap<String,Integer>();
        sortMapByValue(map).forEach(entry ->{
            sortedMap.put(entry.getKey(), entry.getValue().intValue());
        });

        return sortedMap;
    }

    public LinkedHashMap<String, Double> getHighestSpender() {
        var inputMap = new HashMap<String, BigDecimal>();
        var tempMap = new HashMap<String,Double>();

        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT Customer.FirstName, Customer.LastName, Invoice.Total " +
                            "FROM customer INNER JOIN invoice " +
                            "WHERE Customer.CustomerId=Invoice.CustomerId");
            ResultSet set = prep.executeQuery();

            while(set.next()) {
                String name = set.getString("FirstName") + " " + set.getString("LastName");
                BigDecimal temp = set.getBigDecimal("Total");
                if (!inputMap.containsKey(name)) { // check if the country has no value in the map, if so set value to 1
                    inputMap.put(name, temp);
                } else { // else if key exists, add 1 to the linked value
                    inputMap.put(name, inputMap.get(name).add(temp));
                }
            }

            for (Map.Entry<String, BigDecimal> entry : inputMap.entrySet()) {    //Step to convert the BigDecimal to Double to fit our SortByValue method
                tempMap.put(entry.getKey(),entry.getValue().doubleValue());
            }

        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
        var sortedMap = new LinkedHashMap<String,Double>();
        sortMapByValue(tempMap).forEach(entry ->{ // adding entries to a linked hashmap to preserve the order of the stream
            sortedMap.put(entry.getKey(), entry.getValue());
        });
        return sortedMap;
    }

    public ArrayList<String> getCustomersFavoriteGenre(int id) {

        var tempMap = new HashMap<String, Double>();

        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT Genre.Name " +
                            "FROM Genre INNER JOIN Track " +
                            "ON Genre.GenreId=Track.GenreId " +
                            "INNER JOIN InvoiceLine " +
                            "ON Track.TrackId=InvoiceLine.TrackId " +
                            "INNER JOIN Invoice " +
                            "ON InvoiceLine.InvoiceId=Invoice.InvoiceId " +
                            "INNER JOIN Customer " +
                            "ON Invoice.CustomerId=?");
            prep.setInt(1, id);
            ResultSet set = prep.executeQuery();

            while (set.next()) {
                String name = set.getString("Name");
                if (!tempMap.containsKey(name)) { // check if the country has no value in the map, if so set value to 1
                    tempMap.put(name, 1.0);
                } else { // else if key exists, add 1 to the linked value
                    tempMap.put(name, tempMap.get(name) + 1.0);
                }
            }

        } catch(Exception exception){
            System.out.println(exception.toString());
        }


        var favorites = new ArrayList<String>();

        favorites.add(sortMapByValue(tempMap).findFirst().get().getKey());
        double temp = sortMapByValue(tempMap).findFirst().get().getValue();

        if(temp == sortMapByValue(tempMap).skip(1).findFirst().get().getValue()){
            favorites.add(sortMapByValue(tempMap).skip(1).findFirst().get().getKey());
        }
            return favorites;
    }



    public Stream<Map.Entry<String, Double>> sortMapByValue(HashMap<String, Double> map) {

        Stream<Map.Entry<String, Double>> stream = map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())); // creating a stream sorted by value

        return stream;
    }
}
