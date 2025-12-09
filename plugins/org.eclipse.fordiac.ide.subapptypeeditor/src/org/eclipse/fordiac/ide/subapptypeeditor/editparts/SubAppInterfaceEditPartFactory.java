/*******************************************************************************
 * Copyright (c) 2014, 2023 fortiss GmbH
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
package org.eclipse.fordiac.ide.subapptypeeditor.editparts;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class SubAppInterfaceEditPartFactory extends FBInterfaceEditPartFactory {

	public SubAppInterfaceEditPartFactory(final GraphicalEditor editor, final TypeLibrary typeLib) {
		super(editor, typeLib);
	}

	@Override
	protected EditPart createInterfaceEditPart(final VarDeclaration varDecl) {
		return new InterfaceEditPart() {
			@Override
			protected void createEditPolicies() {
				super.createEditPolicies();
				// supapplications don't have a with construct therefore remove connection
				// handles
				removeEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE);
			}
		};
	}

}
