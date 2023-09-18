/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.emf.compare.MatchEngine;

import java.util.function.Function;

import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.eobject.IdentifierEObjectMatcher.DefaultIDFunction;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

public class FordiacMatchEngine extends DefaultMatchEngine {

	public FordiacMatchEngine() {
		super(createCustomMatcher(), new DefaultComparisonFactory(new DefaultEqualityHelperFactory()));
	}

	public static IEObjectMatcher createCustomMatcher() {
		final IEObjectMatcher fallBackMatcher = DefaultMatchEngine
				.createDefaultEObjectMatcher(UseIdentifiers.WHEN_AVAILABLE);
		return new FordiacIdentifierEObjectMatcher(fallBackMatcher, idFunction);

	}

	static Function<EObject, String> idFunction = input -> {
		if (input instanceof FBType || input instanceof AutomationSystem || input instanceof StructuredType
				|| input instanceof Application) {
			return input.getClass().getName();
		}

		if (input instanceof final Connection con) {

			if (con instanceof final EventConnection evntCon) {
				return evntCon.getSourceElement().getName() + "." + evntCon.getSource().getName() + "_" //$NON-NLS-1$ //$NON-NLS-2$
						+ evntCon.getDestinationElement().getName() + "." + evntCon.getDestination().getName(); //$NON-NLS-1$
			}
			if (con instanceof final DataConnection dataCon) {
				return dataCon.getSourceElement().getName() + "." + dataCon.getSource().getName() + "_" //$NON-NLS-1$ //$NON-NLS-2$
						+ dataCon.getDestinationElement().getName() + "." + dataCon.getDestination().getName(); //$NON-NLS-1$
			}
			if (con instanceof final AdapterConnection adapterCon) {
				return adapterCon.getSourceElement().getName() + "." + adapterCon.getSource().getName() + "_" //$NON-NLS-1$ //$NON-NLS-2$
						+ adapterCon.getDestinationElement().getName() + "." + adapterCon.getDestination().getName(); //$NON-NLS-1$
			}
		}

		if (input instanceof final Value val) {
			if (val.getParentIE().getFBNetworkElement() != null) {
				return val.getParentIE().getFBNetworkElement().getName() + "." + val.getParentIE().getName() //$NON-NLS-1$
						+ ".Value";  //$NON-NLS-1$
			}
			return val.getParentIE().getName() + ".Value"; //$NON-NLS-1$
		}

		if ((input instanceof final With with) && (with.eContainer() instanceof final IInterfaceElement ie)) {
			if (ie.getFBNetworkElement() != null) {
				return ie.getFBNetworkElement().getName() + "." + ie.getName() + ".WITH." //$NON-NLS-1$ //$NON-NLS-2$
						+ with.getVariables().getName();// + "#" + with.hashCode();
			}
			return ie.getName() + ".WITH." + with.getVariables().getName(); //$NON-NLS-1$
		}

		if ((input instanceof Event || input instanceof VarDeclaration || input instanceof AdapterDeclaration)
				&& (((IInterfaceElement) input).getFBNetworkElement() != null)) {
			// return ((IInterfaceElement) input).getFBNetworkElement().getName() + "."
			// + ((IInterfaceElement) input).getName();
			return ((IInterfaceElement) input).getFBNetworkElement().getName() + ".InterfaceList."
					+ ((IInterfaceElement) input).getName();
		}

		if (input instanceof final InterfaceList interfaceList) {
			if (interfaceList.getFBNetworkElement() != null) {
				return interfaceList.getFBNetworkElement().getName() + ".InterfaceList";
			}
			if (interfaceList.eContainer() instanceof final FBType fb) {
				return fb.getName() + ".InterfaceList";
			}
		}

		return new DefaultIDFunction().apply(input);
	};

}
