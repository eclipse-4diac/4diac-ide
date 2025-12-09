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

import java.util.Arrays;

import org.eclipse.fordiac.ide.fb.interpreter.api.FBTransactionBuilder;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

public class StationCtrlTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final BasicFBType fb = (BasicFBType) loadFBType("StationCtrl"); //$NON-NLS-1$
		fb.getService().getServiceSequence().clear();
		ServiceSequence seq = ServiceSequenceUtils.addServiceSequence(fb.getService());

		addTransaction(seq, new FBTransactionBuilder("INIT", "INITO")); //$NON-NLS-1$ //$NON-NLS-2$
		runFBTest(fb, seq);

		// ErrorCode is default 0
		fb.getService().getServiceSequence().clear();
		seq = ServiceSequenceUtils.addServiceSequence(fb.getService());

		final String[] outputs = { "StopConv", "PickPart" }; //$NON-NLS-1$//$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("NextPart", Arrays.asList(outputs))); //$NON-NLS-1$
		addTransaction(seq, new FBTransactionBuilder("PartPicked", "StartConv")); //$NON-NLS-1$ //$NON-NLS-2$
		runFBTest(fb, seq);

		fb.getService().getServiceSequence().clear();
		seq = ServiceSequenceUtils.addServiceSequence(fb.getService());
		setVariable(fb, "ErrorCode", "2"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("Error", "StopConv")); //$NON-NLS-1$ //$NON-NLS-2$
		runFBTest(fb, seq, "processingPart"); //$NON-NLS-1$
	}

}
