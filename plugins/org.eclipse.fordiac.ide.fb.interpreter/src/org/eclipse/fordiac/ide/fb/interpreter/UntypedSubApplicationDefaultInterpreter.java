/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *   Paul Pavlicek - cleanup
 *   Felix Schmid - implemented functions and subapps
 *   Jose Cabral  - extracted from DefaultRunFBType
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.InterfacePinUtils;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;

public class UntypedSubApplicationDefaultInterpreter extends FBWithNetworkDefaultInterpreter {

	private final UntypedSubApp uSubApp;

	public UntypedSubApplicationDefaultInterpreter(final EventOccurrence eventOccurrence, final UntypedSubApp uSubApp) {
		super(eventOccurrence);
		this.uSubApp = uSubApp;
	}

	public EList<EventOccurrence> run(final FBNetworkRuntime fBNetworkRuntime) {

		FBNetworkRuntime runtime;

		if (InterfacePinUtils.isInput(eventOccurrence.getEvent())) { // we are entering the inner SubApp network
			runtime = RuntimeFactory.getOrCreateNetworkRuntime(fBNetworkRuntime, uSubApp);
		} else { // we are leaving the inner SubApp network
			runtime = RuntimeFactory.getOrCreateOuterNetworkRuntime(fBNetworkRuntime, uSubApp);
		}

		return switchNetwork(eventOccurrence.getEvent(), runtime);
	}
}
