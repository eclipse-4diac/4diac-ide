/*******************************************************************************
 * Copyright (c) 2022,2023 Paul Pavlicek
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

	private static final int BEST_FITNESS = 0;
	private static final int MAX_ITERATION_DEFAULT = 11;
	private static final int TERMMINATION_COUNT_DEFAULT = 5;
	private static final int INITIAL_LENGTH_DEFAULT = 5;
	private static final int MUTATION_COUNT_DEFAULT = 3;
	private static final int PARENT_COUNT_DEFAULT = 4;

	private enum Mutations {
		REPLACE_EVENT, CHANGE_ORDER, INSERT_EVENT, DELETE_EVENT;
	}

	private boolean crossover = true;
	private boolean crossoverFirst = true;

	private int maxIteration = MAX_ITERATION_DEFAULT;
	private int terminationCount = TERMMINATION_COUNT_DEFAULT;
	private int initialLength = INITIAL_LENGTH_DEFAULT;
	private int mutationCount = MUTATION_COUNT_DEFAULT;
	private int parentCount = PARENT_COUNT_DEFAULT;

	private final List<List<EventOccurrence>> population;
	private List<List<EventOccurrence>> additionalStartingPop;
	private final List<Mutations> mutations;
	protected final Random random = new Random();
	protected FBType type;

	protected AbstractGeneticInputGenerator(final FBType type) {
		this.type = type;
		population = new ArrayList<>();
		mutations = new ArrayList<>();
		parentCount = 2;
	}

	public void setMutationCount(final int mutationCount) {
		this.mutationCount = mutationCount;
	}

	public void setAddStartPop(final List<List<EventOccurrence>> additionalStartingPop) {
		this.additionalStartingPop.addAll(EcoreUtil.copyAll(additionalStartingPop));
	}

	public void setiInitialLength(final int initialLength) {
		this.initialLength = initialLength;
	}

	public void setTerminationCount(final int terminationCount) {
		this.terminationCount = terminationCount;
	}

	public void setMaxIteration(final int maxIteration) {
		this.maxIteration = maxIteration;
	}

	public void setCrossover(final boolean crossover, final boolean crossoverFirst) {
		this.crossover = crossover;
		this.crossoverFirst = crossoverFirst;
	}

	public boolean toggelMutation(final Mutations m) {
		if (!mutations.remove(m)) { // mutations has at most 4 elements ->java:S2250 does't apply
			mutations.add(m);
			return true;
		}
		return false;
	}

	public List<EventOccurrence> runAlgorithm() {
		setupPopulation();
		if (mutations.isEmpty() || terminationCount < 0 || (!crossover && mutationCount < 1)) {	// no mutation takes
																									// place
			sort(population);
			return population.get(BEST_FITNESS);
		}
		final List<EventOccurrence> oldbest = new ArrayList<>();
		oldbest.addAll(population.get(BEST_FITNESS));
		int termination = 0;
		int count = 0;
		do {
			makeDescendent();
			sort(population);
			population.subList(parentCount, population.size()).clear();
			if (compareBest(population.get(BEST_FITNESS), oldbest)) {
				termination++;

			} else {
				oldbest.clear();
				oldbest.addAll(0, population.get(BEST_FITNESS));
				termination = 0;
			}
			count++;
		} while (termination < terminationCount && count < maxIteration);
		return population.get(BEST_FITNESS);
	}

	public static void printList(final List<EventOccurrence> parent) {
		parent.forEach(eo -> System.out.print(eo.getEvent().getName() + " -> ")); //$NON-NLS-1$
	}

	protected abstract double calculateFitness(final List<EventOccurrence> candidate);

	protected static void refreshSequence(final List<EventOccurrence> newParent) {
		newParent.forEach(eo -> {
			eo.setActive(true);
			eo.setIgnored(false);
		});
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

	protected void deleteEvent(final List<EventOccurrence> mutant) {
		final int pos = random.nextInt(mutant.size());
		final List<EventOccurrence> newMutant = new ArrayList<>();
		if (pos > 1) {
			newMutant.addAll(EcoreUtil.copyAll(mutant.subList(0, pos)));
		}
		if (pos + 1 < mutant.size()) {
			newMutant.addAll(EcoreUtil.copyAll(mutant.subList(pos + 1, mutant.size())));
		}
		mutant.clear();
		mutant.addAll(newMutant);
	}

	protected void insertEvent(final List<EventOccurrence> mutant) {
		final int pos = random.nextInt(mutant.size());
		mutant.add(pos, EventOccFactory.createFrom(InputGenerator.getRandomEventsSequence(type, 1), null).get(0));
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

	private static int compareLists(final Map<List<EventOccurrence>, Double> fitlist, final List<EventOccurrence> o1,
			final List<EventOccurrence> o2) {
		return Double.compare(fitlist.get(o1).doubleValue(), fitlist.get(o2).doubleValue()) * -1;
	}

	private Map<List<EventOccurrence>, Double> calculateFitnessAll(final List<List<EventOccurrence>> candidates) {
		final Map<List<EventOccurrence>, Double> fitnessMap = new HashMap<>();
		for (final List<EventOccurrence> sequence : candidates) {
			fitnessMap.put(sequence, Double.valueOf(calculateFitness(sequence)));
		}
		return fitnessMap;
	}

	private void setupPopulation() {
		if (additionalStartingPop != null) {
			final int addPopSize = additionalStartingPop.size();
			for (int i = 0; i < addPopSize; i++) {
				population.add(i, additionalStartingPop.get(i));
			}
			if (addPopSize > parentCount) {
				parentCount = addPopSize;
			} else {
				for (int i = addPopSize; i < parentCount; i++) {
					population.add(EventOccFactory
							.createFrom(InputGenerator.getRandomEventsSequence(type, initialLength), null));
				}
			}
		} else {
			for (int i = 0; i < parentCount; i++) {
				population.add(
						EventOccFactory.createFrom(InputGenerator.getRandomEventsSequence(type, initialLength), null));
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	private void mutate(final List<EventOccurrence> candidate) {
		final List<EventOccurrence> mutant = (List<EventOccurrence>) EcoreUtil.copyAll(candidate);
		for (int i = 0; i < mutationCount; i++) {
			final int rand = random.nextInt(mutations.size());
			switch (mutations.get(rand)) {
			case INSERT_EVENT:
				insertEvent(mutant);
				break;
			case DELETE_EVENT:
				deleteEvent(mutant);
				break;
			case CHANGE_ORDER:
				changeOrder(mutant);
				break;
			case REPLACE_EVENT:
				if (type.getInterfaceList().getEventInputs().size() > 1) {
					replaceEvent(mutant);
				}
				break;
			}
		}
		population.add(mutant);
	}

	private void makeDescendent() {
		if (crossover && crossoverFirst) {
			makeCrossover(population, parentCount);
			sort(population);
			population.subList(parentCount, population.size()).clear();
			mutateAll(mutationCount);
		} else if (crossover) {
			mutateAll(mutationCount);
			sort(population);
			population.subList(parentCount, population.size()).clear();
			makeCrossover(population, parentCount);
		} else {
			mutateAll(mutationCount);
		}
	}

	private void mutateAll(final int mutationCount) {
		if (mutationCount > 0) {
			for (int i = 0; i < parentCount; i++) {
				mutate(population.get(i));
			}
		}
	}

	private void makeCrossover(final List<List<EventOccurrence>> parents, final int parentCount) {
		for (int i = 0; i < parentCount; i++) {
			for (int j = i + 1; j < parentCount; j++) {
				crossover(parents.get(i), parents.get(j), parents);
			}
		}
	}

	private void crossover(final List<EventOccurrence> parentA, final List<EventOccurrence> parentB,
			final List<List<EventOccurrence>> append) {
		final List<EventOccurrence> crossA = (List<EventOccurrence>) EcoreUtil.copyAll(parentA);
		final List<EventOccurrence> crossB = (List<EventOccurrence>) EcoreUtil.copyAll(parentB);
		final int[] locationA = getCrossoverLocation(parentA);
		final int[] locationB = getCrossoverLocation(parentB);
		final List<EventOccurrence> crossSectionA = (List<EventOccurrence>) EcoreUtil
				.copyAll(crossA.subList(locationA[0], locationA[1]));
		final List<EventOccurrence> crossSectionB = (List<EventOccurrence>) EcoreUtil
				.copyAll(crossB.subList(locationB[0], locationB[1]));
		append.add(setCrossoverSequence(locationA[0], locationA[1], crossSectionB, crossA));
		append.add(setCrossoverSequence(locationB[0], locationB[1], crossSectionA, crossB));

	}
}
