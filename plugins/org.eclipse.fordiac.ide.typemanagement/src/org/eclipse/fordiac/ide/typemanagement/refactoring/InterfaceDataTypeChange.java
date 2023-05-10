package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.widgets.Display;

public class InterfaceDataTypeChange extends Change {

	private final FBType fbType;
	private final TypeEntry typeEntry;
	private final String oldName;
	private List<String> pinNames;

	private CompoundCommand cmd = new CompoundCommand();

	public InterfaceDataTypeChange(final FBType fbType, final TypeEntry oldTypeEntry, final String oldName,
			final String newName) {
		this.fbType = fbType;
		this.typeEntry = oldTypeEntry;
		this.oldName = oldName;
		this.pinNames = new ArrayList<>();
	}

	@Override
	public String getName() {
		return "Update InterfacePins: - " + fbType.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
				+ fbType.getTypeEntry().getFile().getFileExtension() + " - " //$NON-NLS-1$
				+ fbType.getTypeEntry().getFile().getProject().getName() + ": " + pinNames.toString();
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {

		for (final IInterfaceElement varDeclaration : fbType.getInterfaceList().getAllInterfaceElements()) {
			final String typeName = varDeclaration.getTypeName();
			if (typeName.equals(oldName)) {
				pinNames.add(varDeclaration.getName() + ":" + typeName);
				cmd.add(new ChangeDataTypeCommand(varDeclaration, (DataType) typeEntry.getTypeEditable()));
			}
		}
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	/** TODO here we need to return the Undo change */
	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		//we have to execute async, otherwise SWT crashes if the editor of the type is opened
		Display.getDefault().asyncExec(()->{
			final var typeEditable = fbType.getTypeEntry().getTypeEditable();
			cmd.execute();
			typeEditable.getTypeEntry().save();
		});
		return null;
	}

	@Override
	public Object getModifiedElement() {
		return fbType;
	}

}
