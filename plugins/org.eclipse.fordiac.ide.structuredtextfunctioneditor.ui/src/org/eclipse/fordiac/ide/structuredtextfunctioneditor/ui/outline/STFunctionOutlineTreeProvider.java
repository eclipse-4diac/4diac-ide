/**
 * Copyright (c) 2022, 2024 Primetals Technologies GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst
 *       - add headings support
 *       - add outline structure
 */
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.outline;

import org.eclipse.fordiac.ide.structuredtextcore.ui.outline.STCoreOutlineTreeProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;

@SuppressWarnings({ "static-method", "java:S100" })
public class STFunctionOutlineTreeProvider extends STCoreOutlineTreeProvider {

	protected boolean _isLeaf(final STFunctionSource modelElement) {
		return modelElement.getFunctions().isEmpty();
	}

	protected void _createChildren(final IOutlineNode parentNode, final STFunctionSource modelElement) {
		modelElement.getFunctions().forEach(function -> createNode(parentNode, function));
	}

	protected boolean _isLeaf(final STFunction modelElement) {
		return modelElement.getVarDeclarations().isEmpty() && !hasHeadings(modelElement);
	}

	protected void _createChildren(final IOutlineNode parentNode, final STFunction modelElement) {
		modelElement.getVarDeclarations().forEach(block -> createNode(parentNode, block));
		createHeadingNodes(parentNode, modelElement);
	}
}
