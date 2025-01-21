/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.Triple;

import com.google.inject.Inject;

public abstract class STAbstractCorePartitioner<E extends INamedElement> implements STCorePartitioner {

	@Inject
	private IEObjectDocumentationProvider documentationProvider;

	protected static Import convertImport(final STImport decl) {
		if (decl.getImportedNamespace() == null || decl.getImportedNamespace().isEmpty()) {
			return null;
		}
		final var result = LibraryElementFactory.eINSTANCE.createImport();
		result.setImportedNamespace(decl.getImportedNamespace());
		return result;
	}

	protected VarDeclaration convertInputParameter(final STVarDeclaration declaration) {
		final var result = convertVariable(declaration);
		result.setIsInput(true);
		return result;
	}

	protected VarDeclaration convertOutputParameter(final STVarDeclaration declaration) {
		return convertVariable(declaration);
	}

	protected VarDeclaration convertInOutParameter(final STVarDeclaration declaration) {
		return convertInputParameter(declaration);
	}

	protected VarDeclaration convertVariable(final STVarDeclaration declaration) {
		final VarDeclaration result = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		result.setName(declaration.getName());
		final String comment = getDocumentationProvider().getDocumentation(declaration);
		if (comment != null) {
			result.setComment(comment);
		}
		result.setType(resolveDataType(declaration.getType(), declaration, GenericTypes.ANY));
		if (declaration.isArray()) {
			ArraySizeHelper.setArraySize(result, extractArraySize(declaration));
		}
		if (declaration.getDefaultValue() != null) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			value.setValue(extractDefaultValue(declaration));
			result.setValue(value);
		}
		if (declaration.getPragma() != null) {
			declaration.getPragma().getAttributes().stream().filter(STAbstractCorePartitioner::isValidAttribute)
					.map(this::convertAttribute).forEachOrdered(result.getAttributes()::add);
		}
		return result;
	}

	protected static boolean isValidParameter(final STVarDeclaration declaration) {
		return declaration.getName() != null && !declaration.getName().isEmpty();
	}

	protected static String extractArraySize(final STVarDeclaration declaration) {
		return NodeModelUtils.findNodesForFeature(declaration, STCorePackage.eINSTANCE.getSTVarDeclaration_Ranges())
				.stream().map(INode::getText).collect(Collectors.joining(",")); //$NON-NLS-1$
	}

	protected static String extractDefaultValue(final STVarDeclaration declaration) {
		return NodeModelUtils
				.findNodesForFeature(declaration, STCorePackage.eINSTANCE.getSTVarDeclaration_DefaultValue()).stream()
				.map(INode::getText).collect(Collectors.joining()).trim();
	}

	protected Attribute convertAttribute(final STAttribute attribute) {
		final Attribute result = LibraryElementFactory.eINSTANCE.createAttribute();
		result.setName(PackageNameHelper.getFullTypeName(attribute.getDeclaration()));
		final String comment = getDocumentationProvider().getDocumentation(attribute);
		if (comment != null) {
			result.setComment(comment);
		}
		result.setType(attribute.getDeclaration().getType());
		result.setAttributeDeclaration(attribute.getDeclaration());
		result.setValue(extractValue(attribute));
		return result;
	}

	protected static boolean isValidAttribute(final STAttribute attribute) {
		return attribute.getDeclaration() != null && attribute.getDeclaration().getType() != null
				&& attribute.getValue() != null;
	}

	protected static String extractValue(final STAttribute attribute) {
		return NodeModelUtils.findNodesForFeature(attribute, STCorePackage.eINSTANCE.getSTAttribute_Value()).stream()
				.map(INode::getText).collect(Collectors.joining()).trim();
	}

	protected static DataType resolveDataType(final INamedElement type, final EObject context,
			final DataType defaultType) {
		if (type != null && type.eIsProxy()) {
			final String linkName = extractLinkName(type, context);
			if (!linkName.isEmpty()) {
				final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(context);
				if (typeLibrary != null) {
					return typeLibrary.getDataTypeLibrary().getType(linkName);
				}
			}
		} else if (type instanceof final DataType dataType) {
			return dataType;
		}
		return defaultType;
	}

	protected static String extractLinkName(final EObject proxy, final EObject context) {
		return extractLinkName(EcoreUtil.getURI(proxy).fragment(), context);
	}

	protected static String extractLinkName(final String uriFragment, final EObject context) {
		if (context.eResource() instanceof final LazyLinkingResource resource
				&& resource.getEncoder().isCrossLinkFragment(resource, uriFragment)) {
			final Triple<EObject, EReference, INode> triple = resource.getEncoder().decode(resource, uriFragment);
			return resource.getLinkingHelper().getCrossRefNodeAsString(triple.getThird(), true);
		}
		return ""; //$NON-NLS-1$
	}

	protected static void generatePackage(final LibraryElement libraryElement, final StringBuilder builder) {
		final String packageName = PackageNameHelper.getPackageName(libraryElement);
		if (!packageName.isBlank()) {
			builder.append("PACKAGE "); //$NON-NLS-1$
			builder.append(packageName);
			builder.append(";"); //$NON-NLS-1$
			builder.append(CommonElementExporter.LINE_END);
			builder.append(CommonElementExporter.LINE_END);
		}
	}

	protected static void generateImports(final LibraryElement libraryElement, final StringBuilder builder) {
		ImportHelper.getImports(libraryElement).forEach(imp -> {
			builder.append("IMPORT "); //$NON-NLS-1$
			builder.append(imp.getImportedNamespace());
			builder.append(";"); //$NON-NLS-1$
			builder.append(CommonElementExporter.LINE_END);
		});
		if (!ImportHelper.getImports(libraryElement).isEmpty()) {
			builder.append(CommonElementExporter.LINE_END);
		}
	}

	protected static void generateVariable(final VarDeclaration varDeclaration, final StringBuilder builder) {
		builder.append(varDeclaration.getName());
		builder.append(" : "); //$NON-NLS-1$
		builder.append(varDeclaration.getFullTypeName());
		if (varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null
				&& !varDeclaration.getValue().getValue().isBlank()) {
			builder.append(" := "); //$NON-NLS-1$
			builder.append(varDeclaration.getValue().getValue());
		}
		builder.append(";"); //$NON-NLS-1$
		if (varDeclaration.getComment() != null && !varDeclaration.getComment().isBlank()) {
			builder.append(" // "); //$NON-NLS-1$
			builder.append(varDeclaration.getComment());
		}
	}

	protected static void handleDuplicates(final List<? extends INamedElement> result) {
		result.stream().collect(Collectors.groupingBy(INamedElement::getName)).forEach((name, duplicates) -> IntStream
				.range(1, duplicates.size()).forEach(index -> duplicates.get(index).setName(name + "_" + index))); //$NON-NLS-1$
	}

	protected IEObjectDocumentationProvider getDocumentationProvider() {
		return documentationProvider;
	}
}
