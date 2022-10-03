/*******************************************************************************
 * Copyright (c) 2022 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.infra;

import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.inputgenerator.GeneticInputGeneratorWithCrossover;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.junit.Test;

public class GeneticInputGeneratorWithCrossoverTest {
	private static final int PARENT_COUNT = 4;
	private static final int INITIAL_LENGTH = 10;

	@SuppressWarnings("static-method")
	@Test
	public void test() {
		final FBType type = AbstractInterpreterTest.loadFBType("geneticAlgorithmTest"); //$NON-NLS-1$
		assert (type != null);
		final List<Event> possibleEvents = type.getInterfaceList().getEventInputs();
		final List<EventOccurrence> best = new GeneticInputGeneratorWithCrossover(type, INITIAL_LENGTH, PARENT_COUNT)
				.runAlgorithm(true, 4);
		assert (best != null);
		assert (!best.isEmpty());
		assert (best.get(0).getFbRuntime() != null);
		assert (best.get(0).getEvent().getWith().get(0).getVariables().getValue().getValue() != null);
		assert (!best.get(0).getEvent().getWith().get(0).getVariables().getValue().getValue().isBlank());

		assert (possibleEvents.stream() //
				.map(Event::getName)//
				.filter(s -> s.equals(best.get(0).getEvent().getName()))//
				.findAny().orElse(null) != null);
	}

}
