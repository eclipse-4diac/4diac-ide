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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Event Occurrence</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getEvent <em>Event</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isActive <em>Active</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isIgnored <em>Ignored</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getFbRuntime <em>Fb Runtime</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getCreatedTransactions <em>Created
 * Transactions</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getParentFB <em>Parent FB</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getResultFBRuntime <em>Result FB
 * Runtime</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventOccurrence()
 * @model annotation="http://www.eclipse.org/emf/2002/GenModel"
 * @generated */
public interface EventOccurrence extends EObject {
	/** Returns the value of the '<em><b>Event</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Event</em>' reference.
	 * @see #setEvent(Event)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventOccurrence_Event()
	 * @model required="true"
	 * @generated */
	Event getEvent();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getEvent
	 * <em>Event</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Event</em>' reference.
	 * @see #getEvent()
	 * @generated */
	void setEvent(Event value);

	/** Returns the value of the '<em><b>Active</b></em>' attribute. The default value is <code>"true"</code>. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Active</em>' attribute.
	 * @see #setActive(boolean)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventOccurrence_Active()
	 * @model default="true"
	 * @generated */
	boolean isActive();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isActive
	 * <em>Active</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Active</em>' attribute.
	 * @see #isActive()
	 * @generated */
	void setActive(boolean value);

	/** Returns the value of the '<em><b>Ignored</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Ignored</em>' attribute.
	 * @see #setIgnored(boolean)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventOccurrence_Ignored()
	 * @model
	 * @generated */
	boolean isIgnored();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isIgnored
	 * <em>Ignored</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Ignored</em>' attribute.
	 * @see #isIgnored()
	 * @generated */
	void setIgnored(boolean value);

	/** Returns the value of the '<em><b>Fb Runtime</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Fb Runtime</em>' containment reference.
	 * @see #setFbRuntime(FBRuntimeAbstract)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventOccurrence_FbRuntime()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated */
	FBRuntimeAbstract getFbRuntime();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getFbRuntime <em>Fb
	 * Runtime</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Fb Runtime</em>' containment reference.
	 * @see #getFbRuntime()
	 * @generated */
	void setFbRuntime(FBRuntimeAbstract value);

	/** Returns the value of the '<em><b>Created Transactions</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction}. It is bidirectional and its opposite is
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getParentEO <em>Parent EO</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Created Transactions</em>' reference list.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventOccurrence_CreatedTransactions()
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getParentEO
	 * @model opposite="parentEO"
	 * @generated */
	EList<Transaction> getCreatedTransactions();

	/** Returns the value of the '<em><b>Parent FB</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Parent FB</em>' reference.
	 * @see #setParentFB(FBNetworkElement)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventOccurrence_ParentFB()
	 * @model
	 * @generated */
	FBNetworkElement getParentFB();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getParentFB
	 * <em>Parent FB</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Parent FB</em>' reference.
	 * @see #getParentFB()
	 * @generated */
	void setParentFB(FBNetworkElement value);

	/** Returns the value of the '<em><b>Result FB Runtime</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Result FB Runtime</em>' containment reference.
	 * @see #setResultFBRuntime(FBRuntimeAbstract)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventOccurrence_ResultFBRuntime()
	 * @model containment="true" resolveProxies="true"
	 * @generated */
	FBRuntimeAbstract getResultFBRuntime();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getResultFBRuntime
	 * <em>Result FB Runtime</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Result FB Runtime</em>' containment reference.
	 * @see #getResultFBRuntime()
	 * @generated */
	void setResultFBRuntime(FBRuntimeAbstract value);

} // EventOccurrence
