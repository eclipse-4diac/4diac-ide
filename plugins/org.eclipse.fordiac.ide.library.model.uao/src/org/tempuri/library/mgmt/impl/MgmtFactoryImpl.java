/**
 */
package org.tempuri.library.mgmt.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.tempuri.library.mgmt.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MgmtFactoryImpl extends EFactoryImpl implements MgmtFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MgmtFactory init() {
		try {
			MgmtFactory theMgmtFactory = (MgmtFactory)EPackage.Registry.INSTANCE.getEFactory(MgmtPackage.eNS_URI);
			if (theMgmtFactory != null) {
				return theMgmtFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MgmtFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MgmtFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MgmtPackage.DEPENDENCIES_TYPE: return createDependenciesType();
			case MgmtPackage.DEPENDENCY_TYPE: return createDependencyType();
			case MgmtPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case MgmtPackage.LIBRARY_TYPE: return createLibraryType();
			case MgmtPackage.LICENSE_TYPE: return createLicenseType();
			case MgmtPackage.MANIFEST_TYPE: return createManifestType();
			case MgmtPackage.READ_ME_TYPE: return createReadMeType();
			case MgmtPackage.SOURCES_TYPE: return createSourcesType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependenciesType createDependenciesType() {
		DependenciesTypeImpl dependenciesType = new DependenciesTypeImpl();
		return dependenciesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependencyType createDependencyType() {
		DependencyTypeImpl dependencyType = new DependencyTypeImpl();
		return dependencyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryType createLibraryType() {
		LibraryTypeImpl libraryType = new LibraryTypeImpl();
		return libraryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LicenseType createLicenseType() {
		LicenseTypeImpl licenseType = new LicenseTypeImpl();
		return licenseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManifestType createManifestType() {
		ManifestTypeImpl manifestType = new ManifestTypeImpl();
		return manifestType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReadMeType createReadMeType() {
		ReadMeTypeImpl readMeType = new ReadMeTypeImpl();
		return readMeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourcesType createSourcesType() {
		SourcesTypeImpl sourcesType = new SourcesTypeImpl();
		return sourcesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MgmtPackage getMgmtPackage() {
		return (MgmtPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MgmtPackage getPackage() {
		return MgmtPackage.eINSTANCE;
	}

} //MgmtFactoryImpl
