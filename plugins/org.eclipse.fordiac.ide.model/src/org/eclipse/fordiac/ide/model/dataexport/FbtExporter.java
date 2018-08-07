/********************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl, Matthias Plasch
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.Iterator;
import java.util.List;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The Class FbtExporter.
 */
class FbtExporter extends CommonElementExporter{


	/**
	 * Instantiates a new fbt exporter.
	 */
	public FbtExporter() {

	}

	/**
	 * Adds the fb type.
	 * 
	 * @param dom
	 *            the dom
	 * @param fb
	 *            the fb
	 */
	@Override
	protected void addType(final Document dom, final FBType fbType){
		Element rootElement = createRootElement(dom, fbType, LibraryElementTags.FB_TYPE);
		
		addCompileAbleTypeData(dom, rootElement, fbType);		
		addInterfaceList(dom, rootElement, fbType.getInterfaceList());
		if (fbType instanceof CompositeFBType) {
			rootElement.appendChild(new FBNetworkExporter(dom).createFBNetworkElement(((CompositeFBType) fbType).getFBNetwork()));
		} else {
			if (fbType instanceof BasicFBType) {
				addBasicFB(dom, rootElement, (BasicFBType) fbType);
			}
		}
		addService(dom, rootElement, fbType);
	}
	
	@Override
	protected FBType getType(PaletteEntry entry){
		return ((FBTypePaletteEntry)entry).getFBType();
	}

