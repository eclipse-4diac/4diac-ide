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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.sTCore.VarDeclaration;
import org.eclipse.foridac.ide.structuredtextfunctioneditor.sTFunction.STFunction;
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
public class STFunctionScopeProvider extends AbstractSTFunctionScopeProvider {
	@Override
	public IScope getScope(final EObject context, final EReference reference) {
		if (context instanceof VarDeclaration && reference == STCorePackage.Literals.VAR_DECLARATION__TYPE) {
			final STFunction rootElement = (STFunction) EcoreUtil.getRootContainer(context);
			final Resource resource = rootElement.eResource();
			final IFile functionFile = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(new Path(resource.getURI().toPlatformString(true)));
			final IProject project = functionFile.getProject();
			final TypeLibrary typeLibrary = TypeLibrary.getTypeLibrary(project);
			final DataTypeLibrary dataTypeLibrary = typeLibrary.getDataTypeLibrary();
			final List<DataType> candidates = dataTypeLibrary.getDataTypes();
			return new SimpleScope(
					Scopes.scopedElementsFor(candidates, QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)), false);
		}
		return super.getScope(context, reference);
	}
}
