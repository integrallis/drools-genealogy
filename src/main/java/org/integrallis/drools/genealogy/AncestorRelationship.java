package org.integrallis.drools.genealogy;

public class AncestorRelationship {
    private Person descendant;
    private Person ancestor;
    
	public AncestorRelationship(Person descendant, Person ancestor) {
		this.descendant = descendant;
		this.ancestor = ancestor;
	}
	
	public Person getDescendant() {
		return descendant;
	}
	
	public Person getAncestor() {
		return ancestor;
	}
	
	public String toString() {
		return ancestor.getName() + " is an ancestor of " + descendant.getName();
	}
    
    
}
