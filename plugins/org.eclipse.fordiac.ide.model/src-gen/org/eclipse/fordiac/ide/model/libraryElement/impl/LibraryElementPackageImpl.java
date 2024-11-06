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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import static org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage.RESOURCE;

import java.util.List;
import java.util.stream.Stream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage;
import org.eclipse.fordiac.ide.model.buildpath.impl.BuildpathPackageImpl;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl;
import org.eclipse.fordiac.ide.model.libraryElement.*;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.CommandStack;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class LibraryElementPackageImpl extends EPackageImpl implements LibraryElementPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adapterConnectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adapterDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adapterFBEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adapterTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass algorithmEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arraySizeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass baseFBTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass basicFBTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass automationSystemEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cfbInstanceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorizableElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communicationChannelEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communicationConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communicationMappingTargetEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compilerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compilerInfoEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositeFBTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurableObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurableFBEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurableMoveFBEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectionRoutingDataEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataConnectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass demultiplexerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deviceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deviceTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ecActionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eccEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ecStateEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ecTransitionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass errorMarkerDataTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass errorMarkerFBNElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass errorMarkerInterfaceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventConnectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fbEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fbNetworkEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fbNetworkElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fbTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionBodyEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionFBTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalConstantsEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass groupEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hiddenElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iCallableEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identificationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iInterfaceElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iNamedElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inputPrimitiveEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interfaceListEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iTypedElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iVarElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass libraryElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localVariableEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mappingEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mappingTargetEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass methodEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiplexerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass originalSourceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass otherAlgorithmEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass otherMethodEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass outputPrimitiveEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass positionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass positionableElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass primitiveEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceTypeNameEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceTypeFBEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass segmentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass segmentTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serviceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serviceSequenceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serviceTransactionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serviceInterfaceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serviceInterfaceFBTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleFBTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stAlgorithmEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stFunctionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stFunctionBodyEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stMethodEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subAppEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structManipulatorEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subAppTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass systemConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textAlgorithmEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textFunctionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textFunctionBodyEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textMethodEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typedConfigureableObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typedSubAppEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass untypedSubAppEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass varDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionInfoEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass withEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass memberVarDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum languageEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType commandStackEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iFileEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType interfaceElementStreamEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iProjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType namedElementStreamEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pointEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType typeEntryEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType typeLibraryEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType varDeclListEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LibraryElementPackageImpl() {
		super(eNS_URI, LibraryElementFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and
	 * for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link LibraryElementPackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LibraryElementPackage init() {
		if (isInited) return (LibraryElementPackage)EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredLibraryElementPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		LibraryElementPackageImpl theLibraryElementPackage = registeredLibraryElementPackage instanceof LibraryElementPackageImpl ? (LibraryElementPackageImpl)registeredLibraryElementPackage : new LibraryElementPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);
		DataPackageImpl theDataPackage = (DataPackageImpl)(registeredPackage instanceof DataPackageImpl ? registeredPackage : DataPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BuildpathPackage.eNS_URI);
		BuildpathPackageImpl theBuildpathPackage = (BuildpathPackageImpl)(registeredPackage instanceof BuildpathPackageImpl ? registeredPackage : BuildpathPackage.eINSTANCE);

		// Create package meta-data objects
		theLibraryElementPackage.createPackageContents();
		theDataPackage.createPackageContents();
		theBuildpathPackage.createPackageContents();

		// Initialize created meta-data
		theLibraryElementPackage.initializePackageContents();
		theDataPackage.initializePackageContents();
		theBuildpathPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theLibraryElementPackage,
			 new EValidator.Descriptor() {
				 @Override
				 public EValidator getEValidator() {
					 return LibraryElementValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theLibraryElementPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LibraryElementPackage.eNS_URI, theLibraryElementPackage);
		return theLibraryElementPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAdapterConnection() {
		return adapterConnectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAdapterConnection_FBNetwork() {
		return (EReference)adapterConnectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAdapterDeclaration() {
		return adapterDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAdapterDeclaration_AdapterFB() {
		return (EReference)adapterDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAdapterDeclaration_InterfaceOnlyAdapterFB() {
		return (EReference)adapterDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAdapterFB() {
		return adapterFBEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAdapterFB_AdapterDecl() {
		return (EReference)adapterFBEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAdapterType() {
		return adapterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAdapterType_PlugType() {
		return (EReference)adapterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAlgorithm() {
		return algorithmEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getApplication() {
		return applicationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getApplication_FBNetwork() {
		return (EReference)applicationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArraySize() {
		return arraySizeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getArraySize_Value() {
		return (EAttribute)arraySizeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArraySize_VarDeclaration() {
		return (EReference)arraySizeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAttribute() {
		return attributeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttribute_AttributeDeclaration() {
		return (EReference)attributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttribute_Type() {
		return (EReference)attributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttribute_Value() {
		return (EAttribute)attributeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAttributeDeclaration() {
		return attributeDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttributeDeclaration_Type() {
		return (EReference)attributeDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBaseFBType() {
		return baseFBTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBaseFBType_InternalVars() {
		return (EReference)baseFBTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBaseFBType_InternalConstVars() {
		return (EReference)baseFBTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBaseFBType_InternalFbs() {
		return (EReference)baseFBTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBaseFBType_Algorithm() {
		return (EReference)baseFBTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBaseFBType_Methods() {
		return (EReference)baseFBTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBaseFBType_Callables() {
		return (EReference)baseFBTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBasicFBType() {
		return basicFBTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBasicFBType_ECC() {
		return (EReference)basicFBTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAutomationSystem() {
		return automationSystemEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAutomationSystem_Application() {
		return (EReference)automationSystemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAutomationSystem_Mapping() {
		return (EReference)automationSystemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAutomationSystem_SystemConfiguration() {
		return (EReference)automationSystemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAutomationSystem_CommandStack() {
		return (EAttribute)automationSystemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCFBInstance() {
		return cfbInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCFBInstance_CfbNetwork() {
		return (EReference)cfbInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getColor() {
		return colorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getColor_Red() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getColor_Green() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getColor_Blue() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getColorizableElement() {
		return colorizableElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getColorizableElement_Color() {
		return (EReference)colorizableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComment() {
		return commentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComment_Width() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComment_Height() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCommunicationChannel() {
		return communicationChannelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCommunicationConfiguration() {
		return communicationConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCommunicationMappingTarget() {
		return communicationMappingTargetEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCommunicationMappingTarget_MappedElements() {
		return (EReference)communicationMappingTargetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompiler() {
		return compilerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCompiler_Language() {
		return (EAttribute)compilerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCompiler_Product() {
		return (EAttribute)compilerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCompiler_Vendor() {
		return (EAttribute)compilerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCompiler_Version() {
		return (EAttribute)compilerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompilerInfo() {
		return compilerInfoEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompilerInfo_Compiler() {
		return (EReference)compilerInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCompilerInfo_Classdef() {
		return (EAttribute)compilerInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCompilerInfo_Header() {
		return (EAttribute)compilerInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCompilerInfo_PackageName() {
		return (EAttribute)compilerInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompilerInfo_Imports() {
		return (EReference)compilerInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompositeFBType() {
		return compositeFBTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompositeFBType_FBNetwork() {
		return (EReference)compositeFBTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConfigurableObject() {
		return configurableObjectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConfigurableObject_Attributes() {
		return (EReference)configurableObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConfigurableFB() {
		return configurableFBEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConfigurableFB_DataType() {
		return (EReference)configurableFBEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConfigurableMoveFB() {
		return configurableMoveFBEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConnection() {
		return connectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConnection_BrokenConnection() {
		return (EAttribute)connectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConnection_Source() {
		return (EReference)connectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConnection_Destination() {
		return (EReference)connectionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConnection_RoutingData() {
		return (EReference)connectionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConnection_Comment() {
		return (EAttribute)connectionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConnectionRoutingData() {
		return connectionRoutingDataEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConnectionRoutingData_Dx1() {
		return (EAttribute)connectionRoutingDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConnectionRoutingData_Dx2() {
		return (EAttribute)connectionRoutingDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConnectionRoutingData_Dy() {
		return (EAttribute)connectionRoutingDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConnectionRoutingData_NeedsValidation() {
		return (EAttribute)connectionRoutingDataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDataConnection() {
		return dataConnectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDataConnection_FBNetwork() {
		return (EReference)dataConnectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDemultiplexer() {
		return demultiplexerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDemultiplexer_IsConfigured() {
		return (EAttribute)demultiplexerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDevice() {
		return deviceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDevice_Resource() {
		return (EReference)deviceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDevice_Profile() {
		return (EAttribute)deviceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDevice_InConnections() {
		return (EReference)deviceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDeviceType() {
		return deviceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDeviceType_VarDeclaration() {
		return (EReference)deviceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDeviceType_ResourceTypeName() {
		return (EReference)deviceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDeviceType_Resource() {
		return (EReference)deviceTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDeviceType_FBNetwork() {
		return (EReference)deviceTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDeviceType_Profile() {
		return (EAttribute)deviceTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDeviceType_AttributeDeclarations() {
		return (EReference)deviceTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getECAction() {
		return ecActionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECAction_Algorithm() {
		return (EReference)ecActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECAction_Output() {
		return (EReference)ecActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECAction_ECState() {
		return (EReference)ecActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getECC() {
		return eccEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECC_ECState() {
		return (EReference)eccEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECC_ECTransition() {
		return (EReference)eccEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECC_Start() {
		return (EReference)eccEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECC_BasicFBType() {
		return (EReference)eccEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getECState() {
		return ecStateEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECState_ECAction() {
		return (EReference)ecStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECState_OutTransitions() {
		return (EReference)ecStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECState_InTransitions() {
		return (EReference)ecStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECState_ECC() {
		return (EReference)ecStateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getECTransition() {
		return ecTransitionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getECTransition_Comment() {
		return (EAttribute)ecTransitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getECTransition_ConditionExpression() {
		return (EAttribute)ecTransitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECTransition_Source() {
		return (EReference)ecTransitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECTransition_Destination() {
		return (EReference)ecTransitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECTransition_ConditionEvent() {
		return (EReference)ecTransitionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getECTransition_ECC() {
		return (EReference)ecTransitionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getErrorMarkerDataType() {
		return errorMarkerDataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getErrorMarkerFBNElement() {
		return errorMarkerFBNElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getErrorMarkerFBNElement_RepairedElement() {
		return (EReference)errorMarkerFBNElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getErrorMarkerInterface() {
		return errorMarkerInterfaceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getErrorMarkerInterface_Value() {
		return (EReference)errorMarkerInterfaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEvent() {
		return eventEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEvent_With() {
		return (EReference)eventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEventConnection() {
		return eventConnectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEventConnection_FBNetwork() {
		return (EReference)eventConnectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFB() {
		return fbEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFBNetwork() {
		return fbNetworkEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFBNetwork_NetworkElements() {
		return (EReference)fbNetworkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFBNetwork_DataConnections() {
		return (EReference)fbNetworkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFBNetwork_EventConnections() {
		return (EReference)fbNetworkEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFBNetwork_AdapterConnections() {
		return (EReference)fbNetworkEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFBNetworkElement() {
		return fbNetworkElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFBNetworkElement_Interface() {
		return (EReference)fbNetworkElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFBNetworkElement_Mapping() {
		return (EReference)fbNetworkElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFBNetworkElement_Group() {
		return (EReference)fbNetworkElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFBType() {
		return fbTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFBType_InterfaceList() {
		return (EReference)fbTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFBType_Service() {
		return (EReference)fbTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunction() {
		return functionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunctionBody() {
		return functionBodyEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunctionFBType() {
		return functionFBTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionFBType_Body() {
		return (EReference)functionFBTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGlobalConstants() {
		return globalConstantsEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGlobalConstants_Constants() {
		return (EReference)globalConstantsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGlobalConstants_Source() {
		return (EReference)globalConstantsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGroup() {
		return groupEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGroup_GroupElements() {
		return (EReference)groupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGroup_Width() {
		return (EAttribute)groupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGroup_Height() {
		return (EAttribute)groupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGroup_Locked() {
		return (EAttribute)groupEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHiddenElement() {
		return hiddenElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getICallable() {
		return iCallableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIdentification() {
		return identificationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentification_ApplicationDomain() {
		return (EAttribute)identificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentification_Classification() {
		return (EAttribute)identificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentification_Description() {
		return (EAttribute)identificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentification_Function() {
		return (EAttribute)identificationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentification_Standard() {
		return (EAttribute)identificationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentification_Type() {
		return (EAttribute)identificationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIInterfaceElement() {
		return iInterfaceElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIInterfaceElement_IsInput() {
		return (EAttribute)iInterfaceElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIInterfaceElement_InputConnections() {
		return (EReference)iInterfaceElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIInterfaceElement_OutputConnections() {
		return (EReference)iInterfaceElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIInterfaceElement_Type() {
		return (EReference)iInterfaceElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getImport() {
		return importEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getImport_ImportedNamespace() {
		return (EAttribute)importEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getINamedElement() {
		return iNamedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getINamedElement_Name() {
		return (EAttribute)iNamedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getINamedElement_Comment() {
		return (EAttribute)iNamedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInputPrimitive() {
		return inputPrimitiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInterfaceList() {
		return interfaceListEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterfaceList_Plugs() {
		return (EReference)interfaceListEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterfaceList_Sockets() {
		return (EReference)interfaceListEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterfaceList_EventInputs() {
		return (EReference)interfaceListEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterfaceList_EventOutputs() {
		return (EReference)interfaceListEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterfaceList_InputVars() {
		return (EReference)interfaceListEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterfaceList_OutputVars() {
		return (EReference)interfaceListEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterfaceList_InOutVars() {
		return (EReference)interfaceListEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterfaceList_OutMappedInOutVars() {
		return (EReference)interfaceListEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterfaceList_ErrorMarker() {
		return (EReference)interfaceListEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getITypedElement() {
		return iTypedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIVarElement() {
		return iVarElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIVarElement_VarDeclarations() {
		return (EReference)iVarElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLibraryElement() {
		return libraryElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLibraryElement_VersionInfo() {
		return (EReference)libraryElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLibraryElement_Identification() {
		return (EReference)libraryElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLibraryElement_CompilerInfo() {
		return (EReference)libraryElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLibraryElement_TypeEntry() {
		return (EAttribute)libraryElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLink() {
		return linkEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLink_Segment() {
		return (EReference)linkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLink_Device() {
		return (EReference)linkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLocalVariable() {
		return localVariableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLocalVariable_ArrayStart() {
		return (EAttribute)localVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLocalVariable_ArrayStop() {
		return (EAttribute)localVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMapping() {
		return mappingEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMapping_From() {
		return (EReference)mappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMapping_To() {
		return (EReference)mappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMappingTarget() {
		return mappingTargetEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMethod() {
		return methodEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMultiplexer() {
		return multiplexerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOriginalSource() {
		return originalSourceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOriginalSource_Text() {
		return (EAttribute)originalSourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOtherAlgorithm() {
		return otherAlgorithmEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOtherAlgorithm_Language() {
		return (EAttribute)otherAlgorithmEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOtherMethod() {
		return otherMethodEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOtherMethod_Language() {
		return (EAttribute)otherMethodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOutputPrimitive() {
		return outputPrimitiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPosition() {
		return positionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPosition_X() {
		return (EAttribute)positionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPosition_Y() {
		return (EAttribute)positionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPositionableElement() {
		return positionableElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPositionableElement_Position() {
		return (EReference)positionableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPrimitive() {
		return primitiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPrimitive_Event() {
		return (EAttribute)primitiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPrimitive_Parameters() {
		return (EAttribute)primitiveEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPrimitive_Interface() {
		return (EReference)primitiveEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getResource() {
		return resourceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getResource_FBNetwork() {
		return (EReference)resourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResource_X() {
		return (EAttribute)resourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResource_Y() {
		return (EAttribute)resourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getResource_Device() {
		return (EReference)resourceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResource_DeviceTypeResource() {
		return (EAttribute)resourceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getResourceTypeName() {
		return resourceTypeNameEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResourceTypeName_Name() {
		return (EAttribute)resourceTypeNameEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getResourceType() {
		return resourceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getResourceType_VarDeclaration() {
		return (EReference)resourceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getResourceType_FBNetwork() {
		return (EReference)resourceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getResourceType_SupportedFBTypes() {
		return (EReference)resourceTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getResourceTypeFB() {
		return resourceTypeFBEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSegment() {
		return segmentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSegment_Width() {
		return (EAttribute)segmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSegment_OutConnections() {
		return (EReference)segmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSegment_Communication() {
		return (EReference)segmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSegmentType() {
		return segmentTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSegmentType_VarDeclaration() {
		return (EReference)segmentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getService() {
		return serviceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getService_RightInterface() {
		return (EReference)serviceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getService_LeftInterface() {
		return (EReference)serviceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getService_ServiceSequence() {
		return (EReference)serviceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getServiceSequence() {
		return serviceSequenceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getServiceSequence_ServiceTransaction() {
		return (EReference)serviceSequenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getServiceSequence_ServiceSequenceType() {
		return (EAttribute)serviceSequenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getServiceSequence_StartState() {
		return (EAttribute)serviceSequenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getServiceSequence_EventManager() {
		return (EReference)serviceSequenceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getServiceTransaction() {
		return serviceTransactionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getServiceTransaction_InputPrimitive() {
		return (EReference)serviceTransactionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getServiceTransaction_OutputPrimitive() {
		return (EReference)serviceTransactionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getServiceInterface() {
		return serviceInterfaceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getServiceInterfaceFBType() {
		return serviceInterfaceFBTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSimpleFBType() {
		return simpleFBTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTAlgorithm() {
		return stAlgorithmEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTFunction() {
		return stFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTFunctionBody() {
		return stFunctionBodyEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTMethod() {
		return stMethodEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubApp() {
		return subAppEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubApp_Width() {
		return (EAttribute)subAppEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubApp_Height() {
		return (EAttribute)subAppEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubApp_Locked() {
		return (EAttribute)subAppEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructManipulator() {
		return structManipulatorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubAppType() {
		return subAppTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSystemConfiguration() {
		return systemConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSystemConfiguration_Devices() {
		return (EReference)systemConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSystemConfiguration_Segments() {
		return (EReference)systemConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSystemConfiguration_Links() {
		return (EReference)systemConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTextAlgorithm() {
		return textAlgorithmEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTextAlgorithm_Text() {
		return (EAttribute)textAlgorithmEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTextFunction() {
		return textFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTextFunction_InputParameters() {
		return (EReference)textFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTextFunction_OutputParameters() {
		return (EReference)textFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTextFunction_InOutParameters() {
		return (EReference)textFunctionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTextFunction_ReturnType() {
		return (EReference)textFunctionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTextFunction_Text() {
		return (EAttribute)textFunctionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTextFunctionBody() {
		return textFunctionBodyEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTextFunctionBody_Text() {
		return (EAttribute)textFunctionBodyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTextMethod() {
		return textMethodEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTextMethod_InputParameters() {
		return (EReference)textMethodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTextMethod_OutputParameters() {
		return (EReference)textMethodEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTextMethod_InOutParameters() {
		return (EReference)textMethodEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTextMethod_ReturnType() {
		return (EReference)textMethodEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTextMethod_Text() {
		return (EAttribute)textMethodEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypedConfigureableObject() {
		return typedConfigureableObjectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypedConfigureableObject_TypeEntry() {
		return (EAttribute)typedConfigureableObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypedSubApp() {
		return typedSubAppEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypedSubApp_SubAppNetwork() {
		return (EReference)typedSubAppEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUntypedSubApp() {
		return untypedSubAppEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUntypedSubApp_SubAppNetwork() {
		return (EReference)untypedSubAppEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValue() {
		return valueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValue_Value() {
		return (EAttribute)valueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVarDeclaration() {
		return varDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVarDeclaration_ArraySize() {
		return (EReference)varDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVarDeclaration_Withs() {
		return (EReference)varDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVarDeclaration_Value() {
		return (EReference)varDeclarationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVersionInfo() {
		return versionInfoEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionInfo_Author() {
		return (EAttribute)versionInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionInfo_Date() {
		return (EAttribute)versionInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionInfo_Organization() {
		return (EAttribute)versionInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionInfo_Remarks() {
		return (EAttribute)versionInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionInfo_Version() {
		return (EAttribute)versionInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWith() {
		return withEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getWith_Variables() {
		return (EReference)withEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMemberVarDeclaration() {
		return memberVarDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMemberVarDeclaration_ParentNames() {
		return (EAttribute)memberVarDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getLanguage() {
		return languageEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getCommandStack() {
		return commandStackEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIFile() {
		return iFileEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getInterfaceElementStream() {
		return interfaceElementStreamEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIProject() {
		return iProjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getNamedElementStream() {
		return namedElementStreamEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getPoint() {
		return pointEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getTypeEntry() {
		return typeEntryEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getTypeLibrary() {
		return typeLibraryEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getVarDeclList() {
		return varDeclListEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LibraryElementFactory getLibraryElementFactory() {
		return (LibraryElementFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		adapterConnectionEClass = createEClass(ADAPTER_CONNECTION);
		createEReference(adapterConnectionEClass, ADAPTER_CONNECTION__FB_NETWORK);

		adapterDeclarationEClass = createEClass(ADAPTER_DECLARATION);
		createEReference(adapterDeclarationEClass, ADAPTER_DECLARATION__ADAPTER_FB);
		createEReference(adapterDeclarationEClass, ADAPTER_DECLARATION__INTERFACE_ONLY_ADAPTER_FB);

		adapterFBEClass = createEClass(ADAPTER_FB);
		createEReference(adapterFBEClass, ADAPTER_FB__ADAPTER_DECL);

		adapterTypeEClass = createEClass(ADAPTER_TYPE);
		createEReference(adapterTypeEClass, ADAPTER_TYPE__PLUG_TYPE);

		algorithmEClass = createEClass(ALGORITHM);

		applicationEClass = createEClass(APPLICATION);
		createEReference(applicationEClass, APPLICATION__FB_NETWORK);

		arraySizeEClass = createEClass(ARRAY_SIZE);
		createEAttribute(arraySizeEClass, ARRAY_SIZE__VALUE);
		createEReference(arraySizeEClass, ARRAY_SIZE__VAR_DECLARATION);

		attributeEClass = createEClass(ATTRIBUTE);
		createEReference(attributeEClass, ATTRIBUTE__ATTRIBUTE_DECLARATION);
		createEReference(attributeEClass, ATTRIBUTE__TYPE);
		createEAttribute(attributeEClass, ATTRIBUTE__VALUE);

		attributeDeclarationEClass = createEClass(ATTRIBUTE_DECLARATION);
		createEReference(attributeDeclarationEClass, ATTRIBUTE_DECLARATION__TYPE);

		baseFBTypeEClass = createEClass(BASE_FB_TYPE);
		createEReference(baseFBTypeEClass, BASE_FB_TYPE__INTERNAL_VARS);
		createEReference(baseFBTypeEClass, BASE_FB_TYPE__INTERNAL_CONST_VARS);
		createEReference(baseFBTypeEClass, BASE_FB_TYPE__INTERNAL_FBS);
		createEReference(baseFBTypeEClass, BASE_FB_TYPE__ALGORITHM);
		createEReference(baseFBTypeEClass, BASE_FB_TYPE__METHODS);
		createEReference(baseFBTypeEClass, BASE_FB_TYPE__CALLABLES);

		basicFBTypeEClass = createEClass(BASIC_FB_TYPE);
		createEReference(basicFBTypeEClass, BASIC_FB_TYPE__ECC);

		automationSystemEClass = createEClass(AUTOMATION_SYSTEM);
		createEReference(automationSystemEClass, AUTOMATION_SYSTEM__APPLICATION);
		createEReference(automationSystemEClass, AUTOMATION_SYSTEM__MAPPING);
		createEReference(automationSystemEClass, AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION);
		createEAttribute(automationSystemEClass, AUTOMATION_SYSTEM__COMMAND_STACK);

		cfbInstanceEClass = createEClass(CFB_INSTANCE);
		createEReference(cfbInstanceEClass, CFB_INSTANCE__CFB_NETWORK);

		colorEClass = createEClass(COLOR);
		createEAttribute(colorEClass, COLOR__RED);
		createEAttribute(colorEClass, COLOR__GREEN);
		createEAttribute(colorEClass, COLOR__BLUE);

		colorizableElementEClass = createEClass(COLORIZABLE_ELEMENT);
		createEReference(colorizableElementEClass, COLORIZABLE_ELEMENT__COLOR);

		commentEClass = createEClass(COMMENT);
		createEAttribute(commentEClass, COMMENT__WIDTH);
		createEAttribute(commentEClass, COMMENT__HEIGHT);

		communicationChannelEClass = createEClass(COMMUNICATION_CHANNEL);

		communicationConfigurationEClass = createEClass(COMMUNICATION_CONFIGURATION);

		communicationMappingTargetEClass = createEClass(COMMUNICATION_MAPPING_TARGET);
		createEReference(communicationMappingTargetEClass, COMMUNICATION_MAPPING_TARGET__MAPPED_ELEMENTS);

		compilerEClass = createEClass(COMPILER);
		createEAttribute(compilerEClass, COMPILER__LANGUAGE);
		createEAttribute(compilerEClass, COMPILER__PRODUCT);
		createEAttribute(compilerEClass, COMPILER__VENDOR);
		createEAttribute(compilerEClass, COMPILER__VERSION);

		compilerInfoEClass = createEClass(COMPILER_INFO);
		createEReference(compilerInfoEClass, COMPILER_INFO__COMPILER);
		createEAttribute(compilerInfoEClass, COMPILER_INFO__CLASSDEF);
		createEAttribute(compilerInfoEClass, COMPILER_INFO__HEADER);
		createEAttribute(compilerInfoEClass, COMPILER_INFO__PACKAGE_NAME);
		createEReference(compilerInfoEClass, COMPILER_INFO__IMPORTS);

		compositeFBTypeEClass = createEClass(COMPOSITE_FB_TYPE);
		createEReference(compositeFBTypeEClass, COMPOSITE_FB_TYPE__FB_NETWORK);

		configurableObjectEClass = createEClass(CONFIGURABLE_OBJECT);
		createEReference(configurableObjectEClass, CONFIGURABLE_OBJECT__ATTRIBUTES);

		configurableFBEClass = createEClass(CONFIGURABLE_FB);
		createEReference(configurableFBEClass, CONFIGURABLE_FB__DATA_TYPE);

		configurableMoveFBEClass = createEClass(CONFIGURABLE_MOVE_FB);

		connectionEClass = createEClass(CONNECTION);
		createEAttribute(connectionEClass, CONNECTION__BROKEN_CONNECTION);
		createEReference(connectionEClass, CONNECTION__SOURCE);
		createEReference(connectionEClass, CONNECTION__DESTINATION);
		createEReference(connectionEClass, CONNECTION__ROUTING_DATA);
		createEAttribute(connectionEClass, CONNECTION__COMMENT);

		connectionRoutingDataEClass = createEClass(CONNECTION_ROUTING_DATA);
		createEAttribute(connectionRoutingDataEClass, CONNECTION_ROUTING_DATA__DX1);
		createEAttribute(connectionRoutingDataEClass, CONNECTION_ROUTING_DATA__DX2);
		createEAttribute(connectionRoutingDataEClass, CONNECTION_ROUTING_DATA__DY);
		createEAttribute(connectionRoutingDataEClass, CONNECTION_ROUTING_DATA__NEEDS_VALIDATION);

		dataConnectionEClass = createEClass(DATA_CONNECTION);
		createEReference(dataConnectionEClass, DATA_CONNECTION__FB_NETWORK);

		demultiplexerEClass = createEClass(DEMULTIPLEXER);
		createEAttribute(demultiplexerEClass, DEMULTIPLEXER__IS_CONFIGURED);

		deviceEClass = createEClass(DEVICE);
		createEReference(deviceEClass, DEVICE__RESOURCE);
		createEAttribute(deviceEClass, DEVICE__PROFILE);
		createEReference(deviceEClass, DEVICE__IN_CONNECTIONS);

		deviceTypeEClass = createEClass(DEVICE_TYPE);
		createEReference(deviceTypeEClass, DEVICE_TYPE__VAR_DECLARATION);
		createEReference(deviceTypeEClass, DEVICE_TYPE__RESOURCE_TYPE_NAME);
		createEReference(deviceTypeEClass, DEVICE_TYPE__RESOURCE);
		createEReference(deviceTypeEClass, DEVICE_TYPE__FB_NETWORK);
		createEAttribute(deviceTypeEClass, DEVICE_TYPE__PROFILE);
		createEReference(deviceTypeEClass, DEVICE_TYPE__ATTRIBUTE_DECLARATIONS);

		ecActionEClass = createEClass(EC_ACTION);
		createEReference(ecActionEClass, EC_ACTION__ALGORITHM);
		createEReference(ecActionEClass, EC_ACTION__OUTPUT);
		createEReference(ecActionEClass, EC_ACTION__EC_STATE);

		eccEClass = createEClass(ECC);
		createEReference(eccEClass, ECC__EC_STATE);
		createEReference(eccEClass, ECC__EC_TRANSITION);
		createEReference(eccEClass, ECC__START);
		createEReference(eccEClass, ECC__BASIC_FB_TYPE);

		ecStateEClass = createEClass(EC_STATE);
		createEReference(ecStateEClass, EC_STATE__EC_ACTION);
		createEReference(ecStateEClass, EC_STATE__OUT_TRANSITIONS);
		createEReference(ecStateEClass, EC_STATE__IN_TRANSITIONS);
		createEReference(ecStateEClass, EC_STATE__ECC);

		ecTransitionEClass = createEClass(EC_TRANSITION);
		createEAttribute(ecTransitionEClass, EC_TRANSITION__COMMENT);
		createEAttribute(ecTransitionEClass, EC_TRANSITION__CONDITION_EXPRESSION);
		createEReference(ecTransitionEClass, EC_TRANSITION__SOURCE);
		createEReference(ecTransitionEClass, EC_TRANSITION__DESTINATION);
		createEReference(ecTransitionEClass, EC_TRANSITION__CONDITION_EVENT);
		createEReference(ecTransitionEClass, EC_TRANSITION__ECC);

		errorMarkerDataTypeEClass = createEClass(ERROR_MARKER_DATA_TYPE);

		errorMarkerFBNElementEClass = createEClass(ERROR_MARKER_FBN_ELEMENT);
		createEReference(errorMarkerFBNElementEClass, ERROR_MARKER_FBN_ELEMENT__REPAIRED_ELEMENT);

		errorMarkerInterfaceEClass = createEClass(ERROR_MARKER_INTERFACE);
		createEReference(errorMarkerInterfaceEClass, ERROR_MARKER_INTERFACE__VALUE);

		eventEClass = createEClass(EVENT);
		createEReference(eventEClass, EVENT__WITH);

		eventConnectionEClass = createEClass(EVENT_CONNECTION);
		createEReference(eventConnectionEClass, EVENT_CONNECTION__FB_NETWORK);

		fbEClass = createEClass(FB);

		fbNetworkEClass = createEClass(FB_NETWORK);
		createEReference(fbNetworkEClass, FB_NETWORK__NETWORK_ELEMENTS);
		createEReference(fbNetworkEClass, FB_NETWORK__DATA_CONNECTIONS);
		createEReference(fbNetworkEClass, FB_NETWORK__EVENT_CONNECTIONS);
		createEReference(fbNetworkEClass, FB_NETWORK__ADAPTER_CONNECTIONS);

		fbNetworkElementEClass = createEClass(FB_NETWORK_ELEMENT);
		createEReference(fbNetworkElementEClass, FB_NETWORK_ELEMENT__INTERFACE);
		createEReference(fbNetworkElementEClass, FB_NETWORK_ELEMENT__MAPPING);
		createEReference(fbNetworkElementEClass, FB_NETWORK_ELEMENT__GROUP);

		fbTypeEClass = createEClass(FB_TYPE);
		createEReference(fbTypeEClass, FB_TYPE__INTERFACE_LIST);
		createEReference(fbTypeEClass, FB_TYPE__SERVICE);

		functionEClass = createEClass(FUNCTION);

		functionBodyEClass = createEClass(FUNCTION_BODY);

		functionFBTypeEClass = createEClass(FUNCTION_FB_TYPE);
		createEReference(functionFBTypeEClass, FUNCTION_FB_TYPE__BODY);

		globalConstantsEClass = createEClass(GLOBAL_CONSTANTS);
		createEReference(globalConstantsEClass, GLOBAL_CONSTANTS__CONSTANTS);
		createEReference(globalConstantsEClass, GLOBAL_CONSTANTS__SOURCE);

		groupEClass = createEClass(GROUP);
		createEReference(groupEClass, GROUP__GROUP_ELEMENTS);
		createEAttribute(groupEClass, GROUP__WIDTH);
		createEAttribute(groupEClass, GROUP__HEIGHT);
		createEAttribute(groupEClass, GROUP__LOCKED);

		hiddenElementEClass = createEClass(HIDDEN_ELEMENT);

		iCallableEClass = createEClass(ICALLABLE);

		identificationEClass = createEClass(IDENTIFICATION);
		createEAttribute(identificationEClass, IDENTIFICATION__APPLICATION_DOMAIN);
		createEAttribute(identificationEClass, IDENTIFICATION__CLASSIFICATION);
		createEAttribute(identificationEClass, IDENTIFICATION__DESCRIPTION);
		createEAttribute(identificationEClass, IDENTIFICATION__FUNCTION);
		createEAttribute(identificationEClass, IDENTIFICATION__STANDARD);
		createEAttribute(identificationEClass, IDENTIFICATION__TYPE);

		iInterfaceElementEClass = createEClass(IINTERFACE_ELEMENT);
		createEAttribute(iInterfaceElementEClass, IINTERFACE_ELEMENT__IS_INPUT);
		createEReference(iInterfaceElementEClass, IINTERFACE_ELEMENT__INPUT_CONNECTIONS);
		createEReference(iInterfaceElementEClass, IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS);
		createEReference(iInterfaceElementEClass, IINTERFACE_ELEMENT__TYPE);

		importEClass = createEClass(IMPORT);
		createEAttribute(importEClass, IMPORT__IMPORTED_NAMESPACE);

		iNamedElementEClass = createEClass(INAMED_ELEMENT);
		createEAttribute(iNamedElementEClass, INAMED_ELEMENT__NAME);
		createEAttribute(iNamedElementEClass, INAMED_ELEMENT__COMMENT);

		inputPrimitiveEClass = createEClass(INPUT_PRIMITIVE);

		interfaceListEClass = createEClass(INTERFACE_LIST);
		createEReference(interfaceListEClass, INTERFACE_LIST__EVENT_INPUTS);
		createEReference(interfaceListEClass, INTERFACE_LIST__EVENT_OUTPUTS);
		createEReference(interfaceListEClass, INTERFACE_LIST__INPUT_VARS);
		createEReference(interfaceListEClass, INTERFACE_LIST__OUTPUT_VARS);
		createEReference(interfaceListEClass, INTERFACE_LIST__IN_OUT_VARS);
		createEReference(interfaceListEClass, INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS);
		createEReference(interfaceListEClass, INTERFACE_LIST__PLUGS);
		createEReference(interfaceListEClass, INTERFACE_LIST__SOCKETS);
		createEReference(interfaceListEClass, INTERFACE_LIST__ERROR_MARKER);

		iTypedElementEClass = createEClass(ITYPED_ELEMENT);

		iVarElementEClass = createEClass(IVAR_ELEMENT);
		createEReference(iVarElementEClass, IVAR_ELEMENT__VAR_DECLARATIONS);

		libraryElementEClass = createEClass(LIBRARY_ELEMENT);
		createEReference(libraryElementEClass, LIBRARY_ELEMENT__VERSION_INFO);
		createEReference(libraryElementEClass, LIBRARY_ELEMENT__IDENTIFICATION);
		createEReference(libraryElementEClass, LIBRARY_ELEMENT__COMPILER_INFO);
		createEAttribute(libraryElementEClass, LIBRARY_ELEMENT__TYPE_ENTRY);

		linkEClass = createEClass(LINK);
		createEReference(linkEClass, LINK__SEGMENT);
		createEReference(linkEClass, LINK__DEVICE);

		localVariableEClass = createEClass(LOCAL_VARIABLE);
		createEAttribute(localVariableEClass, LOCAL_VARIABLE__ARRAY_START);
		createEAttribute(localVariableEClass, LOCAL_VARIABLE__ARRAY_STOP);

		mappingEClass = createEClass(MAPPING);
		createEReference(mappingEClass, MAPPING__FROM);
		createEReference(mappingEClass, MAPPING__TO);

		mappingTargetEClass = createEClass(MAPPING_TARGET);

		memberVarDeclarationEClass = createEClass(MEMBER_VAR_DECLARATION);
		createEAttribute(memberVarDeclarationEClass, MEMBER_VAR_DECLARATION__PARENT_NAMES);

		methodEClass = createEClass(METHOD);

		multiplexerEClass = createEClass(MULTIPLEXER);

		originalSourceEClass = createEClass(ORIGINAL_SOURCE);
		createEAttribute(originalSourceEClass, ORIGINAL_SOURCE__TEXT);

		otherAlgorithmEClass = createEClass(OTHER_ALGORITHM);
		createEAttribute(otherAlgorithmEClass, OTHER_ALGORITHM__LANGUAGE);

		otherMethodEClass = createEClass(OTHER_METHOD);
		createEAttribute(otherMethodEClass, OTHER_METHOD__LANGUAGE);

		outputPrimitiveEClass = createEClass(OUTPUT_PRIMITIVE);

		positionEClass = createEClass(POSITION);
		createEAttribute(positionEClass, POSITION__X);
		createEAttribute(positionEClass, POSITION__Y);

		positionableElementEClass = createEClass(POSITIONABLE_ELEMENT);
		createEReference(positionableElementEClass, POSITIONABLE_ELEMENT__POSITION);

		primitiveEClass = createEClass(PRIMITIVE);
		createEAttribute(primitiveEClass, PRIMITIVE__EVENT);
		createEAttribute(primitiveEClass, PRIMITIVE__PARAMETERS);
		createEReference(primitiveEClass, PRIMITIVE__INTERFACE);

		resourceEClass = createEClass(RESOURCE);
		createEReference(resourceEClass, RESOURCE__FB_NETWORK);
		createEAttribute(resourceEClass, RESOURCE__X);
		createEAttribute(resourceEClass, RESOURCE__Y);
		createEReference(resourceEClass, RESOURCE__DEVICE);
		createEAttribute(resourceEClass, RESOURCE__DEVICE_TYPE_RESOURCE);

		resourceTypeNameEClass = createEClass(RESOURCE_TYPE_NAME);
		createEAttribute(resourceTypeNameEClass, RESOURCE_TYPE_NAME__NAME);

		resourceTypeEClass = createEClass(RESOURCE_TYPE);
		createEReference(resourceTypeEClass, RESOURCE_TYPE__VAR_DECLARATION);
		createEReference(resourceTypeEClass, RESOURCE_TYPE__FB_NETWORK);
		createEReference(resourceTypeEClass, RESOURCE_TYPE__SUPPORTED_FB_TYPES);

		resourceTypeFBEClass = createEClass(RESOURCE_TYPE_FB);

		segmentEClass = createEClass(SEGMENT);
		createEAttribute(segmentEClass, SEGMENT__WIDTH);
		createEReference(segmentEClass, SEGMENT__OUT_CONNECTIONS);
		createEReference(segmentEClass, SEGMENT__COMMUNICATION);

		segmentTypeEClass = createEClass(SEGMENT_TYPE);
		createEReference(segmentTypeEClass, SEGMENT_TYPE__VAR_DECLARATION);

		serviceEClass = createEClass(SERVICE);
		createEReference(serviceEClass, SERVICE__RIGHT_INTERFACE);
		createEReference(serviceEClass, SERVICE__LEFT_INTERFACE);
		createEReference(serviceEClass, SERVICE__SERVICE_SEQUENCE);

		serviceSequenceEClass = createEClass(SERVICE_SEQUENCE);
		createEReference(serviceSequenceEClass, SERVICE_SEQUENCE__SERVICE_TRANSACTION);
		createEAttribute(serviceSequenceEClass, SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE);
		createEAttribute(serviceSequenceEClass, SERVICE_SEQUENCE__START_STATE);
		createEReference(serviceSequenceEClass, SERVICE_SEQUENCE__EVENT_MANAGER);

		serviceTransactionEClass = createEClass(SERVICE_TRANSACTION);
		createEReference(serviceTransactionEClass, SERVICE_TRANSACTION__INPUT_PRIMITIVE);
		createEReference(serviceTransactionEClass, SERVICE_TRANSACTION__OUTPUT_PRIMITIVE);

		serviceInterfaceEClass = createEClass(SERVICE_INTERFACE);

		serviceInterfaceFBTypeEClass = createEClass(SERVICE_INTERFACE_FB_TYPE);

		simpleFBTypeEClass = createEClass(SIMPLE_FB_TYPE);

		stAlgorithmEClass = createEClass(ST_ALGORITHM);

		stFunctionEClass = createEClass(ST_FUNCTION);

		stFunctionBodyEClass = createEClass(ST_FUNCTION_BODY);

		stMethodEClass = createEClass(ST_METHOD);

		subAppEClass = createEClass(SUB_APP);
		createEAttribute(subAppEClass, SUB_APP__WIDTH);
		createEAttribute(subAppEClass, SUB_APP__HEIGHT);
		createEAttribute(subAppEClass, SUB_APP__LOCKED);

		structManipulatorEClass = createEClass(STRUCT_MANIPULATOR);

		subAppTypeEClass = createEClass(SUB_APP_TYPE);

		systemConfigurationEClass = createEClass(SYSTEM_CONFIGURATION);
		createEReference(systemConfigurationEClass, SYSTEM_CONFIGURATION__DEVICES);
		createEReference(systemConfigurationEClass, SYSTEM_CONFIGURATION__SEGMENTS);
		createEReference(systemConfigurationEClass, SYSTEM_CONFIGURATION__LINKS);

		textAlgorithmEClass = createEClass(TEXT_ALGORITHM);
		createEAttribute(textAlgorithmEClass, TEXT_ALGORITHM__TEXT);

		textFunctionEClass = createEClass(TEXT_FUNCTION);
		createEReference(textFunctionEClass, TEXT_FUNCTION__INPUT_PARAMETERS);
		createEReference(textFunctionEClass, TEXT_FUNCTION__OUTPUT_PARAMETERS);
		createEReference(textFunctionEClass, TEXT_FUNCTION__IN_OUT_PARAMETERS);
		createEReference(textFunctionEClass, TEXT_FUNCTION__RETURN_TYPE);
		createEAttribute(textFunctionEClass, TEXT_FUNCTION__TEXT);

		textFunctionBodyEClass = createEClass(TEXT_FUNCTION_BODY);
		createEAttribute(textFunctionBodyEClass, TEXT_FUNCTION_BODY__TEXT);

		textMethodEClass = createEClass(TEXT_METHOD);
		createEReference(textMethodEClass, TEXT_METHOD__INPUT_PARAMETERS);
		createEReference(textMethodEClass, TEXT_METHOD__OUTPUT_PARAMETERS);
		createEReference(textMethodEClass, TEXT_METHOD__IN_OUT_PARAMETERS);
		createEReference(textMethodEClass, TEXT_METHOD__RETURN_TYPE);
		createEAttribute(textMethodEClass, TEXT_METHOD__TEXT);

		typedConfigureableObjectEClass = createEClass(TYPED_CONFIGUREABLE_OBJECT);
		createEAttribute(typedConfigureableObjectEClass, TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY);

		typedSubAppEClass = createEClass(TYPED_SUB_APP);
		createEReference(typedSubAppEClass, TYPED_SUB_APP__SUB_APP_NETWORK);

		untypedSubAppEClass = createEClass(UNTYPED_SUB_APP);
		createEReference(untypedSubAppEClass, UNTYPED_SUB_APP__SUB_APP_NETWORK);

		valueEClass = createEClass(VALUE);
		createEAttribute(valueEClass, VALUE__VALUE);

		varDeclarationEClass = createEClass(VAR_DECLARATION);
		createEReference(varDeclarationEClass, VAR_DECLARATION__ARRAY_SIZE);
		createEReference(varDeclarationEClass, VAR_DECLARATION__WITHS);
		createEReference(varDeclarationEClass, VAR_DECLARATION__VALUE);

		versionInfoEClass = createEClass(VERSION_INFO);
		createEAttribute(versionInfoEClass, VERSION_INFO__AUTHOR);
		createEAttribute(versionInfoEClass, VERSION_INFO__DATE);
		createEAttribute(versionInfoEClass, VERSION_INFO__ORGANIZATION);
		createEAttribute(versionInfoEClass, VERSION_INFO__REMARKS);
		createEAttribute(versionInfoEClass, VERSION_INFO__VERSION);

		withEClass = createEClass(WITH);
		createEReference(withEClass, WITH__VARIABLES);

		// Create enums
		languageEEnum = createEEnum(LANGUAGE);

		// Create data types
		commandStackEDataType = createEDataType(COMMAND_STACK);
		iFileEDataType = createEDataType(IFILE);
		interfaceElementStreamEDataType = createEDataType(INTERFACE_ELEMENT_STREAM);
		iProjectEDataType = createEDataType(IPROJECT);
		namedElementStreamEDataType = createEDataType(NAMED_ELEMENT_STREAM);
		pointEDataType = createEDataType(POINT);
		typeEntryEDataType = createEDataType(TYPE_ENTRY);
		typeLibraryEDataType = createEDataType(TYPE_LIBRARY);
		varDeclListEDataType = createEDataType(VAR_DECL_LIST);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is
	 * guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
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
		DataPackage theDataPackage = (DataPackage)EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		adapterConnectionEClass.getESuperTypes().add(this.getConnection());
		adapterDeclarationEClass.getESuperTypes().add(this.getIInterfaceElement());
		adapterFBEClass.getESuperTypes().add(this.getFB());
		adapterTypeEClass.getESuperTypes().add(theDataPackage.getDataType());
		adapterTypeEClass.getESuperTypes().add(this.getFBType());
		algorithmEClass.getESuperTypes().add(this.getICallable());
		applicationEClass.getESuperTypes().add(this.getINamedElement());
		applicationEClass.getESuperTypes().add(this.getConfigurableObject());
		attributeEClass.getESuperTypes().add(this.getITypedElement());
		attributeDeclarationEClass.getESuperTypes().add(this.getITypedElement());
		attributeDeclarationEClass.getESuperTypes().add(this.getLibraryElement());
		baseFBTypeEClass.getESuperTypes().add(this.getFBType());
		basicFBTypeEClass.getESuperTypes().add(this.getBaseFBType());
		automationSystemEClass.getESuperTypes().add(this.getLibraryElement());
		cfbInstanceEClass.getESuperTypes().add(this.getFB());
		commentEClass.getESuperTypes().add(this.getFBNetworkElement());
		communicationChannelEClass.getESuperTypes().add(this.getFB());
		communicationMappingTargetEClass.getESuperTypes().add(this.getMappingTarget());
		compositeFBTypeEClass.getESuperTypes().add(this.getFBType());
		configurableFBEClass.getESuperTypes().add(this.getFB());
		configurableMoveFBEClass.getESuperTypes().add(this.getConfigurableFB());
		connectionEClass.getESuperTypes().add(this.getConfigurableObject());
		connectionEClass.getESuperTypes().add(this.getHiddenElement());
		dataConnectionEClass.getESuperTypes().add(this.getConnection());
		demultiplexerEClass.getESuperTypes().add(this.getStructManipulator());
		deviceEClass.getESuperTypes().add(this.getTypedConfigureableObject());
		deviceEClass.getESuperTypes().add(this.getPositionableElement());
		deviceEClass.getESuperTypes().add(this.getColorizableElement());
		deviceEClass.getESuperTypes().add(this.getIVarElement());
		deviceTypeEClass.getESuperTypes().add(this.getLibraryElement());
		ecStateEClass.getESuperTypes().add(this.getINamedElement());
		ecStateEClass.getESuperTypes().add(this.getPositionableElement());
		ecTransitionEClass.getESuperTypes().add(this.getPositionableElement());
		errorMarkerDataTypeEClass.getESuperTypes().add(theDataPackage.getDataType());
		errorMarkerFBNElementEClass.getESuperTypes().add(this.getFBNetworkElement());
		errorMarkerInterfaceEClass.getESuperTypes().add(this.getIInterfaceElement());
		eventEClass.getESuperTypes().add(this.getIInterfaceElement());
		eventEClass.getESuperTypes().add(this.getICallable());
		eventConnectionEClass.getESuperTypes().add(this.getConnection());
		fbEClass.getESuperTypes().add(this.getFBNetworkElement());
		fbEClass.getESuperTypes().add(this.getICallable());
		fbNetworkElementEClass.getESuperTypes().add(this.getTypedConfigureableObject());
		fbNetworkElementEClass.getESuperTypes().add(this.getPositionableElement());
		fbTypeEClass.getESuperTypes().add(this.getLibraryElement());
		fbTypeEClass.getESuperTypes().add(this.getICallable());
		functionEClass.getESuperTypes().add(this.getICallable());
		functionFBTypeEClass.getESuperTypes().add(this.getFBType());
		globalConstantsEClass.getESuperTypes().add(this.getLibraryElement());
		groupEClass.getESuperTypes().add(this.getFBNetworkElement());
		hiddenElementEClass.getESuperTypes().add(this.getConfigurableObject());
		iCallableEClass.getESuperTypes().add(this.getINamedElement());
		iInterfaceElementEClass.getESuperTypes().add(this.getITypedElement());
		iInterfaceElementEClass.getESuperTypes().add(this.getConfigurableObject());
		iInterfaceElementEClass.getESuperTypes().add(this.getHiddenElement());
		inputPrimitiveEClass.getESuperTypes().add(this.getPrimitive());
		iTypedElementEClass.getESuperTypes().add(this.getINamedElement());
		libraryElementEClass.getESuperTypes().add(this.getINamedElement());
		libraryElementEClass.getESuperTypes().add(this.getConfigurableObject());
		linkEClass.getESuperTypes().add(this.getINamedElement());
		linkEClass.getESuperTypes().add(this.getConfigurableObject());
		localVariableEClass.getESuperTypes().add(this.getVarDeclaration());
		mappingTargetEClass.getESuperTypes().add(this.getINamedElement());
		memberVarDeclarationEClass.getESuperTypes().add(this.getVarDeclaration());
		methodEClass.getESuperTypes().add(this.getICallable());
		multiplexerEClass.getESuperTypes().add(this.getStructManipulator());
		otherAlgorithmEClass.getESuperTypes().add(this.getTextAlgorithm());
		otherMethodEClass.getESuperTypes().add(this.getTextMethod());
		outputPrimitiveEClass.getESuperTypes().add(this.getPrimitive());
		resourceEClass.getESuperTypes().add(this.getTypedConfigureableObject());
		resourceEClass.getESuperTypes().add(this.getIVarElement());
		resourceEClass.getESuperTypes().add(this.getMappingTarget());
		resourceTypeEClass.getESuperTypes().add(this.getLibraryElement());
		resourceTypeFBEClass.getESuperTypes().add(this.getFB());
		segmentEClass.getESuperTypes().add(this.getTypedConfigureableObject());
		segmentEClass.getESuperTypes().add(this.getPositionableElement());
		segmentEClass.getESuperTypes().add(this.getColorizableElement());
		segmentEClass.getESuperTypes().add(this.getIVarElement());
		segmentTypeEClass.getESuperTypes().add(this.getLibraryElement());
		serviceSequenceEClass.getESuperTypes().add(this.getINamedElement());
		serviceSequenceEClass.getESuperTypes().add(this.getConfigurableObject());
		serviceInterfaceEClass.getESuperTypes().add(this.getINamedElement());
		serviceInterfaceFBTypeEClass.getESuperTypes().add(this.getFBType());
		simpleFBTypeEClass.getESuperTypes().add(this.getBaseFBType());
		stAlgorithmEClass.getESuperTypes().add(this.getTextAlgorithm());
		stFunctionEClass.getESuperTypes().add(this.getTextFunction());
		stFunctionBodyEClass.getESuperTypes().add(this.getTextFunctionBody());
		stMethodEClass.getESuperTypes().add(this.getTextMethod());
		subAppEClass.getESuperTypes().add(this.getFBNetworkElement());
		structManipulatorEClass.getESuperTypes().add(this.getConfigurableFB());
		subAppTypeEClass.getESuperTypes().add(this.getCompositeFBType());
		textAlgorithmEClass.getESuperTypes().add(this.getAlgorithm());
		textFunctionEClass.getESuperTypes().add(this.getFunction());
		textFunctionBodyEClass.getESuperTypes().add(this.getFunctionBody());
		textMethodEClass.getESuperTypes().add(this.getMethod());
		typedConfigureableObjectEClass.getESuperTypes().add(this.getITypedElement());
		typedConfigureableObjectEClass.getESuperTypes().add(this.getConfigurableObject());
		typedSubAppEClass.getESuperTypes().add(this.getSubApp());
		untypedSubAppEClass.getESuperTypes().add(this.getSubApp());
		varDeclarationEClass.getESuperTypes().add(this.getIInterfaceElement());

		// Initialize classes and features; add operations and parameters
		initEClass(adapterConnectionEClass, AdapterConnection.class, "AdapterConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAdapterConnection_FBNetwork(), this.getFBNetwork(), this.getFBNetwork_AdapterConnections(), "fBNetwork", null, 0, 1, AdapterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterConnectionEClass, this.getAdapterDeclaration(), "getAdapterSource", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterConnectionEClass, this.getAdapterDeclaration(), "getAdapterDestination", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(adapterDeclarationEClass, AdapterDeclaration.class, "AdapterDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAdapterDeclaration_AdapterFB(), this.getAdapterFB(), null, "adapterFB", null, 0, 1, AdapterDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAdapterDeclaration_InterfaceOnlyAdapterFB(), this.getAdapterFB(), null, "interfaceOnlyAdapterFB", null, 0, 1, AdapterDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterDeclarationEClass, this.getAdapterType(), "getType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		EOperation op = addEOperation(adapterDeclarationEClass, this.getNamedElementStream(), "findBySimpleName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(adapterFBEClass, AdapterFB.class, "AdapterFB", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAdapterFB_AdapterDecl(), this.getAdapterDeclaration(), null, "adapterDecl", null, 1, 1, AdapterFB.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterFBEClass, theXMLTypePackage.getBoolean(), "isSocket", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterFBEClass, this.getFBType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterFBEClass, theXMLTypePackage.getBoolean(), "isPlug", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterFBEClass, ecorePackage.getEString(), "getQualifiedName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(adapterTypeEClass, AdapterType.class, "AdapterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAdapterType_PlugType(), this.getAdapterType(), null, "plugType", null, 0, 1, AdapterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(algorithmEClass, Algorithm.class, "Algorithm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(algorithmEClass, this.getINamedElement(), "getInputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(algorithmEClass, this.getINamedElement(), "getOutputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(algorithmEClass, this.getINamedElement(), "getInOutParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(algorithmEClass, theDataPackage.getDataType(), "getReturnType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(applicationEClass, Application.class, "Application", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getApplication_FBNetwork(), this.getFBNetwork(), null, "fBNetwork", null, 1, 1, Application.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(applicationEClass, this.getAutomationSystem(), "getAutomationSystem", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(arraySizeEClass, ArraySize.class, "ArraySize", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getArraySize_Value(), ecorePackage.getEString(), "value", "", 0, 1, ArraySize.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getArraySize_VarDeclaration(), this.getVarDeclaration(), this.getVarDeclaration_ArraySize(), "varDeclaration", null, 0, 1, ArraySize.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(attributeEClass, Attribute.class, "Attribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAttribute_AttributeDeclaration(), this.getAttributeDeclaration(), null, "attributeDeclaration", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAttribute_Type(), theDataPackage.getDataType(), null, "type", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAttribute_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(attributeEClass, ecorePackage.getEString(), "getQualifiedName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(attributeEClass, ecorePackage.getEBoolean(), "validateName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(attributeDeclarationEClass, AttributeDeclaration.class, "AttributeDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAttributeDeclaration_Type(), theDataPackage.getAnyDerivedType(), null, "type", null, 0, 1, AttributeDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(attributeDeclarationEClass, null, "setTarget", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theDataPackage.getStructuredType(), "target", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(attributeDeclarationEClass, theDataPackage.getStructuredType(), "getTarget", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(attributeDeclarationEClass, theXMLTypePackage.getBoolean(), "isValidObject", 0, 1, !IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getConfigurableObject(), "object", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(baseFBTypeEClass, BaseFBType.class, "BaseFBType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getBaseFBType_InternalVars(), this.getVarDeclaration(), null, "internalVars", null, 0, -1, BaseFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBaseFBType_InternalConstVars(), this.getVarDeclaration(), null, "internalConstVars", null, 0, -1, BaseFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBaseFBType_InternalFbs(), this.getFB(), null, "internalFbs", null, 0, -1, BaseFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBaseFBType_Algorithm(), this.getAlgorithm(), null, "algorithm", null, 0, -1, BaseFBType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBaseFBType_Methods(), this.getMethod(), null, "methods", null, 0, -1, BaseFBType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBaseFBType_Callables(), this.getICallable(), null, "callables", null, 0, -1, BaseFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(baseFBTypeEClass, this.getAlgorithm(), "getAlgorithmNamed", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(basicFBTypeEClass, BasicFBType.class, "BasicFBType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getBasicFBType_ECC(), this.getECC(), this.getECC_BasicFBType(), "eCC", null, 0, 1, BasicFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(automationSystemEClass, AutomationSystem.class, "AutomationSystem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAutomationSystem_Application(), this.getApplication(), null, "application", null, 0, -1, AutomationSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAutomationSystem_Mapping(), this.getMapping(), null, "mapping", null, 0, -1, AutomationSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAutomationSystem_SystemConfiguration(), this.getSystemConfiguration(), null, "systemConfiguration", null, 1, 1, AutomationSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAutomationSystem_CommandStack(), this.getCommandStack(), "commandStack", null, 0, 1, AutomationSystem.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(automationSystemEClass, this.getDevice(), "getDeviceNamed", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(automationSystemEClass, this.getApplication(), "getApplicationNamed", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(cfbInstanceEClass, CFBInstance.class, "CFBInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCFBInstance_CfbNetwork(), this.getFBNetwork(), null, "cfbNetwork", null, 0, 1, CFBInstance.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(cfbInstanceEClass, this.getFBNetwork(), "loadCFBNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(cfbInstanceEClass, this.getCompositeFBType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(cfbInstanceEClass, this.getNamedElementStream(), "findBySimpleName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(colorEClass, Color.class, "Color", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getColor_Red(), theXMLTypePackage.getInt(), "red", "1", 0, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getColor_Green(), theXMLTypePackage.getInt(), "green", "34", 0, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getColor_Blue(), theXMLTypePackage.getInt(), "blue", "105", 0, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(colorizableElementEClass, ColorizableElement.class, "ColorizableElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getColorizableElement_Color(), this.getColor(), null, "color", null, 1, 1, ColorizableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(commentEClass, Comment.class, "Comment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getComment_Width(), theXMLTypePackage.getDouble(), "width", "200", 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getComment_Height(), theXMLTypePackage.getDouble(), "height", "100", 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		addEOperation(commentEClass, this.getInterfaceList(), "getInterface", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(communicationChannelEClass, CommunicationChannel.class, "CommunicationChannel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(communicationConfigurationEClass, CommunicationConfiguration.class, "CommunicationConfiguration", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(communicationConfigurationEClass, this.getSegment(), "getSegment", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(communicationConfigurationEClass, this.getCommunicationMappingTarget(), "getMappingTargets", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(communicationConfigurationEClass, this.getVarDeclaration(), "getParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(communicationMappingTargetEClass, CommunicationMappingTarget.class, "CommunicationMappingTarget", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCommunicationMappingTarget_MappedElements(), this.getCommunicationChannel(), null, "mappedElements", null, 0, -1, CommunicationMappingTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(compilerEClass, org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, "Compiler", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getCompiler_Language(), this.getLanguage(), "language", "C", 1, 1, org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getCompiler_Product(), theXMLTypePackage.getString(), "product", "", 1, 1, org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getCompiler_Vendor(), theXMLTypePackage.getString(), "vendor", "", 1, 1, org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getCompiler_Version(), theXMLTypePackage.getString(), "version", "", 1, 1, org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(compilerInfoEClass, CompilerInfo.class, "CompilerInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCompilerInfo_Compiler(), this.getCompiler(), null, "compiler", null, 0, -1, CompilerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getCompilerInfo_Classdef(), theXMLTypePackage.getString(), "classdef", "", 0, 1, CompilerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getCompilerInfo_Header(), theXMLTypePackage.getString(), "header", "", 0, 1, CompilerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getCompilerInfo_PackageName(), theXMLTypePackage.getString(), "packageName", null, 0, 1, CompilerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getCompilerInfo_Imports(), this.getImport(), null, "imports", null, 0, -1, CompilerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(compilerInfoEClass, ecorePackage.getEBoolean(), "validatePackageName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(compositeFBTypeEClass, CompositeFBType.class, "CompositeFBType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCompositeFBType_FBNetwork(), this.getFBNetwork(), null, "fBNetwork", null, 0, 1, CompositeFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(configurableObjectEClass, ConfigurableObject.class, "ConfigurableObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getConfigurableObject_Attributes(), this.getAttribute(), null, "attributes", null, 0, -1, ConfigurableObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(configurableObjectEClass, null, "setAttribute", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theDataPackage.getDataType(), "type", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "value", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "comment", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(configurableObjectEClass, null, "setAttribute", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getAttributeDeclaration(), "attributeDeclaration", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "value", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "comment", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(configurableObjectEClass, this.getAttribute(), "getAttribute", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(configurableObjectEClass, theXMLTypePackage.getString(), "getAttributeValue", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(configurableObjectEClass, theXMLTypePackage.getBoolean(), "deleteAttribute", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(configurableFBEClass, ConfigurableFB.class, "ConfigurableFB", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getConfigurableFB_DataType(), theDataPackage.getDataType(), null, "dataType", null, 0, 1, ConfigurableFB.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(configurableFBEClass, null, "updateConfiguration", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(configurableFBEClass, null, "loadConfiguration", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "typeName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(configurableFBEClass, this.getAttribute(), "getConfigurationAsAttributes", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(configurableFBEClass, ecorePackage.getEBoolean(), "validateDataType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(configurableMoveFBEClass, ConfigurableMoveFB.class, "ConfigurableMoveFB", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(connectionEClass, Connection.class, "Connection", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getConnection_BrokenConnection(), theXMLTypePackage.getBoolean(), "brokenConnection", null, 0, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnection_Source(), this.getIInterfaceElement(), this.getIInterfaceElement_OutputConnections(), "source", null, 0, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnection_Destination(), this.getIInterfaceElement(), this.getIInterfaceElement_InputConnections(), "destination", null, 0, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnection_RoutingData(), this.getConnectionRoutingData(), null, "routingData", null, 1, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getConnection_Comment(), theXMLTypePackage.getString(), "comment", "", 0, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		addEOperation(connectionEClass, this.getFBNetworkElement(), "getSourceElement", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(connectionEClass, this.getFBNetworkElement(), "getDestinationElement", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(connectionEClass, ecorePackage.getEBoolean(), "isResourceConnection", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(connectionEClass, this.getFBNetwork(), "getFBNetwork", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(connectionEClass, null, "checkIfConnectionBroken", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(connectionEClass, ecorePackage.getEBoolean(), "isInterfaceConnection", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateMissingSource", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateMissingSourceEndpoint", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateMissingDestination", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateMissingDestinationEndpoint", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateDuplicate", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateTypeMismatch", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateMappedVarInOutsDoNotCrossResourceBoundaries", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateVarInOutArraySizesAreCompatible", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateVarInOutStringLengthsMatch", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateVarInOutsAreNotConnectedToOuts", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, ecorePackage.getEBoolean(), "validateVarInOutConnectionsFormsNoLoop", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(connectionRoutingDataEClass, ConnectionRoutingData.class, "ConnectionRoutingData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getConnectionRoutingData_Dx1(), theXMLTypePackage.getDouble(), "dx1", "0", 0, 1, ConnectionRoutingData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getConnectionRoutingData_Dx2(), theXMLTypePackage.getDouble(), "dx2", "0", 0, 1, ConnectionRoutingData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getConnectionRoutingData_Dy(), theXMLTypePackage.getDouble(), "dy", "0", 0, 1, ConnectionRoutingData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getConnectionRoutingData_NeedsValidation(), theXMLTypePackage.getBoolean(), "needsValidation", null, 0, 1, ConnectionRoutingData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(connectionRoutingDataEClass, ecorePackage.getEBoolean(), "is1SegementData", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(connectionRoutingDataEClass, ecorePackage.getEBoolean(), "is3SegementData", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(connectionRoutingDataEClass, ecorePackage.getEBoolean(), "is5SegementData", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(dataConnectionEClass, DataConnection.class, "DataConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDataConnection_FBNetwork(), this.getFBNetwork(), this.getFBNetwork_DataConnections(), "fBNetwork", null, 0, 1, DataConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(dataConnectionEClass, this.getVarDeclaration(), "getDataSource", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(dataConnectionEClass, this.getVarDeclaration(), "getDataDestination", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(demultiplexerEClass, Demultiplexer.class, "Demultiplexer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getDemultiplexer_IsConfigured(), theXMLTypePackage.getBoolean(), "isConfigured", null, 0, 1, Demultiplexer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(demultiplexerEClass, this.getVarDeclaration(), "getMemberVars", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(deviceEClass, Device.class, "Device", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDevice_Resource(), this.getResource(), this.getResource_Device(), "resource", null, 0, -1, Device.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getDevice_Profile(), ecorePackage.getEString(), "profile", null, 0, 1, Device.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDevice_InConnections(), this.getLink(), this.getLink_Device(), "inConnections", null, 0, -1, Device.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(deviceEClass, this.getAutomationSystem(), "getAutomationSystem", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(deviceEClass, this.getSystemConfiguration(), "getSystemConfiguration", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(deviceEClass, this.getDeviceType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(deviceEClass, this.getResource(), "getResourceNamed", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(deviceTypeEClass, DeviceType.class, "DeviceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDeviceType_VarDeclaration(), this.getVarDeclaration(), null, "varDeclaration", null, 0, -1, DeviceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDeviceType_ResourceTypeName(), this.getResourceTypeName(), null, "resourceTypeName", null, 0, -1, DeviceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDeviceType_Resource(), this.getResource(), null, "resource", null, 0, -1, DeviceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDeviceType_FBNetwork(), this.getFBNetwork(), null, "fBNetwork", null, 0, 1, DeviceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getDeviceType_Profile(), ecorePackage.getEString(), "profile", null, 0, 1, DeviceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDeviceType_AttributeDeclarations(), this.getAttributeDeclaration(), null, "attributeDeclarations", null, 0, -1, DeviceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(ecActionEClass, ECAction.class, "ECAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getECAction_Algorithm(), this.getAlgorithm(), null, "algorithm", null, 0, 1, ECAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECAction_Output(), this.getEvent(), null, "output", null, 0, 1, ECAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECAction_ECState(), this.getECState(), this.getECState_ECAction(), "eCState", null, 1, 1, ECAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(eccEClass, org.eclipse.fordiac.ide.model.libraryElement.ECC.class, "ECC", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getECC_ECState(), this.getECState(), this.getECState_ECC(), "eCState", null, 1, -1, org.eclipse.fordiac.ide.model.libraryElement.ECC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECC_ECTransition(), this.getECTransition(), this.getECTransition_ECC(), "eCTransition", null, 0, -1, org.eclipse.fordiac.ide.model.libraryElement.ECC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECC_Start(), this.getECState(), null, "start", null, 0, 1, org.eclipse.fordiac.ide.model.libraryElement.ECC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECC_BasicFBType(), this.getBasicFBType(), this.getBasicFBType_ECC(), "basicFBType", null, 1, 1, org.eclipse.fordiac.ide.model.libraryElement.ECC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(ecStateEClass, ECState.class, "ECState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getECState_ECAction(), this.getECAction(), this.getECAction_ECState(), "eCAction", null, 0, -1, ECState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECState_OutTransitions(), this.getECTransition(), this.getECTransition_Source(), "outTransitions", null, 0, -1, ECState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECState_InTransitions(), this.getECTransition(), this.getECTransition_Destination(), "inTransitions", null, 0, -1, ECState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECState_ECC(), this.getECC(), this.getECC_ECState(), "eCC", null, 0, 1, ECState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(ecStateEClass, theXMLTypePackage.getBoolean(), "isStartState", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(ecTransitionEClass, ECTransition.class, "ECTransition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getECTransition_Comment(), theXMLTypePackage.getString(), "comment", "", 0, 1, ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getECTransition_ConditionExpression(), theXMLTypePackage.getString(), "conditionExpression", "1", 0, 1, ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getECTransition_Source(), this.getECState(), this.getECState_OutTransitions(), "source", null, 1, 1, ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECTransition_Destination(), this.getECState(), this.getECState_InTransitions(), "destination", null, 1, 1, ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECTransition_ConditionEvent(), this.getEvent(), null, "conditionEvent", null, 0, 1, ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getECTransition_ECC(), this.getECC(), this.getECC_ECTransition(), "eCC", null, 1, 1, ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(ecTransitionEClass, theXMLTypePackage.getString(), "getConditionText", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(ecTransitionEClass, ecorePackage.getEInt(), "getPriority", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(errorMarkerDataTypeEClass, ErrorMarkerDataType.class, "ErrorMarkerDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(errorMarkerFBNElementEClass, ErrorMarkerFBNElement.class, "ErrorMarkerFBNElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getErrorMarkerFBNElement_RepairedElement(), this.getFBNetworkElement(), null, "repairedElement", null, 0, 1, ErrorMarkerFBNElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(errorMarkerFBNElementEClass, theXMLTypePackage.getDouble(), "getWidth", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(errorMarkerFBNElementEClass, theXMLTypePackage.getDouble(), "getHeight", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(errorMarkerInterfaceEClass, ErrorMarkerInterface.class, "ErrorMarkerInterface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getErrorMarkerInterface_Value(), this.getValue(), null, "value", null, 0, 1, ErrorMarkerInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(errorMarkerInterfaceEClass, ecorePackage.getEBoolean(), "validateValue", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(errorMarkerInterfaceEClass, ecorePackage.getEBoolean(), "validateAttributes", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(eventEClass, Event.class, "Event", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEvent_With(), this.getWith(), null, "with", null, 0, -1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(eventEClass, this.getINamedElement(), "getInputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(eventEClass, this.getINamedElement(), "getOutputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(eventEClass, this.getINamedElement(), "getInOutParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(eventEClass, theDataPackage.getDataType(), "getReturnType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(eventConnectionEClass, EventConnection.class, "EventConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEventConnection_FBNetwork(), this.getFBNetwork(), this.getFBNetwork_EventConnections(), "fBNetwork", null, 0, 1, EventConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(eventConnectionEClass, this.getEvent(), "getEventSource", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(eventConnectionEClass, this.getEvent(), "getEventDestination", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(fbEClass, org.eclipse.fordiac.ide.model.libraryElement.FB.class, "FB", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(fbEClass, ecorePackage.getEBoolean(), "isResourceFB", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbEClass, ecorePackage.getEBoolean(), "isResourceTypeFB", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbEClass, this.getFBType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbEClass, this.getINamedElement(), "getInputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbEClass, this.getINamedElement(), "getOutputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbEClass, this.getINamedElement(), "getInOutParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbEClass, theDataPackage.getDataType(), "getReturnType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbEClass, theXMLTypePackage.getDouble(), "getWidth", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbEClass, theXMLTypePackage.getDouble(), "getHeight", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(fbNetworkEClass, FBNetwork.class, "FBNetwork", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getFBNetwork_NetworkElements(), this.getFBNetworkElement(), null, "networkElements", null, 0, -1, FBNetwork.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getFBNetwork_DataConnections(), this.getDataConnection(), this.getDataConnection_FBNetwork(), "dataConnections", null, 0, -1, FBNetwork.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getFBNetwork_EventConnections(), this.getEventConnection(), this.getEventConnection_FBNetwork(), "eventConnections", null, 0, -1, FBNetwork.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getFBNetwork_AdapterConnections(), this.getAdapterConnection(), this.getAdapterConnection_FBNetwork(), "adapterConnections", null, 0, -1, FBNetwork.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, null, "addConnection", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getConnection(), "connection", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, null, "addConnectionWithIndex", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getConnection(), "connection", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getInt(), "index", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, null, "removeConnection", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getConnection(), "connection", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkEClass, theXMLTypePackage.getBoolean(), "isApplicationNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkEClass, theXMLTypePackage.getBoolean(), "isSubApplicationNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkEClass, theXMLTypePackage.getBoolean(), "isResourceNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkEClass, theXMLTypePackage.getBoolean(), "isCFBTypeNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkEClass, this.getAutomationSystem(), "getAutomationSystem", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkEClass, this.getApplication(), "getApplication", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, this.getFB(), "getFBNamed", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, this.getSubApp(), "getSubAppNamed", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, this.getFBNetworkElement(), "getElementNamed", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, theXMLTypePackage.getInt(), "getConnectionIndex", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getConnection(), "connection", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, ecorePackage.getEBoolean(), "validateCollisions", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(fbNetworkElementEClass, FBNetworkElement.class, "FBNetworkElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getFBNetworkElement_Interface(), this.getInterfaceList(), null, "interface", null, 0, 1, FBNetworkElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getFBNetworkElement_Mapping(), this.getMapping(), null, "mapping", null, 0, 1, FBNetworkElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getFBNetworkElement_Group(), this.getGroup(), this.getGroup_GroupElements(), "group", null, 0, 1, FBNetworkElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, this.getResource(), "getResource", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkElementEClass, this.getIInterfaceElement(), "getInterfaceElement", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, this.getFBNetworkElement(), "getOpposite", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, this.getFBNetwork(), "getFbNetwork", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, null, "checkConnections", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, ecorePackage.getEBoolean(), "isMapped", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, this.getFBType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, theXMLTypePackage.getBoolean(), "isNestedInSubApp", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, this.getFBNetworkElement(), "getOuterFBNetworkElement", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, theXMLTypePackage.getBoolean(), "isContainedInTypedInstance", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, theXMLTypePackage.getBoolean(), "isInGroup", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkElementEClass, ecorePackage.getEBoolean(), "validateName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, ecorePackage.getEString(), "getQualifiedName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, theXMLTypePackage.getDouble(), "getWidth", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, theXMLTypePackage.getDouble(), "getHeight", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, theXMLTypePackage.getInt(), "getVisibleWidth", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, theXMLTypePackage.getInt(), "getVisibleHeight", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkElementEClass, this.getIInterfaceElement(), "getInput", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkElementEClass, this.getIInterfaceElement(), "getOutput", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(fbTypeEClass, FBType.class, "FBType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getFBType_InterfaceList(), this.getInterfaceList(), null, "interfaceList", null, 1, 1, FBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getFBType_Service(), this.getService(), null, "service", null, 0, 1, FBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbTypeEClass, this.getINamedElement(), "getInputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbTypeEClass, this.getINamedElement(), "getOutputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbTypeEClass, this.getINamedElement(), "getInOutParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbTypeEClass, theDataPackage.getDataType(), "getReturnType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(functionEClass, Function.class, "Function", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(functionBodyEClass, FunctionBody.class, "FunctionBody", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(functionFBTypeEClass, FunctionFBType.class, "FunctionFBType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getFunctionFBType_Body(), this.getFunctionBody(), null, "body", null, 0, 1, FunctionFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(globalConstantsEClass, GlobalConstants.class, "GlobalConstants", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getGlobalConstants_Constants(), this.getVarDeclaration(), null, "constants", null, 0, -1, GlobalConstants.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGlobalConstants_Source(), this.getOriginalSource(), null, "source", null, 0, 1, GlobalConstants.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(groupEClass, Group.class, "Group", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getGroup_GroupElements(), this.getFBNetworkElement(), this.getFBNetworkElement_Group(), "groupElements", null, 0, -1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getGroup_Width(), theXMLTypePackage.getDouble(), "width", "200", 0, 1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getGroup_Height(), theXMLTypePackage.getDouble(), "height", "100", 0, 1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getGroup_Locked(), theXMLTypePackage.getBoolean(), "locked", "false", 0, 1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		addEOperation(groupEClass, this.getInterfaceList(), "getInterface", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(groupEClass, ecorePackage.getEBoolean(), "validateCollisions", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(groupEClass, this.getInterfaceList(), "getInterface", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(hiddenElementEClass, HiddenElement.class, "HiddenElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(hiddenElementEClass, null, "setVisible", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getBoolean(), "visible", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(hiddenElementEClass, theXMLTypePackage.getBoolean(), "isVisible", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(iCallableEClass, ICallable.class, "ICallable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(iCallableEClass, this.getINamedElement(), "getInputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(iCallableEClass, this.getINamedElement(), "getOutputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(iCallableEClass, this.getINamedElement(), "getInOutParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(iCallableEClass, theDataPackage.getDataType(), "getReturnType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(identificationEClass, Identification.class, "Identification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getIdentification_ApplicationDomain(), theXMLTypePackage.getString(), "applicationDomain", null, 0, 1, Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getIdentification_Classification(), theXMLTypePackage.getString(), "classification", "", 0, 1, Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getIdentification_Description(), theXMLTypePackage.getString(), "description", "", 0, 1, Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getIdentification_Function(), theXMLTypePackage.getString(), "function", "", 0, 1, Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getIdentification_Standard(), theXMLTypePackage.getString(), "standard", "", 0, 1, Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getIdentification_Type(), theXMLTypePackage.getString(), "type", "", 0, 1, Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(iInterfaceElementEClass, IInterfaceElement.class, "IInterfaceElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getIInterfaceElement_IsInput(), theXMLTypePackage.getBoolean(), "isInput", null, 0, 1, IInterfaceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getIInterfaceElement_InputConnections(), this.getConnection(), this.getConnection_Destination(), "inputConnections", null, 0, -1, IInterfaceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getIInterfaceElement_OutputConnections(), this.getConnection(), this.getConnection_Source(), "outputConnections", null, 0, -1, IInterfaceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getIInterfaceElement_Type(), theDataPackage.getDataType(), null, "type", null, 0, 1, IInterfaceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(iInterfaceElementEClass, this.getFBNetworkElement(), "getFBNetworkElement", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(iInterfaceElementEClass, ecorePackage.getEBoolean(), "validateName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(importEClass, Import.class, "Import", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getImport_ImportedNamespace(), theXMLTypePackage.getString(), "importedNamespace", null, 0, 1, Import.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(iNamedElementEClass, INamedElement.class, "INamedElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getINamedElement_Name(), theXMLTypePackage.getString(), "name", "", 1, 1, INamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getINamedElement_Comment(), theXMLTypePackage.getString(), "comment", "", 0, 1, INamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		addEOperation(iNamedElementEClass, ecorePackage.getEString(), "getQualifiedName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(iNamedElementEClass, this.getNamedElementStream(), "findBySimpleName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(iNamedElementEClass, this.getNamedElementStream(), "findByQualifiedName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(iNamedElementEClass, ecorePackage.getEBoolean(), "validateName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(inputPrimitiveEClass, InputPrimitive.class, "InputPrimitive", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(interfaceListEClass, InterfaceList.class, "InterfaceList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInterfaceList_EventInputs(), this.getEvent(), null, "eventInputs", null, 0, -1, InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterfaceList_EventOutputs(), this.getEvent(), null, "eventOutputs", null, 0, -1, InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterfaceList_InputVars(), this.getVarDeclaration(), null, "inputVars", null, 0, -1, InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterfaceList_OutputVars(), this.getVarDeclaration(), null, "outputVars", null, 0, -1, InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterfaceList_InOutVars(), this.getVarDeclaration(), null, "inOutVars", null, 0, -1, InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterfaceList_OutMappedInOutVars(), this.getVarDeclaration(), null, "outMappedInOutVars", null, 0, -1, InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterfaceList_Plugs(), this.getAdapterDeclaration(), null, "plugs", null, 0, -1, InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterfaceList_Sockets(), this.getAdapterDeclaration(), null, "sockets", null, 0, -1, InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterfaceList_ErrorMarker(), this.getErrorMarkerInterface(), null, "errorMarker", null, 0, -1, InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(interfaceListEClass, null, "getAllInterfaceElements", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEEList());
		g2 = createEGenericType(this.getIInterfaceElement());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = addEOperation(interfaceListEClass, this.getEvent(), "getEvent", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(interfaceListEClass, this.getVarDeclaration(), "getVariable", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(interfaceListEClass, this.getIInterfaceElement(), "getInterfaceElement", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(interfaceListEClass, this.getFBNetworkElement(), "getFBNetworkElement", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(interfaceListEClass, this.getAdapterDeclaration(), "getAdapter", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(interfaceListEClass, this.getInterfaceList(), "copy", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(interfaceListEClass, this.getVarDeclList(), "getVisibleInputVars", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(interfaceListEClass, this.getVarDeclList(), "getVisibleOutputVars", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(interfaceListEClass, this.getInterfaceElementStream(), "getInputs", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(interfaceListEClass, this.getInterfaceElementStream(), "getOutputs", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(interfaceListEClass, this.getIInterfaceElement(), "getInput", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(interfaceListEClass, this.getIInterfaceElement(), "getOutput", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(iTypedElementEClass, ITypedElement.class, "ITypedElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(iTypedElementEClass, this.getINamedElement(), "getType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(iTypedElementEClass, ecorePackage.getEString(), "getTypeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(iTypedElementEClass, ecorePackage.getEString(), "getFullTypeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(iTypedElementEClass, ecorePackage.getEBoolean(), "validateType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(iVarElementEClass, IVarElement.class, "IVarElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getIVarElement_VarDeclarations(), this.getVarDeclaration(), null, "varDeclarations", null, 0, -1, IVarElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(libraryElementEClass, LibraryElement.class, "LibraryElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getLibraryElement_VersionInfo(), this.getVersionInfo(), null, "versionInfo", null, 0, -1, LibraryElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLibraryElement_Identification(), this.getIdentification(), null, "identification", null, 0, 1, LibraryElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLibraryElement_CompilerInfo(), this.getCompilerInfo(), null, "compilerInfo", null, 0, 1, LibraryElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getLibraryElement_TypeEntry(), this.getTypeEntry(), "typeEntry", null, 0, 1, LibraryElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(libraryElementEClass, this.getTypeLibrary(), "getTypeLibrary", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(libraryElementEClass, null, "setDocumentation", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "value", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(libraryElementEClass, ecorePackage.getEString(), "getDocumentation", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(linkEClass, Link.class, "Link", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getLink_Segment(), this.getSegment(), this.getSegment_OutConnections(), "segment", null, 0, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLink_Device(), this.getDevice(), this.getDevice_InConnections(), "device", null, 0, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(localVariableEClass, LocalVariable.class, "LocalVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getLocalVariable_ArrayStart(), theXMLTypePackage.getInt(), "arrayStart", null, 0, 1, LocalVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getLocalVariable_ArrayStop(), theXMLTypePackage.getInt(), "arrayStop", null, 0, 1, LocalVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(localVariableEClass, theXMLTypePackage.getInt(), "getArraySizeAsInt", 0, 1, !IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(mappingEClass, Mapping.class, "Mapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getMapping_From(), this.getFBNetworkElement(), null, "from", null, 0, 1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMapping_To(), this.getFBNetworkElement(), null, "to", null, 0, 1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(mappingEClass, this.getAutomationSystem(), "getAutomationSystem", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(mappingTargetEClass, MappingTarget.class, "MappingTarget", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(memberVarDeclarationEClass, MemberVarDeclaration.class, "MemberVarDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getMemberVarDeclaration_ParentNames(), ecorePackage.getEString(), "parentNames", null, 0, -1, MemberVarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(memberVarDeclarationEClass, ecorePackage.getEString(), "getName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(memberVarDeclarationEClass, ecorePackage.getEString(), "getVarName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(methodEClass, Method.class, "Method", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(multiplexerEClass, Multiplexer.class, "Multiplexer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(multiplexerEClass, this.getVarDeclaration(), "getMemberVars", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(originalSourceEClass, OriginalSource.class, "OriginalSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getOriginalSource_Text(), ecorePackage.getEString(), "text", "\"\"", 0, 1, OriginalSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		initEClass(otherAlgorithmEClass, OtherAlgorithm.class, "OtherAlgorithm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getOtherAlgorithm_Language(), theXMLTypePackage.getString(), "language", null, 1, 1, OtherAlgorithm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(otherMethodEClass, OtherMethod.class, "OtherMethod", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getOtherMethod_Language(), theXMLTypePackage.getString(), "language", null, 1, 1, OtherMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(outputPrimitiveEClass, OutputPrimitive.class, "OutputPrimitive", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(positionEClass, Position.class, "Position", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPosition_X(), theXMLTypePackage.getDouble(), "x", "0", 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getPosition_Y(), theXMLTypePackage.getDouble(), "y", "0", 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		addEOperation(positionEClass, this.getPoint(), "toScreenPoint", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(positionableElementEClass, PositionableElement.class, "PositionableElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getPositionableElement_Position(), this.getPosition(), null, "position", null, 0, 1, PositionableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(positionableElementEClass, null, "updatePositionFromScreenCoordinates", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getInt(), "x", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getInt(), "y", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(positionableElementEClass, null, "updatePositionFromScreenCoordinates", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getPoint(), "newPos", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(primitiveEClass, Primitive.class, "Primitive", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPrimitive_Event(), theXMLTypePackage.getString(), "event", null, 1, 1, Primitive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPrimitive_Parameters(), theXMLTypePackage.getString(), "parameters", null, 0, 1, Primitive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPrimitive_Interface(), this.getServiceInterface(), null, "interface", null, 0, 1, Primitive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(primitiveEClass, this.getServiceTransaction(), "getServiceTransaction", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(primitiveEClass, this.getService(), "getService", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(resourceEClass, Resource.class, "Resource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getResource_FBNetwork(), this.getFBNetwork(), null, "fBNetwork", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getResource_X(), theXMLTypePackage.getString(), "x", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getResource_Y(), theXMLTypePackage.getString(), "y", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getResource_Device(), this.getDevice(), this.getDevice_Resource(), "device", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getResource_DeviceTypeResource(), theXMLTypePackage.getBoolean(), "deviceTypeResource", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(resourceEClass, this.getAutomationSystem(), "getAutomationSystem", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(resourceEClass, this.getResourceType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(resourceTypeNameEClass, ResourceTypeName.class, "ResourceTypeName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getResourceTypeName_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ResourceTypeName.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(resourceTypeEClass, ResourceType.class, "ResourceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getResourceType_VarDeclaration(), this.getVarDeclaration(), null, "varDeclaration", null, 0, -1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getResourceType_FBNetwork(), this.getFBNetwork(), null, "fBNetwork", null, 1, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getResourceType_SupportedFBTypes(), this.getFBType(), null, "supportedFBTypes", null, 0, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(resourceTypeFBEClass, ResourceTypeFB.class, "ResourceTypeFB", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(resourceTypeFBEClass, ecorePackage.getEBoolean(), "isResourceTypeFB", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(segmentEClass, Segment.class, "Segment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSegment_Width(), theXMLTypePackage.getDouble(), "width", "200", 0, 1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getSegment_OutConnections(), this.getLink(), this.getLink_Segment(), "outConnections", null, 0, -1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSegment_Communication(), this.getCommunicationConfiguration(), null, "communication", null, 0, 1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(segmentEClass, this.getSegmentType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(segmentEClass, this.getAutomationSystem(), "getAutomationSystem", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(segmentEClass, this.getSystemConfiguration(), "getSystemConfiguration", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(segmentTypeEClass, SegmentType.class, "SegmentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSegmentType_VarDeclaration(), this.getVarDeclaration(), null, "varDeclaration", null, 0, -1, SegmentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(serviceEClass, Service.class, "Service", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getService_RightInterface(), this.getServiceInterface(), null, "rightInterface", null, 0, 1, Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getService_LeftInterface(), this.getServiceInterface(), null, "leftInterface", null, 0, 1, Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getService_ServiceSequence(), this.getServiceSequence(), null, "serviceSequence", null, 0, -1, Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(serviceEClass, this.getFBType(), "getFBType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(serviceSequenceEClass, ServiceSequence.class, "ServiceSequence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getServiceSequence_ServiceTransaction(), this.getServiceTransaction(), null, "serviceTransaction", null, 0, -1, ServiceSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getServiceSequence_ServiceSequenceType(), ecorePackage.getEString(), "serviceSequenceType", "POSSIBLE", 0, 1, ServiceSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getServiceSequence_StartState(), ecorePackage.getEString(), "startState", null, 0, 1, ServiceSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getServiceSequence_EventManager(), ecorePackage.getEObject(), null, "eventManager", null, 0, 1, ServiceSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(serviceSequenceEClass, this.getService(), "getService", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(serviceTransactionEClass, ServiceTransaction.class, "ServiceTransaction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getServiceTransaction_InputPrimitive(), this.getInputPrimitive(), null, "inputPrimitive", null, 0, 1, ServiceTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getServiceTransaction_OutputPrimitive(), this.getOutputPrimitive(), null, "outputPrimitive", null, 0, -1, ServiceTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(serviceTransactionEClass, this.getServiceSequence(), "getServiceSequence", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(serviceInterfaceEClass, ServiceInterface.class, "ServiceInterface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(serviceInterfaceEClass, this.getService(), "getService", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(serviceInterfaceEClass, ecorePackage.getEBoolean(), "isLeftInterface", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(serviceInterfaceFBTypeEClass, ServiceInterfaceFBType.class, "ServiceInterfaceFBType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(simpleFBTypeEClass, SimpleFBType.class, "SimpleFBType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stAlgorithmEClass, STAlgorithm.class, "STAlgorithm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stFunctionEClass, STFunction.class, "STFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stFunctionBodyEClass, STFunctionBody.class, "STFunctionBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stMethodEClass, STMethod.class, "STMethod", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(subAppEClass, SubApp.class, "SubApp", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSubApp_Width(), theXMLTypePackage.getDouble(), "width", "200", 0, 1, SubApp.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getSubApp_Height(), theXMLTypePackage.getDouble(), "height", "100", 0, 1, SubApp.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getSubApp_Locked(), theXMLTypePackage.getBoolean(), "locked", "false", 0, 1, SubApp.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		addEOperation(subAppEClass, this.getSubAppType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(subAppEClass, theXMLTypePackage.getBoolean(), "isUnfolded", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(subAppEClass, theXMLTypePackage.getBoolean(), "isTyped", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(subAppEClass, this.getFBNetwork(), "getSubAppNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(subAppEClass, this.getFBNetwork(), "loadSubAppNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(subAppEClass, theXMLTypePackage.getInt(), "getVisibleWidth", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(subAppEClass, theXMLTypePackage.getInt(), "getVisibleHeight", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(structManipulatorEClass, StructManipulator.class, "StructManipulator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(structManipulatorEClass, null, "updateConfiguration", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(structManipulatorEClass, this.getVarDeclaration(), "getMemberVars", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(subAppTypeEClass, SubAppType.class, "SubAppType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(systemConfigurationEClass, SystemConfiguration.class, "SystemConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSystemConfiguration_Devices(), this.getDevice(), null, "devices", null, 0, -1, SystemConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSystemConfiguration_Segments(), this.getSegment(), null, "segments", null, 0, -1, SystemConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSystemConfiguration_Links(), this.getLink(), null, "links", null, 0, -1, SystemConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(systemConfigurationEClass, this.getAutomationSystem(), "getAutomationSystem", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(systemConfigurationEClass, this.getSegment(), "getSegmentNamed", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(systemConfigurationEClass, this.getDevice(), "getDeviceNamed", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(textAlgorithmEClass, TextAlgorithm.class, "TextAlgorithm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getTextAlgorithm_Text(), ecorePackage.getEString(), "text", null, 0, 1, TextAlgorithm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(textFunctionEClass, TextFunction.class, "TextFunction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTextFunction_InputParameters(), this.getINamedElement(), null, "inputParameters", null, 0, -1, TextFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTextFunction_OutputParameters(), this.getINamedElement(), null, "outputParameters", null, 0, -1, TextFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTextFunction_InOutParameters(), this.getINamedElement(), null, "inOutParameters", null, 0, -1, TextFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTextFunction_ReturnType(), theDataPackage.getDataType(), null, "returnType", null, 0, 1, TextFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getTextFunction_Text(), ecorePackage.getEString(), "text", null, 0, 1, TextFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(textFunctionBodyEClass, TextFunctionBody.class, "TextFunctionBody", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getTextFunctionBody_Text(), ecorePackage.getEString(), "text", null, 0, 1, TextFunctionBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(textMethodEClass, TextMethod.class, "TextMethod", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTextMethod_InputParameters(), this.getINamedElement(), null, "inputParameters", null, 0, -1, TextMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTextMethod_OutputParameters(), this.getINamedElement(), null, "outputParameters", null, 0, -1, TextMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTextMethod_InOutParameters(), this.getINamedElement(), null, "inOutParameters", null, 0, -1, TextMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTextMethod_ReturnType(), theDataPackage.getDataType(), null, "returnType", null, 0, 1, TextMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getTextMethod_Text(), ecorePackage.getEString(), "text", null, 0, 1, TextMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(typedConfigureableObjectEClass, TypedConfigureableObject.class, "TypedConfigureableObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getTypedConfigureableObject_TypeEntry(), this.getTypeEntry(), "typeEntry", null, 0, 1, TypedConfigureableObject.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(typedConfigureableObjectEClass, ecorePackage.getEString(), "getTypeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(typedConfigureableObjectEClass, this.getLibraryElement(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(typedConfigureableObjectEClass, this.getTypeLibrary(), "getTypeLibrary", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(typedConfigureableObjectEClass, ecorePackage.getEString(), "getFullTypeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(typedConfigureableObjectEClass, ecorePackage.getEBoolean(), "validateType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(typedSubAppEClass, TypedSubApp.class, "TypedSubApp", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTypedSubApp_SubAppNetwork(), this.getFBNetwork(), null, "subAppNetwork", null, 0, 1, TypedSubApp.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(typedSubAppEClass, theXMLTypePackage.getBoolean(), "isTyped", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(typedSubAppEClass, this.getFBNetwork(), "loadSubAppNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(typedSubAppEClass, this.getNamedElementStream(), "findBySimpleName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(untypedSubAppEClass, UntypedSubApp.class, "UntypedSubApp", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getUntypedSubApp_SubAppNetwork(), this.getFBNetwork(), null, "subAppNetwork", null, 0, 1, UntypedSubApp.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(untypedSubAppEClass, this.getSubAppType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(untypedSubAppEClass, theXMLTypePackage.getBoolean(), "isTyped", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(untypedSubAppEClass, null, "setTypeEntry", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getTypeEntry(), "typeEntry", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(untypedSubAppEClass, this.getFBNetwork(), "loadSubAppNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(valueEClass, Value.class, "Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getValue_Value(), ecorePackage.getEString(), "value", "", 0, 1, Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		addEOperation(valueEClass, this.getIInterfaceElement(), "getParentIE", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(varDeclarationEClass, VarDeclaration.class, "VarDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getVarDeclaration_ArraySize(), this.getArraySize(), this.getArraySize_VarDeclaration(), "arraySize", null, 0, 1, VarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getVarDeclaration_Withs(), this.getWith(), this.getWith_Variables(), "withs", null, 0, -1, VarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getVarDeclaration_Value(), this.getValue(), null, "value", null, 0, 1, VarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(varDeclarationEClass, theXMLTypePackage.getBoolean(), "isVarConfig", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(varDeclarationEClass, theXMLTypePackage.getBoolean(), "isArray", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(varDeclarationEClass, null, "setVarConfig", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getBoolean(), "config", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(varDeclarationEClass, ecorePackage.getEString(), "getFullTypeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(varDeclarationEClass, ecorePackage.getEBoolean(), "isInOutVar", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(varDeclarationEClass, this.getVarDeclaration(), "getInOutVarOpposite", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(varDeclarationEClass, ecorePackage.getEBoolean(), "validateMultipleInputConnections", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(varDeclarationEClass, ecorePackage.getEBoolean(), "validateNoValueForGenericTypeVariable", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(varDeclarationEClass, ecorePackage.getEBoolean(), "validateValueForGenericInstanceVariable", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(varDeclarationEClass, ecorePackage.getEBoolean(), "validateVarInOutSourceTypeIsWellDefined", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(varDeclarationEClass, ecorePackage.getEBoolean(), "validateVarInOutIsWithed", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(varDeclarationEClass, ecorePackage.getEBoolean(), "validateVarInOutSubappInterface", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(varDeclarationEClass, ecorePackage.getEBoolean(), "validateVarInOutSubappNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(versionInfoEClass, VersionInfo.class, "VersionInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getVersionInfo_Author(), theXMLTypePackage.getString(), "author", "", 1, 1, VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getVersionInfo_Date(), theXMLTypePackage.getString(), "date", "YYYY-MM-DD", 1, 1, VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getVersionInfo_Organization(), theXMLTypePackage.getString(), "organization", "", 1, 1, VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getVersionInfo_Remarks(), theXMLTypePackage.getString(), "remarks", "", 0, 1, VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getVersionInfo_Version(), theXMLTypePackage.getString(), "version", "", 1, 1, VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(withEClass, With.class, "With", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getWith_Variables(), this.getVarDeclaration(), this.getVarDeclaration_Withs(), "variables", null, 1, 1, With.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(languageEEnum, Language.class, "Language"); //$NON-NLS-1$
		addEEnumLiteral(languageEEnum, Language.C);
		addEEnumLiteral(languageEEnum, Language.OTHER);
		addEEnumLiteral(languageEEnum, Language.JAVA);
		addEEnumLiteral(languageEEnum, Language.CPP);

		// Initialize data types
		initEDataType(commandStackEDataType, CommandStack.class, "CommandStack", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(iFileEDataType, IFile.class, "IFile", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(interfaceElementStreamEDataType, Stream.class, "InterfaceElementStream", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.stream.Stream<org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement>"); //$NON-NLS-1$ //$NON-NLS-2$
		initEDataType(iProjectEDataType, IProject.class, "IProject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(namedElementStreamEDataType, Stream.class, "NamedElementStream", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.stream.Stream<org.eclipse.fordiac.ide.model.libraryElement.INamedElement>"); //$NON-NLS-1$ //$NON-NLS-2$
		initEDataType(pointEDataType, Point.class, "Point", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(typeEntryEDataType, TypeEntry.class, "TypeEntry", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(typeLibraryEDataType, TypeLibrary.class, "TypeLibrary", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(varDeclListEDataType, List.class, "VarDeclList", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.List<org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration>"); //$NON-NLS-1$ //$NON-NLS-2$

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http:///org/eclipse/fordiac/ide/model/MetaData
		createMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for
	 * <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$
		addAnnotation
		  (getApplication_FBNetwork(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "FBNetwork", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getAttribute_Value(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Value" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getBaseFBType_InternalVars(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "VarDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getBaseFBType_InternalConstVars(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "VarDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getBaseFBType_InternalFbs(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "VarDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getBaseFBType_Algorithm(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Algorithm", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getBaseFBType_Methods(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Method", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getBaseFBType_Callables(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Callable", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getBasicFBType_ECC(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "ECC", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getAutomationSystem_Application(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Application", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getAutomationSystem_Mapping(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Mapping", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getCompiler_Language(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Language" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getCompiler_Product(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Product" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getCompiler_Vendor(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Vendor" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getCompiler_Version(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Version" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getCompilerInfo_Compiler(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Compiler", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getCompilerInfo_Classdef(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "classdef" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getCompilerInfo_Header(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "header" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getCompilerInfo_PackageName(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "packageName" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getCompositeFBType_FBNetwork(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "FBNetwork", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getConnectionRoutingData_Dx1(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "dx1" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getConnectionRoutingData_Dx2(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "dx2" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getConnectionRoutingData_Dy(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "dy" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getDevice_Resource(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Resource", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getDeviceType_VarDeclaration(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "VarDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getDeviceType_ResourceTypeName(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "ResourceTypeName", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getDeviceType_Resource(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Resource", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getDeviceType_FBNetwork(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "FBNetwork", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getECC_ECState(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "ECState", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getECC_ECTransition(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "ECTransition", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getECState_ECAction(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "ECAction", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getECTransition_Comment(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Comment" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getECTransition_ConditionExpression(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Condition" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getEvent_With(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "With", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getFBNetwork_NetworkElements(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "FB", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getFBNetwork_DataConnections(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "DataConnections", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getFBNetwork_AdapterConnections(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "AdapterConnections", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getFBType_InterfaceList(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "InterfaceList", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getFBType_Service(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "ServiceSequence", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getGroup_GroupElements(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "FB", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getIdentification_ApplicationDomain(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "ApplicationDomain" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getIdentification_Classification(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Classification" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getIdentification_Description(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Description" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getIdentification_Function(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Function" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getIdentification_Standard(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Standard" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getIdentification_Type(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Type" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getImport_ImportedNamespace(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "declaration" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getInterfaceList_EventInputs(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "SubAppEventInputs", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getInterfaceList_EventOutputs(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "SubAppEventOutputs", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getInterfaceList_InputVars(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "InputVars", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getInterfaceList_OutputVars(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "OutputVars", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getInterfaceList_Plugs(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "AdapterDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getInterfaceList_Sockets(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "AdapterDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getInterfaceList_ErrorMarker(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "ErrorMarker", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getOtherAlgorithm_Language(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Language" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getOtherMethod_Language(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Language" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getPositionableElement_Position(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Position", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getPrimitive_Event(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Event" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getPrimitive_Parameters(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Parameters" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getResource_FBNetwork(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "FBNetwork", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getResource_X(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "x" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getResource_Y(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "y" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getResourceTypeName_Name(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Name" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSegment_Width(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "dx1" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getService_ServiceSequence(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "OutputPrimitive", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getServiceSequence_ServiceTransaction(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "ServiceTransaction", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getServiceTransaction_InputPrimitive(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "InputPrimitive", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getServiceTransaction_OutputPrimitive(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "OutputPrimitive", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSystemConfiguration_Devices(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Device", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSystemConfiguration_Segments(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Segment", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSystemConfiguration_Links(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Link", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getVersionInfo_Author(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Author" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getVersionInfo_Date(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Date" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getVersionInfo_Organization(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Organization" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getVersionInfo_Remarks(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Remarks" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getVersionInfo_Version(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Version" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore"; //$NON-NLS-1$
		addAnnotation
		  (attributeEClass.getEOperations().get(1),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (compilerInfoEClass.getEOperations().get(0),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (configurableFBEClass.getEOperations().get(3),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(6),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(7),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(8),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(9),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(10),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(11),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(12),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(13),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(14),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(15),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (connectionEClass.getEOperations().get(16),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (errorMarkerInterfaceEClass.getEOperations().get(0),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (errorMarkerInterfaceEClass.getEOperations().get(1),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (fbNetworkEClass.getEOperations().get(13),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (fbNetworkElementEClass.getEOperations().get(11),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (groupEClass.getEOperations().get(1),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (iInterfaceElementEClass.getEOperations().get(1),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (iNamedElementEClass.getEOperations().get(3),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (iTypedElementEClass.getEOperations().get(3),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (typedConfigureableObjectEClass.getEOperations().get(4),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (varDeclarationEClass.getEOperations().get(6),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (varDeclarationEClass.getEOperations().get(7),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (varDeclarationEClass.getEOperations().get(8),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (varDeclarationEClass.getEOperations().get(9),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (varDeclarationEClass.getEOperations().get(10),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (varDeclarationEClass.getEOperations().get(11),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (varDeclarationEClass.getEOperations().get(12),
		   source,
		   new String[] {
			   "invariant", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/fordiac/ide/model/MetaData</b>.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	protected void createMetaDataAnnotations() {
		String source = "http:///org/eclipse/fordiac/ide/model/MetaData"; //$NON-NLS-1$
		addAnnotation
		  (getInterfaceList_OutMappedInOutVars(),
		   source,
		   new String[] {
			   "synthetic", "true" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

} // LibraryElementPackageImpl
