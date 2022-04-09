/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;

public class DeleteFBTypeParticipant extends DeleteParticipant {

	@Override
	protected boolean initialize(final Object element) {
		return (element instanceof IFile);
	}

	@Override
	public String getName() {
		return Messages.DeleteFBTypeParticipant_Name;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		final ResourceChangeChecker resChecker = context.getChecker(ResourceChangeChecker.class);
		final IResourceChangeDescriptionFactory deltaFactory = resChecker.getDeltaFactory();
		final IResourceDelta[] affectedChildren = deltaFactory.getDelta().getAffectedChildren();

		return verifyAffectedChildren(affectedChildren);
	}

	private static RefactoringStatus verifyAffectedChildren(final IResourceDelta[] affectedChildren) {
		for (final IResourceDelta resourceDelta : affectedChildren) {
			if (resourceDelta.getResource() instanceof IFile) {
				final Palette palette = TypeLibrary.getTypeLibrary(resourceDelta.getResource().getProject())
						.getBlockTypeLib();

				final String typeNameToDelete = TypeEntry.getTypeNameFromFile((IFile) resourceDelta.getResource());
				final List<String> typeNames = checkTypeContainment(palette, typeNameToDelete);

				if (!typeNames.isEmpty()) {
					return RefactoringStatus.createWarningStatus(MessageFormat.format(
							Messages.DeleteFBTypeParticipant_TypeInUseWarning, typeNameToDelete, typeNames.toString()));
				}
			} else {
				return verifyAffectedChildren(resourceDelta.getAffectedChildren());
			}
		}
		return new RefactoringStatus();
	}

	private static List<String> checkTypeContainment(final Palette palette, final String searchTypeName) {
		final List<String> retVal = new ArrayList<>();
		final Stream<Entry<String, ? extends PaletteEntry>> stream = Stream.concat(
				Stream.concat(palette.getFbTypes().entrySet().stream(), palette.getSubAppTypes().entrySet().stream()),
				palette.getResourceTypes().entrySet().stream());

		stream.forEach(entry -> {
			final FBNetwork network = getNetwork(entry);
			if ((null != network) && (containsElementWithType(searchTypeName, network))) {
				retVal.add(entry.getValue().getLabel());
			}
		});

		return retVal;
	}

	private static FBNetwork getNetwork(final Entry<String, ? extends PaletteEntry> entry) {
		FBNetwork network = null;
		final LibraryElement type = entry.getValue().getType();

		if (type instanceof CompositeFBType) {
			network = ((CompositeFBType) type).getFBNetwork();
		} else if (type instanceof ResourceType) {
			network = ((ResourceType) type).getFBNetwork();
		} else if (type instanceof SubAppType) {
			network = ((SubAppType) type).getFBNetwork();
		}
		return network;
	}

	private static boolean containsElementWithType(final String searchTypeName, final FBNetwork network) {
		for (final FBNetworkElement element : network.getNetworkElements()) {
			if (searchTypeName.equals(element.getTypeName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return null;
	}

}
