package ru.springmvc.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springmvc.models.Item;
import ru.springmvc.models.Person;
import ru.springmvc.repositories.ItemsRepository;

@Service
@Transactional(readOnly = true )
public class ItemService {
	private final ItemsRepository itemsRepository;

	@Autowired
	public ItemService(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

	public List<Item> findByItemName(String name) {
		return itemsRepository.findByItemName(name);
	}

	public List<Item> findByOwner(Person person) {
		return itemsRepository.findByOwner(person);
	}

}
