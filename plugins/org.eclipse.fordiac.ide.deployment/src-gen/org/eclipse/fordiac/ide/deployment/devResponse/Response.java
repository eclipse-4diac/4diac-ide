/**
 * ******************************************************************************
 * * Copyright (c) 2012, 2013, 2018 Profactor GmbH, fortiss GmbH, Johannes Kepler University
 * * 
 * * All rights reserved. This program and the accompanying materials
 * * are made available under the terms of the Eclipse Public License v1.0
 * * which accompanies this distribution, and is available at
 * * http://www.eclipse.org/legal/epl-v10.html
 * *
 * * Contributors:
 * *   Gerhard Ebenhofer, Alois Zoitl
 * *     - initial API and implementation and/or initial documentation
 * *   Alois Zoitl - moved to deployment and reworked it to a device response model
 * ******************************************************************************
 * 
 */
package org.eclipse.fordiac.ide.deployment.devResponse;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Response</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getID <em>ID</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getWatches <em>Watches</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getReason <em>Reason</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getFblist <em>Fblist</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getEndpointlist <em>Endpointlist</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage#getResponse()
 * @model
 * @generated
 */
public interface Response extends EObject {
	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ID</em>' attribute.
	 * @see #setID(String)
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage#getResponse_ID()
	 * @model
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getID <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

	/**
	 * Returns the value of the '<em><b>Watches</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Watches</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Watches</em>' reference.
	 * @see #setWatches(Watches)
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage#getResponse_Watches()
	 * @model
	 * @generated
	 */
	Watches getWatches();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getWatches <em>Watches</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Watches</em>' reference.
	 * @see #getWatches()
	 * @generated
	 */
	void setWatches(Watches value);

	/**
	 * Returns the value of the '<em><b>Reason</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reason</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reason</em>' attribute.
	 * @see #setReason(String)
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage#getResponse_Reason()
	 * @model
	 * @generated
	 */
	String getReason();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getReason <em>Reason</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reason</em>' attribute.
	 * @see #getReason()
	 * @generated
	 */
	void setReason(String value);

	/**
	 * Returns the value of the '<em><b>Fblist</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fblist</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fblist</em>' reference.
	 * @see #setFblist(FBList)
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage#getResponse_Fblist()
	 * @model
	 * @generated
	 */
	FBList getFblist();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getFblist <em>Fblist</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fblist</em>' reference.
	 * @see #getFblist()
	 * @generated
	 */
	void setFblist(FBList value);

	/**
	 * Returns the value of the '<em><b>Endpointlist</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Endpointlist</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Endpointlist</em>' reference.
	 * @see #setEndpointlist(EndpointList)
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage#getResponse_Endpointlist()
	 * @model
	 * @generated
	 */
	EndpointList getEndpointlist();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getEndpointlist <em>Endpointlist</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Endpointlist</em>' reference.
	 * @see #getEndpointlist()
	 * @generated
	 */
	void setEndpointlist(EndpointList value);

} // Response
