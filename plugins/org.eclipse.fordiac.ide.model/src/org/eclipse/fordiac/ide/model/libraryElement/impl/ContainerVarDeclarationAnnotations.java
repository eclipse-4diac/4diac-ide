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

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.VarDeclarationFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ContainerVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ContainerVarDeclarationAnnotations {

	static VarDeclaration getCachedMember(final ContainerVarDeclaration contVarDeclaration, final String[] path,
			final boolean demandCreate) {
		VarDeclaration visibleMember = contVarDeclaration;

		for (int i = 0; i < path.length; i++) {
			visibleMember = getCachedMember((ContainerVarDeclaration) visibleMember, path[i], demandCreate);
			if (visibleMember == null
					|| (i != path.length - 1 && !(visibleMember instanceof final ContainerVarDeclaration))) {
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

		final VarDeclaration memVar = getMember(contVarDeclaration, memberName);

		if (memVar == null) {
			return null;
		}

		final VarDeclaration newVisibleMember = VarDeclarationFactory.createVarDecl(memVar.getType());
		if (newVisibleMember instanceof ContainerVarDeclaration) {
			// intermediate container access pins are created per default invisible as they
			// may be only needed to store visible children of them
			// if they are visible commands or the parser will set them visible
			newVisibleMember.setVisible(false);
		}
		newVisibleMember.setName(memberName);
		newVisibleMember.setType(memVar.getType());
		setArraySize(newVisibleMember, getArraySize(memVar));
		newVisibleMember.setIsInput(contVarDeclaration.isIsInput());
		contVarDeclaration.getCachedMembers().add(newVisibleMember);
		return newVisibleMember;
	}

	private static VarDeclaration getMember(final ContainerVarDeclaration structVarDeclarationImpl,
			final String memberName) {
		if (!(structVarDeclarationImpl.getType() instanceof final StructuredType type)) {
			// currently we only support struct member access
			return null;
		}
		return type.getMemberVar(memberName);
	}

	private ContainerVarDeclarationAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
