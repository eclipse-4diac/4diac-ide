/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH, fortiss GmbH,
 *                    Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

/**
 * A factory for creating ECCEditPart objects.
 */
public class ECCEditPartFactory extends Abstract4diacEditPartFactory {

	public ECCEditPartFactory(final GraphicalEditor editor) {
		super(editor);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		return switch (modelElement) {
		case final ECC ecc -> new ECCEditPart();
		case final ECState state -> new ECStateEditPart();
		case final ECTransition trans -> new ECTransitionEditPart();
		case final ECActionAlgorithm alg -> new ECActionAlgorithmEditPart();
		case final ECActionOutputEvent eo -> new ECActionOutputEventEditPart();
		default -> throw createEditpartCreationException(context, modelElement);
		};
	}

}
