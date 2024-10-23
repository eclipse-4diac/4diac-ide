/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
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
 *   Martin Erich Jobst
 *    - refactor marker resolutions
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.FBTreeNodeLabelProvider;
import org.eclipse.fordiac.ide.model.ui.nat.FBTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class ChangeFBMarkerResolution extends AbstractCommandMarkerResolution<FBNetworkElement> {

	private TypeEntry selectedEntry;

	public ChangeFBMarkerResolution(final IMarker marker) {
		super(marker, FBNetworkElement.class);
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				FBTypeSelectionTreeContentProvider.INSTANCE, FBTreeNodeLabelProvider.INSTANCE);
		dialog.setInput(getTypeLibrary());
		if (dialog.open() == Window.OK && dialog.getFirstResult() instanceof final TypeNode node
				&& !node.isDirectory()) {
			selectedEntry = node.getTypeEntry();
			return true;
		}
		return false;
	}

	@Override
	protected Command createCommand(final FBNetworkElement element, final IProgressMonitor monitor)
			throws CoreException {
		return switch (element) {
		case final FB fb when fb.eContainer() instanceof final BaseFBType base && base.getInternalFbs().contains(fb) ->
			new UpdateInternalFBCommand(fb, selectedEntry);
		default -> new UpdateFBTypeCommand(element, selectedEntry);
		};
	}

	@Override
	public String getLabel() {
		return FordiacMessages.Repair_Dialog_ChangeFBType;
	}

	@Override
	public String getDescription() {
		return FordiacMessages.Repair_Dialog_ChangeFBType;
	}

	@Override
	public Image getImage() {
		return null;
	}
}
