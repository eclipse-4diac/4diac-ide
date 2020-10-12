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

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.change.AttributeChangeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.AttributeDeleteCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.junit.jupiter.params.provider.Arguments;

public class AttributeCommandTest extends FBNetworkTestBase {

	private static final String ATTRIBUTE_DEFAULT_NAME = "name"; //$NON-NLS-1$
	private static final String ATTRIBUTE_DEFAULT_VALUE = "value"; //$NON-NLS-1$
	private static final String ATTRIBUTE_DEFAULT_COMMENT = "comment"; //$NON-NLS-1$

	private static final String ATTRIBUTE_NAME = "testattribute"; //$NON-NLS-1$
	private static final String ATTRIBUTE_VALUE = "set to a specific value"; //$NON-NLS-1$
	private static final String ATTRIBUTE_COMMENT = "This explains the itent behind this attribute."; //$NON-NLS-1$

	public static void verifyDefaultState(final State state, final State oldState, final TestFunction t) {
		FBCreateCommandTest.verifyState(state, oldState, t);

		final EList<Attribute> attributes = state.getFbNetwork().getNetworkElements().get(0).getAttributes();
		t.test(attributes);
		t.test(attributes.isEmpty());
	}

	public static State executeCreateCommand(final State state) {
		state.setCommand(new AttributeCreateCommand(state.getFbNetwork().getNetworkElements().get(0)));

		return commandExecution(state);
	}

	public static void verifyCreatedState(final State state, final State oldState, final TestFunction t) {
		final FBNetworkElement networkElement = state.getFbNetwork().getNetworkElements().get(0);
		final EList<Attribute> attributes = networkElement.getAttributes();
		t.test(attributes);
		t.test(attributes.size(), 1);
		t.test(networkElement.getAttribute(ATTRIBUTE_DEFAULT_NAME), attributes.get(0));
		t.test(networkElement.getAttribute(ATTRIBUTE_NAME), (Attribute) null);
		t.test(attributes.get(0).getName(), ATTRIBUTE_DEFAULT_NAME);
		t.test(attributes.get(0).getValue(), ATTRIBUTE_DEFAULT_VALUE);
		t.test(attributes.get(0).getComment(), ATTRIBUTE_DEFAULT_COMMENT);
	}

	public static State executeChangeCommand(final State state) {
		final FBNetworkElement networkElement = state.getFbNetwork().getNetworkElements().get(0);
		state.setCommand(new AttributeChangeCommand(networkElement.getAttributes().get(0), ATTRIBUTE_NAME,
				ATTRIBUTE_VALUE, null, ATTRIBUTE_COMMENT));

		return commandExecution(state);
	}

	public static void verifyChangedState(final State state, final State oldState, final TestFunction t) {
		final FBNetworkElement networkElement = state.getFbNetwork().getNetworkElements().get(0);
		final EList<Attribute> attributes = networkElement.getAttributes();
		t.test(attributes);
		t.test(attributes.size(), 1);
		t.test(attributes.get(0).getName(), ATTRIBUTE_NAME);
		t.test(attributes.get(0).getValue(), ATTRIBUTE_VALUE);
		t.test(attributes.get(0).getComment(), ATTRIBUTE_COMMENT);
		t.test(networkElement.getAttribute(ATTRIBUTE_NAME), attributes.get(0));
		t.test(networkElement.getAttribute(ATTRIBUTE_DEFAULT_NAME), (Attribute) null);
	}

	public static State executeDeleteCommand(final State state) {
		final FBNetworkElement networkElement = state.getFbNetwork().getNetworkElements().get(0);
		state.setCommand(new AttributeDeleteCommand(networkElement, networkElement.getAttributes().get(0)));

		return commandExecution(state);
	}

	public static void verifyDeletedState(final State state, final State oldState, final TestFunction t) {
		final FBNetworkElement networkElement = state.getFbNetwork().getNetworkElements().get(0);
		final EList<Attribute> attributes = networkElement.getAttributes();
		t.test(attributes);
		t.test(attributes.isEmpty());
		t.test(networkElement.getAttribute(ATTRIBUTE_NAME), (Attribute) null);
		t.test(networkElement.getAttribute(ATTRIBUTE_DEFAULT_NAME), (Attribute) null);
	}

