/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial implementation
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.WithEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class FBInterfaceEditPartFactory
		extends org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory {

	public FBInterfaceEditPartFactory(final GraphicalEditor editor, final TypeLibrary typeLib) {
		super(editor, typeLib);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof FBTypeEditorInput && context == null) {
			return new FBTypeRootEditPart();
		}
		if (modelElement instanceof FB) {
			return new TesterFBEditPart();
		}
		if (modelElement instanceof IInterfaceElement) {
			return new InterfaceEditPart();
		}

		if (modelElement instanceof Value) {
			final ValueEditPart part = new ValueEditPart();
			return part;
		}
		if (modelElement instanceof TestElement) {
			return ((TestElement) modelElement).createEditPart();
		}
		if (modelElement instanceof With) {
			return new WithEditPart();
		}
		return super.getPartForElement(context, modelElement);
	}
}
