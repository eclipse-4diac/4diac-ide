/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
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

import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class GeneticInputGeneratorWithFitness extends AbstractGeneticInputGenerator {

	public GeneticInputGeneratorWithFitness(final FBType type) {
		super(type);
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
