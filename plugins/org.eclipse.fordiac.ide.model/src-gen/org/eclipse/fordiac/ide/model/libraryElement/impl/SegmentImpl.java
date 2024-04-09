/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
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

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.IVarElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Segment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl#getColor <em>Color</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl#getVarDeclarations <em>Var Declarations</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl#getOutConnections <em>Out Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl#getCommunication <em>Communication</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SegmentImpl extends TypedConfigureableObjectImpl implements Segment {
	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected Position position;

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected Color color;

	/**
	 * The cached value of the '{@link #getVarDeclarations() <em>Var Declarations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarDeclarations()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> varDeclarations;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH_EDEFAULT = 200;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected int width = WIDTH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutConnections() <em>Out Connections</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<Link> outConnections;

	/**
	 * The cached value of the '{@link #getCommunication() <em>Communication</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommunication()
	 * @generated
	 * @ordered
	 */
	protected CommunicationConfiguration communication;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SegmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.SEGMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getPosition() {
		if (position != null && position.eIsProxy()) {
			InternalEObject oldPosition = (InternalEObject)position;
			position = (Position)eResolveProxy(oldPosition);
			if (position != oldPosition) {
				InternalEObject newPosition = (InternalEObject)position;
				NotificationChain msgs = oldPosition.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__POSITION, null, null);
				if (newPosition.eInternalContainer() == null) {
					msgs = newPosition.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__POSITION, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.SEGMENT__POSITION, oldPosition, position));
			}
		}
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position basicGetPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPosition(Position newPosition, NotificationChain msgs) {
		Position oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SEGMENT__POSITION, oldPosition, newPosition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPosition(Position newPosition) {
		if (newPosition != position) {
			NotificationChain msgs = null;
			if (position != null)
				msgs = ((InternalEObject)position).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__POSITION, null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject)newPosition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__POSITION, null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SEGMENT__POSITION, newPosition, newPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Color getColor() {
		if (color != null && color.eIsProxy()) {
			InternalEObject oldColor = (InternalEObject)color;
			color = (Color)eResolveProxy(oldColor);
			if (color != oldColor) {
				InternalEObject newColor = (InternalEObject)color;
				NotificationChain msgs = oldColor.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__COLOR, null, null);
				if (newColor.eInternalContainer() == null) {
					msgs = newColor.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__COLOR, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.SEGMENT__COLOR, oldColor, color));
			}
		}
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Color basicGetColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColor(Color newColor, NotificationChain msgs) {
		Color oldColor = color;
		color = newColor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SEGMENT__COLOR, oldColor, newColor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setColor(Color newColor) {
		if (newColor != color) {
			NotificationChain msgs = null;
			if (color != null)
				msgs = ((InternalEObject)color).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__COLOR, null, msgs);
			if (newColor != null)
				msgs = ((InternalEObject)newColor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__COLOR, null, msgs);
			msgs = basicSetColor(newColor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SEGMENT__COLOR, newColor, newColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SEGMENT__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VarDeclaration> getVarDeclarations() {
		if (varDeclarations == null) {
			varDeclarations = new EObjectContainmentEList.Resolving<VarDeclaration>(VarDeclaration.class, this, LibraryElementPackage.SEGMENT__VAR_DECLARATIONS);
		}
		return varDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Link> getOutConnections() {
		if (outConnections == null) {
			outConnections = new EObjectWithInverseResolvingEList<Link>(Link.class, this, LibraryElementPackage.SEGMENT__OUT_CONNECTIONS, LibraryElementPackage.LINK__SEGMENT);
		}
		return outConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommunicationConfiguration getCommunication() {
		if (communication != null && communication.eIsProxy()) {
			InternalEObject oldCommunication = (InternalEObject)communication;
			communication = (CommunicationConfiguration)eResolveProxy(oldCommunication);
			if (communication != oldCommunication) {
				InternalEObject newCommunication = (InternalEObject)communication;
				NotificationChain msgs = oldCommunication.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__COMMUNICATION, null, null);
				if (newCommunication.eInternalContainer() == null) {
					msgs = newCommunication.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__COMMUNICATION, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.SEGMENT__COMMUNICATION, oldCommunication, communication));
			}
		}
		return communication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommunicationConfiguration basicGetCommunication() {
		return communication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommunication(CommunicationConfiguration newCommunication, NotificationChain msgs) {
		CommunicationConfiguration oldCommunication = communication;
		communication = newCommunication;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SEGMENT__COMMUNICATION, oldCommunication, newCommunication);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommunication(CommunicationConfiguration newCommunication) {
		if (newCommunication != communication) {
			NotificationChain msgs = null;
			if (communication != null)
				msgs = ((InternalEObject)communication).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__COMMUNICATION, null, msgs);
			if (newCommunication != null)
				msgs = ((InternalEObject)newCommunication).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SEGMENT__COMMUNICATION, null, msgs);
			msgs = basicSetCommunication(newCommunication, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SEGMENT__COMMUNICATION, newCommunication, newCommunication));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SegmentType getType() {
		//this cannot be moved to the annotation class because there we don't have the super access!!!
		org.eclipse.fordiac.ide.model.libraryElement.LibraryElement type = super.getType();
		if(type instanceof SegmentType){
			return (SegmentType) type; 
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AutomationSystem getAutomationSystem() {
		return org.eclipse.fordiac.ide.model.annotations.SegmentAnnotations.getAutomationSystem(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SystemConfiguration getSystemConfiguration() {
		return org.eclipse.fordiac.ide.model.annotations.SegmentAnnotations.getSystemConfiguration(this); 
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void updatePositionFromScreenCoordinates(final int x, final int y) {
		PositionAnnotation.updatePositionFromScreenCoordinates(this, x,y);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void updatePositionFromScreenCoordinates(final Point newPos) {
		updatePositionFromScreenCoordinates(newPos.x, newPos.y);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.SEGMENT__OUT_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutConnections()).basicAdd(otherEnd, msgs);
			default:
				return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.SEGMENT__POSITION:
				return basicSetPosition(null, msgs);
			case LibraryElementPackage.SEGMENT__COLOR:
				return basicSetColor(null, msgs);
			case LibraryElementPackage.SEGMENT__VAR_DECLARATIONS:
				return ((InternalEList<?>)getVarDeclarations()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.SEGMENT__OUT_CONNECTIONS:
				return ((InternalEList<?>)getOutConnections()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.SEGMENT__COMMUNICATION:
				return basicSetCommunication(null, msgs);
			default:
				return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.SEGMENT__POSITION:
				if (resolve) return getPosition();
				return basicGetPosition();
			case LibraryElementPackage.SEGMENT__COLOR:
				if (resolve) return getColor();
				return basicGetColor();
			case LibraryElementPackage.SEGMENT__VAR_DECLARATIONS:
				return getVarDeclarations();
			case LibraryElementPackage.SEGMENT__WIDTH:
				return getWidth();
			case LibraryElementPackage.SEGMENT__OUT_CONNECTIONS:
				return getOutConnections();
			case LibraryElementPackage.SEGMENT__COMMUNICATION:
				if (resolve) return getCommunication();
				return basicGetCommunication();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.SEGMENT__POSITION:
				setPosition((Position)newValue);
				return;
			case LibraryElementPackage.SEGMENT__COLOR:
				setColor((Color)newValue);
				return;
			case LibraryElementPackage.SEGMENT__VAR_DECLARATIONS:
				getVarDeclarations().clear();
				getVarDeclarations().addAll((Collection<? extends VarDeclaration>)newValue);
				return;
			case LibraryElementPackage.SEGMENT__WIDTH:
				setWidth((Integer)newValue);
				return;
			case LibraryElementPackage.SEGMENT__OUT_CONNECTIONS:
				getOutConnections().clear();
				getOutConnections().addAll((Collection<? extends Link>)newValue);
				return;
			case LibraryElementPackage.SEGMENT__COMMUNICATION:
				setCommunication((CommunicationConfiguration)newValue);
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
			case LibraryElementPackage.SEGMENT__POSITION:
				setPosition((Position)null);
				return;
			case LibraryElementPackage.SEGMENT__COLOR:
				setColor((Color)null);
				return;
			case LibraryElementPackage.SEGMENT__VAR_DECLARATIONS:
				getVarDeclarations().clear();
				return;
			case LibraryElementPackage.SEGMENT__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case LibraryElementPackage.SEGMENT__OUT_CONNECTIONS:
				getOutConnections().clear();
				return;
			case LibraryElementPackage.SEGMENT__COMMUNICATION:
				setCommunication((CommunicationConfiguration)null);
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
			case LibraryElementPackage.SEGMENT__POSITION:
				return position != null;
			case LibraryElementPackage.SEGMENT__COLOR:
				return color != null;
			case LibraryElementPackage.SEGMENT__VAR_DECLARATIONS:
				return varDeclarations != null && !varDeclarations.isEmpty();
			case LibraryElementPackage.SEGMENT__WIDTH:
				return width != WIDTH_EDEFAULT;
			case LibraryElementPackage.SEGMENT__OUT_CONNECTIONS:
				return outConnections != null && !outConnections.isEmpty();
			case LibraryElementPackage.SEGMENT__COMMUNICATION:
				return communication != null;
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == PositionableElement.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.SEGMENT__POSITION: return LibraryElementPackage.POSITIONABLE_ELEMENT__POSITION;
				default: return -1;
			}
		}
		if (baseClass == ColorizableElement.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.SEGMENT__COLOR: return LibraryElementPackage.COLORIZABLE_ELEMENT__COLOR;
				default: return -1;
			}
		}
		if (baseClass == IVarElement.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.SEGMENT__VAR_DECLARATIONS: return LibraryElementPackage.IVAR_ELEMENT__VAR_DECLARATIONS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == PositionableElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.POSITIONABLE_ELEMENT__POSITION: return LibraryElementPackage.SEGMENT__POSITION;
				default: return -1;
			}
		}
		if (baseClass == ColorizableElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.COLORIZABLE_ELEMENT__COLOR: return LibraryElementPackage.SEGMENT__COLOR;
				default: return -1;
			}
		}
		if (baseClass == IVarElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.IVAR_ELEMENT__VAR_DECLARATIONS: return LibraryElementPackage.SEGMENT__VAR_DECLARATIONS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (width: "); //$NON-NLS-1$
		result.append(width);
		result.append(')');
		return result.toString();
	}

} //SegmentImpl
