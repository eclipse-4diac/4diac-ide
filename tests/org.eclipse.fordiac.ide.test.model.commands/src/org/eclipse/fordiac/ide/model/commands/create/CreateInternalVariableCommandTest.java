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
package org.eclipse.fordiac.ide.model.commands.create;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateInternalVariableCommandTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.junit.Assume;
import org.junit.runners.Parameterized.Parameters;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateInternalVariableCommandTest extends CreateInternalVariableCommandTestBase {

	private static State executeCommandVar1(State state) {
		BaseFBType baseFBType = getBaseFBType(state, Assume::assumeTrue);

		state.setCommand(new CreateInternalVariableCommand(baseFBType, 0, FordiacKeywords.VARIABLE_INTERNAL,
				getDatatypelib().getType(FordiacKeywords.BOOL)));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateVar1(State state, State oldState, TestFunction t) {
		BaseFBType baseFBType = getBaseFBType(state, t);
		t.test(!baseFBType.getInternalVars().isEmpty());
		t.test(baseFBType.getInternalVars().get(0).getName().equals(FordiacKeywords.VARIABLE_INTERNAL));
		t.test(baseFBType.getInternalVars().get(0).getTypeName().equals(FordiacKeywords.BOOL));
	}

	private static State executeCommandVar2(State state) {
		BaseFBType baseFBType = getBaseFBType(state, Assume::assumeTrue);

		state.setCommand(new CreateInternalVariableCommand(baseFBType, 1, FordiacKeywords.VARIABLE_INTERNAL,
				getDatatypelib().getType(FordiacKeywords.DWORD)));
		// Name will be InternalVar2 because of automatic renaming
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateVar2(State state, State oldState, TestFunction t) {
		verifyStateVar1(state, oldState, t);
		BaseFBType baseFBType = getBaseFBType(state, t);
		t.test(baseFBType.getInternalVars().get(1).getName().equals("InternalVar2"));
		t.test(baseFBType.getInternalVars().get(1).getTypeName().equals(FordiacKeywords.DWORD));
	}

	private static State executeCommandVar3(State state) {
		BaseFBType baseFBType = getBaseFBType(state, Assume::assumeTrue);

		state.setCommand(new CreateInternalVariableCommand(baseFBType));
		// Name will be INTERNALVAR1 because of automatic naming
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateVar3(State state, State oldState, TestFunction t) {
		verifyStateVar2(state, oldState, t);
		BaseFBType baseFBType = getBaseFBType(state, t);
		t.test(baseFBType.getInternalVars().get(2).getName().equals("INTERNALVAR1")); //$NON-NLS-1$
		t.test(baseFBType.getInternalVars().get(2).getTypeName().equals(FordiacKeywords.BOOL)); //$NON-NLS-1$
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<State>("Add an internal variable", //$NON-NLS-1$
						CreateInternalVariableCommandTest::executeCommandVar1, //
						CreateInternalVariableCommandTest::verifyStateVar1 //
				), //
				new ExecutionDescription<State>("Add a second internal variable", //$NON-NLS-1$
						CreateInternalVariableCommandTest::executeCommandVar2, //
						CreateInternalVariableCommandTest::verifyStateVar2 //
				), //
				new ExecutionDescription<State>("Add a third internal variable", //$NON-NLS-1$
						CreateInternalVariableCommandTest::executeCommandVar3, //
						CreateInternalVariableCommandTest::verifyStateVar3 //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
