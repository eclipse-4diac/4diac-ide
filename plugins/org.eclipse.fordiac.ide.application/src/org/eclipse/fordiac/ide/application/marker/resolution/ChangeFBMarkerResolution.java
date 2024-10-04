/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.FBTreeNodeLabelProvider;
import org.eclipse.fordiac.ide.model.ui.nat.FBTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

public class ChangeFBMarkerResolution extends AbstractErrorMarkerResolution {

	private TypeEntry selectedEntry;
	private boolean canceled = false;

	public ChangeFBMarkerResolution(final IMarker marker) {
		super(FordiacMessages.Repair_Dialog_ChangeFBType, marker);
	}

	@Override
	public void run(final IMarker marker) {
		if (canceled) {
			return;
		}

		if (selectedEntry == null) {
			selectTypeEntry();
		}

		if (selectedEntry != null) {
			final EObject target = getTargetElement(marker);

			if (target instanceof final FB fb && fb.eContainer() instanceof final BaseFBType base
					&& base.getInternalFbs().contains(fb)) {
				AbstractLiveSearchContext.executeAndSave(new UpdateInternalFBCommand(fb, selectedEntry), fb,
						new NullProgressMonitor());
			} else if (target instanceof final FBNetworkElement fbne) {
				AbstractLiveSearchContext.executeAndSave(new UpdateFBTypeCommand(fbne, selectedEntry), fbne,
						new NullProgressMonitor());
			}
		}
	}

	private void selectTypeEntry() {
		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				FBTypeSelectionTreeContentProvider.INSTANCE, FBTreeNodeLabelProvider.INSTANCE);
		dialog.setInput(getTypeLibrary());
		if (dialog.open() == Window.OK && dialog.getFirstResult() instanceof final TypeNode node
				&& !node.isDirectory()) {
			selectedEntry = node.getTypeEntry();
		} else {
			canceled = true;
		}
	}
}
