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

import java.util.Arrays;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.junit.Test;

public class EventSplitTest extends AbstractInterpreterTest {

	public EventSplitTest() {
		// do nothing
	}

	@Test
	public void test() {
		final BasicFBType fb = loadFBType("E_SPLIT"); //$NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		final String[] arr = { "EO1", "EO2" }; //$NON-NLS-1$ //$NON-NLS-2$
		final List<String> outputEvents = Arrays.asList(arr);
		addTransaction(seq, new FBTransaction("EI", outputEvents)); //$NON-NLS-1$
		addTransaction(seq, new FBTransaction("EI", outputEvents)); //$NON-NLS-1$
		runTest(fb, seq);
	}

}
