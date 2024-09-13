/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.resourceediting.editparts;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppInterfaceElementEditPart;
import org.eclipse.gef.EditPart;

public class UntypedSubAppInterfaceElementEditPartForResource extends UntypedSubAppInterfaceElementEditPart {

	@Override
	protected IFigure createFigure() {
		final IFigure fig = super.createFigure();
		fig.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorRemoved(final IFigure ancestor) {
				// currently nothing to be done here
			}

			@Override
			public void ancestorMoved(final IFigure ancestor) {
				updateReferenced();
			}

			@Override
			public void ancestorAdded(final IFigure ancestor) {
				// currently nothing to be done here
			}
		});

		return fig;
	}

	private void updateReferenced() {
		EditPart parent = getParent();
		while (parent != null && !(parent instanceof FBNetworkContainerEditPart)) {
			parent = parent.getParent();
		}
		if (parent != null) {
			final FBNetworkContainerEditPart fbcep = (FBNetworkContainerEditPart) parent;
			final VirtualIO referencedElement = fbcep.getVirtualIOElement(getModel());
			if ((referencedElement != null) && (getViewer()
					.getEditPartForModel(referencedElement) instanceof final VirtualInOutputEditPart virtIOEP)) {
				virtIOEP.updatePos(this);
			}
		}
	}

}
