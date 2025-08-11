/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.folding;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethodBody;
import org.eclipse.xtext.ui.editor.folding.DefaultFoldingRegionProvider;

public class STAlgorithmFoldingRegionProvider extends DefaultFoldingRegionProvider {

	@Override
	protected boolean isHandled(final EObject eObject) {
		return super.isHandled(eObject) && !(eObject instanceof STAlgorithmBody || eObject instanceof STMethodBody);
	}
}
