/*******************************************************************************
 * Copyright (c) 2016, 2025 fortiss GmbH, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.model.ui.annotation.GraphicalAnnotation;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public abstract class Abstract4diacEditPartFactory implements EditPartFactory {

	private final GraphicalEditor editor;

	protected Abstract4diacEditPartFactory(final GraphicalEditor editor) {
		this.editor = editor;
	}

	public GraphicalEditor getEditor() {
		return editor;
	}

	@Override
	public EditPart createEditPart(final EditPart context, final Object modelElement) {
		// get EditPart for model element
		final EditPart part = switch (modelElement) {
		// this if needs be the first check so that plugins can more easily provide
		// special behavior for derived classes
		// (e.g., interface elements for monitored adapters)
		case final IEditPartCreator epCreator -> epCreator.createEditPart();
		case final IConnectionEditPartCreator connCreator -> connCreator.createEditPart();
		case final GraphicalAnnotation annotation -> GraphicalAnnotationStyles.getAnnotationEditPart(annotation);
		default -> getPartForElement(context, modelElement);
		};
		if (null == part) {
			throw createEditpartCreationException(context, modelElement);
		}
		part.setModel(modelElement);
		return part;
	}

	/**
	 * Maps an object to an EditPart.
	 *
	 * @throws IllegalArgumentException if no match was found (programming error)
	 */
	protected abstract EditPart getPartForElement(EditPart context, Object modelElement);

	protected static RuntimeException createEditpartCreationException(final EditPart context,
			final Object modelElement) {
		return new IllegalArgumentException(
				MessageFormat.format("Can''t create part for model element: {0} for context {1}", //$NON-NLS-1$
						getClassName(modelElement), getClassName(context)));
	}

	protected static String getClassName(final Object obj) {
		return (obj != null) ? obj.getClass().getName() : "null"; //$NON-NLS-1$
	}
}
