package sg.edu.nus.iss.workshop21.repository;

public interface Queries {
    
    public static final String SQL_SELECT_CUSTOMERS = 
    "select id, company, last_name, first_name, email_address from customers limit ? offset ?";

    public static final String SQL_SELECT_CUSTOMERS_BY_ID = 
    "select id, company, last_name, first_name, email_address from customers where id = ?";

    public static final String SQL_SELECT_ORDER = 
    "select cust.id, cust.first_name, cust.last_name, ord.ship_country_region, ordDeet.product_id, ordDeet.quantity, ordDeet.unit_price from customers as cust join orders as ord on cust.id = ord.customer_id join order_details as ordDeet on ord.id = ordDeet.order_id where cust.id = ?";

    public static final String SQL_COUNT_USERS = 
    "select count(id) from customers";
}
