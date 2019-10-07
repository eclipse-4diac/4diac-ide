/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECCRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECTransitionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

abstract class ECCSection extends AbstractECSection {

	@Override
	protected BasicFBType getType() {
		if (type instanceof ECC) {
			return ((ECC) type).getBasicFBType();
		}
		return null;
	}

	@Override
	protected Object getInputType(Object input) {
		if (input instanceof ECCRootEditPart) {
			return ((ECCRootEditPart) input).getCastedECCModel();
		}
		if (input instanceof ECC) {
			return input;
		}
		if (input instanceof ECActionAlgorithmEditPart) {
			return ((ECActionAlgorithmEditPart) input).getAction().getECState().getECC();
		}
		if (input instanceof ECActionOutputEventEditPart) {
			ECAction action = ((ECActionOutputEventEditPart) input).getAction();
			if (null != action && null != action.getECState()) {
				return action.getECState().getECC();
			}
		}
		if (input instanceof ECTransitionEditPart) {
			return ((ECTransitionEditPart) input).getModel().getECC();
		}
		if (input instanceof ECStateEditPart) {
			return ((ECStateEditPart) input).getCastedModel().getECC();
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
	}
}
