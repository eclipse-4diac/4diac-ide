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
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteWithCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.gef.commands.CompoundCommand;
import org.junit.jupiter.params.provider.Arguments;

public class WithCreateTest extends FBNetworkTestBase {

	public static State createInterfaceElements(final State state) {
		final CompoundCommand c = new CompoundCommand();

		c.add(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null),
				state.getFunctionblock().getType().getInterfaceList(), /* isInput */ true, /* index */ 0));
		c.add(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null),
				state.getFunctionblock().getType().getInterfaceList(), /* isInput */ false, /* index */ 0));

		c.add(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.BOOL),
				state.getFunctionblock().getType().getInterfaceList(), /* isInput */ true, /* index */ 0));
		c.add(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.BOOL),
				state.getFunctionblock().getType().getInterfaceList(), /* isInput */ false, /* index */ 0));

		c.add(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.DWORD),
				state.getFunctionblock().getType().getInterfaceList(), /* isInput */ true, /* index */ 1));
		c.add(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.DWORD),
				state.getFunctionblock().getType().getInterfaceList(), /* isInput */ false, /* index */ 1));

		c.add(new FBCreateCommand(state.getFunctionblock(), state.getFbNetwork(), 0, 0));

		c.add(new FBCreateCommand(state.getFunctionblock(), state.getFbNetwork(), 0, 0));

		state.setCommand(c);

		return commandExecution(state);
	}

	public static void verifyFBCreation(final State state, final State oldState, final TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().size(), 2);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType(),
				state.getFbNetwork().getNetworkElements().get(1).getType());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().size(), 1);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs().size(), 1);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars().size(), 2);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().size(), 2);
		t.test(state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0).getWith().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().get(0).getWith()
				.isEmpty());
	}

	private static State addWith(final State state) {
		state.setCommand(
				new WithCreateCommand(state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0),
						state.getFunctionblock().getType().getInterfaceList().getInputVars().get(0)));

		return commandExecution(state);
	}

	private static void verifyAddWith(final State state, final State oldState, final TestFunction t) {
		t.test(!state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0).getWith().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().get(0).getWith()
				.isEmpty());

		final WithCreateCommand c = new WithCreateCommand(
				state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0),
				state.getFunctionblock().getType().getInterfaceList().getInputVars().get(0));
		t.test(c.getEvent());
		t.test(c.getVarDeclaration());
	}

	public static State updateNetworkElements(final State state) {
		final CompoundCommand c = new CompoundCommand();

		c.add(new UpdateFBTypeCommand(state.getFbNetwork().getNetworkElements().get(0), state.getFunctionblock()));
		c.add(new UpdateFBTypeCommand(state.getFbNetwork().getNetworkElements().get(1), state.getFunctionblock()));

		state.setCommand(c);
		return commandExecution(state);
	}

	private static void verifyUpdateNetworkElementsAddedWith(final State state, final State oldState, final TestFunction t) {
		t.test(!state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0).getWith().isEmpty());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().get(0).getWith()
				.isEmpty());
	}

	private static State deleteWith(final State state) {
		state.setCommand(new DeleteWithCommand(
				state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0).getWith().get(0)));
		return commandExecution(state);
	}

	private static void verifyDeleteWith(final State state, final State oldState, final TestFunction t) {
		t.test(state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0).getWith().isEmpty());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().get(0).getWith()
				.isEmpty());
	}

	private static void verifyUpdateNetworkElementsDeletedWith(final State state, final State oldState, final TestFunction t) {
		t.test(state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0).getWith().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().get(0).getWith()
				.isEmpty());
	}

	private static State nullAll(final State state) {
		state.setCommand(new WithCreateCommand());
		return disabledCommandExecution(state);
	}

	private static State nullEvent(final State state) {
		state.setCommand(new WithCreateCommand(null,
				state.getFunctionblock().getType().getInterfaceList().getInputVars().get(0)));
		return disabledCommandExecution(state);
	}

	private static State nullInput(final State state) {
		state.setCommand(new WithCreateCommand(
				state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0), null));
		return disabledCommandExecution(state);
	}

	private static State onEventOutput(final State state) {
		final WithCreateCommand c = new WithCreateCommand();
		c.setEvent(state.getFunctionblock().getType().getInterfaceList().getEventOutputs().get(0));
		c.setVarDeclaration(state.getFunctionblock().getType().getInterfaceList().getInputVars().get(0));

		state.setCommand(c);

		return disabledCommandExecution(state);
	}

	private static State onOutput(final State state) {
		state.setCommand(
				new WithCreateCommand(state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0),
						state.getFunctionblock().getType().getInterfaceList().getOutputVars().get(0)));
		return disabledCommandExecution(state);
	}

	private static State addExistingWith(final State state) {
		state.setCommand(
				new WithCreateCommand(state.getFunctionblock().getType().getInterfaceList().getEventInputs().get(0),
						state.getFunctionblock().getType().getInterfaceList().getInputVars().get(0)));
		return disabledCommandExecution(state);
	}

	private static void verifyAddExistingWith(final State state, final State oldState, final TestFunction t) {
		if (!state.isViaUndo()) {
			t.test(state.getMessages().size(), 1);
			t.test(state.getMessages().get(0), Messages.WithExists);
		}
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Prepare Functionblocks", //$NON-NLS-1$
						WithCreateTest::createInterfaceElements, //
						WithCreateTest::verifyFBCreation //
						), //
				new ExecutionDescription<>("Add with at functionblock", //$NON-NLS-1$
						WithCreateTest::addWith, //
						WithCreateTest::verifyAddWith //
						), //
				new ExecutionDescription<>("Update Network Elements", //$NON-NLS-1$
						WithCreateTest::updateNetworkElements, //
						WithCreateTest::verifyUpdateNetworkElementsAddedWith //
						), //
				new ExecutionDescription<>("Delete With from functionblock", //$NON-NLS-1$
						WithCreateTest::deleteWith, //
						WithCreateTest::verifyDeleteWith //
						), //
				new ExecutionDescription<>("Update Network Elements", //$NON-NLS-1$
						WithCreateTest::updateNetworkElements, //
						WithCreateTest::verifyUpdateNetworkElementsDeletedWith //
						), //
				new ExecutionDescription<>("Create With without inferface data", //$NON-NLS-1$
						WithCreateTest::nullAll, //
						CommandTestBase::verifyNothing //
						), //
				new ExecutionDescription<>("Create With without event", //$NON-NLS-1$
						WithCreateTest::nullEvent, //
						CommandTestBase::verifyNothing //
						), //
				new ExecutionDescription<>("Create With without input", //$NON-NLS-1$
						WithCreateTest::nullInput, //
						CommandTestBase::verifyNothing //
						), //
				new ExecutionDescription<>("Create With on event-output", //$NON-NLS-1$
						WithCreateTest::onEventOutput, //
						WithCreateTest::verifyNothing //
						), //
				new ExecutionDescription<>("Create With on output", //$NON-NLS-1$
						WithCreateTest::onOutput, //
						WithCreateTest::verifyNothing //
						), //
				new ExecutionDescription<>("Add with at functionblock", //$NON-NLS-1$
						WithCreateTest::addWith, //
						WithCreateTest::verifyAddWith //
						), //
				new ExecutionDescription<>("Add with at functionblock that already exists", //$NON-NLS-1$
						WithCreateTest::addExistingWith, //
						WithCreateTest::verifyAddExistingWith //
						) //
				);
		return createCommands(executionDescriptions);
	}

}
