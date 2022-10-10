/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois ZOitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.EditPart;

public class FBDebugViewEditPartFactory extends FBInterfaceEditPartFactory {

	public FBDebugViewEditPartFactory() {
		super(null, null);  // in our case both can safely be null
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof FBType && context == null) {
			return new FBTypeRootEditPart() {
				@Override
				protected List<?> getModelChildren() {
					final ArrayList<Object> children = new ArrayList<>(1);
					children.add(getModel());
					return children;
				}
			};
		}
		return super.getPartForElement(context, modelElement);
	}
}
