/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.basicfb;

import static org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory.addTransaction;
import static org.eclipse.fordiac.ide.fb.interpreter.mm.utils.VariableUtils.setVariable;
import static org.junit.Assert.assertNotNull;

import org.eclipse.fordiac.ide.fb.interpreter.api.FBTransactionBuilder;
import org.eclipse.fordiac.ide.fb.interpreter.api.ServiceFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

public class EventSwitchTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final BasicFBType fb = (BasicFBType) loadFBType("E_SWITCH"); //$NON-NLS-1$
		assertNotNull(fb);
		fb.setService(ServiceFactory.createDefaultServiceModel());
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "G", "TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("EI", "EO1")); //$NON-NLS-1$ //$NON-NLS-2$

		runFBTest(fb, seq);

		fb.getService().getServiceSequence().clear();
		final ServiceSequence seq2 = ServiceSequenceUtils.addServiceSequence(fb.getService());
		setVariable(fb, "G", "FALSE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq2, new FBTransactionBuilder("EI", "EO0")); //$NON-NLS-1$ //$NON-NLS-2$

		runFBTest(fb, seq2);
	}
}
