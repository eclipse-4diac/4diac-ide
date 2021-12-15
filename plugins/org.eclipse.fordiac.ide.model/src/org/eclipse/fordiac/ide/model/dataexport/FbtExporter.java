/********************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2018 - 2020 Johannes Keppler University, Linz
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl, Matthias Plasch
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Refactored class hierarchy of xml exporters
 *               - fixed coordinate system resolution conversion in in- and export
 *               - changed exporting the Saxx cursor api
 *   Martin Melik Merkumians - adds export of internal FBs
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;

/**
 * The Class FbtExporter.
 */
class FbtExporter extends AbstractBlockTypeExporter {

	private static final Pattern CDATA_END_PATTERN = Pattern.compile("\\]\\]>"); //$NON-NLS-1$

	/**
	 * Instantiates a new fbt exporter.
	 *
	 * @param entry
	 */
	FbtExporter(final FBTypePaletteEntry entry) {
		super(entry.getTypeEditable());
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.FB_TYPE;
	}

	@Override
	protected void createBlockTypeSpecificXMLEntries() throws XMLStreamException {
		if (getType() instanceof CompositeFBType) {
			new FBNetworkExporter(this).createFBNetworkElement(((CompositeFBType) getType()).getFBNetwork());
		} else if (getType() instanceof BasicFBType) {
			addBasicFB((BasicFBType) getType());
		} else if (getType() instanceof SimpleFBType) {
			addSimpleFB((SimpleFBType) getType());
		}
	}

	/**
	 * Adds the basic fb.
	 *
	 * @param type the type
	 * @throws XMLStreamException
	 */
	private void addBasicFB(final BasicFBType type) throws XMLStreamException {
		addStartElement(LibraryElementTags.BASIC_F_B_ELEMENT);
		addInternalVarList(type.getInternalVars(), type.getInternalFbs(), LibraryElementTags.INTERNAL_VARS_ELEMENT);
		addECC(type.getECC());
		for (final Algorithm alg : type.getAlgorithm()) {
			addAlgorithm(alg);
		}
		addEndElement();
	}

	/**
	 * Adds the other algorithm.
	 *
	 * @param algorithm the algorithm
	 * @throws XMLStreamException
	 */
	private void addOtherAlgorithm(final OtherAlgorithm algorithm) throws XMLStreamException {
		addStartElement(LibraryElementTags.OTHER_ELEMENT);
		getWriter().writeAttribute(LibraryElementTags.LANGUAGE_ATTRIBUTE,
				(null != algorithm.getLanguage()) ? algorithm.getLanguage() : ""); //$NON-NLS-1$

		writeTextAlgorithmText(algorithm);
		addInlineEndElement();
	}

	private void writeTextAlgorithmText(final TextAlgorithm algorithm) throws XMLStreamException {
		if (null != algorithm.getText()) {
			final Matcher endPatternMatcher = CDATA_END_PATTERN.matcher(algorithm.getText());
			int currentPosition = 0;
			if (endPatternMatcher.find()) { // Check if we have at least one CData end pattern in the string
				do {
					getWriter().writeCData(algorithm.getText().substring(currentPosition, endPatternMatcher.start()));
					getWriter().writeCharacters("]]>"); //$NON-NLS-1$
					currentPosition = endPatternMatcher.end();
				} while (endPatternMatcher.find());

				if (currentPosition < algorithm.getText().length()) {
					// there is some text after the last CData end pattern
					getWriter().writeCData(algorithm.getText().substring(currentPosition));
				}
			} else {
				// no CData end pattern write the algorithm text as whole
				getWriter().writeCData(algorithm.getText());
			}
		} else {
			getWriter().writeCharacters(""); //$NON-NLS-1$
		}
	}

	/**
	 * Adds the st algorithm.
	 *
	 * @param algorithm the algorithm
	 * @throws XMLStreamException
	 */
	private void addSTAlgorithm(final STAlgorithm algorithm) throws XMLStreamException {
		addStartElement(LibraryElementTags.ST_ELEMENT);
		writeTextAlgorithmText(algorithm);
		addInlineEndElement();
	}