	/*
	 * <!ELEMENT BasicFB (InternalVars?,ECC?,Algorithm)>
	 */
	/**
	 * Adds the basic fb.
	 * 
	 * @param dom
	 *            the dom
	 * @param rootEle
	 *            the root ele
	 * @param type
	 *            the type
	 */
	private void addBasicFB(final Document dom, final Element rootEle,
			final BasicFBType type) {
		Element basicElement = dom.createElement(LibraryElementTags.BASIC_F_B_ELEMENT);
		addInternalVars(dom, basicElement, type.getInternalVars());
		addECC(dom, basicElement, type.getECC());
		addAlgorithm(dom, basicElement, type.getAlgorithm());
		rootEle.appendChild(basicElement);
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
	private void addAlgorithm(final Document dom,
			final Element basicElement, final List<Algorithm> algorithms) {
		for (Iterator<Algorithm> iter = algorithms.iterator(); iter.hasNext();) {
			Algorithm algorithm = iter.next();
			Element algorithmElement = dom.createElement(LibraryElementTags.ALGORITHM_ELEMENT);
			
			setNameAttribute(algorithmElement, algorithm.getName());
			setCommentAttribute(algorithmElement, algorithm);
			
			if (algorithm instanceof STAlgorithm) {
				addSTAlgorithm(dom, algorithmElement, (STAlgorithm) algorithm);
			} else if (algorithm instanceof OtherAlgorithm) {
				addOtherAlgorithm(dom, algorithmElement,
						(OtherAlgorithm) algorithm);
			}
			// TODO: add all Algorithms

			basicElement.appendChild(algorithmElement);
		}
	}

	/**
	 * Adds the other algorithm.
	 * 
	 * @param dom
	 *            the dom
	 * @param algorithmElement
	 *            the algorithm element
	 * @param algorithm
	 *            the algorithm
	 */
	private static void addOtherAlgorithm(final Document dom,
			final Element algorithmElement, final OtherAlgorithm algorithm) {
		Element st = dom.createElement(LibraryElementTags.OTHER_ELEMENT);
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
	private static void addSTAlgorithm(final Document dom,
			final Element algorithmElement, final STAlgorithm algorithm) {
		Element st = dom.createElement(LibraryElementTags.ST_ELEMENT);
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
	 * @param dom
	 *            the dom
	 * @param basicElement
	 *            the basic element
	 * @param ecc
	 *            the ecc
	 */
	private void addECC(final Document dom, final Element basicElement,
			final ECC ecc) {
		Element eccElement = dom.createElement(LibraryElementTags.ECC_ELEMENT);
		if (ecc != null) {
			addECStates(dom, eccElement, ecc.getECState(), ecc.getStart());
			addECTransitions(dom, eccElement, ecc);
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
	/**
	 * Adds the ec transitions.
	 * 
	 * @param dom
	 *            the dom
	 * @param eccElement
	 *            the ecc element
	 * @param ecc
	 *            the ecc
	 */
	private void addECTransitions(final Document dom,
			final Element eccElement, final ECC ecc) {
		for (Iterator<ECTransition> iter = ecc.getECTransition().iterator(); iter
				.hasNext();) {
			ECTransition transition = iter.next();
			addTransition(dom, eccElement, transition);
		}
	}

	/**
	 * Adds the transition.
	 * 
	 * @param dom
	 *            the dom
	 * @param eccElement
	 *            the ecc element
	 * @param transition
	 *            the transition
	 */
	private void addTransition(final Document dom,
			final Element eccElement, final ECTransition transition) {

		Element transElement = dom.createElement(LibraryElementTags.ECTRANSITION_ELEMENT);
		transElement.setAttribute(LibraryElementTags.SOURCE_ATTRIBUTE, transition.getSource().getName());
		transElement.setAttribute(LibraryElementTags.DESTINATION_ATTRIBUTE, transition.getDestination()
				.getName());
		transElement.setAttribute(LibraryElementTags.CONDITION_ATTRIBUTE, transition.getConditionText());
		transElement.setAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, transition.getComment());
		
		transElement.setAttribute(LibraryElementTags.X_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(transition.getX()).toString());
		transElement.setAttribute(LibraryElementTags.Y_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(transition.getY()).toString());
		eccElement.appendChild(transElement);
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
	 * @param dom
	 *            the dom
	 * @param eccElement
	 *            the ecc element
	 * @param states
	 *            the states
	 * @param startState
	 *            the start state
	 */
	private void addECStates(final Document dom,
			final Element eccElement, final List<ECState> states,
			final ECState startState) {
		Element stateElement = createECState(dom, startState);
		if (stateElement != null) {
			eccElement.appendChild(stateElement);
		}
		for (Iterator<ECState> iter = states.iterator(); iter.hasNext();) {
			ECState state = iter.next();
			if (!state.equals(startState)) {
				stateElement = createECState(dom, state);
				if (stateElement != null) {
					eccElement.appendChild(stateElement);
				}
			}
		}

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
	private Element createECState(final Document dom, final ECState state) {
		if (state != null) {
			Element stateElement = dom.createElement(LibraryElementTags.ECSTATE_ELEMENT);
			
			setNameAttribute(stateElement, state.getName());
			setCommentAttribute(stateElement, state);
			
			stateElement.setAttribute(LibraryElementTags.X_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(state.getX()).toString());
			stateElement.setAttribute(LibraryElementTags.Y_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(state.getY()).toString());

			addECActions(dom, stateElement, state.getECAction());

			return stateElement;
		}
		return null;
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
	private static void addECActions(final Document dom,
			final Element stateElement, final List<ECAction> actions) {
		for (Iterator<ECAction> iter = actions.iterator(); iter.hasNext();) {
			ECAction action = iter.next();
			Element actionElement = dom.createElement(LibraryElementTags.ECACTION_ELEMENT);
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
	 * Adds the internal vars.
	 * 
	 * @param dom
	 *            the dom
	 * @param basicElement
	 *            the basic element
	 * @param internalVars
	 *            the internal vars
	 */
	private void addInternalVars(final Document dom,
			final Element basicElement, final List<VarDeclaration> internalVars) {
		Iterator<VarDeclaration> iter = internalVars.iterator();
		if (iter.hasNext()) {
			Element internalVarsElement = dom.createElement(LibraryElementTags.INTERNAL_VARS_ELEMENT);
			while (iter.hasNext()) {
				VarDeclaration internalVar = iter.next();
				addVariable(dom, internalVarsElement,
						internalVar);
			}
			basicElement.appendChild(internalVarsElement);
		}
	}

	

}
