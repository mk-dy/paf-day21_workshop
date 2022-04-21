package sg.edu.nus.iss.workshop21.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Customer {
    
    private Integer customerId;
    private String company;
    private String lastName;
    private String firstName;
    private String emailAddress;

    private String shipCountry;
    
    private Integer productId;
    private Double quantity;
    private Double unitPrice;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public static Customer convert(SqlRowSet rs) {
        Customer cust = new Customer();
        cust.setCustomerId(rs.getInt("id"));
        cust.setCompany(rs.getString("company"));
        cust.setLastName(rs.getString("last_name"));
        cust.setFirstName(rs.getString("first_name"));
        cust.setEmailAddress(rs.getString("email_address"));

        return cust;

    }
}
