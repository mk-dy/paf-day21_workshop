package sg.edu.nus.iss.workshop21.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.workshop21.models.Customer;
import sg.edu.nus.iss.workshop21.repository.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepo;

    public List<Customer> showCustomers() {
        return showCustomers(5, 0);
    }  

    public List<Customer> showCustomers(Integer limit) {
        return showCustomers(limit, 0);
    }

    public List<Customer> showCustomers(Integer limit, Integer offset) {
        return customerRepo.getCustomers(limit, offset);
    }

    public Optional<Customer> showCustById(Integer customerId) {
        return customerRepo.getCustById(customerId);
    }

    public Optional<List<Customer>> showCustOrder(Integer customerId) {
        return customerRepo.getOrder(customerId);
    }

    public Integer getNumofCustomer() {
        return customerRepo.getNumofCustomers();
    }
}   
