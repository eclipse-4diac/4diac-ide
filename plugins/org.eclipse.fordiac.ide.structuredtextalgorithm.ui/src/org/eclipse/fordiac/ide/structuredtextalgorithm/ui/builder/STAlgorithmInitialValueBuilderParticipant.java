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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.ModelIssueListValidationMesageAcceptor;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.ValidationUtil;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreImportValidator;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreTypeUsageCollector;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class STAlgorithmInitialValueBuilderParticipant implements IXtextBuilderParticipant {

	@Inject
	private MarkerCreator markerCreator;

	@Inject
	private Provider<STCoreTypeUsageCollector> typeUsageCollectorProvider;

	@Inject
	private STCoreImportValidator importValidator;

	@Override
	public void build(final IBuildContext context, final IProgressMonitor monitor) throws CoreException {
		final List<IResourceDescription.Delta> deltas = getRelevantDeltas(context);
		for (final var delta : deltas) {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			if (delta.getOld() != null) {
				doClean(delta);
			}
			if (delta.getNew() != null) {
				doBuild(delta, context, monitor);
			}
		}
	}

	protected void doBuild(final IResourceDescription.Delta delta, final IBuildContext context,
			final IProgressMonitor monitor) throws CoreException {
		try {
			final IFile file = getFile(delta.getNew().getURI());
			final STCoreTypeUsageCollector typeUsageCollector = typeUsageCollectorProvider.get();
			final boolean ignoreWarnings = ValidationUtil.isIgnoreWarnings(file);
			final Resource resource = context.getResourceSet().getResource(delta.getUri(), true);
			final TreeIterator<EObject> allContents = EcoreUtil.getAllContents(resource, true);
			while (allContents.hasNext()) {
				if (monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
				final EObject target = allContents.next();
				if (target instanceof SystemConfiguration) {
					allContents.prune();
				} else if (target instanceof final Attribute attribute) {
					validateType(attribute, delta, typeUsageCollector, ignoreWarnings, context, monitor);
					validateValue(attribute, delta, typeUsageCollector, ignoreWarnings, context, monitor);
				} else if (target instanceof final VarDeclaration varDeclaration) {
					validateType(varDeclaration, delta, typeUsageCollector, ignoreWarnings, context, monitor);
					validateValue(varDeclaration, delta, typeUsageCollector, ignoreWarnings, context, monitor);
				} else if (target instanceof STSource) {
					typeUsageCollector.collectUsedTypes(target);
				}
			}
			for (final EObject object : resource.getContents()) {
				if (object instanceof final LibraryElement libraryElement) {
					validateImports(libraryElement, delta, typeUsageCollector.getUsedTypes(), ignoreWarnings, monitor);
				}
			}
		} catch (final OperationCanceledException e) {
			throw e;
		} catch (final RuntimeException e) {
			throw new CoreException(Status.error("Exception processing build delta " + delta.getUri().toString(), e)); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("static-method")
	protected void doClean(final IResourceDescription.Delta delta) throws CoreException {
		final IFile file = getFile(delta.getOld().getURI());
		if (file != null && file.exists()) {
			file.deleteMarkers(FordiacErrorMarker.INITIAL_VALUE_MARKER, true, IResource.DEPTH_INFINITE);
			file.deleteMarkers(FordiacErrorMarker.TYPE_DECLARATION_MARKER, true, IResource.DEPTH_INFINITE);
			file.deleteMarkers(FordiacErrorMarker.IMPORT_MARKER, true, IResource.DEPTH_INFINITE);
		}
	}

	protected void validateType(final VarDeclaration varDeclaration, final IResourceDescription.Delta delta,
			final STCoreTypeUsageCollector typeUsageCollector, final boolean ignoreWarnings,
			final IBuildContext context, final IProgressMonitor monitor) throws CoreException {
		if (ValidationUtil.isContainedInTypedInstance(varDeclaration)) {
			// do not validate type declarations inside typed instances, since those are
			// declared in the context of the type
			return;
		}
		final List<Issue> issues = new ArrayList<>();
		if (varDeclaration.getType() instanceof AnyType) {
			if (varDeclaration.isArray()) {
				validate(varDeclaration.getArraySize(), delta, typeUsageCollector, issues, context);
				issues.replaceAll(issue -> ValidationUtil.convertToModelIssue(issue, varDeclaration.getArraySize()));
			} else {
				typeUsageCollector.addUsedType(varDeclaration.getType());
			}
		}
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		if (!issues.isEmpty()) {
			final IFile file = getFile(delta.getUri());
			if (file != null && file.exists()) {
				createMarkers(file, FordiacErrorMarker.TYPE_DECLARATION_MARKER, issues, ignoreWarnings, monitor);
			}
		}
	}

	protected void validateValue(final VarDeclaration varDeclaration, final IResourceDescription.Delta delta,
			final STCoreTypeUsageCollector typeUsageCollector, final boolean ignoreWarnings,
			final IBuildContext context, final IProgressMonitor monitor) throws CoreException {
		final String value = getValue(varDeclaration);
		final List<Issue> issues = new ArrayList<>();
		// do not parse value if blank or variable has invalid type
		if (!value.isBlank() && varDeclaration.getType() instanceof AnyType) {
			final INamedElement featureType = STCoreUtil.getFeatureType(varDeclaration);
			try {
				new TypedValueConverter((DataType) featureType, true).toValue(value);
			} catch (final Exception e) {
				validate(varDeclaration.getValue(), delta, typeUsageCollector, issues, context);
				issues.replaceAll(issue -> ValidationUtil.convertToModelIssue(issue, varDeclaration.getValue()));
			}
		}
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		if (!issues.isEmpty()) {
			final IFile file = getFile(delta.getUri());
			if (file != null && file.exists()) {
				createMarkers(file, FordiacErrorMarker.INITIAL_VALUE_MARKER, issues, ignoreWarnings, monitor);
			}
		}
	}

	@SuppressWarnings("unused")
	protected static void validateType(final Attribute attribute, final IResourceDescription.Delta delta,
			final STCoreTypeUsageCollector typeUsageCollector, final boolean ignoreWarnings,
			final IBuildContext context, final IProgressMonitor monitor) {
		if (attribute.getAttributeDeclaration() != null) {
			typeUsageCollector.addUsedType(attribute.getAttributeDeclaration());
		} else if (attribute.getType() instanceof AnyType) {
			typeUsageCollector.addUsedType(attribute.getType());
		}
	}

	protected void validateValue(final Attribute attribute, final IResourceDescription.Delta delta,
			final STCoreTypeUsageCollector typeUsageCollector, final boolean ignoreWarnings,
			final IBuildContext context, final IProgressMonitor monitor) throws CoreException {
		final String value = getValue(attribute);
		final List<Issue> issues = new ArrayList<>();
		if (!value.isBlank() && attribute.getType() instanceof AnyType
				&& !InternalAttributeDeclarations.isInternalAttribute(attribute.getAttributeDeclaration())) {
			final DataType featureType = getActualType(attribute);
			try {
				new TypedValueConverter(featureType, attribute.getAttributeDeclaration() != null).toValue(value);
			} catch (final Exception e) {
				validate(attribute, delta, typeUsageCollector, issues, context);
				issues.replaceAll(issue -> ValidationUtil.convertToModelIssue(issue, attribute,
						LibraryElementPackage.Literals.ATTRIBUTE__VALUE));
			}
		}
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		if (!issues.isEmpty()) {
			final IFile file = getFile(delta.getUri());
			if (file != null && file.exists()) {
				createMarkers(file, FordiacErrorMarker.INITIAL_VALUE_MARKER, issues, ignoreWarnings, monitor);
			}
		}
	}

	protected void validateImports(final LibraryElement element, final IResourceDescription.Delta delta,
			final Set<QualifiedName> usedTypes, final boolean ignoreWarnings, final IProgressMonitor monitor)
			throws CoreException {
		final List<Import> imports = ImportHelper.getImports(element);
		if (imports.isEmpty()) {
			return;
		}

		final String packageName = PackageNameHelper.getPackageName(element);
		final ModelIssueListValidationMesageAcceptor acceptor = new ModelIssueListValidationMesageAcceptor();
		importValidator.validateImports(packageName, imports, usedTypes, acceptor);
		if (!acceptor.isEmpty()) {
			final IFile file = getFile(delta.getUri());
			if (file != null && file.exists()) {
				createMarkers(file, FordiacErrorMarker.IMPORT_MARKER, acceptor.getIssues(), ignoreWarnings, monitor);
			}
		}
	}

	protected static void validate(final EObject element, final IResourceDescription.Delta delta,
			final STCoreTypeUsageCollector typeUsageCollector, final List<Issue> issues, final IBuildContext context) {
		final String fragment = LibraryElementXtextResource
				.toExternalFragment(element.eResource().getURIFragment(element));
		final URI uri = delta.getUri().appendQuery(fragment);
		final Resource resource = context.getResourceSet().getResource(uri, true);
		if (resource instanceof final XtextResource xtextResource) {
			EcoreUtil.resolveAll(xtextResource);
			final IResourceValidator validator = xtextResource.getResourceServiceProvider().getResourceValidator();
			issues.addAll(validator.validate(xtextResource, CheckMode.FAST_ONLY, CancelIndicator.NullImpl));
		}
		if (!resource.getContents().isEmpty()) {
			typeUsageCollector.collectUsedTypes(resource.getContents().get(0));
		}
		resource.unload();
		context.getResourceSet().getResources().remove(resource);
	}

	protected void createMarkers(final IFile file, final String type, final List<Issue> issues,
			final boolean ignoreWarnings, final IProgressMonitor monitor) throws CoreException {
		for (final Issue issue : issues) {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			if (ValidationUtil.shouldProcess(issue, ignoreWarnings)) {
				markerCreator.createMarker(issue, file, type);
			}
		}
	}

	protected static String getValue(final VarDeclaration varDeclaration) {
		if (varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null) {
			return varDeclaration.getValue().getValue();
		}
		return ""; //$NON-NLS-1$
	}

	protected static String getValue(final Attribute attribute) {
		if (attribute.getValue() != null) {
			return attribute.getValue();
		}
		return ""; //$NON-NLS-1$
	}

	protected static DataType getActualType(final Attribute attribute) {
		if (attribute.getType() instanceof final DirectlyDerivedType directlyDerivedType) {
			return directlyDerivedType.getBaseType();
		}
		return attribute.getType();
	}

	protected List<IResourceDescription.Delta> getRelevantDeltas(final IBuildContext context) {
		return context.getDeltas().stream().filter(this::isRelevantDelta).toList();
	}

	protected boolean isRelevantDelta(final IResourceDescription.Delta delta) {
		return STAlgorithmResource.isValidUri(delta.getUri()) && !delta.getUri().hasQuery();
	}

	protected static IFile getFile(final URI uri) {
		if (uri.isPlatformResource()) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
		}
		return null;
	}
}
