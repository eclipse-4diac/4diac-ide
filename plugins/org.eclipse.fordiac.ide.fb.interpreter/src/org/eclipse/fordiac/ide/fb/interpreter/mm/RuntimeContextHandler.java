/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.mm;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

/**
 * @brief Keeps the context of the runtimes intact after processing a
 *        transaction
 *
 *        Network and composite runtimes contain other runtimes, When assigning
 *        these runtimes to the event occurrence for their execution, the model
 *        steals these runtimes. This class remembers the original context and
 *        moves back the used runtime to where it belonged.
 *
 */
public class RuntimeContextHandler implements AutoCloseable {

	// Represent the runtime to be used to execute a FB, along with the
	// FBNetworkRuntime where the runtime is stored.

	/**
	 * @brief Information needed to restore the original context
	 *
	 * @param runtimeContainer the network runtime that holds the runtime to be used
	 * @param compositeRuntime if the runtime belongs to a composite runtime, this
	 *                         parameter is used. If not used, this is set to null
	 * @param runtimeElement   FB network element from the runtime to be used is
	 *                         mapped
	 * @param runtime          runtime to be used by the event occurrence
	 */
	private record ExecutionContext(FBNetworkRuntime runtimeContainer, CompositeFBTypeRuntime compositeRuntime,
			FBNetworkElement runtimeElement, FBRuntimeAbstract runtime) {
	}

	private ExecutionContext executionContext;

	/**
	 * @brief Stores the original context of the runtime to be used and assigns the
	 *        runtime to the event occurrence
	 *
	 * @param parentRuntime   the parent runtime to look for the context recursively
	 * @param eventOccurrence the event occurrence containing the parent FB and
	 *                        where the runtime is assigned
	 */
	public RuntimeContextHandler(final FBNetworkRuntime parentRuntime, final EventOccurrence eventOccurrence) {

		executionContext = getExecutionContext(null, null, parentRuntime, eventOccurrence.getParentFB());
		if (executionContext == null) {
			executionContext = new ExecutionContext(null, null, null, parentRuntime);
		}

		eventOccurrence.setFbRuntime(executionContext.runtime);
	}

	@Override
	public void close() {
		if (executionContext.runtimeContainer == null) {
			// the runtime was not moved from anywhere
			return;
		}

		if (executionContext.compositeRuntime != null) {
			executionContext.compositeRuntime.setNetworkRuntime((FBNetworkRuntime) executionContext.runtime);
		} else {
			executionContext.runtimeContainer.getTypeRuntimes().put(executionContext.runtimeElement,
					executionContext.runtime);
		}
	}

	private ExecutionContext getExecutionContext(final FBNetworkRuntime runtimeContainer,
			final FBNetworkElement runtimeElement, final FBNetworkRuntime fbNetworkRuntime,
			final FBNetworkElement searchedFB) {

		if (fbNetworkRuntime.getTypeRuntimes().get(searchedFB) != null) {
			return new ExecutionContext(runtimeContainer, null, runtimeElement, fbNetworkRuntime);
		}

		for (final var entry : fbNetworkRuntime.getTypeRuntimes()) {
			if (entry.getValue() instanceof final FBNetworkRuntime fbNetwork) {
				final var possibleContext = getExecutionContext(fbNetworkRuntime, entry.getKey(), fbNetwork,
						searchedFB);
				if (possibleContext != null) {
					return possibleContext;
				}
			} else if (entry.getValue() instanceof final CompositeFBTypeRuntime compositeFBTypeRuntime) {
				final var possibleContext = getExecutionContext(fbNetworkRuntime, entry.getKey(),
						compositeFBTypeRuntime, searchedFB);
				if (possibleContext != null) {
					return possibleContext;
				}
			}
		}

		return null;
	}

	private ExecutionContext getExecutionContext(final FBNetworkRuntime runtimeContainer,
			final FBNetworkElement runtimeElement, final CompositeFBTypeRuntime compositeRuntime,
			final FBNetworkElement searchedFB) {

		if (compositeRuntime.getNetworkRuntime().getTypeRuntimes().get(searchedFB) != null) {
			return new ExecutionContext(runtimeContainer, compositeRuntime, runtimeElement,
					compositeRuntime.getNetworkRuntime());
		}

		for (final var entry : compositeRuntime.getNetworkRuntime().getTypeRuntimes()) {
			if (entry.getValue() instanceof final FBNetworkRuntime fbNetwork) {
				final var possibleContext = getExecutionContext(compositeRuntime.getNetworkRuntime(), entry.getKey(),
						fbNetwork, searchedFB);
				if (possibleContext != null) {
					return possibleContext;
				}
			} else if (entry.getValue() instanceof final CompositeFBTypeRuntime compositeFBTypeRuntime) {
				final var possibleContext = getExecutionContext(compositeRuntime.getNetworkRuntime(), entry.getKey(),
						compositeFBTypeRuntime, searchedFB);
				if (possibleContext != null) {
					return possibleContext;
				}
			}
		}

		return null;
	}

}
