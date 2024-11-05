/********************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Johannes Keppler University, Linz,
 *                          Primetals Technologies Austria GmbH
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
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OtherMethod;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.TextMethod;
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
		for (final ICallable callable : type.getCallables()) {
			addICallable(callable);
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

		writeAlgorithmText(algorithm.getText());
		addInlineEndElement();
	}

	private void writeAlgorithmText(final String text) throws XMLStreamException {
		if (null != text) {
			writeCDataSection(text);
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
		writeAlgorithmText(algorithm.getText());
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
				getWriter().writeAttribute(LibraryElementTags.ALGORITHM_ELEMENT, action.getAlgorithm().getName());
			}
			if (action.getOutput() != null) {
				getWriter().writeAttribute(LibraryElementTags.OUTPUT_ATTRIBUTE,
						getActionOutputEventName(action.getOutput()));
			}
		}
	}

	private static String getActionOutputEventName(final Event event) {
		if (event.getFBNetworkElement() instanceof AdapterFB) {
			return event.getFBNetworkElement().getName() + "." + event.getName(); //$NON-NLS-1$
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
		for (final ICallable callable : type.getCallables()) {
			addICallable(callable);
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

	/**
	 * Adds the algorithm.
	 *
	 * @param algorithms the algorithms
	 * @throws XMLStreamException
	 */
	private void addAlgorithm(final Algorithm algorithm) throws XMLStreamException {
		addStartElement(LibraryElementTags.ALGORITHM_ELEMENT);

		addNameAndCommentAttribute(algorithm);

		if (algorithm instanceof final STAlgorithm stAlg) {
			addSTAlgorithm(stAlg);
		} else if (algorithm instanceof final OtherAlgorithm oAlg) {
			addOtherAlgorithm(oAlg);
		}
		addEndElement();
	}

	/**
	 * Adds the method.
	 *
	 * @param method the method
	 * @throws XMLStreamException
	 */
	private void addICallable(final ICallable callable) throws XMLStreamException {
		if (callable instanceof final Algorithm alg) {
			addAlgorithm(alg);
		} else if (callable instanceof final Method method) {
			addMethod(method);
		}
	}

	/**
	 * Adds the method.
	 *
	 * @param method the method
	 * @throws XMLStreamException
	 */
	private void addMethod(final Method method) throws XMLStreamException {
		addStartElement(LibraryElementTags.METHOD_ELEMENT);

		addNameAttribute(method.getName());
		addTypeAttribute(method.getReturnType());
		addCommentAttribute(method.getComment());

		if (method instanceof final STMethod stMethod) {
			addSTMethod(stMethod);
		} else if (method instanceof final OtherMethod oMethod) {
			addOtherMethod(oMethod);
		}
		addEndElement();
	}

	/**
	 * Adds the st method.
	 *
	 * @param method the method
	 * @throws XMLStreamException
	 */
	private void addSTMethod(final STMethod method) throws XMLStreamException {
		addStartElement(LibraryElementTags.ST_ELEMENT);
		writeAlgorithmText(method.getText());
		addInlineEndElement();
		writeTextMethodParameters(method);
	}

	/**
	 * Adds the other method.
	 *
	 * @param method the method
	 * @throws XMLStreamException
	 */
	private void addOtherMethod(final OtherMethod method) throws XMLStreamException {
		addStartElement(LibraryElementTags.OTHER_ELEMENT);
		getWriter().writeAttribute(LibraryElementTags.LANGUAGE_ATTRIBUTE,
				(null != method.getLanguage()) ? method.getLanguage() : ""); //$NON-NLS-1$

		writeAlgorithmText(method.getText());
		addInlineEndElement();
		writeTextMethodParameters(method);
	}

	private void writeTextMethodParameters(final TextMethod method) throws XMLStreamException {
		addVarList(method.getInputParameters().stream().map(VarDeclaration.class::cast).toList(),
				LibraryElementTags.INPUT_VARS_ELEMENT);
		addVarList(method.getOutputParameters().stream().map(VarDeclaration.class::cast).toList(),
				LibraryElementTags.OUTPUT_VARS_ELEMENT);
		addVarList(method.getInOutParameters().stream().map(VarDeclaration.class::cast).toList(),
				LibraryElementTags.INOUT_VARS_ELEMENT);
	}
}
