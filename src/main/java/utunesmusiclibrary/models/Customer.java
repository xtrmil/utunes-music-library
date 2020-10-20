package utunesmusiclibrary.models;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String country;
    private String postalCode;
    private String phoneNumber;

    public Customer(){

    }

    public Customer(int customerId,String firstName,String lastName,String country,String postalCode,String phoneNumber){
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCountry() { return country; }
    public String getPostalCode() { return postalCode; }
    public String getPhoneNumber() { return phoneNumber; }

}
