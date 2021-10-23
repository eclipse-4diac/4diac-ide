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
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.TreeViewer;

public class CheckableStructTree extends AbstractStructTree<CheckableStructTreeNode> {

	public CheckableStructTree(StructManipulator manipulator, StructuredType struct, TreeViewer viewer) {
		super(manipulator, struct, viewer);
	}

	public CheckableStructTree(StructManipulator manipulator, StructuredType struct) {
		super(manipulator, struct);
	}

	@Override
	public void buildTree(StructManipulator struct, StructuredType structType, CheckableStructTreeNode parent) {
		for (final VarDeclaration memberVariable : structType.getMemberVariables()) {
			final CheckableStructTreeNode treeNode = parent.addChild(memberVariable);

			if (struct.getInterfaceElement(treeNode.getPinName()) != null) {
				treeNode.check(true);
			}

			if ((memberVariable.getType() instanceof StructuredType)
					&& (memberVariable.getType() != GenericTypes.ANY_STRUCT)) {
				buildTree(struct, (StructuredType) memberVariable.getType(), treeNode);
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
