package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.types.InstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.StructDataTypeSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
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
	public RefactoringStatus checkFinalConditions(final IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		final RefactoringStatus status= new RefactoringStatus();

		return status;
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		final RefactoringStatus status= new RefactoringStatus();
		try {
			monitor.beginTask("Checking preconditions...", 1); //$NON-NLS-1$
			checkEditor(status, typeEntry, getOldName());


		} finally {
			monitor.done();
		}
		return status;
	}

	public static void checkEditor(final RefactoringStatus result, final TypeEntry typeEntry, final String oldName) {
		// depending if the in-place renaming is active we may not be in the display thread
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
				SWT.NONE, "Save", "Cancel");  //$NON-NLS-1$//$NON-NLS-2$
		return result == 0;
	}

	@Override
	public Change createChange(final IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		try {
			monitor.beginTask("Creating change...", 1); //$NON-NLS-1$

			final LibraryElement type = typeEntry.getType();
			if (!(type instanceof StructuredType)) {
				return null;
			}

			final InstanceSearch search = StructDataTypeSearch.createStructMemberSearch((StructuredType) typeEntry.getTypeEditable());

			final List<INamedElement> allTypesWithStruct = search
					.searchStructuredTypes(typeEntry.getTypeLibrary());


			final CompositeChange parentChange = new CompositeChange(
					"Rename Type from " + typeEntry.getTypeName() + " to : " + getNewName()); //$NON-NLS-1$ //$NON-NLS-2$

			parentChange.add(new RenameTypeChange(typeEntry, getNewName() + ".dtp")); //$NON-NLS-1$

			final CompositeChange change = new CompositeChange("Affected Struct that contain struct as member"); //$NON-NLS-1$


			for (final INamedElement element : allTypesWithStruct) {
				final StructuredTypeMemberChange structChange = new StructuredTypeMemberChange((StructuredType) element,
						typeEntry, typeEntry.getTypeName(), getNewName());
				change.add(structChange);
			}
			if (!allTypesWithStruct.isEmpty()) {
				parentChange.add(change);
			}

			return parentChange;

		} finally {
			monitor.done();
		}
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