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
import java.util.stream.Stream;

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
import org.eclipse.fordiac.ide.model.libraryElement.ArraySize;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.HiddenElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceElementAnnotations;
import org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringVarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringAdapterInterfaceEditPart;
import org.eclipse.gef.EditPart;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Adapter
 * Monitoring Var Declaration</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl#getComment
 * <em>Comment</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl#getAttributes
 * <em>Attributes</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl#isIsInput
 * <em>Is Input</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl#getInputConnections
 * <em>Input Connections</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl#getOutputConnections
 * <em>Output Connections</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl#getType
 * <em>Type</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl#getArraySize
 * <em>Array Size</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl#getWiths
 * <em>Withs</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl#getValue
 * <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdapterMonitoringVarDeclarationImpl extends EObjectImpl implements AdapterMonitoringVarDeclaration {
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
	 *
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

	/**
	 * The default value of the '{@link #isIsInput() <em>Is Input</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isIsInput()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_INPUT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsInput() <em>Is Input</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
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
	 * The cached value of the '{@link #getArraySize() <em>Array Size</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getArraySize()
	 * @generated
	 * @ordered
	 */
	protected ArraySize arraySize;

	/**
	 * The cached value of the '{@link #getWiths() <em>Withs</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getWiths()
	 * @generated
	 * @ordered
	 */
	protected EList<With> withs;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected Value value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AdapterMonitoringVarDeclarationImpl() {
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MonitoringPackage.Literals.ADAPTER_MONITORING_VAR_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setName(final String newName) {
		final String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setComment(final String newComment) {
		final String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__COMMENT, oldComment, comment));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList.Resolving<>(Attribute.class, this,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isIsInput() {
		return isInput;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIsInput(final boolean newIsInput) {
		final boolean oldIsInput = isInput;
		isInput = newIsInput;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__IS_INPUT, oldIsInput, isInput));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Value getValue() {
		if (value != null && value.eIsProxy()) {
			final InternalEObject oldValue = (InternalEObject) value;
			value = (Value) eResolveProxy(oldValue);
			if (value != oldValue) {
				final InternalEObject newValue = (InternalEObject) value;
				NotificationChain msgs = oldValue.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE, null,
						null);
				if (newValue.eInternalContainer() == null) {
					msgs = newValue.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE, null,
							msgs);
				}
				if (msgs != null) {
					msgs.dispatch();
				}
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE, oldValue, value));
				}
			}
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Value basicGetValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetValue(final Value newValue, NotificationChain msgs) {
		final Value oldValue = value;
		value = newValue;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE, oldValue, newValue);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setValue(final Value newValue) {
		if (newValue != value) {
			NotificationChain msgs = null;
			if (value != null) {
				msgs = ((InternalEObject) value).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE, null,
						msgs);
			}
			if (newValue != null) {
				msgs = ((InternalEObject) newValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE, null,
						msgs);
			}
			msgs = basicSetValue(newValue, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE, newValue, newValue));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Connection> getInputConnections() {
		if (inputConnections == null) {
			inputConnections = new EObjectWithInverseResolvingEList<>(Connection.class, this,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__INPUT_CONNECTIONS,
					LibraryElementPackage.CONNECTION__DESTINATION);
		}
		return inputConnections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Connection> getOutputConnections() {
		if (outputConnections == null) {
			outputConnections = new EObjectWithInverseResolvingEList<>(Connection.class, this,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__OUTPUT_CONNECTIONS,
					LibraryElementPackage.CONNECTION__SOURCE);
		}
		return outputConnections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ArraySize getArraySize() {
		if (arraySize != null && arraySize.eIsProxy()) {
			final InternalEObject oldArraySize = (InternalEObject) arraySize;
			arraySize = (ArraySize) eResolveProxy(oldArraySize);
			if (arraySize != oldArraySize) {
				final InternalEObject newArraySize = (InternalEObject) arraySize;
				NotificationChain msgs = oldArraySize.eInverseRemove(this,
						LibraryElementPackage.ARRAY_SIZE__VAR_DECLARATION, ArraySize.class, null);
				if (newArraySize.eInternalContainer() == null) {
					msgs = newArraySize.eInverseAdd(this, LibraryElementPackage.ARRAY_SIZE__VAR_DECLARATION,
							ArraySize.class, msgs);
				}
				if (msgs != null) {
					msgs.dispatch();
				}
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE, oldArraySize, arraySize));
				}
			}
		}
		return arraySize;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ArraySize basicGetArraySize() {
		return arraySize;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetArraySize(final ArraySize newArraySize, NotificationChain msgs) {
		final ArraySize oldArraySize = arraySize;
		arraySize = newArraySize;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE, oldArraySize, newArraySize);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setArraySize(final ArraySize newArraySize) {
		if (newArraySize != arraySize) {
			NotificationChain msgs = null;
			if (arraySize != null) {
				msgs = ((InternalEObject) arraySize).eInverseRemove(this,
						LibraryElementPackage.ARRAY_SIZE__VAR_DECLARATION, ArraySize.class, msgs);
			}
			if (newArraySize != null) {
				msgs = ((InternalEObject) newArraySize).eInverseAdd(this,
						LibraryElementPackage.ARRAY_SIZE__VAR_DECLARATION, ArraySize.class, msgs);
			}
			msgs = basicSetArraySize(newArraySize, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE, newArraySize, newArraySize));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DataType getType() {
		if (type != null && type.eIsProxy()) {
			final InternalEObject oldType = (InternalEObject) type;
			type = (DataType) eResolveProxy(oldType);
			if ((type != oldType) && eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public DataType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setType(final DataType newType) {
		final DataType oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__TYPE, oldType, type));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getTypeName() {
		final org.eclipse.fordiac.ide.model.libraryElement.INamedElement type = getType();
		if (type != null) {
			return type.getName();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean validateType(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.TypedElementAnnotations.validateType(this, diagnostics,
				context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean validateName(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceElementAnnotations.validateName(this,
				diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<With> getWiths() {
		if (withs == null) {
			withs = new EObjectWithInverseResolvingEList<>(With.class, this,
					MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__WITHS, LibraryElementPackage.WITH__VARIABLES);
		}
		return withs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isArray() {
		return org.eclipse.fordiac.ide.model.Annotations.isArray(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBNetworkElement getFBNetworkElement() {
		return org.eclipse.fordiac.ide.model.Annotations.getFBNetworkElement(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setVisible(final boolean visible) {
		org.eclipse.fordiac.ide.model.annotations.HiddenElementAnnotations.setVisible(this, visible);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isVisible() {
		return org.eclipse.fordiac.ide.model.annotations.HiddenElementAnnotations.isVisible(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setAttribute(final String attributeName, final DataType type, final String value,
			final String comment) {
		org.eclipse.fordiac.ide.model.Annotations.setAttribute(this, attributeName, type, value, comment);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setAttribute(final AttributeDeclaration attributeDeclaration, final String value,
			final String comment) {
		org.eclipse.fordiac.ide.model.Annotations.setAttribute(this, attributeDeclaration, value, comment);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Attribute getAttribute(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.getAttribute(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getAttributeValue(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.getAttributeValue(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean deleteAttribute(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.deleteAttribute(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getQualifiedName() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.getQualifiedName(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Stream<INamedElement> findBySimpleName(final String name) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.findBySimpleName(this, name);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Stream<INamedElement> findByQualifiedName(final String name) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.findByQualifiedName(this,
				name);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
		switch (featureID) {
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__INPUT_CONNECTIONS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getInputConnections()).basicAdd(otherEnd, msgs);
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__OUTPUT_CONNECTIONS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getOutputConnections()).basicAdd(otherEnd,
					msgs);
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE:
			if (arraySize != null) {
				msgs = ((InternalEObject) arraySize).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE, null,
						msgs);
			}
			return basicSetArraySize((ArraySize) otherEnd, msgs);
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__WITHS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getWiths()).basicAdd(otherEnd, msgs);
		default:
			return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
			final NotificationChain msgs) {
		return switch (featureID) {
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ATTRIBUTES -> ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__INPUT_CONNECTIONS -> ((InternalEList<?>) getInputConnections()).basicRemove(otherEnd, msgs);
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__OUTPUT_CONNECTIONS -> ((InternalEList<?>) getOutputConnections()).basicRemove(otherEnd, msgs);
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE -> basicSetArraySize(null, msgs);
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__WITHS -> ((InternalEList<?>) getWiths()).basicRemove(otherEnd, msgs);
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE -> basicSetValue(null, msgs);
		default -> super.eInverseRemove(otherEnd, featureID, msgs);
		};
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		switch (featureID) {
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__NAME:
			return getName();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__COMMENT:
			return getComment();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ATTRIBUTES:
			return getAttributes();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__IS_INPUT:
			return isIsInput();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__INPUT_CONNECTIONS:
			return getInputConnections();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__OUTPUT_CONNECTIONS:
			return getOutputConnections();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__TYPE:
			if (resolve) {
				return getType();
			}
			return basicGetType();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE:
			if (resolve) {
				return getArraySize();
			}
			return basicGetArraySize();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__WITHS:
			return getWiths();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE:
			if (resolve) {
				return getValue();
			}
			return basicGetValue();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__NAME:
			setName((String) newValue);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__COMMENT:
			setComment((String) newValue);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends Attribute>) newValue);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__IS_INPUT:
			setIsInput((Boolean) newValue);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__INPUT_CONNECTIONS:
			getInputConnections().clear();
			getInputConnections().addAll((Collection<? extends Connection>) newValue);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__OUTPUT_CONNECTIONS:
			getOutputConnections().clear();
			getOutputConnections().addAll((Collection<? extends Connection>) newValue);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__TYPE:
			setType((DataType) newValue);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE:
			setArraySize((ArraySize) newValue);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__WITHS:
			getWiths().clear();
			getWiths().addAll((Collection<? extends With>) newValue);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE:
			setValue((Value) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(final int featureID) {
		switch (featureID) {
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__NAME:
			setName(NAME_EDEFAULT);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__COMMENT:
			setComment(COMMENT_EDEFAULT);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ATTRIBUTES:
			getAttributes().clear();
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__IS_INPUT:
			setIsInput(IS_INPUT_EDEFAULT);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__INPUT_CONNECTIONS:
			getInputConnections().clear();
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__OUTPUT_CONNECTIONS:
			getOutputConnections().clear();
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__TYPE:
			setType((DataType) null);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE:
			setArraySize((ArraySize) null);
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__WITHS:
			getWiths().clear();
			return;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE:
			setValue((Value) null);
			return;
		default:
			super.eUnset(featureID);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(final int featureID) {
		return switch (featureID) {
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__NAME -> NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__COMMENT -> COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ATTRIBUTES -> attributes != null && !attributes.isEmpty();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__IS_INPUT -> isInput != IS_INPUT_EDEFAULT;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__INPUT_CONNECTIONS -> inputConnections != null && !inputConnections.isEmpty();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__OUTPUT_CONNECTIONS -> outputConnections != null && !outputConnections.isEmpty();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__TYPE -> type != null;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE -> arraySize != null;
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__WITHS -> withs != null && !withs.isEmpty();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE -> value != null;
		default -> super.eIsSet(featureID);
		};
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(final int derivedFeatureID, final Class<?> baseClass) {
		if (baseClass == INamedElement.class) {
			return switch (derivedFeatureID) {
			case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__NAME -> LibraryElementPackage.INAMED_ELEMENT__NAME;
			case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__COMMENT -> LibraryElementPackage.INAMED_ELEMENT__COMMENT;
			default -> -1;
			};
		}
		if (baseClass == ITypedElement.class) {
			return switch (derivedFeatureID) {
			default -> -1;
			};
		}
		if (baseClass == ConfigurableObject.class) {
			return switch (derivedFeatureID) {
			case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ATTRIBUTES -> LibraryElementPackage.CONFIGURABLE_OBJECT__ATTRIBUTES;
			default -> -1;
			};
		}
		if (baseClass == HiddenElement.class) {
			return switch (derivedFeatureID) {
			default -> -1;
			};
		}
		if (baseClass == IInterfaceElement.class) {
			return switch (derivedFeatureID) {
			case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__IS_INPUT -> LibraryElementPackage.IINTERFACE_ELEMENT__IS_INPUT;
			case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__INPUT_CONNECTIONS -> LibraryElementPackage.IINTERFACE_ELEMENT__INPUT_CONNECTIONS;
			case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__OUTPUT_CONNECTIONS -> LibraryElementPackage.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS;
			case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__TYPE -> LibraryElementPackage.IINTERFACE_ELEMENT__TYPE;
			default -> -1;
			};
		}
		if (baseClass == VarDeclaration.class) {
			return switch (derivedFeatureID) {
			case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE -> LibraryElementPackage.VAR_DECLARATION__ARRAY_SIZE;
			case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__WITHS -> LibraryElementPackage.VAR_DECLARATION__WITHS;
			case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE -> LibraryElementPackage.VAR_DECLARATION__VALUE;
			default -> -1;
			};
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(final int baseFeatureID, final Class<?> baseClass) {
		if (baseClass == INamedElement.class) {
			return switch (baseFeatureID) {
			case LibraryElementPackage.INAMED_ELEMENT__NAME -> MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__NAME;
			case LibraryElementPackage.INAMED_ELEMENT__COMMENT -> MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__COMMENT;
			default -> -1;
			};
		}
		if (baseClass == ITypedElement.class) {
			return switch (baseFeatureID) {
			default -> -1;
			};
		}
		if (baseClass == ConfigurableObject.class) {
			return switch (baseFeatureID) {
			case LibraryElementPackage.CONFIGURABLE_OBJECT__ATTRIBUTES -> MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ATTRIBUTES;
			default -> -1;
			};
		}
		if (baseClass == HiddenElement.class) {
			return switch (baseFeatureID) {
			default -> -1;
			};
		}
		if (baseClass == IInterfaceElement.class) {
			return switch (baseFeatureID) {
			case LibraryElementPackage.IINTERFACE_ELEMENT__IS_INPUT -> MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__IS_INPUT;
			case LibraryElementPackage.IINTERFACE_ELEMENT__INPUT_CONNECTIONS -> MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__INPUT_CONNECTIONS;
			case LibraryElementPackage.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS -> MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__OUTPUT_CONNECTIONS;
			case LibraryElementPackage.IINTERFACE_ELEMENT__TYPE -> MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__TYPE;
			default -> -1;
			};
		}
		if (baseClass == VarDeclaration.class) {
			return switch (baseFeatureID) {
			case LibraryElementPackage.VAR_DECLARATION__ARRAY_SIZE -> MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE;
			case LibraryElementPackage.VAR_DECLARATION__WITHS -> MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__WITHS;
			case LibraryElementPackage.VAR_DECLARATION__VALUE -> MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION__VALUE;
			default -> -1;
			};
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
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

	@Override
	public boolean isVarConfig() {
		return org.eclipse.fordiac.ide.model.Annotations.isVarConfig(this);
	}

	@Override
	public void setVarConfig(final boolean config) {
		// TODO Auto-generated method stub

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getFullTypeName() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceElementAnnotations.getFullTypeName(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isInOutVar() {
		return InterfaceElementAnnotations.isInOutVar(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean validateVarInOutSourceTypeIsWellDefined(final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationAnnotations
				.validateVarInOutSourceTypeIsWellDefined(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VarDeclaration getInOutVarOpposite() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationAnnotations.getInOutVarOpposite(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean validateMultipleInputConnections(final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationAnnotations
				.validateMultipleInputConnections(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean validateNoValueForGenericTypeVariable(final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationAnnotations
				.validateNoValueForGenericTypeVariable(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean validateValueForGenericInstanceVariable(final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationAnnotations
				.validateValueForGenericInstanceVariable(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean validateVarInOutIsWithed(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationAnnotations
				.validateVarInOutIsWithed(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean validateVarInOutSubappInterface(final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationAnnotations
				.validateVarInOutSubappInterface(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean validateVarInOutSubappNetwork(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationAnnotations
				.validateVarInOutSubappNetwork(this, diagnostics, context);
	}

} // AdapterMonitoringVarDeclarationImpl
