package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Objects;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ReplaceVarsWithStructChange extends AbstractCommandChange<FBType> {
	private final Set<String> vars;
	private final DataType struct;
	private final String name;
	private final InterfaceList interfacelist;
	private final boolean isInput;
	private final int position;

	protected ReplaceVarsWithStructChange(final URI elementURI, final Class<FBType> elementClass,
			final Set<String> vars, final DataType struct, final String name, final InterfaceList interfacelist,
			final boolean isInput, final int position) {
		super(elementURI, elementClass);
		this.vars = Objects.requireNonNull(vars);
		this.struct = Objects.requireNonNull(struct);
		this.name = Objects.requireNonNull(name);
		this.interfacelist = Objects.requireNonNull(interfacelist);
		this.isInput = isInput;
		this.position = position;
	}

	@Override
	public void initializeValidationData(final FBType element, final IProgressMonitor pm) {
		// TODO Auto-generated method stub

	}

	@Override
	public RefactoringStatus isValid(final FBType element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createCommand(final FBType element) {
		return new ReplaceVarsWithStructCommand(vars, struct, name, interfacelist, isInput, position);
	}

}
