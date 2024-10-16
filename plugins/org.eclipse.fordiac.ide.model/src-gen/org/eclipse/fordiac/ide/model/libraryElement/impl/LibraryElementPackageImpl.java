/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.IVarElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.LocalVariable;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.TypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.commands.CommandStack;

/** <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated */
public class LibraryElementPackageImpl extends EPackageImpl implements LibraryElementPackage {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass adapterDeclarationEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass adapterTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass algorithmEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass applicationEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass basicFBTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass compilerInfoEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass compilerEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass connectionEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass connectionRoutingDataEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass deviceEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass deviceTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass ecActionEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass eccEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass ecStateEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass ecTransitionEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass eventEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass fbEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass fbNetworkElementEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass subAppEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass fbTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass identificationEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass inputPrimitiveEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass interfaceListEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass linkEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass mappingEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass otherAlgorithmEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass outputPrimitiveEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass attributeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass resourceEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass resourceTypeNameEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass resourceTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass segmentEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass serviceSequenceEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass serviceTransactionEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass serviceInterfaceFBTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass stAlgorithmEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass fbNetworkEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass subAppTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass automationSystemEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass varDeclarationEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass versionInfoEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass withEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass libraryElementEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass compilableTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass configurableObjectEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass compositeFBTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass textAlgorithmEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass dataConnectionEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass eventConnectionEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass adapterConnectionEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass serviceInterfaceEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass iInterfaceElementEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass valueEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass systemConfigurationEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass iNamedElementEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass resourceTypeFBEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass segmentTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass adapterFBTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass adapterEventEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass serviceEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass typedConfigureableObjectEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass adapterFBEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass primitiveEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass positionableElementEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass positionEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass colorEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass colorizableElementEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass iVarElementEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass attributeDeclarationEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass typedElementEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass simpleFBTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass baseFBTypeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass structManipulatorEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass demultiplexerEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass multiplexerEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass localVariableEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass errorMarkerFBNElementEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass errorMarkerInterfaceEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass cfbInstanceEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass errorMarkerRefEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EEnum languageEEnum = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EDataType iProjectEDataType = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EDataType iFileEDataType = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EDataType commandStackEDataType = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EDataType pointEDataType = null;

