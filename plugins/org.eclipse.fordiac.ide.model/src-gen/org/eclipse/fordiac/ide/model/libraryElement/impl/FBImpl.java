/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/** <!-- begin-user-doc --> An implementation of the model object ' <em><b>FB</b></em>'. <!-- end-user-doc -->
 *
 * @generated */
public class FBImpl extends FBNetworkElementImpl implements FB {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	protected FBImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.FB;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public boolean isResourceTypeFB() {
		return org.eclipse.fordiac.ide.model.Annotations.isResourceTypeFB(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public boolean isResourceFB() {
		return org.eclipse.fordiac.ide.model.Annotations.isResourceFB(this);
	}

} // FBImpl
