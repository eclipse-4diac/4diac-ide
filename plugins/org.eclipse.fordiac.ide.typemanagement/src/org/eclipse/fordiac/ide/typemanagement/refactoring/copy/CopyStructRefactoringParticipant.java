package org.eclipse.fordiac.ide.typemanagement.refactoring.copy;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.CopyParticipant;

public class CopyStructRefactoringParticipant extends CopyParticipant {

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IFolder folder && ((IFolder) element).getName().endsWith(".dtp")) { //$NON-NLS-1$
			System.out.println("True!");
			return true;
		}
		System.out.println("False!");
		return false;
	}

	@Override
	public String getName() {
		System.out.println("getName");
		return Messages.CopyStruct_Change_Name;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		System.out.println("checkConditions");
		return null;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		System.out.println("createChange");
		return null;
	}

}
