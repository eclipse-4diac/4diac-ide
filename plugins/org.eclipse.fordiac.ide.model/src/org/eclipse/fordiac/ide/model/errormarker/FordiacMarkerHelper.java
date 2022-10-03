/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz, Primetals Technologies Austria GmbH
 *               2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - extension for connection error markers
 *   Michael Oberlehner
 *               - Refactoring of API
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.errormarker;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public final class FordiacMarkerHelper {

	private static final String FB_NETWORK_ELEMENT_TARGET = "FBNetworkElement"; //$NON-NLS-1$
	private static final String CONNECTION_TARGET = "Connection"; //$NON-NLS-1$
	private static final String VALUE_TARGET = "Value"; //$NON-NLS-1$

	public static boolean markerTargetsFBNetworkElement(final IMarker marker) {
		return FB_NETWORK_ELEMENT_TARGET.equals(marker.getAttribute(ErrorMarkerBuilder.TARGET_TYPE, null));
	}

	public static boolean markerTargetsConnection(final IMarker marker) {
		return CONNECTION_TARGET.equals(marker.getAttribute(ErrorMarkerBuilder.TARGET_TYPE, null));
	}

	public static boolean markerTargetsValue(final IMarker marker) {
		return VALUE_TARGET.equals(marker.getAttribute(ErrorMarkerBuilder.TARGET_TYPE, null));
	}

	public static String getTargetIdentifier(final EObject element) {
		if (element instanceof FBNetworkElement) {
			return FB_NETWORK_ELEMENT_TARGET;
		}
		if (element instanceof Connection) {
			return CONNECTION_TARGET;
		}
		if (element instanceof Value) {
			return VALUE_TARGET;
		}
		return null;
	}

	public static String getLocation(final EObject element) {
		if (element instanceof FBNetworkElement) {
			return getFBNElementLocation((FBNetworkElement) element);
		}
		if (element instanceof FBNetwork) {
			return getFBNetworkLocation((FBNetwork) element);
		}
		return null;
	}

	private static String getFBNElementLocation(final FBNetworkElement element) {
		final EObject container = element.eContainer().eContainer();
		if (container instanceof FBType) {
			return element.getName();
		}
		final StringBuilder builder = new StringBuilder(element.getName());
		createHierarchicalName(element.eContainer().eContainer(), builder);
		return builder.toString();
	}

	private static String getFBNetworkLocation(final FBNetwork element) {
		final EObject container = element.eContainer();
		if (container instanceof FBType) {
			return ""; //$NON-NLS-1$
		}
		final StringBuilder builder = new StringBuilder();
		createHierarchicalName(container, builder);
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1); // remove the last dot
		}

		return builder.toString();
	}

	private static void createHierarchicalName(final EObject container, final StringBuilder builder) {
		EObject runner = container;
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

		if (runner instanceof Resource) {
			builder.insert(0, '.');
			builder.insert(0, ((Resource) runner).getName());
			if (runner.eContainer() != null) {
				builder.insert(0, '.');
				builder.insert(0, ((Device) runner.eContainer()).getName());
			}
		}
	}

	public static ErrorMarkerFBNElement createErrorMarkerFB(final String name) {
		final ErrorMarkerFBNElement createErrorMarkerFBNElement = LibraryElementFactory.eINSTANCE
				.createErrorMarkerFBNElement();
		createErrorMarkerFBNElement.setName(name);
		createErrorMarkerFBNElement.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		final Position position = LibraryElementFactory.eINSTANCE.createPosition();
		position.setX(0);
		position.setY(0);
		createErrorMarkerFBNElement.setPosition(position);
		return createErrorMarkerFBNElement;
	}

	public static FBNetworkElement createTypeErrorMarkerFB(final String typeFbElement, final TypeLibrary typeLibrary,
			final FBType fbType) {
		final ErrorMarkerFBNElement errorFb = FordiacMarkerHelper.createErrorMarkerFB(typeFbElement);
		final TypeEntry entry = typeLibrary.createErrorTypeEntry(typeFbElement, fbType);
		errorFb.setTypeEntry(entry);
		return errorFb;
	}

	public static ErrorMarkerInterface createWrongDataTypeMarker(final IInterfaceElement oldInterface,
			final IInterfaceElement newInterface, final FBNetworkElement element,
			final List<ErrorMarkerBuilder> errorPins,final String errorMessage) {

		final ErrorMarkerInterface createErrorMarker = createErrorMarkerInterfaceElement(element, oldInterface,
				errorMessage, errorPins);
		createErrorMarker.setErrorMessage(errorMessage);
		return createErrorMarker;
	}

	public static ErrorMarkerInterface createErrorMarkerInterfaceElement(final FBNetworkElement newElement,
			final IInterfaceElement oldInterface, final String errorMessage, final List<ErrorMarkerBuilder> errorPins) {
		final boolean markerExists = newElement.getInterface().getErrorMarker().stream().anyMatch(
				e -> e.getName().equals(oldInterface.getName()) && (e.isIsInput() == oldInterface.isIsInput()));

		final ErrorMarkerInterface interfaceElement = ConnectionHelper.createErrorMarkerInterface(
				oldInterface.getType(), oldInterface.getName(), oldInterface.isIsInput(), newElement.getInterface());

		if (isVariable(oldInterface) && ((VarDeclaration) oldInterface).getValue() != null
				&& !((VarDeclaration) oldInterface).getValue().getValue().isBlank()) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			value.setValue(((VarDeclaration) oldInterface).getValue().getValue());
			interfaceElement.setValue(value);
		}

		if (!markerExists) {
			final ErrorMarkerBuilder createErrorMarker = ErrorMarkerBuilder.createErrorMarkerBuilder(errorMessage, newElement,
					0);
			createErrorMarker.setErrorMarkerRef(interfaceElement);
			createErrorMarker.createMarkerInFile();
			errorPins.add(createErrorMarker);
		}
		return interfaceElement;
	}

	public static boolean isVariable(final IInterfaceElement oldInterface) {
		return (oldInterface instanceof VarDeclaration) && !(oldInterface instanceof AdapterDeclaration);
	}

	private FordiacMarkerHelper() {
		throw new UnsupportedOperationException("FordiacMarkerHelper should not be instantiated"); //$NON-NLS-1$
	}

}
