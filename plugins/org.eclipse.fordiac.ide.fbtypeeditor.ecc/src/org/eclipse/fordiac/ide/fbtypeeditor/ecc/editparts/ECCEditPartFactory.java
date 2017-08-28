/*******************************************************************************
 * Copyright (c) 2008, 2012, 2013 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	public ECCEditPartFactory(GraphicalEditor editor) {
		super(editor);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context,
			final Object modelElement) {
		if (modelElement instanceof ECC) {
			return new ECCRootEditPart();
		}
		if (modelElement instanceof ECState) {
			return new ECStateEditPart();
		}
		if (modelElement instanceof ECTransition) {
			return new ECTransitionEditPart();
		}
				
		if (modelElement instanceof ECActionAlgorithm){
			return new ECActionAlgorithmEditPart();
		}
		
		if (modelElement instanceof ECActionOutputEvent){
			return new ECActionOutputEventEditPart();
		}

		throw createEditpartCreationException(modelElement); 
	}

}
