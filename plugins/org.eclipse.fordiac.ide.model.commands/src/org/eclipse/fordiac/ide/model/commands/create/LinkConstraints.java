/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, AIT fortiss GmbH
 * 				 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren, Monika Wenger, Matthias Plasch
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Code cleanup, fixed adapter connection creation issue
 *               - reworked and harmonized source/target checking 551042
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_ADAPTER;

import java.text.MessageFormat;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;

/** The Class LinkConstraints. */
public final class LinkConstraints {

	/** Can create data connection.
	 *
	 * @param source             the source
	 * @param target             the target
	 * @param parent             the fbnetwork the connection should be created in
	 * @param internalConnection the internal connection
	 *
	 * @return true, if a data connection can be created (the source and target type are compatible */
	public static boolean canCreateDataConnection(final IInterfaceElement source, final IInterfaceElement target,
			final FBNetwork parent) {
		return canExistDataConnection(source, target, parent, null);
	}

	/** Can exist data connection.
	 *
	 * @param source             the source
	 * @param target             the target
	 * @param parent             the fbnetwork the connection should be created in
	 * @param internalConnection the internal connection
	 * @param reConnect          is it a reconnection or not
	 *
	 * @return true, if successful */
	public static boolean canExistDataConnection(IInterfaceElement source, IInterfaceElement target,
			final FBNetwork parent,
			final Connection con) {

		if (!isDataPin(source) || !isDataPin(target)) {
			ErrorMessenger.popUpErrorMessage(Messages.ConnectingIncompatibleInterfaceTypes);
			return false;
		}

		if (isSwapNeeded(source, parent)) {
			final IInterfaceElement temp = source;
			source = target;
			target = temp;
		}

		if (!sourceAndDestCheck(source, target)) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
			return false;
		}

