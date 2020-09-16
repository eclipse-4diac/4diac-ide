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

import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class FBCreateCommandTest extends FBNetworkTestBase {

	public static State executeCommand(State state) {
		state.setCommand(new FBCreateCommand(state.getFunctionblock(), state.getFbNetwork(), 0, 0));

		return commandExecution(state);
	}

	public static void verifyState(State state, State oldState, TestFunction t) {
		t.test(!state.getFbNetwork().isSubApplicationNetwork());
		t.test(!state.getFbNetwork().getNetworkElements().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getPlugs());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getSockets());
		t.test(null == state.getFbNetwork().getNetworkElements().get(0).getOpposite());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getName());
		t.test(state.getFbNetwork().getNetworkElements().get(0).eContainer());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs()
				.equals(state.getFunctionblock().getFBType().getInterfaceList().getEventInputs()));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs()
				.equals(state.getFunctionblock().getFBType().getInterfaceList().getEventOutputs()));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars()
				.equals(state.getFunctionblock().getFBType().getInterfaceList().getInputVars()));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars()
				.equals(state.getFunctionblock().getFBType().getInterfaceList().getOutputVars()));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getPlugs()
				.equals(state.getFunctionblock().getFBType().getInterfaceList().getPlugs()));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getSockets()
				.equals(state.getFunctionblock().getFBType().getInterfaceList().getSockets()));
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<State>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						FBCreateCommandTest::verifyState //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
