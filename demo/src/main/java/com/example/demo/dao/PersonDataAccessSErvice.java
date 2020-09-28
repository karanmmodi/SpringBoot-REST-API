package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

@Repository("fakeDao")
public class PersonDataAccessSErvice implements PersonDao {

	private static List<Person> DB = new ArrayList<Person>();

	@Override
	public int insertPerson(UUID id, Person person) {
		DB.add(new Person(id, person.getName()));
		return 1;
	}

	@Override
	public List<Person> selectAllPeople() {
		return DB;
	}

	@Override
	public Optional<Person> getPersonById(UUID id) {
		return DB.stream()
				.filter(Person -> Person.getId().equals(id)).findFirst();
	}

	@Override
	public int updatePerson(UUID id, Person update) {
		return getPersonById(id)
				.map(person ->{
					int indexOfPersonToDelete = DB.indexOf(person);
					if(indexOfPersonToDelete>=0) {
						DB.set(indexOfPersonToDelete, new Person(id, update.getName()));
						return 1;
					}
					return 0;
				}).orElse(0);
	}

	@Override
	public int deletePersonByID(UUID id) {
		Optional<Person> personOptional=getPersonById(id);
		if(personOptional.isEmpty()) {
			return 0;
		}
		DB.remove(personOptional.get());
		return 1;
	}

}
