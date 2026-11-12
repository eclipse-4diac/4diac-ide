/********************************************************************************
 * Copyright (c) 2008 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                    Johannes Keppler University, Linz,
 *                    Primetals Technologies Austria GmbH,
 *                    Martin Erich Jobst
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
 *   Alois Zoitl - updated for new adapter FB handling
 *   Martin Erich Jobst - rework source element export
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.OtherSourceElement;
import org.eclipse.fordiac.ide.model.libraryElement.STSourceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SourceComment;
import org.eclipse.fordiac.ide.model.libraryElement.SourceElement;
import org.eclipse.fordiac.ide.model.libraryElement.TextMethod;
import org.eclipse.fordiac.ide.model.libraryElement.TextSourceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * The Class FbtExporter.
 */
public class FbtExporter extends AbstractBlockTypeExporter {

	/**
	 * Instantiates a new fbt exporter.
	 *
	 * @param entry
	 */
	public FbtExporter(final FBType type) {
		super(type);
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.FB_TYPE;
	}

	@Override
	protected void createBlockTypeSpecificXMLEntries() throws XMLStreamException {
		if (getType() instanceof final CompositeFBType cFBT) {
			new FBNetworkExporter(this).createFBNetworkElement(cFBT.getFBNetwork());
		} else if (getType() instanceof final BasicFBType bFBT) {
			addBasicFB(bFBT);
		} else if (getType() instanceof final SimpleFBType sFBT) {
			addSimpleFB(sFBT);
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
		addVarList(type.getInternalConstVars(), LibraryElementTags.INTERNAL_CONST_VARS_ELEMENT);
		addECC(type.getECC());
		for (final SourceElement element : type.getSourceElements()) {
			addSourceElement(element);
		}
		addEndElement();
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
		addCommentAttribute(transition.getComment());
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

		addNameAndCommentAttribute(state);
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
				getWriter().writeAttribute(LibraryElementTags.ALGORITHM_ELEMENT, action.getAlgorithm());
			}
			if (action.getOutput() != null) {
				getWriter().writeAttribute(LibraryElementTags.OUTPUT_ATTRIBUTE,
						getActionOutputEventName(action.getOutput()));
			}
		}
	}

	private static String getActionOutputEventName(final Event event) {
		if (event.getBlockFBNetworkElement() instanceof AdapterFB) {
			return event.getBlockFBNetworkElement().getName() + "." + event.getName(); //$NON-NLS-1$
		}
		return event.getName();
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
		addVarList(type.getInternalConstVars(), LibraryElementTags.INTERNAL_CONST_VARS_ELEMENT);
		addSimpleECStates(type.getSimpleECStates());
		for (final SourceElement element : type.getSourceElements()) {
			addSourceElement(element);
		}
		addEndElement();
	}

	/**
	 * Adds the simple ec states.
	 *
	 * @param states the states
	 * @throws XMLStreamException
	 */
	private void addSimpleECStates(final List<SimpleECState> states) throws XMLStreamException {
		for (final SimpleECState state : states) {
			createSimpleECState(state);
		}
	}

	/**
	 * Creates the simple ec state.
	 *
	 * @param state the state
	 * @throws XMLStreamException
	 */
	private void createSimpleECState(final SimpleECState state) throws XMLStreamException {
		addStartElement(LibraryElementTags.ECSTATE_ELEMENT);

		addNameAndCommentAttribute(state);
		addSimpleECActions(state.getSimpleECActions());

		addEndElement();
	}

	/**
	 * Adds the simple ec actions.
	 *
	 * @param actions the actions
	 * @throws XMLStreamException
	 */
	private void addSimpleECActions(final List<SimpleECAction> actions) throws XMLStreamException {
		for (final SimpleECAction action : actions) {
			addEmptyStartElement(LibraryElementTags.ECACTION_ELEMENT);
			if (action.getAlgorithm() != null) {
				getWriter().writeAttribute(LibraryElementTags.ALGORITHM_ELEMENT, action.getAlgorithm());
			}
			if (action.getOutput() != null) {
				getWriter().writeAttribute(LibraryElementTags.OUTPUT_ATTRIBUTE,
						getActionOutputEventName(action.getOutput()));
			}
		}
	}

	private void addSourceElement(final SourceElement element) throws XMLStreamException {
		switch (element) {
		case final Algorithm alg -> addAlgorithm(alg);
		case final Method method -> addMethod(method);
		case final SourceComment comment -> addComment(comment);
		default -> throw new IllegalArgumentException("Unexpected value: " + element); //$NON-NLS-1$
		}
	}

	private void addAlgorithm(final Algorithm algorithm) throws XMLStreamException {
		addStartElement(LibraryElementTags.ALGORITHM_ELEMENT);
		addNameAndCommentAttribute(algorithm);
		writeSourceElement(algorithm);
		addEndElement();
	}

	private void addMethod(final Method method) throws XMLStreamException {
		addStartElement(LibraryElementTags.METHOD_ELEMENT);

		addNameAttribute(method.getName());
		addTypeAttribute(method.getReturnType());
		addCommentAttribute(method.getComment());

		writeSourceElement(method);
		if (method instanceof final TextMethod textMethod) {
			writeTextMethodParameters(textMethod);
		}
		addEndElement();
	}

	private void writeTextMethodParameters(final TextMethod method) throws XMLStreamException {
		addVarList(method.getInputParameters().stream().map(VarDeclaration.class::cast).toList(),
				LibraryElementTags.INPUT_VARS_ELEMENT);
		addVarList(method.getOutputParameters().stream().map(VarDeclaration.class::cast).toList(),
				LibraryElementTags.OUTPUT_VARS_ELEMENT);
		addVarList(method.getInOutParameters().stream().map(VarDeclaration.class::cast).toList(),
				LibraryElementTags.INOUT_VARS_ELEMENT);
	}

	private void addComment(final SourceComment comment) throws XMLStreamException {
		addStartElement(LibraryElementTags.COMMENT_ELEMENT);
		writeSourceElement(comment);
		addEndElement();
	}

	private void writeSourceElement(final SourceElement element) throws XMLStreamException {
		switch (element) {
		case final STSourceElement st -> writeSTSourceElement(st);
		case final OtherSourceElement other -> writeOtherSourceElement(other);
		default -> throw new IllegalArgumentException("Unexpected value: " + element); //$NON-NLS-1$
		}
	}

	private void writeSTSourceElement(final STSourceElement element) throws XMLStreamException {
		addStartElement(LibraryElementTags.ST_ELEMENT);
		writeText(element);
		addInlineEndElement();
	}

	private void writeOtherSourceElement(final OtherSourceElement element) throws XMLStreamException {
		addStartElement(LibraryElementTags.OTHER_ELEMENT);
		getWriter().writeAttribute(LibraryElementTags.LANGUAGE_ATTRIBUTE,
				(null != element.getLanguage()) ? element.getLanguage() : ""); //$NON-NLS-1$
		writeText(element);
		addInlineEndElement();
	}

	private void writeText(final TextSourceElement element) throws XMLStreamException {
		if (element.getText() != null) {
			writeCDataSection(element.getText());
		} else {
			getWriter().writeCharacters(""); //$NON-NLS-1$
		}
	}
}
