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

import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_ADAPTER;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_BIT;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_DATE;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_ELEMENTARY;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_INT;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_MAGNITUDE;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_NUM;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_REAL;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.ANY_STRING;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.BOOL;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.BYTE;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.DATE;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.DATE_AND_TIME;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.DINT;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.DWORD;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.INT;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.LINT;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.LREAL;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.LWORD;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.REAL;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.SINT;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.STRING;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.TIME;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.TIME_OF_DAY;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.UDINT;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.UINT;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.ULINT;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.USINT;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.WORD;
import static org.eclipse.fordiac.ide.model.FordiacKeywords.WSTRING;

import java.text.MessageFormat;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.ErrorMessenger;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.ModelCommandsPlugin;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;

/**
 * The Class LinkConstraints.
 */
public final class LinkConstraints {

	/**
	 * Property ID for the enable CASTS preferences
	 *
	 * This constant is duplicated from the application plugin to avoid dependency
	 * cycles.
	 */
	public static final String PREFERENCE_ENABLE_CASTS = "enableCastPreference"; //$NON-NLS-1$

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
	public static boolean canCreateDataConnection(final VarDeclaration source, final VarDeclaration target,
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
	public static boolean canExistDataConnection(VarDeclaration source, VarDeclaration target, FBNetwork parent,
			Connection con) {
		if (source != null && source != target) {
			if (((source instanceof AdapterDeclaration) && (!(target instanceof AdapterDeclaration)))
					|| (!(source instanceof AdapterDeclaration) && (target instanceof AdapterDeclaration))) {
				// don't check if we try to create a connection from data to adapter or vice
				// versa
				// TODO model refactoring - remove this when AdapterDeclaration is not inherited
				// from VarDeclaration any more
				return false;
			}

			if (isSwapNeeded(source, parent)) {
				VarDeclaration temp = source;
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
		return false;
	}

	/**
	 * Elements which are not linked by a with construct are not allowed to be
	 * connected
	 *
	 * @param varDecl
	 * @return
	 */
	public static boolean isWithConstraintOK(final VarDeclaration varDecl) {
		if ((!(varDecl instanceof AdapterDeclaration)) && (varDecl.getWiths().isEmpty())) { // elements which are not
																							// connect by withs are not
																							// allowed to be connected
			EObject obj = varDecl.eContainer();
			if (null != obj) {
				obj = obj.eContainer();
				if ((obj instanceof CompositeFBType) || (obj instanceof SubApp)) {
					// data connections from and to interface data ports from composits should also
					// be allowed from unwithed composite inputs (e.g., parameters for the FB)
					return true;
				}
			}

			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_ERROR_NotConnectedToAnEventByAWithConstruct, varDecl.getName()));
			return false;
		}
		return true;
	}

	private static boolean hasAlreadyOutputConnectionsCheck(VarDeclaration source, Connection con) {
		for (Connection connection : source.getOutputConnections()) {
			if (!connection.equals(con)) {
				return true;
			}
		}

		return false;
	}

	/** The compatibility. */
	private static boolean[][] compatibility = {
			{ true, false, false, false, false, false, false, false, false, true, false, false, false, false, false,
					false, false, false, false, false, true, false, false, true },
			{ false, true, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, false, false, false, true, false, false, false, true, false, false, false, false, false,
					false, false, false, false, false, true, false, false, true },
			{ false, false, false, false, false, false, true, false, false, true, false, false, false, false, false,
					false, false, false, false, false, true, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, false, false, false, false, false, false, true, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, true },
			{ false, false, false, false, false, false, false, false, false, true, false, false, false, false, false,
					false, false, false, false, false, true, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, false, false, false, false, false, false, false, true, false, false, false, true, false,
					false, false, false, false, false, true, false, false, true },
			{ false, false, false, false, false, false, false, false, false, true, false, false, false, false, true,
					false, false, false, false, false, true, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, false, false, false, false, false, false, false, true, false, false, false, false, false,
					false, true, false, false, false, true, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, false, false, false, false, false, false, false, true, false, false, false, false, false,
					false, false, false, false, true, true, false, false, true },
			{ false, false, false, false, false, false, false, false, false, true, false, false, false, false, false,
					false, false, false, false, false, true, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true, true, true, true, false, false, true,
					false, true, true, false, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, false } };

	/**
	 * Type check.
	 *
	 * @param source the source
	 * @param target the target
	 *
	 * @return true, if successful
	 */
	public static boolean typeCheck(final VarDeclaration source, final VarDeclaration target) {

		boolean automaticCasts = ModelCommandsPlugin.getDefault().getPreferenceStore()
				.getBoolean(PREFERENCE_ENABLE_CASTS);

		if (automaticCasts) {
			BaseType1 sourceType = BaseType1.getByName(source.getTypeName().toUpperCase());
			BaseType1 destType = BaseType1.getByName(target.getTypeName().toUpperCase());

			if (sourceType != null && destType != null) {
				return compatibility[sourceType.getValue()][destType.getValue()];
			}
			return false;
		}

		return defaultTypeCompatibilityCheck(source, target);
	}

	/**
	 * Checks for already input connections check.
	 *
	 * @param target the target
	 * @param con
	 *
	 * @return true, if successful
	 */
	private static boolean hasAlreadyInputConnectionsCheck(final VarDeclaration src, final VarDeclaration target,
			final Connection con) {
		boolean hasOnlyBrokenCons = true;

		if (!target.getInputConnections().isEmpty()) {
			FBNetwork fbNetworkTarget = getContainingFBNetwork(target);
			FBNetwork fbNetworkSrc = getContainingFBNetwork(src);
			if ((null != fbNetworkTarget) && (fbNetworkTarget != fbNetworkSrc)) {
				// Broken connection check must only be performed of src and target are in
				// different networks allowing to connect broken
				// connections in the same container would lead to broken fb networks, see
				// [issues:#82] for details
				for (Connection connection : target.getInputConnections()) {
					if ((!connection.isBrokenConnection()) && !(connection.equals(con))) {
						hasOnlyBrokenCons = false;
						break;
					}
				}
			} else {
				for (Connection connection : target.getInputConnections()) {
					if (!connection.equals(con)) {
						hasOnlyBrokenCons = false;
						break;
					}
				}
			}
		}
		return hasOnlyBrokenCons;
	}

	public static boolean isSwapNeeded(IInterfaceElement source, FBNetwork parent) {
		if ((source.eContainer().eContainer() instanceof CompositeFBType)
				|| (source.getFBNetworkElement().getFbNetwork() != parent)) {
			// the FBNetwork elements are not in the same resource this means one of both is
			// a subapp interface element of the containing subapp
			return !source.isIsInput();
		}
		return source.isIsInput();
	}

	private static FBNetwork getContainingFBNetwork(VarDeclaration target) {
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

	/**
	 * Source and dest check.
	 *
	 * @param source the source
	 * @param target the target
	 *
	 * @return true, if successful
	 */
	private static boolean sourceAndDestCheck(final IInterfaceElement source, final IInterfaceElement target) {
		boolean canExist = (source.isIsInput() && !target.isIsInput()) || (!source.isIsInput() && target.isIsInput());

		if (!canExist) {
			if ((source.isIsInput() && target.isIsInput()) || (!source.isIsInput() && !target.isIsInput())) {
				EObject sourceCont = source.eContainer().eContainer();
				EObject destCont = target.eContainer().eContainer();
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

	private static boolean isTypeContainer(EObject cont) {
		return (cont instanceof SubApp) || (cont instanceof SubAppType) || (cont instanceof CompositeFBType);
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
	public static boolean canExistEventConnection(final Event source, final Event target) {
		if (source != null && target != null) {
			return sourceAndDestCheck(source, target);
		}
		return false;
	}

	public static boolean canExistAdapterConnection(AdapterDeclaration source, AdapterDeclaration target,
			FBNetwork parent, Connection con) {
		if (source != null && target != null && source != target) {
			if (isSwapNeeded(source, parent)) {
				AdapterDeclaration temp = source;
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
		return false;
	}

	public static boolean canCreateAdapterConnection(AdapterDeclaration source, AdapterDeclaration target,
			FBNetwork parent) {
		return canExistAdapterConnection(source, target, parent, null);
	}

	/**
	 * Default type compatibility check.
	 *
	 * @param source the source
	 * @param target the target
	 *
	 * @return true, if default type compatibility check
	 */
	private static boolean defaultTypeCompatibilityCheck(final VarDeclaration source, final VarDeclaration target) {
		try {
			if (source.getType().getName().toUpperCase().startsWith(ANY)
					&& target.getType().getName().toUpperCase().startsWith(ANY)) {
				return checkAnyAnyCompatibility();
			}

			if (source.getType().getName().equalsIgnoreCase(ANY) && !target.getType().getName().equalsIgnoreCase(ANY)) {
				return true;
			}
			if (!source.getType().getName().equalsIgnoreCase(ANY) && target.getType().getName().equalsIgnoreCase(ANY)) {
				return true;
			}

			if (source.getType().getName().equalsIgnoreCase(ANY) && target.getType().getName().equalsIgnoreCase(ANY)) {
				return checkAnyAnyCompatibility();
			}

			if (source.getType().getName().equalsIgnoreCase(ANY_ELEMENTARY)
					&& !target.getType().getName().equalsIgnoreCase(ANY_ELEMENTARY)) {
				return true;
			}
			if (!source.getType().getName().equalsIgnoreCase(ANY_ELEMENTARY)
					&& target.getType().getName().equalsIgnoreCase(ANY_ELEMENTARY)) {
				return true;
			}

			if (source.getType().getName().equalsIgnoreCase(ANY_ELEMENTARY)
					&& target.getType().getName().equalsIgnoreCase(ANY_ELEMENTARY)) {
				return checkAnyAnyCompatibility();
			}

			if (source.getType().getName().equalsIgnoreCase(ANY_MAGNITUDE)) {
				return anyMagnitudeCompatibility(target.getType().getName());
			}

			if (target.getType().getName().equalsIgnoreCase(ANY_MAGNITUDE)) {
				return anyMagnitudeCompatibility(source.getType().getName());
			}

			if (source.getType().getName().equalsIgnoreCase(ANY_NUM)) {
				return anyNumCompatibility(target.getType().getName());
			}

			if (target.getType().getName().equalsIgnoreCase(ANY_NUM)) {
				return anyNumCompatibility(source.getType().getName());
			}

			if (source.getType().getName().equalsIgnoreCase(ANY_INT)) {
				return anyIntCompatiblity(target.getType().getName());
			}

			if (target.getType().getName().equalsIgnoreCase(ANY_INT)) {
				return anyIntCompatiblity(source.getType().getName());
			}

			if (source.getType().getName().equalsIgnoreCase(ANY_REAL)) {
				return anyRealCompatibility(target.getType().getName());
			}

			if (target.getType().getName().equalsIgnoreCase(ANY_REAL)) {
				return anyRealCompatibility(source.getType().getName());
			}

			if (source.getType().getName().equalsIgnoreCase(ANY_BIT)) {
				return anyBitCompatibility(target.getType().getName());
			}

			if (target.getType().getName().equalsIgnoreCase(ANY_BIT)) {
				return anyBitCompatibility(source.getType().getName());
			}

			if ((source.getType().getName().equalsIgnoreCase(ANY_STRING))
					&& (target.getType().getName().equalsIgnoreCase(STRING)
							|| target.getType().getName().equalsIgnoreCase(WSTRING))) {
				return true;
			}
			if ((target.getType().getName().equalsIgnoreCase(ANY_STRING))
					&& (source.getType().getName().equalsIgnoreCase(STRING)
							|| source.getType().getName().equalsIgnoreCase(WSTRING))) {
				return true;
			}
			if (source.getType().getName().equalsIgnoreCase(ANY_STRING)
					&& target.getType().getName().equalsIgnoreCase(ANY_STRING)) {
				return checkAnyAnyCompatibility();
			}

			if ((source.getType().getName().equalsIgnoreCase(ANY_DATE))
					&& (target.getType().getName().equalsIgnoreCase(DATE)
							|| target.getType().getName().equalsIgnoreCase(DATE_AND_TIME)
							|| target.getType().getName().equalsIgnoreCase(TIME_OF_DAY))) {
				return true;
			}
			if ((target.getType().getName().equalsIgnoreCase(ANY_DATE))
					&& (source.getType().getName().equalsIgnoreCase(DATE)
							|| source.getType().getName().equalsIgnoreCase(DATE_AND_TIME)
							|| source.getType().getName().equalsIgnoreCase(TIME_OF_DAY))) {
				return true;
			}
			if (source.getType().getName().equalsIgnoreCase(ANY_DATE)
					&& target.getType().getName().equalsIgnoreCase(ANY_DATE)) {
				return checkAnyAnyCompatibility();
			}

			return source.getType().getName().equalsIgnoreCase(target.getType().getName());
		} catch (NullPointerException e) {
			System.out.println(e);
		}

		return false;
	}

	private static boolean checkAnyAnyCompatibility() {
		return false;
	}

	private static boolean anyBitCompatibility(String name) {
		return (name.equalsIgnoreCase(WORD) || name.equalsIgnoreCase(LWORD) || name.equalsIgnoreCase(DWORD)
				|| name.equalsIgnoreCase(BYTE) || name.equalsIgnoreCase(BOOL));
	}

	private static boolean anyMagnitudeCompatibility(String name) {
		return (anyNumCompatibility(name) || name.equalsIgnoreCase(TIME));
	}

	private static boolean anyNumCompatibility(String name) {
		return (anyIntCompatiblity(name) || anyRealCompatibility(name));
	}

	private static boolean anyRealCompatibility(String name) {
		return (name.equalsIgnoreCase(REAL) || name.equalsIgnoreCase(LREAL));
	}

	private static boolean anyIntCompatiblity(String name) {
		return (name.equalsIgnoreCase(INT) || name.equalsIgnoreCase(UINT) || name.equalsIgnoreCase(SINT)
				|| name.equalsIgnoreCase(LINT) || name.equalsIgnoreCase(DINT) || name.equalsIgnoreCase(USINT)
				|| name.equalsIgnoreCase(UDINT) || name.equalsIgnoreCase(ULINT));
	}

	private static boolean adapaterTypeCompatibilityCheck(final AdapterDeclaration source,
			final AdapterDeclaration target) {
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
