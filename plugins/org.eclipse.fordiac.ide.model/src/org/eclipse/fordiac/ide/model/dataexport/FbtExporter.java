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

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OtherMethod;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.TextMethod;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;

/**
 * The Class FbtExporter.
 */
class FbtExporter extends AbstractBlockTypeExporter {

	/**
	 * Instantiates a new fbt exporter.
	 *
	 * @param entry
	 */
	FbtExporter(final FBTypeEntry entry) {
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
		for (final ICallable callable : type.getCallables()) {
			addICallable(callable);
		}
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

	/** Adds the method.
	 *
	 * @param method the method
	 * @throws XMLStreamException */
	private void addICallable(final ICallable callable) throws XMLStreamException {
		if (callable instanceof Algorithm) {
			addAlgorithm((Algorithm) callable);
		} else if (callable instanceof Method) {
			addMethod((Method) callable);
		}
	}

	/** Adds the method.
	 *
	 * @param method the method
	 * @throws XMLStreamException */
	private void addMethod(final Method method) throws XMLStreamException {
		addStartElement(LibraryElementTags.METHOD_ELEMENT);

		addNameAttribute(method.getName());
		addTypeAttribute(method.getReturnType());
		addCommentAttribute(method);

		if (method instanceof STMethod) {
			addSTMethod((STMethod) method);
		} else if (method instanceof OtherMethod) {
			addOtherMethod((OtherMethod) method);
		}
		addEndElement();
	}

	/** Adds the st method.
	 *
	 * @param method the method
	 * @throws XMLStreamException */
	private void addSTMethod(final STMethod method) throws XMLStreamException {
		addStartElement(LibraryElementTags.ST_ELEMENT);
		writeAlgorithmText(method.getText());
		addInlineEndElement();
		writeTextMethodParameters(method);
	}

	/** Adds the other method.
	 *
	 * @param method the method
	 * @throws XMLStreamException */
	private void addOtherMethod(final OtherMethod method) throws XMLStreamException {
		addStartElement(LibraryElementTags.OTHER_ELEMENT);
		getWriter().writeAttribute(LibraryElementTags.LANGUAGE_ATTRIBUTE,
				(null != method.getLanguage()) ? method.getLanguage() : ""); //$NON-NLS-1$

		writeAlgorithmText(method.getText());
		addInlineEndElement();
		writeTextMethodParameters(method);
	}

	private void writeTextMethodParameters(final TextMethod method) throws XMLStreamException {
		for (final INamedElement element : method.getInputParameters()) {
			addParameter(element);
		}
		for (final INamedElement element : method.getOutputParameters()) {
			addParameter(element);
		}
		for (final INamedElement element : method.getInOutParameters()) {
			addParameter(element);
		}
	}

	private void addParameter(final INamedElement element) throws XMLStreamException {
		if (element instanceof VarDeclaration) {
			addVarDeclaration((VarDeclaration) element);
		}
	}

}
