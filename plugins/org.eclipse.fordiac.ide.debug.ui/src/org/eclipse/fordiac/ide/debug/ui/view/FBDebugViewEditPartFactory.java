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

import org.eclipse.fordiac.ide.debug.ui.view.editparts.FBDebugViewRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeEditPart;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.EditPart;

public class FBDebugViewEditPartFactory extends FBInterfaceEditPartFactory {

	public FBDebugViewEditPartFactory() {
		super(null, null);  // in our case both can safely be null
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof FBEvaluator<?>) {
			return new FBDebugViewRootEditPart();
		}
		if (modelElement instanceof FBType) {
			// we can not use the version of parent as this expects a FBTypeRootEditPart as context which we don't have
			// here
			return new FBTypeEditPart();
		}
		return super.getPartForElement(context, modelElement);
	}
}
