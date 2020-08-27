/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
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

import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public abstract class Abstract4diacEditPartFactory implements EditPartFactory {

	private GraphicalEditor editor;

	public Abstract4diacEditPartFactory(GraphicalEditor editor) {
		super();
		this.editor = editor;
	}

	public GraphicalEditor getEditor() {
		return editor;
	}

	@Override
	public EditPart createEditPart(EditPart context, Object modelElement) {
		// get EditPart for model element
		EditPart part = null;
		try {
			if (modelElement instanceof IEditPartCreator) {
				// this if needs be the first check so that plugins can more easily provide
				// special behavior for derived classes
				// (e.g., interface elements for monitored adapters)
				part = ((IEditPartCreator) modelElement).createEditPart();
			} else if (modelElement instanceof IConnectionEditPartCreator) {
				part = ((IConnectionEditPartCreator) modelElement).createEditPart();
			} else {
				part = getPartForElement(context, modelElement);
			}
			if (null != part) {
				part.setModel(modelElement);
			}
		} catch (RuntimeException e) {
			Activator.getDefault().logError(Messages.Abstract4diacEditPartFactory_ERROR_CantCreatePartForModelElement,
					e);
		}
		return part;
	}

	/**
	 * Maps an object to an EditPart.
	 *
	 * @throws RuntimeException if no match was found (programming error)
	 */
	protected abstract EditPart getPartForElement(EditPart context, Object modelElement);

	protected RuntimeException createEditpartCreationException(final Object modelElement) {
		return new RuntimeException(MessageFormat.format(
				Messages.Abstract4diacEditPartFactory_LABEL_RUNTIMEException_CantCreateModelForElement,
				((modelElement != null) ? modelElement.getClass().getName() : "null"))); //$NON-NLS-1$
	}
}
