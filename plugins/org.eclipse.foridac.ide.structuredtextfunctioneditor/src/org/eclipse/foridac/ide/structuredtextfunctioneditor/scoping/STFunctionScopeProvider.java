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
package org.eclipse.foridac.ide.structuredtextfunctioneditor.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;

/**
 * This class contains custom scoping description.
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
public class STFunctionScopeProvider extends AbstractSTFunctionScopeProvider {
	@Override
	public IScope getScope(final EObject context, final EReference reference) {
		// TODO: add elementary final data types to global scope returns
		// if (context instanceof VarDeclaration && reference == STCorePackage.Literals.VAR_DECLARATION__TYPE) {
		// final STFunction rootElement = (STFunction) EcoreUtil.getRootContainer(context);
		// final Resource resource = rootElement.eResource();
		// final IFile functionFile = ResourcesPlugin.getWorkspace().getRoot()
		// .getFile(new Path(resource.getURI().toPlatformString(true)));
		// final IProject project = functionFile.getProject();
		// final TypeLibrary typeLibrary = TypeLibrary.getTypeLibrary(project);
		// final DataTypeLibrary dataTypeLibrary = typeLibrary.getDataTypeLibrary();
		// final List<DataType> candidates = dataTypeLibrary.getNonUserDefinedDataTypes();
		// return new SimpleScope(
		// Scopes.scopedElementsFor(candidates, QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)), false);
		// }
		return super.getScope(context, reference);
	}
}
