/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial commit of contract specification editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.scoping;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;

/**
 * This class contains custom scoping description.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
public class ContractSpecScopeProvider extends AbstractContractSpecScopeProvider {

	public static URI interfaceURI;
	private static final ContractSpecPackage pack = ContractSpecPackage.eINSTANCE;

	@Override
	public IScope getScope(final EObject context, final EReference reference) {
		if (interfaceURI != null && (reference == pack.getEventSpec_Port() || reference == pack.getCausalFuncDecl_P1()
				|| reference == pack.getCausalFuncDecl_P2())) {

			final var res = context.eResource().getResourceSet().getResource(interfaceURI, false);
			if (res != null) {
				return Scopes.scopeFor(res.getContents());
			}
		}
		return super.getScope(context, reference);
	}
}
