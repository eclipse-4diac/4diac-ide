/**
 */
package org.eclipse.fordiac.ide.library.model.library.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.library.model.library.Dependencies;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.Product;
import org.eclipse.fordiac.ide.library.model.library.VersionInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.ProductImpl#getVersionInfo <em>Version Info</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.ProductImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.ProductImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProductImpl extends MinimalEObjectImpl.Container implements Product {
	/**
	 * The cached value of the '{@link #getVersionInfo() <em>Version Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionInfo()
	 * @generated
	 * @ordered
	 */
	protected VersionInfo versionInfo;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<Dependencies> dependencies;

	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<String> attribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProductImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.PRODUCT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVersionInfo(final VersionInfo newVersionInfo, NotificationChain msgs) {
		final VersionInfo oldVersionInfo = versionInfo;
		versionInfo = newVersionInfo;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryPackage.PRODUCT__VERSION_INFO, oldVersionInfo, newVersionInfo);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVersionInfo(final VersionInfo newVersionInfo) {
		if (newVersionInfo != versionInfo) {
			NotificationChain msgs = null;
			if (versionInfo != null) {
				msgs = ((InternalEObject)versionInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.PRODUCT__VERSION_INFO, null, msgs);
			}
			if (newVersionInfo != null) {
				msgs = ((InternalEObject)newVersionInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.PRODUCT__VERSION_INFO, null, msgs);
			}
			msgs = basicSetVersionInfo(newVersionInfo, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.PRODUCT__VERSION_INFO, newVersionInfo, newVersionInfo));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Dependencies> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectContainmentEList<>(Dependencies.class, this, LibraryPackage.PRODUCT__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getAttribute() {
		if (attribute == null) {
			attribute = new EDataTypeEList<>(String.class, this, LibraryPackage.PRODUCT__ATTRIBUTE);
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID, final NotificationChain msgs) {
		switch (featureID) {
		case LibraryPackage.PRODUCT__VERSION_INFO:
			return basicSetVersionInfo(null, msgs);
		case LibraryPackage.PRODUCT__DEPENDENCIES:
			return ((InternalEList<?>)getDependencies()).basicRemove(otherEnd, msgs);
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
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		switch (featureID) {
		case LibraryPackage.PRODUCT__VERSION_INFO:
			return getVersionInfo();
		case LibraryPackage.PRODUCT__DEPENDENCIES:
			return getDependencies();
		case LibraryPackage.PRODUCT__ATTRIBUTE:
			return getAttribute();
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
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
		case LibraryPackage.PRODUCT__VERSION_INFO:
			setVersionInfo((VersionInfo)newValue);
			return;
		case LibraryPackage.PRODUCT__DEPENDENCIES:
			getDependencies().clear();
			getDependencies().addAll((Collection<? extends Dependencies>)newValue);
			return;
		case LibraryPackage.PRODUCT__ATTRIBUTE:
			getAttribute().clear();
			getAttribute().addAll((Collection<? extends String>)newValue);
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
	public void eUnset(final int featureID) {
		switch (featureID) {
		case LibraryPackage.PRODUCT__VERSION_INFO:
			setVersionInfo((VersionInfo)null);
			return;
		case LibraryPackage.PRODUCT__DEPENDENCIES:
			getDependencies().clear();
			return;
		case LibraryPackage.PRODUCT__ATTRIBUTE:
			getAttribute().clear();
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
	public boolean eIsSet(final int featureID) {
		switch (featureID) {
		case LibraryPackage.PRODUCT__VERSION_INFO:
			return versionInfo != null;
		case LibraryPackage.PRODUCT__DEPENDENCIES:
			return dependencies != null && !dependencies.isEmpty();
		case LibraryPackage.PRODUCT__ATTRIBUTE:
			return attribute != null && !attribute.isEmpty();
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
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (attribute: "); //$NON-NLS-1$
		result.append(attribute);
		result.append(')');
		return result.toString();
	}

} //ProductImpl
