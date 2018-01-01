package com.example.shdemo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.shdemo.domain.Customer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Shirt;

@Component
@Transactional
public class SellingMangerHibernateImpl implements SellingManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Long addCustomer(Customer customer) {
		customer.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(customer);
	}
	
	@Override
	public void deleteCustomer(Customer customer) {
		customer = (Customer) sessionFactory.getCurrentSession().get(Customer.class,
				customer.getId());

		for (Shirt shirt : customer.getShirts()) {
			shirt.setSold(false);
			sessionFactory.getCurrentSession().update(shirt);
		}
		sessionFactory.getCurrentSession().delete(customer);
	}

	@Override
	public List<Shirt> getOwnedShirts(Customer customer) {
		customer = (Customer) sessionFactory.getCurrentSession().get(Customer.class, customer.getId());
		List<Shirt> shirts = new ArrayList<Shirt>(customer.getShirts());
		return shirts;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers() {
		return sessionFactory.getCurrentSession().getNamedQuery("customer.all").list();
	}

	@Override
	public Customer findCustomerById(Long id) {
		return (Customer) sessionFactory.getCurrentSession().get(Customer.class, id);
	}

	@Override
	public Long addNewShirt(Shirt shirt) {
		return (Long) sessionFactory.getCurrentSession().save(shirt);
	}

	@Override
	public void sellShirt(Long customerId, Long shirtId) {
		Customer customer = (Customer) sessionFactory.getCurrentSession().get(Customer.class, customerId);
		Shirt shirt = (Shirt) sessionFactory.getCurrentSession().get(Shirt.class, shirtId);
		shirt.setSold(true);
		shirt.setCustomer(customer);
		customer.getShirts().add(shirt);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Shirt> getAvailableShirts() {
		return sessionFactory.getCurrentSession().getNamedQuery("shirt.unsold").list();
	}

	@Override
	public Shirt findShirtById(Long id) {
		return (Shirt) sessionFactory.getCurrentSession().get(Shirt.class, id);
	}
	
	@Override
	public void deleteShirt(Shirt shirt) {
		sessionFactory.getCurrentSession().delete(shirt);
	}

}
