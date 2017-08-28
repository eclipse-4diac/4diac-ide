/********************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Monika Wenger, Gerd Kainz, Martin Jobst
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Annotation;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;

public class NameRepository {


	public static final Set<String> RESERVED_KEYWORDS =
			Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(new String[] {
					"VAR", "END_VAR", "CONSTANT", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					"SUPER", "RETURN", //$NON-NLS-1$ //$NON-NLS-2$
					"IF", "THEN", "END_IF", "ELSIF", "ELSE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					"CASE", "OF", "END_CASE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					"EXIT", "CONTINUE", //$NON-NLS-1$ //$NON-NLS-2$
					"FOR", "TO", "BY", "DO", "END_FOR", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					"WHILE", "END_WHILE", //$NON-NLS-1$ //$NON-NLS-2$
					"REPEAT", "UNTIL", "END_REPEAT", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					"OR", "XOR", "AND", "MOD", "NOT", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					"E", "D", "H", "M", "S", "MS", "US", "NS", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
					"DINT", "INT", "SINT", "LINT", "UINT", "USINT", "UDINT", "ULINT", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
					"REAL", "LREAL", //$NON-NLS-1$ //$NON-NLS-2$
					"STRING", "WSTRING", //$NON-NLS-1$ //$NON-NLS-2$
					"CHAR", "WCHAR", //$NON-NLS-1$ //$NON-NLS-2$
					"TIME", "LTIME", //$NON-NLS-1$ //$NON-NLS-2$
					"TIME_OF_DAY", "LTIME_OF_DAY", "TOD", "LTOD", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					"DATE", "LDATE", //$NON-NLS-1$ //$NON-NLS-2$
					"DATE_AND_TIME", "LDATE_AND_TIME", //$NON-NLS-1$ //$NON-NLS-2$
					"BOOL" //$NON-NLS-1$
					})));

	private NameRepository() {
		// empty private constructor
	}
	
	public static void checkNameIdentifier(INamedElement element){
		element.getAnnotations().clear();
		if(!IdentifierVerifyer.isValidIdentifier(element.getName())){
			Annotation ano = element.createAnnotation("Name: " + element.getName() + " is not a valid identifier!");
			ano.setServity(2); // 2 means error!
		}
	}
	

	/**
	 * Checks whether the instance name is unique in the fb network it is contained in
	 * 
	 * @param fb
	 * @return
	 */
	public static boolean isFBNetworkUniqueFBInstanceName(FB fb) {
		boolean retVal = true;
		if (fb != null) {
			FBNetwork fbn = fb.getFbNetwork();
			if(null != fbn){
				for (FBNetworkElement element : fbn.getNetworkElements()) {
					if((!element.equals(fb)) && (element.getName().equals(fb.getName()))){
						retVal = false;
						break;
					}				
				}
			}
		}
		return retVal;				
	}
	
	/** Generating a unique name for a name proposal which is definitely not in the list of given existing names
	 * 
	 * If the proposed name is already found in the list an '_' and a consecutive number is appended to the proposed name. 
	 * The number incremented until a unique name is found.  
	 * 
	 * @param existingNameList the list of names already existing in the context 
	 * @param nameProposal a proposal for a name as starting point
	 * @return a unique name
	 */
	static private String getUniqueName(HashSet<String> existingNameList, String nameProposal){
		String temp = nameProposal;
		
		int i = 1;
		while (existingNameList.contains(temp)) {
			temp = nameProposal + "_" + i; //$NON-NLS-1$
			i++;
		}
		return temp;
	}
	
	

	/**
	 * Returns a unique Instance name for a FB network
	 * 
	 * 
	 * @param fbn  the fbnetwork where the unique names should be searched for.
	 * @param nameProposal the proposal for the new name
	 * @return
	 */
	public static String getFBNetworkUniqueFBNetworkElementInstanceName(
			FBNetwork fbn, String nameProposal) {
		if (fbn != null) {
			//TODO consider to also add the referenced FB here so that the old name is not considered
			HashSet<String> uniqueNames = getNameList(null, fbn.getNetworkElements()); 
			return getUniqueName(uniqueNames, nameProposal);
		}
		return nameProposal;
	}
	
	public static String getResourceUniqueFBInstanceName(final Resource resourceUT, final String name) {
		HashSet<String> resourceNames = getNameList(resourceUT, resourceUT.getFBNetwork().getNetworkElements());
		return getUniqueName(resourceNames, name);
	}

	public static String getUniqueResourceInstanceName(final Resource resourceUT, final String name) {
		if(null != resourceUT.eContainer()){
			HashSet<String> resourceNames = getNameList(resourceUT,resourceUT.getDevice().getResource());
			return getUniqueName(resourceNames, name);
		}
		return name;
	}

	public static String getUniqueDeviceInstanceName(final Device deviceUT, final String name) {
		if(null != deviceUT.eContainer()){
			HashSet<String> deviceNames = getNameList(deviceUT, deviceUT.getAutomationSystem().getSystemConfiguration().getDevices());
			return getUniqueName(deviceNames, name);
		}
		return name;
	}

	public static String getUniqueElementName(INamedElement element, EObject object, String name){
		if(element instanceof IInterfaceElement){
			IInterfaceElement ie = (IInterfaceElement)element;
			if(ie.getFBNetworkElement() instanceof SubApp && null == ie.getFBNetworkElement().getType()){
				return getUniqueInterfaceElementName(ie, (InterfaceList)ie.eContainer(), name);
			}
			return getUniqueInterfaceElementName(ie, (FBType)object, name);
		}
		if(element instanceof ECState){
			return getUniqueECCStateName((ECState)element, (ECC)object, name);
		}
		if(element instanceof Algorithm){
			return getUniqueAlgorithmName((Algorithm)element, (BasicFBType)object, name);
		}
		return name;
	}
	
	public static String getUniqueECCStateName(ECState state, ECC ecc, String name) {
		HashSet<String> stateNames = getNameList(state, ecc.getECState());
		return getUniqueName(stateNames, name);
	}

	public static String getUniqueInterfaceElementName(IInterfaceElement iElement, InterfaceList interfaceList, String name) {
		ArrayList<INamedElement> elements = new ArrayList<INamedElement>();
		elements.addAll(interfaceList.getAllInterfaceElements());		
		String retVal = checkReservedKeyWords(name);
		int i = 1;
		while (!isUnique(iElement, retVal, elements))  {
			retVal = name + i;
			i++;
		}
		return retVal;
	}
	
	public static String getUniqueInterfaceElementName(IInterfaceElement iElement, FBType fbType, String name) {
		ArrayList<INamedElement> elements = new ArrayList<INamedElement>();
		elements.addAll(fbType.getInterfaceList().getAllInterfaceElements());
		if(fbType instanceof BasicFBType){
			elements.addAll(((BasicFBType)fbType).getInternalVars());
		}
		String retVal = checkReservedKeyWords(name);
		int i = 1;
		while (!isUnique(iElement, retVal, elements))  {
			retVal = name + i;
			i++;
		}
		return retVal;
	}

	public static String getUniqueAlgorithmName(Algorithm algorithm, BasicFBType fbType, String name){
		ArrayList<INamedElement> elements = new ArrayList<INamedElement>();
		elements.addAll(fbType.getAlgorithm());
		
		String retVal = name;
		int i = 1;
		while (!isUnique(algorithm, retVal, elements))  {
			retVal = name + i;
			i++;
		}
		return retVal;
	}

	private static boolean isUnique(INamedElement iElement, String name, ArrayList<INamedElement> elements) {
		for (INamedElement element : elements) {
			 if (!element.equals(iElement) && element.getName()
					.toUpperCase().equals(name.toUpperCase())) {
				 return false;
			 }
		}
		return true;
	}
	
	private static String checkReservedKeyWords(String name) {
		if(RESERVED_KEYWORDS.contains(name.toUpperCase())) {
			return name + "1"; //$NON-NLS-1$
		}
		for (DataType dataType : DataTypeLibrary.getInstance().getDataTypesSorted()) {
			if(dataType.getName().equalsIgnoreCase(name)){
				return name + "1"; //$NON-NLS-1$
			}
		}
		return name;
	}
	
	private static HashSet<String> getNameList(INamedElement refElement, EList<?extends INamedElement> elementsList){
		HashSet<String> nameList = new HashSet<>();		
		for (INamedElement element : elementsList) {
			if(!element.equals(refElement)){
				nameList.add(element.getName());
			}
		}		
		return nameList;
	}

}
