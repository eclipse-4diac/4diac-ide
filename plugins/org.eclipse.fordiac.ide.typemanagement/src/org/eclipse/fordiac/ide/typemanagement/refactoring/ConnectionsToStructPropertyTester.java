package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;

public class ConnectionsToStructPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		Object dest = null;
		Object src = null;
		if (!(receiver instanceof final IStructuredSelection selection)) {
			return false;
		}
		for (final Object sel : selection) {
			if (!(sel instanceof final EditPart part)) {
				return false;
			}
			if (part.getModel() instanceof EventConnection) {
				return false;
			}
			if (!(part.getModel() instanceof final Connection con)) {
				return false;
			}
			if (con.getSource() instanceof final VarDeclaration vars && vars.isInOutVar()
					|| con.getDestination() instanceof final VarDeclaration vard && vard.isInOutVar()) {
				return false;
			}
			if (src != null) {
				if (!con.getSourceElement().equals(src)) {
					return false;
				}
			} else {
				src = con.getSourceElement();
			}

			if (dest != null) {
				if (!con.getDestinationElement().equals(dest)) {
					return false;
				}
			} else {
				dest = con.getDestinationElement();
			}

			// TODO: check if inoutvar
		}
		return true;
	}

}
