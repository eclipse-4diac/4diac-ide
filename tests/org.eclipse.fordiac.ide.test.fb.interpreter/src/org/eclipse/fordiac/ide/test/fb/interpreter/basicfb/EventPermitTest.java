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
import static org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils.setVariable;

import org.eclipse.fordiac.ide.fb.interpreter.api.FBTransactionBuilder;
import org.eclipse.fordiac.ide.fb.interpreter.api.ServiceFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

public class EventPermitTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final BasicFBType fb = (BasicFBType) loadFBType("E_PERMIT"); //$NON-NLS-1$
		fb.setService(ServiceFactory.createDefaultServiceModel());
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		// input PERMIT is default 0, no output event sent
		addTransaction(seq, new FBTransactionBuilder("EI")); //$NON-NLS-1$
		runFBTest(fb, seq);

		// set input PERMIT to 1, event goes through
		fb.getService().getServiceSequence().clear();
		final ServiceSequence seq2 = ServiceSequenceUtils.addServiceSequence(fb.getService());
		setVariable(fb, "PERMIT", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq2, new FBTransactionBuilder("EI", "EO")); //$NON-NLS-1$ //$NON-NLS-2$

	}

}
