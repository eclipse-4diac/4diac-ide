/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.validation.Activator;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.expressions.Variable;

public final class ValidationHelper {

	public static void validate(final INamedElement namedElement) {
		final List<Constraint> constraints = OCLParser.loadOCLConstraints(namedElement);
		final IResource iresource = getFile(namedElement);
		try {
			iresource.deleteMarkers(IValidationMarker.TYPE, true, IResource.DEPTH_INFINITE);
		} catch (final CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e); // $NON-NLS-1$
		}
		for (final Constraint constraint : constraints) {
			final Variable<EClassifier, EParameter> context = constraint.getSpecification().getContextVariable();
			final String contextName = (context.getType().getName());
			for (final TreeIterator<?> iterator = namedElement.eAllContents(); iterator.hasNext();) {
				final EObject object = (EObject) iterator.next();
				final String objectName = object.eClass().getName();
				if (contextName.equals(objectName)) {
					if (!Activator.getDefault().getOclInstance().check(object, constraint)) {
						try {
							final String[] properties = ConstraintHelper.getConstraintProperties(constraint.getName());
							addValidationMarker(iresource, properties[0], properties[1], createHierarchicalName(object),
									object.hashCode());
						} catch (final CoreException e) {
							Activator.getDefault().logError(e.getMessage(), e);
						}
					}
				}
			}
		}
	}

	private static void addValidationMarker(final IResource iresource, final String message, final String severity, final String location,
			final int lineNumber) throws CoreException {
		final IMarker imarker = iresource.createMarker(IValidationMarker.TYPE);
		imarker.setAttribute(IMarker.MESSAGE, message);
		switch (severity) {
		case "ERROR":
			imarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			break;
		case "WARNING":
			imarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			break;
		case "INFO":
			imarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
			break;
		default:
			imarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
			break;
		}
		imarker.setAttribute(IMarker.LOCATION, location);
		imarker.setAttribute(IMarker.LINE_NUMBER, String.valueOf(lineNumber));
	}

	private static IResource getFile(final INamedElement element) {
		if (element instanceof FBType) {
			return ((FBType) element).getPaletteEntry().getFile();
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
			builder.append(element.getName());
			builder.append('.');
			builder.append(varDeclaration.getName());
			return builder.toString();
		} else if (object instanceof FBNetworkElement) {
			final FBNetworkElement element = (FBNetworkElement) object;
			final EObject runner = element.getFbNetwork().eContainer();
			final StringBuilder builder = new StringBuilder(getApplicationHierarchy(runner));
			builder.append(element.getName());
			return builder.toString();
		} else if (object instanceof Event) {
			final Event event = (Event) object;
			final FBNetworkElement element = event.getFBNetworkElement();
			final EObject runner = element.getFbNetwork().eContainer();
			final StringBuilder builder = new StringBuilder(getApplicationHierarchy(runner));
			builder.append(element.getName());
			builder.append('.');
			builder.append(event.getName());
			return builder.toString();
		} else if (object instanceof ECState) {
			final ECState state = (ECState) object;
			final StringBuilder builder = new StringBuilder("ECC");
			builder.append('.');
			builder.append(state.getName());
			return builder.toString();
		} else if (object instanceof ECC) {
			final StringBuilder builder = new StringBuilder("ECC");
			return builder.toString();
		} else if (object instanceof ECTransition) {
			final ECTransition transition = (ECTransition) object;
			final StringBuilder builder = new StringBuilder("ECC");
			builder.append('.');
			builder.append("Transition X:" + transition.getPosition().getX() + " Y:" + transition.getPosition().getY());
			return builder.toString();
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
		return builder.toString();
	}

	private ValidationHelper() {
		throw new UnsupportedOperationException();
	}
}
