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

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Annotation;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;

public final class NameRepository {


	private static final Set<String> RESERVED_KEYWORDS =
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
			Annotation ano = element.createAnnotation(MessageFormat.format(Messages.NameRepository_NameNotAValidIdentifier, element.getName()));
			ano.setServity(2); // 2 means error!
		}
	}
	
	/** Check and if necessary adapt the given name proposal so that it is a valid name for the given INamedElement
	 *
	 * This method expects that the element is already correctly inserted in the model and that its eContainer returns the correct container.
	 * The nameProposal needs to be a valid identifier
	 *
	 * @param element      the element for which the name should be created
	 * @param nameProposal a proposal for a name of the element
	 * @return  a valid unique element name
	 */
	public static String createUniqueName(final INamedElement element, final String nameProposal) {
		Assert.isTrue(IdentifierVerifyer.isValidIdentifier(nameProposal), "The given name proposal is not a valid identifier!");  //$NON-NLS-1$
		Assert.isNotNull(element.eContainer(), "For a correct operation createuniqueName expects that the model element is already added in its containing model!"); //$NON-NLS-1$

		String retVal = nameProposal;
		if(element instanceof IInterfaceElement) {
			//for interface elements we need to check if it not a reserved keyword
			retVal = checkReservedKeyWords(nameProposal);
		}
		return getUniqueName(getRefNames(element), retVal);
	}

	/** Check if the given nameProposal is a valid name for the given named element.
	 *
	 * @param element       the named element for which a new name proposal should be checked
	 * @param nameProposal  the new name to be checked
	 * @return  true if the nameProposal is a valid new name for the named element
	 */
	public static boolean isValidName(final INamedElement element, final String nameProposal) {
		Assert.isNotNull(element.eContainer(), "For a correct operation createuniqueName expects that the model element is already added in its containing model!"); //$NON-NLS-1$
		Abstract4DIACUIPlugin.statusLineErrorMessage(null);

		if(!IdentifierVerifyer.isValidIdentifier(nameProposal)){
			Abstract4DIACUIPlugin.statusLineErrorMessage(MessageFormat.format(Messages.NameRepository_NameNotAValidIdentifier, nameProposal));
			return false;
		}
		if(element instanceof IInterfaceElement && RESERVED_KEYWORDS.contains(nameProposal)) {
			Abstract4DIACUIPlugin.statusLineErrorMessage(MessageFormat.format(Messages.NameRepository_NameReservedKeyWord, nameProposal));
			return false;
		}

		if(getRefNames(element).contains(nameProposal)) {
			Abstract4DIACUIPlugin.statusLineErrorMessage((MessageFormat.format(Messages.NameRepository_NameAlreadyExists, nameProposal)));
			return false;
		}

		return true;
	}


	private static Set<String> getRefNames(INamedElement refElement){
		EList<?extends INamedElement> elementsList = null;

		//TODO consider moving this instance of cascade into the model utilizing the inheritance hierarchy to our advantage
		if(refElement instanceof Algorithm) {
			elementsList = ((BasicFBType)((Algorithm)refElement).eContainer()).getAlgorithm();
		} else if(refElement instanceof Application) {
			elementsList = ((Application)refElement).getAutomationSystem().getApplication();
		} else if(refElement instanceof Device) {
			elementsList = ((Device)refElement).getSystemConfiguration().getDevices();
		} else if(refElement instanceof FBNetworkElement) {
			elementsList = ((FBNetworkElement)refElement).getFbNetwork().getNetworkElements();
		} else if(refElement instanceof Resource) {
			elementsList = ((Resource)refElement).getDevice().getResource();
		} else if(refElement instanceof Segment) {
			elementsList = ((SystemConfiguration)((Segment)refElement).eContainer()).getSegments();
		} else if(refElement instanceof ECState) {
			elementsList = ((ECC)((ECState)refElement).eContainer()).getECState();
		} else if(refElement instanceof IInterfaceElement) {
			EList<INamedElement> elements = new BasicEList<INamedElement>();
			InterfaceList interfaceList = null;
			if(((IInterfaceElement)refElement).eContainer() instanceof InterfaceList) {
				interfaceList = (InterfaceList)((IInterfaceElement)refElement).eContainer();
			} else {
				//this is an internal variable
				interfaceList = ((BasicFBType)((IInterfaceElement)refElement).eContainer()).getInterfaceList();
			}
			elements.addAll(interfaceList.getAllInterfaceElements());
			if(interfaceList.eContainer() instanceof BasicFBType){
				elements.addAll(((BasicFBType)interfaceList.eContainer()).getInternalVars());
			}
			elementsList = elements;
		} else {
			throw new IllegalArgumentException("Refenrence list for given class not available: " + refElement.getClass().toString()); //$NON-NLS-1$
		}

		return elementsList.stream().filter(element -> element != refElement).map(element -> element.getName()).collect(Collectors.toSet());
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
	private static String getUniqueName(Set<String> existingNameList, String nameProposal){
		String temp = nameProposal;
		
		int i = 1;
		while (existingNameList.contains(temp)) {
			temp = nameProposal + "_" + i; //$NON-NLS-1$
			i++;
		}
		return temp;
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
	
	}
