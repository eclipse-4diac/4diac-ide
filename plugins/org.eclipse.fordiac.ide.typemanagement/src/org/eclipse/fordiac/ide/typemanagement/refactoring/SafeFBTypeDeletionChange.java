/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.swt.widgets.Display;

public class SafeFBTypeDeletionChange extends CompositeChange {

	public SafeFBTypeDeletionChange(final FBType type) {
		super("Delete FB type"); //$NON-NLS-1$
		createUpdateChanges(this, type);
	}

	private static void createUpdateChanges(final CompositeChange change, final FBType type) {
		// @formatter:off
		final InstanceSearch search = new InstanceSearch((final INamedElement searchCandiate) ->
			searchCandiate instanceof final FBNetworkElement fb
					&& fb.getTypeName() != null
					&& fb.getTypeName().equalsIgnoreCase(type.getName()));

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

		// internal fbs
		search.performInternalFBSearch(type.getTypeLibrary()).stream()
			.filter(FB.class::isInstance) // TODO does this need to be filtered?
			.map(FB.class::cast)
			.map(fb -> new UpdateInternalFBChange((BaseFBType) fb.eContainer(), fb))
			.forEach(change::add);
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
				SafeFBTypeDeletionChange.createUpdateChanges(this, rootType);
			}
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			final UpdateFBTypeCommand cmd = new UpdateFBTypeCommand(fb, fb.getTypeEntry());
			Display.getDefault().execute(cmd::execute);
			return super.perform(pm);
		}

	}

	private static class UpdateInternalFBChange extends CompositeChange {

		final BaseFBType baseFb;
		final FB internalFb;

		public UpdateInternalFBChange(final BaseFBType baseFb, final FB internalFb) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateInternalFB,
					internalFb.getQualifiedName()));
			this.baseFb = baseFb;
			this.internalFb = internalFb;
			SafeFBTypeDeletionChange.createUpdateChanges(this, baseFb);
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			new DeleteInternalFBCommand((BaseFBType) baseFb.getTypeEntry().getTypeEditable(), internalFb).execute();
			return super.perform(pm);
		}

	}

}
