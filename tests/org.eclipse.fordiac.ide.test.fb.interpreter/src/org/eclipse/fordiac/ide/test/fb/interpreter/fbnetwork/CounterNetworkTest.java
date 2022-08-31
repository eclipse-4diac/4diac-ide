package org.eclipse.fordiac.ide.test.fb.interpreter.fbnetwork;

import static org.eclipse.fordiac.ide.fb.interpreter.mm.utils.FBNetworkTestRunner.runFBNetworkTest;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

/** small fb network consisting of e_sr, e_switch, and e_ctud */
public class CounterNetworkTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final FBNetwork network = loadFbNetwork("ExampleFbNetwork", "CounterNetwork"); //$NON-NLS-1$ //$NON-NLS-2$
		assertNotNull(network);

		final EList<Transaction> returnedTransactions = runFBNetworkTest(network, "E_SPLIT", "EI"); //$NON-NLS-1$ //$NON-NLS-2$

		final Transaction finalResult = returnedTransactions.get(returnedTransactions.size() - 1);
		assertTrue(finalResult.getInputEventOccurrence().getEvent().getName().equals("CU"));//$NON-NLS-1$
		final VarDeclaration quPin = (VarDeclaration) finalResult.getInputEventOccurrence().getParentFB()
				.getInterfaceElement("QU");
		assertTrue(quPin.getValue().getValue().equals("TRUE")); //$NON-NLS-1$
		final VarDeclaration cvPin = (VarDeclaration) finalResult.getInputEventOccurrence().getParentFB()
				.getInterfaceElement("CV");
		assertTrue(cvPin.getValue().getValue().equals("1")); //$NON-NLS-1$

	}
}
