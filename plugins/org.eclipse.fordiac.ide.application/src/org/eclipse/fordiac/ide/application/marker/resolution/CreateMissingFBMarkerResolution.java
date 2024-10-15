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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.wizards.RestrictedNewTypeWizard;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class CreateMissingFBMarkerResolution extends AbstractCommandMarkerResolution<FBNetworkElement> {

	private TypeEntry newEntry;
	List<EObject> targets = new ArrayList<>();

	public CreateMissingFBMarkerResolution(final IMarker marker) {
		super(marker, FBNetworkElement.class);
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		final String defaultName = FordiacErrorMarker.getData(markers[0])[0];
		newEntry = RestrictedNewTypeWizard.showRestrictedNewTypeWizard(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), defaultName, ".fbt", //$NON-NLS-1$
				markers[0].getResource().getProject().getName());
		return newEntry != null;
	}

	@Override
	protected Command createCommand(final FBNetworkElement element, final IProgressMonitor monitor)
			throws CoreException {
		return switch (element) {
		case final FB fb when fb.eContainmentFeature() == LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS ->
			new UpdateInternalFBCommand(fb, newEntry);
		default -> new UpdateFBTypeCommand(element, newEntry);
		};
	}

	@Override
	public String getLabel() {
		return FordiacMessages.Repair_Dialog_New_FB;
	}

	@Override
	public String getDescription() {
		return FordiacMessages.Repair_Dialog_New_FB;
	}

	@Override
	public Image getImage() {
		return null;
	}
}
