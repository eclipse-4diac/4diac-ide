/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.insert;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateInternalVariableCommandTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class InsertAlgorithmCommandTest extends CreateInternalVariableCommandTestBase {

	private static final String ALGORITHM_NAME = "algorithm1"; //$NON-NLS-1$
	private static final String ALGORITHM2_NAME = "algorithm2"; //$NON-NLS-1$
	private static final String ALGORITHM3_NAME = "algorithm3"; //$NON-NLS-1$
	private static final String ALGORITHM_TEXT = "Hokus Pokus"; //$NON-NLS-1$
	private static final String ALGORITHM_COMMENT = "Magic!"; //$NON-NLS-1$

	private static State executeCommandWithIndex(State state, int index) {
		getBaseFBType(state, assumption);

		final STAlgorithm stAlg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		stAlg.setName(ALGORITHM_NAME); // Algorithm name changes based on what is already in the list
		stAlg.setText(ALGORITHM_TEXT);
		stAlg.setComment(ALGORITHM_COMMENT);

		final PaletteEntry pe = state.getFunctionblock();
		assumption.test(pe.getType() instanceof BasicFBType);
		final BasicFBType fb = (BasicFBType) pe.getType();

		state.setCommand(new InsertAlgorithmCommand(fb, stAlg, index));

		return commandExecution(state);
	}

	private static void verifyStateWithAlgorithmIndex(State state, State oldState, TestFunction t, int index,
			String algorithmName) {
		final EList<Algorithm> algorithmList = ((BasicFBType) state.getFbNetwork().getNetworkElements().get(0)
				.getType()).getAlgorithm();

		t.test(state.getFbNetwork().getNetworkElements().get(0).getType() instanceof BasicFBType);
		t.test(!algorithmList.isEmpty());
		t.test(algorithmList.get(index) instanceof STAlgorithm);
		t.test(((STAlgorithm) algorithmList.get(index)).getComment(), ALGORITHM_COMMENT);
		t.test(((STAlgorithm) algorithmList.get(index)).getText(), ALGORITHM_TEXT);
		t.test(algorithmList.get(index).getName(), algorithmName);
	}

	private static State executeCommand1(State state) {
		return executeCommandWithIndex(state, 0);
	}

	private static void verifyState1(State state, State oldState, TestFunction t) {
		verifyStateWithAlgorithmIndex(state, oldState, t, 0, ALGORITHM_NAME);
	}

	private static State executeCommand2(State state) {
		return executeCommand1(state);
	}

	private static void verifyState2(State state, State oldState, TestFunction t) {
		verifyStateWithAlgorithmIndex(state, oldState, t, 0, ALGORITHM2_NAME);
		verifyStateWithAlgorithmIndex(state, oldState, t, 1, ALGORITHM_NAME);
	}

	private static State executeCommand3(State state) {
		return executeCommandWithIndex(state, 2);
	}

	private static void verifyState3(State state, State oldState, TestFunction t) {
		verifyState2(state, oldState, t);
		verifyStateWithAlgorithmIndex(state, oldState, t, 2, ALGORITHM3_NAME);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add a ST algorithm", //$NON-NLS-1$
						InsertAlgorithmCommandTest::executeCommand1, //
						InsertAlgorithmCommandTest::verifyState1 //
				), //
				new ExecutionDescription<>("Add a second ST algorithm at index 0", //$NON-NLS-1$
						InsertAlgorithmCommandTest::executeCommand2, //
						InsertAlgorithmCommandTest::verifyState2 //
				), //
				new ExecutionDescription<>("Add a third ST algorithm at end of list", //$NON-NLS-1$
						InsertAlgorithmCommandTest::executeCommand3, //
						InsertAlgorithmCommandTest::verifyState3 //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
