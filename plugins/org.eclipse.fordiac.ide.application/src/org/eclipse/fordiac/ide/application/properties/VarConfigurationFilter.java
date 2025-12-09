/*******************************************************************************
 * Copyright (c) 2023, 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - Refactored it to a one liner
 *   Sebastian Hollersbacher - Corrected Valid Elements
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class VarConfigurationFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return getModel(toTest) != null;
	}

	public static INamedElement getModel(final Object toTest) {
		if (toTest instanceof final EditPart ep) {
			return switch (ep.getModel()) {
			case final Application app -> app;
			case final SubApp subApp when !subApp.isContainedInTypedInstance() -> subApp;
			case final FBNetwork fbNetwork when fbNetwork.eContainer() instanceof SubAppType
					|| fbNetwork.eContainer() instanceof Application
					|| fbNetwork.eContainer() instanceof final UntypedSubApp utsa
							&& !utsa.isContainedInTypedInstance() ->
				(INamedElement) fbNetwork.eContainer();
			default -> null;
			};
		}
		return null;
	}
}