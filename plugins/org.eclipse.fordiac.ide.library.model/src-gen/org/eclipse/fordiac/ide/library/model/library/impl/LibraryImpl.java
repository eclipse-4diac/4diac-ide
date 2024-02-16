/**
 */
package org.eclipse.fordiac.ide.library.model.library.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.fordiac.ide.library.model.library.Excludes;
import org.eclipse.fordiac.ide.library.model.library.Includes;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl#getExcludes <em>Excludes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl#getSymbolicName <em>Symbolic Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LibraryImpl extends MinimalEObjectImpl.Container implements Library {
	/**
	 * The cached value of the '{@link #getIncludes() <em>Includes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludes()
	 * @generated
	 * @ordered
	 */
	protected Includes includes;

	/**
	 * The cached value of the '{@link #getExcludes() <em>Excludes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludes()
	 * @generated
	 * @ordered
	 */
	protected Excludes excludes;

	/**
	 * The default value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final String ATTRIBUTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected String attribute = ATTRIBUTE_EDEFAULT;

	/**
	 * This is true if the Attribute attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean attributeESet;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSymbolicName() <em>Symbolic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbolicName()
	 * @generated
	 * @ordered
	 */
	protected static final String SYMBOLIC_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSymbolicName() <em>Symbolic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbolicName()
	 * @generated
	 * @ordered
	 */
	protected String symbolicName = SYMBOLIC_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Includes getIncludes() {
		return includes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIncludes(final Includes newIncludes, NotificationChain msgs) {
		final Includes oldIncludes = includes;
		includes = newIncludes;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryPackage.LIBRARY__INCLUDES, oldIncludes, newIncludes);
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
	public void setIncludes(final Includes newIncludes) {
		if (newIncludes != includes) {
			NotificationChain msgs = null;
			if (includes != null) {
				msgs = ((InternalEObject)includes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.LIBRARY__INCLUDES, null, msgs);
			}
			if (newIncludes != null) {
				msgs = ((InternalEObject)newIncludes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.LIBRARY__INCLUDES, null, msgs);
			}
			msgs = basicSetIncludes(newIncludes, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.LIBRARY__INCLUDES, newIncludes, newIncludes));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Excludes getExcludes() {
		return excludes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExcludes(final Excludes newExcludes, NotificationChain msgs) {
		final Excludes oldExcludes = excludes;
		excludes = newExcludes;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryPackage.LIBRARY__EXCLUDES, oldExcludes, newExcludes);
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
	public void setExcludes(final Excludes newExcludes) {
		if (newExcludes != excludes) {
			NotificationChain msgs = null;
			if (excludes != null) {
				msgs = ((InternalEObject)excludes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.LIBRARY__EXCLUDES, null, msgs);
			}
			if (newExcludes != null) {
				msgs = ((InternalEObject)newExcludes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryPackage.LIBRARY__EXCLUDES, null, msgs);
			}
			msgs = basicSetExcludes(newExcludes, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.LIBRARY__EXCLUDES, newExcludes, newExcludes));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAttribute() {
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttribute(final String newAttribute) {
		final String oldAttribute = attribute;
		attribute = newAttribute;
		final boolean oldAttributeESet = attributeESet;
		attributeESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.LIBRARY__ATTRIBUTE, oldAttribute, attribute, !oldAttributeESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetAttribute() {
		final String oldAttribute = attribute;
		final boolean oldAttributeESet = attributeESet;
		attribute = ATTRIBUTE_EDEFAULT;
		attributeESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, LibraryPackage.LIBRARY__ATTRIBUTE, oldAttribute, ATTRIBUTE_EDEFAULT, oldAttributeESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetAttribute() {
		return attributeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComment(final String newComment) {
		final String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.LIBRARY__COMMENT, oldComment, comment));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(final String newName) {
		final String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.LIBRARY__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSymbolicName() {
		return symbolicName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSymbolicName(final String newSymbolicName) {
		final String oldSymbolicName = symbolicName;
		symbolicName = newSymbolicName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryPackage.LIBRARY__SYMBOLIC_NAME, oldSymbolicName, symbolicName));
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
		case LibraryPackage.LIBRARY__INCLUDES:
			return basicSetIncludes(null, msgs);
		case LibraryPackage.LIBRARY__EXCLUDES:
			return basicSetExcludes(null, msgs);
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
		case LibraryPackage.LIBRARY__INCLUDES:
			return getIncludes();
		case LibraryPackage.LIBRARY__EXCLUDES:
			return getExcludes();
		case LibraryPackage.LIBRARY__ATTRIBUTE:
			return getAttribute();
		case LibraryPackage.LIBRARY__COMMENT:
			return getComment();
		case LibraryPackage.LIBRARY__NAME:
			return getName();
		case LibraryPackage.LIBRARY__SYMBOLIC_NAME:
			return getSymbolicName();
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
		case LibraryPackage.LIBRARY__INCLUDES:
			setIncludes((Includes)newValue);
			return;
		case LibraryPackage.LIBRARY__EXCLUDES:
			setExcludes((Excludes)newValue);
			return;
		case LibraryPackage.LIBRARY__ATTRIBUTE:
			setAttribute((String)newValue);
			return;
		case LibraryPackage.LIBRARY__COMMENT:
			setComment((String)newValue);
			return;
		case LibraryPackage.LIBRARY__NAME:
			setName((String)newValue);
			return;
		case LibraryPackage.LIBRARY__SYMBOLIC_NAME:
			setSymbolicName((String)newValue);
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
		case LibraryPackage.LIBRARY__INCLUDES:
			setIncludes((Includes)null);
			return;
		case LibraryPackage.LIBRARY__EXCLUDES:
			setExcludes((Excludes)null);
			return;
		case LibraryPackage.LIBRARY__ATTRIBUTE:
			unsetAttribute();
			return;
		case LibraryPackage.LIBRARY__COMMENT:
			setComment(COMMENT_EDEFAULT);
			return;
		case LibraryPackage.LIBRARY__NAME:
			setName(NAME_EDEFAULT);
			return;
		case LibraryPackage.LIBRARY__SYMBOLIC_NAME:
			setSymbolicName(SYMBOLIC_NAME_EDEFAULT);
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
		case LibraryPackage.LIBRARY__INCLUDES:
			return includes != null;
		case LibraryPackage.LIBRARY__EXCLUDES:
			return excludes != null;
		case LibraryPackage.LIBRARY__ATTRIBUTE:
			return isSetAttribute();
		case LibraryPackage.LIBRARY__COMMENT:
			return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
		case LibraryPackage.LIBRARY__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case LibraryPackage.LIBRARY__SYMBOLIC_NAME:
			return SYMBOLIC_NAME_EDEFAULT == null ? symbolicName != null : !SYMBOLIC_NAME_EDEFAULT.equals(symbolicName);
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
		if (attributeESet) {
			result.append(attribute);
		}
		else {
			result.append("<unset>"); //$NON-NLS-1$
		}
		result.append(", comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", symbolicName: "); //$NON-NLS-1$
		result.append(symbolicName);
		result.append(')');
		return result.toString();
	}

} //LibraryImpl
