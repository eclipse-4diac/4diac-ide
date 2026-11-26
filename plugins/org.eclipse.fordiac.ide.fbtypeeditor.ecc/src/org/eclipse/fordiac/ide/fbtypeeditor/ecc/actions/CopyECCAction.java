/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar Sinha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.ui.FordiacClipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class CopyECCAction extends SelectionAction {

	public CopyECCAction(final IWorkbenchPart part) {
		super(part);
		setId(ActionFactory.COPY.getId());
		setText(Messages.ECCActions_Copy);
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
	}

	@Override
	protected boolean calculateEnabled() {
		return !getSelectedStates().isEmpty();
	}

	@Override
	public void run() {
		final List<ECState> selected = getSelectedStates();
		if (selected.isEmpty()) {
			return;
		}
		final BasicFBType sourceType = selected.get(0).getECC().getBasicFBType();
		final List<ECState> copies = selected.stream().map(EcoreUtil::copy).toList();
		FordiacClipboard.INSTANCE.setGraphicalContents(new ECCCopyPasteData(copies, sourceType));
	}

	private List<ECState> getSelectedStates() {
		return ((List<?>) getSelectedObjects()).stream().filter(ECStateEditPart.class::isInstance)
				.map(ep -> ((ECStateEditPart) ep).getModel()).filter(state -> !isStartState(state)).toList();
	}

	private static boolean isStartState(final ECState state) {
		return (state.eContainer() instanceof final ECC ecc) && (ecc.getStart() == state);
	}
}
