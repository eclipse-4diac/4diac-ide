/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
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
package org.eclipse.fordiac.ide.model.commands.change;

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.getArraySize;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.testinfra.ValueCommandTestBase;
import org.junit.jupiter.params.provider.Arguments;

public class ChangeArraySizeCommandTest extends ValueCommandTestBase {

	private static State executeCommand(final State state, final String newSize) {
		state.setCommand(new ChangeArraySizeCommand(state.getVar(), newSize));

		return commandExecution(state);
	}

	private static void verifyState(final State state, final TestFunction t, final String newSize) {
		t.test(getArraySize(state.getVar()), newSize);
		t.test(state.getVar().isArray(), !(newSize.isBlank()));
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Change Array Size to empty String", // //$NON-NLS-1$
						(final State s) -> executeCommand(s, ""), // //$NON-NLS-1$
						(final State s, final State o, final TestFunction t) -> verifyState(s, t, "") // //$NON-NLS-1$
				), //
				new ExecutionDescription<>("Change Array Size to 2", // //$NON-NLS-1$
						(final State s) -> executeCommand(s, "2"), // //$NON-NLS-1$
						(final State s, final State o, final TestFunction t) -> verifyState(s, t, "2") // //$NON-NLS-1$
				), //
				new ExecutionDescription<>("Change Array Size to 0", // //$NON-NLS-1$
						(final State s) -> executeCommand(s, "0"), // //$NON-NLS-1$
						(final State s, final State o, final TestFunction t) -> verifyState(s, t, "0") // //$NON-NLS-1$
				), //
				new ExecutionDescription<>("Change Array Size to -1", // //$NON-NLS-1$
						(final State s) -> executeCommand(s, "-1"), // //$NON-NLS-1$
						(final State s, final State o, final TestFunction t) -> verifyState(s, t, "-1") // //$NON-NLS-1$
				), //
				new ExecutionDescription<>("Change Array Size to abc", // //$NON-NLS-1$
						(final State s) -> executeCommand(s, "abc"), // //$NON-NLS-1$
						(final State s, final State o, final TestFunction t) -> verifyState(s, t, "abc") // //$NON-NLS-1$
				));

		return createCommands(executionDescriptions);
	}

}