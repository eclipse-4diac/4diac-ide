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
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.impl.ErrorFBTypeEntryImpl;
import org.eclipse.fordiac.ide.typemanagement.wizards.RestrictedNewTypeWizard;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.ui.PlatformUI;

public class CreateMissingFBMarkerResolution extends AbstractErrorMarkerResolution {

	private TypeEntry newEntry;
	private boolean canceled = false;

	public CreateMissingFBMarkerResolution(final IMarker marker) {
		super(FordiacMessages.Repair_Dialog_New_FB, marker);
	}

	@Override
	public void run(final IMarker marker) {
		if (canceled) {
			return;
		}

		if (newEntry == null) {
			// TODO fix, if same name as old -> markers get invalid
			createNewEntry(FordiacErrorMarker.getData(this.marker)[0]);
		}

		if (newEntry != null) {
			final EObject target = FordiacErrorMarker.getTarget(marker);
			if (target instanceof final ErrorMarkerFBNElement err) {
				AbstractLiveSearchContext.executeAndSave(new UpdateFBTypeCommand(err, newEntry), err,
						new NullProgressMonitor());
			}

			if (target instanceof final FB fb && fb.getTypeEntry() instanceof ErrorFBTypeEntryImpl
					&& fb.eContainer() instanceof final BaseFBType base && base.getInternalFbs().contains(fb)) {
				AbstractLiveSearchContext.executeAndSave(new UpdateInternalFBCommand(fb, newEntry), fb,
						new NullProgressMonitor());
			}
		}
	}

	private void createNewEntry(final String defaultName) {
		newEntry = RestrictedNewTypeWizard.showRestrictedNewTypeWizard(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), defaultName, ".fbt", //$NON-NLS-1$
				marker.getResource().getProject().getName());
		if (newEntry == null) {
			canceled = true;
		}
	}
}
