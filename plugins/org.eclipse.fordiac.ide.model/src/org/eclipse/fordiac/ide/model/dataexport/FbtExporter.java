/********************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2018 Johannes Keppler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl, Matthias Plasch
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - Refactored class hierarchy of xml exporters  
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.List;

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
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.w3c.dom.Element;

/**
 * The Class FbtExporter.
 */
class FbtExporter extends AbstractTypeExporter{

	
	/**
	 * Instantiates a new fbt exporter.
	 * @param entry 
	 */
	FbtExporter(FBTypePaletteEntry entry) {
		super(entry.getFBType());
	}
	
	@Override
	protected String getRootTag() {
		return  LibraryElementTags.FB_TYPE;
	}
	
	@Override
	protected void createTypeSpecificXMLEntries(Element rootElement){
		if (getType() instanceof CompositeFBType) {
			FBNetworkExporter nwExporter = new FBNetworkExporter(getDom());
			rootElement.appendChild(nwExporter.createFBNetworkElement(((CompositeFBType) getType()).getFBNetwork()));
		} else if (getType() instanceof BasicFBType) {
			addBasicFB(rootElement, (BasicFBType) getType());
		} else if(getType() instanceof SimpleFBType) {
			addSimpleFB(rootElement, (SimpleFBType) getType());
		}
	}
	
	/*
	 * <!ELEMENT BasicFB (InternalVars?,ECC?,Algorithm)>
	 */
	/**
	 * Adds the basic fb.
	 * 
	 * @param rootEle
	 *            the root ele
	 * @param type
	 *            the type
	 */
	private void addBasicFB(final Element rootEle, final BasicFBType type) {
		Element basicElement = createElement(LibraryElementTags.BASIC_F_B_ELEMENT);
		addVarList(basicElement, type.getInternalVars(), LibraryElementTags.INTERNAL_VARS_ELEMENT);
		addECC(basicElement, type.getECC());
		type.getAlgorithm().forEach(alg -> addAlgorithm(basicElement, alg));
		rootEle.appendChild(basicElement);
	}

	

	/**
	 * Adds the other algorithm.
	 * 
	 * @param algorithmElement
	 *            the algorithm element
	 * @param algorithm
	 *            the algorithm
	 */
	private void addOtherAlgorithm(final Element algorithmElement, final OtherAlgorithm algorithm) {
		Element st = createElement(LibraryElementTags.OTHER_ELEMENT);
		if (algorithm.getLanguage() != null) {
			st.setAttribute(LibraryElementTags.LANGUAGE_ATTRIBUTE, algorithm.getLanguage());
		} else {
			st.setAttribute(LibraryElementTags.LANGUAGE_ATTRIBUTE, ""); //$NON-NLS-1$
		}
		if (algorithm.getText() != null) {
			st.setAttribute(LibraryElementTags.TEXT_ATTRIBUTE, algorithm.getText());
		} else {
			st.setAttribute(LibraryElementTags.TEXT_ATTRIBUTE, ""); //$NON-NLS-1$
		}
		algorithmElement.appendChild(st);
	}

	/**
	 * Adds the st algorithm.
	 * 
	 * @param dom
	 *            the dom
	 * @param algorithmElement
	 *            the algorithm element
	 * @param algorithm
	 *            the algorithm
	 */
	private void addSTAlgorithm(final Element algorithmElement, final STAlgorithm algorithm) {
		Element st = createElement(LibraryElementTags.ST_ELEMENT);
		if (algorithm.getText() != null) {
			st.setAttribute(LibraryElementTags.TEXT_ATTRIBUTE, algorithm.getText());
		} else {
			st.setAttribute(LibraryElementTags.TEXT_ATTRIBUTE, ""); //$NON-NLS-1$
		}
		algorithmElement.appendChild(st);
	}

	/*
	 * <!ELEMENT ECC (ECState+,ECTransition+) >
	 */
	/**
	 * Adds the ecc.
	 * 
	 * @param basicElement
	 *            the basic element
	 * @param ecc
	 *            the ecc
	 */
	private void addECC(final Element basicElement,
			final ECC ecc) {
		Element eccElement = createElement(LibraryElementTags.ECC_ELEMENT);
		if (ecc != null) {
			addECStates(eccElement, ecc.getECState(), ecc.getStart());
			ecc.getECTransition().forEach(transition -> eccElement.appendChild(createTransitionEntry(transition)) );
		}
		basicElement.appendChild(eccElement);
	}

	/*
	 * 
	 * <!ELEMENT ECTransition EMPTY>
	 * 
	 * <!ATTLIST ECTransition Source CDATA #REQUIRED Destination CDATA #REQUIRED
	 * Condition CDATA #REQUIRED Comment CDATA #IMPLIED x CDATA #IMPLIED y CDATA
	 * #IMPLIED >
	 */

