/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class InterfaceHelper {
	public static boolean canHidePin(final IInterfaceElement element) {
		final boolean isInput = element.isIsInput();
		final boolean hasInput = !element.getInputConnections().isEmpty();
		final boolean hasOutput = !element.getOutputConnections().isEmpty();

		if (isInput ? hasInput : hasOutput) {
			return false;
		}

		if (element.getBlockFBNetworkElement() instanceof final UntypedSubApp subApp && subApp.isUnfolded()) {
			return !hasInput && !hasOutput;
		}

		if (element instanceof final VarDeclaration varDecl && varDecl.isInOutVar()) {
			return canHideVarInOut(varDecl);
		}

		return true;
	}

	private static boolean canHideVarInOut(final VarDeclaration varInOut) {
		if (varInOut.getFBType() instanceof SubAppType) {
			return varInOut.isIsInput() ? varInOut.getOutputConnections().isEmpty()
					: varInOut.getInputConnections().isEmpty();
		}
		if (varInOut.isIsInput() && varInOut.getBlockFBNetworkElement() instanceof UntypedSubApp) {
			return varInOut.getOutputConnections().isEmpty();
		}
		if (varInOut.isIsInput() && varInOut.getBlockFBNetworkElement() instanceof final TypedSubApp tsa) {
			// we need the typePin to have all the connections from inside the Type
			final var typePin = tsa.getType().getInterfaceList().getInterfaceElement(varInOut);
			return typePin.getOutputConnections().isEmpty();
		}
		return true;
	}

	private InterfaceHelper() {
		throw new UnsupportedOperationException();
	}
}
