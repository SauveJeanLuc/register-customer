package management.customermanagement.controllers;

import management.customermanagement.domain.Customer;
import management.customermanagement.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService){
        this.customerService = customerService;
    }
    //Get the list of all available customers at index page
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("customers", customerService.listAllCustomers());
        return "index";
    }
    //Get the list of all available customers at index page
    @RequestMapping("/customers")
    public String getAllCustomers(Model model){
        model.addAttribute("customers", customerService.listAllCustomers());
        return "index";
    }

    //Get An id's properties for editing
    @RequestMapping("/customer/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "add";
    }

    //Create a new Customer
    @RequestMapping("/customer/new")
    public String newCustomer(Model model){
        model.addAttribute("customer", new Customer());
        return "add";
    }

    //Saving or Updating a Customer
    @RequestMapping(value = "/postCustomer", method = RequestMethod.POST)
    public String saveCustomer(Customer customer){
        Customer saveCustomer = customerService.updateCustomer(customer);
        return "redirect:/customers/";
    }

    //Delete a Customer
    @RequestMapping("/customer/delete/{id}")
    public String delete(@PathVariable Integer id){
        customerService.deleteCustomer(id);;
        return "redirect:/customers";
    }

}
