/*******************************************************************************
 * Copyright (c) 2020, 2025 Sandor Bacsi, Johannes Kepler University
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *   Ernst Blecha - run as a Job, add progress indication
 *   Michael Oberlehner - extract synchronous validation for OCL builder
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.helpers.ModelHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.validation.Messages;
import org.eclipse.fordiac.ide.validation.ocl.OCLConstraintDefinition;
import org.eclipse.fordiac.ide.validation.ocl.OCLConstraintEvaluator;
import org.eclipse.fordiac.ide.validation.ocl.OCLDiagnostic;
import org.eclipse.fordiac.ide.validation.ocl.OCLMarker;

public final class ValidationHelper {

	public static List<OCLMarker> createValidationMarkers(final INamedElement namedElement,
			final List<OCLConstraintDefinition> definitions, final OCLConstraintEvaluator evaluator,
			final IProgressMonitor monitor) {
		if (namedElement == null) {
			return List.of();
		}
		final IResource defaultResource = ModelHelper.getFileFromContext(namedElement);
		if (defaultResource == null) {
			return List.of();
		}

		final List<EObject> objects = collectObjects(namedElement);
		final SubMonitor progress = SubMonitor.convert(monitor, Math.max(1, objects.size()));
		final Map<EClass, List<OCLConstraintDefinition>> definitionsByType = new HashMap<>();
		final List<OCLMarker> markers = new ArrayList<>();
		for (final EObject object : objects) {
			checkCanceled(progress);
			for (final OCLConstraintDefinition definition : definitionsByType.computeIfAbsent(object.eClass(),
					type -> findApplicableDefinitions(type, definitions))) {
				checkCanceled(progress);
				progress.setTaskName(MessageFormat.format(Messages.ValidationHelper_SubtaskFormat,
						createHierarchicalName(object), definition.constraint().getName()));
				evaluator.evaluate(object, definition)
						.map(diagnostic -> createMarker(defaultResource, diagnostic)).ifPresent(markers::add);
			}
			progress.worked(1);
		}
		return markers;
	}

	private static List<OCLConstraintDefinition> findApplicableDefinitions(final EClass type,
			final List<OCLConstraintDefinition> definitions) {
		return definitions.stream().filter(definition -> definition.appliesTo(type)).toList();
	}

	private static OCLMarker createMarker(final IResource defaultResource, final OCLDiagnostic diagnostic) {
		final EObject markerTarget = diagnostic.markerTarget();
		final IResource targetResource = ModelHelper.getFileFromContext(markerTarget);
		final ErrorMarkerBuilder builder = ErrorMarkerBuilder.createErrorMarkerBuilder(diagnostic.message())
				.setType(IValidationMarker.TYPE).setSeverity(diagnostic.severity())
				.setLocation(createHierarchicalName(markerTarget)).setTarget(markerTarget);
		return new OCLMarker(targetResource != null ? targetResource : defaultResource, builder);
	}

	private static List<EObject> collectObjects(final INamedElement namedElement) {
		final List<EObject> objects = new ArrayList<>();
		objects.add(namedElement);
		for (final TreeIterator<?> iterator = namedElement.eAllContents(); iterator.hasNext();) {
			final Object next = iterator.next();
			if (next instanceof final EObject object) {
				objects.add(object);
			}
		}
		return objects;
	}

	private static void checkCanceled(final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	public static String createHierarchicalName(final EObject object) {
		if (object == null) {
			return "NULL"; //$NON-NLS-1$
		}
		if (object instanceof final Connection connection) {
			return createHierarchicalName(connection.getSource()) + " -> " //$NON-NLS-1$
					+ createHierarchicalName(connection.getDestination());
		}
		if (object instanceof final FBNetwork network) {
			return createHierarchicalName(network.eContainer());
		}
		if (object instanceof ECC) {
			return "ECC"; //$NON-NLS-1$
		}
		if (object instanceof final ECState state) {
			return "ECC." + state.getName(); //$NON-NLS-1$
		}
		if (object instanceof final ECTransition transition) {
			return "ECC.Transition X:" + transition.getPosition().getX() + " Y:" //$NON-NLS-1$ //$NON-NLS-2$
					+ transition.getPosition().getY();
		}
		if (object instanceof final INamedElement namedElement) {
			return namedElement.getQualifiedName();
		}
		return object.toString();
	}

	private ValidationHelper() {
		throw new UnsupportedOperationException();
	}
}
