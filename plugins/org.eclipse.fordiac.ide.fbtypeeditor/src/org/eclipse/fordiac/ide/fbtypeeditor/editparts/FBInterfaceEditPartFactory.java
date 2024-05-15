/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class FBInterfaceEditPartFactory extends Abstract4diacEditPartFactory {

	private final TypeLibrary typeLib;

	public FBInterfaceEditPartFactory(final GraphicalEditor editor, final TypeLibrary typeLib) {
		super(editor);
		this.typeLib = typeLib;
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof FBType && context == null) {
			return new FBTypeRootEditPart();
		}
		if (modelElement instanceof FBType && context instanceof FBTypeRootEditPart) {
			return new FBTypeEditPart();
		}
		if (modelElement instanceof AbstractContainerElement) {
			return new InterfaceContainerEditPart();
		}

		if (modelElement instanceof Event) {
			return new InterfaceEditPart();
		}
		if (modelElement instanceof final VarDeclaration varDecl) {
			return createInterfaceEditPart(varDecl);
		}
		if (modelElement instanceof AdapterDeclaration) {
			return new AdapterInterfaceEditPart();
		}
		if (modelElement instanceof With) {
			return new WithEditPart();
		}
		if (modelElement instanceof CommentTypeField) {
			return new CommentTypeEditPart();
		}
		if (modelElement instanceof CommentTypeField.CommentTypeSeparator) {
			return new CommentTypeSeparatorEditPart();
		}
		if (modelElement instanceof CommentField) {
			return new CommentEditPart();
		}
		if (modelElement instanceof TypeField) {
			return new TypeEditPart(typeLib);
		}
		throw createEditpartCreationException(modelElement);
	}

	// make it protected none static so that subclasses can override it and provide
	// own interface edit parts if needed
	@SuppressWarnings("static-method")
	protected EditPart createInterfaceEditPart(final VarDeclaration varDecl) {
		return new InterfaceEditPart();
	}
}
