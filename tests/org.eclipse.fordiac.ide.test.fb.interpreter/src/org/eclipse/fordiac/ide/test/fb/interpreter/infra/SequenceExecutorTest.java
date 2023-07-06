/*******************************************************************************
 * Copyright (c) 2022 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Paul Pavlicek - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.infra;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.inputgenerator.SequenceExecutor;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.junit.Test;

public class SequenceExecutorTest {

	@SuppressWarnings("static-method")
	@Test
	public void test() {
		final FBType fBType = AbstractInterpreterTest.loadFBType("E_CTU"); //$NON-NLS-1$
		assertNotNull(fBType);
		final EList<Transaction> trans = SequenceExecutor.executeRandomSequence(fBType, 3, true);
		assert (trans.size() == 3);
		assert (trans.stream().noneMatch(t -> t.getInputEventOccurrence().isActive()));
		assert (trans.stream().noneMatch(t -> t.getInputEventOccurrence().isIgnored()));
	}

}
