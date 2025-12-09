/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.buildpath.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.fordiac.ide.model.buildpath.Attribute;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathFactory;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage;
import org.eclipse.fordiac.ide.model.buildpath.DocumentRoot;
import org.eclipse.fordiac.ide.model.buildpath.Pattern;
import org.eclipse.fordiac.ide.model.buildpath.PatternType;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;

import org.eclipse.fordiac.ide.model.data.DataPackage;

import org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

import org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BuildpathPackageImpl extends EPackageImpl implements BuildpathPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass buildpathEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass patternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sourceFolderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum patternTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType patternTypeObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BuildpathPackageImpl() {
		super(eNS_URI, BuildpathFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link BuildpathPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BuildpathPackage init() {
		if (isInited) return (BuildpathPackage)EPackage.Registry.INSTANCE.getEPackage(BuildpathPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredBuildpathPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		BuildpathPackageImpl theBuildpathPackage = registeredBuildpathPackage instanceof BuildpathPackageImpl ? (BuildpathPackageImpl)registeredBuildpathPackage : new BuildpathPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);
		LibraryElementPackageImpl theLibraryElementPackage = (LibraryElementPackageImpl)(registeredPackage instanceof LibraryElementPackageImpl ? registeredPackage : LibraryElementPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);
		DataPackageImpl theDataPackage = (DataPackageImpl)(registeredPackage instanceof DataPackageImpl ? registeredPackage : DataPackage.eINSTANCE);

		// Create package meta-data objects
		theBuildpathPackage.createPackageContents();
		theLibraryElementPackage.createPackageContents();
		theDataPackage.createPackageContents();

		// Initialize created meta-data
		theBuildpathPackage.initializePackageContents();
		theLibraryElementPackage.initializePackageContents();
		theDataPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBuildpathPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BuildpathPackage.eNS_URI, theBuildpathPackage);
		return theBuildpathPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAttribute() {
		return attributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttribute_Name() {
		return (EAttribute)attributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttribute_Value() {
		return (EAttribute)attributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBuildpath() {
		return buildpathEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBuildpath_SourceFolders() {
		return (EReference)buildpathEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_Buildpath() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSourceFolder() {
		return sourceFolderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSourceFolder_Includes() {
		return (EReference)sourceFolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSourceFolder_Excludes() {
		return (EReference)sourceFolderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSourceFolder_Attributes() {
		return (EReference)sourceFolderEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSourceFolder_Name() {
		return (EAttribute)sourceFolderEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSourceFolder_Buildpath() {
		return (EReference)sourceFolderEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getPatternType() {
		return patternTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getPatternTypeObject() {
		return patternTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPattern() {
		return patternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPattern_Value() {
		return (EAttribute)patternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPattern_Syntax() {
		return (EAttribute)patternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BuildpathFactory getBuildpathFactory() {
		return (BuildpathFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		attributeEClass = createEClass(ATTRIBUTE);
		createEAttribute(attributeEClass, ATTRIBUTE__NAME);
		createEAttribute(attributeEClass, ATTRIBUTE__VALUE);

		buildpathEClass = createEClass(BUILDPATH);
		createEReference(buildpathEClass, BUILDPATH__SOURCE_FOLDERS);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BUILDPATH);

		patternEClass = createEClass(PATTERN);
		createEAttribute(patternEClass, PATTERN__VALUE);
		createEAttribute(patternEClass, PATTERN__SYNTAX);

		sourceFolderEClass = createEClass(SOURCE_FOLDER);
		createEReference(sourceFolderEClass, SOURCE_FOLDER__INCLUDES);
		createEReference(sourceFolderEClass, SOURCE_FOLDER__EXCLUDES);
		createEReference(sourceFolderEClass, SOURCE_FOLDER__ATTRIBUTES);
		createEAttribute(sourceFolderEClass, SOURCE_FOLDER__NAME);
		createEReference(sourceFolderEClass, SOURCE_FOLDER__BUILDPATH);

		// Create enums
		patternTypeEEnum = createEEnum(PATTERN_TYPE);

		// Create data types
		patternTypeObjectEDataType = createEDataType(PATTERN_TYPE_OBJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(attributeEClass, Attribute.class, "Attribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAttribute_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAttribute_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(buildpathEClass, Buildpath.class, "Buildpath", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getBuildpath_SourceFolders(), this.getSourceFolder(), this.getSourceFolder_Buildpath(), "sourceFolders", null, 0, -1, Buildpath.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDocumentRoot_Buildpath(), this.getBuildpath(), null, "buildpath", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(patternEClass, Pattern.class, "Pattern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPattern_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, Pattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPattern_Syntax(), this.getPatternType(), "syntax", "glob", 0, 1, Pattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(sourceFolderEClass, SourceFolder.class, "SourceFolder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSourceFolder_Includes(), this.getPattern(), null, "includes", null, 0, -1, SourceFolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSourceFolder_Excludes(), this.getPattern(), null, "excludes", null, 0, -1, SourceFolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSourceFolder_Attributes(), this.getAttribute(), null, "attributes", null, 0, -1, SourceFolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSourceFolder_Name(), theXMLTypePackage.getAnyURI(), "name", null, 1, 1, SourceFolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSourceFolder_Buildpath(), this.getBuildpath(), this.getBuildpath_SourceFolders(), "buildpath", null, 0, 1, SourceFolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(patternTypeEEnum, PatternType.class, "PatternType"); //$NON-NLS-1$
		addEEnumLiteral(patternTypeEEnum, PatternType.GLOB);
		addEEnumLiteral(patternTypeEEnum, PatternType.REGEX);

		// Initialize data types
		initEDataType(patternTypeObjectEDataType, PatternType.class, "PatternTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$
		addAnnotation
		  (attributeEClass,
		   source,
		   new String[] {
			   "name", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getAttribute_Name(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "name" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getAttribute_Value(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "value" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (buildpathEClass,
		   source,
		   new String[] {
			   "name", "buildpath", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getBuildpath_SourceFolders(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "sourceFolder" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (documentRootEClass,
		   source,
		   new String[] {
			   "name", "", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getDocumentRoot_Mixed(),
		   source,
		   new String[] {
			   "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", ":mixed" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "xmlns:prefix" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "xsi:schemaLocation" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getDocumentRoot_Buildpath(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "buildpath", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (patternEClass,
		   source,
		   new String[] {
			   "name", "pattern", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getPattern_Value(),
		   source,
		   new String[] {
			   "name", ":0", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getPattern_Syntax(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "syntax" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (patternTypeEEnum,
		   source,
		   new String[] {
			   "name", "patternType" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (patternTypeObjectEDataType,
		   source,
		   new String[] {
			   "name", "patternType:Object", //$NON-NLS-1$ //$NON-NLS-2$
			   "baseType", "patternType" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (sourceFolderEClass,
		   source,
		   new String[] {
			   "name", "sourceFolder", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSourceFolder_Includes(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "include" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSourceFolder_Excludes(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "exclude" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSourceFolder_Attributes(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "attribute" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSourceFolder_Name(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "name" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

} //BuildpathPackageImpl
