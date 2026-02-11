/**
 */
package org.tempuri.library.mgmt;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getComment <em>Comment</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getDependency <em>Dependency</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getLibrary <em>Library</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getLicense <em>License</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getManifest <em>Manifest</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getPath <em>Path</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getReadMe <em>Read Me</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getSources <em>Sources</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.DocumentRoot#getVendor <em>Vendor</em>}</li>
 * </ul>
 *
 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_Comment()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Comment' namespace='##targetNamespace'"
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.DocumentRoot#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependencies</em>' containment reference.
	 * @see #setDependencies(DependenciesType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_Dependencies()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Dependencies' namespace='##targetNamespace'"
	 * @generated
	 */
	DependenciesType getDependencies();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.DocumentRoot#getDependencies <em>Dependencies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dependencies</em>' containment reference.
	 * @see #getDependencies()
	 * @generated
	 */
	void setDependencies(DependenciesType value);

	/**
	 * Returns the value of the '<em><b>Dependency</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependency</em>' containment reference.
	 * @see #setDependency(DependencyType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_Dependency()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Dependency' namespace='##targetNamespace'"
	 * @generated
	 */
	DependencyType getDependency();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.DocumentRoot#getDependency <em>Dependency</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dependency</em>' containment reference.
	 * @see #getDependency()
	 * @generated
	 */
	void setDependency(DependencyType value);

	/**
	 * Returns the value of the '<em><b>Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library</em>' containment reference.
	 * @see #setLibrary(LibraryType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_Library()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Library' namespace='##targetNamespace'"
	 * @generated
	 */
	LibraryType getLibrary();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.DocumentRoot#getLibrary <em>Library</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Library</em>' containment reference.
	 * @see #getLibrary()
	 * @generated
	 */
	void setLibrary(LibraryType value);

	/**
	 * Returns the value of the '<em><b>License</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>License</em>' containment reference.
	 * @see #setLicense(LicenseType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_License()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='License' namespace='##targetNamespace'"
	 * @generated
	 */
	LicenseType getLicense();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.DocumentRoot#getLicense <em>License</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>License</em>' containment reference.
	 * @see #getLicense()
	 * @generated
	 */
	void setLicense(LicenseType value);

	/**
	 * Returns the value of the '<em><b>Manifest</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manifest</em>' containment reference.
	 * @see #setManifest(ManifestType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_Manifest()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Manifest' namespace='##targetNamespace'"
	 * @generated
	 */
	ManifestType getManifest();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.DocumentRoot#getManifest <em>Manifest</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manifest</em>' containment reference.
	 * @see #getManifest()
	 * @generated
	 */
	void setManifest(ManifestType value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_Path()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Path' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.DocumentRoot#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Read Me</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Me</em>' containment reference.
	 * @see #setReadMe(ReadMeType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_ReadMe()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ReadMe' namespace='##targetNamespace'"
	 * @generated
	 */
	ReadMeType getReadMe();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.DocumentRoot#getReadMe <em>Read Me</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Me</em>' containment reference.
	 * @see #getReadMe()
	 * @generated
	 */
	void setReadMe(ReadMeType value);

	/**
	 * Returns the value of the '<em><b>Sources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sources</em>' containment reference.
	 * @see #setSources(SourcesType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_Sources()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Sources' namespace='##targetNamespace'"
	 * @generated
	 */
	SourcesType getSources();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.DocumentRoot#getSources <em>Sources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sources</em>' containment reference.
	 * @see #getSources()
	 * @generated
	 */
	void setSources(SourcesType value);

	/**
	 * Returns the value of the '<em><b>Vendor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vendor</em>' attribute.
	 * @see #setVendor(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDocumentRoot_Vendor()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Vendor' namespace='##targetNamespace'"
	 * @generated
	 */
	String getVendor();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.DocumentRoot#getVendor <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vendor</em>' attribute.
	 * @see #getVendor()
	 * @generated
	 */
	void setVendor(String value);

} // DocumentRoot
