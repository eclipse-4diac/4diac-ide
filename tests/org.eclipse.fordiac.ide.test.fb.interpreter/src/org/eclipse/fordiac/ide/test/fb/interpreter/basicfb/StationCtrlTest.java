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

import java.util.Arrays;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.FBTransaction;

public class StationCtrlTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final BasicFBType fb = loadFBType("StationCtrl"); //$NON-NLS-1$
		fb.getService().getServiceSequence().clear();
		ServiceSequence seq = addServiceSequence(fb.getService());

		addTransaction(seq, new FBTransaction("INIT", "INITO")); //$NON-NLS-1$ //$NON-NLS-2$
		runTest(fb, seq);

		// ErrorCode is default 0
		fb.getService().getServiceSequence().clear();
		seq = addServiceSequence(fb.getService());

		final String[] outputs = { "StopConv", "PickPart" }; //$NON-NLS-1$//$NON-NLS-2$
		addTransaction(seq, new FBTransaction("NextPart", Arrays.asList(outputs))); //$NON-NLS-1$
		addTransaction(seq, new FBTransaction("PartPicked", "StartConv")); //$NON-NLS-1$ //$NON-NLS-2$
		runTest(fb, seq);


		fb.getService().getServiceSequence().clear();
		seq = addServiceSequence(fb.getService());
		setVariable(fb, "ErrorCode", "2"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransaction("Error", "StopConv")); //$NON-NLS-1$ //$NON-NLS-2$
		runTest(fb, seq, "processingPart"); //$NON-NLS-1$
	}



}
