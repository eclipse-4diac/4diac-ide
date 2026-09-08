/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class DeleteInterfaceMarkerResolution extends AbstractCommandMarkerResolution<IInterfaceElement> {

	protected DeleteInterfaceMarkerResolution(final IMarker marker) {
		super(marker, IInterfaceElement.class);
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		return true;
	}

	@Override
	protected Command createCommand(final IInterfaceElement element, final IProgressMonitor monitor)
			throws CoreException {
		if (element.getBlockFBNetworkElement() instanceof UntypedSubApp) {
			return new DeleteSubAppInterfaceElementCommand(element);
		}
		return new DeleteInterfaceCommand(element);
	}

	@Override
	public String getLabel() {
		return "Delete";
	}

	@Override
	public String getDescription() {
		return "Delete pin";
	}

	@Override
	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE);
	}
}
