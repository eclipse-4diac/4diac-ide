/**
 */
package org.eclipse.fordiac.ide.library.model.library.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.fordiac.ide.library.model.library.Libraries;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Product;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Manifest</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl#getLibraries <em>Libraries</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl#getScope <em>Scope</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ManifestImpl extends MinimalEObjectImpl.Container implements Manifest {
	/**
	 * The cached value of the '{@link #getProduct() <em>Product</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProduct()
	 * @generated
	 * @ordered
	 */
	protected Product product;

	/**
	 * The cached value of the '{@link #getLibraries() <em>Libraries</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLibraries()
	 * @generated
	 * @ordered
	 */
	protected Libraries libraries;

	/**
	 * The default value of the '{@link #getScope() <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected static final String SCOPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getScope() <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected String scope = SCOPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManifestImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.MANIFEST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Product getProduct() {
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProduct(final Product newProduct, NotificationChain msgs) {
		final Product oldProduct = product;
		product = newProduct;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryPackage.MANIFEST__PRODUCT, oldProduct, newProduct);
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
	public void setProduct(final Product newProduct) {
		if (newProduct != product) {
			NotificationChain msgs = null;
			if (product != null) {
				msgs = ((InternalEObject)product).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.MANIFEST__PRODUCT, null, msgs);
			}
			if (newProduct != null) {
				msgs = ((InternalEObject)newProduct).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.MANIFEST__PRODUCT, null, msgs);
			}
			msgs = basicSetProduct(newProduct, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.MANIFEST__PRODUCT, newProduct, newProduct));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Libraries getLibraries() {
		return libraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLibraries(final Libraries newLibraries, NotificationChain msgs) {
		final Libraries oldLibraries = libraries;
		libraries = newLibraries;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryPackage.MANIFEST__LIBRARIES, oldLibraries, newLibraries);
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
	public void setLibraries(final Libraries newLibraries) {
		if (newLibraries != libraries) {
			NotificationChain msgs = null;
			if (libraries != null) {
				msgs = ((InternalEObject)libraries).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.MANIFEST__LIBRARIES, null, msgs);
			}
			if (newLibraries != null) {
				msgs = ((InternalEObject)newLibraries).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.MANIFEST__LIBRARIES, null, msgs);
			}
			msgs = basicSetLibraries(newLibraries, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.MANIFEST__LIBRARIES, newLibraries, newLibraries));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getScope() {
		return scope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setScope(final String newScope) {
		final String oldScope = scope;
		scope = newScope;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.MANIFEST__SCOPE, oldScope, scope));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID, final NotificationChain msgs) {
		switch (featureID) {
		case LibraryPackage.MANIFEST__PRODUCT:
			return basicSetProduct(null, msgs);
		case LibraryPackage.MANIFEST__LIBRARIES:
			return basicSetLibraries(null, msgs);
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
		case LibraryPackage.MANIFEST__PRODUCT:
			return getProduct();
		case LibraryPackage.MANIFEST__LIBRARIES:
			return getLibraries();
		case LibraryPackage.MANIFEST__SCOPE:
			return getScope();
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
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
		case LibraryPackage.MANIFEST__PRODUCT:
			setProduct((Product)newValue);
			return;
		case LibraryPackage.MANIFEST__LIBRARIES:
			setLibraries((Libraries)newValue);
			return;
		case LibraryPackage.MANIFEST__SCOPE:
			setScope((String)newValue);
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
		case LibraryPackage.MANIFEST__PRODUCT:
			setProduct((Product)null);
			return;
		case LibraryPackage.MANIFEST__LIBRARIES:
			setLibraries((Libraries)null);
			return;
		case LibraryPackage.MANIFEST__SCOPE:
			setScope(SCOPE_EDEFAULT);
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
		case LibraryPackage.MANIFEST__PRODUCT:
			return product != null;
		case LibraryPackage.MANIFEST__LIBRARIES:
			return libraries != null;
		case LibraryPackage.MANIFEST__SCOPE:
			return SCOPE_EDEFAULT == null ? scope != null : !SCOPE_EDEFAULT.equals(scope);
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
		result.append(" (scope: "); //$NON-NLS-1$
		result.append(scope);
		result.append(')');
		return result.toString();
	}

} //ManifestImpl
