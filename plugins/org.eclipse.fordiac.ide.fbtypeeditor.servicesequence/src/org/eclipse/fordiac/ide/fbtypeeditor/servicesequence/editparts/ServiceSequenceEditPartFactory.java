/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

/**
 * A factory for creating FBInterfaceEditPart objects.
 */
public class ServiceSequenceEditPartFactory extends Abstract4diacEditPartFactory {

	

	public ServiceSequenceEditPartFactory(GraphicalEditor editor) {
		super(editor);
	}

	@Override
	protected EditPart getPartForElement(EditPart context, final Object modelElement) {
		if (modelElement instanceof FBType && context == null) {
			return new SequenceRootEditPart();
		}
		if (modelElement instanceof ServiceSequence) {
			return new ServiceSequenceEditPart();
		}
		if (modelElement instanceof ServiceTransaction) {
			return new TransactionEditPart();
		}
		if (modelElement instanceof InputPrimitive) {
			return new InputPrimitiveEditPart();
		}
		if (modelElement instanceof OutputPrimitive) {
			return new OutputPrimitiveEditPart();
		}
		if (modelElement instanceof PrimitiveConnection) {
			return new PrimitiveConnectionEditPart();
		}
		if (modelElement instanceof ConnectingConnection) {
			return new ConnectingConnectionEditPart();
		}
		throw createEditpartCreationException(modelElement);
	}

}
