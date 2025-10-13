/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.annotation;

import java.text.MessageFormat;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.util.marker.MarkerDescriptor;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class UtilityMarkerAnnotationStyler implements GraphicalAnnotationStyler {

	@Override
	public void applyStyles(final IFigure figure, final GraphicalAnnotation annotation) {
		final Color annotationColor = getColor(annotation);
		if (annotationColor != null) {
			GraphicalAnnotationStyles.setAnnotationFeedbackBorder(figure, annotationColor);
		}
		final IContributionItem statusLineItem = getStatusLineContributionItem(annotation);
		if (statusLineItem != null) {
			getStatusLineManager().add(statusLineItem);
			getStatusLineManager().update(true);
		}
	}

	@Override
	public void removeStyles(final IFigure figure, final GraphicalAnnotation annotation) {
		GraphicalAnnotationStyles.removeAnnotationBorders(figure);
		switch (annotation.getType()) {
		case GraphicalAnnotation.TYPE_UTIL_PREDECESSOR:
			getStatusLineManager().remove(MarkerDescriptor.PREDECESSOR.ID());
			break;
		case GraphicalAnnotation.TYPE_UTIL_CONNECTION_SRC:
			getStatusLineManager().remove(MarkerDescriptor.CONNECTION_SOURCE.ID());
			break;
		default:
			break;
		}
		getStatusLineManager().update(true);
	}

	@Override
	public Color getColor(final GraphicalAnnotation annotation) {
		return switch (annotation.getType()) {
		case GraphicalAnnotation.TYPE_UTIL_PREDECESSOR -> MarkerDescriptor.PREDECESSOR.color();
		case GraphicalAnnotation.TYPE_UTIL_CONNECTION_SRC -> MarkerDescriptor.CONNECTION_SOURCE.color();
		default -> null;
		};
	}

	private static IContributionItem getStatusLineContributionItem(final GraphicalAnnotation annotation) {
		switch (annotation.getType()) {
		case GraphicalAnnotation.TYPE_UTIL_PREDECESSOR:
			if (annotation.getTarget() instanceof final INamedElement elem) {
				final String text = MessageFormat.format(Messages.UtilityMarker_ActiveMarker,
						MarkerDescriptor.PREDECESSOR.name(), elem.getQualifiedName());
				return createStatusLineItem(MarkerDescriptor.PREDECESSOR.ID(), text);
			}
			break;
		case GraphicalAnnotation.TYPE_UTIL_CONNECTION_SRC:
			if (annotation.getTarget() instanceof final INamedElement elem) {
				final String text = MessageFormat.format(Messages.UtilityMarker_ActiveMarker,
						MarkerDescriptor.CONNECTION_SOURCE.name(), elem.getQualifiedName());
				return createStatusLineItem(MarkerDescriptor.CONNECTION_SOURCE.ID(), text);
			}
			break;
		default:
			break;
		}
		return null;
	}

	private static IContributionItem createStatusLineItem(final String id, final String text) {
		final StatusLineContributionItem item = new StatusLineContributionItem("", text.length()); //$NON-NLS-1$
		item.setId(id);
		item.setText(text);
		return item;
	}

	private static IStatusLineManager getStatusLineManager() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorSite()
				.getActionBars().getStatusLineManager();
	}

	@Override
	public Image getImage(final GraphicalAnnotation annotation) {
		return null;
	}

	@Override
	public Image getOverlayImage(final GraphicalAnnotation annotation) {
		return null;
	}

}
