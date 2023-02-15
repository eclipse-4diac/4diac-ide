package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Map;

import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;

public class RenameTypeRefactoringContribution extends RefactoringContribution {

	@Override
	public RefactoringDescriptor createDescriptor(final String id, final String project, final String description, final String comment, final Map arguments, final int flags) {
		return null;
	}

	@Override
	public Map retrieveArgumentMap(final RefactoringDescriptor descriptor) {
		return super.retrieveArgumentMap(descriptor);
	}
}