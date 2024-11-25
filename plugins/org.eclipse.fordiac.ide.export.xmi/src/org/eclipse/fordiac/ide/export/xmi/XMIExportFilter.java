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
package org.eclipse.fordiac.ide.export.xmi;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.ExportFilter;
import org.eclipse.fordiac.ide.globalconstantseditor.ui.resource.GlobalConstantsResourceSetInitializer;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource.STAlgorithmResourceSetInitializer;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.parsetree.reconstr.STCoreCommentAssociater;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.resource.STFunctionResourceSetInitializer;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValues;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportFactory;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValues;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclarations;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

public class XMIExportFilter extends ExportFilter {
	public static final String XMI_EXTENSION = "xmi"; //$NON-NLS-1$

	@Override
	public void export(final IFile typeFile, final String destination, final boolean forceOverwrite)
			throws ExportException {
		export(typeFile, destination, forceOverwrite, null);
	}

	@Override
	public void export(final IFile typeFile, final String destination, final boolean forceOverwrite,
			final EObject element) throws ExportException {
		if (typeFile == null || !typeFile.exists()) {
			getErrors().add(Messages.XMIExportFilter_UnknownFile);
			return;
		}

		final URI sourceUri = URI.createPlatformResourceURI(typeFile.getFullPath().toString(), true);
		final URI destinationUri = URI.createFileURI(destination);
		final URI xmiUri = destinationUri.appendSegment(typeFile.getName()).appendFileExtension(XMI_EXTENSION);

		final XtextResourceSet resourceSet = new XtextResourceSet();
		new STAlgorithmResourceSetInitializer().initialize(resourceSet, typeFile.getProject());
		new STFunctionResourceSetInitializer().initialize(resourceSet, typeFile.getProject());
		new GlobalConstantsResourceSetInitializer().initialize(resourceSet, typeFile.getProject());
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);

		final Resource resource = resourceSet.getResource(sourceUri, true);
		if (resource instanceof final XtextResource xtextResource) {
			final EObject root = xtextResource.getParseResult().getRootASTElement();
			if (root instanceof final STSource source) {
				final var commentAssociater = ((XtextResource) resource).getResourceServiceProvider()
						.get(STCoreCommentAssociater.class);
				if (commentAssociater != null) {
					source.getComments().addAll(commentAssociater.associateComments(source));
				}
			}
		}
		resource.getContents().add(createInitialValues(resource));
		resource.getContents().add(createTypeDeclarations(resource));
		resource.getContents().add(createAttributeValues(resource));

		final ResourceSetImpl xmiResourceSet = new ResourceSetImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().putIfAbsent(XMI_EXTENSION,
				new XMIResourceFactoryImpl());
		final Resource xmiResource = xmiResourceSet.createResource(xmiUri);
		xmiResource.getContents().addAll(EcoreUtil.copyAll(resource.getContents()));

		if (!forceOverwrite && xmiResourceSet.getURIConverter().exists(xmiUri, Collections.emptyMap())
				&& !MessageDialog.openConfirm(Display.getDefault().getActiveShell(),
						Messages.XMIExportFilter_OverwriteDialogTitle,
						MessageFormat.format(Messages.XMIExportFilter_OverwriteDialogMessage, xmiUri.toFileString()))) {
			return;
		}

