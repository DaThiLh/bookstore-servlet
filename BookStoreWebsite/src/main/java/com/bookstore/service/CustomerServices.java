package com.bookstore.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bookstore.dao.CustomerDAO;
import com.bookstore.entity.Customer;

public class CustomerServices {
  private CustomerDAO customerDAO;
  private HttpServletRequest request;
  private HttpServletResponse response;

  public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
    super();
    this.request = request;
    this.response = response;
    this.customerDAO = new CustomerDAO();
  }

  public void listCustomers(String message) throws ServletException, IOException {
    List<Customer> listCustomers = customerDAO.listAll();

    if (message != null) {
      request.setAttribute("message", message);
    }

    request.setAttribute("listCustomers", listCustomers);

    String listPage = "customer_list.jsp";
    RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
    requestDispatcher.forward(request, response);
  }

  public void listCustomers() throws ServletException, IOException {
    listCustomers(null);
  }

  public void createCustomer() throws ServletException, IOException {
    String email = request.getParameter("email");
    Customer existCustomer = customerDAO.findByEmail(email);

    // Find customer who is already signing this email
    if (existCustomer != null) {
      String message = "Could not create new customer, this email " + email
          + " is already registered by another customer.";
      listCustomers(message);

    } else {
      // Otherwise, Retrieve the information of the new customer
      String fullName = request.getParameter("fullname");
      String password = request.getParameter("password");
      String phone = request.getParameter("phone");
      String address = request.getParameter("address");
      String city = request.getParameter("city");
      String zipCode = request.getParameter("zipcode");
      String country = request.getParameter("country");

      // Set this value to the properties of the Customer object
      Customer newCustomer = new Customer();
      newCustomer.setEmail(email);
      newCustomer.setFullname(fullName);
      newCustomer.setPassword(password);
      newCustomer.setPhone(phone);
      newCustomer.setAddress(address);
      newCustomer.setCity(city);
      newCustomer.setZipcode(zipCode);
      newCustomer.setCountry(country);

      customerDAO.create(newCustomer);

      String message = "New customer has been created successfully.";
      listCustomers(message);
    }
  }

  public void editCustomer() throws ServletException, IOException {
    // Get parameters from request
    Integer customerId = Integer.parseInt(request.getParameter("id"));
    Customer customer = customerDAO.get(customerId);

    /* Display an error message when the admin attempts to edit a customer 
       which has been deleted by another admin */
    if (customer == null) {
      String message = "Could not find customer with ID " + customerId;
      CommonUtility.showMessageBackend(message, request, response);
    } else {
      request.setAttribute("customer", customer);
      CommonUtility.forwardToPage("customer_form.jsp", request, response);
    }
  }

  public void updateCustomer() throws ServletException, IOException {
    // Get parameters from existing customers
    Integer customerId = Integer.parseInt(request.getParameter("customerId"));
    String email = request.getParameter("email");

    Customer customerByEmail = customerDAO.findByEmail(email);
    String message = null;

    // Check if existing customers or not have the same ID as the existing ones
    if (customerByEmail != null && customerByEmail.getCustomerId() != customerId) {
      message = "Could not update the customer ID " + customerId
          + " because there's an existing customer having the same email.";
    } else {
      // Otherwise, Retrieve the edit information to update this customer
      String fullName = request.getParameter("fullname");
      String password = request.getParameter("password");
      String phone = request.getParameter("phone");
      String address = request.getParameter("address");
      String city = request.getParameter("city");
      String zipCode = request.getParameter("zipcode");
      String country = request.getParameter("country");

      // Set this value to the properties of the Customer object
      Customer customerById = customerDAO.get(customerId);
      customerById.setEmail(email);
      customerById.setFullname(fullName);
      customerById.setPassword(password);
      customerById.setPhone(phone);
      customerById.setAddress(address);
      customerById.setCity(city);
      customerById.setZipcode(zipCode);
      customerById.setCountry(country);

      customerDAO.update(customerById);

      message = "The customer has been updated succesfully.";
    }

    listCustomers(message);
  }

  public void deleteCustomer() throws ServletException, IOException {
    // Get parameters from request
    Integer customerId = Integer.parseInt(request.getParameter("id"));
    Customer customer = customerDAO.get(customerId);
        
    // Delete existed customer or display error if non-exist customer
    if (customer != null) {
        customerDAO.delete(customerId);
        
        String message = "The customer has been deleted successfully.";
        listCustomers(message);         
    } else {
        String message = "Could not find customer with ID " + customerId + ", "
                + "or it has been deleted by another admin";
        CommonUtility.showMessageBackend(message, request, response);
    }
  }
}