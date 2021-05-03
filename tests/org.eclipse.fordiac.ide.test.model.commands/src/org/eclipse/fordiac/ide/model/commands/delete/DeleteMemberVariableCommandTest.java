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
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.delete;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.testinfra.DeleteMemberVariableCommandTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class DeleteMemberVariableCommandTest extends DeleteMemberVariableCommandTestBase {

	private static State executeDeletion(State state, VarDeclaration var) {
		state.setCommand(new DeleteMemberVariableCommand(state.getStructuredType(), var));

		return commandExecution(state);
	}

	private static void verifyDeletion(State state, State oldState, TestFunction t, VarDeclaration var) {
		// verify that old variables are still there
		t.test((oldState.getStructuredType().getMemberVariables().size() - 1), state.getStructuredType()
				.getMemberVariables().size());
		// verify that new variable is not in member variables anymore
		for (final VarDeclaration member : state.getStructuredType().getMemberVariables()) {
			t.test(!member.getName().equals(var.getName()));
		}
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> deletionExecutionDescriptions = List.of(
				new ExecutionDescription<>("Delete first member var", //$NON-NLS-1$
						(State state) -> executeDeletion(state, varList.get(0)),
						(State state, State oldState, TestFunction t) -> verifyDeletion(state, oldState, t,
								varList.get(0))),

				new ExecutionDescription<>("Delete middle member var", //$NON-NLS-1$
						(State state) -> executeDeletion(state, varList.get(2)),
						(State state, State oldState, TestFunction t) -> verifyDeletion(state, oldState, t,
								varList.get(2))),

				new ExecutionDescription<>("Delete last member var", //$NON-NLS-1$
						(State state) -> executeDeletion(state, varList.get(varList.size() - 1)),
						(State state, State oldState, TestFunction t) -> verifyDeletion(state, oldState, t,
								varList.get(varList.size() - 1))),

				new ExecutionDescription<>("Delete member struct", //$NON-NLS-1$
						(State state) -> executeDeletion(state, varList.get(3)), (State state, State oldState,
								TestFunction t) -> verifyDeletion(state, oldState, t, varList.get(3)))

		);
		return createCommands(deletionExecutionDescriptions);
	}
}
