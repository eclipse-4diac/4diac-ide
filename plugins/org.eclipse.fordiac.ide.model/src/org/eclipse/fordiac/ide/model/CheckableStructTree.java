/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber - refactored class structure
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.TreeViewer;

public class CheckableStructTree extends AbstractStructTree<CheckableStructTreeNode> {

	public CheckableStructTree(final StructManipulator manipulator, final StructuredType struct,
			final TreeViewer viewer) {
		super(manipulator, struct, viewer);
	}

	public CheckableStructTree(final StructManipulator manipulator, final StructuredType struct) {
		super(manipulator, struct);
	}

	@Override
	public void buildTree(final StructManipulator struct, final StructuredType structType,
			final CheckableStructTreeNode parent) {
		for (final VarDeclaration memberVariable : structType.getMemberVariables()) {
			final CheckableStructTreeNode treeNode = parent.addChild(memberVariable);

			final IInterfaceElement variablePin = struct.getInterfaceElement(treeNode.getPinName());
			if ((variablePin != null) && !(variablePin instanceof ErrorMarkerInterface)) {
				treeNode.check(true);
			}

			if ((memberVariable.getType() instanceof final StructuredType structuredtype)
					&& (memberVariable.getType() != GenericTypes.ANY_STRUCT)) {
				buildTree(struct, structuredtype, treeNode);
			} else if (treeNode.isChecked()) {
				CheckableStructTreeNode.greyParents(treeNode);
			}
		}
	}

	@Override
	public CheckableStructTreeNode createRoot() {
		return new CheckableStructTreeNode(this);
	}

}