		if (!hasAlreadyInputConnectionsCheck(source, target, con)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection, target.getName()));
			return false;
		}

		if (!typeCheck(source, target)) {
			ErrorMessenger
			.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
					(null != source.getType()) ? source.getType().getName() : FordiacMessages.NA,
							(null != target.getType()) ? target.getType().getName() : FordiacMessages.NA));
			return false;
		}

		return isWithConstraintOK(source) && isWithConstraintOK(target);
	}

	/** Elements which are not linked by a with construct are not allowed to be connected
	 *
	 * @param varDecl
	 * @return */
	public static boolean isWithConstraintOK(final IInterfaceElement varDecl) {
		if ((!(varDecl instanceof ErrorMarkerInterface)) && (((VarDeclaration) varDecl).getWiths().isEmpty())) {
			// data in or outputs which are not connect by withs are not allowed to be connected
			EObject obj = varDecl.eContainer();
			if (null != obj) {
				obj = obj.eContainer();
				if ((obj instanceof CompositeFBType) || (obj instanceof SubApp)
						|| (obj instanceof ErrorMarkerFBNElement)) {
					// data connections from and to interface data ports from composits, subaps, error markers should
					// also be allowed from unwithed composite inputs (e.g., parameters for the FB)
					return true;
				}
			}

			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_ERROR_NotConnectedToAnEventByAWithConstruct, varDecl.getName()));
			return false;
		}
		return true;
	}

	private static boolean hasAlreadyOutputConnectionsCheck(final IInterfaceElement source, final Connection con) {
		for (final Connection connection : source.getOutputConnections()) {
			if (!connection.equals(con)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Type check.
	 *
	 * @param source the source
	 * @param target the target
	 *
	 * @return true, if successful
	 */
	public static boolean typeCheck(final IInterfaceElement source, final IInterfaceElement target) {
		// if source has generic type, it adapts to the target, which must fall into the generic type category
		if (GenericTypes.isAnyType(source.getType()) && source.getType().isAssignableFrom(target.getType())) {
			return true;
		}
		return target.getType().isAssignableFrom(source.getType());
	}

	/** Checks for already input connections check.
	 *
	 * @param target the target
	 * @param con
	 *
	 * @return true, if successful */
	private static boolean hasAlreadyInputConnectionsCheck(final IInterfaceElement src, final IInterfaceElement target,
			final Connection con) {
		boolean hasOnlyBrokenCons = true;

		if (!target.getInputConnections().isEmpty()) {
			final FBNetwork fbNetworkTarget = getContainingFBNetwork(target);
			final FBNetwork fbNetworkSrc = getContainingFBNetwork(src);
			if ((null != fbNetworkTarget) && (fbNetworkTarget != fbNetworkSrc)) {
				// Broken connection check must only be performed of src and target are in
				// different networks allowing to connect broken
				// connections in the same container would lead to broken fb networks, see
				// [issues:#82] for details
				for (final Connection connection : target.getInputConnections()) {
					if ((!connection.isBrokenConnection()) && !(connection.equals(con))) {
						hasOnlyBrokenCons = false;
						break;
					}
				}
			} else {
				for (final Connection connection : target.getInputConnections()) {
					if (!connection.equals(con)) {
						hasOnlyBrokenCons = false;
						break;
					}
				}
			}
		}
		return hasOnlyBrokenCons;
	}

	public static boolean isSwapNeeded(final IInterfaceElement source, final FBNetwork parent) {
		if (((source.eContainer() != null) && (source.eContainer().eContainer() instanceof CompositeFBType))
				|| ((source.getFBNetworkElement() != null)
						&& (source.getFBNetworkElement().getFbNetwork() != parent))) {
			// the FBNetwork elements are not in the same resource this means one of both is
			// a subapp interface element of the containing subapp
			return !source.isIsInput();
		}
		return source.isIsInput();
	}

	private static FBNetwork getContainingFBNetwork(final IInterfaceElement target) {
		FBNetwork retVal = null;
		EObject obj = target.eContainer();
		if (null != obj) {
			obj = obj.eContainer();
			if (null != obj) {
				obj = obj.eContainer();
				if (obj instanceof FBNetwork) {
					retVal = (FBNetwork) obj;
				}
			}
		}
		return retVal;
	}

	/** Source and dest check.
	 *
	 * @param source the source
	 * @param target the target
	 *
	 * @return true, if successful */
	private static boolean sourceAndDestCheck(final IInterfaceElement source, final IInterfaceElement target) {
		boolean canExist = (source.isIsInput() && !target.isIsInput()) || (!source.isIsInput() && target.isIsInput());

		if (!canExist) {
			if ((source.isIsInput() && target.isIsInput()) || (!source.isIsInput() && !target.isIsInput())) {
				final EObject sourceCont = source.eContainer().eContainer();
				final EObject destCont = target.eContainer().eContainer();
				// the connection can exist in this case if it is of an interface element of the
				// container (e.g., SubApp, CFB) and an internal FB
				canExist = (sourceCont != destCont) && (isTypeContainer(sourceCont) || isTypeContainer(destCont))
						&& (sourceCont.eContainer() != destCont.eContainer()); // and they are not on the same level
				// (e.g., two subapps in the same
				// subapplication/application
			}
		}
		return canExist;
	}

	private static boolean isTypeContainer(final EObject cont) {
		return (cont instanceof SubApp) || (cont instanceof SubAppType) || (cont instanceof CompositeFBType);
	}

	/** Can exist event connection.
	 *
	 * @param source             the source
	 * @param target             the target
	 * @param internalConnection the internal connection
	 *
	 * @return true, if successful */
	public static boolean canExistEventConnection(IInterfaceElement source, IInterfaceElement target,
			final FBNetwork parent) {
		if (!isEventPin(source) || !isEventPin(target)) {
			ErrorMessenger.popUpErrorMessage(Messages.ConnectingIncompatibleInterfaceTypes);
			return false;
		}

		if (isSwapNeeded(source, parent)) {
			final IInterfaceElement temp = source;
			source = target;
			target = temp;
		}

		if (duplicateConnection(source, target)) {
			return false;
		}
		return sourceAndDestCheck(source, target);
	}

	public static boolean duplicateConnection(final IInterfaceElement source, final IInterfaceElement destination) {
		for (final Connection con : source.getOutputConnections()) {
			if (con.getDestination() == destination) {
				return true;
			}
		}
		return false;
	}

	private static boolean isEventPin(final IInterfaceElement pin) {
		return (pin != null && pin.getType() instanceof EventType);
	}

	private static boolean isDataPin(final IInterfaceElement pin) {
		return (pin != null && !(pin.getType() instanceof EventType) && !(pin.getType() instanceof AdapterType));
	}

	private static boolean isAdapterPin(final IInterfaceElement pin) {
		return (pin != null && pin.getType() instanceof AdapterType);
	}

	public static boolean canExistAdapterConnection(IInterfaceElement source, IInterfaceElement target,
			final FBNetwork parent, final Connection con) {
		if (!isAdapterPin(source) || !isAdapterPin(target)) {
			ErrorMessenger.popUpErrorMessage(Messages.ConnectingIncompatibleInterfaceTypes);
			return false;
		}
		if (isSwapNeeded(source, parent)) {
			final IInterfaceElement temp = source;
			source = target;
			target = temp;
		}

		if (!sourceAndDestCheck(source, target)) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
			return false;
		}

		if (!hasAlreadyInputConnectionsCheck(source, target, con)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection, target.getName()));
			return false;
		}

		if (hasAlreadyOutputConnectionsCheck(source, con)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyOutputConnection, source.getName()));
			return false;
		}

		if (!adapaterTypeCompatibilityCheck(source, target)) {
			ErrorMessenger
			.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
					(null != source.getType()) ? source.getType().getName() : FordiacMessages.ND,
							(null != target.getType()) ? target.getType().getName() : FordiacMessages.ND));
			return false;
		}

		return true;
	}

	public static boolean canCreateAdapterConnection(final IInterfaceElement source, final IInterfaceElement target,
			final FBNetwork parent) {
		return canExistAdapterConnection(source, target, parent, null);
	}


	private static boolean adapaterTypeCompatibilityCheck(final IInterfaceElement source,
			final IInterfaceElement target) {
		if (((source.getType().getName().equals(ANY_ADAPTER)) && (!target.getType().getName().equals(ANY_ADAPTER)))
				|| ((!source.getType().getName().equals(ANY_ADAPTER))
						&& (target.getType().getName().equals(ANY_ADAPTER)))) {
			return true;
		}
		return source.getType().getName().equalsIgnoreCase(target.getType().getName());
	}

	private LinkConstraints() {
		throw new UnsupportedOperationException(Messages.LinkConstraints_ClassLinconstraintsShouldNotBeCreated);
	}

}
