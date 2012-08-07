package org.integrallis.drools.genealogy;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * This is a sample file to launch a rule package from a rule source file.
 */
public class GenealogyTest {
	
	public static final void main(String[] args) {
		try {
			// 1 - load the rules in to a knowledge builder
			KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			knowledgeBuilder.add(ResourceFactory.newClassPathResource("genealogy.drl"), ResourceType.DRL);
			KnowledgeBuilderErrors errors = knowledgeBuilder.getErrors();
			if (errors.size() > 0) {
				for (KnowledgeBuilderError error: errors) {
					System.err.println(error);
				}
				throw new IllegalArgumentException("Could not parse knowledge.");
			}
			// 2 - create a knowledge base using a knowledge builder
			KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
			knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
			
			// 3 - create a stateful knowledge session
			StatefulKnowledgeSession knowledgeSession = knowledgeBase.newStatefulKnowledgeSession();
			
			// create a logger for the knowledge session
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(knowledgeSession, "test");
			
			// 4 - create and assert some facts
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
		
			// 5 - fire the rules
			knowledgeSession.fireAllRules();
			
			logger.close();
			knowledgeSession.dispose();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}	    
}
