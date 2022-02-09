/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.scoping;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.SimpleScope;
import org.eclipse.xtext.util.SimpleAttributeResolver;

/**
 * This class contains custom scoping description.
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
public class STCoreScopeProvider extends AbstractSTCoreScopeProvider {
	@Override
	public IScope getScope(final EObject context, final EReference reference) {
		if (reference == STCorePackage.Literals.ST_VAR_DECLARATION__TYPE) {
			final IScope globalScope = super.getScope(context, reference);
			final List<DataType> candidates = DataTypeLibrary.getNonUserDefinedDataTypes();
			return new SimpleScope(globalScope,
					Scopes.scopedElementsFor(candidates, QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)),
					false);
		}
		return super.getScope(context, reference);
	}
}
