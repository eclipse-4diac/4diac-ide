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

public class EventCTDTest extends AbstractInterpreterTest {

	public EventCTDTest() {
		// do nothing
	}

	@Test
	public void test() {
		final BasicFBType fb = loadFBType("E_CTD"); //$NON-NLS-1$
		ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransaction("LD", "LDO", "Q:=FALSE;CV:=1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("CD", "CDO", "Q:=TRUE;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		runTest(fb, seq);

		seq = addServiceSequence(fb.getService());
		setVariable(fb, "PV", "2"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransaction("LD", "LDO", "Q:=FALSE;CV:=2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("CD", "CDO", "Q:=FALSE;CV:=1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("CD", "CDO", "Q:=TRUE;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		runTest(fb, seq);

		seq = addServiceSequence(fb.getService());
		setVariable(fb, "PV", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransaction("LD", "LDO", "Q:=TRUE;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("CD")); //$NON-NLS-1$
		runTest(fb, seq);
	}

}
