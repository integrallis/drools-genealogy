package org.integrallis.drools.genealogy;

import java.util.ArrayList;
import java.util.List;

public class SiblingRelationship {
    public static enum SiblingType { 
    	SIBLING("Siblings"), HALF_SIBLING("Half Siblings"); 
    	private SiblingType(String label) { this.label = label; }
    	private String label;
    	public String toString() { return label; }
    };
    
    private List<Person> members = new ArrayList<Person>();
    private SiblingType type;
    private Person person1;
    private Person person2;
    
    public SiblingRelationship(Person sibling1, Person sibling2, SiblingType type) {
    	members.add(sibling1);
    	members.add(sibling2);
    	person1 = sibling1;
    	person2 = sibling2;
    	this.type = type;
    }
    
    public List<Person> getMembers() {
    	return members;
    }

	public SiblingType getType() {
		return type;
	}
	
	public void printIt() {
		System.out.println(person1.getName() + " and " + person2.getName() + " are " + type);
	}
	
}
