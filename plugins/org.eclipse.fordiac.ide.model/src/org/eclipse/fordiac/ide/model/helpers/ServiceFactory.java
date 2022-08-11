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
package org.eclipse.fordiac.ide.model.helpers;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

public class ServiceFactory {
	// TODO impement class
	public static Primitive createPrimitiveFrom(final Event event, final String parameters) {
		return null;

		// if event.isInput = true: ServiceSequenceUtils.createInputPrimitive

		// else ServiceSequenceUtils.createOutputPrimitive

	}

	public static ServiceTransaction createServiceTransactionFrom(final Primitive p) {
		return null;

	}

	public static ServiceSequence createServiceSequence(final List<ServiceTransaction> transactions) {
		return null;
	}

	public static ServiceInterface createServiceInterface(final String name) {
		return null;
	}

	public static Service createDefautServiceModel() {
		return null;
		// ServiceSequenceUtils.createEmptyServiceModel
	}

	// method addServiceSequence from ServiceSequenceUtils

}
