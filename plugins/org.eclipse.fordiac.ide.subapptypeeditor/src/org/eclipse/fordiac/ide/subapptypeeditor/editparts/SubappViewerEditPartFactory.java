/*******************************************************************************
 * Copyright (c) 2021 Primemetals Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class SubappViewerEditPartFactory extends CompositeViewerEditPartFactory {

	public SubappViewerEditPartFactory(final GraphicalEditor editor, final FBNetworkElement fbInstance,
			final EditPart fbEditPart2) {
		super(editor, fbInstance, fbEditPart2);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof FBNetwork && ((FBNetwork) modelElement).eContainer() instanceof SubAppType) {
			final SubAppInstanceViewerEditPart edit = new SubAppInstanceViewerEditPart();
			edit.setFbInstance(fbInstance);
			if (fbEditPart != null && fbEditPart.getParent() instanceof SubAppInstanceViewerEditPart) {
				edit.setparentInstanceViewerEditPart((SubAppInstanceViewerEditPart) fbEditPart.getParent());
			}
			return edit;
		}
		return super.getPartForElement(context, modelElement);
	}

}
