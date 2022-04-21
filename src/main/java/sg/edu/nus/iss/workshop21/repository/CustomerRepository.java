package sg.edu.nus.iss.workshop21.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.workshop21.models.Customer;
import static sg.edu.nus.iss.workshop21.repository.Queries.*;

@Repository
public class CustomerRepository {
    
    @Autowired
    private JdbcTemplate template;

    public List<Customer> getCustomers(Integer limit, Integer offset) {
        List<Customer> customerList = new LinkedList<>();
        
        final SqlRowSet rs = template.queryForRowSet(
            SQL_SELECT_CUSTOMERS, limit, offset);

        while (rs.next()) {
            Customer customer = new Customer();
            customer = Customer.convert(rs);
            customerList.add(customer);
        }

        return customerList;
    }

    public Optional<Customer> getCustById(Integer customerId) {
        
        final SqlRowSet rs = template.queryForRowSet(
            SQL_SELECT_CUSTOMERS_BY_ID, customerId);

        if (!rs.next()) {
            return Optional.empty();
        }

        Customer customer = new Customer();
        customer = Customer.convert(rs);
        return Optional.of(customer);
    }

    public Optional<List<Customer>> getOrder(Integer customerId) {
        
        List<Customer> custListOrder = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ORDER, customerId);

        if (!rs.next()) {
            return Optional.empty();
        }

        rs.beforeFirst();
        while (rs.next()) {
            Customer customer = new Customer();
            customer.setCustomerId(rs.getInt("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setShipCountry(rs.getString("ship_country_region"));
            customer.setProductId(Integer.valueOf(rs.getString("product_id")));
            customer.setQuantity(Double.valueOf(rs.getString("quantity")));
            customer.setUnitPrice(Double.valueOf(rs.getString("unit_price")));
            custListOrder.add(customer);
        }
        return Optional.of(custListOrder);

    }

    public Integer getNumofCustomers() {
        SqlRowSet rs = template.queryForRowSet(SQL_COUNT_USERS);
        rs.next();
        Integer count = rs.getInt("count(id)");
        return count;

    }



}
