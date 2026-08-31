/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.ocl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.fordiac.ide.validation.handlers.IValidationMarker;
import org.eclipse.fordiac.ide.validation.handlers.OCLParser;
import org.eclipse.fordiac.ide.validation.handlers.ValidationHelper;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;

public final class OCLValidationSession implements AutoCloseable {

	private final OCL ocl;
	private final List<OCLConstraintDefinition> definitions;
	private final OCLConstraintEvaluator evaluator;
	private final List<OCLMarker> markers = new ArrayList<>();

	public static OCLValidationSession create(final IProject project,
			final Collection<? extends INamedElement> validationTargets) {
		final OCL ocl = createOCL();
		try {
			setValidationExtent(ocl, validationTargets);
			return new OCLValidationSession(ocl, OCLParser.loadOCLConstraints(project, ocl));
		} catch (final RuntimeException e) {
			ocl.dispose();
			throw e;
		}
	}

	public static OCL createOCL() {
		return OCL.newInstance(new EcoreEnvironmentFactory(createPackageRegistry()));
	}

	public static void setValidationExtent(final OCL ocl, final Collection<? extends EObject> roots) {
		ocl.setExtentMap(createValidationExtent(roots));
	}

	public static Map<EClass, Set<EObject>> createValidationExtent(final Collection<? extends EObject> roots) {
		final Map<EClass, Set<EObject>> extent = new LinkedHashMap<>();
		for (final EObject root : roots) {
			addToValidationExtent(extent, root);
			for (final TreeIterator<EObject> iterator = root.eAllContents(); iterator.hasNext();) {
				addToValidationExtent(extent, iterator.next());
			}
		}
		extent.replaceAll((_, instances) -> Collections.unmodifiableSet(instances));
		return Collections.unmodifiableMap(extent);
	}

	private static EPackage.Registry createPackageRegistry() {
		final EPackage.Registry packageRegistry = new EPackageRegistryImpl();
		packageRegistry.putAll(EPackage.Registry.INSTANCE);
		registerPackage(packageRegistry, LibraryElementPackage.eINSTANCE);
		registerPackage(packageRegistry, DataPackage.eINSTANCE);
		registerPackage(packageRegistry, BuildpathPackage.eINSTANCE);
		return packageRegistry;
	}

	private static void registerPackage(final EPackage.Registry packageRegistry, final EPackage ePackage) {
		packageRegistry.put(ePackage.getNsURI(), ePackage);
		packageRegistry.put(ePackage.getName(), ePackage);
	}

	private static void addToValidationExtent(final Map<EClass, Set<EObject>> extent, final EObject object) {
		addInstance(extent, object.eClass(), object);
		for (final EClass superType : object.eClass().getEAllSuperTypes()) {
			addInstance(extent, superType, object);
		}
	}

	private static void addInstance(final Map<EClass, Set<EObject>> extent, final EClass type, final EObject object) {
		extent.computeIfAbsent(type, _ -> new LinkedHashSet<>()).add(object);
	}

	private OCLValidationSession(final OCL ocl, final OCLParser.ParseResult parseResult) {
		this.ocl = ocl;
		parseResult.problems().forEach(problem -> addSourceMarker(problem.source(), problem.message()));

		final List<OCLConstraintDefinition> loadedDefinitions = new ArrayList<>(parseResult.constraints().size());
		for (final OCLParser.LoadedConstraint loadedConstraint : parseResult.constraints()) {
			try {
				loadedDefinitions
						.add(OCLConstraintDefinition.from(loadedConstraint.constraint(), loadedConstraint.source()));
			} catch (final RuntimeException e) {
				addConstraintProblem(loadedConstraint.source(), e.getMessage(), e);
			}
		}
		definitions = List.copyOf(loadedDefinitions);
		evaluator = new OCLConstraintEvaluator(ocl, this::addConstraintProblem);
	}

	public void validate(final INamedElement validationTarget, final IProgressMonitor monitor) {
		final List<OCLMarker> validationMarkers = ValidationHelper.createValidationMarkers(validationTarget,
				definitions, evaluator, monitor);
		markers.addAll(validationMarkers);
	}

	public List<OCLMarker> getMarkers() {
		return List.copyOf(markers);
	}

	private void addConstraintProblem(final OCLConstraintEvaluator.ConstraintError error) {
		addConstraintProblem(error.definition().sourceFile(), error.message(), error.cause());
	}

	private void addConstraintProblem(final IFile source, final String message, final RuntimeException exception) {
		FordiacLogHelper.logError(message, exception);
		addSourceMarker(source, message);
	}

	private void addSourceMarker(final IFile source, final String message) {
		if (source != null) {
			markers.add(new OCLMarker(source,
					ErrorMarkerBuilder.createErrorMarkerBuilder(message).setType(IValidationMarker.TYPE)
							.setSeverity(IMarker.SEVERITY_ERROR)
							.setLocation(source.getProjectRelativePath().toString())));
		}
	}

	@Override
	public void close() {
		ocl.dispose();
	}
}
