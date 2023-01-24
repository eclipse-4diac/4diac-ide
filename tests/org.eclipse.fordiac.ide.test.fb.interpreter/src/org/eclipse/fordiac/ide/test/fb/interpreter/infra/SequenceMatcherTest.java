/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.infra;

import static org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory.addTransaction;
import static org.eclipse.fordiac.ide.fb.interpreter.mm.utils.VariableUtils.setVariable;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.eclipse.fordiac.ide.fb.interpreter.api.FBTransactionBuilder;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.FBTestRunner;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.junit.Test;

public class SequenceMatcherTest {
	private static final String TEST_FB_NAME = "E_CTD"; //$NON-NLS-1$

	@SuppressWarnings("static-method")
	@Test
	public void testCorrectParameters() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", "Q:=FALSE;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testCorrectParametersAfterCounting() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType("E_CTU"); // $NON-NLS-1$ //$NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("CU", "CUO", "Q:=TRUE;CV:=1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testWrongBooleanParameter() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", "Q:=TRUE;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(!result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testWrongBooleanAsNumber() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", "Q:=1;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(!result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testCorrectBooleanAsNumber() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", "Q:=0;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testWrongParameterName() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", "Qq:=0;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(!result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testCorrectParameterWithPrefix() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", "Q:=BOOL#FALSE;CV:=UINT#0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> res = FBTestRunner.runFBTest(fb, seq);
		assertTrue(res.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testNonAssignablePrefix() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", "Q:=BOOL#FALSE;CV:=INT#0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(!result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testIncorrectPrefix() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", "Q:=BOOL#FALSE;CV:=BOOL#0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(!result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testWrongEventName() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LD", "Q:=0;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(!result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testWrongEvent() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "CDO", "Q:=0;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(!result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testSpacesInParameterField() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", "Q:=0; CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testExtraSemicolonAtEnd() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", "Q:=0;CV:=0;")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testExtraSemicolonAtBeginning() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", "LDO", ";Q:=0;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testExtraEvent() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI", List.of("LDO", "LDO"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(!result.isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testMissingEvent() {
		final BasicFBType fb = (BasicFBType) AbstractInterpreterTest.loadFBType(TEST_FB_NAME); // $NON-NLS-1$
		final ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("LDI")); //$NON-NLS-1$
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(!result.isEmpty());
	}
}
