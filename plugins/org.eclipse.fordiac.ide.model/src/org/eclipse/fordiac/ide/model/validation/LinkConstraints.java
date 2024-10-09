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
package org.eclipse.fordiac.ide.model.validation;

import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_ADAPTER;
import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.getArraySize;

import java.text.MessageFormat;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
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

	/**
	 * Can create data connection.
	 *
	 * @param source             the source
	 * @param target             the target
	 * @param parent             the fbnetwork the connection should be created in
	 * @param internalConnection the internal connection
	 *
	 * @return true, if a data connection can be created (the source and target type
	 *         are compatible
	 */
	public static boolean canCreateDataConnection(final IInterfaceElement source, final IInterfaceElement target,
			final FBNetwork parent) {
		return canExistDataConnection(source, target, parent, null);
	}

	/**
	 * Can exist data connection.
	 *
	 * @param source             the source
	 * @param target             the target
	 * @param parent             the fbnetwork the connection should be created in
	 * @param internalConnection the internal connection
	 * @param reConnect          is it a reconnection or not
	 *
	 * @return true, if successful
	 */
	public static boolean canExistDataConnection(IInterfaceElement source, IInterfaceElement target,
			final FBNetwork parent, final Connection con) {

		if (!isDataPin(source) || !isDataPin(target)) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_ConnectingIncompatibleInterfaceTypes);
			return false;
		}

		// from here on we can assume we have only data pins

		if (isSwapNeeded(source, parent)) {
			final IInterfaceElement temp = source;
			source = target;
			target = temp;
		}

		if (!sourceAndDestCheck(source, target, parent)) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
			return false;
		}

		if (target instanceof final VarDeclaration targetData && targetData.isInOutVar()
				&& source instanceof final VarDeclaration sourceData && !sourceData.isInOutVar()) {
			// a non inout is connected to an inout
			ErrorMessenger.popUpErrorMessage(Messages.ConnectionValidator_OutputsCannotBeConnectedToVarInOuts);
			return false;
		}

		if (!hasAlreadyInputConnectionsCheck(source, target, con)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection, target.getName()));
			return false;
		}

		if (!typeCheck(source, target)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
					(null != source.getFullTypeName()) ? source.getFullTypeName() : FordiacMessages.NA,
					(null != target.getFullTypeName()) ? target.getFullTypeName() : FordiacMessages.NA));
			return false;

		}

		return isWithConstraintOK(source) && isWithConstraintOK(target);
	}

	/**
	 * Elements which are not linked by a with construct are not allowed to be
	 * connected
	 *
	 * @param varDecl
	 * @return
	 */
	public static boolean isWithConstraintOK(final IInterfaceElement varDecl) {
		if ((!(varDecl instanceof ErrorMarkerInterface)) && (((VarDeclaration) varDecl).getWiths().isEmpty())) {
			// data in or outputs which are not connect by withs are not allowed to be
			// connected
			if (null != varDecl.eContainer()) {
				final var obj = varDecl.eContainer().eContainer();
				if ((obj instanceof CompositeFBType) || (obj instanceof SubApp)
						|| (obj instanceof ErrorMarkerFBNElement)) {
					// data connections from and to interface data ports from composits, subaps,
					// error markers should
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

	public static boolean hasAlreadyOutputConnectionsCheck(final IInterfaceElement source, final Connection con) {
		return source.getOutputConnections().stream().anyMatch(connection -> !connection.equals(con));
	}

	/**
	 * Type check.
	 *
	 * @param source the source
	 * @param target the target
	 *
	 * @return true, if target is assignable from source
	 */
	public static boolean typeCheck(final IInterfaceElement source, final IInterfaceElement target) {
		final DataType sourceType = getFullDataType(source);
		final DataType targetType = getFullDataType(target);
		// check for InOut sources
		if (source instanceof final VarDeclaration sourceVar && sourceVar.isInOutVar()) {
			// relaxed checking if types could be compatible at all
			return targetType.isAssignableFrom(sourceType) || sourceType.isAssignableFrom(targetType);
		}
		// check for adapter connections
		if (source instanceof final AdapterDeclaration sourceAdapter
				&& target instanceof final AdapterDeclaration targetAdapter) {
			return adapaterTypeCompatibilityCheck(sourceAdapter.getType(), targetAdapter.getType());
		}
		// if source has generic type, it adapts to the target, which must fall into the
		// generic type category
		if (GenericTypes.isAnyType(sourceType) && sourceType.isAssignableFrom(targetType)) {
			return true;
		}
		return targetType.isAssignableFrom(sourceType);
	}

	public static DataType getFullDataType(final IInterfaceElement element) {
		if (element instanceof final VarDeclaration varDeclaration && varDeclaration.isArray()) {
			return TypeDeclarationParser.parseTypeDeclaration(varDeclaration.getType(), getArraySize(varDeclaration));
		}
		return element.getType();
	}

	/**
	 * Checks for already input connections check.
	 *
	 * @param target the target
	 * @param con
	 *
	 * @return true, if successful
	 */
	public static boolean hasAlreadyInputConnectionsCheck(final IInterfaceElement src, final IInterfaceElement target,
			final Connection con) {
		if (!target.getInputConnections().isEmpty()) {
			final FBNetwork fbNetworkTarget = getContainingFBNetwork(target);
			final FBNetwork fbNetworkSrc = getContainingFBNetwork(src);
			if ((null != fbNetworkTarget) && (fbNetworkTarget != fbNetworkSrc)) {
				// Broken connection check must only be performed if src and target are in
				// different networks allowing to connect broken connections in the same
				// container would lead to broken fb networks, see [issues:#82] for details
				return target.getInputConnections().stream()
						.noneMatch(connection -> (!connection.isBrokenConnection()) && !(connection.equals(con)));
			}
			return target.getInputConnections().stream().noneMatch(connection -> (!connection.equals(con)));
		}
		return true;
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
		if ((null != target.eContainer() && target.eContainer().eContainer() != null)
				&& (target.eContainer().eContainer().eContainer() instanceof final FBNetwork fbNetwork)) {
			return fbNetwork;
		}
		return null;
	}

	/**
	 * Source and dest check.
	 *
	 * @param source the source
	 * @param target the target
	 *
	 * @return true, if successful
	 */
	public static boolean sourceAndDestCheck(final IInterfaceElement source, final IInterfaceElement target,
			final FBNetwork parent) {
		return isValidConnSource(source, parent) && isValidConnDestination(target, parent);
	}

	public static boolean isValidConnSource(final IInterfaceElement source, final FBNetwork parent) {
		final EObject sourceCont = source.eContainer().eContainer();
		if (!source.isIsInput()) {
			// an output can only be a connection source if its container is part of the
			// parent
			return sourceCont != null && parent == sourceCont.eContainer();
		}
		// an input can only be a connection source if its a type input or the parent is
		// the inside of the subapp
		return isTypeContainer(sourceCont) || isInsideSubapp(sourceCont, parent);
	}

	public static boolean isValidConnDestination(final IInterfaceElement dst, final FBNetwork parent) {
		final EObject dstCont = dst.eContainer().eContainer();
		if (dst.isIsInput()) {
			// an input can only be a connection source if its container is part of the
			// parent
			return dstCont != null && parent == dstCont.eContainer();
		}
		// an output can only be a connection source if its a type input or the parent
		// is the inside of the subapp
		return isTypeContainer(dstCont) || isInsideSubapp(dstCont, parent);
	}

	private static boolean isTypeContainer(final EObject cont) {
		return (cont instanceof SubAppType) || (cont instanceof CompositeFBType);
	}

	private static boolean isInsideSubapp(final EObject cont, final FBNetwork parent) {
		return (cont instanceof final SubApp subApp) && subApp.getSubAppNetwork() == parent;
	}

	/**
	 * Can exist event connection.
	 *
	 * @param source             the source
	 * @param target             the target
	 * @param internalConnection the internal connection
	 *
	 * @return true, if successful
	 */
	public static boolean canExistEventConnection(IInterfaceElement source, IInterfaceElement target,
			final FBNetwork parent) {
		if (!isEventPin(source) || !isEventPin(target)) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_ConnectingIncompatibleInterfaceTypes);
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
		return sourceAndDestCheck(source, target, parent);
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

	public static boolean isDataPin(final IInterfaceElement pin) {
		return (pin != null && !(pin.getType() instanceof EventType) && !(pin.getType() instanceof AdapterType));
	}

	private static boolean isAdapterPin(final IInterfaceElement pin) {
		return (pin != null && pin.getType() instanceof AdapterType);
	}

	public static boolean canExistAdapterConnection(IInterfaceElement source, IInterfaceElement target,
			final FBNetwork parent, final Connection con) {
		if (!isAdapterPin(source) || !isAdapterPin(target)) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_ConnectingIncompatibleInterfaceTypes);
			return false;
		}
		if (isSwapNeeded(source, parent)) {
			final IInterfaceElement temp = source;
			source = target;
			target = temp;
		}

		if (!sourceAndDestCheck(source, target, parent)) {
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
			ErrorMessenger.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
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

	public static boolean adapaterTypeCompatibilityCheck(final IInterfaceElement source,
			final IInterfaceElement target) {
		if (source instanceof final AdapterDeclaration sourceAdapter
				&& target instanceof final AdapterDeclaration targetAdapter) {
			return adapaterTypeCompatibilityCheck(sourceAdapter.getType(), targetAdapter.getType());
		}
		return false;
	}

	public static boolean adapaterTypeCompatibilityCheck(final AdapterType source, final AdapterType target) {
		return (source != null && source.getName().equalsIgnoreCase(target.getName()))
				|| (isAnyAdapter(source) ^ isAnyAdapter(target));
	}

	public static boolean isAnyAdapter(final AdapterType type) {
		return type != null && ANY_ADAPTER.equals(type.getName());
	}

	private LinkConstraints() {
		throw new UnsupportedOperationException(Messages.LinkConstraints_ClassLinkconstraintsShouldNotBeCreated);
	}

}
