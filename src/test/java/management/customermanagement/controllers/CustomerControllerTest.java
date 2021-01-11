package management.customermanagement.controllers;

import management.customermanagement.domain.Customer;
import management.customermanagement.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest {

    @Mock // A Mockito Mock object
    private CustomerService customerService;

    @InjectMocks //This setups up controller, and then injects mock object into it.
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this); // This initializes controller and mocks

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testIndex() throws Exception{
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"));
    }

    @Test
    public void testGetAllCustomers() throws Exception{
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        //Mockito interaction, for returning list of products
        when(customerService.listAllCustomers()).thenReturn( (List) customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("customers", hasSize(2)));
    }

    @Test
    public void testEdit() throws Exception{
        Integer id = 1;

        //Mockito stub to return new product for ID 1
        when(customerService.getCustomerById(id)).thenReturn( new Customer());

        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }
    @Test
    public void testNewCustomers() throws Exception{

        // The Controller should not call any Service
        verifyNoInteractions(customerService);

        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testSaveCustomer() throws Exception{
        Integer customer_id = 1;
        String firstName = "Sauve";
        String lastName = "Jean-Luc";
        String email = "sauvejeanluc3@gmail.com";
        String phoneNumber = "+250788531231";
        String addressLineOne ="1256-23";
        String addressLineTwo ="KK St 123";
        String city = "Kigali";
        String state = "New york";
        String zipCode = "871929";

        Customer returnCustomer = new Customer();
        returnCustomer.setCustomer_id(customer_id);
        returnCustomer.setFirstName(firstName);
        returnCustomer.setLastName(lastName);
        returnCustomer.setEmail(email);
        returnCustomer.setPhoneNumber(phoneNumber);
        returnCustomer.setAddressLineOne(addressLineOne);
        returnCustomer.setAddressLineTwo(addressLineTwo);
        returnCustomer.setCity(city);
        returnCustomer.setState(state);
        returnCustomer.setZipCode(zipCode);

        when(customerService.updateCustomer(org.mockito.ArgumentMatchers.<Customer>any())).thenReturn(returnCustomer);

        //Should you have some time, work on the rest of properties too.
        mockMvc.perform(
                post("/postCustomer")
                .param("customer_id","1")
                .param("firstName", firstName)
                .param("lastName",lastName)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andExpect(model().attribute("customer", hasProperty("customer_id", is(customer_id))))
                .andExpect(model().attribute("customer", hasProperty("firstName", is(firstName))))
                .andExpect(model().attribute("customer", hasProperty("lastName", is(lastName))));

        //Verify Properties of bound object
        ArgumentCaptor<Customer> boundCustomer = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).updateCustomer(boundCustomer.capture());

        assertEquals(customer_id, boundCustomer.getValue().getCustomer_id());
        assertEquals(firstName, boundCustomer.getValue().getFirstName());
        assertEquals(lastName, boundCustomer.getValue().getLastName());


    }

    @Test
    public void testDelete() throws Exception{
        Integer id = 1;

        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers"));

        verify(customerService, times(1)).deleteCustomer(1);
    }

}
