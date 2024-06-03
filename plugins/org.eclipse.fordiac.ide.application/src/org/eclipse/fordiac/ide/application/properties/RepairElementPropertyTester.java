/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.handlers.RepairCommandHandler;
import org.eclipse.gef.EditPart;
import org.eclipse.ui.views.markers.MarkerItem;

public class RepairElementPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		return isSupported(receiver);
	}

	protected static boolean isSupported(final Object element) {

		// the error marker from the problem view are always coming in a list
		if (element instanceof final List markerList) {
			return isMarkerFromProblemsView(markerList);
		}

		if (element instanceof final EditPart editPart) {
			return isErrorMarkerFromEditor(editPart.getModel());
		}

		return false;
	}

	private static boolean isErrorMarkerFromEditor(final Object model) {
		return RepairCommandHandler.getEObjectFromEditorSelection(model) != null;
	}

	private static boolean isMarkerFromProblemsView(final List<?> markerList) {
		for (final var marker : markerList) {
			if (!(marker instanceof final MarkerItem markerItem)) {
				return false;
			}
			final EObject target = RepairCommandHandler.getEObjectFromMarkerItem(markerItem);
			if (target == null) {
				return false;
			}
		}
		return true;
	}

}
