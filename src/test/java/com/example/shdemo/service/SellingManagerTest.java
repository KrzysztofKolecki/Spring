package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.example.shdemo.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Shirt;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SellingManagerTest {

	@Autowired
	SellingManager sellingManager;

	private final String NAME_1 = "Nike air";
	private final String COLOR_1 = "black";
	private final String SIZE_1 = "L";
	
	private final String NAME_2 = "Patagonia";
	private final String COLOR_2 = "black";
	private final String SIZE_2 = "XL";

	private final String CUSTOMER_NAME = "Adam";
	private final String CUSTOMER_SURNAME = "Kowal";

	@Test
	public void addCustomerCheck() {

		Customer customer = new Customer();
		customer.setName(CUSTOMER_NAME);
		customer.setSurname(CUSTOMER_SURNAME);

		Long customerId = sellingManager.addCustomer(customer);

		Customer retrievedCustomer = sellingManager.findCustomerById(customerId);
		assertEquals(CUSTOMER_NAME, retrievedCustomer.getName());
		assertEquals(CUSTOMER_SURNAME, retrievedCustomer.getSurname());
		
		sellingManager.deleteCustomer(customer);
		
		retrievedCustomer = sellingManager.findCustomerById(customerId);
		assertEquals(null, retrievedCustomer);

	}

	@Test
	public void addShirtCheck() {

		Shirt shirt = new Shirt();
		shirt.setName(NAME_1);
		shirt.setColor(COLOR_1);
		shirt.setSize(SIZE_1);

		Long shirtId = sellingManager.addNewShirt(shirt);

		Shirt retrievedShirt = sellingManager.findShirtById(shirtId);
		assertEquals(NAME_1, retrievedShirt.getName());
		assertEquals(COLOR_1, retrievedShirt.getColor());
		assertEquals(SIZE_1, retrievedShirt.getSize());

		sellingManager.deleteShirt(shirt);
		
		Shirt retrievedShirt2 = sellingManager.findShirtById(shirtId);
		assertEquals(null, retrievedShirt2);
		
	}
	
	@Test
	public void sellShirtCheck() {

		Customer customer = new Customer();
		customer.setName(CUSTOMER_NAME);
		customer.setSurname(CUSTOMER_SURNAME);
		Long customerId = sellingManager.addCustomer(customer);
		
		Shirt shirt = new Shirt();
		shirt.setName(NAME_2);
		shirt.setColor(COLOR_2);
		shirt.setSize(SIZE_2);
		Long shirtId = sellingManager.addNewShirt(shirt);

		sellingManager.sellShirt(customerId, shirtId);
		
		Customer retrievedCustomer = sellingManager.findCustomerById(customerId);
		List<Shirt> ownedShirts = sellingManager.getOwnedShirts(retrievedCustomer);
		
	
		assertEquals(1, ownedShirts.size());
		assertEquals(NAME_2, ownedShirts.get(0).getName());
		assertEquals(COLOR_2, ownedShirts.get(0).getColor());
		assertEquals(SIZE_2, ownedShirts.get(0).getSize());
		assertEquals(customer, ownedShirts.get(0).getCustomer());
	}

}
