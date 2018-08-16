/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, AIT fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren, Monika Wenger, Matthias Plasch
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.text.MessageFormat;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ModelCommandsPlugin;
import org.eclipse.fordiac.ide.model.commands.Messages;
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
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;

/**
 * The Class LinkConstraints.
 */
public final class LinkConstraints {

	private static final String ANY_ADAPTER = "ANY_ADAPTER"; //$NON-NLS-1$
	private static final String ANY_DATE = "ANY_DATE"; //$NON-NLS-1$
	private static final String ANY_STRING = "ANY_STRING"; //$NON-NLS-1$
	private static final String ANY_BIT = "ANY_BIT"; //$NON-NLS-1$
	private static final String ANY_REAL = "ANY_REAL"; //$NON-NLS-1$
	private static final String ANY_INT = "ANY_INT"; //$NON-NLS-1$
	private static final String ANY_NUM = "ANY_NUM"; //$NON-NLS-1$
	private static final String ANY_MAGNITUDE = "ANY_MAGNITUDE"; //$NON-NLS-1$
	private static final String ANY_ELEMENTARY = "ANY_ELEMENTARY"; //$NON-NLS-1$
	private static final String ANY = "ANY"; //$NON-NLS-1$
	/**Property ID for the enable CASTS preferences 
	 * 
	 * This constant is duplicated from the application plugin to avoid dependency cycles.
	 */
	public static final String PREFERENCE_ENABLE_CASTS = "enableCastPreference"; //$NON-NLS-1$

	
	/**
	 * Can create data connection.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param internalConnection
	 *            the internal connection
	 * 
	 * @return true, if a dataconnection can be created (the source and target
	 *         type are compatible
	 */
	public static boolean canCreateDataConnection(final VarDeclaration source,
			final VarDeclaration target) {
		return canExistDataConnection(source, target, null);
	}
	
