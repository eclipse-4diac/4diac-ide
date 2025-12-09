/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.xtext.ui.refactoring2.rename.ISimpleNameProvider.DefaultImpl;
import org.eclipse.xtext.util.SimpleAttributeResolver;

@SuppressWarnings("restriction")
public class STCoreSimpleNameProvider extends DefaultImpl {

	@Override
	public boolean canRename(final EObject target) {
		if (target instanceof STSource) {
			return false; // cannot rename source (i.e., package declaration)
		}
		return super.canRename(target);
	}

	@Override
	protected EAttribute getNameEAttribute(final EObject target) {
		return SimpleAttributeResolver.NAME_RESOLVER.getAttribute(target);
	}
}
