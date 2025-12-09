/*******************************************************************************
 * Copyright (c) 2020, 2025 Johannes Kepler University
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

import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class FBCreateCommandTest extends FBNetworkTestBase {

	public static State executeCommand(final State state) {
		state.setCommand(new FBCreateCommand(state.getFunctionblock(), state.getFbNetwork(), 0, 0));

		tester.get().test(state.getCommand() instanceof FBCreateCommand);
		final FBCreateCommand c = (FBCreateCommand) state.getCommand();
		tester.get().test(c.getTypeEntry(), state.getFunctionblock());

		return commandExecution(state);
	}

	public static void verifyState(final State state, final State oldState, final TestFunction t) {
		t.test(!state.getFbNetwork().isSubApplicationNetwork());
		t.test(!state.getFbNetwork().getNetworkElements().isEmpty());
		t.test(state.getFbNetwork().getElementNamed(FBNetworkTestBase.State.FUNCTIONBLOCK_NAME));
		final BlockFBNetworkElement fb = (BlockFBNetworkElement) state.getFbNetwork().getNetworkElements().get(0);
		t.test(fb.getInterface());
		t.test(fb.getInterface().getEventInputs());
		t.test(fb.getInterface().getEventOutputs());
		t.test(fb.getInterface().getInputVars());
		t.test(fb.getInterface().getOutputVars());
		t.test(fb.getInterface().getPlugs());
		t.test(fb.getInterface().getSockets());
		t.test(null == fb.getOpposite());
		t.test(fb.getName());
		t.test(fb.eContainer());
		t.test(fb.getInterface().getEventInputs().equals(state.getFunctionblock().getInterface().getEventInputs()));
		t.test(fb.getInterface().getEventOutputs().equals(state.getFunctionblock().getInterface().getEventOutputs()));
		t.test(fb.getInterface().getInputVars().equals(state.getFunctionblock().getInterface().getInputVars()));
		t.test(fb.getInterface().getOutputVars().equals(state.getFunctionblock().getInterface().getOutputVars()));
		t.test(fb.getInterface().getPlugs().equals(state.getFunctionblock().getInterface().getPlugs()));
		t.test(fb.getInterface().getSockets().equals(state.getFunctionblock().getInterface().getSockets()));
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						FBCreateCommandTest::verifyState //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
