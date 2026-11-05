/**
 * *******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.bulkeditor.query.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FieldConstraintImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FieldConstraintImpl#isCaseSensitive <em>Case Sensitive</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FieldConstraintImpl#isWholeWord <em>Whole Word</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FieldConstraintImpl#isEntire <em>Entire</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FieldConstraintImpl#isRegex <em>Regex</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FieldConstraintImpl extends EObjectImpl implements FieldConstraint {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isCaseSensitive() <em>Case Sensitive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCaseSensitive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASE_SENSITIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCaseSensitive() <em>Case Sensitive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCaseSensitive()
	 * @generated
	 * @ordered
	 */
	protected boolean caseSensitive = CASE_SENSITIVE_EDEFAULT;

	/**
	 * The default value of the '{@link #isWholeWord() <em>Whole Word</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWholeWord()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WHOLE_WORD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWholeWord() <em>Whole Word</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWholeWord()
	 * @generated
	 * @ordered
	 */
	protected boolean wholeWord = WHOLE_WORD_EDEFAULT;

	/**
	 * The default value of the '{@link #isEntire() <em>Entire</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEntire()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENTIRE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEntire() <em>Entire</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEntire()
	 * @generated
	 * @ordered
	 */
	protected boolean entire = ENTIRE_EDEFAULT;

	/**
	 * The default value of the '{@link #isRegex() <em>Regex</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRegex()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REGEX_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRegex() <em>Regex</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRegex()
	 * @generated
	 * @ordered
	 */
	protected boolean regex = REGEX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FieldConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QueryPackage.Literals.FIELD_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.FIELD_CONSTRAINT__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCaseSensitive(boolean newCaseSensitive) {
		boolean oldCaseSensitive = caseSensitive;
		caseSensitive = newCaseSensitive;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.FIELD_CONSTRAINT__CASE_SENSITIVE, oldCaseSensitive, caseSensitive));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWholeWord() {
		return wholeWord;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWholeWord(boolean newWholeWord) {
		boolean oldWholeWord = wholeWord;
		wholeWord = newWholeWord;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.FIELD_CONSTRAINT__WHOLE_WORD, oldWholeWord, wholeWord));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isEntire() {
		return entire;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEntire(boolean newEntire) {
		boolean oldEntire = entire;
		entire = newEntire;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.FIELD_CONSTRAINT__ENTIRE, oldEntire, entire));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isRegex() {
		return regex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRegex(boolean newRegex) {
		boolean oldRegex = regex;
		regex = newRegex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.FIELD_CONSTRAINT__REGEX, oldRegex, regex));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case QueryPackage.FIELD_CONSTRAINT__VALUE:
				return getValue();
			case QueryPackage.FIELD_CONSTRAINT__CASE_SENSITIVE:
				return isCaseSensitive();
			case QueryPackage.FIELD_CONSTRAINT__WHOLE_WORD:
				return isWholeWord();
			case QueryPackage.FIELD_CONSTRAINT__ENTIRE:
				return isEntire();
			case QueryPackage.FIELD_CONSTRAINT__REGEX:
				return isRegex();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case QueryPackage.FIELD_CONSTRAINT__VALUE:
				setValue((String)newValue);
				return;
			case QueryPackage.FIELD_CONSTRAINT__CASE_SENSITIVE:
				setCaseSensitive((Boolean)newValue);
				return;
			case QueryPackage.FIELD_CONSTRAINT__WHOLE_WORD:
				setWholeWord((Boolean)newValue);
				return;
			case QueryPackage.FIELD_CONSTRAINT__ENTIRE:
				setEntire((Boolean)newValue);
				return;
			case QueryPackage.FIELD_CONSTRAINT__REGEX:
				setRegex((Boolean)newValue);
				return;
			default:
				super.eSet(featureID, newValue);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case QueryPackage.FIELD_CONSTRAINT__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case QueryPackage.FIELD_CONSTRAINT__CASE_SENSITIVE:
				setCaseSensitive(CASE_SENSITIVE_EDEFAULT);
				return;
			case QueryPackage.FIELD_CONSTRAINT__WHOLE_WORD:
				setWholeWord(WHOLE_WORD_EDEFAULT);
				return;
			case QueryPackage.FIELD_CONSTRAINT__ENTIRE:
				setEntire(ENTIRE_EDEFAULT);
				return;
			case QueryPackage.FIELD_CONSTRAINT__REGEX:
				setRegex(REGEX_EDEFAULT);
				return;
			default:
				super.eUnset(featureID);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case QueryPackage.FIELD_CONSTRAINT__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case QueryPackage.FIELD_CONSTRAINT__CASE_SENSITIVE:
				return caseSensitive != CASE_SENSITIVE_EDEFAULT;
			case QueryPackage.FIELD_CONSTRAINT__WHOLE_WORD:
				return wholeWord != WHOLE_WORD_EDEFAULT;
			case QueryPackage.FIELD_CONSTRAINT__ENTIRE:
				return entire != ENTIRE_EDEFAULT;
			case QueryPackage.FIELD_CONSTRAINT__REGEX:
				return regex != REGEX_EDEFAULT;
			default:
				return super.eIsSet(featureID);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (value: "); //$NON-NLS-1$
		result.append(value);
		result.append(", caseSensitive: "); //$NON-NLS-1$
		result.append(caseSensitive);
		result.append(", wholeWord: "); //$NON-NLS-1$
		result.append(wholeWord);
		result.append(", entire: "); //$NON-NLS-1$
		result.append(entire);
		result.append(", regex: "); //$NON-NLS-1$
		result.append(regex);
		result.append(')');
		return result.toString();
	}

} //FieldConstraintImpl