	public static void verifyCreated2State(final State state, final State oldState, final TestFunction t) {
		final FBNetworkElement networkElement = state.getFbNetwork().getNetworkElements().get(0);
		final EList<Attribute> attributes = networkElement.getAttributes();
		t.test(attributes);
		t.test(attributes.size(), 2);
		t.test(networkElement.getAttribute(ATTRIBUTE_DEFAULT_NAME), attributes.get(0));
		t.test(networkElement.getAttribute(ATTRIBUTE_NAME), (Attribute) null);
		t.test(attributes.get(0).getName(), ATTRIBUTE_DEFAULT_NAME);
		t.test(attributes.get(0).getValue(), ATTRIBUTE_DEFAULT_VALUE);
		t.test(attributes.get(0).getComment(), ATTRIBUTE_DEFAULT_COMMENT);
		t.test(attributes.get(1).getName(), ATTRIBUTE_DEFAULT_NAME);
		t.test(attributes.get(1).getValue(), ATTRIBUTE_DEFAULT_VALUE);
		t.test(attributes.get(1).getComment(), ATTRIBUTE_DEFAULT_COMMENT);
	}

	public static void verifyChanged2State(final State state, final State oldState, final TestFunction t) {
		final FBNetworkElement networkElement = state.getFbNetwork().getNetworkElements().get(0);
		final EList<Attribute> attributes = networkElement.getAttributes();
		t.test(attributes);
		t.test(attributes.size(), 2);
		t.test(networkElement.getAttribute(ATTRIBUTE_NAME).getName(), ATTRIBUTE_NAME);
		t.test(networkElement.getAttribute(ATTRIBUTE_NAME).getValue(), ATTRIBUTE_VALUE);
		t.test(networkElement.getAttribute(ATTRIBUTE_NAME).getComment(), ATTRIBUTE_COMMENT);
		t.test(networkElement.getAttribute(ATTRIBUTE_DEFAULT_NAME).getName(), ATTRIBUTE_DEFAULT_NAME);
		t.test(networkElement.getAttribute(ATTRIBUTE_DEFAULT_NAME).getValue(), ATTRIBUTE_DEFAULT_VALUE);
		t.test(networkElement.getAttribute(ATTRIBUTE_DEFAULT_NAME).getComment(), ATTRIBUTE_DEFAULT_COMMENT);
	}

	public static void verifyDeleted2State(final State state, final State oldState, final TestFunction t) {
		final FBNetworkElement networkElement = state.getFbNetwork().getNetworkElements().get(0);
		final EList<Attribute> attributes = networkElement.getAttributes();
		t.test(attributes);
		t.test(attributes.size(), 1);
		t.test(networkElement.getAttribute(ATTRIBUTE_DEFAULT_NAME), attributes.get(0));
		t.test(networkElement.getAttribute(ATTRIBUTE_NAME), (Attribute) null);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						AttributeCommandTest::verifyDefaultState //
				), //
				new ExecutionDescription<>("Create Attribute", //$NON-NLS-1$
						AttributeCommandTest::executeCreateCommand, //
						AttributeCommandTest::verifyCreatedState //
				), //
				new ExecutionDescription<>("Change Attribute", //$NON-NLS-1$
						AttributeCommandTest::executeChangeCommand, //
						AttributeCommandTest::verifyChangedState //
				), //
				new ExecutionDescription<>("Delete Attribute", //$NON-NLS-1$
						AttributeCommandTest::executeDeleteCommand, //
						AttributeCommandTest::verifyDeletedState //
				), //
				new ExecutionDescription<>("Recreate Attribute", //$NON-NLS-1$
						AttributeCommandTest::executeCreateCommand, //
						AttributeCommandTest::verifyCreatedState //
				), //
				new ExecutionDescription<>("Create second Attribute", //$NON-NLS-1$
						AttributeCommandTest::executeCreateCommand, //
						AttributeCommandTest::verifyCreated2State //
				), //
				new ExecutionDescription<>("Change first Attribute", //$NON-NLS-1$
						AttributeCommandTest::executeChangeCommand, //
						AttributeCommandTest::verifyChanged2State //
				), //
				new ExecutionDescription<>("Delete second Attribute", //$NON-NLS-1$
						AttributeCommandTest::executeDeleteCommand, //
						AttributeCommandTest::verifyDeleted2State //
				), //
				new ExecutionDescription<>("Delete last Attribute", //$NON-NLS-1$
						AttributeCommandTest::executeDeleteCommand, //
						AttributeCommandTest::verifyDeletedState //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
