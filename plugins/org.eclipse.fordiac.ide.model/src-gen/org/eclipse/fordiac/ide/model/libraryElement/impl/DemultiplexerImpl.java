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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
	 *
	 * @generated
	 */
	protected DemultiplexerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.DEMULTIPLEXER;
	}

	@Override
	public void setAttribute(String attributeName, String type, String value, String comment) {
		super.setAttribute(attributeName, type, value, comment);
		if ("VisibleChildren".equals(attributeName)) { //$NON-NLS-1$
			setMemberVariablesAsPorts(null);
			List<String> visibleChildrenNames = Arrays.asList(value.trim().split(",")); //$NON-NLS-1$
			setMemberVariablesAsPort(getVarDeclarations(visibleChildrenNames));
		}
	}

	private Collection<VarDeclaration> getVarDeclarations(List<String> varDeclNames) {
		List<VarDeclaration> vars = new ArrayList<>();
		varDeclNames.forEach(name -> {
			VarDeclaration varDecl = EcoreUtil.copy(findVarDeclarationInStruct(structType, name));
			if (null != varDecl) {
				varDecl.setName(name);
				vars.add(varDecl);
			}
		});
		return vars;
	}

	private static VarDeclaration findVarDeclarationInStruct(StructuredType struct, String name) {
		String[] subnames = name.split("\\."); //$NON-NLS-1$
		List<VarDeclaration> members = struct.getMemberVariables();
		VarDeclaration found = null;
		for (String subname : subnames) { //
			Object[] findings = members.stream().filter(var -> var.getName().equals(subname)).toArray();
			if (findings.length > 0) {
				found = (VarDeclaration) findings[0];
			}
			if ((null != found) && (found.getType() instanceof StructuredType)) {
				members = ((StructuredType) found.getType()).getMemberVariables();
			}
		}
		return found;
	}

	/**
	 * sets all member variables of the passed StructuredType as output ports
	 *
	 */
	@Override
	protected void setMemberVariablesAsPorts(StructuredType newStructType) {
		// create member variables of struct as data output ports
		getInterface().getOutputVars().clear();
		if (null != newStructType) {
			Collection<VarDeclaration> list = EcoreUtil.copyAll(newStructType.getMemberVariables());
			setMemberVariablesAsPort(list);
			getInterface().getInputVars().get(0).setType(newStructType); // there should be only one output
		}
	}

	private void setMemberVariablesAsPort(Collection<VarDeclaration> list) {
		list.forEach(varDecl -> {
			varDecl.setIsInput(false);
			if (null != varDecl.getValue()) {
				// if we have a value set it empty to get rid of default values from the struct
				// type
				varDecl.getValue().setValue(""); //$NON-NLS-1$
			}
		});
		Event ev = getInterface().getEventOutputs().get(0);

		// create with constructs
		list.forEach(varDecl -> {
			With with = LibraryElementFactory.eINSTANCE.createWith();
			with.setVariables(varDecl);
			ev.getWith().add(with);
		});

		// add data output ports to the interface
		getInterface().getOutputVars().addAll(list);
	}

} // DemultiplexerImpl