	/**
	 * Adds the ecc.
	 *
	 * @param ecc the ecc
	 * @throws XMLStreamException
	 */
	private void addECC(final ECC ecc) throws XMLStreamException {
		addStartElement(LibraryElementTags.ECC_ELEMENT);
		if (ecc != null) {
			addECStates(ecc.getECState(), ecc.getStart());
			for (final ECTransition transition : ecc.getECTransition()) {
				createTransitionEntry(transition);
			}
		}
		addEndElement();
	}

	/**
	 * Create a transition entry
	 *
	 * @param transition the transition
	 * @throws XMLStreamException
	 */
	private void createTransitionEntry(final ECTransition transition) throws XMLStreamException {
		addEmptyStartElement(LibraryElementTags.ECTRANSITION_ELEMENT);
		getWriter().writeAttribute(LibraryElementTags.SOURCE_ATTRIBUTE, transition.getSource().getName());
		getWriter().writeAttribute(LibraryElementTags.DESTINATION_ATTRIBUTE, transition.getDestination().getName());
		getWriter().writeAttribute(LibraryElementTags.CONDITION_ATTRIBUTE, transition.getConditionText());
		if (null != transition.getComment()) {
			getWriter().writeAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, transition.getComment());
		}
		addXYAttributes(transition);
	}

	/**
	 * Adds the ec states.
	 *
	 * @param states     the states
	 * @param startState the start state
	 * @throws XMLStreamException
	 */
	private void addECStates(final List<ECState> states, final ECState startState) throws XMLStreamException {
		createECState(startState);
		for (final ECState state : states) {
			if (!state.equals(startState)) {
				createECState(state);
			}
		}
	}

	/**
	 * Creates the ec state.
	 *
	 * @param state the state
	 * @throws XMLStreamException
	 */
	private void createECState(final ECState state) throws XMLStreamException {
		addStartElement(LibraryElementTags.ECSTATE_ELEMENT);

		addNameAttribute(state.getName());
		addCommentAttribute(state);
		addXYAttributes(state);

		addECActions(state.getECAction());

		addEndElement();
	}

	/**
	 * Adds the ec actions.
	 *
	 * @param actions the actions
	 * @throws XMLStreamException
	 */
	private void addECActions(final List<ECAction> actions) throws XMLStreamException {
		for (final ECAction action : actions) {
			addEmptyStartElement(LibraryElementTags.ECACTION_ELEMENT);
			if (action.getAlgorithm() != null) {
				getWriter().writeAttribute(LibraryElementTags.ALGORITHM_ELEMENT, action.getAlgorithm().getName());
			}
			if (action.getOutput() != null) {
				getWriter().writeAttribute(LibraryElementTags.OUTPUT_ATTRIBUTE, action.getOutput().getName());
			}
		}
	}

	/**
	 * Adds the simple fb.
	 *
	 * @param type the type
	 * @throws XMLStreamException
	 */
	private void addSimpleFB(final SimpleFBType type) throws XMLStreamException {
		addStartElement(LibraryElementTags.SIMPLE_F_B_ELEMENT);
		addInternalVarList(type.getInternalVars(), type.getInternalFbs(), LibraryElementTags.INTERNAL_VARS_ELEMENT);
		addAlgorithm(type.getAlgorithm());
		addEndElement();
	}

	/**
	 * Adds the algorithm.
	 *
	 * @param algorithms the algorithms
	 * @throws XMLStreamException
	 */
	private void addAlgorithm(final Algorithm algorithm) throws XMLStreamException {
		addStartElement(LibraryElementTags.ALGORITHM_ELEMENT);

		addNameAttribute(algorithm.getName());
		addCommentAttribute(algorithm);

		if (algorithm instanceof STAlgorithm) {
			addSTAlgorithm((STAlgorithm) algorithm);
		} else if (algorithm instanceof OtherAlgorithm) {
			addOtherAlgorithm((OtherAlgorithm) algorithm);
		}
		addEndElement();
	}

}
