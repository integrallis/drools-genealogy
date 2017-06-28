package org.integrallis.drools.genealogy;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.integrallis.drools.genealogy.SiblingRelationship.SiblingType;
import org.integrallis.drools.junit.BaseDroolsTestCase;
import org.junit.Test;

public class GenealogyTestCase extends BaseDroolsTestCase {
	
	public GenealogyTestCase() {
		super("ksession-rules");
	}
	
	@Test
	public void testSiblings() {
        Person brian = new Person("Brian");
        Person anne = new Person("Anne");
        Person jessica = new Person("Jessica");
        Person michael = new Person("Michael", anne, brian);
        Person steve = new Person("Steve", jessica, brian);
        Person chuck = new Person("Chuck", anne, brian);
        
        knowledgeSession.insert(brian);
        knowledgeSession.insert(anne);
        knowledgeSession.insert(jessica);
        knowledgeSession.insert(michael);
        knowledgeSession.insert(steve);
        knowledgeSession.insert(chuck);

        knowledgeSession.fireAllRules();
        
		QueryResults results = knowledgeSession.getQueryResults( "getAllRelationships" );
		
		assertEquals(3, results.size());
		
		for ( QueryResultsRow row : results ) {
			SiblingRelationship relationship = (SiblingRelationship) row.get( "relationship" );
        	List<Person> members = relationship.getMembers();
        	assert members.size() == 2; // trivial and stupid!
        	Person sibling1 = members.get(0);
        	Person sibling2 = members.get(1);
        	Person father1 = sibling1.getFather();
        	Person mother1 = sibling1.getMother();
        	Person father2 = sibling2.getFather();
        	Person mother2 = sibling2.getMother();
        	if (relationship.getType() == SiblingType.SIBLING) {
        		assertEquals(father1, father2);
        		assertEquals(mother1, mother2);
        	}
        	else if (relationship.getType() == SiblingType.HALF_SIBLING) {
   		       assertTrue(((mother2 == mother1) && (father2 != father1)) || ((mother2 != mother1) && (father2 == father1)));
        	}
		}
	}
	
	@Test
	public void testAncestry() {
		Person ruby = new Person("Ruby Smith");
		Person roland = new Person("Roland Bodden");
	    Person arlene = new Person("Arlene Bodden", ruby, roland);
	    Person gil = new Person("Gil Sam");
	    Person juanita = new Person("Juanita Domaschko");
	    Person johnGampa = new Person("John Domaschko");
	    Person johnSr = new Person("John S. Domaschko", juanita, johnGampa);
	    Person jane = new Person("Jane Domaschko Vest");
	    Person brian = new Person("Brian Sam-Bodden", arlene, gil);
	    Person anne = new Person("Anne Sam-Bodden", jane, johnSr);
	    Person michael = new Person("Michael Sam-Bodden", anne, brian);
	    Person steve = new Person("Stephen Sam-Bodden", anne, brian);
	    Person johnJr = new Person("John V. Domaschko", jane, johnSr);
	    Person sarah = new Person("Sarah Brown Domaschko");
	    Person lauren = new Person("Lauren Domaschko", sarah, johnJr);
	    Person kaitlyn = new Person("Kaitlyn Domaschko", sarah, johnJr);
	    
	    Person[] everybody = new Person[] { 
            ruby, roland, arlene, gil, juanita, johnGampa, johnSr, jane, 
            brian, anne, michael, steve, johnJr, sarah, lauren, kaitlyn
        };
	    
	    for (Person person : everybody) {
	      	knowledgeSession.insert(person);
		}
	    
        knowledgeSession.fireAllRules();
        
		QueryResults results = knowledgeSession.getQueryResults( "getAllAncestorsFor", new Object[] { michael } );
		
		List<Person> expectedAncestorsForMichael = Arrays.asList(new Person[] { ruby, roland, arlene, gil, juanita, 
				                                              johnGampa, johnSr, jane, brian, anne });
		
		List<Person> calculatedAncestorsForMichael = new ArrayList<Person>();
		
		int numberOfRelationships = 0;
		
		for ( QueryResultsRow row : results ) {
			AncestorRelationship relationship = (AncestorRelationship) row.get( "relationship" );
			calculatedAncestorsForMichael.add(relationship.getAncestor());
			System.out.println(relationship);
			numberOfRelationships++;
		}
		assertEquals(expectedAncestorsForMichael.size(), numberOfRelationships);
		assertTrue(calculatedAncestorsForMichael.containsAll(expectedAncestorsForMichael));
	    
	}
	

}
