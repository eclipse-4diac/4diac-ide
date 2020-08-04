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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
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
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.validation.Activator;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.expressions.Variable;
import org.eclipse.ui.handlers.HandlerUtil;

public class Validate extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		INamedElement selectedElement = getSelectedElement(
				(StructuredSelection) HandlerUtil.getCurrentSelection(event));
		List<Constraint> constraints = OCLParser.loadOCLConstraints(selectedElement);
		IResource iresource = getFile(selectedElement);
		try {
			iresource.deleteMarkers(IValidationMarker.TYPE, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e); // $NON-NLS-1$
		}
		for (Constraint constraint : constraints) {
			Variable<EClassifier, EParameter> context = constraint.getSpecification().getContextVariable();
			String contextName = (context.getType().getName());
			for (TreeIterator<?> iterator = selectedElement.eAllContents(); iterator.hasNext();) {
				EObject object = (EObject) iterator.next();
				String objectName = object.eClass().getName();
				if (contextName.equals(objectName))
					if (!Activator.getDefault().getOclInstance().check(object, constraint)) {
						try {
							String[] properties = ConstraintHelper.getConstraintProperties(constraint.getName());
							addValidationMarker(iresource, properties[0], properties[1],
									createHierarchicalName(object));
						} catch (CoreException e) {
							Activator.getDefault().logError(e.getMessage(), e);
						}
					}
			}
		}
		return null;
	}

	private static void addValidationMarker(IResource iresource, String message, String severity, String location)
			throws CoreException {
		IMarker imarker = iresource.createMarker(IValidationMarker.TYPE);
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
	}

	private static INamedElement getSelectedElement(StructuredSelection currentSelection) {
		Object obj = currentSelection.getFirstElement();
		if (obj instanceof IFile) {
			return checkSelectedFile((IFile) obj);
		}
		return (obj instanceof INamedElement) ? (INamedElement) obj : null;
	}

	private static INamedElement checkSelectedFile(IFile file) {
		PaletteEntry entry = TypeLibrary.getPaletteEntryForFile(file);
		if (entry instanceof FBTypePaletteEntry) {
			return ((FBTypePaletteEntry) entry).getFBType();
		}
		return null;
	}

	private static IResource getFile(INamedElement element) {
		if (element instanceof FBType) {
			return ((FBType) element).getPaletteEntry().getFile();
		}

		if (element instanceof Application || element instanceof SubApp) {
			return ((Application) element).getAutomationSystem().getSystemFile();
		}
		return null;
	}

	private static String createHierarchicalName(EObject object) {
		// We have to cover all possible context of the constraints
		if (object instanceof VarDeclaration) {
			VarDeclaration varDeclaration = (VarDeclaration) object;
			FBNetworkElement element = varDeclaration.getFBNetworkElement();
			EObject runner = element.getFbNetwork().eContainer();
			StringBuilder builder = new StringBuilder(getApplicationHierarchy(runner));
			builder.append(element.getName());
			builder.append('.');
			builder.append(varDeclaration.getName());
			return builder.toString();
		} else if (object instanceof FBNetworkElement) {
			FBNetworkElement element = (FBNetworkElement) object;
			EObject runner = element.getFbNetwork().eContainer();
			StringBuilder builder = new StringBuilder(getApplicationHierarchy(runner));
			builder.append(element.getName());
			return builder.toString();
		} else if (object instanceof Event) {
			Event event = (Event) object;
			FBNetworkElement element = event.getFBNetworkElement();
			EObject runner = element.getFbNetwork().eContainer();
			StringBuilder builder = new StringBuilder(getApplicationHierarchy(runner));
			builder.append(element.getName());
			builder.append('.');
			builder.append(event.getName());
			return builder.toString();
		} else if (object instanceof ECState) {
			ECState state = (ECState) object;
			FBType fbType = state.getECC().getBasicFBType();
			StringBuilder builder = new StringBuilder(fbType.getName());
			builder.append('.');
			builder.append("ECC");
			builder.append('.');
			builder.append(state.getName());
			return builder.toString();
		} else if (object instanceof ECC) {
			ECC ecc = (ECC) object;
			FBType fbType = ecc.getBasicFBType();
			StringBuilder builder = new StringBuilder(fbType.getName());
			builder.append('.');
			builder.append("ECC");
			return builder.toString();
		} else if (object instanceof ECTransition) {
			ECTransition transition = (ECTransition) object;
			FBType fbType = transition.getECC().getBasicFBType();
			StringBuilder builder = new StringBuilder(fbType.getName());
			builder.append('.');
			builder.append("ECC");
			builder.append('.');
			// TODO: ECTransition toString()? Display the coordinates of the transition?
			builder.append("Transition X:" + transition.getX() + " Y:" + transition.getY());
			return builder.toString();
		} else {
			return object.toString();
		}
	}

	private static String getApplicationHierarchy(EObject runner) {
		StringBuilder builder = new StringBuilder();
		while (runner instanceof SubApp) {
			SubApp parent = (SubApp) runner;
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
}