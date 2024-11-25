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
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

final class CallableAnnotations {

	private CallableAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused")
	static EList<INamedElement> getInputParameters(final Algorithm algorithm) {
		return ECollections.emptyEList(); // algorithms may not have parameters
	}

	@SuppressWarnings("unused")
	static EList<INamedElement> getOutputParameters(final Algorithm algorithm) {
		return ECollections.emptyEList(); // algorithms may not have parameters
	}

	@SuppressWarnings("unused")
	static EList<INamedElement> getInOutParameters(final Algorithm algorithm) {
		return ECollections.emptyEList(); // algorithms may not have parameters
	}

	@SuppressWarnings("unused")
	static DataType getReturnType(final Algorithm algorithm) {
		return null; // algorithms may not have a return type
	}

	static EList<INamedElement> getInputParameters(final FBType type) {
		return ECollections.unmodifiableEList(type.getInterfaceList().getInputVars());
	}

	static EList<INamedElement> getOutputParameters(final FBType type) {
		return ECollections.unmodifiableEList(
				type.getInterfaceList().getOutputVars().stream().filter(v -> !v.getName().isEmpty()).toList());
	}

	static EList<INamedElement> getInOutParameters(final FBType type) {
		return ECollections.unmodifiableEList(type.getInterfaceList().getInOutVars());
	}

	static DataType getReturnType(final FBType type) {
		return type.getInterfaceList().getOutputVars().stream().filter(v -> v.getName().isEmpty()).findAny()
				.map(VarDeclaration::getType).orElse(null);
	}

	static EList<INamedElement> getInputParameters(final Event event) {
		if (event.eContainer() instanceof final InterfaceList interfaceList) {
			return ECollections.unmodifiableEList(interfaceList.getInputVars());
		}
		return ECollections.emptyEList();
	}

	static EList<INamedElement> getOutputParameters(final Event event) {
		if (event.eContainer() instanceof final InterfaceList interfaceList) {
			return ECollections.unmodifiableEList(
					interfaceList.getOutputVars().stream().filter(v -> !v.getName().isEmpty()).toList());
		}
		return ECollections.emptyEList();
	}

	static EList<INamedElement> getInOutParameters(final Event event) {
		if (event.eContainer() instanceof final InterfaceList interfaceList) {
			return ECollections.unmodifiableEList(interfaceList.getInOutVars());
		}
		return ECollections.emptyEList();
	}

	static DataType getReturnType(final Event event) {
		if (event.eContainer() instanceof final InterfaceList interfaceList) {
			return interfaceList.getOutputVars().stream().filter(v -> v.getName().isEmpty()).findAny()
					.map(VarDeclaration::getType).orElse(null);
		}
		return null;
	}

	static EList<INamedElement> getInputParameters(final FB fb) {
		return ECollections.unmodifiableEList(fb.getInterface().getInputVars());
	}

	static EList<INamedElement> getOutputParameters(final FB fb) {
		return ECollections.unmodifiableEList(
				fb.getInterface().getOutputVars().stream().filter(v -> !v.getName().isEmpty()).toList());
	}

	static EList<INamedElement> getInOutParameters(final FB fb) {
		return ECollections.unmodifiableEList(fb.getInterface().getInOutVars());
	}

	static DataType getReturnType(final FB fb) {
		return fb.getInterface().getOutputVars().stream().filter(v -> v.getName().isEmpty()).findAny()
				.map(VarDeclaration::getType).orElse(null);
	}
}
