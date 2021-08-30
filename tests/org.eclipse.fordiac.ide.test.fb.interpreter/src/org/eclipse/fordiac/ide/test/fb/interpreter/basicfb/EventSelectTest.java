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

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.FBTransaction;
import org.junit.Test;

public class EventSelectTest extends AbstractInterpreterTest {

	public EventSelectTest() {
		// do nothing
	}

	@Test
	public void test() throws Exception {
		final BasicFBType fb = loadFBType("E_SELECT"); //$NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "G", "TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransaction("EI0")); //$NON-NLS-1$
		addTransaction(seq, new FBTransaction("EI1", "EO")); //$NON-NLS-1$ //$NON-NLS-2$

		runTest(fb, seq);

		fb.getService().getServiceSequence().clear();
		final ServiceSequence seq2 = addServiceSequence(fb.getService());
		setVariable(fb, "G", "FALSE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq2, new FBTransaction("EI0", "EO")); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq2, new FBTransaction("EI1")); //$NON-NLS-1$

		runTest(fb, seq2);

	}
}
