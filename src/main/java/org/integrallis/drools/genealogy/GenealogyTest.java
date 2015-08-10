package org.integrallis.drools.genealogy;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class GenealogyTest {
	
	public static final void main(String[] args) {
	    KieSession knowledgeSession = null;
	    try {
	        // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
		    KieContainer kContainer = ks.getKieClasspathContainer();
		    knowledgeSession = kContainer.newKieSession("ksession-rules");

			// 4 - create and assert some facts
            Person phil = new Person("Phil");
            Person claire = new Person("Anne");
            Person jessica = new Person("Jessica");
            Person michael = new Person("Michael", claire, phil);
            Person steve = new Person("Steve", jessica, phil);
            Person chuck = new Person("Chuck", claire, phil);
            
            knowledgeSession.insert(phil);
            knowledgeSession.insert(claire);
            knowledgeSession.insert(jessica);
            knowledgeSession.insert(michael);
            knowledgeSession.insert(steve);
            knowledgeSession.insert(chuck);
		
			// 5 - fire the rules
			knowledgeSession.fireAllRules();			
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			knowledgeSession.dispose();
		}
	}	    
}