		try {
			final HashMap<String, Object> options = new HashMap<>();
			options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
			options.put(XMLResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);
			xmiResource.save(options);
		} catch (final IOException e) {
			getErrors().add(e.getMessage());
		}
	}

	protected XMIExportInitialValues createInitialValues(final Resource resource) {
		final var result = XMIExportFactory.eINSTANCE.createXMIExportInitialValues();
		StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(EcoreUtil.getAllProperContents(resource, true), 0), false)
				.filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast)
				.filter(XMIExportFilter::hasInitialValue).map(this::createInitialValue).flatMap(Optional::stream)
				.forEachOrdered(result.getInitialValues()::add);
		StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(EcoreUtil.getAllProperContents(resource, true), 0), false)
				.filter(DirectlyDerivedType.class::isInstance).map(DirectlyDerivedType.class::cast)
				.filter(XMIExportFilter::hasInitialValue).map(this::createInitialValue).flatMap(Optional::stream)
				.forEachOrdered(result.getInitialValues()::add);
		return result;
	}

	protected Optional<XMIExportInitialValue> createInitialValue(final VarDeclaration varDeclaration) {
		final var source = parseInitialValue(varDeclaration);
		if (source == null || source.getInitializerExpression() == null) {
			return Optional.empty();
		}
		final var result = XMIExportFactory.eINSTANCE.createXMIExportInitialValue();
		result.setVariable(varDeclaration);
		result.setExpression(source.getInitializerExpression());
		result.setValue(evaluateInitialValue(varDeclaration));
		return Optional.of(result);
	}

	protected STInitializerExpressionSource parseInitialValue(final VarDeclaration varDeclaration) {
		return StructuredTextParseUtil.parse(varDeclaration.getValue().getValue(), varDeclaration.eResource().getURI(),
				STCoreUtil.getFeatureType(varDeclaration),
				EcoreUtil2.getContainerOfType(varDeclaration, LibraryElement.class), null, getErrors(), getWarnings(),
				getInfos());
	}

	protected String evaluateInitialValue(final VarDeclaration varDeclaration) {
		try {
			return VariableOperations.newVariable(varDeclaration).toString();
		} catch (final Exception e) {
			getErrors().add(MessageFormat.format(Messages.XMIExportFilter_InitialValueError,
					varDeclaration.getQualifiedName(), e.getMessage()));
			return null;
		}
	}

	protected static boolean hasInitialValue(final VarDeclaration varDeclaration) {
		return varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null
				&& !varDeclaration.getValue().getValue().isBlank();
	}

	protected Optional<XMIExportInitialValue> createInitialValue(final DirectlyDerivedType directlyDerivedType) {
		final var source = parseInitialValue(directlyDerivedType);
		if (source == null || source.getInitializerExpression() == null) {
			return Optional.empty();
		}
		final var result = XMIExportFactory.eINSTANCE.createXMIExportInitialValue();
		result.setVariable(directlyDerivedType);
		result.setExpression(source.getInitializerExpression());
		result.setValue(evaluateInitialValue(directlyDerivedType));
		return Optional.of(result);
	}

	protected STInitializerExpressionSource parseInitialValue(final DirectlyDerivedType directlyDerivedType) {
		return StructuredTextParseUtil.parse(directlyDerivedType.getInitialValue(),
				directlyDerivedType.eResource().getURI(), directlyDerivedType.getBaseType(),
				EcoreUtil2.getContainerOfType(directlyDerivedType, LibraryElement.class), null, getErrors(),
				getWarnings(), getInfos());
	}

	protected String evaluateInitialValue(final DirectlyDerivedType directlyDerivedType) {
		try {
			return VariableOperations.newVariable(directlyDerivedType).toString();
		} catch (final Exception e) {
			getErrors().add(MessageFormat.format(Messages.XMIExportFilter_InitialValueError,
					directlyDerivedType.getQualifiedName(), e.getMessage()));
			return null;
		}
	}

	protected static boolean hasInitialValue(final DirectlyDerivedType directlyDerivedType) {
		return directlyDerivedType.getInitialValue() != null && !directlyDerivedType.getInitialValue().isBlank();
	}

	protected XMIExportTypeDeclarations createTypeDeclarations(final Resource resource) {
		final var result = XMIExportFactory.eINSTANCE.createXMIExportTypeDeclarations();
		StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(EcoreUtil.getAllProperContents(resource, true), 0), false)
				.filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast)
				.filter(VarDeclaration::isArray).map(this::createTypeDeclaration).flatMap(Optional::stream)
				.forEachOrdered(result.getTypeDeclarations()::add);
		return result;
	}

	protected Optional<XMIExportTypeDeclaration> createTypeDeclaration(final VarDeclaration varDeclaration) {
		final var source = parseTypeDeclaration(varDeclaration);
		if (source == null || source.getType() == null) {
			return Optional.empty();
		}
		final var result = XMIExportFactory.eINSTANCE.createXMIExportTypeDeclaration();
		result.setVariable(varDeclaration);
		result.setTypeDeclaration(source);
		result.setResultType(EcoreUtil.copy(evaluateResultType(varDeclaration)));
		return Optional.of(result);
	}

	protected STTypeDeclaration parseTypeDeclaration(final VarDeclaration varDeclaration) {
		return StructuredTextParseUtil.parseType(varDeclaration, getErrors(), getWarnings(), getInfos());
	}

	protected INamedElement evaluateResultType(final VarDeclaration varDeclaration) {
		try {
			return VariableOperations.evaluateResultType(varDeclaration);
		} catch (final Exception e) {
			getErrors().add(MessageFormat.format(Messages.XMIExportFilter_ResultTypeError,
					varDeclaration.getQualifiedName(), e.getMessage()));
			return null;
		}
	}

	protected XMIExportAttributeValues createAttributeValues(final Resource resource) {
		final var result = XMIExportFactory.eINSTANCE.createXMIExportAttributeValues();
		StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(EcoreUtil.getAllProperContents(resource, true), 0), false)
				.filter(Attribute.class::isInstance).map(Attribute.class::cast)
				.filter(XMIExportFilter::hasAttributeValue).map(this::createAttributeValue).flatMap(Optional::stream)
				.forEachOrdered(result.getAttributeValues()::add);
		return result;
	}

	protected Optional<XMIExportAttributeValue> createAttributeValue(final Attribute attribute) {
		final var source = parseAttributeValue(attribute);
		if (source == null || source.getInitializerExpression() == null) {
			return Optional.empty();
		}
		final var result = XMIExportFactory.eINSTANCE.createXMIExportAttributeValue();
		result.setAttribute(attribute);
		result.setExpression(source.getInitializerExpression());
		result.setValue(evaluateAttributeValue(attribute));
		return Optional.of(result);
	}

	protected STInitializerExpressionSource parseAttributeValue(final Attribute attribute) {
		return StructuredTextParseUtil.parse(attribute.getValue(), attribute.eResource().getURI(), attribute.getType(),
				EcoreUtil2.getContainerOfType(attribute, LibraryElement.class), null, getWarnings(), getWarnings(),
				getInfos());
	}

	protected String evaluateAttributeValue(final Attribute attribute) {
		try {
			return VariableOperations.newVariable(attribute).toString();
		} catch (final Exception e) {
			getWarnings().add(MessageFormat.format(Messages.XMIExportFilter_AttributeValueError,
					attribute.getQualifiedName(), e.getMessage()));
			return null;
		}
	}

	protected static boolean hasAttributeValue(final Attribute attribute) {
		return attribute.getType() instanceof AnyType && attribute.getValue() != null
				&& !attribute.getValue().isBlank();
	}
}
