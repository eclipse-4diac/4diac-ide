package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class RenameTypeAction implements IWorkbenchWindowActionDelegate {



	private TypeEntry typeEntry;
	private IWorkbenchWindow fWindow;

	@Override
	public void dispose() {
		// Do nothing
	}

	@Override
	public void init(final IWorkbenchWindow window) {
		fWindow = window;
	}

	@Override
	public void run(final IAction action) {
		final RenameTypeRefactoring refactoring = new RenameTypeRefactoring(typeEntry, typeEntry.getTypeName());
		run(new RenameTypeRefactoringWizard(refactoring, 0), fWindow.getShell(),
				"Rename Type"); //$NON-NLS-1$
	}


	public void run(final RefactoringWizard wizard, final Shell parent, final String dialogTitle) {
		try {
			final RefactoringWizardOpenOperation operation= new RefactoringWizardOpenOperation(wizard);
			operation.run(parent, dialogTitle);
		} catch (final InterruptedException exception) {

		}
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		typeEntry = null;
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection extended = (IStructuredSelection) selection;
			final Object[] elements = extended.toArray();
			if (elements.length == 1 && elements[0] instanceof IFile) {
				typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) elements[0]);
			}
		}

	}
}