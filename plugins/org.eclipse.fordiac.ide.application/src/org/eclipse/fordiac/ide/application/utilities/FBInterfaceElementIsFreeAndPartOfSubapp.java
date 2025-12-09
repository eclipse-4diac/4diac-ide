/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class FBInterfaceElementIsFreeAndPartOfSubapp extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		return receiver instanceof final List<?> receiverList && checkList(receiverList)
				&& receiverList.get(0) instanceof final InterfaceEditPartForFBNetwork ieEditPart
				&& ieEditPart.getModel() instanceof final IInterfaceElement ie && isFromFBOrCFB(ie)
				&& hasNoConnections(ie) && isNestedInSubApp(ie);
	}

	private static boolean checkList(final List<?> reciverList) {
		return reciverList.size() == 1;
	}

	private static boolean isNestedInSubApp(final IInterfaceElement ie) {
		if (ie.eContainer() != null && ie.eContainer().eContainer() instanceof final FBNetworkElement fbne) {
			return fbne.isNestedInSubApp();
		}
		return false;
	}

	private static boolean isFromFBOrCFB(final IInterfaceElement ie) {
		if (ie.eContainer() != null) {
			if (ie.eContainer().eContainer() instanceof FB) {
				return true;
			}
			if (ie.eContainer().eContainer() instanceof final SubApp subapp) {
				return subapp.isTyped();
			}
		}
		return false;
	}

	private static boolean hasNoConnections(final IInterfaceElement ie) {
		return ((ie.isIsInput() && ie.getInputConnections().isEmpty())
				|| (!ie.isIsInput() && ie.getOutputConnections().isEmpty()) && isNestedInSubApp(ie));
	}

}
