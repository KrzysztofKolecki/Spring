package com.example.shdemo.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
	@NamedQuery(name = "customer.all", query = "Select c from Customer c"),
})
public class Customer {

	private Long id;

	private String name;
	private String surname;

	private List<Shirt> shirts = new ArrayList<Shirt>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Shirt> getShirts() {
		return shirts;
	}
	public void setShirts(List<Shirt> shirts) {
		this.shirts = shirts;
	}
}
