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

import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;

public final class NameRepository {

	private NameRepository() {
		// empty private constructor
	}

	public static String createUniqueTypeName(final LibraryElement type) {
		final TypeLibrary typeLibrary = type.getTypeLibrary();
		String typeName = type.getName();

		if (type instanceof DataType) {
			final DataTypeLibrary dataTypeLibrary = typeLibrary.getDataTypeLibrary();
			while (dataTypeLibrary.getTypeIfExists(typeName) != null) {
				typeName = createUniqueName(type.getName(), typeName);
			}
			return typeName;
		}

		if (type instanceof FBType) {
			while (typeLibrary.getFBTypeEntry(typeName) != null) {
				typeName = createUniqueName(type.getName(), typeName);
			}
			return typeName;
		}

		if (type instanceof SubAppType) {
			while (typeLibrary.getSubAppTypeEntry(typeName) != null) {
				typeName = createUniqueName(type.getName(), typeName);
			}
			return typeName;
		}

		if (type instanceof AdapterType) {
			while (typeLibrary.getAdapterTypeEntry(typeName) != null) {
				typeName = createUniqueName(type.getName(), typeName);
			}
			return typeName;
		}

		return null;

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
		String retVal = nameProposal;

		if (IdentifierVerifier.verifyIdentifier(nameProposal).isPresent() && nameProposal.contains(".")) //$NON-NLS-1$
		{
			retVal = nameProposal.replace(".", "_"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		Assert.isTrue(IdentifierVerifier.verifyIdentifier(retVal).isEmpty(),
				"The given name proposal is not a valid identifier!"); //$NON-NLS-1$

		Assert.isNotNull(element.eContainer(),
				"For a correct operation createuniqueName expects that the model element is already added in its containing model!"); //$NON-NLS-1$

		if (element instanceof IInterfaceElement) {
			// for interface elements we need to check if it not a reserved keyword
			retVal = checkReservedKeyWords(retVal);
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

		if (IdentifierVerifier.verifyIdentifier(nameProposal).isPresent()) {
			ErrorMessenger.popUpErrorMessage(
					MessageFormat.format(Messages.NameRepository_NameNotAValidIdentifier, nameProposal));
			return false;
		}
		if (element instanceof IInterfaceElement && FordiacKeywords.isReservedKeyword(nameProposal)) {
			ErrorMessenger
			.popUpErrorMessage(MessageFormat.format(Messages.NameRepository_NameReservedKeyWord, nameProposal));
			return false;
		}

		if (getRefNames(element).parallelStream().anyMatch(name -> name.equalsIgnoreCase(nameProposal))) {
			ErrorMessenger
			.popUpErrorMessage(MessageFormat.format(Messages.NameRepository_NameAlreadyExists, nameProposal));
			return false;
		}

		return true;
	}

	private static Set<String> getRefNames(final INamedElement refElement) {
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
			if (refElement.eContainer() instanceof BaseFBType) {
				elementsList = ((BaseFBType) (refElement.eContainer())).getInternalFbs();
			} else {
				elementsList = ((FBNetworkElement) refElement).getFbNetwork().getNetworkElements();
			}
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
					elements.addAll(((BaseFBType) interfaceList.eContainer()).getInternalConstVars());
				}
				elementsList = elements;
			}
		} else if (refElement instanceof ServiceSequence) {
			final ServiceSequence seq = (ServiceSequence) refElement;
			elementsList = seq.getService().getServiceSequence();
		} else {
			throw new IllegalArgumentException(
					"Reference list for given class not available: " + refElement.getClass().toString()); //$NON-NLS-1$
		}

		return elementsList.stream().filter(element -> element != refElement).map(INamedElement::getName)
				.collect(Collectors.toSet());
	}

	/** Generating a unique name for a name proposal which is definitely not in the list of given existing names
	 *
	 * If the proposed name is already found in the list an '_' and a consecutive number is appended to the proposed
	 * name. The number incremented until a unique name is found.
	 *
	 * @param existingNameList the list of names already existing in the context
	 * @param nameProposal     a proposal for a name as starting point
	 * @return a unique name */
	private static String getUniqueName(final Set<String> existingNameList, final String nameProposal) {
		String temp = nameProposal;
		while (existingNameList.contains(temp)) {
			temp = createUniqueName(nameProposal, temp);
		}
		return temp;
	}

	static String createUniqueName(final String nameProposal, final String temp) {
		final String digits = extractDigitsFromEnd(temp);
		if (!"".equals(digits)) { //$NON-NLS-1$
			try {
				// if we are close to the limits of int we need to check if we have an overflow: check the exception
				final long newNumber = Integer.parseInt(digits) + 1L;
				// if the number was fine during conversion we need to check that we did not go over while incrementing
				if (newNumber > Integer.MAX_VALUE) {
					return createFallbackProposal(nameProposal);
				}

				return temp.substring(0, temp.length() - digits.length())
						+ zeroPrefixedNumber(newNumber, digits.length());
			} catch (final NumberFormatException e) {
				return createFallbackProposal(nameProposal);
			}

		}
		return createFallbackProposal(nameProposal);
	}

	private static String zeroPrefixedNumber(final long number, final int digits) {
		final String zeroPrefixedDecimal = "%0" + digits + "d"; //$NON-NLS-1$ //$NON-NLS-2$
		return String.format(zeroPrefixedDecimal, Long.valueOf(number));
	}

	private static String createFallbackProposal(final String nameProposal) {
		return nameProposal + "_1"; //$NON-NLS-1$
	}

	private static String extractDigitsFromEnd(final String data) {
		final int MAX_LENGTH_INTEGER = 10;
		final StringBuilder sb = new StringBuilder();
		int c = 0;
		for (int i = data.length() - 1; i > 0 && c <= MAX_LENGTH_INTEGER; i--, c++) {
			if (data.charAt(i) >= '0' && data.charAt(i) <= '9') {
				sb.insert(0, data.charAt(i));
			} else {
				break;
			}
		}
		if (c > MAX_LENGTH_INTEGER) {
			return ""; //$NON-NLS-1$
		}
		return sb.toString();
	}

	private static String checkReservedKeyWords(final String name) {
		if (FordiacKeywords.isReservedKeyword(name)) {
			return name + "1"; //$NON-NLS-1$
		}
		return name;
	}
}

