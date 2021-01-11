package management.customermanagement.services;

import management.customermanagement.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface CustomerService {

    List<Customer> listAllCustomers();
    Customer getCustomerById(Integer id);
    Customer updateCustomer(Customer customer);
    void deleteCustomer(Integer id);

}
