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

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class CallableAnnotations {

	private CallableAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused")
	static EList<ITypedElement> getInputParameters(final Algorithm algorithm) {
		return ECollections.emptyEList(); // algorithms may not have parameters
	}

	@SuppressWarnings("unused")
	static EList<ITypedElement> getOutputParameters(final Algorithm algorithm) {
		return ECollections.emptyEList(); // algorithms may not have parameters
	}

	@SuppressWarnings("unused")
	static EList<ITypedElement> getInOutParameters(final Algorithm algorithm) {
		return ECollections.emptyEList(); // algorithms may not have parameters
	}

	@SuppressWarnings("unused")
	static DataType getReturnType(final Algorithm algorithm) {
		return null; // algorithms may not have a return type
	}

	static EList<ITypedElement> getInputParameters(final FBType type) {
		return ECollections.unmodifiableEList(type.getInterfaceList().getInputVars());
	}

	static EList<ITypedElement> getOutputParameters(final FBType type) {
		return ECollections.unmodifiableEList(
				type.getInterfaceList().getOutputVars().stream().filter(v -> !v.getName().isEmpty()).toList());
	}

	static EList<ITypedElement> getInOutParameters(final FBType type) {
		return ECollections.unmodifiableEList(type.getInterfaceList().getInOutVars());
	}

	static DataType getReturnType(final FBType type) {
		return type.getInterfaceList().getOutputVars().stream().filter(v -> v.getName().isEmpty()).findAny()
				.map(VarDeclaration::getType).orElse(null);
	}

	static EList<ITypedElement> getInputParameters(final Event event) {
		if (event.eContainer() instanceof final InterfaceList interfaceList) {
			return ECollections.unmodifiableEList(interfaceList.getInputVars());
		}
		return ECollections.emptyEList();
	}

	static EList<ITypedElement> getOutputParameters(final Event event) {
		if (event.eContainer() instanceof final InterfaceList interfaceList) {
			return ECollections.unmodifiableEList(
					interfaceList.getOutputVars().stream().filter(v -> !v.getName().isEmpty()).toList());
		}
		return ECollections.emptyEList();
	}

	static EList<ITypedElement> getInOutParameters(final Event event) {
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

	static EList<ITypedElement> getInputParameters(final FB fb) {
		return ECollections.unmodifiableEList(fb.getInterface().getInputVars());
	}

	static EList<ITypedElement> getOutputParameters(final FB fb) {
		return ECollections.unmodifiableEList(
				fb.getInterface().getOutputVars().stream().filter(v -> !v.getName().isEmpty()).toList());
	}

	static EList<ITypedElement> getInOutParameters(final FB fb) {
		return ECollections.unmodifiableEList(fb.getInterface().getInOutVars());
	}

	static DataType getReturnType(final FB fb) {
		return fb.getInterface().getOutputVars().stream().filter(v -> v.getName().isEmpty()).findAny()
				.map(VarDeclaration::getType).orElse(null);
	}

	/**
	 * Do not call directly! Use {@link ICallable#getSignature()} instead.
	 *
	 * Must be accessible from derived models.
	 */
	public static String getSignature(final ICallable callable) {
		return callable.getName()
				+ Stream.of(callable.getInputParameters().stream().map(CallableAnnotations::getInputParameterSignature),
						callable.getInOutParameters().stream().map(CallableAnnotations::getInOutParameterSignature),
						callable.getOutputParameters().stream().map(CallableAnnotations::getOutputParameterSignature))
						.flatMap(Function.identity())
						.collect(Collectors.joining(", ", "(", callable.isVarargs() ? " ...)" : ")")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ (callable.getReturnType() != null ? " : " + callable.getReturnType().getName() : ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	static String getInputParameterSignature(final ITypedElement parameter) {
		return getParameterSignature(parameter, ""); //$NON-NLS-1$
	}

	static String getInOutParameterSignature(final ITypedElement parameter) {
		return getParameterSignature(parameter, "&&"); //$NON-NLS-1$
	}

	static String getOutputParameterSignature(final ITypedElement parameter) {
		return getParameterSignature(parameter, "&"); //$NON-NLS-1$
	}

	static String getParameterSignature(final ITypedElement parameter, final String typePrefix) {
		return parameter.getName() + " : " + typePrefix + parameter.getFullTypeName(); //$NON-NLS-1$
	}

	/**
	 * Do not call directly! Use {@link ICallable#isVarargs()} instead.
	 *
	 * Must be accessible from derived models.
	 */
	public static boolean isVarargs(final ICallable callable) {
		return false;
	}
}
