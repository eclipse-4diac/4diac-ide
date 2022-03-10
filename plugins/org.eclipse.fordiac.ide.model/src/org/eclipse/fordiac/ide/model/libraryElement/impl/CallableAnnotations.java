/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;

final class CallableAnnotations {

	private CallableAnnotations() {
	}

	static EList<INamedElement> getInputParameters(final Algorithm algorithm) {
		return ECollections.emptyEList(); // algorithms may not have parameters
	}

	static EList<INamedElement> getOutputParameters(final Algorithm algorithm) {
		return ECollections.emptyEList(); // algorithms may not have parameters
	}

	static DataType getReturnType(final Algorithm algorithm) {
		return null; // algorithms may not have a return type
	}

	static EList<INamedElement> getInputParameters(final FBType type) {
		return ECollections.unmodifiableEList(type.getInterfaceList().getInputVars());
	}

	static EList<INamedElement> getOutputParameters(final FBType type) {
		return ECollections.unmodifiableEList(type.getInterfaceList().getOutputVars());
	}

	static DataType getReturnType(final FBType type) {
		return null; // FB types may not have a return type
	}

	static EList<INamedElement> getInputParameters(final Event event) {
		if (event.eContainer() instanceof InterfaceList) {
			return ECollections.unmodifiableEList(((InterfaceList) event.eContainer()).getInputVars());
		}
		return ECollections.emptyEList();
	}

	static EList<INamedElement> getOutputParameters(final Event event) {
		if (event.eContainer() instanceof InterfaceList) {
			return ECollections.unmodifiableEList(((InterfaceList) event.eContainer()).getOutputVars());
		}
		return ECollections.emptyEList();
	}

	static DataType getReturnType(final Event event) {
		return null; // events may not have a return type
	}
}
