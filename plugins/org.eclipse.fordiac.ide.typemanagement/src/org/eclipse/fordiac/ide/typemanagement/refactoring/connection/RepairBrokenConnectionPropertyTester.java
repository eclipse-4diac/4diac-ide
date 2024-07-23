package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;

public class RepairBrokenConnectionPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		if (receiver instanceof final IStructuredSelection sel && sel.size() == 1
				&& sel.getFirstElement() instanceof final EditPart part
				&& part.getModel() instanceof ErrorMarkerInterface) {
			return true;
		}
		return false;
	}

}
