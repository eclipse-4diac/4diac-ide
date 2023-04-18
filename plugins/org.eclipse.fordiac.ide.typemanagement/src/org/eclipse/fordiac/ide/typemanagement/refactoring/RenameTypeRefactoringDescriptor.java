package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RenameTypeRefactoringDescriptor extends RefactoringDescriptor {

	public static final String REFACTORING_ID = "org.eclipse.fordiac.ide.typemanagement.refactoring.renametype";

	private final Map fArguments;

	public RenameTypeRefactoringDescriptor(final String project, final String description, final String comment, final Map arguments) {
		super(REFACTORING_ID, project, description, comment, RefactoringDescriptor.STRUCTURAL_CHANGE | RefactoringDescriptor.MULTI_CHANGE);
		fArguments= arguments;
	}

	@Override
	public Refactoring createRefactoring(final RefactoringStatus status) throws CoreException {
		final RenameTypeRefactoring refactoring= new RenameTypeRefactoring();

		return refactoring;
	}

	public Map getArguments() {
		return fArguments;
	}
}