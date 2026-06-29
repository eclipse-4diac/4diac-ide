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
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.ModelHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.validation.Activator;
import org.eclipse.fordiac.ide.validation.Messages;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.expressions.Variable;

public final class ValidationHelper {

	public static void validate(final INamedElement namedElement) {
		final Job job = new OCLJob(Messages.ValidationHelper_OCLJobName, namedElement);
		job.setUser(true);
		job.setPriority(Job.BUILD);
		job.setRule(getFile(namedElement));
		job.schedule();
	}

	public static void validateSync(final INamedElement namedElement, final IProgressMonitor monitor) {
		final List<Constraint> constraints = OCLParser.loadOCLConstraints(namedElement);
		validateSync(namedElement, constraints, monitor);
	}

	public static void validateSync(final INamedElement namedElement, final List<Constraint> constraints,
			final IProgressMonitor monitor) {
		if (namedElement == null) {
			return;
		}
		final IResource resource = getFile(namedElement);
		clearOclMarkers(resource);
		final List<ErrorMarkerBuilder> markerBuilders = createValidationMarkers(resource, namedElement, constraints,
				monitor);
		FordiacMarkerHelper.updateMarkers(resource, IValidationMarker.TYPE, markerBuilders, true);
	}

	public static List<ErrorMarkerBuilder> createValidationMarkers(final IResource resource,
			final INamedElement namedElement, final List<Constraint> constraints, final IProgressMonitor monitor) {
		final List<EObject> objects = collectObjects(namedElement);
		final SubMonitor progress = SubMonitor.convert(monitor, Math.max(1, objects.size() * constraints.size()));
		final List<ErrorMarkerBuilder> markerBuilders = new ArrayList<>();
		for (final EObject object : objects) {
			for (final Constraint constraint : constraints) {
				progress.split(1);
				if (progress.isCanceled()) {
					return markerBuilders;
				}
				if (matchesContext(object, constraint)) {
					progress.setTaskName(MessageFormat.format(Messages.ValidationHelper_SubtaskFormat,
							createHierarchicalName(object), constraint.getName()));
					if (!Activator.getDefault().getOclInstance().check(object, constraint)) {
						final ConstraintHelper properties = new ConstraintHelper(constraint.getName());
						markerBuilders.add(ErrorMarkerBuilder.createErrorMarkerBuilder(properties.getMessage())
								.setType(IValidationMarker.TYPE).setSeverity(properties.getSeverity())
								.setLocation(createHierarchicalName(object)).setTarget(object));
					}
				}
			}
		}
		return markerBuilders;
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

	private static boolean matchesContext(final EObject object, final Constraint constraint) {
		final Variable<EClassifier, EParameter> context = constraint.getSpecification().getContextVariable();
		return context != null && context.getType() instanceof final EClass contextClass
				&& contextClass.isSuperTypeOf(object.eClass());
	}

	private static class OCLJob extends Job {
		private final INamedElement namedElement;

		public OCLJob(final String JobName, final INamedElement namedElement) {
			super(JobName);
			this.namedElement = namedElement;
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			validateSync(namedElement, monitor);
			return Status.OK_STATUS;
		}
	}

	public static void clearOclMarkers(final IResource resource) {
		try {
			if (resource != null) {
				resource.deleteMarkers(IValidationMarker.TYPE, true, IResource.DEPTH_INFINITE);
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
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
			return "ECC.Transition X:" + transition.getPosition().getX() + " Y:" + transition.getPosition().getY(); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (object instanceof final INamedElement namedElement) {
			return namedElement.getQualifiedName();
		}
		return object.toString();
	}

	public static IResource getFile(final INamedElement element) {
		if (element instanceof final FBType fbtype) {
			return fbtype.getTypeEntry().getFile();
		}
		return ModelHelper.getFileFromContext(element);
	}

	private ValidationHelper() {
		throw new UnsupportedOperationException();
	}
}
