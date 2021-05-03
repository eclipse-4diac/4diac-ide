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

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVariableOrderCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalVariableCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateInternalVariableCommandTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateInternalVariableCommandTest extends CreateInternalVariableCommandTestBase {

	private static final String VARIABLE1_NAME = FordiacKeywords.VARIABLE_INTERNAL;
	private static final String VARIABLE2_NAME = "InternalVar2"; //$NON-NLS-1$
	private static final String VARIABLE3_NAME = "INTERNALVAR1"; //$NON-NLS-1$

	private static State executeCommandVar1(State state) {
		BaseFBType baseFBType = getBaseFBType(state, assumption);

		state.setCommand(new CreateInternalVariableCommand(baseFBType, 0, FordiacKeywords.VARIABLE_INTERNAL,
				getDatatypelib().getType(FordiacKeywords.BOOL)));

		return commandExecution(state);
	}

	private static void verifyStateVar1(State state, State oldState, TestFunction t) {
		BaseFBType baseFBType = getBaseFBType(state, t);
		t.test(!baseFBType.getInternalVars().isEmpty());
		t.test(baseFBType.getInternalVars().get(0).getName(), VARIABLE1_NAME);
		t.test(baseFBType.getInternalVars().get(0).getTypeName(), FordiacKeywords.BOOL);
	}

	private static State executeCommandVar2(State state) {
		BaseFBType baseFBType = getBaseFBType(state, assumption);

		state.setCommand(new CreateInternalVariableCommand(baseFBType, 1, FordiacKeywords.VARIABLE_INTERNAL,
				getDatatypelib().getType(FordiacKeywords.DWORD)));
		// Name will be InternalVar2 because of automatic renaming

		return commandExecution(state);
	}

	private static void verifyStateVar2(State state, State oldState, TestFunction t) {
		verifyStateVar1(state, oldState, t);
		BaseFBType baseFBType = getBaseFBType(state, t);
		t.test(baseFBType.getInternalVars().get(1).getName(), VARIABLE2_NAME);
		t.test(baseFBType.getInternalVars().get(1).getTypeName(), FordiacKeywords.DWORD);
	}

	private static State executeCommandVar3(State state) {
		BaseFBType baseFBType = getBaseFBType(state, assumption);

		state.setCommand(new CreateInternalVariableCommand(baseFBType));
		// Name will be INTERNALVAR1 because of automatic naming

		return commandExecution(state);
	}

	private static void verifyStateVar3(State state, State oldState, TestFunction t) {
		verifyStateVar2(state, oldState, t);
		BaseFBType baseFBType = getBaseFBType(state, t);
		t.test(baseFBType.getInternalVars().get(2).getName(), VARIABLE3_NAME);
		t.test(baseFBType.getInternalVars().get(2).getTypeName(), FordiacKeywords.BOOL);
	}

	private static State executeReorder(State state, int index, boolean direction) {
		final BaseFBType baseFBType = getBaseFBType(state, assumption);
		state.setCommand(new ChangeVariableOrderCommand(baseFBType.getInternalVars(),
				baseFBType.getInternalVars().get(index), direction));
		return commandExecution(state);
	}

	private static void verifyOrder(State state, State oldState, TestFunction t, String name1, String name2,
			String name3) {
		final BaseFBType baseFBType = getBaseFBType(state, t);
		t.test(baseFBType.getInternalVars().size(), 3);
		t.test(baseFBType.getInternalVars().get(0).getName(), name1);
		t.test(baseFBType.getInternalVars().get(1).getName(), name2);
		t.test(baseFBType.getInternalVars().get(2).getName(), name3);
	}

	private static State executeDeleteVariable(State state) {
		final BaseFBType baseFBType = getBaseFBType(state, assumption);
		final VarDeclaration v = baseFBType.getInternalVars().get(0);
		assumption.test(v.getName(), VARIABLE3_NAME);
		state.setCommand(new DeleteInternalVariableCommand(baseFBType, v));
		return commandExecution(state);
	}

	private static void verifyDelete(State state, State oldState, TestFunction t) {
		final BaseFBType baseFBType = getBaseFBType(state, t);
		t.test(baseFBType.getInternalVars().size(),
				((BaseFBType) oldState.getFunctionblock().getType()).getInternalVars().size() - 1);
		t.test(baseFBType.getInternalVars().get(0).getName(), VARIABLE1_NAME);
		t.test(baseFBType.getInternalVars().get(1).getName(), VARIABLE2_NAME);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add an internal variable", //$NON-NLS-1$
						CreateInternalVariableCommandTest::executeCommandVar1, //
						CreateInternalVariableCommandTest::verifyStateVar1 //
				), //
				new ExecutionDescription<>("Add a second internal variable", //$NON-NLS-1$
						CreateInternalVariableCommandTest::executeCommandVar2, //
						CreateInternalVariableCommandTest::verifyStateVar2 //
				), //
				new ExecutionDescription<>("Add a third internal variable", //$NON-NLS-1$
						CreateInternalVariableCommandTest::executeCommandVar3, //
						CreateInternalVariableCommandTest::verifyStateVar3 //
				), //
				new ExecutionDescription<>("move second algorithmn to third place", //$NON-NLS-1$
						(State s) -> executeReorder(s, 1, false), //
						(State s, State o, TestFunction t) -> verifyOrder(s, o, t, VARIABLE1_NAME, VARIABLE3_NAME,
								VARIABLE2_NAME)//
				), //
				new ExecutionDescription<>("move second algorithmn to first place", //$NON-NLS-1$
						(State s) -> executeReorder(s, 1, true), //
						(State s, State o, TestFunction t) -> verifyOrder(s, o, t, VARIABLE3_NAME, VARIABLE1_NAME,
								VARIABLE2_NAME)//
				), //
				new ExecutionDescription<>("move first algorithmn past lower bound", //$NON-NLS-1$
						(State s) -> executeReorder(s, 0, true), //
						(State s, State o, TestFunction t) -> verifyOrder(s, o, t, VARIABLE3_NAME, VARIABLE1_NAME,
								VARIABLE2_NAME)//
				), //
				new ExecutionDescription<>("move third algorithmn past upper bound", //$NON-NLS-1$
						(State s) -> executeReorder(s, 2, false), //
						(State s, State o, TestFunction t) -> verifyOrder(s, o, t, VARIABLE3_NAME, VARIABLE1_NAME,
								VARIABLE2_NAME)//
				), //
				new ExecutionDescription<>("delete first entry", //$NON-NLS-1$
						CreateInternalVariableCommandTest::executeDeleteVariable, //
						CreateInternalVariableCommandTest::verifyDelete //
				)//
		);

		return createCommands(executionDescriptions);
	}

}
