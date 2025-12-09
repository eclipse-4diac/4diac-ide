/*******************************************************************************
 * Copyright (c) 2022, 2025 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst
 *       - add outline structure
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.outline;

import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.structuredtextcore.ui.outline.STCoreOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;

@SuppressWarnings({ "static-method", "java:S100" })
public class GlobalConstantsOutlineTreeProvider extends STCoreOutlineTreeProvider {

	protected boolean _isLeaf(final STGlobalConstsSource modelElement) {
		return modelElement.getConstants() == null;
	}

	protected void _createChildren(final IOutlineNode parentNode, final STGlobalConstsSource modelElement) {
		if (modelElement.getConstants() != null) {
			createNode(parentNode, modelElement.getConstants());
		}
	}
}
