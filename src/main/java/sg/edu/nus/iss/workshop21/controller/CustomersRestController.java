package sg.edu.nus.iss.workshop21.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.workshop21.models.Customer;
import sg.edu.nus.iss.workshop21.service.CustomerService;

@RestController
@RequestMapping(path="/api/customers")
public class CustomersRestController {
    
    @Autowired
    private CustomerService customerSvc;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomers() {
        
        JsonObject resp;
        JsonObjectBuilder mainBuilder = Json.createObjectBuilder();
        JsonArrayBuilder custInfoArrayBuilder = Json.createArrayBuilder();

        // generate our list of customers then we iterate and slowly build into jsonobject
        List<Customer> custList = customerSvc.showCustomers();
        System.out.println(">>>>>>>>> check: " + custList);

        for (Customer customer: custList) {
            custInfoArrayBuilder.add(Json.createObjectBuilder()
                    .add("customer id", customer.getCustomerId())
                    .add("first name",customer.getFirstName())
                    .add("last name",customer.getLastName())
                    .add("company",customer.getCompany())
                );
                    // .add("email",customer.getEmailAddress());
                    // issue arises when jsonobject contains null i.e. email address
                    //java.lang.NullPointerException: Value in JsonObjects name/value pair cannot be null    
        }
        mainBuilder.add("customers", custInfoArrayBuilder);
        resp = mainBuilder.build(); 
        
        return ResponseEntity.ok(resp.toString());
    }

    @GetMapping(path="/{customer_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomersById(@PathVariable String customer_id) {

        Optional<Customer> custOpt = customerSvc.showCustById(Integer.valueOf(customer_id));

        JsonObject resp;
        JsonObjectBuilder mainBuilder = Json.createObjectBuilder();

        if (custOpt.isEmpty()) {
            mainBuilder.add("error","User not found");
            resp= mainBuilder.build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp.toString());
        }
        Customer customer = custOpt.get();

        mainBuilder.add("customer id",customer.getCustomerId())
                    .add("first name",customer.getFirstName())
                    .add("last name",customer.getLastName())
                    .add("company",customer.getCompany());
        
        resp = mainBuilder.build();

        return ResponseEntity.ok(resp.toString());
    }

    @GetMapping(path="/{customer_id}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustOrders(@PathVariable String customer_id) {
        
        Optional<List<Customer>> custOpt = customerSvc.showCustOrder(Integer.valueOf(customer_id));

        JsonObject resp;
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        JsonObjectBuilder subBuild = Json.createObjectBuilder();

        // if customer id > the count from sql, return message
        if (Integer.valueOf(customer_id) > customerSvc.getNumofCustomer()) {
            resp = builder.add("Error", "User Not Found").build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp.toString());
        }        
        
        if (custOpt.isEmpty()) {
            builder.add("",arrBuilder);
            resp = builder.build();
            return ResponseEntity.ok(resp.toString());
        }

        List<Customer> custList = custOpt.get();
        for (Customer cust: custList) {
                arrBuilder.add(subBuild
                    .add("first name",cust.getFirstName())
                    .add("last name",cust.getLastName())
                    .add("country of shipment", cust.getShipCountry())
                    .add("product id",cust.getProductId())
                    .add("quantity",cust.getQuantity())
                    .add("unit price", cust.getUnitPrice()));
        }
        resp = builder.add("Orders Made by Customer Id: " + customer_id ,arrBuilder).build();

        return ResponseEntity.ok(resp.toString());
    }


    
}
