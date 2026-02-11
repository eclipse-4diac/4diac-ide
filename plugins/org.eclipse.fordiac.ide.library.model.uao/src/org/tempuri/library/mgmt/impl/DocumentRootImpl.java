/**
 */
package org.tempuri.library.mgmt.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.tempuri.library.mgmt.DependenciesType;
import org.tempuri.library.mgmt.DependencyType;
import org.tempuri.library.mgmt.DocumentRoot;
import org.tempuri.library.mgmt.LibraryType;
import org.tempuri.library.mgmt.LicenseType;
import org.tempuri.library.mgmt.ManifestType;
import org.tempuri.library.mgmt.MgmtPackage;
import org.tempuri.library.mgmt.ReadMeType;
import org.tempuri.library.mgmt.SourcesType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getDependency <em>Dependency</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getLibrary <em>Library</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getLicense <em>License</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getManifest <em>Manifest</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getPath <em>Path</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getReadMe <em>Read Me</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getSources <em>Sources</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.impl.DocumentRootImpl#getVendor <em>Vendor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocumentRootImpl extends MinimalEObjectImpl.Container implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

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
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MgmtPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, MgmtPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, MgmtPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, MgmtPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return (String)getMixed().get(MgmtPackage.Literals.DOCUMENT_ROOT__COMMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		((FeatureMap.Internal)getMixed()).set(MgmtPackage.Literals.DOCUMENT_ROOT__COMMENT, newComment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependenciesType getDependencies() {
		return (DependenciesType)getMixed().get(MgmtPackage.Literals.DOCUMENT_ROOT__DEPENDENCIES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDependencies(DependenciesType newDependencies, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MgmtPackage.Literals.DOCUMENT_ROOT__DEPENDENCIES, newDependencies, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDependencies(DependenciesType newDependencies) {
		((FeatureMap.Internal)getMixed()).set(MgmtPackage.Literals.DOCUMENT_ROOT__DEPENDENCIES, newDependencies);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependencyType getDependency() {
		return (DependencyType)getMixed().get(MgmtPackage.Literals.DOCUMENT_ROOT__DEPENDENCY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDependency(DependencyType newDependency, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MgmtPackage.Literals.DOCUMENT_ROOT__DEPENDENCY, newDependency, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDependency(DependencyType newDependency) {
		((FeatureMap.Internal)getMixed()).set(MgmtPackage.Literals.DOCUMENT_ROOT__DEPENDENCY, newDependency);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryType getLibrary() {
		return (LibraryType)getMixed().get(MgmtPackage.Literals.DOCUMENT_ROOT__LIBRARY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLibrary(LibraryType newLibrary, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MgmtPackage.Literals.DOCUMENT_ROOT__LIBRARY, newLibrary, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLibrary(LibraryType newLibrary) {
		((FeatureMap.Internal)getMixed()).set(MgmtPackage.Literals.DOCUMENT_ROOT__LIBRARY, newLibrary);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LicenseType getLicense() {
		return (LicenseType)getMixed().get(MgmtPackage.Literals.DOCUMENT_ROOT__LICENSE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLicense(LicenseType newLicense, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MgmtPackage.Literals.DOCUMENT_ROOT__LICENSE, newLicense, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLicense(LicenseType newLicense) {
		((FeatureMap.Internal)getMixed()).set(MgmtPackage.Literals.DOCUMENT_ROOT__LICENSE, newLicense);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManifestType getManifest() {
		return (ManifestType)getMixed().get(MgmtPackage.Literals.DOCUMENT_ROOT__MANIFEST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetManifest(ManifestType newManifest, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MgmtPackage.Literals.DOCUMENT_ROOT__MANIFEST, newManifest, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setManifest(ManifestType newManifest) {
		((FeatureMap.Internal)getMixed()).set(MgmtPackage.Literals.DOCUMENT_ROOT__MANIFEST, newManifest);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return (String)getMixed().get(MgmtPackage.Literals.DOCUMENT_ROOT__PATH, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		((FeatureMap.Internal)getMixed()).set(MgmtPackage.Literals.DOCUMENT_ROOT__PATH, newPath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReadMeType getReadMe() {
		return (ReadMeType)getMixed().get(MgmtPackage.Literals.DOCUMENT_ROOT__READ_ME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReadMe(ReadMeType newReadMe, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MgmtPackage.Literals.DOCUMENT_ROOT__READ_ME, newReadMe, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadMe(ReadMeType newReadMe) {
		((FeatureMap.Internal)getMixed()).set(MgmtPackage.Literals.DOCUMENT_ROOT__READ_ME, newReadMe);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourcesType getSources() {
		return (SourcesType)getMixed().get(MgmtPackage.Literals.DOCUMENT_ROOT__SOURCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSources(SourcesType newSources, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MgmtPackage.Literals.DOCUMENT_ROOT__SOURCES, newSources, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSources(SourcesType newSources) {
		((FeatureMap.Internal)getMixed()).set(MgmtPackage.Literals.DOCUMENT_ROOT__SOURCES, newSources);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVendor() {
		return (String)getMixed().get(MgmtPackage.Literals.DOCUMENT_ROOT__VENDOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVendor(String newVendor) {
		((FeatureMap.Internal)getMixed()).set(MgmtPackage.Literals.DOCUMENT_ROOT__VENDOR, newVendor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MgmtPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case MgmtPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case MgmtPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case MgmtPackage.DOCUMENT_ROOT__DEPENDENCIES:
				return basicSetDependencies(null, msgs);
			case MgmtPackage.DOCUMENT_ROOT__DEPENDENCY:
				return basicSetDependency(null, msgs);
			case MgmtPackage.DOCUMENT_ROOT__LIBRARY:
				return basicSetLibrary(null, msgs);
			case MgmtPackage.DOCUMENT_ROOT__LICENSE:
				return basicSetLicense(null, msgs);
			case MgmtPackage.DOCUMENT_ROOT__MANIFEST:
				return basicSetManifest(null, msgs);
			case MgmtPackage.DOCUMENT_ROOT__READ_ME:
				return basicSetReadMe(null, msgs);
			case MgmtPackage.DOCUMENT_ROOT__SOURCES:
				return basicSetSources(null, msgs);
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
			case MgmtPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case MgmtPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case MgmtPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case MgmtPackage.DOCUMENT_ROOT__COMMENT:
				return getComment();
			case MgmtPackage.DOCUMENT_ROOT__DEPENDENCIES:
				return getDependencies();
			case MgmtPackage.DOCUMENT_ROOT__DEPENDENCY:
				return getDependency();
			case MgmtPackage.DOCUMENT_ROOT__LIBRARY:
				return getLibrary();
			case MgmtPackage.DOCUMENT_ROOT__LICENSE:
				return getLicense();
			case MgmtPackage.DOCUMENT_ROOT__MANIFEST:
				return getManifest();
			case MgmtPackage.DOCUMENT_ROOT__PATH:
				return getPath();
			case MgmtPackage.DOCUMENT_ROOT__READ_ME:
				return getReadMe();
			case MgmtPackage.DOCUMENT_ROOT__SOURCES:
				return getSources();
			case MgmtPackage.DOCUMENT_ROOT__VENDOR:
				return getVendor();
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
			case MgmtPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__COMMENT:
				setComment((String)newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__DEPENDENCIES:
				setDependencies((DependenciesType)newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__DEPENDENCY:
				setDependency((DependencyType)newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__LIBRARY:
				setLibrary((LibraryType)newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__LICENSE:
				setLicense((LicenseType)newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__MANIFEST:
				setManifest((ManifestType)newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__PATH:
				setPath((String)newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__READ_ME:
				setReadMe((ReadMeType)newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__SOURCES:
				setSources((SourcesType)newValue);
				return;
			case MgmtPackage.DOCUMENT_ROOT__VENDOR:
				setVendor((String)newValue);
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
			case MgmtPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case MgmtPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case MgmtPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case MgmtPackage.DOCUMENT_ROOT__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case MgmtPackage.DOCUMENT_ROOT__DEPENDENCIES:
				setDependencies((DependenciesType)null);
				return;
			case MgmtPackage.DOCUMENT_ROOT__DEPENDENCY:
				setDependency((DependencyType)null);
				return;
			case MgmtPackage.DOCUMENT_ROOT__LIBRARY:
				setLibrary((LibraryType)null);
				return;
			case MgmtPackage.DOCUMENT_ROOT__LICENSE:
				setLicense((LicenseType)null);
				return;
			case MgmtPackage.DOCUMENT_ROOT__MANIFEST:
				setManifest((ManifestType)null);
				return;
			case MgmtPackage.DOCUMENT_ROOT__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case MgmtPackage.DOCUMENT_ROOT__READ_ME:
				setReadMe((ReadMeType)null);
				return;
			case MgmtPackage.DOCUMENT_ROOT__SOURCES:
				setSources((SourcesType)null);
				return;
			case MgmtPackage.DOCUMENT_ROOT__VENDOR:
				setVendor(VENDOR_EDEFAULT);
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
			case MgmtPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case MgmtPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case MgmtPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case MgmtPackage.DOCUMENT_ROOT__COMMENT:
				return COMMENT_EDEFAULT == null ? getComment() != null : !COMMENT_EDEFAULT.equals(getComment());
			case MgmtPackage.DOCUMENT_ROOT__DEPENDENCIES:
				return getDependencies() != null;
			case MgmtPackage.DOCUMENT_ROOT__DEPENDENCY:
				return getDependency() != null;
			case MgmtPackage.DOCUMENT_ROOT__LIBRARY:
				return getLibrary() != null;
			case MgmtPackage.DOCUMENT_ROOT__LICENSE:
				return getLicense() != null;
			case MgmtPackage.DOCUMENT_ROOT__MANIFEST:
				return getManifest() != null;
			case MgmtPackage.DOCUMENT_ROOT__PATH:
				return PATH_EDEFAULT == null ? getPath() != null : !PATH_EDEFAULT.equals(getPath());
			case MgmtPackage.DOCUMENT_ROOT__READ_ME:
				return getReadMe() != null;
			case MgmtPackage.DOCUMENT_ROOT__SOURCES:
				return getSources() != null;
			case MgmtPackage.DOCUMENT_ROOT__VENDOR:
				return VENDOR_EDEFAULT == null ? getVendor() != null : !VENDOR_EDEFAULT.equals(getVendor());
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
