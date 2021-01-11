package management.customermanagement.services;

import management.customermanagement.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService{
    private Map<Integer, Customer> customers;

    //Constructor
    public CustomerServiceImpl(){
        loadCustomers();
    }

    @Override
    public List<Customer> listAllCustomers() {
        return  new ArrayList<>(customers.values());
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customers.get(id);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        if(customer != null){
            if (customer.getCustomer_id() == null){
                customer.setCustomer_id(getNextKey());
            }
            customers.put(customer.getCustomer_id(), customer);
            return customer;
        }
        else {
            throw new RuntimeException("Customer can't be null");
        }
    }

    @Override
    public void deleteCustomer(Integer id) {
            customers.remove(id);
    }
    private int getNextKey(){
        if(customers.keySet()==null){
            return 1;
        }
        else {
            return Collections.max(customers.keySet()) + 1;
        }
    }

    public void loadCustomers(){
        customers = new HashMap<>();

        Customer customer1 = new Customer();
        customer1.setCustomer_id(1);
        customer1.setAddressLineOne("KKS 123");
        customer1.setAddressLineTwo("ST 23");
        customer1.setCity("Kigali");
        customer1.setEmail("sauvejeanluc3@gmail.com");
        customer1.setFirstName("SAUVE");
        customer1.setLastName("Jean-Luc");
        customer1.setState("Gasogi");
        customer1.setZipCode("456-678");

        customers.put(1,customer1);
//        customers.put(2,customer1);
    }
}
