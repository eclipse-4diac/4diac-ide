/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.testinfra.MapToCommandSystemTestBase;
import org.eclipse.gef.commands.Command;
import org.junit.jupiter.params.provider.Arguments;

//this class uses data/MapToCommandTest
public class MapToCommandTest extends MapToCommandSystemTestBase {

	private static final String EVENT_EI = "EI"; //$NON-NLS-1$
	private static final String EVENT_EO = "EO"; //$NON-NLS-1$

	private static State mapMidToRes1(final State state) {
		final Command cmd = MapToCommand.createMapToCommand(state.getMid(), state.getResource());
		state.setCommand(cmd);
		tester.get().test(cmd != null);
		return commandExecution(state);
	}

	private static void verifyMidMappedWithResourceConnections(final State state, final State oldState,
			final TestFunction t) {
		t.test(state.getMid().isMapped());
		t.test(state.getSystem().getMapping().size() == 3);

		t.test(state.getResourceElement(FB_MID) != null);
		t.test(state.getMid().getOpposite() == state.getResourceElement(FB_MID));

		t.test(state.getResourceNetwork().getEventConnections().size() == 2);
		t.test(hasEventConnection(state.getResourceNetwork(), FB_SRC, EVENT_EO, FB_MID, EVENT_EI));
		t.test(hasEventConnection(state.getResourceNetwork(), FB_MID, EVENT_EO, FB_DST, EVENT_EI));
	}

	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List
				.of(new ExecutionDescription<>("Map MID to Device1.Res1", MapToCommandTest::mapMidToRes1, //$NON-NLS-1$
						MapToCommandTest::verifyMidMappedWithResourceConnections));

		return createCommands(executionDescriptions);
	}
}