/********************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Monika Wenger, Gerd Kainz, Martin Jobst
 *    - initial API and implementation and/or initial documentation
 *  Bianca Wiesmayr
 *    - improve name proposals
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import static org.eclipse.fordiac.ide.model.FordiacKeywords.RESERVED_KEYWORDS;

import java.text.MessageFormat;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
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

public final class NameRepository {

	private static final Pattern END_IN_NUMBER_PATTERN = Pattern.compile("^.*\\d$"); //$NON-NLS-1$
	private static final Pattern GET_LAST_NUMBER_PATTERN = Pattern.compile("\\d+$"); //$NON-NLS-1$

	private NameRepository() {
		// empty private constructor
	}

	/**
	 * Check and if necessary adapt the given name proposal so that it is a valid
	 * name for the given INamedElement
	 *
	 * This method expects that the element is already correctly inserted in the
	 * model and that its eContainer returns the correct container. The nameProposal
	 * needs to be a valid identifier
	 *
	 * @param element      the element for which the name should be created
	 * @param nameProposal a proposal for a name of the element
	 * @return a valid unique element name
	 */
	public static String createUniqueName(final INamedElement element, final String nameProposal) {
		Assert.isTrue(IdentifierVerifyer.isValidIdentifier(nameProposal),
				"The given name proposal is not a valid identifier!"); //$NON-NLS-1$
		Assert.isNotNull(element.eContainer(),
				"For a correct operation createuniqueName expects that the model element is already added in its containing model!"); //$NON-NLS-1$

		String retVal = nameProposal;
		if (element instanceof IInterfaceElement) {
			// for interface elements we need to check if it not a reserved keyword
			retVal = checkReservedKeyWords(nameProposal);
		}
		return getUniqueName(getRefNames(element), retVal);
	}

	/**
	 * Check if the given nameProposal is a valid name for the given named element.
	 *
	 * @param element      the named element for which a new name proposal should be
	 *                     checked
	 * @param nameProposal the new name to be checked
	 * @return true if the nameProposal is a valid new name for the named element
	 */
	public static boolean isValidName(final INamedElement element, final String nameProposal) {
		Assert.isNotNull(element.eContainer(),
				"For a correct operation createuniqueName expects that the model element is already added in its containing model!"); //$NON-NLS-1$

		if (!IdentifierVerifyer.isValidIdentifier(nameProposal)) {
			ErrorMessenger.popUpErrorMessage(
					MessageFormat.format(Messages.NameRepository_NameNotAValidIdentifier, nameProposal));
			return false;
		}
		if (element instanceof IInterfaceElement && RESERVED_KEYWORDS.contains(nameProposal)) {
			ErrorMessenger.popUpErrorMessage(
					MessageFormat.format(Messages.NameRepository_NameReservedKeyWord, nameProposal));
			return false;
		}

		if (getRefNames(element).contains(nameProposal)) {
			ErrorMessenger.popUpErrorMessage(
					MessageFormat.format(Messages.NameRepository_NameAlreadyExists, nameProposal));
			return false;
		}

		return true;
	}

	private static Set<String> getRefNames(INamedElement refElement) {
		EList<? extends INamedElement> elementsList = null;

		// TODO consider moving this instance of cascade into the model utilizing the
		// inheritance hierarchy to our advantage
		if (refElement instanceof Algorithm) {
			elementsList = ((BasicFBType) ((Algorithm) refElement).eContainer()).getAlgorithm();
		} else if (refElement instanceof Application) {
			elementsList = ((Application) refElement).getAutomationSystem().getApplication();
		} else if (refElement instanceof Device) {
			elementsList = ((Device) refElement).getSystemConfiguration().getDevices();
		} else if (refElement instanceof FBNetworkElement) {
			elementsList = ((FBNetworkElement) refElement).getFbNetwork().getNetworkElements();
		} else if (refElement instanceof Resource) {
			elementsList = ((Resource) refElement).getDevice().getResource();
		} else if (refElement instanceof Segment) {
			elementsList = ((SystemConfiguration) ((Segment) refElement).eContainer()).getSegments();
		} else if (refElement instanceof ECState) {
			elementsList = ((ECC) ((ECState) refElement).eContainer()).getECState();
		} else if (refElement instanceof IInterfaceElement) {
			if (((IInterfaceElement) refElement).eContainer() instanceof StructuredType) {
				elementsList = ((StructuredType) ((IInterfaceElement) refElement).eContainer()).getMemberVariables();
			} else {
				final EList<INamedElement> elements = new BasicEList<>();
				InterfaceList interfaceList = null;
				if (((IInterfaceElement) refElement).eContainer() instanceof InterfaceList) {
					interfaceList = (InterfaceList) ((IInterfaceElement) refElement).eContainer();
				} else {
					// this is an internal variable
					interfaceList = ((BaseFBType) ((IInterfaceElement) refElement).eContainer()).getInterfaceList();
				}
				elements.addAll(interfaceList.getAllInterfaceElements());
				if (interfaceList.eContainer() instanceof BaseFBType) { // has internal variables
					elements.addAll(((BaseFBType) interfaceList.eContainer()).getInternalVars());
				}
				elementsList = elements;
			}
		} else {
			throw new IllegalArgumentException(
					"Refenrence list for given class not available: " + refElement.getClass().toString()); //$NON-NLS-1$
		}

		return elementsList.stream().filter(element -> element != refElement).map(element -> element.getName())
				.collect(Collectors.toSet());
	}

	/**
	 * Generating a unique name for a name proposal which is definitely not in the
	 * list of given existing names
	 *
	 * If the proposed name is already found in the list an '_' and a consecutive
	 * number is appended to the proposed name. The number incremented until a
	 * unique name is found.
	 *
	 * @param existingNameList the list of names already existing in the context
	 * @param nameProposal     a proposal for a name as starting point
	 * @return a unique name
	 */
	private static String getUniqueName(Set<String> existingNameList, String nameProposal) {
		String temp = nameProposal;
		while (existingNameList.contains(temp)) {
			if (END_IN_NUMBER_PATTERN.matcher(temp).matches()) {
				final Matcher matchNumber = GET_LAST_NUMBER_PATTERN.matcher(temp);
				matchNumber.find();
				final int number = Integer.parseInt(temp.substring(matchNumber.start(), matchNumber.end())) + 1;
				temp = temp.substring(0, matchNumber.start()) + number; // $NON-NLS-1$
			} else {
				temp = nameProposal + "_" + 1; //$NON-NLS-1$
			}
		}
		return temp;
	}

	private static String checkReservedKeyWords(String name) {
		if (RESERVED_KEYWORDS.contains(name.toUpperCase())) {
			return name + "1"; //$NON-NLS-1$
		}
		return name;
	}

}
