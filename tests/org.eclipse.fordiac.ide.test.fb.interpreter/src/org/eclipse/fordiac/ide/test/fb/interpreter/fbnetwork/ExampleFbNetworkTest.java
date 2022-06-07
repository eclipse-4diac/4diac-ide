package org.eclipse.fordiac.ide.test.fb.interpreter.fbnetwork;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;
import org.junit.Test;

/** small fb network consisting of e_sr, e_switch, and e_ctud */
public class ExampleFbNetworkTest extends AbstractInterpreterTest {

//	@Override
//	public void test() {
//		final FBNetwork network = loadFbNetwork("ExampleFbNetwork", "ExampleFbNetwork"); //$NON-NLS-1$ //$NON-NLS-2$
//		assertNotNull(network);
//
//		EList<Transaction> returnedTrasactions =
//				runFBNetworkTest(network, (Event) network.getFBNamed("E_SR").getInterfaceElement("S")); //$NON-NLS-1$ //$NON-NLS-2$
//		
//		System.out.println("Check each transaction and EventOcurrencce");
//	}
	
	@Override
	public void test() {
		final FBNetwork network = loadFbNetwork("ExampleFbNetwork", "ExampleFbNetwork"); //$NON-NLS-1$ //$NON-NLS-2$
		assertNotNull(network);

		EList<Transaction> returnedTrasactions =
				runFBNetworkTest(network, (Event) network.getFBNamed("E_SR").getInterfaceElement("R")); //$NON-NLS-1$ //$NON-NLS-2$
		
		System.out.println("Check each transaction and EventOcurrencce");
	}
}
