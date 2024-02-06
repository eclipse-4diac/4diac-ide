/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalFBCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.search.types.InstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class SafeFBTypeDeletionChange extends CompositeChange {

	public SafeFBTypeDeletionChange(final FBType type) {
		super(Messages.DeleteFBTypeParticipant_Change_SafeDeletionChangeTitle);
		addUpdateChanges(this, type, true);
	}

	public static void addUpdateChanges(final CompositeChange change, final FBType type,
			final boolean deleteInternalFBs) {
		// @formatter:off
		final InstanceSearch search = new InstanceSearch((final INamedElement searchCandiate) ->
			searchCandiate instanceof final FBNetworkElement fb
					&& fb.getFullTypeName() != null
					&& fb.getFullTypeName().equalsIgnoreCase(type.getTypeEntry().getFullTypeName()));

		// application instances
		SystemManager.INSTANCE.getProjectSystems(type.getTypeLibrary().getProject()).stream()
			.map(search::performApplicationSearch)
			.flatMap(Collection::stream)
			.map(FBNetworkElement.class::cast)
			.map(UpdateFBTypeInstanceChange::new)
			.forEach(change::add);

		// type lib network instances
		search.performTypeLibraryNetworkSearch(type.getTypeLibrary()).stream()
			.map(FBNetworkElement.class::cast)
			.map(UpdateFBTypeInstanceChange::new)
			.forEach(change::add);

		if (deleteInternalFBs) {
			search.performInternalFBSearch(type.getTypeLibrary()).stream()
				.map(FB.class::cast)
				.map(fb -> new DeleteInternalFBChange((BaseFBType) fb.eContainer(), fb))
				.forEach(change::add);
		}
		// @formatter:on
	}

	private static class UpdateFBTypeInstanceChange extends CompositeChange {

		final FBNetworkElement fb;

		public UpdateFBTypeInstanceChange(final FBNetworkElement fb) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateInstance, fb.getQualifiedName()));
			this.fb = fb;

			// if contained inside a fbtype's network, update the containing type
			final FBType rootType = FBNetworkHelper.getRootType(fb);
			if (rootType != null) {
				SafeFBTypeDeletionChange.addUpdateChanges(this, rootType, false);
			}
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			final Command cmd = new UpdateFBTypeCommand(fb, getErrorMarkerEntry());
			ChangeExecutionHelper.executeChange(cmd, fb, pm);
			return super.perform(pm);
		}

		private TypeEntry getErrorMarkerEntry() {
			final TypeLibrary typeLibrary = fb.getTypeEntry().getTypeLibrary();
			return typeLibrary.createErrorTypeEntry(fb.getTypeName(), fb.getType().eClass());
		}

	}

	private static class DeleteInternalFBChange extends CompositeChange {

		final BaseFBType baseFb;
		final FB internalFb;

		public DeleteInternalFBChange(final BaseFBType baseFb, final FB internalFb) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateInternalFB,
					internalFb.getQualifiedName()));
			this.baseFb = baseFb;
			this.internalFb = internalFb;
			SafeFBTypeDeletionChange.addUpdateChanges(this, baseFb, false);
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			final TypeEntry typeEntry = baseFb.getTypeEntry();
			final Command cmd = new DeleteInternalFBCommand((BaseFBType) typeEntry.getTypeEditable(), internalFb);
			ChangeExecutionHelper.executeChange(cmd, baseFb, pm);
			return super.perform(pm);
		}

	}

}
