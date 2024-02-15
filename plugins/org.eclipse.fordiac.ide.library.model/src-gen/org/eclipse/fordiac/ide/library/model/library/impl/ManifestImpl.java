/**
 */
package org.eclipse.fordiac.ide.library.model.library.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.fordiac.ide.library.model.library.Dependencies;
import org.eclipse.fordiac.ide.library.model.library.Exports;
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
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl#getExports <em>Exports</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl#getScope <em>Scope</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ManifestImpl extends MinimalEObjectImpl.Container implements Manifest {
	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected Dependencies dependencies;

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
	 * The cached value of the '{@link #getExports() <em>Exports</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExports()
	 * @generated
	 * @ordered
	 */
	protected Exports exports;

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
	public Dependencies getDependencies() {
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDependencies(final Dependencies newDependencies, NotificationChain msgs) {
		final Dependencies oldDependencies = dependencies;
		dependencies = newDependencies;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryPackage.MANIFEST__DEPENDENCIES, oldDependencies, newDependencies);
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
	public void setDependencies(final Dependencies newDependencies) {
		if (newDependencies != dependencies) {
			NotificationChain msgs = null;
			if (dependencies != null) {
				msgs = ((InternalEObject)dependencies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.MANIFEST__DEPENDENCIES, null, msgs);
			}
			if (newDependencies != null) {
				msgs = ((InternalEObject)newDependencies).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.MANIFEST__DEPENDENCIES, null, msgs);
			}
			msgs = basicSetDependencies(newDependencies, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.MANIFEST__DEPENDENCIES, newDependencies, newDependencies));
		}
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
	public Exports getExports() {
		return exports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExports(final Exports newExports, NotificationChain msgs) {
		final Exports oldExports = exports;
		exports = newExports;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryPackage.MANIFEST__EXPORTS, oldExports, newExports);
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
	public void setExports(final Exports newExports) {
		if (newExports != exports) {
			NotificationChain msgs = null;
			if (exports != null) {
				msgs = ((InternalEObject)exports).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.MANIFEST__EXPORTS, null, msgs);
			}
			if (newExports != null) {
				msgs = ((InternalEObject)newExports).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.MANIFEST__EXPORTS, null, msgs);
			}
			msgs = basicSetExports(newExports, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.MANIFEST__EXPORTS, newExports, newExports));
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
		case LibraryPackage.MANIFEST__DEPENDENCIES:
			return basicSetDependencies(null, msgs);
		case LibraryPackage.MANIFEST__PRODUCT:
			return basicSetProduct(null, msgs);
		case LibraryPackage.MANIFEST__EXPORTS:
			return basicSetExports(null, msgs);
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
		case LibraryPackage.MANIFEST__DEPENDENCIES:
			return getDependencies();
		case LibraryPackage.MANIFEST__PRODUCT:
			return getProduct();
		case LibraryPackage.MANIFEST__EXPORTS:
			return getExports();
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
		case LibraryPackage.MANIFEST__DEPENDENCIES:
			setDependencies((Dependencies)newValue);
			return;
		case LibraryPackage.MANIFEST__PRODUCT:
			setProduct((Product)newValue);
			return;
		case LibraryPackage.MANIFEST__EXPORTS:
			setExports((Exports)newValue);
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
		case LibraryPackage.MANIFEST__DEPENDENCIES:
			setDependencies((Dependencies)null);
			return;
		case LibraryPackage.MANIFEST__PRODUCT:
			setProduct((Product)null);
			return;
		case LibraryPackage.MANIFEST__EXPORTS:
			setExports((Exports)null);
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
		case LibraryPackage.MANIFEST__DEPENDENCIES:
			return dependencies != null;
		case LibraryPackage.MANIFEST__PRODUCT:
			return product != null;
		case LibraryPackage.MANIFEST__EXPORTS:
			return exports != null;
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
		result.append(" (scope: ");
		result.append(scope);
		result.append(')');
		return result.toString();
	}

} //ManifestImpl
