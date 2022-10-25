/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
 *******************************************************************************/

package org.eclipse.fordiac.ide.globalconstantseditor.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.GlobalConstantsPackage;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.xtext.scoping.IScope;

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
public class GlobalConstantsScopeProvider extends AbstractGlobalConstantsScopeProvider {
	
	@Override
	public IScope getScope(final EObject context, final EReference reference) {
		if (reference == GlobalConstantsPackage.Literals.ST_VAR_GLOBAL_DECLARATION_BLOCK__VAR_DECLARATIONS) {
			final IScope globalScope = super.getScope(context, reference);
			return scopeFor(DataTypeLibrary.getNonUserDefinedDataTypes(), globalScope);
		}
		return super.getScope(context, reference);
	}

}
