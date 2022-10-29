package ru.springmvc.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person owner;

	@Column(name = "item_name")
	private Long itemName;

	public Item() {}

	public Item(Long itemName) {
		this.itemName = itemName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public Long getItemName() {
		return itemName;
	}

	public void setItemName(Long itemName) {
		this.itemName = itemName;
	}

}
