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
package org.eclipse.fordiac.ide.fb.interpreter.inputgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

public class InputGenerator {

	private static Random random = new Random();
	private static ValueRandom randomV = new ValueRandom();

	/** This method generates a sequence of input events for a given FB. All input events from the interface are
	 * considered as potential events. It is not guaranteed that each possible input event is present in the input event
	 * sequence. The events are selected randomly.
	 *
	 * @param fb    The FB for which the input sequence is generated
	 * @param count The number of generated input events
	 * @return A list of events of length count */
	public static List<Event> getRandomEventsSequence(final FBNetworkElement fb, final int count) {
		final List<Event> randomEvents = new ArrayList<>();
		final EList<Event> fbEvents = fb.getInterface().getEventInputs();
		final int numberPossibleEvents = fbEvents.size();
		for (int i = 0; i < count; i++) {
			final Event randomEvent = fbEvents.get(random.nextInt(numberPossibleEvents));
			randomEvents.add(randomEvent);
		}
		return randomEvents;
	}

	/** This method generates a sequence of input events for a given FB. All input events from the interface are
	 * considered as potential events. It is not guaranteed that each possible input event is present in the input event
	 * sequence. The events are selected randomly on the bases of the seed.
	 *
	 * @param fb    The FB for which the input sequence is generated
	 * @param count The number of generated input events
	 * @param seed  The seed used for random number generation
	 * @return A list of events of length count */
	public static List<Event> getRandomEventsSequence(final FBNetworkElement fb, final int count, final long seed) {
		random.setSeed(seed);
		return getRandomEventsSequence(fb, count);
	}

	public static List<VarDeclaration> getRandomDataSequence(final Event event) {
		final List<VarDeclaration> vars = event.getWith().stream().map(With::getVariables).filter(Objects::nonNull)
				.collect(Collectors.toList());
		for (final VarDeclaration variabe : vars) {
			if (variabe.getValue() == null) {
				final Value v = LibraryElementFactory.eINSTANCE.createValue();
				variabe.setValue(v);
			}
			variabe.getValue().setValue(getRandom(variabe));

		}
		return vars;
	}

	public static List<VarDeclaration> getRandomDataSequence(final Event event, final long seed) {
		final ValueRandom oldrandom = randomV;
		randomV.setSeed(seed);
		final List<VarDeclaration> randomData = getRandomDataSequence(event);
		randomV = oldrandom;
		return randomData;
	}

	private static String getRandom(final VarDeclaration x) {
		return randomV.getRandom(x.getType().getClass());
	}

	private InputGenerator() {
		throw new UnsupportedOperationException("this class should not be instantiated"); //$NON-NLS-1$
	}
}
