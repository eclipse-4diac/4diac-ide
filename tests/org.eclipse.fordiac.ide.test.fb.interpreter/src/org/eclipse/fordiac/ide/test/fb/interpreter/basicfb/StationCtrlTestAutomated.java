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

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

public class StationCtrlTestAutomated extends AbstractInterpreterTest {

	@Override
	public void test() {
		final BasicFBType fb = (BasicFBType) loadFBType("StationCtrl2", false); //$NON-NLS-1$

		ServiceSequence seq = fb.getService().getServiceSequence().get(0);
		runFBTest(fb, seq, "START"); //$NON-NLS-1$

		seq = fb.getService().getServiceSequence().get(1);
		runFBTest(fb, seq, "START"); //$NON-NLS-1$

		seq = fb.getService().getServiceSequence().get(2);
		runFBTest(fb, seq, "processingPart"); //$NON-NLS-1$
	}

}