	/**
	 * Can exist data connection.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param internalConnection
	 *            the internal connection
	 * @param reConnect
	 * 			  is it a reconnection or not
	 * 
	 * @return true, if successful
	 */
	public static boolean canExistDataConnection(final VarDeclaration source,
			final VarDeclaration target, Connection con) {
		if (source != null && target == null) {// connection just started
			if (!isWithConstraintOK(source)) {
				return false;
			}
			Abstract4DIACUIPlugin.statusLineErrorMessage(null);

			return true;
		}
		
		if (source != null && target != null && source != target) {
			if (((source instanceof AdapterDeclaration) && (!(target instanceof AdapterDeclaration))) ||
			     (!(source instanceof AdapterDeclaration) && (target instanceof AdapterDeclaration))) {
				//don't check if we try to create a connection from data to adapter or vice versa
				//TODO model refactoring - remove this when AdapterDeclaration is not inherited from VarDeclaration any more
				return false;
			}
			
			if (!sourceAndDestCheck(source, target)) {
				Abstract4DIACUIPlugin.statusLineErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
				return false;
			}
			
			if (!hasAlreadyInputConnectionsCheck(target, source, con)) {
				Abstract4DIACUIPlugin.statusLineErrorMessage(MessageFormat
										.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection,
												new Object[] { target.getName() }));
				return false;
			}

			if (!typeCheck(source, target)) {
				Abstract4DIACUIPlugin.statusLineErrorMessage(MessageFormat.format(
						Messages.LinkConstraints_STATUSMessage_NotCompatible,
						new Object[] { (null != source.getType()) ? source.getType().getName() : "N/A",
								(null != target.getType()) ? target.getType().getName() : "N/A" }));
				return false;
			}

			Abstract4DIACUIPlugin.statusLineErrorMessage(null);
			
			// FIX Input event can be connected to its own output event - ID: 3029433
//			if (source.isIsInput() && target.isIsInput() && source.eContainer().equals(target.eContainer())) // connection from input to output within one fb 
//				return false;
//			
			return isWithConstraintOK(source) && isWithConstraintOK(target);

		}
		return false;
	}

	/**
	 * Elements which are not linked by a with construct are not allowed to be connected
	 * @param varDecl
	 * @return
	 */
	public static boolean isWithConstraintOK(final VarDeclaration varDecl) {
		if ((!(varDecl instanceof AdapterDeclaration)) && (varDecl.getWiths().isEmpty())) { // elements which are not connect by withs are not allowed to be connected
			EObject obj = varDecl.eContainer();
			if(null != obj){
				obj = obj.eContainer();
				if((obj instanceof CompositeFBType) || (obj instanceof SubApp)){
					//data connections from and to interface data ports from composits should also be allowed from unwithed composite inputs (e.g., parameters for the FB)
					Abstract4DIACUIPlugin.statusLineErrorMessage(null);
					return true;
				}
			}
			
			Abstract4DIACUIPlugin.statusLineErrorMessage(MessageFormat.format(
					"{0} is not connected to an Event by a With-Construct" , varDecl.getName()));
			return false;
		}
		Abstract4DIACUIPlugin.statusLineErrorMessage(null);
		return true;
	}

	private static boolean hasAlreadyOutputConnectionsCheck(
			VarDeclaration source, Connection con) {
		for (Connection connection : source.getOutputConnections()) {
			if(!connection.equals(con)){
				return true;
			}
		}
		
		return false;
	}

	/** The compatibility. */
	private static boolean[][] compatibility = {
			{ true, false, false, false, false, false, false, false, false,
					true, false, false, false, false, false, false, false,
					false, false, false, true, false, false, true },
			{ false, true, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, false, false, false, true, false, false, false,
					true, false, false, false, false, false, false, false,
					false, false, false, true, false, false, true },
			{ false, false, false, false, false, false, true, false, false,
					true, false, false, false, false, false, false, false,
					false, false, false, true, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, false, false, false, false, false, false, true,
					false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, true },
			{ false, false, false, false, false, false, false, false, false,
					true, false, false, false, false, false, false, false,
					false, false, false, true, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, false, false, false, false, false, false, false,
					true, false, false, false, true, false, false, false,
					false, false, false, true, false, false, true },
			{ false, false, false, false, false, false, false, false, false,
					true, false, false, false, false, true, false, false,
					false, false, false, true, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, false, false, false, false, false, false, false,
					true, false, false, false, false, false, false, true,
					false, false, false, true, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, false, false, false, false, false, false, false,
					true, false, false, false, false, false, false, false,
					false, false, true, true, false, false, true },
			{ false, false, false, false, false, false, false, false, false,
					true, false, false, false, false, false, false, false,
					false, false, false, true, false, false, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ false, false, true, true, true, false, false, true, false, true,
					true, true, true, false, false, true, false, true, true,
					false, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true, true, true, true, true, true,
					true, true, false } };

	/**
	 * Type check.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * 
	 * @return true, if successful
	 */
	private static boolean typeCheck(final VarDeclaration source,
			final VarDeclaration target) {

		boolean automaticCasts = ModelCommandsPlugin
				.getDefault()
				.getPreferenceStore()
				.getBoolean(PREFERENCE_ENABLE_CASTS);

		if (automaticCasts) {
			BaseType1 sourceType = BaseType1.getByName(source.getTypeName()
					.toUpperCase());
			BaseType1 destType = BaseType1.getByName(target.getTypeName()
					.toUpperCase());

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
	 * @param target
	 *            the target
	 * @param con 
	 * 
	 * @return true, if successful
	 */
	private static boolean hasAlreadyInputConnectionsCheck(
			final VarDeclaration target, VarDeclaration src, Connection con) {
		boolean hasOnlyBrokenCons = true;
		
		VarDeclaration destination = target;
		if ((destination.isIsInput() && destination.eContainer().eContainer() instanceof CompositeFBType) ||
		     (!destination.isIsInput() && !(destination.eContainer().eContainer() instanceof CompositeFBType))) {
			destination = src;
			src = target;
		}
		
		
		if(!destination.getInputConnections().isEmpty()){
			FBNetwork fbNetworkTarget = getContainingFBNetwork(destination);
			FBNetwork fbNetworkSrc = getContainingFBNetwork(src);
			if((null != fbNetworkTarget) && (fbNetworkTarget != fbNetworkSrc)){
				//Broken connection check must only be performed of src and target are in different networks allowing to connect broken 
				//connections in the same container would lead to broken fb networks, see [issues:#82] for details
				for (Connection connection : destination.getInputConnections()) {
					if ((!connection.isBrokenConnection()) && !(connection.equals(con))){
						hasOnlyBrokenCons = false;
						break;
					}
				}
			}else{
				for (Connection connection : destination.getInputConnections()) {
					if(!connection.equals(con)){
						hasOnlyBrokenCons = false;			
						break;
					}
				}
			}
		}
		return hasOnlyBrokenCons;
	}

	private static FBNetwork getContainingFBNetwork(VarDeclaration target) {
		FBNetwork retVal = null;
		EObject obj = target.eContainer();
		if(null != obj){
			obj = obj.eContainer();
			if(null != obj){
				obj = obj.eContainer();
				if(obj instanceof FBNetwork){
					retVal = (FBNetwork)obj; 				
				}
			}
		}
		return retVal;
	}

	/**
	 * Source and dest check.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * 
	 * @return true, if successful
	 */
	private static boolean sourceAndDestCheck(final IInterfaceElement source,
			final IInterfaceElement target) {
		boolean canExist = (source.isIsInput() && !target.isIsInput())
				|| (!source.isIsInput() && target.isIsInput());
		
		if(!canExist){
			if((source.isIsInput() && target.isIsInput()) || 
					(!source.isIsInput() && !target.isIsInput())){
				EObject sourceCont = source.eContainer().eContainer();
				EObject destCont = target.eContainer().eContainer();
				//the connection can exist in this case if it is of an interface element of the container (e.g., SubApp, CFB) and an internal FB
				canExist = (sourceCont != destCont) && (isTypeContainer(sourceCont) || isTypeContainer(destCont)) &&
						(sourceCont.eContainer() != destCont.eContainer());  //and they are not on the same level (e.g., two subapps in the same subapplication/application						
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
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param internalConnection
	 *            the internal connection
	 * 
	 * @return true, if successful
	 */
	public static boolean canExistEventConnection(final Event source, final Event target) {
		if (source != null && target == null) {
			return true;
		}
		
		if (source != null && target != null) {
			boolean canExist = sourceAndDestCheck(source, target);
			// FIX Input event can be connected to its own output event - ID: 3029433
//			if (source.isIsInput() && target.isIsInput() && source.eContainer().equals(target.eContainer())) // connection from input to output within one fb 
//				return false;
			
			return canExist;
		}
		return false;
	}

	public static boolean canExistAdapterConnection(AdapterDeclaration source, AdapterDeclaration target, Connection con) {
		if (source != null && target != null && source != target) {
			if(!sourceAndDestCheck(source, target)){
				Abstract4DIACUIPlugin.statusLineErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
				return false;
			}

			if(!hasAlreadyInputConnectionsCheck(target, source, con)) {
				Abstract4DIACUIPlugin.statusLineErrorMessage(MessageFormat
										.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection,
												target.getName()));
				return false;
			}
			
			if(hasAlreadyOutputConnectionsCheck(source, con)){
				Abstract4DIACUIPlugin.statusLineErrorMessage(MessageFormat.format(
						Messages.LinkConstraints_STATUSMessage_hasAlreadyOutputConnection, source.getName()));
				return false;
			}

			if(!adapaterTypeCompatibilityCheck(source, target)) {
				Abstract4DIACUIPlugin.statusLineErrorMessage(MessageFormat.format(
						Messages.LinkConstraints_STATUSMessage_NotCompatible,
						new Object[] { (null != source.getType()) ? source.getType().getName() : "N/D",
								(null != target.getType()) ? target.getType().getName() : "N/D" }));
				return false;
			}

			Abstract4DIACUIPlugin.statusLineErrorMessage(null);
			return true;
		}
		return false;
	}


	public static boolean canCreateAdapterConnection(AdapterDeclaration source, AdapterDeclaration target) {
		return canExistAdapterConnection(source, target, null);
	}
	
	/**
	 * Default type compatibility check.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * 
	 * @return true, if default type compatibility check
	 */
	private static boolean defaultTypeCompatibilityCheck(
			final VarDeclaration source, final VarDeclaration target) {
		try {
			if (source.getType().getName().toUpperCase().startsWith(ANY)
					&& target.getType().getName().toUpperCase().startsWith(ANY)) {
				return checkAnyAnyCompatibility();
			}

			if (source.getType().getName().equalsIgnoreCase(ANY)
					&& !target.getType().getName().equalsIgnoreCase(ANY)) { 
				return true;
			}
			if (!source.getType().getName().equalsIgnoreCase(ANY) 
					&& target.getType().getName().equalsIgnoreCase(ANY)) { 
				return true;
			}

			if (source.getType().getName().equalsIgnoreCase(ANY) 
					&& target.getType().getName().equalsIgnoreCase(ANY)) {
				return checkAnyAnyCompatibility();
			}

			if (source.getType().getName().equalsIgnoreCase(ANY_ELEMENTARY) 
					&& !target.getType().getName()
							.equalsIgnoreCase(ANY_ELEMENTARY)) { 
				return true;
			}
			if (!source.getType().getName().equalsIgnoreCase(ANY_ELEMENTARY) 
					&& target.getType().getName()
							.equalsIgnoreCase(ANY_ELEMENTARY)) { 
				return true;
			}

			if (source.getType().getName().equalsIgnoreCase(ANY_ELEMENTARY) 
					&& target.getType().getName()
							.equalsIgnoreCase(ANY_ELEMENTARY)) {
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
					&& (target.getType().getName().equalsIgnoreCase("STRING") || target.getType().getName().equalsIgnoreCase("WSTRING"))) { //$NON-NLS-1$ //$NON-NLS-2$
				return true;
			}
			if ((target.getType().getName().equalsIgnoreCase(ANY_STRING))
					&& (source.getType().getName().equalsIgnoreCase("STRING") || source.getType().getName().equalsIgnoreCase("WSTRING"))) { //$NON-NLS-1$ //$NON-NLS-2$
				return true;
			}
			if (source.getType().getName().equalsIgnoreCase(ANY_STRING) 
					&& target.getType().getName()
							.equalsIgnoreCase(ANY_STRING)) { 
				return checkAnyAnyCompatibility();
			}

			if ((source.getType().getName().equalsIgnoreCase(ANY_DATE))
					&& (target.getType().getName().equalsIgnoreCase("DATE") //$NON-NLS-1$
							|| target.getType().getName()
									.equalsIgnoreCase("DATE_AND_TIME") || target //$NON-NLS-1$
							.getType().getName()
							.equalsIgnoreCase("TIME_OF_DAY"))) { //$NON-NLS-1$
				return true;
			}
			if ((target.getType().getName().equalsIgnoreCase(ANY_DATE))
					&& (source.getType().getName().equalsIgnoreCase("DATE") //$NON-NLS-1$
							|| source.getType().getName()
									.equalsIgnoreCase("DATE_AND_TIME") || source //$NON-NLS-1$
							.getType().getName()
							.equalsIgnoreCase("TIME_OF_DAY"))) { //$NON-NLS-1$
				return true;
			}
			if (source.getType().getName().equalsIgnoreCase(ANY_DATE) 
					&& target.getType().getName().equalsIgnoreCase(ANY_DATE)) { 
				return checkAnyAnyCompatibility();
			}
			
			return source.getType().getName()
					.equalsIgnoreCase(target.getType().getName());
		} catch (NullPointerException e) {
			System.out.println(e);
		}

		return false;
	}
	
	private static boolean checkAnyAnyCompatibility() {
		return false;
	}

	private static boolean anyBitCompatibility(String name) {
		return (name.equalsIgnoreCase("WORD") || name.equalsIgnoreCase("LWORD")  //$NON-NLS-1$//$NON-NLS-2$
				|| name.equalsIgnoreCase("DWORD") //$NON-NLS-1$
				|| name.equalsIgnoreCase("BYTE") || name //$NON-NLS-1$
					.equalsIgnoreCase("BOOL")); //$NON-NLS-1$
	}

	private static boolean anyMagnitudeCompatibility(String name) {
		return (anyNumCompatibility(name) || name.equalsIgnoreCase("TIME")); //$NON-NLS-1$
	}

	private static boolean anyNumCompatibility(String name) {
		return (anyIntCompatiblity(name) || anyRealCompatibility(name));
	}

	private static boolean anyRealCompatibility(String name) {
		return (name.equalsIgnoreCase("REAL") || name.equalsIgnoreCase("LREAL")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static boolean anyIntCompatiblity(String name) {
		return (name.equalsIgnoreCase("INT") || name.equalsIgnoreCase("UINT") //$NON-NLS-1$ //$NON-NLS-2$
				|| name.equalsIgnoreCase("SINT") || name.equalsIgnoreCase("LINT") //$NON-NLS-1$ //$NON-NLS-2$
				|| name.equalsIgnoreCase("DINT") || name.equalsIgnoreCase("USINT")//$NON-NLS-1$ //$NON-NLS-2$
				|| name.equalsIgnoreCase("UDINT") || name.equalsIgnoreCase("ULINT"));//$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public static boolean adapaterTypeCompatibilityCheck(final AdapterDeclaration source, final AdapterDeclaration target){
		try {
			if (((source.getType().getName().equals(ANY_ADAPTER)) && (!target.getType().getName().equals(ANY_ADAPTER))) || 
					((!source.getType().getName().equals(ANY_ADAPTER)) && (target.getType().getName().equals(ANY_ADAPTER)))) { 
				return true;
			}
			return source.getType().getName().equalsIgnoreCase(target.getType().getName());
		} catch (NullPointerException e) {
			System.out.println(e);
		}

		return false;
	}
	
	private LinkConstraints() {
		throw new UnsupportedOperationException("Class Linconstraints should not be created!");
	}

}

