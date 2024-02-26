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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.linking.impl.ImportedNamesAdapter;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.impl.DefaultResourceDescription;
import org.eclipse.xtext.util.IResourceScopeCache;

public class STAlgorithmResourceDescription extends DefaultResourceDescription {

	private final IQualifiedNameConverter nameConverter;

	private Iterable<QualifiedName> importedNames;

	public STAlgorithmResourceDescription(final Resource resource, final IDefaultResourceDescriptionStrategy strategy,
			final IResourceScopeCache cache, final IQualifiedNameConverter nameConverter) {
		super(resource, strategy, cache);
		this.nameConverter = nameConverter;
	}

	@Override
	public Iterable<QualifiedName> getImportedNames() {
		if (importedNames == null) {
			importedNames = computeImportedNames();
		}
		return importedNames;
	}

	protected Set<QualifiedName> computeImportedNames() {
		final Set<QualifiedName> result = new HashSet<>();
		final TreeIterator<EObject> allContents = EcoreUtil.getAllContents(getResource(), true);
		while (allContents.hasNext()) {
			final EObject target = allContents.next();
			if (target instanceof STSource) {
				allContents.prune();
			} else if (target instanceof final Attribute attribute) {
				computeImportedNames(attribute, result);
			} else if (target instanceof final VarDeclaration varDeclaration) {
				computeImportedNames(varDeclaration, result);
			} else if (target instanceof final TypedConfigureableObject typedConfigureableObject) {
				computeImportedNames(typedConfigureableObject, result);
			}
		}
		super.getImportedNames().forEach(result::add);
		return result;
	}

	protected void computeImportedNames(final Attribute attr, final Set<QualifiedName> result) {
		final String fullTypeName = PackageNameHelper.getFullTypeName(attr.getType());
		if (fullTypeName != null && !fullTypeName.isEmpty()) {
			result.add(nameConverter.toQualifiedName(fullTypeName).toLowerCase());
		}
		if (!STCoreUtil.isSimpleAttributeValue(attr)) {
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
			result.add(nameConverter.toQualifiedName(fullTypeName).toLowerCase());
		}
		if ((decl.isArray() && !TypeDeclarationParser.isSimpleTypeDeclaration(decl.getArraySize().getValue()))) {
			final STTypeDeclaration source = StructuredTextParseUtil.validateType(decl, null);
			if (source != null) {
				result.addAll(getImportedNames(source.eResource()));
			}
		}
		if (!STCoreUtil.isSimpleInitialValue(decl)) {
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
			result.add(nameConverter.toQualifiedName(fullTypeName).toLowerCase());
		}
	}

	protected static Set<QualifiedName> getImportedNames(final Resource resource) {
		EcoreUtil.resolveAll(resource);
		final ImportedNamesAdapter adapter = ImportedNamesAdapter.find(resource);
		if (adapter != null) {
			return adapter.getImportedNames();
		}
		return Collections.emptySet();
	}
}
