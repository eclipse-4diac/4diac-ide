/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.monitoring.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.HiddenElement;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringEvent;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringAdapterInterfaceEditPart;
import org.eclipse.gef.EditPart;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Adapter
 * Monitoring Event</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl#isIsInput <em>Is Input</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl#getInputConnections <em>Input Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl#getOutputConnections <em>Output Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl#getWith <em>With</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdapterMonitoringEventImpl extends EObjectImpl implements AdapterMonitoringEvent {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

	/**
	 * The default value of the '{@link #isIsInput() <em>Is Input</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isIsInput()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_INPUT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsInput() <em>Is Input</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isIsInput()
	 * @generated
	 * @ordered
	 */
	protected boolean isInput = IS_INPUT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInputConnections() <em>Input
	 * Connections</em>}' reference list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getInputConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> inputConnections;

	/**
	 * The cached value of the '{@link #getOutputConnections() <em>Output
	 * Connections</em>}' reference list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getOutputConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> outputConnections;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected DataType type;

	/**
	 * The cached value of the '{@link #getWith() <em>With</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getWith()
	 * @generated
	 * @ordered
	 */
	protected EList<With> with;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected AdapterMonitoringEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MonitoringPackage.Literals.ADAPTER_MONITORING_EVENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.ADAPTER_MONITORING_EVENT__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.ADAPTER_MONITORING_EVENT__COMMENT, oldComment, comment));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList.Resolving<>(Attribute.class, this, MonitoringPackage.ADAPTER_MONITORING_EVENT__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsInput() {
		return isInput;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsInput(boolean newIsInput) {
		boolean oldIsInput = isInput;
		isInput = newIsInput;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.ADAPTER_MONITORING_EVENT__IS_INPUT, oldIsInput, isInput));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeName() {
		org.eclipse.fordiac.ide.model.libraryElement.INamedElement type = getType();
		if(type != null){
			return type.getName();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFullTypeName() {
		return getTypeName();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateType(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.TypedElementAnnotations.validateType(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateName(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceElementAnnotations.validateName(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Connection> getInputConnections() {
		if (inputConnections == null) {
			inputConnections = new EObjectWithInverseResolvingEList<>(Connection.class, this, MonitoringPackage.ADAPTER_MONITORING_EVENT__INPUT_CONNECTIONS, LibraryElementPackage.CONNECTION__DESTINATION);
		}
		return inputConnections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Connection> getOutputConnections() {
		if (outputConnections == null) {
			outputConnections = new EObjectWithInverseResolvingEList<>(Connection.class, this, MonitoringPackage.ADAPTER_MONITORING_EVENT__OUTPUT_CONNECTIONS, LibraryElementPackage.CONNECTION__SOURCE);
		}
		return outputConnections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (DataType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MonitoringPackage.ADAPTER_MONITORING_EVENT__TYPE, oldType, type));
				}
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public DataType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(DataType newType) {
		DataType oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.ADAPTER_MONITORING_EVENT__TYPE, oldType, type));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<With> getWith() {
		if (with == null) {
			with = new EObjectContainmentEList<>(With.class, this, MonitoringPackage.ADAPTER_MONITORING_EVENT__WITH);
		}
		return with;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<INamedElement> getInputParameters() {
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<INamedElement> getOutputParameters() {
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<INamedElement> getInOutParameters() {
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataType getReturnType() {
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetworkElement getFBNetworkElement() {
		return org.eclipse.fordiac.ide.model.Annotations.getFBNetworkElement(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVisible(final boolean visible) {
		org.eclipse.fordiac.ide.model.annotations.HiddenElementAnnotations.setVisible(this,visible);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isVisible() {
		return org.eclipse.fordiac.ide.model.annotations.HiddenElementAnnotations.isVisible(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttribute(final String attributeName, final DataType type, final String value,
			final String comment) {
		org.eclipse.fordiac.ide.model.Annotations.setAttribute(this, attributeName, type, value, comment);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttribute(final AttributeDeclaration attributeDeclaration, final String value,
			final String comment) {
		org.eclipse.fordiac.ide.model.Annotations.setAttribute(this, attributeDeclaration, value, comment);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Attribute getAttribute(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.getAttribute(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAttributeValue(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.getAttributeValue(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean deleteAttribute(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.deleteAttribute(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getQualifiedName() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.getQualifiedName(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__INPUT_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInputConnections()).basicAdd(otherEnd, msgs);
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__OUTPUT_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutputConnections()).basicAdd(otherEnd, msgs);
			default:
				return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__INPUT_CONNECTIONS:
				return ((InternalEList<?>)getInputConnections()).basicRemove(otherEnd, msgs);
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__OUTPUT_CONNECTIONS:
				return ((InternalEList<?>)getOutputConnections()).basicRemove(otherEnd, msgs);
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__WITH:
				return ((InternalEList<?>)getWith()).basicRemove(otherEnd, msgs);
			default:
				return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__NAME:
				return getName();
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__COMMENT:
				return getComment();
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__ATTRIBUTES:
				return getAttributes();
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__IS_INPUT:
				return isIsInput();
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__INPUT_CONNECTIONS:
				return getInputConnections();
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__OUTPUT_CONNECTIONS:
				return getOutputConnections();
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__TYPE:
				if (resolve) {
					return getType();
				}
				return basicGetType();
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__WITH:
				return getWith();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__NAME:
				setName((String)newValue);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__COMMENT:
				setComment((String)newValue);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends Attribute>)newValue);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__IS_INPUT:
				setIsInput((Boolean)newValue);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__INPUT_CONNECTIONS:
				getInputConnections().clear();
				getInputConnections().addAll((Collection<? extends Connection>)newValue);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__OUTPUT_CONNECTIONS:
				getOutputConnections().clear();
				getOutputConnections().addAll((Collection<? extends Connection>)newValue);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__TYPE:
				setType((DataType)newValue);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__WITH:
				getWith().clear();
				getWith().addAll((Collection<? extends With>)newValue);
				return;
			default:
				super.eSet(featureID, newValue);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__ATTRIBUTES:
				getAttributes().clear();
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__IS_INPUT:
				setIsInput(IS_INPUT_EDEFAULT);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__INPUT_CONNECTIONS:
				getInputConnections().clear();
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__OUTPUT_CONNECTIONS:
				getOutputConnections().clear();
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__TYPE:
				setType((DataType)null);
				return;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__WITH:
				getWith().clear();
				return;
			default:
				super.eUnset(featureID);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__IS_INPUT:
				return isInput != IS_INPUT_EDEFAULT;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__INPUT_CONNECTIONS:
				return inputConnections != null && !inputConnections.isEmpty();
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__OUTPUT_CONNECTIONS:
				return outputConnections != null && !outputConnections.isEmpty();
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__TYPE:
				return type != null;
			case MonitoringPackage.ADAPTER_MONITORING_EVENT__WITH:
				return with != null && !with.isEmpty();
			default:
				return super.eIsSet(featureID);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == INamedElement.class) {
			switch (derivedFeatureID) {
				case MonitoringPackage.ADAPTER_MONITORING_EVENT__NAME: return LibraryElementPackage.INAMED_ELEMENT__NAME;
				case MonitoringPackage.ADAPTER_MONITORING_EVENT__COMMENT: return LibraryElementPackage.INAMED_ELEMENT__COMMENT;
				default: return -1;
			}
		}
		if (baseClass == ITypedElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ConfigurableObject.class) {
			switch (derivedFeatureID) {
				case MonitoringPackage.ADAPTER_MONITORING_EVENT__ATTRIBUTES: return LibraryElementPackage.CONFIGURABLE_OBJECT__ATTRIBUTES;
				default: return -1;
			}
		}
		if (baseClass == HiddenElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IInterfaceElement.class) {
			switch (derivedFeatureID) {
				case MonitoringPackage.ADAPTER_MONITORING_EVENT__IS_INPUT: return LibraryElementPackage.IINTERFACE_ELEMENT__IS_INPUT;
				case MonitoringPackage.ADAPTER_MONITORING_EVENT__INPUT_CONNECTIONS: return LibraryElementPackage.IINTERFACE_ELEMENT__INPUT_CONNECTIONS;
				case MonitoringPackage.ADAPTER_MONITORING_EVENT__OUTPUT_CONNECTIONS: return LibraryElementPackage.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS;
				case MonitoringPackage.ADAPTER_MONITORING_EVENT__TYPE: return LibraryElementPackage.IINTERFACE_ELEMENT__TYPE;
				default: return -1;
			}
		}
		if (baseClass == ICallable.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == Event.class) {
			switch (derivedFeatureID) {
				case MonitoringPackage.ADAPTER_MONITORING_EVENT__WITH: return LibraryElementPackage.EVENT__WITH;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == INamedElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.INAMED_ELEMENT__NAME: return MonitoringPackage.ADAPTER_MONITORING_EVENT__NAME;
				case LibraryElementPackage.INAMED_ELEMENT__COMMENT: return MonitoringPackage.ADAPTER_MONITORING_EVENT__COMMENT;
				default: return -1;
			}
		}
		if (baseClass == ITypedElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ConfigurableObject.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.CONFIGURABLE_OBJECT__ATTRIBUTES: return MonitoringPackage.ADAPTER_MONITORING_EVENT__ATTRIBUTES;
				default: return -1;
			}
		}
		if (baseClass == HiddenElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IInterfaceElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.IINTERFACE_ELEMENT__IS_INPUT: return MonitoringPackage.ADAPTER_MONITORING_EVENT__IS_INPUT;
				case LibraryElementPackage.IINTERFACE_ELEMENT__INPUT_CONNECTIONS: return MonitoringPackage.ADAPTER_MONITORING_EVENT__INPUT_CONNECTIONS;
				case LibraryElementPackage.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS: return MonitoringPackage.ADAPTER_MONITORING_EVENT__OUTPUT_CONNECTIONS;
				case LibraryElementPackage.IINTERFACE_ELEMENT__TYPE: return MonitoringPackage.ADAPTER_MONITORING_EVENT__TYPE;
				default: return -1;
			}
		}
		if (baseClass == ICallable.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == Event.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.EVENT__WITH: return MonitoringPackage.ADAPTER_MONITORING_EVENT__WITH;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(", isInput: "); //$NON-NLS-1$
		result.append(isInput);
		result.append(')');
		return result.toString();
	}

	@Override
	public EditPart createEditPart() {
		return new MonitoringAdapterInterfaceEditPart();
	}

} // AdapterMonitoringEventImpl
