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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventOccFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.EventManagerUtils;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class GeneticInputGeneratorWithCrossover extends AbstractGeneticInputGenerator {
	private static final int MAX_ITERATION_DEFAULT = 11;
	private static final int TERMINATION_COUNT_DEFAULT = 4;
	private int parentCount;
	private final List<List<EventOccurrence>> best;

	public GeneticInputGeneratorWithCrossover(final FBType type, final int initialLength) {
		this(type, initialLength, 2);
	}

	public GeneticInputGeneratorWithCrossover(final FBType type, final int initialLength, final int parentCount) {
		super(type);
		best = new ArrayList<>();
		if (parentCount < 2) {
			this.parentCount = 2;
		} else {
			this.parentCount = parentCount;
		}
		for (int i = 0; i < parentCount; i++) {
			best.add(EventOccFactory.createFrom(InputGenerator.getRandomEventsSequence(type, initialLength), null));
		}
	}

	@Override
	public List<EventOccurrence> runAlgorithm() {
		return runAlgorithm(MAX_ITERATION_DEFAULT);
	}

	public List<EventOccurrence> runAlgorithm(final int maxIterations,
			final List<List<EventOccurrence>> additionalStartingPop) {
		best.addAll(additionalStartingPop);
		return runAlgorithm(maxIterations);
	}

	public List<EventOccurrence> runAlgorithm(final int maxIterations) {
		int count = 0;
		do {
			makeCrossover(best, parentCount);
			sort(best);
			best.subList(parentCount, best.size()).clear();
			count++;
		} while (count < maxIterations);
		return best.get(0);
	}

	public List<EventOccurrence> runAlgorithm(final boolean terminationCondition, final int terminationCount) {
		if (!terminationCondition) {
			return runAlgorithm();
		}
		final List<EventOccurrence> oldbest = new ArrayList<>();
		oldbest.addAll(best.get(0));
		int termination = 0;
		do {
			makeCrossover(best, parentCount);
			sort(best);
			best.subList(parentCount, best.size()).clear();
			if (compareBest(best.get(0), oldbest)) {
				termination++;

			} else {
				oldbest.clear();
				oldbest.addAll(0, best.get(0));
				termination = 0;
			}
		} while (termination < terminationCount);
		return best.get(0);
	}

	public List<EventOccurrence> runAlgorithm(final boolean terminationCondition) {
		return runAlgorithm(terminationCondition, TERMINATION_COUNT_DEFAULT);
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

	@Override
	protected double calculateFitness(final List<EventOccurrence> candidate) {
		if (candidate.isEmpty()) {
			return Double.NEGATIVE_INFINITY;
		}
		final FBRuntimeAbstract rt = RuntimeFactory.createFrom(type);
		candidate.get(0).setFbRuntime(rt);
		final List<FBTransaction> transactions = TransactionFactory.createFrom(candidate);
		final EventManager eventManager = EventManagerFactory.createFrom(transactions);
		EventManagerUtils.process(eventManager);
		final int outputEOs = countOutputEventOccurrences(eventManager.getTransactions());
		return ((double) outputEOs) / eventManager.getTransactions().size();

	}
}
