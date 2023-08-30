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
 *   Daniel Lindhuber - safe type deletion
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

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
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;

public class DeleteFBTypeParticipant extends DeleteParticipant {

	IFile file = null;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IFile targetFile) {
			this.file = targetFile;
			return true;
		}
		return false;
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
			if (!(resourceDelta.getResource() instanceof IFile)) {
				return verifyAffectedChildren(resourceDelta.getAffectedChildren());
			}
			final TypeLibrary typelib = TypeLibraryManager.INSTANCE
					.getTypeLibrary(resourceDelta.getResource().getProject());
			final TypeEntry typeEntry = typelib.getTypeEntry((IFile) resourceDelta.getResource());
			final List<String> typeNames = checkTypeContainment(typelib, typeEntry);
			if (!typeNames.isEmpty()) {
				return RefactoringStatus
						.createWarningStatus(MessageFormat.format(Messages.DeleteFBTypeParticipant_TypeInUseWarning,
								typeEntry.getTypeName(), typeNames.toString()));
			}
		}
		return new RefactoringStatus();
	}

	private static List<String> checkTypeContainment(final TypeLibrary typelib, final TypeEntry typeEntry) {
		final List<String> retVal = new ArrayList<>();
		final Stream<Entry<String, ? extends TypeEntry>> stream = Stream.concat(
				Stream.concat(typelib.getFbTypes().entrySet().stream(), typelib.getSubAppTypes().entrySet().stream()),
				typelib.getResourceTypes().entrySet().stream());

		stream.forEach(entry -> {
			final FBNetwork network = getNetwork(entry);
			if ((null != network) && (containsElementWithType(typeEntry, network))) {
				retVal.add(entry.getValue().getFullTypeName());
			}
		});

		return retVal;
	}

	private static FBNetwork getNetwork(final Entry<String, ? extends TypeEntry> entry) {
		FBNetwork network = null;
		final LibraryElement type = entry.getValue().getType();

		if (type instanceof final CompositeFBType cfbType) {
			network = cfbType.getFBNetwork();
		} else if (type instanceof final ResourceType resType) {
			network = resType.getFBNetwork();
		} else if (type instanceof final SubAppType subAppType) {
			network = subAppType.getFBNetwork();
		}
		return network;
	}

	private static boolean containsElementWithType(final TypeEntry typeEntry, final FBNetwork network) {
		for (final FBNetworkElement element : network.getNetworkElements()) {
			if (typeEntry.getFullTypeName().equals(element.getFullTypeName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		try {
			pm.beginTask("Creating change...", 1); //$NON-NLS-1$
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);

			if (typeEntry.getType() instanceof final StructuredType struct) {
				return new SafeStructDeletionChange(struct);
			}
			if (typeEntry.getType() instanceof final FBType type) {
				return new SafeFBTypeDeletionChange(type);
			}

			return null;
		} finally {
			pm.done();
		}
	}

}
