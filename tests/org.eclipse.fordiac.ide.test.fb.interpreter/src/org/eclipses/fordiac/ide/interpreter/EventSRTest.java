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
package org.eclipses.fordiac.ide.interpreter;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.junit.Test;

public class EventSRTest extends AbstractInterpreterTest {

	public EventSRTest() {
		// do nothing
	}

	@Test
	public void test() {
		final BasicFBType fb = loadFBType("E_SR"); //$NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		addTransaction(seq, new FBTransaction("R")); //$NON-NLS-1$
		addTransaction(seq, new FBTransaction("S", "EO", "Q:=TRUE")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("R", "EO", "Q:=FALSE")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("R")); //$NON-NLS-1$
		addTransaction(seq, new FBTransaction("S", "EO", "Q:=TRUE")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("S")); //$NON-NLS-1$
		addTransaction(seq, new FBTransaction("R", "EO", "Q:=FALSE")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("S", "EO", "Q:=TRUE")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		// new FBTransaction().inputEvent("R").expectedOutput("EO").expectedOutput("
		runTest(fb, seq);
	}



}
