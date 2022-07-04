package org.eclipse.fordiac.ide.test.fb.interpreter.fbnetwork;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;
import org.junit.Test;

/** small fb network consisting of e_sr, e_switch, and e_ctud */
public class SimpleEventConnectionTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		
		final FBNetwork network = loadFbNetwork("ReferenceExamples", "ReferenceExamples", "EventConnections"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertNotNull(network);

		SubApp subApp = network.getSubAppNamed("Ex1a");
		
		EList<Transaction> returnedTrasactions =
				runFBNetworkTest(subApp.getSubAppNetwork(), (Event) subApp.getSubAppNetwork().getFBNamed("E_SPLIT").getInterfaceElement("EI")); //$NON-NLS-1$ //$NON-NLS-2$
		
		
		assertTrue(returnedTrasactions.size() == 4);
		assertTrue(returnedTrasactions.get(0).getInputEventOccurrence().getEvent().getName().equals("S")); //$NON-NLS-1$
		assertTrue(returnedTrasactions.get(0).getInputEventOccurrence().getParentFB().getName().equals("E_SR")); //$NON-NLS-1$
		
		assertTrue(returnedTrasactions.get(0).getInputEventOccurrence().getParentFB().getInterfaceElement("Q") instanceof VarDeclaration);
		
		assertTrue(((VarDeclaration)returnedTrasactions.get(0).getInputEventOccurrence().getParentFB().getInterfaceElement("Q"))
				.getValue().getValue().equals("TRUE"));
		
		assertTrue(((FBTransaction)returnedTrasactions.get(1))
				.getOutputEventOccurrences().get(0).getEvent().getName().equals("EO1"));
		
		assertTrue(returnedTrasactions.get(1).getInputEventOccurrence().getEvent().getName().equals("EI")); //$NON-NLS-1$
				
		assertTrue(returnedTrasactions.get(2).getInputEventOccurrence().getEvent().getName().equals("CD")); //$NON-NLS-1$
		
		assertTrue(((FBTransaction)returnedTrasactions.get(2))
				.getOutputEventOccurrences().isEmpty());
		
		System.out.println("Check each transaction and EventOcurrencce");
	}
	
//	@Override
//	public void test() {
//		final FBNetwork network = loadFbNetwork("ExampleFbNetwork", "ExampleFbNetwork"); //$NON-NLS-1$ //$NON-NLS-2$
//		assertNotNull(network);
//
//		EList<Transaction> returnedTrasactions =
//				runFBNetworkTest(network, (Event) network.getFBNamed("E_SR").getInterfaceElement("R")); //$NON-NLS-1$ //$NON-NLS-2$
//		
//		System.out.println("Check each transaction and EventOcurrencce");
//	}
}
