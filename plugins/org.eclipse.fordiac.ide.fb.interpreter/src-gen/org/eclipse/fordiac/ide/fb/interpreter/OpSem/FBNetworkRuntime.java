/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>FB Network Runtime</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getFbnetwork <em>Fbnetwork</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getTransferData <em>Transfer Data</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getTypeRuntimes <em>Type Runtimes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBNetworkRuntime()
 * @model
 * @generated */
public interface FBNetworkRuntime extends FBRuntimeAbstract {
	/** Returns the value of the '<em><b>Fbnetwork</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Fbnetwork</em>' reference.
	 * @see #setFbnetwork(FBNetwork)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBNetworkRuntime_Fbnetwork()
	 * @model required="true"
	 * @generated */
	FBNetwork getFbnetwork();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getFbnetwork
	 * <em>Fbnetwork</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Fbnetwork</em>' reference.
	 * @see #getFbnetwork()
	 * @generated */
	void setFbnetwork(FBNetwork value);

	/** Returns the value of the '<em><b>Transfer Data</b></em>' map. The key is of type
	 * {@link org.eclipse.fordiac.ide.model.libraryElement.Connection}, and the value is of type
	 * {@link org.eclipse.fordiac.ide.model.libraryElement.Value}, <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Transfer Data</em>' map.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBNetworkRuntime_TransferData()
	 * @model mapType="org.eclipse.fordiac.ide.fb.interpreter.OpSem.ConnectionToValueMap&lt;org.eclipse.fordiac.ide.model.libraryElement.Connection,
	 *        org.eclipse.fordiac.ide.model.libraryElement.Value&gt;"
	 * @generated */
	EMap<Connection, Value> getTransferData();

	/** Returns the value of the '<em><b>Type Runtimes</b></em>' map. The key is of type
	 * {@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement}, and the value is of type
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract}, <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the value of the '<em>Type Runtimes</em>' map.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBNetworkRuntime_TypeRuntimes()
	 * @model mapType="org.eclipse.fordiac.ide.fb.interpreter.OpSem.RuntimeMap&lt;org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement,
	 *        org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract&gt;"
	 * @generated */
	EMap<FBNetworkElement, FBRuntimeAbstract> getTypeRuntimes();

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" required="true"
	 * @generated */
	@Override
	FBNetwork getModel();

} // FBNetworkRuntime
