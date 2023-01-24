package com.paula.os.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Technic extends Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@OneToMany(mappedBy = "technic")
	private List<OS> list = new ArrayList<>();

	public Technic() {
		super();
	}

	public Technic(Integer id, String name, String cpf, String phone) {
		super(id, name, cpf, phone);
	}

	public List<OS> getList() {
		return list;
	}

	public void setList(List<OS> list) {
		this.list = list;
	}

}
