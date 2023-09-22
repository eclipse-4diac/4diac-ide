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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.search.types.FBInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.FBTypeSearch;
import org.eclipse.fordiac.ide.model.search.types.InstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.StructDataTypeSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class RenameTypeRefactoring extends Refactoring {

	private final TypeEntry typeEntry;
	private String newName;
	private final String oldName;

	protected String getOldName() {
		return oldName;
	}

	public RenameTypeRefactoring(final TypeEntry typeEntry, final String oldName) {
		this.typeEntry = typeEntry;
		this.oldName = oldName;
	}

	public RenameTypeRefactoring() {
		this.typeEntry = null;
		this.oldName = ""; //$NON-NLS-1$
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor monitor)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor monitor)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		try {
			monitor.beginTask("Checking preconditions...", 1); //$NON-NLS-1$
			checkEditor(status, typeEntry, getOldName());

		} finally {
			monitor.done();
		}
		return status;
	}

	public static void checkEditor(final RefactoringStatus result, final TypeEntry typeEntry, final String oldName) {
		// depending if the in-place renaming is active we may not be in the display
		// thread
		Display.getDefault().syncExec(() -> {
			final IEditorPart findEditor = EditorUtils
					.findEditor((final IEditorPart editor) -> editor.getEditorInput() instanceof FileEditorInput
							&& ((FileEditorInput) editor.getEditorInput()).getFile().equals(typeEntry.getFile()));
			if (findEditor != null && findEditor.isDirty()) {
				if (shouldSaveFile(findEditor.getSite().getShell(), oldName)) {
					findEditor.doSave(new NullProgressMonitor());
				} else {
					result.addFatalError("Abort rename as editor is dirty!"); //$NON-NLS-1$
				}
			}
		});
	}

	private static boolean shouldSaveFile(final Shell shell, final String oldName) {
		final int result = MessageDialog.open(MessageDialog.QUESTION, shell, "Rename of Type with unsaved changes!", //$NON-NLS-1$
				MessageFormat.format(
						"There are unsaved changes for type \"{0}\". Do you want to save them before renaming?", //$NON-NLS-1$
						TypeEntry.getTypeNameFromFileName(oldName)),
				SWT.NONE, "Save", "Cancel"); //$NON-NLS-1$//$NON-NLS-2$
		return result == 0;
	}

	@Override
	public Change createChange(final IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		try {
			monitor.beginTask("Creating change...", 1); //$NON-NLS-1$

			final LibraryElement type = typeEntry.getType();
			if (type instanceof final StructuredType structType) {
				return createStructDataChange(structType);
			}
			if (type instanceof final FBType fbType) {
				return createFBDataChange(fbType);
			}
			return null;

		} finally {
			monitor.done();
		}
	}

	private CompositeChange createStructDataChange(final StructuredType type) {
		InstanceSearch search = StructDataTypeSearch
				.createStructMemberSearch((StructuredType) typeEntry.getTypeEditable());

		final Set<INamedElement> allFBWithStruct = InstanceSearch.performSearch(
				StructDataTypeSearch.createStructMemberSearch((StructuredType) typeEntry.getTypeEditable()),
				StructDataTypeSearch.createStructInterfaceSearch((StructuredType) typeEntry.getTypeEditable()),
				new FBInstanceSearch((DataTypeEntry) typeEntry));
		allFBWithStruct.addAll(search.searchStructuredTypes(typeEntry.getTypeLibrary()));
		final CompositeChange parentChange = new CompositeChange(
				MessageFormat.format(Messages.Refactoring_RenameFromTo, typeEntry.getTypeName(), getNewName()));

		parentChange.add(new RenameTypeChange(typeEntry, getNewName() + ".dtp")); //$NON-NLS-1$

		final CompositeChange change = new CompositeChange(Messages.Refactoring_AffectedStruct);
		final CompositeChange fbTypeChanges = new CompositeChange("Fb Types:"); //$NON-NLS-1$
		search = StructDataTypeSearch.createStructInterfaceSearch((StructuredType) typeEntry.getTypeEditable());
		final Set<INamedElement> fbTypes = search.performTypeLibBlockSearch(typeEntry.getTypeLibrary());
		fbTypes.forEach(fb -> fbTypeChanges.add(new InterfaceDataTypeChange((FBType) fb, typeEntry, oldName)));
		parentChange.add(fbTypeChanges);
		allFBWithStruct.stream().map(this::createSubChange).forEach(change::add);

		if (!allFBWithStruct.isEmpty()) {
			parentChange.add(change);
		}

		return parentChange;
	}

	private CompositeChange createFBDataChange(final FBType type) {
		final CompositeChange parentChange = new CompositeChange(
				MessageFormat.format(Messages.Refactoring_RenameFromTo, typeEntry.getTypeName(), getNewName()));
		Set<INamedElement> allFBs;
		if (type instanceof SubAppType) {
			parentChange.add(
					new RenameTypeChange(typeEntry, getNewName() + TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING_WITH_DOT));
		} else {
			parentChange
					.add(new RenameTypeChange(typeEntry, getNewName() + TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT));
		}
		final CompositeChange change = new CompositeChange(Messages.Refactoring_AffectedInstancesOfFB);
		allFBs = InstanceSearch.performSearch(FBTypeSearch.createFBTypeSearch(type));
		change.add(new UpdateInstancesChange(
				allFBs.stream().map(FBNetworkElement.class::cast).collect(Collectors.toList()), typeEntry));
		if (!allFBs.isEmpty()) {
			parentChange.add(change);
		}
		return parentChange;
	}

	private Change createSubChange(final INamedElement element) {
		if (element instanceof final StructuredType stElement) {
			return new StructuredTypeMemberChange(stElement, typeEntry, typeEntry.getTypeName(), getNewName());
		}
		if (element instanceof final FBType fbType) {
			return new InterfaceDataTypeChange(fbType, typeEntry, oldName);
		}
		if (element instanceof final FBNetworkElement elem) {
			return new UpdateInstancesChange(elem, typeEntry);
		}
		return null;
	}

	public RefactoringStatus setAndValidateTypeName(final String name) {

		final RefactoringStatus result = new RefactoringStatus();

		if (getOldName().equals(name)) {
			result.addFatalError(MessageFormat.format(" {0} is not a new Name", name)); //$NON-NLS-1$

		} else if (nameExistsInTypeLibrary(name)) {
			result.addFatalError(MessageFormat.format(Messages.RenameType_TypeExists, name));
		}

		if (result.isOK()) {
			newName = name;
		}
		return result;

	}

	protected boolean nameExistsInTypeLibrary(final String name) {
		return TypeLibraryManager.INSTANCE.getTypeLibrary(typeEntry.getFile().getProject()).find(name) != null;
	}

	@Override
	public String getName() {
		return Messages.RenameType_Name;
	}

	public TypeEntry getTypeEntry() {
		return typeEntry;
	}

	public String getNewName() {
		return newName;
	}

}