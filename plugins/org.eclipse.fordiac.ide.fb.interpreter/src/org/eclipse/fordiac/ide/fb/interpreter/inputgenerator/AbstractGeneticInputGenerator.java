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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventOccFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public abstract class AbstractGeneticInputGenerator {

	protected final Random random = new Random();
	protected FBType type;

	protected AbstractGeneticInputGenerator(final FBType type) {
		this.type = type;
	}

	public abstract List<EventOccurrence> runAlgorithm();

	protected abstract double calculateFitness(final List<EventOccurrence> candidate);

	protected static void refreshSequence(final List<EventOccurrence> newParent) {
		newParent.forEach(eo -> {
			eo.setActive(true);
			eo.setIgnored(false);
		});
	}

	public static void printList(final List<EventOccurrence> parent) {
		parent.forEach(eo -> System.out.print(eo.getEvent().getName() + " -> ")); //$NON-NLS-1$
	}

	protected static int countOutputEventOccurrences(final EList<Transaction> transactions) {
		int count = 0;
		for (final Transaction t : transactions) {
			if (t instanceof final FBTransaction fbt) {
				count += fbt.getOutputEventOccurrences().size();
			}
		}
		return count;
	}

	protected static List<EventOccurrence> setCrossoverSequence(final int first, final int second,
			final List<EventOccurrence> crossoverSequence, final List<EventOccurrence> original) {
		final List<EventOccurrence> crossed = new ArrayList<>();
		crossed.addAll(original.subList(0, first));
		crossed.addAll(crossoverSequence);
		crossed.addAll(original.subList(second, original.size() - 1));
		return crossed;
	}

	protected int[] getCrossoverLocation(final List<EventOccurrence> candidate) {
		final int sizeCandidate = candidate.size();
		final int from = random.nextInt(sizeCandidate);
		final int to = random.nextInt(sizeCandidate);
		if (to > from) {
			return new int[] { from, to };
		}
		return new int[] { to, from };
	}

	protected void mutate(final List<EventOccurrence> candidate, final List<List<EventOccurrence>> append) {
		final List<EventOccurrence> mutant = (List<EventOccurrence>) EcoreUtil.copyAll(candidate);
		final int rand = random.nextInt(4);
		switch (rand) {
		case 0:
			addEvent(mutant);
			break;
		case 1:
			removeEvent(mutant);
			break;
		case 2:
			changeOrder(mutant);
			break;
		default:
			if (type.getInterfaceList().getEventInputs().size() > 1) {
				replaceEvent(mutant);
			}
			break;
		}
		append.add(mutant);
	}

	// addEvent is only of use when no crossover is not used (crossover changes the
	// size by more then one)
	protected void addEvent(final List<EventOccurrence> mutant) {
		mutant.addAll(EventOccFactory.createFrom(InputGenerator.getRandomEventsSequence(type, 1), null));
	}

	// addEvent is only of use when no crossover is not used (crossover changes the
	// size by more then one)
	protected static void removeEvent(final List<EventOccurrence> mutant) {
		if (!mutant.isEmpty()) {
			mutant.remove(mutant.size() - 1);
		}
	}

	protected void changeOrder(final List<EventOccurrence> mutant) {
		if (mutant.size() >= 2) {
			final int pos = random.nextInt(mutant.size() - 1);
			final EventOccurrence temp = mutant.get(pos);
			mutant.set(pos, mutant.get(pos + 1));
			mutant.set(pos + 1, temp);
		}
	}

	protected void replaceEvent(final List<EventOccurrence> mutant) {
		final int pos = random.nextInt(mutant.size());
		final String name = mutant.get(pos).getEvent().getName();
		mutant.remove(pos);
		List<EventOccurrence> eventocc = EventOccFactory.createFrom(InputGenerator.getRandomEventsSequence(type, 1),
				null);
		while (name.equals(eventocc.get(0).getEvent().getName())) {
			eventocc.clear();
			eventocc = EventOccFactory.createFrom(InputGenerator.getRandomEventsSequence(type, 1), null);

		}
		mutant.addAll(pos, EventOccFactory.createFrom(InputGenerator.getRandomEventsSequence(type, 1), null));
	}

	protected void sort(final List<List<EventOccurrence>> original) {
		for (final List<EventOccurrence> element : original) {
			refreshSequence(element);
		}
		final Map<List<EventOccurrence>, Double> fitlist = calculateFitnessAll(original);

		original.sort((o1, o2) -> compareLists(fitlist, o1, o2));
	}

	private static int compareLists(final Map<List<EventOccurrence>, Double> fitlist, final List<EventOccurrence> o1,
			final List<EventOccurrence> o2) {
		return Double.compare(fitlist.get(o1).doubleValue(), fitlist.get(o2).doubleValue()) * -1;
	}

	protected static boolean compareBest(final List<EventOccurrence> o1, final List<EventOccurrence> o2) {
		if (o1.size() != o2.size()) {
			return false;
		}
		for (int i = 0; i < o1.size(); i++) {
			if (o1.get(i).getEvent() != o2.get(i).getEvent()) {
				return false;
			}
		}
		return true;
	}

	private Map<List<EventOccurrence>, Double> calculateFitnessAll(final List<List<EventOccurrence>> candidates) {
		final Map<List<EventOccurrence>, Double> fitnessMap = new HashMap<>();
		for (final List<EventOccurrence> sequence : candidates) {
			fitnessMap.put(sequence, Double.valueOf(calculateFitness(sequence)));
		}
		return fitnessMap;
	}

}