	/** Create a transition entry for the dom
	 * 
	 * @param transition
	 *            the transition
	 */
	private Element createTransitionEntry(final ECTransition transition) {
		Element transElement = createElement(LibraryElementTags.ECTRANSITION_ELEMENT);
		transElement.setAttribute(LibraryElementTags.SOURCE_ATTRIBUTE, transition.getSource().getName());
		transElement.setAttribute(LibraryElementTags.DESTINATION_ATTRIBUTE, transition.getDestination()
				.getName());
		transElement.setAttribute(LibraryElementTags.CONDITION_ATTRIBUTE, transition.getConditionText());
		transElement.setAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, transition.getComment());
		
		transElement.setAttribute(LibraryElementTags.X_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(transition.getX()).toString());
		transElement.setAttribute(LibraryElementTags.Y_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(transition.getY()).toString());
		
		return transElement;
	}

	/*
	 * <!ELEMENT ECState (ECAction)>
	 * 
	 * <!ATTLIST ECState Name CDATA #REQUIRED Comment CDATA #IMPLIED x CDATA
	 * #IMPLIED y CDATA #IMPLIED >
	 */
	/**
	 * Adds the ec states.
	 * 
	 * @param eccElement
	 *            the ecc element
	 * @param states
	 *            the states
	 * @param startState
	 *            the start state
	 */
	private void addECStates(final Element eccElement, final List<ECState> states,
			final ECState startState) {
		eccElement.appendChild(createECState(startState));
		states.forEach(state -> {
			if (!state.equals(startState)) {
				eccElement.appendChild(createECState(state));
			}
		});

	}

	/**
	 * Creates the ec state.
	 * 
	 * @param dom
	 *            the dom
	 * @param state
	 *            the state
	 * 
	 * @return the element
	 */
	private Element createECState(final ECState state) {
		Element stateElement = createElement(LibraryElementTags.ECSTATE_ELEMENT);
		
		setNameAttribute(stateElement, state.getName());
		setCommentAttribute(stateElement, state);
		
		stateElement.setAttribute(LibraryElementTags.X_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(state.getX()).toString());
		stateElement.setAttribute(LibraryElementTags.Y_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(state.getY()).toString());

		addECActions(stateElement, state.getECAction());

		return stateElement;
	}

	/*
	 * <!ELEMENT ECAction EMPTY>
	 * 
	 * <!ATTLIST ECAction Algorithm CDATA #IMPLIED Output CDATA #IMPLIED >
	 */
	/**
	 * Adds the ec actions.
	 * 
	 * @param dom
	 *            the dom
	 * @param stateElement
	 *            the state element
	 * @param actions
	 *            the actions
	 */
	private void addECActions(final Element stateElement, final List<ECAction> actions) {
		for (ECAction action : actions) {
			Element actionElement = createElement(LibraryElementTags.ECACTION_ELEMENT);
			if (action.getAlgorithm() != null) {
				actionElement.setAttribute(LibraryElementTags.ALGORITHM_ELEMENT, action.getAlgorithm()
						.getName());
			}
			if (action.getOutput() != null) {
				actionElement.setAttribute(LibraryElementTags.OUTPUT_ATTRIBUTE, action.getOutput()
						.getName());
			}
			stateElement.appendChild(actionElement);
		}
	}
	
	/**
	 * Adds the simple fb.
	 * 
	 * @param dom
	 *            the dom
	 * @param rootEle
	 *            the root ele
	 * @param type
	 *            the type
	 */
	private void addSimpleFB(final Element rootEle, final SimpleFBType type) {
		Element simpleElement = createElement(LibraryElementTags.SIMPLE_F_B_ELEMENT);
		addVarList(simpleElement, type.getInternalVars(), LibraryElementTags.INTERNAL_VARS_ELEMENT);
		addAlgorithm(simpleElement, type.getAlgorithm());
		rootEle.appendChild(simpleElement);
	}

	/**
	 * Adds the algorithm.
	 * 
	 * @param dom
	 *            the dom
	 * @param basicElement
	 *            the basic element
	 * @param algorithms
	 *            the algorithms
	 */
	private void addAlgorithm(final Element basicElement, final Algorithm algorithm) {
		Element algorithmElement = createElement(LibraryElementTags.ALGORITHM_ELEMENT);
		
		setNameAttribute(algorithmElement, algorithm.getName());
		setCommentAttribute(algorithmElement, algorithm);
		
		if (algorithm instanceof STAlgorithm) {
			addSTAlgorithm(algorithmElement, (STAlgorithm) algorithm);
		} else if (algorithm instanceof OtherAlgorithm) {
			addOtherAlgorithm(algorithmElement,
					(OtherAlgorithm) algorithm);
		}
		
		basicElement.appendChild(algorithmElement);
	}

}
