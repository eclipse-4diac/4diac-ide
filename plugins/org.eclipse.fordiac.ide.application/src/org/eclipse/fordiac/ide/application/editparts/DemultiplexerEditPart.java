/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DemultiplexerEditPart extends AbstractStructManipulatorEditPart {

	@Override
	public Demultiplexer getModel() {
		return (Demultiplexer) super.getModel();
	}

	@Override
	protected List<Object> getModelChildren() {
		final List<Object> children = super.getModelChildren();
		if (getModel().isIsConfigured() && isTopLevelPinHidden()) {
			children.addAll(getPinIndicators(false, true));
		}
		return children;
	}

	private boolean isTopLevelPinHidden() {
		if (getModel().getDataType() instanceof final StructuredType struct) {
			final EList<VarDeclaration> topLevelChildren = struct.getMemberVariables();
			final EList<VarDeclaration> visibleChildren = getModel().getMemberVars();
			if (topLevelChildren.size() > visibleChildren.size()) {
				return true;
			}
			return compareVisibleChildrenNames(topLevelChildren, visibleChildren);
		}
		return false;
	}

	private static boolean compareVisibleChildrenNames(final EList<VarDeclaration> topLevelChildren,
			final EList<VarDeclaration> visibleChildren) {
		for (final VarDeclaration structVar : topLevelChildren) {
			if (visibleChildren.stream().noneMatch(visibleVar -> structVar.getName().equals(visibleVar.getName()))) {
				return true;
			}
		}
		return false;
	}
}
