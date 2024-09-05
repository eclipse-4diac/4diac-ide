/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.outline;

import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSourceElement;
import org.eclipse.fordiac.ide.structuredtextcore.ui.outline.STCoreOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;

@SuppressWarnings({ "static-method", "java:S100" })
public class STAlgorithmOutlineTreeProvider extends STCoreOutlineTreeProvider {

	protected boolean _isLeaf(final STAlgorithmSourceElement modelElement) {
		return !hasHeadings(modelElement);
	}

	protected void _createChildren(final IOutlineNode parentNode, final STAlgorithmSourceElement modelElement) {
		createHeadingNodes(parentNode, modelElement);
	}
}
