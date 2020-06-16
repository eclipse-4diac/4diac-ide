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
package org.eclipse.fordiac.ide.model.commands.change;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.junit.runners.Parameterized.Parameters;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class FBCreateCommandTest extends FbNetworkTestBase {

	private static State executeCommand(State state) {
		state.setCommand(new FBCreateCommand(state.getFunctionblock(), state.getFbNetwork(), 0, 0));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}

	private static boolean verifyState(State state, State oldState) {
		return !state.getFbNetwork().isSubApplicationNetwork() && !state.getFbNetwork().getNetworkElements().isEmpty()
				&& (null != state.getFbNetwork().getNetworkElements().get(0).getInterface())
				&& (null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs())
				&& (null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs())
				&& (null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars())
				&& (null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars())
				&& (null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getPlugs())
				&& (null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getSockets())
				&& (null == state.getFbNetwork().getNetworkElements().get(0).getOpposite())
				&& (null != state.getFbNetwork().getNetworkElements().get(0).getName())
				&& (null != state.getFbNetwork().getNetworkElements().get(0).eContainer())
				&& state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs()
						.equals(state.getFunctionblock().getFBType().getInterfaceList().getEventInputs())
				&& //
				state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs()
						.equals(state.getFunctionblock().getFBType().getInterfaceList().getEventInputs())
				&& //
				state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars()
						.equals(state.getFunctionblock().getFBType().getInterfaceList().getInputVars())
				&& //
				state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars()
						.equals(state.getFunctionblock().getFBType().getInterfaceList().getOutputVars())
				&& //
				state.getFbNetwork().getNetworkElements().get(0).getInterface().getPlugs()
						.equals(state.getFunctionblock().getFBType().getInterfaceList().getPlugs())
				&& //
				state.getFbNetwork().getNetworkElements().get(0).getInterface().getSockets()
						.equals(state.getFunctionblock().getFBType().getInterfaceList().getSockets());
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<State>("Add Functionblock", //
						FBCreateCommandTest::executeCommand, //
						FBCreateCommandTest::verifyState //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
