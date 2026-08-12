/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.libraryElement.impl;

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.getArraySize;
import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.setArraySize;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.VarDeclarationFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ContainerVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public class ContainerVarDeclarationAnnotations {

	static VarDeclaration getCachedMember(final ContainerVarDeclaration contVarDeclaration, final List<String> path,
			final boolean demandCreate) {
		VarDeclaration visibleMember = contVarDeclaration;

		for (int i = 0; i < path.size(); i++) {
			visibleMember = getCachedMember((ContainerVarDeclaration) visibleMember, path.get(i), demandCreate);
			if (visibleMember == null
					|| (i != path.size() - 1 && !(visibleMember instanceof final ContainerVarDeclaration))) {
				return null;
			}
		}

		return visibleMember;
	}

	private static VarDeclaration getCachedMember(final ContainerVarDeclaration contVarDeclaration,
			final String memberName, final boolean demandCreate) {

		final VarDeclaration visibleMember = contVarDeclaration.getCachedMember(memberName);
		if (visibleMember != null) {
			return visibleMember;
		}

		if (!demandCreate) {
			return null;
		}

		final DataType memberType = getMemberType(contVarDeclaration, memberName);
		if (memberType == null) {
			return null;
		}

		final VarDeclaration newVisibleMember = VarDeclarationFactory.createVarDecl(memberType);
		newVisibleMember.setName(memberName);
		newVisibleMember.setType(memberType);
		final VarDeclaration memVar = getMember(contVarDeclaration, memberName);
		if (memVar != null) {
			setArraySize(newVisibleMember, getArraySize(memVar));
		}
		newVisibleMember.setIsInput(contVarDeclaration.isIsInput());
		insertNewVisibleMember(contVarDeclaration, newVisibleMember);
		if (newVisibleMember.isIsInput()) {
			newVisibleMember.setValue(LibraryElementFactory.eINSTANCE.createValue());
		}
		return newVisibleMember;
	}

	private static DataType getMemberType(final ContainerVarDeclaration contVarDeclaration, final String memberName) {
		final VarDeclaration memVar = getMember(contVarDeclaration, memberName);
		if (memVar != null) {
			return memVar.getType();
		}
		if (contVarDeclaration.getType() instanceof final ErrorDataType errorDataType) {
			return errorDataType;
		}
		return null;
	}

	private static VarDeclaration getMember(final ContainerVarDeclaration structVarDeclarationImpl,
			final String memberName) {
		if (!(structVarDeclarationImpl.getType() instanceof final StructuredType type)) {
			// currently we only support struct member access
			return null;
		}
		return type.getMemberVar(memberName);
	}

	private static void insertNewVisibleMember(final ContainerVarDeclaration contVarDeclaration,
			final VarDeclaration newVisibleMember) {
		final EList<VarDeclaration> cachedMembers = contVarDeclaration.getCachedMembers();
		if (contVarDeclaration.getType() instanceof final StructuredType type) {
			final EList<VarDeclaration> typeMemVars = type.getMemberVariables();

			int pos = Collections.binarySearch(cachedMembers, newVisibleMember,
					Comparator.comparingInt(vm -> findIndex(typeMemVars, vm.getName())));
			if (pos < 0) {
				pos = -(pos + 1);
				// we have a position add it accordingly
				cachedMembers.add(pos, newVisibleMember);
				return;
			}
		}
		// per default add it at the end
		cachedMembers.add(newVisibleMember);

	}

	private static int findIndex(final EList<VarDeclaration> memberVars, final String varName) {
		for (int i = 0; i < memberVars.size(); i++) {
			if (memberVars.get(i).getName().equalsIgnoreCase(varName)) {
				return i;
			}
		}
		return -1;
	}

	public static TreeIterator<VarDeclaration> getAllCachedMembers(final ContainerVarDeclaration contVarDeclaration) {
		return new AbstractTreeIterator<>(contVarDeclaration, false) {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<VarDeclaration> getChildren(final Object object) {
				if (object instanceof final ContainerVarDeclaration container) {
					return container.getCachedMembers().iterator();
				}
				return Collections.emptyIterator();
			}
		};
	}

	public static boolean validateMemberInputConnections(final ContainerVarDeclaration contVarDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (contVarDeclaration.isIsInput() && !contVarDeclaration.getInputConnections().isEmpty()
				&& hasMemberInputConnection(contVarDeclaration)) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONTAINER_VAR_DECLARATION__VALIDATE_MEMBER_INPUT_CONNECTIONS,
						Messages.ContainerVarDeclarationAnnotations_MemberInputConnection,
						FordiacMarkerHelper.getDiagnosticData(contVarDeclaration,
								LibraryElementPackage.Literals.IINTERFACE_ELEMENT__INPUT_CONNECTIONS)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateMemberInitialValues(final ContainerVarDeclaration contVarDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (contVarDeclaration.hasValue() && hasMemberInitialValue(contVarDeclaration)) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONTAINER_VAR_DECLARATION__VALIDATE_MEMBER_INITIAL_VALUES,
						Messages.ContainerVarDeclarationAnnotations_MemberInitialValue,
						FordiacMarkerHelper.getDiagnosticData(contVarDeclaration)));
			}
			return false;
		}
		return true;
	}

	private static boolean hasMemberInputConnection(final ContainerVarDeclaration contVarDeclaration) {
		final TreeIterator<VarDeclaration> members = contVarDeclaration.getAllCachedMembers();
		while (members.hasNext()) {
			if (!members.next().getInputConnections().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasMemberInitialValue(final ContainerVarDeclaration contVarDeclaration) {
		final TreeIterator<VarDeclaration> members = contVarDeclaration.getAllCachedMembers();
		while (members.hasNext()) {
			if (members.next().hasValue()) {
				return true;
			}
		}
		return false;
	}

	private ContainerVarDeclarationAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
