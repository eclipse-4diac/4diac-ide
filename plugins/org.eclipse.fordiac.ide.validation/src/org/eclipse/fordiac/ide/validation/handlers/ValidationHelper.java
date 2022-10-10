/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 *               2021 Johannes Kepler University
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.validation.Activator;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.expressions.Variable;

public final class ValidationHelper {


	private static class OCLJob extends Job {
		private final INamedElement namedElement;

		public OCLJob(final String JobName, final INamedElement namedElement) {
			super(JobName);
			this.namedElement = namedElement;
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			final SubMonitor subMonitor = SubMonitor.convert(monitor);

			final int count = countObjects(subMonitor);

			final List<Constraint> constraints = OCLParser.loadOCLConstraints(namedElement);
			subMonitor.setWorkRemaining(count * constraints.size());

			if (subMonitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			final IResource iresource = getFile(namedElement);
			clearErrorMarkers(iresource);


			for (final TreeIterator<?> iterator = namedElement.eAllContents(); iterator.hasNext();) {
				final EObject object = (EObject) iterator.next();
				for (final Constraint constraint : constraints) {
					subMonitor.split(1);
					if (subMonitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					final String objectName = object.eClass().getName();

					final Variable<EClassifier, EParameter> context = constraint.getSpecification()
							.getContextVariable();
					final String contextName = (context.getType().getName());
					if (contextName.equals(objectName)) {
						subMonitor.setTaskName(
								MessageFormat.format("{0}: {1}", createHierarchicalName(object), constraint.getName()));
						if (!Activator.getDefault().getOclInstance().check(object, constraint)) {
							final ConstraintHelper properties = new ConstraintHelper(constraint.getName());
							addValidationMarker(iresource, properties.getMessage(), properties.getSeverity(),
									createHierarchicalName(object), object.hashCode(), object);
						}
					}
				}
			}
			return Status.OK_STATUS;
		}

		private static void clearErrorMarkers(final IResource iresource) {
			try {
				if (iresource != null) {
					iresource.deleteMarkers(IValidationMarker.TYPE, true, IResource.DEPTH_INFINITE);
				}
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e); // $NON-NLS-1$
			}
		}

		private int countObjects(final SubMonitor subMonitor) {
			int count = 0;
			for (final TreeIterator<?> iterator = namedElement.eAllContents(); iterator.hasNext(); iterator.next()) {
				if (subMonitor.isCanceled()) {
					return -1;
				}
				count++;
			}
			return count;
		}

		private static void addValidationMarker(final IResource iresource, final String message, final int severity,
				final String location, final int lineNumber, final EObject context) {
			if (iresource == null) {
				return;
			}

			final ErrorMarkerBuilder marker = new ErrorMarkerBuilder();
			marker.addMessage(message);
			switch (severity) {
			case IMarker.SEVERITY_WARNING:
				marker.setSeverityWarning();
				break;
			case IMarker.SEVERITY_INFO:
				marker.setSeverityInfo();
				break;
			default:
				break;
			}
			marker.addLocation(location);
			marker.addLineNumber(lineNumber);

			marker.setMarkerType(IValidationMarker.TYPE);

			marker.addTargetIdentifier(context);

			if (context instanceof Connection) {
				marker.setErrorMarkerRef((ErrorMarkerRef) context);
			} else if (context instanceof VarDeclaration) {
				marker.addTargetIdentifier(((VarDeclaration) context).getValue());
				marker.setErrorMarkerRef(((VarDeclaration) context).getValue());
			}

			marker.createMarkerInResource(iresource);
		}

		private static IResource getFile(final INamedElement element) {
			if (element instanceof FBType) {
				return ((FBType) element).getTypeEntry().getFile();
			}

			if (element instanceof Application || element instanceof SubApp) {
				return ((Application) element).getAutomationSystem().getSystemFile();
			}
			return null;
		}

		private static String createHierarchicalName(final EObject object) {
			// We have to cover all possible context of the constraints
			if (object instanceof VarDeclaration) {
				final VarDeclaration varDeclaration = (VarDeclaration) object;
				final FBNetworkElement element = varDeclaration.getFBNetworkElement();
				final EObject runner = element.getFbNetwork().eContainer();
				final StringBuilder builder = new StringBuilder(getApplicationHierarchy(runner));
				builder.append('.');
				builder.append(element.getName());
				builder.append('.');
				builder.append(varDeclaration.getName());
				return builder.toString();
			} else if (object instanceof Connection) {
				final StringBuilder builder = new StringBuilder(
						createHierarchicalName(((Connection) object).getSource()));
				builder.append(" -> "); //$NON-NLS-1$
				builder.append(createHierarchicalName(((Connection) object).getDestination()));
				return builder.toString();
			} else if (object instanceof FBNetwork) {
				final FBNetwork element = (FBNetwork) object;
				final EObject runner = element.eContainer();
				final StringBuilder builder = new StringBuilder(getApplicationHierarchy(runner));
				return builder.toString();
			} else if (object instanceof FBNetworkElement) {
				final FBNetworkElement element = (FBNetworkElement) object;
				final EObject runner = element.getFbNetwork().eContainer();
				final StringBuilder builder = new StringBuilder(getApplicationHierarchy(runner));
				builder.append('.');
				builder.append(element.getName());
				return builder.toString();
			} else if (object instanceof Event) {
				final Event event = (Event) object;
				final FBNetworkElement element = event.getFBNetworkElement();
				final EObject runner = element.getFbNetwork().eContainer();
				final StringBuilder builder = new StringBuilder(getApplicationHierarchy(runner));
				builder.append('.');
				builder.append(element.getName());
				builder.append('.');
				builder.append(event.getName());
				return builder.toString();
			} else if (object instanceof ECState) {
				final ECState state = (ECState) object;
				final StringBuilder builder = new StringBuilder("ECC"); //$NON-NLS-1$
				builder.append('.');
				builder.append(state.getName());
				return builder.toString();
			} else if (object instanceof ECC) {
				final StringBuilder builder = new StringBuilder("ECC"); //$NON-NLS-1$
				return builder.toString();
			} else if (object instanceof ECTransition) {
				final ECTransition transition = (ECTransition) object;
				final StringBuilder builder = new StringBuilder("ECC"); //$NON-NLS-1$
				builder.append('.');
				builder.append(
						"Transition X:" + transition.getPosition().getX() + " Y:" + transition.getPosition().getY()); //$NON-NLS-1$ //$NON-NLS-2$
				return builder.toString();
			} else if (object == null) {
				return "NULL"; //$NON-NLS-1$
			} else {
				return object.toString();
			}
		}

		private static String getApplicationHierarchy(EObject runner) {
			final StringBuilder builder = new StringBuilder();
			while (runner instanceof SubApp) {
				final SubApp parent = (SubApp) runner;
				builder.insert(0, '.');
				builder.insert(0, parent.getName());
				runner = parent.getFbNetwork().eContainer();
			}
			if (runner instanceof Application) {
				builder.insert(0, '.');
				builder.insert(0, ((Application) runner).getName());
			}
			int lastIndex = builder.length();
			lastIndex = lastIndex == 0 ? 0 : lastIndex - 1;
			if (builder.length() > 0 && builder.charAt(lastIndex) == '.') {
				builder.deleteCharAt(lastIndex);
			}
			return builder.toString();
		}

	}

	public static void validate(final INamedElement namedElement) {
		final Job job = new OCLJob("OCL validation", namedElement);
		job.schedule();
	}

	private ValidationHelper() {
		throw new UnsupportedOperationException();
	}
}
