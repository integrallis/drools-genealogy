package org.integrallis.drools.genealogy;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private Person mother;
    private Person father;
    private List<Person> parents = new ArrayList<Person>();
    
    public Person(String name) {
    	this.name = name;
    }
    
	public Person(String name, Person mother, Person father) {
		this.name = name;
		this.mother = mother;
		this.father = father;
		parents.add(mother);
		parents.add(father);
	}
    
	public String getName() {
		return name;
	}
	public Person getMother() {
		return mother;
	}
	public Person getFather() {
		return father;
	}

	public List<Person> getParents() {
		return parents;
	}
	
	public boolean equals(Object object) {
		// short circuits
		if (object == null)
			return false;
		if (this == object)
			return true;
		if (!(object instanceof Person))
			return false;
		final Person person = (Person) object;		
		return (this.name.equals(person.getName()));
	}
	
}
