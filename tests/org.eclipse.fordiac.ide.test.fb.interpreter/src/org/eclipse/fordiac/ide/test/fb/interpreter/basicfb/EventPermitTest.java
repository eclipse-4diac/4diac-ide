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
 *   Antonio Garmendía, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.basicfb;

import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.FBTransaction;

public class EventPermitTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final BasicFBType fb = loadFBType("E_PERMIT"); //$NON-NLS-1$
		fb.setService(ServiceSequenceUtils.createEmptyServiceModel());
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		// input PERMIT is default 0, no output event sent
		addTransaction(seq, new FBTransaction("EI")); //$NON-NLS-1$
		runTest(fb, seq);

		// set input PERMIT to 1, event goes through
		fb.getService().getServiceSequence().clear();
		final ServiceSequence seq2 = ServiceSequenceUtils.addServiceSequence(fb.getService());
		setVariable(fb, "PERMIT", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq2, new FBTransaction("EI", "EO")); //$NON-NLS-1$ //$NON-NLS-2$

	}



}
