/*
 * Copyright (C) 2021 jlgranda
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.jlgranda.rules;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.jlgranda.fede.model.accounting.CashBoxPartial;
import org.jlgranda.fede.model.accounting.Record;
import org.jlgranda.fede.model.accounting.RecordTemplate;
import org.jlgranda.fede.model.document.FacturaElectronica;
import org.jlgranda.fede.model.sales.Invoice;
import org.jlgranda.fede.model.sales.Payment;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

/**
 *
 * @author jlgranda
 */
public class RuleRunner {
    
    /*@Inject
    @KSession
    KieSession session;
    
    @Inject
    KieBuilder kbuilder;*/
    
    private static KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    private static Collection<KiePackage> pkgs;
    private static InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    private static KieServices kieServices = KieServices.Factory.get();
    private static StatelessKieSession ksession;
    
    public KnowledgeBuilderErrors run(RecordTemplate recordTemplate, Invoice invoice, Record record) {
        
        // read second rule from String
        String myRule = recordTemplate.getRule();
        Resource myResource = ResourceFactory.newReaderResource((Reader) new StringReader(myRule));
        kbuilder.add(myResource, ResourceType.DRL);
 
        // Check the builder for errors
        if ( kbuilder.hasErrors() ) {
//            ksession.getKieBase().removeRule(recordTemplate.getName(), recordTemplate.getRule());
            return kbuilder.getErrors();
        }
 
        // get the compiled packages (which are serializable)
        pkgs = kbuilder.getKnowledgePackages();
 
        // add the packages to a knowledgebase (deploy the knowledge packages).
        kbase.addPackages( pkgs );
 
        ksession = kbase.newStatelessKieSession();
        
        KieCommands kieCommands = kieServices.getCommands();
                
        List<Command> cmds = new ArrayList<>();
        cmds.add( kieCommands.newSetGlobal( "record", record, true ) );
        cmds.add( kieCommands.newInsert( invoice, "invoice" ) );
        //cmds.add( kieCommands.newQuery( "Get People" "getPeople" );
        ksession.execute( kieCommands.newBatchExecution( cmds ));
        //ExecutionResults results = ksession.execute( kieCommands.newBatchExecution( cmds ));
        //results.getValue( "list1" ); // returns the ArrayList
        //record = (Record) results.getValue( "record" ); // returns the inserted fact Person
        //results.getValue( "Get People" );// returns the query as a QueryResults instance.
        return null; //No hay errores de compilación
    }
    
    public KnowledgeBuilderErrors run(RecordTemplate recordTemplate, FacturaElectronica facturaElectronica, Record record) {
        
        // read second rule from String
        String myRule = recordTemplate.getRule();
        Resource myResource = ResourceFactory.newReaderResource((Reader) new StringReader(myRule));
        kbuilder.add(myResource, ResourceType.DRL);
 
        // Check the builder for errors
        if ( kbuilder.hasErrors() ) {
//            ksession.getKieBase().removeRule(recordTemplate.getName(), recordTemplate.getRule());
            return kbuilder.getErrors();
        }
 
        // get the compiled packages (which are serializable)
        pkgs = kbuilder.getKnowledgePackages();
 
        // add the packages to a knowledgebase (deploy the knowledge packages).
        kbase.addPackages( pkgs );
 
        ksession = kbase.newStatelessKieSession();
        
        KieCommands kieCommands = kieServices.getCommands();
                
        List<Command> cmds = new ArrayList<>();
        cmds.add( kieCommands.newSetGlobal( "record", record, true ) );
        cmds.add( kieCommands.newInsert( facturaElectronica, "facturaElectronica" ) );
        //cmds.add( kieCommands.newQuery( "Get People" "getPeople" );
        ksession.execute( kieCommands.newBatchExecution( cmds ));
        //ExecutionResults results = ksession.execute( kieCommands.newBatchExecution( cmds ));
        //results.getValue( "list1" ); // returns the ArrayList
        //record = (Record) results.getValue( "record" ); // returns the inserted fact Person
        //results.getValue( "Get People" );// returns the query as a QueryResults instance.
        return null; //No hay errores de compilación
    }
    
    public KnowledgeBuilderErrors run(RecordTemplate recordTemplate, Payment payment, Record record) {
        
        // read second rule from String
        String myRule = recordTemplate.getRule();
        Resource myResource = ResourceFactory.newReaderResource((Reader) new StringReader(myRule));
        kbuilder.add(myResource, ResourceType.DRL);
 
        // Check the builder for errors
        if ( kbuilder.hasErrors() ) {
//            ksession.getKieBase().removeRule(recordTemplate.getName(), recordTemplate.getRule());
            return kbuilder.getErrors();
        }
 
        // get the compiled packages (which are serializable)
        pkgs = kbuilder.getKnowledgePackages();
 
        // add the packages to a knowledgebase (deploy the knowledge packages).
        kbase.addPackages( pkgs );
 
        ksession = kbase.newStatelessKieSession();
        
        KieCommands kieCommands = kieServices.getCommands();
                
        List<Command> cmds = new ArrayList<>();
        cmds.add( kieCommands.newSetGlobal( "record", record, true ) );
        cmds.add( kieCommands.newInsert( payment, "payment" ) );
        //cmds.add( kieCommands.newQuery( "Get People" "getPeople" );
        ksession.execute( kieCommands.newBatchExecution( cmds ));
        //ExecutionResults results = ksession.execute( kieCommands.newBatchExecution( cmds ));
        //results.getValue( "list1" ); // returns the ArrayList
        //record = (Record) results.getValue( "record" ); // returns the inserted fact Person
        //results.getValue( "Get People" );// returns the query as a QueryResults instance.
        return null; //No hay errores de compilación
    }
    
    public KnowledgeBuilderErrors run(RecordTemplate recordTemplate, CashBoxPartial cashBoxPartial, Record record) {
        
        // read second rule from String
        String myRule = recordTemplate.getRule();
        Resource myResource = ResourceFactory.newReaderResource((Reader) new StringReader(myRule));
        kbuilder.add(myResource, ResourceType.DRL);
 
        // Check the builder for errors
        if ( kbuilder.hasErrors() ) {
            //ksession.getKieBase().removeRule(recordTemplate.getName(), recordTemplate.getRule());
            return kbuilder.getErrors();
        }
 
        // get the compiled packages (which are serializable)
        pkgs = kbuilder.getKnowledgePackages();
 
        // add the packages to a knowledgebase (deploy the knowledge packages).
        kbase.addPackages( pkgs );
 
        ksession = kbase.newStatelessKieSession();
        
        KieCommands kieCommands = kieServices.getCommands();
                
        List<Command> cmds = new ArrayList<>();
        cmds.add( kieCommands.newSetGlobal( "record", record, true ) );
        cmds.add( kieCommands.newInsert( cashBoxPartial, "cashBoxPartial" ) );
        //cmds.add( kieCommands.newQuery( "Get People" "getPeople" );
        ksession.execute( kieCommands.newBatchExecution( cmds ));
        //ExecutionResults results = ksession.execute( kieCommands.newBatchExecution( cmds ));
        //results.getValue( "list1" ); // returns the ArrayList
        //record = (Record) results.getValue( "record" ); // returns the inserted fact Person
        //results.getValue( "Get People" );// returns the query as a QueryResults instance.
        return null; //No hay errores de compilación
    }
}
