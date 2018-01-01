package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Customer;
import com.example.shdemo.domain.Shirt;

public interface SellingManager {
	
	Long addCustomer(Customer customer);
	List<Customer> getAllCustomers();
	void deleteCustomer(Customer customer);
	Customer findCustomerById(Long id);

	Long addNewShirt(Shirt shirt);
	List<Shirt> getAvailableShirts();
	Shirt findShirtById(Long id);
	void deleteShirt(Shirt shirt);

	List<Shirt> getOwnedShirts(Customer customer);
	void sellShirt(Long customerId, Long shirtId);

}
