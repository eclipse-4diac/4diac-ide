/*******************************************************************************
 * Copyright (c) 2011, 2025 Profactor GmbH, TU Wien ACIN, fortiss GmbH,,
 *                          Primetals Technologies Austria GmbH
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

import org.eclipse.fordiac.ide.fbtypeeditor.model.AbstractContainerElement;
import org.eclipse.fordiac.ide.fbtypeeditor.model.CommentPinProperty;
import org.eclipse.fordiac.ide.fbtypeeditor.model.TypePinProperty;
import org.eclipse.fordiac.ide.fbtypeeditor.model.WithPinProperty;
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
		return switch (modelElement) {
		case final FBType _ when context == null -> new FBTypeRootEditPart();
		case final FBType _ when context instanceof FBTypeRootEditPart -> new FBTypeEditPart();
		case final AbstractContainerElement _ -> new InterfaceContainerEditPart();
		case final Event _ -> new InterfaceEditPart();
		case final VarDeclaration varDecl -> createInterfaceEditPart(varDecl);
		case final AdapterDeclaration _ -> new AdapterInterfaceEditPart();
		case final With _ -> new WithEditPart();
		case final CommentPinProperty _ -> new CommentEditPart();
		case final TypePinProperty _ -> new TypeEditPart(typeLib);
		case final WithPinProperty _ -> new WithEndPointEditPart();
		default -> throw createEditpartCreationException(context, modelElement);
		};
	}

	// make it protected none static so that subclasses can override it and provide
	// own interface edit parts if needed
	@SuppressWarnings("static-method")
	protected EditPart createInterfaceEditPart(final VarDeclaration varDecl) {
		return new InterfaceEditPart();
	}
}
