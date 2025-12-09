/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.resource;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescription;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IResourceScopeCache;

public class STAlgorithmResourceDescription extends STCoreResourceDescription {

	public STAlgorithmResourceDescription(final Resource resource, final IDefaultResourceDescriptionStrategy strategy,
			final IResourceScopeCache cache, final IQualifiedNameConverter nameConverter) {
		super(resource, strategy, cache, nameConverter);
	}

	@Override
	protected void computeImportedNames(final EObject object, final Set<QualifiedName> result) {
		switch (object) {
		case final Attribute attribute -> computeImportedNames(attribute, result);
		case final VarDeclaration varDeclaration -> computeImportedNames(varDeclaration, result);
		case final TypedConfigureableObject typedConfigureableObject ->
			computeImportedNames(typedConfigureableObject, result);
		case final Import imp -> computeImportedNames(imp, result);
		default -> {
			// ignore
		}
		}
	}

	protected void computeImportedNames(final Attribute attr, final Set<QualifiedName> result) {
		final String fullTypeName = PackageNameHelper.getFullTypeName(attr.getType());
		if (fullTypeName != null && !fullTypeName.isEmpty()) {
			result.add(getNameConverter().toQualifiedName(fullTypeName).toLowerCase());
		}
		if (!STCoreUtil.isSimpleAttributeValue(attr, false)) {
			final STInitializerExpressionSource source = StructuredTextParseUtil.validate(attr.getValue(), getURI(),
					STCoreUtil.getFeatureType(attr), EcoreUtil2.getContainerOfType(attr, LibraryElement.class), null,
					null);
			if (source != null) {
				result.addAll(getImportedNames(source.eResource()));
			}
		}
	}

	protected void computeImportedNames(final VarDeclaration decl, final Set<QualifiedName> result) {
		final String fullTypeName = PackageNameHelper.getFullTypeName(decl.getType());
		if (fullTypeName != null && !fullTypeName.isEmpty()) {
			result.add(getNameConverter().toQualifiedName(fullTypeName).toLowerCase());
		}
		if ((decl.isArray() && !TypeDeclarationParser.isSimpleTypeDeclaration(decl.getArraySize().getValue()))) {
			final STTypeDeclaration source = StructuredTextParseUtil.validateType(decl, null);
			if (source != null) {
				result.addAll(getImportedNames(source.eResource()));
			}
		}
		if (!STCoreUtil.isSimpleInitialValue(decl, false)) {
			final STInitializerExpressionSource source = StructuredTextParseUtil.validate(decl.getValue().getValue(),
					getURI(), STCoreUtil.getFeatureType(decl),
					EcoreUtil2.getContainerOfType(decl, LibraryElement.class), null, null);
			if (source != null) {
				result.addAll(getImportedNames(source.eResource()));
			}
		}
	}

	protected void computeImportedNames(final TypedConfigureableObject element, final Set<QualifiedName> result) {
		final String fullTypeName = element.getFullTypeName();
		if (fullTypeName != null && !fullTypeName.isEmpty()) {
			result.add(getNameConverter().toQualifiedName(fullTypeName).toLowerCase());
		}
	}
}
