/**
 */
package org.tempuri.library.mgmt.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.tempuri.library.mgmt.LibraryType;
import org.tempuri.library.mgmt.LicenseType;
import org.tempuri.library.mgmt.MgmtPackage;
import org.tempuri.library.mgmt.ReadMeType;
import org.tempuri.library.mgmt.SourcesType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Library Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.tempuri.library.mgmt.impl.LibraryTypeImpl#getReadMe <em>Read Me</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.LibraryTypeImpl#getSources <em>Sources</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.LibraryTypeImpl#getVendor <em>Vendor</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.LibraryTypeImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.LibraryTypeImpl#getLicense <em>License</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.LibraryTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.LibraryTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.LibraryTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LibraryTypeImpl extends MinimalEObjectImpl.Container implements LibraryType {
	/**
	 * The cached value of the '{@link #getReadMe() <em>Read Me</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReadMe()
	 * @generated
	 * @ordered
	 */
	protected ReadMeType readMe;

	/**
	 * The cached value of the '{@link #getSources() <em>Sources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSources()
	 * @generated
	 * @ordered
	 */
	protected SourcesType sources;

	/**
	 * The default value of the '{@link #getVendor() <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendor()
	 * @generated
	 * @ordered
	 */
	protected static final String VENDOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVendor() <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendor()
	 * @generated
	 * @ordered
	 */
	protected String vendor = VENDOR_EDEFAULT;

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
	 * The cached value of the '{@link #getLicense() <em>License</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLicense()
	 * @generated
	 * @ordered
	 */
	protected LicenseType license;

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
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LibraryTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MgmtPackage.Literals.LIBRARY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReadMeType getReadMe() {
		return readMe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReadMe(ReadMeType newReadMe, NotificationChain msgs) {
		ReadMeType oldReadMe = readMe;
		readMe = newReadMe;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__READ_ME, oldReadMe, newReadMe);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadMe(ReadMeType newReadMe) {
		if (newReadMe != readMe) {
			NotificationChain msgs = null;
			if (readMe != null)
				msgs = ((InternalEObject)readMe).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MgmtPackage.LIBRARY_TYPE__READ_ME, null, msgs);
			if (newReadMe != null)
				msgs = ((InternalEObject)newReadMe).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MgmtPackage.LIBRARY_TYPE__READ_ME, null, msgs);
			msgs = basicSetReadMe(newReadMe, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__READ_ME, newReadMe, newReadMe));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourcesType getSources() {
		return sources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSources(SourcesType newSources, NotificationChain msgs) {
		SourcesType oldSources = sources;
		sources = newSources;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__SOURCES, oldSources, newSources);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSources(SourcesType newSources) {
		if (newSources != sources) {
			NotificationChain msgs = null;
			if (sources != null)
				msgs = ((InternalEObject)sources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MgmtPackage.LIBRARY_TYPE__SOURCES, null, msgs);
			if (newSources != null)
				msgs = ((InternalEObject)newSources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MgmtPackage.LIBRARY_TYPE__SOURCES, null, msgs);
			msgs = basicSetSources(newSources, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__SOURCES, newSources, newSources));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVendor(String newVendor) {
		String oldVendor = vendor;
		vendor = newVendor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__VENDOR, oldVendor, vendor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LicenseType getLicense() {
		return license;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLicense(LicenseType newLicense, NotificationChain msgs) {
		LicenseType oldLicense = license;
		license = newLicense;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__LICENSE, oldLicense, newLicense);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLicense(LicenseType newLicense) {
		if (newLicense != license) {
			NotificationChain msgs = null;
			if (license != null)
				msgs = ((InternalEObject)license).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MgmtPackage.LIBRARY_TYPE__LICENSE, null, msgs);
			if (newLicense != null)
				msgs = ((InternalEObject)newLicense).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MgmtPackage.LIBRARY_TYPE__LICENSE, null, msgs);
			msgs = basicSetLicense(newLicense, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__LICENSE, newLicense, newLicense));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MgmtPackage.LIBRARY_TYPE__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MgmtPackage.LIBRARY_TYPE__READ_ME:
				return basicSetReadMe(null, msgs);
			case MgmtPackage.LIBRARY_TYPE__SOURCES:
				return basicSetSources(null, msgs);
			case MgmtPackage.LIBRARY_TYPE__LICENSE:
				return basicSetLicense(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MgmtPackage.LIBRARY_TYPE__READ_ME:
				return getReadMe();
			case MgmtPackage.LIBRARY_TYPE__SOURCES:
				return getSources();
			case MgmtPackage.LIBRARY_TYPE__VENDOR:
				return getVendor();
			case MgmtPackage.LIBRARY_TYPE__COMMENT:
				return getComment();
			case MgmtPackage.LIBRARY_TYPE__LICENSE:
				return getLicense();
			case MgmtPackage.LIBRARY_TYPE__NAME:
				return getName();
			case MgmtPackage.LIBRARY_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case MgmtPackage.LIBRARY_TYPE__VERSION:
				return getVersion();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MgmtPackage.LIBRARY_TYPE__READ_ME:
				setReadMe((ReadMeType)newValue);
				return;
			case MgmtPackage.LIBRARY_TYPE__SOURCES:
				setSources((SourcesType)newValue);
				return;
			case MgmtPackage.LIBRARY_TYPE__VENDOR:
				setVendor((String)newValue);
				return;
			case MgmtPackage.LIBRARY_TYPE__COMMENT:
				setComment((String)newValue);
				return;
			case MgmtPackage.LIBRARY_TYPE__LICENSE:
				setLicense((LicenseType)newValue);
				return;
			case MgmtPackage.LIBRARY_TYPE__NAME:
				setName((String)newValue);
				return;
			case MgmtPackage.LIBRARY_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case MgmtPackage.LIBRARY_TYPE__VERSION:
				setVersion((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MgmtPackage.LIBRARY_TYPE__READ_ME:
				setReadMe((ReadMeType)null);
				return;
			case MgmtPackage.LIBRARY_TYPE__SOURCES:
				setSources((SourcesType)null);
				return;
			case MgmtPackage.LIBRARY_TYPE__VENDOR:
				setVendor(VENDOR_EDEFAULT);
				return;
			case MgmtPackage.LIBRARY_TYPE__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case MgmtPackage.LIBRARY_TYPE__LICENSE:
				setLicense((LicenseType)null);
				return;
			case MgmtPackage.LIBRARY_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MgmtPackage.LIBRARY_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case MgmtPackage.LIBRARY_TYPE__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MgmtPackage.LIBRARY_TYPE__READ_ME:
				return readMe != null;
			case MgmtPackage.LIBRARY_TYPE__SOURCES:
				return sources != null;
			case MgmtPackage.LIBRARY_TYPE__VENDOR:
				return VENDOR_EDEFAULT == null ? vendor != null : !VENDOR_EDEFAULT.equals(vendor);
			case MgmtPackage.LIBRARY_TYPE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case MgmtPackage.LIBRARY_TYPE__LICENSE:
				return license != null;
			case MgmtPackage.LIBRARY_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MgmtPackage.LIBRARY_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case MgmtPackage.LIBRARY_TYPE__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
		}
		return super.eIsSet(featureID);
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
		result.append(" (vendor: ");
		result.append(vendor);
		result.append(", comment: ");
		result.append(comment);
		result.append(", name: ");
		result.append(name);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //LibraryTypeImpl
