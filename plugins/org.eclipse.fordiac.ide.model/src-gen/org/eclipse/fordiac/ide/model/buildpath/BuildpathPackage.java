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
package org.eclipse.fordiac.ide.model.buildpath;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathFactory
 * @model kind="package"
 * @generated
 */
public interface BuildpathPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "buildpath"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/4diac/xml/buildpath.xsd"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "buildpath"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BuildpathPackage eINSTANCE = org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.buildpath.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.AttributeImpl
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathImpl <em>Buildpath</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathImpl
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getBuildpath()
	 * @generated
	 */
	int BUILDPATH = 1;

	/**
	 * The feature id for the '<em><b>Source Folders</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDPATH__SOURCE_FOLDERS = 0;

	/**
	 * The number of structural features of the '<em>Buildpath</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDPATH_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.buildpath.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.DocumentRootImpl
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 2;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Buildpath</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BUILDPATH = 3;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.buildpath.impl.SourceFolderImpl <em>Source Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.SourceFolderImpl
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getSourceFolder()
	 * @generated
	 */
	int SOURCE_FOLDER = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.buildpath.PatternType <em>Pattern Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.buildpath.PatternType
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getPatternType()
	 * @generated
	 */
	int PATTERN_TYPE = 5;

	/**
	 * The meta object id for the '<em>Pattern Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.buildpath.PatternType
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getPatternTypeObject()
	 * @generated
	 */
	int PATTERN_TYPE_OBJECT = 6;

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.buildpath.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.buildpath.Attribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.Attribute#getName()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.buildpath.Attribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.Attribute#getValue()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Value();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.buildpath.impl.PatternImpl <em>Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.PatternImpl
	 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getPattern()
	 * @generated
	 */
	int PATTERN = 3;


	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Syntax</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN__SYNTAX = 1;

	/**
	 * The number of structural features of the '<em>Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Includes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FOLDER__INCLUDES = 0;

	/**
	 * The feature id for the '<em><b>Excludes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FOLDER__EXCLUDES = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FOLDER__ATTRIBUTES = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FOLDER__NAME = 3;

	/**
	 * The feature id for the '<em><b>Buildpath</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FOLDER__BUILDPATH = 4;

	/**
	 * The number of structural features of the '<em>Source Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FOLDER_FEATURE_COUNT = 5;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.buildpath.Buildpath <em>Buildpath</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Buildpath</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.Buildpath
	 * @generated
	 */
	EClass getBuildpath();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.buildpath.Buildpath#getSourceFolders <em>Source Folders</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source Folders</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.Buildpath#getSourceFolders()
	 * @see #getBuildpath()
	 * @generated
	 */
	EReference getBuildpath_SourceFolders();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.buildpath.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fordiac.ide.model.buildpath.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fordiac.ide.model.buildpath.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fordiac.ide.model.buildpath.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.buildpath.DocumentRoot#getBuildpath <em>Buildpath</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Buildpath</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.DocumentRoot#getBuildpath()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Buildpath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder <em>Source Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Folder</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.SourceFolder
	 * @generated
	 */
	EClass getSourceFolder();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getIncludes <em>Includes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Includes</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getIncludes()
	 * @see #getSourceFolder()
	 * @generated
	 */
	EReference getSourceFolder_Includes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getExcludes <em>Excludes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Excludes</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getExcludes()
	 * @see #getSourceFolder()
	 * @generated
	 */
	EReference getSourceFolder_Excludes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getAttributes()
	 * @see #getSourceFolder()
	 * @generated
	 */
	EReference getSourceFolder_Attributes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getName()
	 * @see #getSourceFolder()
	 * @generated
	 */
	EAttribute getSourceFolder_Name();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getBuildpath <em>Buildpath</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Buildpath</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getBuildpath()
	 * @see #getSourceFolder()
	 * @generated
	 */
	EReference getSourceFolder_Buildpath();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.model.buildpath.PatternType <em>Pattern Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Pattern Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.PatternType
	 * @generated
	 */
	EEnum getPatternType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.fordiac.ide.model.buildpath.PatternType <em>Pattern Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pattern Type Object</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.PatternType
	 * @model instanceClass="org.eclipse.fordiac.ide.model.buildpath.PatternType"
	 *        extendedMetaData="name='patternType:Object' baseType='patternType'"
	 * @generated
	 */
	EDataType getPatternTypeObject();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.buildpath.Pattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pattern</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.Pattern
	 * @generated
	 */
	EClass getPattern();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.buildpath.Pattern#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.Pattern#getValue()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.buildpath.Pattern#getSyntax <em>Syntax</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Syntax</em>'.
	 * @see org.eclipse.fordiac.ide.model.buildpath.Pattern#getSyntax()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_Syntax();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BuildpathFactory getBuildpathFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.buildpath.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.AttributeImpl
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__NAME = eINSTANCE.getAttribute_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__VALUE = eINSTANCE.getAttribute_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathImpl <em>Buildpath</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathImpl
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getBuildpath()
		 * @generated
		 */
		EClass BUILDPATH = eINSTANCE.getBuildpath();

		/**
		 * The meta object literal for the '<em><b>Source Folders</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUILDPATH__SOURCE_FOLDERS = eINSTANCE.getBuildpath_SourceFolders();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.buildpath.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.DocumentRootImpl
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Buildpath</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BUILDPATH = eINSTANCE.getDocumentRoot_Buildpath();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.buildpath.impl.SourceFolderImpl <em>Source Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.SourceFolderImpl
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getSourceFolder()
		 * @generated
		 */
		EClass SOURCE_FOLDER = eINSTANCE.getSourceFolder();

		/**
		 * The meta object literal for the '<em><b>Includes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_FOLDER__INCLUDES = eINSTANCE.getSourceFolder_Includes();

		/**
		 * The meta object literal for the '<em><b>Excludes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_FOLDER__EXCLUDES = eINSTANCE.getSourceFolder_Excludes();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_FOLDER__ATTRIBUTES = eINSTANCE.getSourceFolder_Attributes();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_FOLDER__NAME = eINSTANCE.getSourceFolder_Name();

		/**
		 * The meta object literal for the '<em><b>Buildpath</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_FOLDER__BUILDPATH = eINSTANCE.getSourceFolder_Buildpath();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.buildpath.PatternType <em>Pattern Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.buildpath.PatternType
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getPatternType()
		 * @generated
		 */
		EEnum PATTERN_TYPE = eINSTANCE.getPatternType();

		/**
		 * The meta object literal for the '<em>Pattern Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.buildpath.PatternType
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getPatternTypeObject()
		 * @generated
		 */
		EDataType PATTERN_TYPE_OBJECT = eINSTANCE.getPatternTypeObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.buildpath.impl.PatternImpl <em>Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.PatternImpl
		 * @see org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl#getPattern()
		 * @generated
		 */
		EClass PATTERN = eINSTANCE.getPattern();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATTERN__VALUE = eINSTANCE.getPattern_Value();

		/**
		 * The meta object literal for the '<em><b>Syntax</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATTERN__SYNTAX = eINSTANCE.getPattern_Syntax();

	}

} //BuildpathPackage
