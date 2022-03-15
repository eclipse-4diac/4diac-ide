/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH
 *               2022 Martin Erich Jobst
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
 *   Martin Jobst
 *       - add scope for function return values
 *       - clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.scoping;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;
import org.eclipse.xtext.scoping.IScope;

/** This class contains custom scoping description.
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping on how and when to use it. */
public class STFunctionScopeProvider extends AbstractSTFunctionScopeProvider {
	@Override
	public IScope getScope(final EObject context, final EReference reference) {
		if (reference == STFunctionPackage.Literals.ST_FUNCTION__RETURN_TYPE) {
			final IScope globalScope = super.getScope(context, reference);
			return scopeFor(DataTypeLibrary.getNonUserDefinedDataTypes(), globalScope);
		} else if (reference == STCorePackage.Literals.ST_CALL_NAMED_OUTPUT_ARGUMENT__TARGET) {
			final var function = getFunction(context);
			return function != null ? scopeFor(List.of(function), super.getScope(context, reference))
					: super.getScope(context, reference);
		}
		return super.getScope(context, reference);
	}

	protected STFunction getFunction(final EObject context) {
		if (context instanceof STFunction) {
			return (STFunction) context;
		} else if (context != null) {
			return getFunction(context.eContainer());
		}
		return null;
	}
}
