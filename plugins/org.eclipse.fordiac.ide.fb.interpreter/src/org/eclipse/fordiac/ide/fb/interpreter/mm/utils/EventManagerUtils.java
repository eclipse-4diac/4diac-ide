/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.mm.utils;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;

public class EventManagerUtils {

	private EventManagerUtils() {
		throw new AssertionError("This class cannot be inherited"); //$NON-NLS-1$
	}

	public static final void process(EventManager eventManager) {
		final var transactions = eventManager.getTransactions();
		for (var i = 0; i < transactions.size(); i++) {
			final var transaction = transactions.get(i);
			if (transaction instanceof FBTransaction) {
				FBTransaction fbTransaction = (FBTransaction) transaction;
				final var result = transaction.getInputEventOccurrence().getFbRuntime().run(eventManager);
				fbTransaction.getOutputEventOccurrences().addAll(result);
				if ((i + 1) < transactions.size()) {
					// TODO use ECoreUtil.copyAll(list of things to copy);
					final var copyfbRuntime = new Copier();
					final FBRuntimeAbstract newfbRuntime;
					// choose the latest: input event occurr. or last output event occurr.
					if (fbTransaction.getOutputEventOccurrences().isEmpty()) {
						newfbRuntime = (FBRuntimeAbstract) copyfbRuntime.copy(transaction.getInputEventOccurrence().getFbRuntime());
					} else {
						newfbRuntime = (FBRuntimeAbstract) copyfbRuntime.copy(
								fbTransaction.getOutputEventOccurrences().get(fbTransaction.getOutputEventOccurrences().size() - 1).
								getFbRuntime());
					}
					copyfbRuntime.copyReferences();
					transactions.get(i + 1).getInputEventOccurrence().setFbRuntime(newfbRuntime);
				}
			}
		}
	}

}
