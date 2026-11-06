/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Dimitrios Kalligaridis - fix struct reference repointing on package folder rename
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.edit.DataTypeEditBuilder;
import org.eclipse.fordiac.ide.typemanagement.refactoring.move.MoveTypeModelEdit;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ltk.ui.refactoring.resource.RenameResourceWizard;
import org.eclipse.swt.widgets.Shell;

public final class TypeRefactoringHelper {

	public static void addPackageNameModelEdit(final List<ModelEdit<?>> modelEdits, final TypeEntry typeEntry,
			final String newPackageName) {
		modelEdits.add(new MoveTypeModelEdit(newPackageName,
				MessageFormat.format(Messages.MoveTypeToPackage_RenamePackageTo, newPackageName), typeEntry.getURI()));
	}

	public static void openRenameResourceWizard(final TypeEntry typeEntry, final Shell shell) {
		if (typeEntry == null || typeEntry.getFile() == null) {
			return;
		}

		try {
			RefactoringUtil.saveAllAndBuild();
			final RenameResourceWizard wizard = new RenameResourceWizard(typeEntry.getFile());
			final RefactoringWizardOpenOperation openOperation = new RefactoringWizardOpenOperation(wizard);
			openOperation.run(shell, Messages.RenameType_Name);
		} catch (final OperationCanceledException e) {
			// ignore
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (final Exception e) {
			FordiacLogHelper.logError("Error during type rename refactoring", e); //$NON-NLS-1$
		}
	}

	public static void addModelEditsForMovedType(final List<ModelEdit<?>> modelEdits, final TypeEntry typeEntry,
			final IPath newPath) {
		if (typeEntry instanceof final DataTypeEntry dtEntry) {
			DataTypeEditBuilder.createStructuredDataTypeChanges(dtEntry, modelEdits,
					DataTypeEditBuilder.getFullTypeName(newPath));
		} else {
			addInstanceChanges(modelEdits, typeEntry);
		}
	}

	public static void addModelEditsForRenamedType(final List<ModelEdit<?>> modelEdits, final TypeEntry typeEntry,
			final IPath newPath) {
		if (typeEntry instanceof final DataTypeEntry dtEntry) {
			// Derive the target name from the new path, so a package folder rename repoints struct
			// references to the new package instead of the type entry's old package name.
			DataTypeEditBuilder.createStructuredDataTypeChanges(dtEntry, modelEdits,
					DataTypeEditBuilder.getFullTypeName(newPath));
		} else {
			addInstanceChanges(modelEdits, typeEntry);
		}
	}

	public static void addModelEditsForPackageChangedType(final List<ModelEdit<?>> modelEdits, final TypeEntry typeEntry,
			final String newPackageName) {
		if (typeEntry instanceof final DataTypeEntry dtEntry) {
			final String targetTypeName = DataTypeEditBuilder.getFullTypeName(newPackageName, typeEntry.getTypeName());
			DataTypeEditBuilder.createStructuredDataTypeChanges(dtEntry, modelEdits, targetTypeName);
		} else {
			addInstanceChanges(modelEdits, typeEntry);
		}
	}

	private static void addInstanceChanges(final List<ModelEdit<?>> modelEdits, final TypeEntry typeEntry) {
		final List<? extends EObject> result = new BlockTypeInstanceSearch(typeEntry).performSearch();

		for (final EObject eObject : result) {
			if (eObject instanceof final BlockFBNetworkElement elem) {
				modelEdits.add(new UpdateFBTypeModelEdit(elem, typeEntry));
			}
		}
	}

	private TypeRefactoringHelper() {
		throw new UnsupportedOperationException();
	}
}
