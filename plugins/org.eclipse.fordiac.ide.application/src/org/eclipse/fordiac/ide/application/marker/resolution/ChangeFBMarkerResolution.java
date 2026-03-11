/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH,
 *                          Martin Erich Jobst
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.FBSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.FBTreeNodeLabelProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class ChangeFBMarkerResolution extends AbstractCommandMarkerResolution<BlockFBNetworkElement> {

	private TypeEntry selectedEntry;
	private List<EClass> rootTypes;

	public ChangeFBMarkerResolution(final IMarker marker) {
		super(marker, BlockFBNetworkElement.class);
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		rootTypes = Arrays.stream(markers).map(IMarker::getResource).filter(IFile.class::isInstance)
				.map(IFile.class::cast).map(file -> getTypeLibrary().getTypeEntry(file)).filter(Objects::nonNull)
				.map(TypeEntry::getTypeEClass).toList();

		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				FBSelectionTreeContentProvider.INSTANCE, FBTreeNodeLabelProvider.INSTANCE, this::isCompatible);
		dialog.setInput(getTypeLibrary());
		if (dialog.open() == Window.OK && dialog.getFirstResult() instanceof final TypeNode node
				&& !node.isDirectory()) {
			selectedEntry = node.getTypeEntry();
			return true;
		}
		return false;
	}

	@Override
	protected Command createCommand(final BlockFBNetworkElement element, final IProgressMonitor monitor)
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

	private boolean isCompatible(final LibraryElement newElement) {
		return rootTypes.stream().noneMatch(eClass -> {
			if (LibraryElementPackage.Literals.BASE_FB_TYPE.isSuperTypeOf(eClass)) {
				return newElement instanceof SubAppType || newElement instanceof FunctionFBType;
			}

			if (LibraryElementPackage.Literals.COMPOSITE_FB_TYPE.isSuperTypeOf(eClass)
					&& !LibraryElementPackage.Literals.SUB_APP_TYPE.isSuperTypeOf(eClass)) {
				return newElement instanceof SubAppType;
			}

			return false;
		});
	}
}
