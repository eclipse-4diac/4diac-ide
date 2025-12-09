/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeDeclarationCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.AttributeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class ChangeAttributeMarkerResolution extends AbstractCommandMarkerResolution<Attribute> {

	private AttributeTypeEntry attribute;

	protected ChangeAttributeMarkerResolution(final IMarker marker) {
		super(marker, Attribute.class);
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				AttributeSelectionTreeContentProvider.INSTANCE);
		dialog.setInput(getTypeLibrary());
		if ((dialog.open() == Window.OK && dialog.getFirstResult() instanceof final TypeNode node
				&& !node.isDirectory())) {
			attribute = (AttributeTypeEntry) node.getTypeEntry();
			return true;
		}
		return false;
	}

	@Override
	protected Command createCommand(final Attribute element, final IProgressMonitor monitor) throws CoreException {
		return ChangeAttributeDeclarationCommand.forName(element, attribute.getFullTypeName());
	}

	@Override
	public String getDescription() {
		return FordiacMessages.Repair_Dialog_ChangeAttribute;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getLabel() {
		return FordiacMessages.Repair_Dialog_ChangeAttribute;
	}

}
