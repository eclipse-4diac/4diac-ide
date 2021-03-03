/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.fordiac.ide.model.data.DataType;
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
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.IVarElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
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

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage
 * @generated
 */
public class LibraryElementSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static LibraryElementPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public LibraryElementSwitch() {
		if (modelPackage == null) {
			modelPackage = LibraryElementPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a
	 * non null result; it yields that result. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case LibraryElementPackage.ADAPTER_DECLARATION: {
			AdapterDeclaration adapterDeclaration = (AdapterDeclaration) theEObject;
			T result = caseAdapterDeclaration(adapterDeclaration);
			if (result == null)
				result = caseVarDeclaration(adapterDeclaration);
			if (result == null)
				result = caseIInterfaceElement(adapterDeclaration);
			if (result == null)
				result = caseINamedElement(adapterDeclaration);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.ADAPTER_TYPE: {
			AdapterType adapterType = (AdapterType) theEObject;
			T result = caseAdapterType(adapterType);
			if (result == null)
				result = caseDataType(adapterType);
			if (result == null)
				result = caseLibraryElement(adapterType);
			if (result == null)
				result = caseINamedElement(adapterType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.ALGORITHM: {
			Algorithm algorithm = (Algorithm) theEObject;
			T result = caseAlgorithm(algorithm);
			if (result == null)
				result = caseINamedElement(algorithm);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.APPLICATION: {
			Application application = (Application) theEObject;
			T result = caseApplication(application);
			if (result == null)
				result = caseConfigurableObject(application);
			if (result == null)
				result = caseINamedElement(application);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.BASIC_FB_TYPE: {
			BasicFBType basicFBType = (BasicFBType) theEObject;
			T result = caseBasicFBType(basicFBType);
			if (result == null)
				result = caseBaseFBType(basicFBType);
			if (result == null)
				result = caseFBType(basicFBType);
			if (result == null)
				result = caseCompilableType(basicFBType);
			if (result == null)
				result = caseLibraryElement(basicFBType);
			if (result == null)
				result = caseINamedElement(basicFBType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.COMPILER_INFO: {
			CompilerInfo compilerInfo = (CompilerInfo) theEObject;
			T result = caseCompilerInfo(compilerInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.COMPILER: {
			org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler = (org.eclipse.fordiac.ide.model.libraryElement.Compiler) theEObject;
			T result = caseCompiler(compiler);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.CONNECTION: {
			Connection connection = (Connection) theEObject;
			T result = caseConnection(connection);
			if (result == null)
				result = caseConfigurableObject(connection);
			if (result == null)
				result = caseINamedElement(connection);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.CONNECTION_ROUTING_DATA: {
			ConnectionRoutingData connectionRoutingData = (ConnectionRoutingData) theEObject;
			T result = caseConnectionRoutingData(connectionRoutingData);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.DEVICE: {
			Device device = (Device) theEObject;
			T result = caseDevice(device);
			if (result == null)
				result = caseTypedConfigureableObject(device);
			if (result == null)
				result = casePositionableElement(device);
			if (result == null)
				result = caseColorizableElement(device);
			if (result == null)
				result = caseIVarElement(device);
			if (result == null)
				result = caseConfigurableObject(device);
			if (result == null)
				result = caseINamedElement(device);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.DEVICE_TYPE: {
			DeviceType deviceType = (DeviceType) theEObject;
			T result = caseDeviceType(deviceType);
			if (result == null)
				result = caseCompilableType(deviceType);
			if (result == null)
				result = caseLibraryElement(deviceType);
			if (result == null)
				result = caseINamedElement(deviceType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.EC_ACTION: {
			ECAction ecAction = (ECAction) theEObject;
			T result = caseECAction(ecAction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.ECC: {
			ECC ecc = (ECC) theEObject;
			T result = caseECC(ecc);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.EC_STATE: {
			ECState ecState = (ECState) theEObject;
			T result = caseECState(ecState);
			if (result == null)
				result = caseINamedElement(ecState);
			if (result == null)
				result = casePositionableElement(ecState);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.EC_TRANSITION: {
			ECTransition ecTransition = (ECTransition) theEObject;
			T result = caseECTransition(ecTransition);
			if (result == null)
				result = casePositionableElement(ecTransition);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.EVENT: {
			Event event = (Event) theEObject;
			T result = caseEvent(event);
			if (result == null)
				result = caseIInterfaceElement(event);
			if (result == null)
				result = caseINamedElement(event);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.FB: {
			FB fb = (FB) theEObject;
			T result = caseFB(fb);
			if (result == null)
				result = caseFBNetworkElement(fb);
			if (result == null)
				result = caseTypedConfigureableObject(fb);
			if (result == null)
				result = casePositionableElement(fb);
			if (result == null)
				result = caseConfigurableObject(fb);
			if (result == null)
				result = caseINamedElement(fb);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.FB_NETWORK_ELEMENT: {
			FBNetworkElement fbNetworkElement = (FBNetworkElement) theEObject;
			T result = caseFBNetworkElement(fbNetworkElement);
			if (result == null)
				result = caseTypedConfigureableObject(fbNetworkElement);
			if (result == null)
				result = casePositionableElement(fbNetworkElement);
			if (result == null)
				result = caseConfigurableObject(fbNetworkElement);
			if (result == null)
				result = caseINamedElement(fbNetworkElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SUB_APP: {
			SubApp subApp = (SubApp) theEObject;
			T result = caseSubApp(subApp);
			if (result == null)
				result = caseFBNetworkElement(subApp);
			if (result == null)
				result = caseTypedConfigureableObject(subApp);
			if (result == null)
				result = casePositionableElement(subApp);
			if (result == null)
				result = caseConfigurableObject(subApp);
			if (result == null)
				result = caseINamedElement(subApp);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.FB_TYPE: {
			FBType fbType = (FBType) theEObject;
			T result = caseFBType(fbType);
			if (result == null)
				result = caseCompilableType(fbType);
			if (result == null)
				result = caseLibraryElement(fbType);
			if (result == null)
				result = caseINamedElement(fbType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.IDENTIFICATION: {
			Identification identification = (Identification) theEObject;
			T result = caseIdentification(identification);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.INPUT_PRIMITIVE: {
			InputPrimitive inputPrimitive = (InputPrimitive) theEObject;
			T result = caseInputPrimitive(inputPrimitive);
			if (result == null)
				result = casePrimitive(inputPrimitive);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.INTERFACE_LIST: {
			InterfaceList interfaceList = (InterfaceList) theEObject;
			T result = caseInterfaceList(interfaceList);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.LINK: {
			Link link = (Link) theEObject;
			T result = caseLink(link);
			if (result == null)
				result = caseConfigurableObject(link);
			if (result == null)
				result = caseINamedElement(link);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.MAPPING: {
			Mapping mapping = (Mapping) theEObject;
			T result = caseMapping(mapping);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.OTHER_ALGORITHM: {
			OtherAlgorithm otherAlgorithm = (OtherAlgorithm) theEObject;
			T result = caseOtherAlgorithm(otherAlgorithm);
			if (result == null)
				result = caseTextAlgorithm(otherAlgorithm);
			if (result == null)
				result = caseAlgorithm(otherAlgorithm);
			if (result == null)
				result = caseINamedElement(otherAlgorithm);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.OUTPUT_PRIMITIVE: {
			OutputPrimitive outputPrimitive = (OutputPrimitive) theEObject;
			T result = caseOutputPrimitive(outputPrimitive);
			if (result == null)
				result = casePrimitive(outputPrimitive);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.ATTRIBUTE: {
			Attribute attribute = (Attribute) theEObject;
			T result = caseAttribute(attribute);
			if (result == null)
				result = caseINamedElement(attribute);
			if (result == null)
				result = caseTypedElement(attribute);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.RESOURCE: {
			Resource resource = (Resource) theEObject;
			T result = caseResource(resource);
			if (result == null)
				result = caseTypedConfigureableObject(resource);
			if (result == null)
				result = caseIVarElement(resource);
			if (result == null)
				result = caseConfigurableObject(resource);
			if (result == null)
				result = caseINamedElement(resource);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.RESOURCE_TYPE_NAME: {
			ResourceTypeName resourceTypeName = (ResourceTypeName) theEObject;
			T result = caseResourceTypeName(resourceTypeName);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.RESOURCE_TYPE: {
			ResourceType resourceType = (ResourceType) theEObject;
			T result = caseResourceType(resourceType);
			if (result == null)
				result = caseCompilableType(resourceType);
			if (result == null)
				result = caseLibraryElement(resourceType);
			if (result == null)
				result = caseINamedElement(resourceType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SEGMENT: {
			Segment segment = (Segment) theEObject;
			T result = caseSegment(segment);
			if (result == null)
				result = caseTypedConfigureableObject(segment);
			if (result == null)
				result = casePositionableElement(segment);
			if (result == null)
				result = caseColorizableElement(segment);
			if (result == null)
				result = caseConfigurableObject(segment);
			if (result == null)
				result = caseINamedElement(segment);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SERVICE_SEQUENCE: {
			ServiceSequence serviceSequence = (ServiceSequence) theEObject;
			T result = caseServiceSequence(serviceSequence);
			if (result == null)
				result = caseINamedElement(serviceSequence);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SERVICE_TRANSACTION: {
			ServiceTransaction serviceTransaction = (ServiceTransaction) theEObject;
			T result = caseServiceTransaction(serviceTransaction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SERVICE_INTERFACE_FB_TYPE: {
			ServiceInterfaceFBType serviceInterfaceFBType = (ServiceInterfaceFBType) theEObject;
			T result = caseServiceInterfaceFBType(serviceInterfaceFBType);
			if (result == null)
				result = caseFBType(serviceInterfaceFBType);
			if (result == null)
				result = caseCompilableType(serviceInterfaceFBType);
			if (result == null)
				result = caseLibraryElement(serviceInterfaceFBType);
			if (result == null)
				result = caseINamedElement(serviceInterfaceFBType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.ST_ALGORITHM: {
			STAlgorithm stAlgorithm = (STAlgorithm) theEObject;
			T result = caseSTAlgorithm(stAlgorithm);
			if (result == null)
				result = caseTextAlgorithm(stAlgorithm);
			if (result == null)
				result = caseAlgorithm(stAlgorithm);
			if (result == null)
				result = caseINamedElement(stAlgorithm);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.FB_NETWORK: {
			FBNetwork fbNetwork = (FBNetwork) theEObject;
			T result = caseFBNetwork(fbNetwork);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SUB_APP_TYPE: {
			SubAppType subAppType = (SubAppType) theEObject;
			T result = caseSubAppType(subAppType);
			if (result == null)
				result = caseCompositeFBType(subAppType);
			if (result == null)
				result = caseFBType(subAppType);
			if (result == null)
				result = caseCompilableType(subAppType);
			if (result == null)
				result = caseLibraryElement(subAppType);
			if (result == null)
				result = caseINamedElement(subAppType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.AUTOMATION_SYSTEM: {
			AutomationSystem automationSystem = (AutomationSystem) theEObject;
			T result = caseAutomationSystem(automationSystem);
			if (result == null)
				result = caseLibraryElement(automationSystem);
			if (result == null)
				result = caseINamedElement(automationSystem);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.VAR_DECLARATION: {
			VarDeclaration varDeclaration = (VarDeclaration) theEObject;
			T result = caseVarDeclaration(varDeclaration);
			if (result == null)
				result = caseIInterfaceElement(varDeclaration);
			if (result == null)
				result = caseINamedElement(varDeclaration);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.VERSION_INFO: {
			VersionInfo versionInfo = (VersionInfo) theEObject;
			T result = caseVersionInfo(versionInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.WITH: {
			With with = (With) theEObject;
			T result = caseWith(with);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.LIBRARY_ELEMENT: {
			LibraryElement libraryElement = (LibraryElement) theEObject;
			T result = caseLibraryElement(libraryElement);
			if (result == null)
				result = caseINamedElement(libraryElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.COMPILABLE_TYPE: {
			CompilableType compilableType = (CompilableType) theEObject;
			T result = caseCompilableType(compilableType);
			if (result == null)
				result = caseLibraryElement(compilableType);
			if (result == null)
				result = caseINamedElement(compilableType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.CONFIGURABLE_OBJECT: {
			ConfigurableObject configurableObject = (ConfigurableObject) theEObject;
			T result = caseConfigurableObject(configurableObject);
			if (result == null)
				result = caseINamedElement(configurableObject);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.COMPOSITE_FB_TYPE: {
			CompositeFBType compositeFBType = (CompositeFBType) theEObject;
			T result = caseCompositeFBType(compositeFBType);
			if (result == null)
				result = caseFBType(compositeFBType);
			if (result == null)
				result = caseCompilableType(compositeFBType);
			if (result == null)
				result = caseLibraryElement(compositeFBType);
			if (result == null)
				result = caseINamedElement(compositeFBType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.TEXT_ALGORITHM: {
			TextAlgorithm textAlgorithm = (TextAlgorithm) theEObject;
			T result = caseTextAlgorithm(textAlgorithm);
			if (result == null)
				result = caseAlgorithm(textAlgorithm);
			if (result == null)
				result = caseINamedElement(textAlgorithm);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.DATA_CONNECTION: {
			DataConnection dataConnection = (DataConnection) theEObject;
			T result = caseDataConnection(dataConnection);
			if (result == null)
				result = caseConnection(dataConnection);
			if (result == null)
				result = caseConfigurableObject(dataConnection);
			if (result == null)
				result = caseINamedElement(dataConnection);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.EVENT_CONNECTION: {
			EventConnection eventConnection = (EventConnection) theEObject;
			T result = caseEventConnection(eventConnection);
			if (result == null)
				result = caseConnection(eventConnection);
			if (result == null)
				result = caseConfigurableObject(eventConnection);
			if (result == null)
				result = caseINamedElement(eventConnection);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.ADAPTER_CONNECTION: {
			AdapterConnection adapterConnection = (AdapterConnection) theEObject;
			T result = caseAdapterConnection(adapterConnection);
			if (result == null)
				result = caseConnection(adapterConnection);
			if (result == null)
				result = caseConfigurableObject(adapterConnection);
			if (result == null)
				result = caseINamedElement(adapterConnection);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SERVICE_INTERFACE: {
			ServiceInterface serviceInterface = (ServiceInterface) theEObject;
			T result = caseServiceInterface(serviceInterface);
			if (result == null)
				result = caseINamedElement(serviceInterface);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.IINTERFACE_ELEMENT: {
			IInterfaceElement iInterfaceElement = (IInterfaceElement) theEObject;
			T result = caseIInterfaceElement(iInterfaceElement);
			if (result == null)
				result = caseINamedElement(iInterfaceElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.VALUE: {
			Value value = (Value) theEObject;
			T result = caseValue(value);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SYSTEM_CONFIGURATION: {
			SystemConfiguration systemConfiguration = (SystemConfiguration) theEObject;
			T result = caseSystemConfiguration(systemConfiguration);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.INAMED_ELEMENT: {
			INamedElement iNamedElement = (INamedElement) theEObject;
			T result = caseINamedElement(iNamedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.RESOURCE_TYPE_FB: {
			ResourceTypeFB resourceTypeFB = (ResourceTypeFB) theEObject;
			T result = caseResourceTypeFB(resourceTypeFB);
			if (result == null)
				result = caseFB(resourceTypeFB);
			if (result == null)
				result = caseFBNetworkElement(resourceTypeFB);
			if (result == null)
				result = caseTypedConfigureableObject(resourceTypeFB);
			if (result == null)
				result = casePositionableElement(resourceTypeFB);
			if (result == null)
				result = caseConfigurableObject(resourceTypeFB);
			if (result == null)
				result = caseINamedElement(resourceTypeFB);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SEGMENT_TYPE: {
			SegmentType segmentType = (SegmentType) theEObject;
			T result = caseSegmentType(segmentType);
			if (result == null)
				result = caseCompilableType(segmentType);
			if (result == null)
				result = caseLibraryElement(segmentType);
			if (result == null)
				result = caseINamedElement(segmentType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.ADAPTER_FB_TYPE: {
			AdapterFBType adapterFBType = (AdapterFBType) theEObject;
			T result = caseAdapterFBType(adapterFBType);
			if (result == null)
				result = caseFBType(adapterFBType);
			if (result == null)
				result = caseCompilableType(adapterFBType);
			if (result == null)
				result = caseLibraryElement(adapterFBType);
			if (result == null)
				result = caseINamedElement(adapterFBType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.ADAPTER_EVENT: {
			AdapterEvent adapterEvent = (AdapterEvent) theEObject;
			T result = caseAdapterEvent(adapterEvent);
			if (result == null)
				result = caseEvent(adapterEvent);
			if (result == null)
				result = caseIInterfaceElement(adapterEvent);
			if (result == null)
				result = caseINamedElement(adapterEvent);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SERVICE: {
			Service service = (Service) theEObject;
			T result = caseService(service);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT: {
			TypedConfigureableObject typedConfigureableObject = (TypedConfigureableObject) theEObject;
			T result = caseTypedConfigureableObject(typedConfigureableObject);
			if (result == null)
				result = caseConfigurableObject(typedConfigureableObject);
			if (result == null)
				result = caseINamedElement(typedConfigureableObject);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.ADAPTER_FB: {
			AdapterFB adapterFB = (AdapterFB) theEObject;
			T result = caseAdapterFB(adapterFB);
			if (result == null)
				result = caseFB(adapterFB);
			if (result == null)
				result = caseFBNetworkElement(adapterFB);
			if (result == null)
				result = caseTypedConfigureableObject(adapterFB);
			if (result == null)
				result = casePositionableElement(adapterFB);
			if (result == null)
				result = caseConfigurableObject(adapterFB);
			if (result == null)
				result = caseINamedElement(adapterFB);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.PRIMITIVE: {
			Primitive primitive = (Primitive) theEObject;
			T result = casePrimitive(primitive);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.POSITIONABLE_ELEMENT: {
			PositionableElement positionableElement = (PositionableElement) theEObject;
			T result = casePositionableElement(positionableElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.POSITION: {
			Position position = (Position) theEObject;
			T result = casePosition(position);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.COLOR: {
			Color color = (Color) theEObject;
			T result = caseColor(color);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.COLORIZABLE_ELEMENT: {
			ColorizableElement colorizableElement = (ColorizableElement) theEObject;
			T result = caseColorizableElement(colorizableElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.IVAR_ELEMENT: {
			IVarElement iVarElement = (IVarElement) theEObject;
			T result = caseIVarElement(iVarElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.ATTRIBUTE_DECLARATION: {
			AttributeDeclaration attributeDeclaration = (AttributeDeclaration) theEObject;
			T result = caseAttributeDeclaration(attributeDeclaration);
			if (result == null)
				result = caseINamedElement(attributeDeclaration);
			if (result == null)
				result = caseTypedElement(attributeDeclaration);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.TYPED_ELEMENT: {
			TypedElement typedElement = (TypedElement) theEObject;
			T result = caseTypedElement(typedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.SIMPLE_FB_TYPE: {
			SimpleFBType simpleFBType = (SimpleFBType) theEObject;
			T result = caseSimpleFBType(simpleFBType);
			if (result == null)
				result = caseBaseFBType(simpleFBType);
			if (result == null)
				result = caseFBType(simpleFBType);
			if (result == null)
				result = caseCompilableType(simpleFBType);
			if (result == null)
				result = caseLibraryElement(simpleFBType);
			if (result == null)
				result = caseINamedElement(simpleFBType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.BASE_FB_TYPE: {
			BaseFBType baseFBType = (BaseFBType) theEObject;
			T result = caseBaseFBType(baseFBType);
			if (result == null)
				result = caseFBType(baseFBType);
			if (result == null)
				result = caseCompilableType(baseFBType);
			if (result == null)
				result = caseLibraryElement(baseFBType);
			if (result == null)
				result = caseINamedElement(baseFBType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.STRUCT_MANIPULATOR: {
			StructManipulator structManipulator = (StructManipulator) theEObject;
			T result = caseStructManipulator(structManipulator);
			if (result == null)
				result = caseFB(structManipulator);
			if (result == null)
				result = caseFBNetworkElement(structManipulator);
			if (result == null)
				result = caseTypedConfigureableObject(structManipulator);
			if (result == null)
				result = casePositionableElement(structManipulator);
			if (result == null)
				result = caseConfigurableObject(structManipulator);
			if (result == null)
				result = caseINamedElement(structManipulator);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.DEMULTIPLEXER: {
			Demultiplexer demultiplexer = (Demultiplexer) theEObject;
			T result = caseDemultiplexer(demultiplexer);
			if (result == null)
				result = caseStructManipulator(demultiplexer);
			if (result == null)
				result = caseFB(demultiplexer);
			if (result == null)
				result = caseFBNetworkElement(demultiplexer);
			if (result == null)
				result = caseTypedConfigureableObject(demultiplexer);
			if (result == null)
				result = casePositionableElement(demultiplexer);
			if (result == null)
				result = caseConfigurableObject(demultiplexer);
			if (result == null)
				result = caseINamedElement(demultiplexer);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.MULTIPLEXER: {
			Multiplexer multiplexer = (Multiplexer) theEObject;
			T result = caseMultiplexer(multiplexer);
			if (result == null)
				result = caseStructManipulator(multiplexer);
			if (result == null)
				result = caseFB(multiplexer);
			if (result == null)
				result = caseFBNetworkElement(multiplexer);
			if (result == null)
				result = caseTypedConfigureableObject(multiplexer);
			if (result == null)
				result = casePositionableElement(multiplexer);
			if (result == null)
				result = caseConfigurableObject(multiplexer);
			if (result == null)
				result = caseINamedElement(multiplexer);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LibraryElementPackage.LOCAL_VARIABLE: {
			LocalVariable localVariable = (LocalVariable) theEObject;
			T result = caseLocalVariable(localVariable);
			if (result == null)
				result = caseVarDeclaration(localVariable);
			if (result == null)
				result = caseIInterfaceElement(localVariable);
			if (result == null)
				result = caseINamedElement(localVariable);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter
	 * Declaration</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter
	 *         Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterDeclaration(AdapterDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter
	 * Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter
	 *         Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterType(AdapterType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Algorithm</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Algorithm</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlgorithm(Algorithm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Application</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Application</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplication(Application object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic FB
	 * Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic FB
	 *         Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicFBType(BasicFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Compiler
	 * Info</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Compiler
	 *         Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompilerInfo(CompilerInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Compiler</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Compiler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompiler(org.eclipse.fordiac.ide.model.libraryElement.Compiler object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Connection</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnection(Connection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Connection Routing Data</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Connection Routing Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectionRoutingData(ConnectionRoutingData object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Device</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Device</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDevice(Device object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Device
	 * Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Device
	 *         Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeviceType(DeviceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EC
	 * Action</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EC
	 *         Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseECAction(ECAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>ECC</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>ECC</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseECC(ECC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EC
	 * State</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EC
	 *         State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseECState(ECState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EC
	 * Transition</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EC
	 *         Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseECTransition(ECTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Event</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEvent(Event object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB
	 * Network</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB
	 *         Network</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFBNetwork(FBNetwork object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>FB</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>FB</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFB(FB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB
	 * Network Element</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB
	 *         Network Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFBNetworkElement(FBNetworkElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB
	 * Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB
	 *         Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFBType(FBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Identification</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Identification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentification(Identification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Input
	 * Primitive</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Input
	 *         Primitive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInputPrimitive(InputPrimitive object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Interface List</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Interface List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInterfaceList(InterfaceList object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Link</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLink(Link object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Mapping</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapping(Mapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Other
	 * Algorithm</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Other
	 *         Algorithm</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOtherAlgorithm(OtherAlgorithm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Output
	 * Primitive</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Output
	 *         Primitive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOutputPrimitive(OutputPrimitive object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Resource</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResource(Resource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource
	 * Type Name</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource
	 *         Type Name</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceTypeName(ResourceTypeName object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource
	 * Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource
	 *         Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceType(ResourceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Segment</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Segment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSegment(Segment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service
	 * Sequence</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service
	 *         Sequence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceSequence(ServiceSequence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service
	 * Transaction</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service
	 *         Transaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceTransaction(ServiceTransaction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service
	 * Interface FB Type</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service
	 *         Interface FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceInterfaceFBType(ServiceInterfaceFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST
	 * Algorithm</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST
	 *         Algorithm</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTAlgorithm(STAlgorithm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub
	 * App</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub
	 *         App</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubApp(SubApp object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub App
	 * Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub App
	 *         Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubAppType(SubAppType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Automation System</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Automation System</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAutomationSystem(AutomationSystem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Var
	 * Declaration</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Var
	 *         Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVarDeclaration(VarDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version
	 * Info</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version
	 *         Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionInfo(VersionInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>With</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>With</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWith(With object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Library
	 * Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Library
	 *         Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLibraryElement(LibraryElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Compilable Type</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Compilable Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompilableType(CompilableType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Configurable Object</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Configurable Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigurableObject(ConfigurableObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Composite FB Type</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Composite FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositeFBType(CompositeFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text
	 * Algorithm</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text
	 *         Algorithm</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextAlgorithm(TextAlgorithm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data
	 * Connection</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data
	 *         Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataConnection(DataConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event
	 * Connection</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event
	 *         Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventConnection(EventConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter
	 * Connection</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter
	 *         Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterConnection(AdapterConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service
	 * Interface</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service
	 *         Interface</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceInterface(ServiceInterface object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>IInterface Element</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>IInterface Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIInterfaceElement(IInterfaceElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Value</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValue(Value object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>System
	 * Configuration</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>System
	 *         Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSystemConfiguration(SystemConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed
	 * Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed
	 *         Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedElement(INamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource
	 * Type FB</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource
	 *         Type FB</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceTypeFB(ResourceTypeFB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Segment
	 * Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Segment
	 *         Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSegmentType(SegmentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter
	 * FB Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter
	 *         FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterFBType(AdapterFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter
	 * Event</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter
	 *         Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterEvent(AdapterEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Service</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Service</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseService(Service object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typed
	 * Configureable Object</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typed
	 *         Configureable Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypedConfigureableObject(TypedConfigureableObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter
	 * FB</em>'. <!-- begin-user-doc --> This implementation returns null; returning
	 * a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter
	 *         FB</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterFB(AdapterFB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Primitive</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Primitive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrimitive(Primitive object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Positionable Element</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Positionable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePositionableElement(PositionableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Position</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Position</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePosition(Position object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Color</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Color</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColor(Color object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Colorizable Element</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Colorizable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColorizableElement(ColorizableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IVar
	 * Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IVar
	 *         Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIVarElement(IVarElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Attribute Declaration</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Attribute Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeDeclaration(AttributeDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typed
	 * Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typed
	 *         Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypedElement(TypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple
	 * FB Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple
	 *         FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleFBType(SimpleFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Base FB
	 * Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Base FB
	 *         Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBaseFBType(BaseFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Struct
	 * Manipulator</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Struct
	 *         Manipulator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStructManipulator(StructManipulator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Demultiplexer</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Demultiplexer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDemultiplexer(Demultiplexer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Multiplexer</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Multiplexer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultiplexer(Multiplexer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Local
	 * Variable</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Local
	 *         Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalVariable(LocalVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Attribute</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttribute(Attribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataType(DataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>EObject</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last
	 * case anyway. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // LibraryElementSwitch
