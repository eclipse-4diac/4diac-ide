/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDx1 <em>Dx1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDx2 <em>Dx2</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDy <em>Dy</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isResTypeConnection <em>Res Type Connection</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isBrokenConnection <em>Broken Connection</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDestination <em>Destination</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection()
 * @model abstract="true"
 * @generated
 */
public interface Connection extends ConfigurableObject {
	/**
	 * Returns the value of the '<em><b>Dx1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dx1</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dx1</em>' attribute.
	 * @see #setDx1(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_Dx1()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='attribute' name='dx1'"
	 * @generated
	 */
	int getDx1();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDx1 <em>Dx1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dx1</em>' attribute.
	 * @see #getDx1()
	 * @generated
	 */
	void setDx1(int value);

	/**
	 * Returns the value of the '<em><b>Dx2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dx2</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dx2</em>' attribute.
	 * @see #setDx2(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_Dx2()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='attribute' name='dx2'"
	 * @generated
	 */
	int getDx2();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDx2 <em>Dx2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dx2</em>' attribute.
	 * @see #getDx2()
	 * @generated
	 */
	void setDx2(int value);

	/**
	 * Returns the value of the '<em><b>Dy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dy</em>' attribute.
	 * @see #setDy(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_Dy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='attribute' name='dy'"
	 * @generated
	 */
	int getDy();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDy <em>Dy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dy</em>' attribute.
	 * @see #getDy()
	 * @generated
	 */
	void setDy(int value);

	/**
	 * Returns the value of the '<em><b>Res Type Connection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Res Type Connection</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Res Type Connection</em>' attribute.
	 * @see #setResTypeConnection(boolean)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_ResTypeConnection()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isResTypeConnection();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isResTypeConnection <em>Res Type Connection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Res Type Connection</em>' attribute.
	 * @see #isResTypeConnection()
	 * @generated
	 */
	void setResTypeConnection(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Connection</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.Annotations.isResourceConnection(this);'"
	 * @generated
	 */
	boolean isResourceConnection();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.Annotations.getFBNetwork(this);'"
	 * @generated
	 */
	FBNetwork getFBNetwork();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='org.eclipse.fordiac.ide.model.Annotations.checkifConnectionBroken(this);'"
	 * @generated
	 */
	void checkIfConnectionBroken();

	/**
	 * Returns the value of the '<em><b>Broken Connection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Broken Connection</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Broken Connection</em>' attribute.
	 * @see #setBrokenConnection(boolean)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_BrokenConnection()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isBrokenConnection();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isBrokenConnection <em>Broken Connection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Broken Connection</em>' attribute.
	 * @see #isBrokenConnection()
	 * @generated
	 */
	void setBrokenConnection(boolean value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getOutputConnections <em>Output Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(IInterfaceElement)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_Source()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getOutputConnections
	 * @model opposite="outputConnections"
	 * @generated
	 */
	IInterfaceElement getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(IInterfaceElement value);

	/**
	 * Returns the value of the '<em><b>Destination</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getInputConnections <em>Input Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination</em>' reference.
	 * @see #setDestination(IInterfaceElement)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_Destination()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getInputConnections
	 * @model opposite="inputConnections"
	 * @generated
	 */
	IInterfaceElement getDestination();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDestination <em>Destination</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination</em>' reference.
	 * @see #getDestination()
	 * @generated
	 */
	void setDestination(IInterfaceElement value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.Annotations.getSourceElement(this);'"
	 * @generated
	 */
	FBNetworkElement getSourceElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.Annotations.getDestinationElement(this);'"
	 * @generated
	 */
	FBNetworkElement getDestinationElement();

} // Connection