	/** Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#eNS_URI
	 * @see #init()
	 * @generated */
	private LibraryElementPackageImpl() {
		super(eNS_URI, LibraryElementFactory.eINSTANCE);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private static boolean isInited = false;

	/** Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link LibraryElementPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated */
	public static LibraryElementPackage init() {
		if (isInited) {
			return (LibraryElementPackage) EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredLibraryElementPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final LibraryElementPackageImpl theLibraryElementPackage = registeredLibraryElementPackage instanceof LibraryElementPackageImpl
				? (LibraryElementPackageImpl) registeredLibraryElementPackage
				: new LibraryElementPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PalettePackage.eNS_URI);
		final PalettePackageImpl thePalettePackage = (PalettePackageImpl) (registeredPackage instanceof PalettePackageImpl
				? registeredPackage
				: PalettePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);
		final DataPackageImpl theDataPackage = (DataPackageImpl) (registeredPackage instanceof DataPackageImpl
				? registeredPackage
				: DataPackage.eINSTANCE);

		// Create package meta-data objects
		theLibraryElementPackage.createPackageContents();
		thePalettePackage.createPackageContents();
		theDataPackage.createPackageContents();

		// Initialize created meta-data
		theLibraryElementPackage.initializePackageContents();
		thePalettePackage.initializePackageContents();
		theDataPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLibraryElementPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LibraryElementPackage.eNS_URI, theLibraryElementPackage);
		return theLibraryElementPackage;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getAdapterDeclaration() {
		return adapterDeclarationEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAdapterDeclaration_AdapterFB() {
		return (EReference) adapterDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAdapterDeclaration_PaletteEntry() {
		return (EReference) adapterDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getAdapterType() {
		return adapterTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAdapterType_AdapterFBType() {
		return (EReference) adapterTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getAlgorithm() {
		return algorithmEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getApplication() {
		return applicationEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getApplication_FBNetwork() {
		return (EReference) applicationEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getBasicFBType() {
		return basicFBTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getBasicFBType_ECC() {
		return (EReference) basicFBTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getBasicFBType_Algorithm() {
		return (EReference) basicFBTypeEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getCompilerInfo() {
		return compilerInfoEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getCompilerInfo_Compiler() {
		return (EReference) compilerInfoEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getCompilerInfo_Classdef() {
		return (EAttribute) compilerInfoEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getCompilerInfo_Header() {
		return (EAttribute) compilerInfoEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getCompiler() {
		return compilerEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getCompiler_Language() {
		return (EAttribute) compilerEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getCompiler_Product() {
		return (EAttribute) compilerEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getCompiler_Vendor() {
		return (EAttribute) compilerEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getCompiler_Version() {
		return (EAttribute) compilerEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getConnection() {
		return connectionEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getConnection_ResTypeConnection() {
		return (EAttribute) connectionEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getConnection_BrokenConnection() {
		return (EAttribute) connectionEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getConnection_Source() {
		return (EReference) connectionEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getConnection_Destination() {
		return (EReference) connectionEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getConnection_RoutingData() {
		return (EReference) connectionEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getConnectionRoutingData() {
		return connectionRoutingDataEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getConnectionRoutingData_Dx1() {
		return (EAttribute) connectionRoutingDataEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getConnectionRoutingData_Dx2() {
		return (EAttribute) connectionRoutingDataEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getConnectionRoutingData_Dy() {
		return (EAttribute) connectionRoutingDataEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getConnectionRoutingData_NeedsValidation() {
		return (EAttribute) connectionRoutingDataEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getDevice() {
		return deviceEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getDevice_Resource() {
		return (EReference) deviceEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getDevice_Profile() {
		return (EAttribute) deviceEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getDevice_InConnections() {
		return (EReference) deviceEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getDeviceType() {
		return deviceTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getDeviceType_VarDeclaration() {
		return (EReference) deviceTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getDeviceType_ResourceTypeName() {
		return (EReference) deviceTypeEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getDeviceType_Resource() {
		return (EReference) deviceTypeEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getDeviceType_FBNetwork() {
		return (EReference) deviceTypeEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getDeviceType_Profile() {
		return (EAttribute) deviceTypeEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getDeviceType_AttributeDeclarations() {
		return (EReference) deviceTypeEClass.getEStructuralFeatures().get(5);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getECAction() {
		return ecActionEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECAction_Algorithm() {
		return (EReference) ecActionEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECAction_Output() {
		return (EReference) ecActionEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECAction_ECState() {
		return (EReference) ecActionEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getECC() {
		return eccEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECC_ECState() {
		return (EReference) eccEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECC_ECTransition() {
		return (EReference) eccEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECC_Start() {
		return (EReference) eccEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECC_BasicFBType() {
		return (EReference) eccEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getECState() {
		return ecStateEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECState_ECAction() {
		return (EReference) ecStateEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECState_OutTransitions() {
		return (EReference) ecStateEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECState_InTransitions() {
		return (EReference) ecStateEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECState_ECC() {
		return (EReference) ecStateEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getECTransition() {
		return ecTransitionEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getECTransition_Comment() {
		return (EAttribute) ecTransitionEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getECTransition_ConditionExpression() {
		return (EAttribute) ecTransitionEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECTransition_Source() {
		return (EReference) ecTransitionEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECTransition_Destination() {
		return (EReference) ecTransitionEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECTransition_ConditionEvent() {
		return (EReference) ecTransitionEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getECTransition_ECC() {
		return (EReference) ecTransitionEClass.getEStructuralFeatures().get(5);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getEvent() {
		return eventEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getEvent_With() {
		return (EReference) eventEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getFB() {
		return fbEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getFBNetworkElement() {
		return fbNetworkElementEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBNetworkElement_Interface() {
		return (EReference) fbNetworkElementEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBNetworkElement_Mapping() {
		return (EReference) fbNetworkElementEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getSubApp() {
		return subAppEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getSubApp_SubAppNetwork() {
		return (EReference) subAppEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getFBType() {
		return fbTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBType_InterfaceList() {
		return (EReference) fbTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBType_Service() {
		return (EReference) fbTypeEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getIdentification() {
		return identificationEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getIdentification_ApplicationDomain() {
		return (EAttribute) identificationEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getIdentification_Classification() {
		return (EAttribute) identificationEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getIdentification_Description() {
		return (EAttribute) identificationEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getIdentification_Function() {
		return (EAttribute) identificationEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getIdentification_Standard() {
		return (EAttribute) identificationEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getIdentification_Type() {
		return (EAttribute) identificationEClass.getEStructuralFeatures().get(5);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getInputPrimitive() {
		return inputPrimitiveEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getInterfaceList() {
		return interfaceListEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getInterfaceList_Plugs() {
		return (EReference) interfaceListEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getInterfaceList_Sockets() {
		return (EReference) interfaceListEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getInterfaceList_EventInputs() {
		return (EReference) interfaceListEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getInterfaceList_EventOutputs() {
		return (EReference) interfaceListEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getInterfaceList_InputVars() {
		return (EReference) interfaceListEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getInterfaceList_OutputVars() {
		return (EReference) interfaceListEClass.getEStructuralFeatures().get(5);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getInterfaceList_ErrorMarker() {
		return (EReference) interfaceListEClass.getEStructuralFeatures().get(6);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getLink() {
		return linkEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getLink_Segment() {
		return (EReference) linkEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getLink_Device() {
		return (EReference) linkEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getMapping() {
		return mappingEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getMapping_From() {
		return (EReference) mappingEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getMapping_To() {
		return (EReference) mappingEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getOtherAlgorithm() {
		return otherAlgorithmEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getOtherAlgorithm_Language() {
		return (EAttribute) otherAlgorithmEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getOutputPrimitive() {
		return outputPrimitiveEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getAttribute() {
		return attributeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAttribute_AttributeDeclaration() {
		return (EReference) attributeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getAttribute_Value() {
		return (EAttribute) attributeEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getResource() {
		return resourceEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getResource_FBNetwork() {
		return (EReference) resourceEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getResource_X() {
		return (EAttribute) resourceEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getResource_Y() {
		return (EAttribute) resourceEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getResource_Device() {
		return (EReference) resourceEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getResource_DeviceTypeResource() {
		return (EAttribute) resourceEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getResourceTypeName() {
		return resourceTypeNameEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getResourceTypeName_Name() {
		return (EAttribute) resourceTypeNameEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getResourceType() {
		return resourceTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getResourceType_VarDeclaration() {
		return (EReference) resourceTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getResourceType_FBNetwork() {
		return (EReference) resourceTypeEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getResourceType_SupportedFBTypes() {
		return (EReference) resourceTypeEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getSegment() {
		return segmentEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getSegment_Width() {
		return (EAttribute) segmentEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getSegment_VarDeclarations() {
		return (EReference) segmentEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getSegment_OutConnections() {
		return (EReference) segmentEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getServiceSequence() {
		return serviceSequenceEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getServiceSequence_ServiceTransaction() {
		return (EReference) serviceSequenceEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getServiceTransaction() {
		return serviceTransactionEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getServiceTransaction_InputPrimitive() {
		return (EReference) serviceTransactionEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getServiceTransaction_OutputPrimitive() {
		return (EReference) serviceTransactionEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getServiceInterfaceFBType() {
		return serviceInterfaceFBTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getSTAlgorithm() {
		return stAlgorithmEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getFBNetwork() {
		return fbNetworkEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBNetwork_NetworkElements() {
		return (EReference) fbNetworkEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBNetwork_DataConnections() {
		return (EReference) fbNetworkEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBNetwork_EventConnections() {
		return (EReference) fbNetworkEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBNetwork_AdapterConnections() {
		return (EReference) fbNetworkEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getSubAppType() {
		return subAppTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getAutomationSystem() {
		return automationSystemEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAutomationSystem_Application() {
		return (EReference) automationSystemEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAutomationSystem_Mapping() {
		return (EReference) automationSystemEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAutomationSystem_Palette() {
		return (EReference) automationSystemEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAutomationSystem_SystemConfiguration() {
		return (EReference) automationSystemEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getAutomationSystem_SystemFile() {
		return (EAttribute) automationSystemEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getAutomationSystem_CommandStack() {
		return (EAttribute) automationSystemEClass.getEStructuralFeatures().get(5);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getVarDeclaration() {
		return varDeclarationEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getVarDeclaration_ArraySize() {
		return (EAttribute) varDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getVarDeclaration_Withs() {
		return (EReference) varDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getVarDeclaration_Value() {
		return (EReference) varDeclarationEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getVersionInfo() {
		return versionInfoEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getVersionInfo_Author() {
		return (EAttribute) versionInfoEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getVersionInfo_Date() {
		return (EAttribute) versionInfoEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getVersionInfo_Organization() {
		return (EAttribute) versionInfoEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getVersionInfo_Remarks() {
		return (EAttribute) versionInfoEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getVersionInfo_Version() {
		return (EAttribute) versionInfoEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getWith() {
		return withEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getWith_Variables() {
		return (EReference) withEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getLibraryElement() {
		return libraryElementEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getLibraryElement_VersionInfo() {
		return (EReference) libraryElementEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getLibraryElement_Identification() {
		return (EReference) libraryElementEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getLibraryElement_PaletteEntry() {
		return (EReference) libraryElementEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getCompilableType() {
		return compilableTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getCompilableType_CompilerInfo() {
		return (EReference) compilableTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getConfigurableObject() {
		return configurableObjectEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getConfigurableObject_Attributes() {
		return (EReference) configurableObjectEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getCompositeFBType() {
		return compositeFBTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getCompositeFBType_FBNetwork() {
		return (EReference) compositeFBTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getTextAlgorithm() {
		return textAlgorithmEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getTextAlgorithm_Text() {
		return (EAttribute) textAlgorithmEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getDataConnection() {
		return dataConnectionEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getEventConnection() {
		return eventConnectionEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getAdapterConnection() {
		return adapterConnectionEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getServiceInterface() {
		return serviceInterfaceEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getIInterfaceElement() {
		return iInterfaceElementEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getIInterfaceElement_IsInput() {
		return (EAttribute) iInterfaceElementEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getIInterfaceElement_InputConnections() {
		return (EReference) iInterfaceElementEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getIInterfaceElement_OutputConnections() {
		return (EReference) iInterfaceElementEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getIInterfaceElement_Type() {
		return (EReference) iInterfaceElementEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getIInterfaceElement_TypeName() {
		return (EAttribute) iInterfaceElementEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getValue() {
		return valueEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getValue_Value() {
		return (EAttribute) valueEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getSystemConfiguration() {
		return systemConfigurationEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getSystemConfiguration_Devices() {
		return (EReference) systemConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getSystemConfiguration_Segments() {
		return (EReference) systemConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getSystemConfiguration_Links() {
		return (EReference) systemConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getINamedElement() {
		return iNamedElementEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getINamedElement_Name() {
		return (EAttribute) iNamedElementEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getINamedElement_Comment() {
		return (EAttribute) iNamedElementEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getResourceTypeFB() {
		return resourceTypeFBEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getSegmentType() {
		return segmentTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getSegmentType_VarDeclaration() {
		return (EReference) segmentTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getAdapterFBType() {
		return adapterFBTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAdapterFBType_AdapterType() {
		return (EReference) adapterFBTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getAdapterEvent() {
		return adapterEventEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAdapterEvent_AdapterDeclaration() {
		return (EReference) adapterEventEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getService() {
		return serviceEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getService_RightInterface() {
		return (EReference) serviceEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getService_LeftInterface() {
		return (EReference) serviceEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getService_ServiceSequence() {
		return (EReference) serviceEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getTypedConfigureableObject() {
		return typedConfigureableObjectEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getTypedConfigureableObject_PaletteEntry() {
		return (EReference) typedConfigureableObjectEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getAdapterFB() {
		return adapterFBEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getAdapterFB_AdapterDecl() {
		return (EReference) adapterFBEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getPrimitive() {
		return primitiveEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getPrimitive_Event() {
		return (EAttribute) primitiveEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getPrimitive_Parameters() {
		return (EAttribute) primitiveEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getPrimitive_Interface() {
		return (EReference) primitiveEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getPositionableElement() {
		return positionableElementEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getPositionableElement_Position() {
		return (EReference) positionableElementEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getPosition() {
		return positionEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getPosition_X() {
		return (EAttribute) positionEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getPosition_Y() {
		return (EAttribute) positionEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getColor() {
		return colorEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getColor_Red() {
		return (EAttribute) colorEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getColor_Green() {
		return (EAttribute) colorEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getColor_Blue() {
		return (EAttribute) colorEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getColorizableElement() {
		return colorizableElementEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getColorizableElement_Color() {
		return (EReference) colorizableElementEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getIVarElement() {
		return iVarElementEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getIVarElement_VarDeclarations() {
		return (EReference) iVarElementEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getAttributeDeclaration() {
		return attributeDeclarationEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getAttributeDeclaration_InitialValue() {
		return (EAttribute) attributeDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getTypedElement() {
		return typedElementEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getTypedElement_Type() {
		return (EAttribute) typedElementEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getSimpleFBType() {
		return simpleFBTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getSimpleFBType_Algorithm() {
		return (EReference) simpleFBTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getBaseFBType() {
		return baseFBTypeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getBaseFBType_InternalVars() {
		return (EReference) baseFBTypeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getBaseFBType_InternalFbs() {
		return (EReference) baseFBTypeEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getStructManipulator() {
		return structManipulatorEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getStructManipulator_StructType() {
		return (EReference) structManipulatorEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getDemultiplexer() {
		return demultiplexerEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getMultiplexer() {
		return multiplexerEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getLocalVariable() {
		return localVariableEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getLocalVariable_ArrayStart() {
		return (EAttribute) localVariableEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getLocalVariable_ArrayStop() {
		return (EAttribute) localVariableEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getErrorMarkerFBNElement() {
		return errorMarkerFBNElementEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getErrorMarkerFBNElement_RepairedElement() {
		return (EReference) errorMarkerFBNElementEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getErrorMarkerInterface() {
		return errorMarkerInterfaceEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getErrorMarkerInterface_RepairedEndpoint() {
		return (EReference) errorMarkerInterfaceEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getCFBInstance() {
		return cfbInstanceEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getCFBInstance_CfbNetwork() {
		return (EReference) cfbInstanceEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getErrorMarkerRef() {
		return errorMarkerRefEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getErrorMarkerRef_FileMarkerId() {
		return (EAttribute) errorMarkerRefEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getErrorMarkerRef_ErrorMessage() {
		return (EAttribute) errorMarkerRefEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EEnum getLanguage() {
		return languageEEnum;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EDataType getIProject() {
		return iProjectEDataType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EDataType getIFile() {
		return iFileEDataType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EDataType getCommandStack() {
		return commandStackEDataType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EDataType getPoint() {
		return pointEDataType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public LibraryElementFactory getLibraryElementFactory() {
		return (LibraryElementFactory) getEFactoryInstance();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private boolean isCreated = false;

	/** Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
	 * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		adapterDeclarationEClass = createEClass(ADAPTER_DECLARATION);
		createEReference(adapterDeclarationEClass, ADAPTER_DECLARATION__ADAPTER_FB);
		createEReference(adapterDeclarationEClass, ADAPTER_DECLARATION__PALETTE_ENTRY);

		adapterTypeEClass = createEClass(ADAPTER_TYPE);
		createEReference(adapterTypeEClass, ADAPTER_TYPE__ADAPTER_FB_TYPE);

		algorithmEClass = createEClass(ALGORITHM);

		applicationEClass = createEClass(APPLICATION);
		createEReference(applicationEClass, APPLICATION__FB_NETWORK);

		basicFBTypeEClass = createEClass(BASIC_FB_TYPE);
		createEReference(basicFBTypeEClass, BASIC_FB_TYPE__ECC);
		createEReference(basicFBTypeEClass, BASIC_FB_TYPE__ALGORITHM);

		compilerInfoEClass = createEClass(COMPILER_INFO);
		createEReference(compilerInfoEClass, COMPILER_INFO__COMPILER);
		createEAttribute(compilerInfoEClass, COMPILER_INFO__CLASSDEF);
		createEAttribute(compilerInfoEClass, COMPILER_INFO__HEADER);

		compilerEClass = createEClass(COMPILER);
		createEAttribute(compilerEClass, COMPILER__LANGUAGE);
		createEAttribute(compilerEClass, COMPILER__PRODUCT);
		createEAttribute(compilerEClass, COMPILER__VENDOR);
		createEAttribute(compilerEClass, COMPILER__VERSION);

		connectionEClass = createEClass(CONNECTION);
		createEAttribute(connectionEClass, CONNECTION__RES_TYPE_CONNECTION);
		createEAttribute(connectionEClass, CONNECTION__BROKEN_CONNECTION);
		createEReference(connectionEClass, CONNECTION__SOURCE);
		createEReference(connectionEClass, CONNECTION__DESTINATION);
		createEReference(connectionEClass, CONNECTION__ROUTING_DATA);

		connectionRoutingDataEClass = createEClass(CONNECTION_ROUTING_DATA);
		createEAttribute(connectionRoutingDataEClass, CONNECTION_ROUTING_DATA__DX1);
		createEAttribute(connectionRoutingDataEClass, CONNECTION_ROUTING_DATA__DX2);
		createEAttribute(connectionRoutingDataEClass, CONNECTION_ROUTING_DATA__DY);
		createEAttribute(connectionRoutingDataEClass, CONNECTION_ROUTING_DATA__NEEDS_VALIDATION);

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

		eventEClass = createEClass(EVENT);
		createEReference(eventEClass, EVENT__WITH);

		fbEClass = createEClass(FB);

		fbNetworkElementEClass = createEClass(FB_NETWORK_ELEMENT);
		createEReference(fbNetworkElementEClass, FB_NETWORK_ELEMENT__INTERFACE);
		createEReference(fbNetworkElementEClass, FB_NETWORK_ELEMENT__MAPPING);

		subAppEClass = createEClass(SUB_APP);
		createEReference(subAppEClass, SUB_APP__SUB_APP_NETWORK);

		fbTypeEClass = createEClass(FB_TYPE);
		createEReference(fbTypeEClass, FB_TYPE__INTERFACE_LIST);
		createEReference(fbTypeEClass, FB_TYPE__SERVICE);

		identificationEClass = createEClass(IDENTIFICATION);
		createEAttribute(identificationEClass, IDENTIFICATION__APPLICATION_DOMAIN);
		createEAttribute(identificationEClass, IDENTIFICATION__CLASSIFICATION);
		createEAttribute(identificationEClass, IDENTIFICATION__DESCRIPTION);
		createEAttribute(identificationEClass, IDENTIFICATION__FUNCTION);
		createEAttribute(identificationEClass, IDENTIFICATION__STANDARD);
		createEAttribute(identificationEClass, IDENTIFICATION__TYPE);

		inputPrimitiveEClass = createEClass(INPUT_PRIMITIVE);

		interfaceListEClass = createEClass(INTERFACE_LIST);
		createEReference(interfaceListEClass, INTERFACE_LIST__PLUGS);
		createEReference(interfaceListEClass, INTERFACE_LIST__SOCKETS);
		createEReference(interfaceListEClass, INTERFACE_LIST__EVENT_INPUTS);
		createEReference(interfaceListEClass, INTERFACE_LIST__EVENT_OUTPUTS);
		createEReference(interfaceListEClass, INTERFACE_LIST__INPUT_VARS);
		createEReference(interfaceListEClass, INTERFACE_LIST__OUTPUT_VARS);
		createEReference(interfaceListEClass, INTERFACE_LIST__ERROR_MARKER);

		linkEClass = createEClass(LINK);
		createEReference(linkEClass, LINK__SEGMENT);
		createEReference(linkEClass, LINK__DEVICE);

		mappingEClass = createEClass(MAPPING);
		createEReference(mappingEClass, MAPPING__FROM);
		createEReference(mappingEClass, MAPPING__TO);

		otherAlgorithmEClass = createEClass(OTHER_ALGORITHM);
		createEAttribute(otherAlgorithmEClass, OTHER_ALGORITHM__LANGUAGE);

		outputPrimitiveEClass = createEClass(OUTPUT_PRIMITIVE);

		attributeEClass = createEClass(ATTRIBUTE);
		createEReference(attributeEClass, ATTRIBUTE__ATTRIBUTE_DECLARATION);
		createEAttribute(attributeEClass, ATTRIBUTE__VALUE);

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

		segmentEClass = createEClass(SEGMENT);
		createEAttribute(segmentEClass, SEGMENT__WIDTH);
		createEReference(segmentEClass, SEGMENT__VAR_DECLARATIONS);
		createEReference(segmentEClass, SEGMENT__OUT_CONNECTIONS);

		serviceSequenceEClass = createEClass(SERVICE_SEQUENCE);
		createEReference(serviceSequenceEClass, SERVICE_SEQUENCE__SERVICE_TRANSACTION);

		serviceTransactionEClass = createEClass(SERVICE_TRANSACTION);
		createEReference(serviceTransactionEClass, SERVICE_TRANSACTION__INPUT_PRIMITIVE);
		createEReference(serviceTransactionEClass, SERVICE_TRANSACTION__OUTPUT_PRIMITIVE);

		serviceInterfaceFBTypeEClass = createEClass(SERVICE_INTERFACE_FB_TYPE);

		stAlgorithmEClass = createEClass(ST_ALGORITHM);

		fbNetworkEClass = createEClass(FB_NETWORK);
		createEReference(fbNetworkEClass, FB_NETWORK__NETWORK_ELEMENTS);
		createEReference(fbNetworkEClass, FB_NETWORK__DATA_CONNECTIONS);
		createEReference(fbNetworkEClass, FB_NETWORK__EVENT_CONNECTIONS);
		createEReference(fbNetworkEClass, FB_NETWORK__ADAPTER_CONNECTIONS);

		subAppTypeEClass = createEClass(SUB_APP_TYPE);

		automationSystemEClass = createEClass(AUTOMATION_SYSTEM);
		createEReference(automationSystemEClass, AUTOMATION_SYSTEM__APPLICATION);
		createEReference(automationSystemEClass, AUTOMATION_SYSTEM__MAPPING);
		createEReference(automationSystemEClass, AUTOMATION_SYSTEM__PALETTE);
		createEReference(automationSystemEClass, AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION);
		createEAttribute(automationSystemEClass, AUTOMATION_SYSTEM__SYSTEM_FILE);
		createEAttribute(automationSystemEClass, AUTOMATION_SYSTEM__COMMAND_STACK);

		varDeclarationEClass = createEClass(VAR_DECLARATION);
		createEAttribute(varDeclarationEClass, VAR_DECLARATION__ARRAY_SIZE);
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

		libraryElementEClass = createEClass(LIBRARY_ELEMENT);
		createEReference(libraryElementEClass, LIBRARY_ELEMENT__VERSION_INFO);
		createEReference(libraryElementEClass, LIBRARY_ELEMENT__IDENTIFICATION);
		createEReference(libraryElementEClass, LIBRARY_ELEMENT__PALETTE_ENTRY);

		compilableTypeEClass = createEClass(COMPILABLE_TYPE);
		createEReference(compilableTypeEClass, COMPILABLE_TYPE__COMPILER_INFO);

		configurableObjectEClass = createEClass(CONFIGURABLE_OBJECT);
		createEReference(configurableObjectEClass, CONFIGURABLE_OBJECT__ATTRIBUTES);

		compositeFBTypeEClass = createEClass(COMPOSITE_FB_TYPE);
		createEReference(compositeFBTypeEClass, COMPOSITE_FB_TYPE__FB_NETWORK);

		textAlgorithmEClass = createEClass(TEXT_ALGORITHM);
		createEAttribute(textAlgorithmEClass, TEXT_ALGORITHM__TEXT);

		dataConnectionEClass = createEClass(DATA_CONNECTION);

		eventConnectionEClass = createEClass(EVENT_CONNECTION);

		adapterConnectionEClass = createEClass(ADAPTER_CONNECTION);

		serviceInterfaceEClass = createEClass(SERVICE_INTERFACE);

		iInterfaceElementEClass = createEClass(IINTERFACE_ELEMENT);
		createEAttribute(iInterfaceElementEClass, IINTERFACE_ELEMENT__IS_INPUT);
		createEReference(iInterfaceElementEClass, IINTERFACE_ELEMENT__INPUT_CONNECTIONS);
		createEReference(iInterfaceElementEClass, IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS);
		createEReference(iInterfaceElementEClass, IINTERFACE_ELEMENT__TYPE);
		createEAttribute(iInterfaceElementEClass, IINTERFACE_ELEMENT__TYPE_NAME);

		valueEClass = createEClass(VALUE);
		createEAttribute(valueEClass, VALUE__VALUE);

		systemConfigurationEClass = createEClass(SYSTEM_CONFIGURATION);
		createEReference(systemConfigurationEClass, SYSTEM_CONFIGURATION__DEVICES);
		createEReference(systemConfigurationEClass, SYSTEM_CONFIGURATION__SEGMENTS);
		createEReference(systemConfigurationEClass, SYSTEM_CONFIGURATION__LINKS);

		iNamedElementEClass = createEClass(INAMED_ELEMENT);
		createEAttribute(iNamedElementEClass, INAMED_ELEMENT__NAME);
		createEAttribute(iNamedElementEClass, INAMED_ELEMENT__COMMENT);

		resourceTypeFBEClass = createEClass(RESOURCE_TYPE_FB);

		segmentTypeEClass = createEClass(SEGMENT_TYPE);
		createEReference(segmentTypeEClass, SEGMENT_TYPE__VAR_DECLARATION);

		adapterFBTypeEClass = createEClass(ADAPTER_FB_TYPE);
		createEReference(adapterFBTypeEClass, ADAPTER_FB_TYPE__ADAPTER_TYPE);

		adapterEventEClass = createEClass(ADAPTER_EVENT);
		createEReference(adapterEventEClass, ADAPTER_EVENT__ADAPTER_DECLARATION);

		serviceEClass = createEClass(SERVICE);
		createEReference(serviceEClass, SERVICE__RIGHT_INTERFACE);
		createEReference(serviceEClass, SERVICE__LEFT_INTERFACE);
		createEReference(serviceEClass, SERVICE__SERVICE_SEQUENCE);

		typedConfigureableObjectEClass = createEClass(TYPED_CONFIGUREABLE_OBJECT);
		createEReference(typedConfigureableObjectEClass, TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY);

		adapterFBEClass = createEClass(ADAPTER_FB);
		createEReference(adapterFBEClass, ADAPTER_FB__ADAPTER_DECL);

		primitiveEClass = createEClass(PRIMITIVE);
		createEAttribute(primitiveEClass, PRIMITIVE__EVENT);
		createEAttribute(primitiveEClass, PRIMITIVE__PARAMETERS);
		createEReference(primitiveEClass, PRIMITIVE__INTERFACE);

		positionableElementEClass = createEClass(POSITIONABLE_ELEMENT);
		createEReference(positionableElementEClass, POSITIONABLE_ELEMENT__POSITION);

		positionEClass = createEClass(POSITION);
		createEAttribute(positionEClass, POSITION__X);
		createEAttribute(positionEClass, POSITION__Y);

		colorEClass = createEClass(COLOR);
		createEAttribute(colorEClass, COLOR__RED);
		createEAttribute(colorEClass, COLOR__GREEN);
		createEAttribute(colorEClass, COLOR__BLUE);

		colorizableElementEClass = createEClass(COLORIZABLE_ELEMENT);
		createEReference(colorizableElementEClass, COLORIZABLE_ELEMENT__COLOR);

		iVarElementEClass = createEClass(IVAR_ELEMENT);
		createEReference(iVarElementEClass, IVAR_ELEMENT__VAR_DECLARATIONS);

		attributeDeclarationEClass = createEClass(ATTRIBUTE_DECLARATION);
		createEAttribute(attributeDeclarationEClass, ATTRIBUTE_DECLARATION__INITIAL_VALUE);

		typedElementEClass = createEClass(TYPED_ELEMENT);
		createEAttribute(typedElementEClass, TYPED_ELEMENT__TYPE);

		simpleFBTypeEClass = createEClass(SIMPLE_FB_TYPE);
		createEReference(simpleFBTypeEClass, SIMPLE_FB_TYPE__ALGORITHM);

		baseFBTypeEClass = createEClass(BASE_FB_TYPE);
		createEReference(baseFBTypeEClass, BASE_FB_TYPE__INTERNAL_VARS);
		createEReference(baseFBTypeEClass, BASE_FB_TYPE__INTERNAL_FBS);

		structManipulatorEClass = createEClass(STRUCT_MANIPULATOR);
		createEReference(structManipulatorEClass, STRUCT_MANIPULATOR__STRUCT_TYPE);

		demultiplexerEClass = createEClass(DEMULTIPLEXER);

		multiplexerEClass = createEClass(MULTIPLEXER);

		localVariableEClass = createEClass(LOCAL_VARIABLE);
		createEAttribute(localVariableEClass, LOCAL_VARIABLE__ARRAY_START);
		createEAttribute(localVariableEClass, LOCAL_VARIABLE__ARRAY_STOP);

		errorMarkerFBNElementEClass = createEClass(ERROR_MARKER_FBN_ELEMENT);
		createEReference(errorMarkerFBNElementEClass, ERROR_MARKER_FBN_ELEMENT__REPAIRED_ELEMENT);

		errorMarkerInterfaceEClass = createEClass(ERROR_MARKER_INTERFACE);
		createEReference(errorMarkerInterfaceEClass, ERROR_MARKER_INTERFACE__REPAIRED_ENDPOINT);

		cfbInstanceEClass = createEClass(CFB_INSTANCE);
		createEReference(cfbInstanceEClass, CFB_INSTANCE__CFB_NETWORK);

		errorMarkerRefEClass = createEClass(ERROR_MARKER_REF);
		createEAttribute(errorMarkerRefEClass, ERROR_MARKER_REF__FILE_MARKER_ID);
		createEAttribute(errorMarkerRefEClass, ERROR_MARKER_REF__ERROR_MESSAGE);

		// Create enums
		languageEEnum = createEEnum(LANGUAGE);

		// Create data types
		iProjectEDataType = createEDataType(IPROJECT);
		iFileEDataType = createEDataType(IFILE);
		commandStackEDataType = createEDataType(COMMAND_STACK);
		pointEDataType = createEDataType(POINT);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private boolean isInitialized = false;

	/** Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		final PalettePackage thePalettePackage = (PalettePackage) EPackage.Registry.INSTANCE
				.getEPackage(PalettePackage.eNS_URI);
		final DataPackage theDataPackage = (DataPackage) EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);
		final XMLTypePackage theXMLTypePackage = (XMLTypePackage) EPackage.Registry.INSTANCE
				.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		adapterDeclarationEClass.getESuperTypes().add(this.getVarDeclaration());
		adapterTypeEClass.getESuperTypes().add(theDataPackage.getDataType());
		algorithmEClass.getESuperTypes().add(this.getINamedElement());
		applicationEClass.getESuperTypes().add(this.getConfigurableObject());
		basicFBTypeEClass.getESuperTypes().add(this.getBaseFBType());
		connectionEClass.getESuperTypes().add(this.getConfigurableObject());
		deviceEClass.getESuperTypes().add(this.getTypedConfigureableObject());
		deviceEClass.getESuperTypes().add(this.getPositionableElement());
		deviceEClass.getESuperTypes().add(this.getColorizableElement());
		deviceEClass.getESuperTypes().add(this.getIVarElement());
		deviceTypeEClass.getESuperTypes().add(this.getCompilableType());
		ecStateEClass.getESuperTypes().add(this.getINamedElement());
		ecStateEClass.getESuperTypes().add(this.getPositionableElement());
		ecTransitionEClass.getESuperTypes().add(this.getPositionableElement());
		eventEClass.getESuperTypes().add(this.getIInterfaceElement());
		fbEClass.getESuperTypes().add(this.getFBNetworkElement());
		fbNetworkElementEClass.getESuperTypes().add(this.getTypedConfigureableObject());
		fbNetworkElementEClass.getESuperTypes().add(this.getPositionableElement());
		subAppEClass.getESuperTypes().add(this.getFBNetworkElement());
		fbTypeEClass.getESuperTypes().add(this.getCompilableType());
		inputPrimitiveEClass.getESuperTypes().add(this.getPrimitive());
		linkEClass.getESuperTypes().add(this.getConfigurableObject());
		otherAlgorithmEClass.getESuperTypes().add(this.getTextAlgorithm());
		outputPrimitiveEClass.getESuperTypes().add(this.getPrimitive());
		attributeEClass.getESuperTypes().add(this.getINamedElement());
		attributeEClass.getESuperTypes().add(this.getTypedElement());
		resourceEClass.getESuperTypes().add(this.getTypedConfigureableObject());
		resourceEClass.getESuperTypes().add(this.getIVarElement());
		resourceTypeEClass.getESuperTypes().add(this.getCompilableType());
		segmentEClass.getESuperTypes().add(this.getTypedConfigureableObject());
		segmentEClass.getESuperTypes().add(this.getPositionableElement());
		segmentEClass.getESuperTypes().add(this.getColorizableElement());
		serviceSequenceEClass.getESuperTypes().add(this.getINamedElement());
		serviceInterfaceFBTypeEClass.getESuperTypes().add(this.getFBType());
		stAlgorithmEClass.getESuperTypes().add(this.getTextAlgorithm());
		subAppTypeEClass.getESuperTypes().add(this.getCompositeFBType());
		automationSystemEClass.getESuperTypes().add(this.getLibraryElement());
		varDeclarationEClass.getESuperTypes().add(this.getIInterfaceElement());
		libraryElementEClass.getESuperTypes().add(this.getINamedElement());
		compilableTypeEClass.getESuperTypes().add(this.getLibraryElement());
		configurableObjectEClass.getESuperTypes().add(this.getINamedElement());
		compositeFBTypeEClass.getESuperTypes().add(this.getFBType());
		textAlgorithmEClass.getESuperTypes().add(this.getAlgorithm());
		dataConnectionEClass.getESuperTypes().add(this.getConnection());
		eventConnectionEClass.getESuperTypes().add(this.getConnection());
		adapterConnectionEClass.getESuperTypes().add(this.getConnection());
		serviceInterfaceEClass.getESuperTypes().add(this.getINamedElement());
		iInterfaceElementEClass.getESuperTypes().add(this.getConfigurableObject());
		valueEClass.getESuperTypes().add(this.getErrorMarkerRef());
		resourceTypeFBEClass.getESuperTypes().add(this.getFB());
		segmentTypeEClass.getESuperTypes().add(this.getCompilableType());
		adapterFBTypeEClass.getESuperTypes().add(this.getFBType());
		adapterEventEClass.getESuperTypes().add(this.getEvent());
		typedConfigureableObjectEClass.getESuperTypes().add(this.getConfigurableObject());
		adapterFBEClass.getESuperTypes().add(this.getFB());
		attributeDeclarationEClass.getESuperTypes().add(this.getINamedElement());
		attributeDeclarationEClass.getESuperTypes().add(this.getTypedElement());
		simpleFBTypeEClass.getESuperTypes().add(this.getBaseFBType());
		baseFBTypeEClass.getESuperTypes().add(this.getFBType());
		structManipulatorEClass.getESuperTypes().add(this.getFB());
		demultiplexerEClass.getESuperTypes().add(this.getStructManipulator());
		multiplexerEClass.getESuperTypes().add(this.getStructManipulator());
		localVariableEClass.getESuperTypes().add(this.getVarDeclaration());
		errorMarkerFBNElementEClass.getESuperTypes().add(this.getFBNetworkElement());
		errorMarkerFBNElementEClass.getESuperTypes().add(this.getErrorMarkerRef());
		errorMarkerInterfaceEClass.getESuperTypes().add(this.getIInterfaceElement());
		errorMarkerInterfaceEClass.getESuperTypes().add(this.getErrorMarkerRef());
		cfbInstanceEClass.getESuperTypes().add(this.getFB());

		// Initialize classes and features; add operations and parameters
		initEClass(adapterDeclarationEClass, AdapterDeclaration.class, "AdapterDeclaration", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAdapterDeclaration_AdapterFB(), this.getAdapterFB(), this.getAdapterFB_AdapterDecl(),
				"adapterFB", null, 0, 1, AdapterDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAdapterDeclaration_PaletteEntry(), thePalettePackage.getAdapterTypePaletteEntry(), null,
				"paletteEntry", null, 0, 1, AdapterDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(adapterDeclarationEClass, this.getAdapterType(), "getType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(adapterTypeEClass, AdapterType.class, "AdapterType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAdapterType_AdapterFBType(), this.getAdapterFBType(), null, "adapterFBType", null, 0, 1, //$NON-NLS-1$
				AdapterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(adapterTypeEClass, this.getInterfaceList(), "getInterfaceList", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterTypeEClass, this.getAdapterFBType(), "getPlugType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterTypeEClass, this.getAdapterFBType(), "getSocketType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(algorithmEClass, Algorithm.class, "Algorithm", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(applicationEClass, Application.class, "Application", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getApplication_FBNetwork(), this.getFBNetwork(), null, "fBNetwork", null, 1, 1, //$NON-NLS-1$
				Application.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(applicationEClass, this.getAutomationSystem(), "getAutomationSystem", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(basicFBTypeEClass, BasicFBType.class, "BasicFBType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBasicFBType_ECC(), this.getECC(), this.getECC_BasicFBType(), "eCC", null, 0, 1, //$NON-NLS-1$
				BasicFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBasicFBType_Algorithm(), this.getAlgorithm(), null, "algorithm", null, 0, -1, //$NON-NLS-1$
				BasicFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(basicFBTypeEClass, this.getAlgorithm(), "getAlgorithmNamed", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(compilerInfoEClass, CompilerInfo.class, "CompilerInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompilerInfo_Compiler(), this.getCompiler(), null, "compiler", null, 0, -1, //$NON-NLS-1$
				CompilerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompilerInfo_Classdef(), theXMLTypePackage.getString(), "classdef", "", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				CompilerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompilerInfo_Header(), theXMLTypePackage.getString(), "header", "", 0, 1, CompilerInfo.class, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compilerEClass, org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, "Compiler", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompiler_Language(), this.getLanguage(), "language", "C", 1, 1, //$NON-NLS-1$ //$NON-NLS-2$
				org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompiler_Product(), theXMLTypePackage.getString(), "product", "", 1, 1, //$NON-NLS-1$ //$NON-NLS-2$
				org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompiler_Vendor(), theXMLTypePackage.getString(), "vendor", "", 1, 1, //$NON-NLS-1$ //$NON-NLS-2$
				org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompiler_Version(), theXMLTypePackage.getString(), "version", "", 1, 1, //$NON-NLS-1$ //$NON-NLS-2$
				org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(connectionEClass, Connection.class, "Connection", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConnection_ResTypeConnection(), theXMLTypePackage.getBoolean(), "resTypeConnection", null, 0, //$NON-NLS-1$
				1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnection_BrokenConnection(), theXMLTypePackage.getBoolean(), "brokenConnection", null, 0, 1, //$NON-NLS-1$
				Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getConnection_Source(), this.getIInterfaceElement(),
				this.getIInterfaceElement_OutputConnections(), "source", null, 0, 1, Connection.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getConnection_Destination(), this.getIInterfaceElement(),
				this.getIInterfaceElement_InputConnections(), "destination", null, 0, 1, Connection.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnection_RoutingData(), this.getConnectionRoutingData(), null, "routingData", null, 1, 1, //$NON-NLS-1$
				Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(connectionEClass, this.getFBNetworkElement(), "getSourceElement", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(connectionEClass, this.getFBNetworkElement(), "getDestinationElement", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(connectionEClass, ecorePackage.getEBoolean(), "isResourceConnection", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(connectionEClass, this.getFBNetwork(), "getFBNetwork", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(connectionEClass, null, "checkIfConnectionBroken", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(connectionEClass, null, "updateRoutingData", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getInt(), "dx1", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getInt(), "dy", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getInt(), "dx2", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(connectionRoutingDataEClass, ConnectionRoutingData.class, "ConnectionRoutingData", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConnectionRoutingData_Dx1(), theXMLTypePackage.getInt(), "dx1", "0", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				ConnectionRoutingData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectionRoutingData_Dx2(), theXMLTypePackage.getInt(), "dx2", "0", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				ConnectionRoutingData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectionRoutingData_Dy(), theXMLTypePackage.getInt(), "dy", "0", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				ConnectionRoutingData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deviceEClass, Device.class, "Device", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDevice_Resource(), this.getResource(), this.getResource_Device(), "resource", null, 0, -1, //$NON-NLS-1$
				Device.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDevice_Profile(), ecorePackage.getEString(), "profile", null, 0, 1, Device.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDevice_InConnections(), this.getLink(), this.getLink_Device(), "inConnections", null, 0, -1, //$NON-NLS-1$
				Device.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(deviceEClass, this.getAutomationSystem(), "getAutomationSystem", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(deviceEClass, this.getSystemConfiguration(), "getSystemConfiguration", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(deviceEClass, this.getDeviceType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(deviceEClass, this.getResource(), "getResourceNamed", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(deviceTypeEClass, DeviceType.class, "DeviceType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDeviceType_VarDeclaration(), this.getVarDeclaration(), null, "varDeclaration", null, 0, -1, //$NON-NLS-1$
				DeviceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeviceType_ResourceTypeName(), this.getResourceTypeName(), null, "resourceTypeName", null, 0, //$NON-NLS-1$
				-1, DeviceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeviceType_Resource(), this.getResource(), null, "resource", null, 0, -1, DeviceType.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeviceType_FBNetwork(), this.getFBNetwork(), null, "fBNetwork", null, 0, 1, DeviceType.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeviceType_Profile(), ecorePackage.getEString(), "profile", null, 0, 1, DeviceType.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeviceType_AttributeDeclarations(), this.getAttributeDeclaration(), null,
				"attributeDeclarations", null, 0, -1, DeviceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ecActionEClass, ECAction.class, "ECAction", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getECAction_Algorithm(), this.getAlgorithm(), null, "algorithm", null, 0, 1, ECAction.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECAction_Output(), this.getEvent(), null, "output", null, 0, 1, ECAction.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getECAction_ECState(), this.getECState(), this.getECState_ECAction(), "eCState", null, 1, 1, //$NON-NLS-1$
				ECAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eccEClass, org.eclipse.fordiac.ide.model.libraryElement.ECC.class, "ECC", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getECC_ECState(), this.getECState(), this.getECState_ECC(), "eCState", null, 1, -1, //$NON-NLS-1$
				org.eclipse.fordiac.ide.model.libraryElement.ECC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECC_ECTransition(), this.getECTransition(), this.getECTransition_ECC(), "eCTransition", null, //$NON-NLS-1$
				1, -1, org.eclipse.fordiac.ide.model.libraryElement.ECC.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECC_Start(), this.getECState(), null, "start", null, 0, 1, //$NON-NLS-1$
				org.eclipse.fordiac.ide.model.libraryElement.ECC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECC_BasicFBType(), this.getBasicFBType(), this.getBasicFBType_ECC(), "basicFBType", null, 1, //$NON-NLS-1$
				1, org.eclipse.fordiac.ide.model.libraryElement.ECC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ecStateEClass, ECState.class, "ECState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getECState_ECAction(), this.getECAction(), this.getECAction_ECState(), "eCAction", null, 0, -1, //$NON-NLS-1$
				ECState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECState_OutTransitions(), this.getECTransition(), this.getECTransition_Source(),
				"outTransitions", null, 0, -1, ECState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, //$NON-NLS-1$
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECState_InTransitions(), this.getECTransition(), this.getECTransition_Destination(),
				"inTransitions", null, 0, -1, ECState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, //$NON-NLS-1$
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECState_ECC(), this.getECC(), this.getECC_ECState(), "eCC", null, 0, 1, ECState.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(ecStateEClass, theXMLTypePackage.getBoolean(), "isStartState", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(ecTransitionEClass, ECTransition.class, "ECTransition", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getECTransition_Comment(), theXMLTypePackage.getString(), "comment", "", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getECTransition_ConditionExpression(), theXMLTypePackage.getString(), "conditionExpression", "1", //$NON-NLS-1$ //$NON-NLS-2$
				1, 1, ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getECTransition_Source(), this.getECState(), this.getECState_OutTransitions(), "source", null, 1, //$NON-NLS-1$
				1, ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECTransition_Destination(), this.getECState(), this.getECState_InTransitions(), "destination", //$NON-NLS-1$
				null, 1, 1, ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECTransition_ConditionEvent(), this.getEvent(), null, "conditionEvent", null, 0, 1, //$NON-NLS-1$
				ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECTransition_ECC(), this.getECC(), this.getECC_ECTransition(), "eCC", null, 1, 1, //$NON-NLS-1$
				ECTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(ecTransitionEClass, theXMLTypePackage.getString(), "getConditionText", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(ecTransitionEClass, ecorePackage.getEInt(), "getPriority", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(eventEClass, Event.class, "Event", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEvent_With(), this.getWith(), null, "with", null, 0, -1, Event.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(fbEClass, org.eclipse.fordiac.ide.model.libraryElement.FB.class, "FB", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		addEOperation(fbEClass, ecorePackage.getEBoolean(), "isResourceFB", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbEClass, ecorePackage.getEBoolean(), "isResourceTypeFB", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbEClass, this.getFBType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(fbNetworkElementEClass, FBNetworkElement.class, "FBNetworkElement", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFBNetworkElement_Interface(), this.getInterfaceList(), null, "interface", null, 0, 1, //$NON-NLS-1$
				FBNetworkElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFBNetworkElement_Mapping(), this.getMapping(), null, "mapping", null, 0, 1, //$NON-NLS-1$
				FBNetworkElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(fbNetworkElementEClass, this.getResource(), "getResource", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkElementEClass, this.getIInterfaceElement(), "getInterfaceElement", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, this.getFBNetworkElement(), "getOpposite", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, this.getFBNetwork(), "getFbNetwork", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, null, "checkConnections", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, ecorePackage.getEBoolean(), "isMapped", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, this.getFBType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkElementEClass, theXMLTypePackage.getBoolean(), "isNestedInSubApp", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(fbNetworkElementEClass, this.getFBNetworkElement(), "getOuterFBNetworkElement", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(fbNetworkElementEClass, theXMLTypePackage.getBoolean(), "isContainedInTypedInstance", 0, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);

		initEClass(subAppEClass, SubApp.class, "SubApp", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSubApp_SubAppNetwork(), this.getFBNetwork(), null, "subAppNetwork", null, 0, 1, SubApp.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		addEOperation(subAppEClass, this.getSubAppType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(subAppEClass, theXMLTypePackage.getBoolean(), "isUnfolded", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(subAppEClass, theXMLTypePackage.getBoolean(), "isTyped", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(subAppEClass, this.getFBNetwork(), "loadSubAppNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(fbTypeEClass, FBType.class, "FBType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getFBType_InterfaceList(), this.getInterfaceList(), null, "interfaceList", null, 1, 1, //$NON-NLS-1$
				FBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFBType_Service(), this.getService(), null, "service", null, 1, 1, FBType.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(identificationEClass, Identification.class, "Identification", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdentification_ApplicationDomain(), theXMLTypePackage.getString(), "applicationDomain", null, //$NON-NLS-1$
				0, 1, Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentification_Classification(), theXMLTypePackage.getString(), "classification", "", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentification_Description(), theXMLTypePackage.getString(), "description", "", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentification_Function(), theXMLTypePackage.getString(), "function", "", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentification_Standard(), theXMLTypePackage.getString(), "standard", "", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				Identification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentification_Type(), theXMLTypePackage.getString(), "type", "", 0, 1, Identification.class, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inputPrimitiveEClass, InputPrimitive.class, "InputPrimitive", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(interfaceListEClass, InterfaceList.class, "InterfaceList", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInterfaceList_Plugs(), this.getAdapterDeclaration(), null, "plugs", null, 0, -1, //$NON-NLS-1$
				InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInterfaceList_Sockets(), this.getAdapterDeclaration(), null, "sockets", null, 0, -1, //$NON-NLS-1$
				InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInterfaceList_EventInputs(), this.getEvent(), null, "eventInputs", null, 0, -1, //$NON-NLS-1$
				InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInterfaceList_EventOutputs(), this.getEvent(), null, "eventOutputs", null, 0, -1, //$NON-NLS-1$
				InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInterfaceList_InputVars(), this.getVarDeclaration(), null, "inputVars", null, 0, -1, //$NON-NLS-1$
				InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInterfaceList_OutputVars(), this.getVarDeclaration(), null, "outputVars", null, 0, -1, //$NON-NLS-1$
				InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInterfaceList_ErrorMarker(), this.getIInterfaceElement(), null, "errorMarker", null, 0, -1, //$NON-NLS-1$
				InterfaceList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(interfaceListEClass, null, "getAllInterfaceElements", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		final EGenericType g1 = createEGenericType(ecorePackage.getEEList());
		final EGenericType g2 = createEGenericType(this.getIInterfaceElement());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = addEOperation(interfaceListEClass, this.getEvent(), "getEvent", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(interfaceListEClass, this.getVarDeclaration(), "getVariable", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(interfaceListEClass, this.getIInterfaceElement(), "getInterfaceElement", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(interfaceListEClass, this.getFBNetworkElement(), "getFBNetworkElement", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		op = addEOperation(interfaceListEClass, this.getAdapterDeclaration(), "getAdapter", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(interfaceListEClass, this.getInterfaceList(), "copy", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(linkEClass, Link.class, "Link", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getLink_Segment(), this.getSegment(), this.getSegment_OutConnections(), "segment", null, 0, 1, //$NON-NLS-1$
				Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLink_Device(), this.getDevice(), this.getDevice_InConnections(), "device", null, 0, 1, //$NON-NLS-1$
				Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingEClass, Mapping.class, "Mapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getMapping_From(), this.getFBNetworkElement(), null, "from", null, 0, 1, Mapping.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMapping_To(), this.getFBNetworkElement(), null, "to", null, 0, 1, Mapping.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(mappingEClass, this.getAutomationSystem(), "getAutomationSystem", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(otherAlgorithmEClass, OtherAlgorithm.class, "OtherAlgorithm", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOtherAlgorithm_Language(), theXMLTypePackage.getString(), "language", null, 1, 1, //$NON-NLS-1$
				OtherAlgorithm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(outputPrimitiveEClass, OutputPrimitive.class, "OutputPrimitive", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(attributeEClass, Attribute.class, "Attribute", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttribute_AttributeDeclaration(), this.getAttributeDeclaration(), null,
				"attributeDeclaration", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttribute_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, Attribute.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceEClass, Resource.class, "Resource", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResource_FBNetwork(), this.getFBNetwork(), null, "fBNetwork", null, 0, 1, Resource.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResource_X(), theXMLTypePackage.getString(), "x", null, 0, 1, Resource.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResource_Y(), theXMLTypePackage.getString(), "y", null, 0, 1, Resource.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResource_Device(), this.getDevice(), this.getDevice_Resource(), "device", null, 0, 1, //$NON-NLS-1$
				Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResource_DeviceTypeResource(), theXMLTypePackage.getBoolean(), "deviceTypeResource", null, 0, //$NON-NLS-1$
				1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		addEOperation(resourceEClass, this.getAutomationSystem(), "getAutomationSystem", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(resourceEClass, this.getResourceType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(resourceTypeNameEClass, ResourceTypeName.class, "ResourceTypeName", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getResourceTypeName_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, //$NON-NLS-1$
				ResourceTypeName.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(resourceTypeEClass, ResourceType.class, "ResourceType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResourceType_VarDeclaration(), this.getVarDeclaration(), null, "varDeclaration", null, 0, -1, //$NON-NLS-1$
				ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceType_FBNetwork(), this.getFBNetwork(), null, "fBNetwork", null, 1, 1, //$NON-NLS-1$
				ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceType_SupportedFBTypes(), this.getFBType(), null, "supportedFBTypes", null, 0, 1, //$NON-NLS-1$
				ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(segmentEClass, Segment.class, "Segment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSegment_Width(), theXMLTypePackage.getInt(), "width", "200", 0, 1, Segment.class, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSegment_VarDeclarations(), this.getVarDeclaration(), null, "varDeclarations", null, 0, -1, //$NON-NLS-1$
				Segment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSegment_OutConnections(), this.getLink(), this.getLink_Segment(), "outConnections", null, 0, //$NON-NLS-1$
				-1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(segmentEClass, this.getSegmentType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(serviceSequenceEClass, ServiceSequence.class, "ServiceSequence", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getServiceSequence_ServiceTransaction(), this.getServiceTransaction(), null,
				"serviceTransaction", null, 0, -1, ServiceSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(serviceTransactionEClass, ServiceTransaction.class, "ServiceTransaction", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getServiceTransaction_InputPrimitive(), this.getInputPrimitive(), null, "inputPrimitive", null, //$NON-NLS-1$
				0, 1, ServiceTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getServiceTransaction_OutputPrimitive(), this.getOutputPrimitive(), null, "outputPrimitive", //$NON-NLS-1$
				null, 0, -1, ServiceTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(serviceInterfaceFBTypeEClass, ServiceInterfaceFBType.class, "ServiceInterfaceFBType", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stAlgorithmEClass, STAlgorithm.class, "STAlgorithm", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(fbNetworkEClass, FBNetwork.class, "FBNetwork", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFBNetwork_NetworkElements(), this.getFBNetworkElement(), null, "networkElements", null, 0, -1, //$NON-NLS-1$
				FBNetwork.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFBNetwork_DataConnections(), this.getDataConnection(), null, "dataConnections", null, 0, -1, //$NON-NLS-1$
				FBNetwork.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFBNetwork_EventConnections(), this.getEventConnection(), null, "eventConnections", null, 0, //$NON-NLS-1$
				-1, FBNetwork.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFBNetwork_AdapterConnections(), this.getAdapterConnection(), null, "adapterConnections", null, //$NON-NLS-1$
				0, -1, FBNetwork.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(fbNetworkEClass, null, "addConnection", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getConnection(), "connection", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, null, "removeConnection", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getConnection(), "connection", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkEClass, theXMLTypePackage.getBoolean(), "isApplicationNetwork", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(fbNetworkEClass, theXMLTypePackage.getBoolean(), "isSubApplicationNetwork", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(fbNetworkEClass, theXMLTypePackage.getBoolean(), "isResourceNetwork", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(fbNetworkEClass, theXMLTypePackage.getBoolean(), "isCFBTypeNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkEClass, this.getAutomationSystem(), "getAutomationSystem", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbNetworkEClass, this.getApplication(), "getApplication", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, this.getFB(), "getFBNamed", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, this.getSubApp(), "getSubAppNamed", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(fbNetworkEClass, this.getFBNetworkElement(), "getElementNamed", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(subAppTypeEClass, SubAppType.class, "SubAppType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(automationSystemEClass, AutomationSystem.class, "AutomationSystem", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAutomationSystem_Application(), this.getApplication(), null, "application", null, 0, -1, //$NON-NLS-1$
				AutomationSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAutomationSystem_Mapping(), this.getMapping(), null, "mapping", null, 0, -1, //$NON-NLS-1$
				AutomationSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAutomationSystem_Palette(), thePalettePackage.getPalette(), null, "palette", null, 1, 1, //$NON-NLS-1$
				AutomationSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAutomationSystem_SystemConfiguration(), this.getSystemConfiguration(), null,
				"systemConfiguration", null, 1, 1, AutomationSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAutomationSystem_SystemFile(), this.getIFile(), "systemFile", null, 0, 1, //$NON-NLS-1$
				AutomationSystem.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getAutomationSystem_CommandStack(), this.getCommandStack(), "commandStack", null, 0, 1, //$NON-NLS-1$
				AutomationSystem.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		op = addEOperation(automationSystemEClass, this.getDevice(), "getDeviceNamed", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(automationSystemEClass, this.getApplication(), "getApplicationNamed", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(varDeclarationEClass, VarDeclaration.class, "VarDeclaration", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVarDeclaration_ArraySize(), theXMLTypePackage.getInt(), "arraySize", null, 0, 1, //$NON-NLS-1$
				VarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getVarDeclaration_Withs(), this.getWith(), this.getWith_Variables(), "withs", null, 0, -1, //$NON-NLS-1$
				VarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVarDeclaration_Value(), this.getValue(), null, "value", null, 0, 1, VarDeclaration.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		addEOperation(varDeclarationEClass, theXMLTypePackage.getBoolean(), "isArray", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(versionInfoEClass, VersionInfo.class, "VersionInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersionInfo_Author(), theXMLTypePackage.getString(), "author", "", 1, 1, VersionInfo.class, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionInfo_Date(), theXMLTypePackage.getString(), "date", "YYYY-MM-DD", 1, 1, //$NON-NLS-1$ //$NON-NLS-2$
				VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionInfo_Organization(), theXMLTypePackage.getString(), "organization", "", 1, 1, //$NON-NLS-1$ //$NON-NLS-2$
				VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionInfo_Remarks(), theXMLTypePackage.getString(), "remarks", "", 0, 1, VersionInfo.class, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionInfo_Version(), theXMLTypePackage.getString(), "version", "", 1, 1, VersionInfo.class, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(withEClass, With.class, "With", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getWith_Variables(), this.getVarDeclaration(), this.getVarDeclaration_Withs(), "variables", null, //$NON-NLS-1$
				1, 1, With.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(libraryElementEClass, LibraryElement.class, "LibraryElement", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLibraryElement_VersionInfo(), this.getVersionInfo(), null, "versionInfo", null, 1, -1, //$NON-NLS-1$
				LibraryElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLibraryElement_Identification(), this.getIdentification(), null, "identification", null, 0, 1, //$NON-NLS-1$
				LibraryElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLibraryElement_PaletteEntry(), thePalettePackage.getPaletteEntry(),
				thePalettePackage.getPaletteEntry_Type(), "paletteEntry", null, 0, 1, LibraryElement.class, //$NON-NLS-1$
				IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(libraryElementEClass, thePalettePackage.getTypeLibrary(), "getTypeLibrary", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(compilableTypeEClass, CompilableType.class, "CompilableType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompilableType_CompilerInfo(), this.getCompilerInfo(), null, "compilerInfo", null, 0, 1, //$NON-NLS-1$
				CompilableType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configurableObjectEClass, ConfigurableObject.class, "ConfigurableObject", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConfigurableObject_Attributes(), this.getAttribute(), null, "attributes", null, 0, -1, //$NON-NLS-1$
				ConfigurableObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(configurableObjectEClass, null, "setAttribute", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "type", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "value", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "comment", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(configurableObjectEClass, this.getAttribute(), "getAttribute", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(configurableObjectEClass, theXMLTypePackage.getString(), "getAttributeValue", 0, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theXMLTypePackage.getString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(configurableObjectEClass, theXMLTypePackage.getBoolean(), "deleteAttribute", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, theXMLTypePackage.getString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(compositeFBTypeEClass, CompositeFBType.class, "CompositeFBType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositeFBType_FBNetwork(), this.getFBNetwork(), null, "fBNetwork", null, 0, 1, //$NON-NLS-1$
				CompositeFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(textAlgorithmEClass, TextAlgorithm.class, "TextAlgorithm", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTextAlgorithm_Text(), ecorePackage.getEString(), "text", null, 0, 1, TextAlgorithm.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataConnectionEClass, DataConnection.class, "DataConnection", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		addEOperation(dataConnectionEClass, this.getVarDeclaration(), "getDataSource", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(dataConnectionEClass, this.getVarDeclaration(), "getDataDestination", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(eventConnectionEClass, EventConnection.class, "EventConnection", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		addEOperation(eventConnectionEClass, this.getEvent(), "getEventSource", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(eventConnectionEClass, this.getEvent(), "getEventDestination", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(adapterConnectionEClass, AdapterConnection.class, "AdapterConnection", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		addEOperation(adapterConnectionEClass, this.getAdapterDeclaration(), "getAdapterSource", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(adapterConnectionEClass, this.getAdapterDeclaration(), "getAdapterDestination", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(serviceInterfaceEClass, ServiceInterface.class, "ServiceInterface", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(iInterfaceElementEClass, IInterfaceElement.class, "IInterfaceElement", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIInterfaceElement_IsInput(), theXMLTypePackage.getBoolean(), "isInput", null, 0, 1, //$NON-NLS-1$
				IInterfaceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getIInterfaceElement_InputConnections(), this.getConnection(), this.getConnection_Destination(),
				"inputConnections", null, 0, -1, IInterfaceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIInterfaceElement_OutputConnections(), this.getConnection(), this.getConnection_Source(),
				"outputConnections", null, 0, -1, IInterfaceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIInterfaceElement_Type(), theDataPackage.getDataType(), null, "type", null, 1, 1, //$NON-NLS-1$
				IInterfaceElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIInterfaceElement_TypeName(), ecorePackage.getEString(), "typeName", null, 0, 1, //$NON-NLS-1$
				IInterfaceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		addEOperation(iInterfaceElementEClass, this.getFBNetworkElement(), "getFBNetworkElement", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(valueEClass, Value.class, "Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getValue_Value(), ecorePackage.getEString(), "value", "", 0, 1, Value.class, !IS_TRANSIENT, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(valueEClass, this.getVarDeclaration(), "getVarDeclaration", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(systemConfigurationEClass, SystemConfiguration.class, "SystemConfiguration", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSystemConfiguration_Devices(), this.getDevice(), null, "devices", null, 1, -1, //$NON-NLS-1$
				SystemConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSystemConfiguration_Segments(), this.getSegment(), null, "segments", null, 0, -1, //$NON-NLS-1$
				SystemConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSystemConfiguration_Links(), this.getLink(), null, "links", null, 0, -1, //$NON-NLS-1$
				SystemConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(systemConfigurationEClass, this.getAutomationSystem(), "getAutomationSystem", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		op = addEOperation(systemConfigurationEClass, this.getSegment(), "getSegmentNamed", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(systemConfigurationEClass, this.getDevice(), "getDeviceNamed", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(iNamedElementEClass, INamedElement.class, "INamedElement", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getINamedElement_Name(), theXMLTypePackage.getString(), "name", "", 1, 1, INamedElement.class, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getINamedElement_Comment(), theXMLTypePackage.getString(), "comment", "", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				INamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(resourceTypeFBEClass, ResourceTypeFB.class, "ResourceTypeFB", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		addEOperation(resourceTypeFBEClass, ecorePackage.getEBoolean(), "isResourceTypeFB", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(segmentTypeEClass, SegmentType.class, "SegmentType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSegmentType_VarDeclaration(), this.getVarDeclaration(), null, "varDeclaration", null, 0, -1, //$NON-NLS-1$
				SegmentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(adapterFBTypeEClass, AdapterFBType.class, "AdapterFBType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAdapterFBType_AdapterType(), this.getAdapterType(), null, "adapterType", null, 0, 1, //$NON-NLS-1$
				AdapterFBType.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(adapterEventEClass, AdapterEvent.class, "AdapterEvent", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAdapterEvent_AdapterDeclaration(), this.getAdapterDeclaration(), null, "adapterDeclaration", //$NON-NLS-1$
				null, 1, 1, AdapterEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(serviceEClass, Service.class, "Service", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getService_RightInterface(), this.getServiceInterface(), null, "rightInterface", null, 0, 1, //$NON-NLS-1$
				Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getService_LeftInterface(), this.getServiceInterface(), null, "leftInterface", null, 0, 1, //$NON-NLS-1$
				Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getService_ServiceSequence(), this.getServiceSequence(), null, "serviceSequence", null, 0, -1, //$NON-NLS-1$
				Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typedConfigureableObjectEClass, TypedConfigureableObject.class, "TypedConfigureableObject", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypedConfigureableObject_PaletteEntry(), thePalettePackage.getPaletteEntry(), null,
				"paletteEntry", null, 1, 1, TypedConfigureableObject.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(typedConfigureableObjectEClass, ecorePackage.getEString(), "getTypeName", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(typedConfigureableObjectEClass, this.getLibraryElement(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(typedConfigureableObjectEClass, thePalettePackage.getTypeLibrary(), "getTypeLibrary", 1, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);

		initEClass(adapterFBEClass, AdapterFB.class, "AdapterFB", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAdapterFB_AdapterDecl(), this.getAdapterDeclaration(), this.getAdapterDeclaration_AdapterFB(),
				"adapterDecl", null, 1, 1, AdapterFB.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, //$NON-NLS-1$
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(adapterFBEClass, theXMLTypePackage.getBoolean(), "isSocket", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterFBEClass, this.getFBType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterFBEClass, theXMLTypePackage.getBoolean(), "isPlug", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(primitiveEClass, Primitive.class, "Primitive", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPrimitive_Event(), theXMLTypePackage.getString(), "event", null, 1, 1, Primitive.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrimitive_Parameters(), theXMLTypePackage.getString(), "parameters", null, 0, 1, //$NON-NLS-1$
				Primitive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getPrimitive_Interface(), this.getServiceInterface(), null, "interface", null, 1, 1, //$NON-NLS-1$
				Primitive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(positionableElementEClass, PositionableElement.class, "PositionableElement", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPositionableElement_Position(), this.getPosition(), null, "position", null, 1, 1, //$NON-NLS-1$
				PositionableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(positionableElementEClass, null, "updatePosition", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getInt(), "x", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theXMLTypePackage.getInt(), "y", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(positionableElementEClass, null, "updatePosition", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getPoint(), "newPos", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(positionEClass, Position.class, "Position", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPosition_X(), theXMLTypePackage.getInt(), "x", "0", 0, 1, Position.class, !IS_TRANSIENT, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPosition_Y(), theXMLTypePackage.getInt(), "y", "0", 0, 1, Position.class, !IS_TRANSIENT, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(positionEClass, this.getPoint(), "asPoint", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(colorEClass, Color.class, "Color", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getColor_Red(), theXMLTypePackage.getInt(), "red", "1", 0, 1, Color.class, !IS_TRANSIENT, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColor_Green(), theXMLTypePackage.getInt(), "green", "34", 0, 1, Color.class, !IS_TRANSIENT, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColor_Blue(), theXMLTypePackage.getInt(), "blue", "105", 0, 1, Color.class, !IS_TRANSIENT, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(colorizableElementEClass, ColorizableElement.class, "ColorizableElement", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getColorizableElement_Color(), this.getColor(), null, "color", null, 1, 1, //$NON-NLS-1$
				ColorizableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iVarElementEClass, IVarElement.class, "IVarElement", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIVarElement_VarDeclarations(), this.getVarDeclaration(), null, "varDeclarations", null, 0, -1, //$NON-NLS-1$
				IVarElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeDeclarationEClass, AttributeDeclaration.class, "AttributeDeclaration", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeDeclaration_InitialValue(), theXMLTypePackage.getString(), "initialValue", null, 0, //$NON-NLS-1$
				1, AttributeDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typedElementEClass, TypedElement.class, "TypedElement", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypedElement_Type(), theDataPackage.getBaseType1(), "type", "STRING", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				TypedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(simpleFBTypeEClass, SimpleFBType.class, "SimpleFBType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimpleFBType_Algorithm(), this.getAlgorithm(), null, "algorithm", null, 1, 1, //$NON-NLS-1$
				SimpleFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(baseFBTypeEClass, BaseFBType.class, "BaseFBType", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBaseFBType_InternalVars(), this.getVarDeclaration(), null, "internalVars", null, 0, -1, //$NON-NLS-1$
				BaseFBType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBaseFBType_InternalFbs(), this.getFB(), null, "internalFbs", null, 0, -1, BaseFBType.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(structManipulatorEClass, StructManipulator.class, "StructManipulator", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStructManipulator_StructType(), theDataPackage.getStructuredType(), null, "structType", null, //$NON-NLS-1$
				1, 1, StructManipulator.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(structManipulatorEClass, null, "setStructTypeElementsAtInterface", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, theDataPackage.getStructuredType(), "newStruct", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(demultiplexerEClass, Demultiplexer.class, "Demultiplexer", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(multiplexerEClass, Multiplexer.class, "Multiplexer", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(localVariableEClass, LocalVariable.class, "LocalVariable", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocalVariable_ArrayStart(), theXMLTypePackage.getInt(), "arrayStart", null, 0, 1, //$NON-NLS-1$
				LocalVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalVariable_ArrayStop(), theXMLTypePackage.getInt(), "arrayStop", null, 0, 1, //$NON-NLS-1$
				LocalVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		addEOperation(localVariableEClass, theXMLTypePackage.getInt(), "getArraySize", 0, 1, !IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(errorMarkerFBNElementEClass, ErrorMarkerFBNElement.class, "ErrorMarkerFBNElement", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getErrorMarkerFBNElement_RepairedElement(), this.getFBNetworkElement(), null, "repairedElement", //$NON-NLS-1$
				null, 0, 1, ErrorMarkerFBNElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorMarkerInterfaceEClass, ErrorMarkerInterface.class, "ErrorMarkerInterface", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getErrorMarkerInterface_RepairedEndpoint(), this.getIInterfaceElement(), null,
				"repairedEndpoint", null, 0, 1, ErrorMarkerInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cfbInstanceEClass, CFBInstance.class, "CFBInstance", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCFBInstance_CfbNetwork(), this.getFBNetwork(), null, "cfbNetwork", null, 0, 1, //$NON-NLS-1$
				CFBInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(cfbInstanceEClass, this.getFBNetwork(), "loadCFBNetwork", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(cfbInstanceEClass, this.getCompositeFBType(), "getType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(errorMarkerRefEClass, ErrorMarkerRef.class, "ErrorMarkerRef", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorMarkerRef_FileMarkerId(), ecorePackage.getELong(), "fileMarkerId", null, 0, 1, //$NON-NLS-1$
				ErrorMarkerRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorMarkerRef_ErrorMessage(), theXMLTypePackage.getString(), "errorMessage", null, 0, 1, //$NON-NLS-1$
				ErrorMarkerRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(languageEEnum, Language.class, "Language"); //$NON-NLS-1$
		addEEnumLiteral(languageEEnum, Language.C);
		addEEnumLiteral(languageEEnum, Language.OTHER);
		addEEnumLiteral(languageEEnum, Language.JAVA);
		addEEnumLiteral(languageEEnum, Language.CPP);

		// Initialize data types
		initEDataType(iProjectEDataType, IProject.class, "IProject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(iFileEDataType, IFile.class, "IFile", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(commandStackEDataType, CommandStack.class, "CommandStack", IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pointEDataType, Point.class, "Point", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/** Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected void createExtendedMetaDataAnnotations() {
		final String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$
		addAnnotation(getAdapterType_AdapterFBType(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Service", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getApplication_FBNetwork(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "FBNetwork", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getBasicFBType_ECC(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "ECC", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getBasicFBType_Algorithm(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Algorithm", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCompilerInfo_Compiler(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Compiler", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCompilerInfo_Classdef(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "classdef" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCompilerInfo_Header(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "header" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCompiler_Language(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Language" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCompiler_Product(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Product" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCompiler_Vendor(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Vendor" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCompiler_Version(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Version" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getConnectionRoutingData_Dx1(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "dx1" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getConnectionRoutingData_Dx2(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "dx2" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getConnectionRoutingData_Dy(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "dy" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getDevice_Resource(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Resource", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getDeviceType_VarDeclaration(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "VarDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getDeviceType_ResourceTypeName(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "ResourceTypeName", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getDeviceType_Resource(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Resource", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getDeviceType_FBNetwork(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "FBNetwork", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getECC_ECState(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "ECState", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getECC_ECTransition(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "ECTransition", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getECState_ECAction(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "ECAction", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getECTransition_Comment(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Comment" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getECTransition_ConditionExpression(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Condition" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getEvent_With(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "With", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getFBType_InterfaceList(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "InterfaceList", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getFBType_Service(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "ServiceSequence", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getIdentification_ApplicationDomain(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "ApplicationDomain" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getIdentification_Classification(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Classification" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getIdentification_Description(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Description" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getIdentification_Function(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Function" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getIdentification_Standard(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Standard" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getIdentification_Type(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Type" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getInterfaceList_Plugs(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "AdapterDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getInterfaceList_Sockets(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "AdapterDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getInterfaceList_EventInputs(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "SubAppEventInputs", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getInterfaceList_EventOutputs(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "SubAppEventOutputs", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getInterfaceList_InputVars(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "InputVars", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getInterfaceList_OutputVars(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "OutputVars", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getInterfaceList_ErrorMarker(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "ErrorMarker", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getOtherAlgorithm_Language(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Language" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getAttribute_Value(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Value" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getResource_FBNetwork(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "FBNetwork", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getResource_X(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "x" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getResource_Y(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "y" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getResourceTypeName_Name(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Name" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getSegment_Width(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "dx1" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getServiceSequence_ServiceTransaction(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "ServiceTransaction", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getServiceTransaction_InputPrimitive(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "InputPrimitive", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getServiceTransaction_OutputPrimitive(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "OutputPrimitive", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getFBNetwork_NetworkElements(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "FB", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getFBNetwork_DataConnections(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "DataConnections", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getFBNetwork_AdapterConnections(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "AdapterConnections", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getAutomationSystem_Application(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Application", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getAutomationSystem_Mapping(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Mapping", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getVarDeclaration_ArraySize(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "arraySize" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getVersionInfo_Author(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Author" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getVersionInfo_Date(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Date" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getVersionInfo_Organization(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Organization" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getVersionInfo_Remarks(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Remarks" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getVersionInfo_Version(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Version" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getCompositeFBType_FBNetwork(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "FBNetwork", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getSystemConfiguration_Devices(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Device", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getSystemConfiguration_Segments(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Segment", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getSystemConfiguration_Links(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Link", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getService_ServiceSequence(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "OutputPrimitive", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getPrimitive_Event(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Event" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getPrimitive_Parameters(), source, new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Parameters" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getPositionableElement_Position(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Position", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getSimpleFBType_Algorithm(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "Algorithm", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getBaseFBType_InternalVars(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "VarDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
		addAnnotation(getBaseFBType_InternalFbs(), source, new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
				"name", "VarDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
				"namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		});
	}

} // LibraryElementPackageImpl
