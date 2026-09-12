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

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.ServiceInterfaceFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.mm.InterfacePinUtils;

public class ServiceInterfaceFBTypeDefaultInterpreter {

	private final EventOccurrence eventOccurrence;

	private static boolean outputInitO = true;

	public static void setOutputInitO(final boolean outputInitO) {
		ServiceInterfaceFBTypeDefaultInterpreter.outputInitO = outputInitO;
	}

	public ServiceInterfaceFBTypeDefaultInterpreter(final EventOccurrence eventOccurrence) {
		this.eventOccurrence = eventOccurrence;
	}

	public EList<EventOccurrence> run(final ServiceInterfaceFBTypeRuntime fbTypeRuntime) {
		// TODO this should probably be replaced by an extensible structure with
		// simulators to also cover other SIFB functionalities.
		if (outputInitO && this.eventOccurrence.getEvent().getName().equals("INIT")) { //$NON-NLS-1$
			final var outputEvent = InterfacePinUtils.findEventInInterface(fbTypeRuntime.getModel(), "INITO"); //$NON-NLS-1$
			return ECollections
					.asEList(Utils.createOutputEventOccurrence(fbTypeRuntime, outputEvent, fbTypeRuntime.getModel()));
		}

		// not supported
		return ECollections.emptyEList();
	}
}
