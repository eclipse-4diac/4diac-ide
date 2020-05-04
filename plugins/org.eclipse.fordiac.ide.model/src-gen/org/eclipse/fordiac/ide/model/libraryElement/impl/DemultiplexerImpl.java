/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Demultiplexer</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public class DemultiplexerImpl extends StructManipulatorImpl implements Demultiplexer {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected DemultiplexerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.DEMULTIPLEXER;
	}

	@Override
	protected void setMemberVariablesAsPorts(StructuredType newStructType) {
		// create member variables of struct as data output ports
		getInterface().getOutputVars().clear();
		Collection<VarDeclaration> list = EcoreUtil.copyAll(newStructType.getMemberVariables());
		list.forEach(varDecl -> varDecl.setIsInput(false));
		Event ev = getInterface().getEventOutputs().get(0);

		// create with constructs
		list.forEach(varDecl -> {
			With with = LibraryElementFactory.eINSTANCE.createWith();
			with.setVariables(varDecl);
			ev.getWith().add(with);
		});

		// add data output ports to the interface
		getInterface().getOutputVars().addAll(list);
		getInterface().getInputVars().get(0).setType(newStructType); // there should be only one output
		setAttribute("StructuredType", "STRING", getStructType().getName(), COMMENT_EDEFAULT); //$NON-NLS-1$ //$NON-NLS-2$
	}

} // DemultiplexerImpl
