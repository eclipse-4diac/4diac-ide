/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.gef.utilities;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.helpers.FBShapeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.preferences.ModelPreferenceConstants;
import org.eclipse.fordiac.ide.model.preferences.PreferenceProvider;

public class MarginBoundsHelper {
	private int marginTopBottom = 0;
	private int marginLeftRight = 0;

	public void updateMargins(final Object editPartModel) {
		if (editPartModel instanceof final PositionableElement posElement
				&& EcoreUtil.getRootContainer(posElement) instanceof final LibraryElement libElement) {
			final IProject project = libElement.getTypeEntry().getFile().getProject();
			marginLeftRight = getDisplayMargin(project, ModelPreferenceConstants.MARGIN_LEFT_RIGHT);
			marginTopBottom = getDisplayMargin(project, ModelPreferenceConstants.MARGIN_TOP_BOTTOM);
		}
	}

	private static int getDisplayMargin(final IProject project, final String modelPreferenceKey) {
		final var modelMargin = PreferenceProvider.getInt(ModelPreferenceConstants.MODEL_PREFERENCES_ID,
				modelPreferenceKey, 0, project);
		return CoordinateConverter.INSTANCE.iec61499ToScreen(modelMargin * FBShapeHelper.IEC61499_LINE_HEIGHT);
	}

	public void expandRectangle(final Rectangle rectangle) {
		rectangle.expand(marginLeftRight, marginTopBottom);
	}
}
