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
package org.eclipse.fordiac.ide.model.libraryElement.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage
 * @generated
 */
public class LibraryElementSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static LibraryElementPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryElementSwitch() {
		if (modelPackage == null) {
			modelPackage = LibraryElementPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case LibraryElementPackage.ADAPTER_CONNECTION: {
				AdapterConnection adapterConnection = (AdapterConnection)theEObject;
				T result = caseAdapterConnection(adapterConnection);
				if (result == null) result = caseConnection(adapterConnection);
				if (result == null) result = caseHiddenElement(adapterConnection);
				if (result == null) result = caseConfigurableObject(adapterConnection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ADAPTER_DECLARATION: {
				AdapterDeclaration adapterDeclaration = (AdapterDeclaration)theEObject;
				T result = caseAdapterDeclaration(adapterDeclaration);
				if (result == null) result = caseIInterfaceElement(adapterDeclaration);
				if (result == null) result = caseITypedElement(adapterDeclaration);
				if (result == null) result = caseHiddenElement(adapterDeclaration);
				if (result == null) result = caseINamedElement(adapterDeclaration);
				if (result == null) result = caseConfigurableObject(adapterDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ADAPTER_FB: {
				AdapterFB adapterFB = (AdapterFB)theEObject;
				T result = caseAdapterFB(adapterFB);
				if (result == null) result = caseFB(adapterFB);
				if (result == null) result = caseFBNetworkElement(adapterFB);
				if (result == null) result = caseICallable(adapterFB);
				if (result == null) result = caseTypedConfigureableObject(adapterFB);
				if (result == null) result = casePositionableElement(adapterFB);
				if (result == null) result = caseITypedElement(adapterFB);
				if (result == null) result = caseConfigurableObject(adapterFB);
				if (result == null) result = caseINamedElement(adapterFB);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ADAPTER_TYPE: {
				AdapterType adapterType = (AdapterType)theEObject;
				T result = caseAdapterType(adapterType);
				if (result == null) result = caseDataType(adapterType);
				if (result == null) result = caseFBType(adapterType);
				if (result == null) result = caseLibraryElement(adapterType);
				if (result == null) result = caseICallable(adapterType);
				if (result == null) result = caseINamedElement(adapterType);
				if (result == null) result = caseConfigurableObject(adapterType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ALGORITHM: {
				Algorithm algorithm = (Algorithm)theEObject;
				T result = caseAlgorithm(algorithm);
				if (result == null) result = caseICallable(algorithm);
				if (result == null) result = caseINamedElement(algorithm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.APPLICATION: {
				Application application = (Application)theEObject;
				T result = caseApplication(application);
				if (result == null) result = caseINamedElement(application);
				if (result == null) result = caseConfigurableObject(application);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ARRAY_SIZE: {
				ArraySize arraySize = (ArraySize)theEObject;
				T result = caseArraySize(arraySize);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ATTRIBUTE: {
				Attribute attribute = (Attribute)theEObject;
				T result = caseAttribute(attribute);
				if (result == null) result = caseITypedElement(attribute);
				if (result == null) result = caseINamedElement(attribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ATTRIBUTE_DECLARATION: {
				AttributeDeclaration attributeDeclaration = (AttributeDeclaration)theEObject;
				T result = caseAttributeDeclaration(attributeDeclaration);
				if (result == null) result = caseITypedElement(attributeDeclaration);
				if (result == null) result = caseLibraryElement(attributeDeclaration);
				if (result == null) result = caseINamedElement(attributeDeclaration);
				if (result == null) result = caseConfigurableObject(attributeDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.BASE_FB_TYPE: {
				BaseFBType baseFBType = (BaseFBType)theEObject;
				T result = caseBaseFBType(baseFBType);
				if (result == null) result = caseFBType(baseFBType);
				if (result == null) result = caseLibraryElement(baseFBType);
				if (result == null) result = caseICallable(baseFBType);
				if (result == null) result = caseINamedElement(baseFBType);
				if (result == null) result = caseConfigurableObject(baseFBType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.BASIC_FB_TYPE: {
				BasicFBType basicFBType = (BasicFBType)theEObject;
				T result = caseBasicFBType(basicFBType);
				if (result == null) result = caseBaseFBType(basicFBType);
				if (result == null) result = caseFBType(basicFBType);
				if (result == null) result = caseLibraryElement(basicFBType);
				if (result == null) result = caseICallable(basicFBType);
				if (result == null) result = caseINamedElement(basicFBType);
				if (result == null) result = caseConfigurableObject(basicFBType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.AUTOMATION_SYSTEM: {
				AutomationSystem automationSystem = (AutomationSystem)theEObject;
				T result = caseAutomationSystem(automationSystem);
				if (result == null) result = caseLibraryElement(automationSystem);
				if (result == null) result = caseINamedElement(automationSystem);
				if (result == null) result = caseConfigurableObject(automationSystem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.CFB_INSTANCE: {
				CFBInstance cfbInstance = (CFBInstance)theEObject;
				T result = caseCFBInstance(cfbInstance);
				if (result == null) result = caseFB(cfbInstance);
				if (result == null) result = caseFBNetworkElement(cfbInstance);
				if (result == null) result = caseICallable(cfbInstance);
				if (result == null) result = caseTypedConfigureableObject(cfbInstance);
				if (result == null) result = casePositionableElement(cfbInstance);
				if (result == null) result = caseITypedElement(cfbInstance);
				if (result == null) result = caseConfigurableObject(cfbInstance);
				if (result == null) result = caseINamedElement(cfbInstance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.COLOR: {
				Color color = (Color)theEObject;
				T result = caseColor(color);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.COLORIZABLE_ELEMENT: {
				ColorizableElement colorizableElement = (ColorizableElement)theEObject;
				T result = caseColorizableElement(colorizableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.COMMENT: {
				Comment comment = (Comment)theEObject;
				T result = caseComment(comment);
				if (result == null) result = caseFBNetworkElement(comment);
				if (result == null) result = caseTypedConfigureableObject(comment);
				if (result == null) result = casePositionableElement(comment);
				if (result == null) result = caseITypedElement(comment);
				if (result == null) result = caseConfigurableObject(comment);
				if (result == null) result = caseINamedElement(comment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.COMMUNICATION_CHANNEL: {
				CommunicationChannel communicationChannel = (CommunicationChannel)theEObject;
				T result = caseCommunicationChannel(communicationChannel);
				if (result == null) result = caseFB(communicationChannel);
				if (result == null) result = caseFBNetworkElement(communicationChannel);
				if (result == null) result = caseICallable(communicationChannel);
				if (result == null) result = caseTypedConfigureableObject(communicationChannel);
				if (result == null) result = casePositionableElement(communicationChannel);
				if (result == null) result = caseITypedElement(communicationChannel);
				if (result == null) result = caseConfigurableObject(communicationChannel);
				if (result == null) result = caseINamedElement(communicationChannel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.COMMUNICATION_CONFIGURATION: {
				CommunicationConfiguration communicationConfiguration = (CommunicationConfiguration)theEObject;
				T result = caseCommunicationConfiguration(communicationConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.COMMUNICATION_MAPPING_TARGET: {
				CommunicationMappingTarget communicationMappingTarget = (CommunicationMappingTarget)theEObject;
				T result = caseCommunicationMappingTarget(communicationMappingTarget);
				if (result == null) result = caseMappingTarget(communicationMappingTarget);
				if (result == null) result = caseINamedElement(communicationMappingTarget);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.COMPILER: {
				org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler = (org.eclipse.fordiac.ide.model.libraryElement.Compiler)theEObject;
				T result = caseCompiler(compiler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.COMPILER_INFO: {
				CompilerInfo compilerInfo = (CompilerInfo)theEObject;
				T result = caseCompilerInfo(compilerInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.COMPOSITE_FB_TYPE: {
				CompositeFBType compositeFBType = (CompositeFBType)theEObject;
				T result = caseCompositeFBType(compositeFBType);
				if (result == null) result = caseFBType(compositeFBType);
				if (result == null) result = caseLibraryElement(compositeFBType);
				if (result == null) result = caseICallable(compositeFBType);
				if (result == null) result = caseINamedElement(compositeFBType);
				if (result == null) result = caseConfigurableObject(compositeFBType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.CONFIGURABLE_OBJECT: {
				ConfigurableObject configurableObject = (ConfigurableObject)theEObject;
				T result = caseConfigurableObject(configurableObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.CONFIGURABLE_FB: {
				ConfigurableFB configurableFB = (ConfigurableFB)theEObject;
				T result = caseConfigurableFB(configurableFB);
				if (result == null) result = caseFB(configurableFB);
				if (result == null) result = caseFBNetworkElement(configurableFB);
				if (result == null) result = caseICallable(configurableFB);
				if (result == null) result = caseTypedConfigureableObject(configurableFB);
				if (result == null) result = casePositionableElement(configurableFB);
				if (result == null) result = caseITypedElement(configurableFB);
				if (result == null) result = caseConfigurableObject(configurableFB);
				if (result == null) result = caseINamedElement(configurableFB);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.CONFIGURABLE_MOVE_FB: {
				ConfigurableMoveFB configurableMoveFB = (ConfigurableMoveFB)theEObject;
				T result = caseConfigurableMoveFB(configurableMoveFB);
				if (result == null) result = caseConfigurableFB(configurableMoveFB);
				if (result == null) result = caseFB(configurableMoveFB);
				if (result == null) result = caseFBNetworkElement(configurableMoveFB);
				if (result == null) result = caseICallable(configurableMoveFB);
				if (result == null) result = caseTypedConfigureableObject(configurableMoveFB);
				if (result == null) result = casePositionableElement(configurableMoveFB);
				if (result == null) result = caseITypedElement(configurableMoveFB);
				if (result == null) result = caseConfigurableObject(configurableMoveFB);
				if (result == null) result = caseINamedElement(configurableMoveFB);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.CONNECTION: {
				Connection connection = (Connection)theEObject;
				T result = caseConnection(connection);
				if (result == null) result = caseHiddenElement(connection);
				if (result == null) result = caseConfigurableObject(connection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.CONNECTION_ROUTING_DATA: {
				ConnectionRoutingData connectionRoutingData = (ConnectionRoutingData)theEObject;
				T result = caseConnectionRoutingData(connectionRoutingData);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.DATA_CONNECTION: {
				DataConnection dataConnection = (DataConnection)theEObject;
				T result = caseDataConnection(dataConnection);
				if (result == null) result = caseConnection(dataConnection);
				if (result == null) result = caseHiddenElement(dataConnection);
				if (result == null) result = caseConfigurableObject(dataConnection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.DEMULTIPLEXER: {
				Demultiplexer demultiplexer = (Demultiplexer)theEObject;
				T result = caseDemultiplexer(demultiplexer);
				if (result == null) result = caseStructManipulator(demultiplexer);
				if (result == null) result = caseConfigurableFB(demultiplexer);
				if (result == null) result = caseFB(demultiplexer);
				if (result == null) result = caseFBNetworkElement(demultiplexer);
				if (result == null) result = caseICallable(demultiplexer);
				if (result == null) result = caseTypedConfigureableObject(demultiplexer);
				if (result == null) result = casePositionableElement(demultiplexer);
				if (result == null) result = caseITypedElement(demultiplexer);
				if (result == null) result = caseConfigurableObject(demultiplexer);
				if (result == null) result = caseINamedElement(demultiplexer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.DEVICE: {
				Device device = (Device)theEObject;
				T result = caseDevice(device);
				if (result == null) result = caseTypedConfigureableObject(device);
				if (result == null) result = casePositionableElement(device);
				if (result == null) result = caseColorizableElement(device);
				if (result == null) result = caseIVarElement(device);
				if (result == null) result = caseITypedElement(device);
				if (result == null) result = caseConfigurableObject(device);
				if (result == null) result = caseINamedElement(device);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.DEVICE_TYPE: {
				DeviceType deviceType = (DeviceType)theEObject;
				T result = caseDeviceType(deviceType);
				if (result == null) result = caseLibraryElement(deviceType);
				if (result == null) result = caseINamedElement(deviceType);
				if (result == null) result = caseConfigurableObject(deviceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.EC_ACTION: {
				ECAction ecAction = (ECAction)theEObject;
				T result = caseECAction(ecAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ECC: {
				ECC ecc = (ECC)theEObject;
				T result = caseECC(ecc);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.EC_STATE: {
				ECState ecState = (ECState)theEObject;
				T result = caseECState(ecState);
				if (result == null) result = caseINamedElement(ecState);
				if (result == null) result = casePositionableElement(ecState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.EC_TRANSITION: {
				ECTransition ecTransition = (ECTransition)theEObject;
				T result = caseECTransition(ecTransition);
				if (result == null) result = casePositionableElement(ecTransition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ERROR_MARKER_DATA_TYPE: {
				ErrorMarkerDataType errorMarkerDataType = (ErrorMarkerDataType)theEObject;
				T result = caseErrorMarkerDataType(errorMarkerDataType);
				if (result == null) result = caseDataType(errorMarkerDataType);
				if (result == null) result = caseLibraryElement(errorMarkerDataType);
				if (result == null) result = caseINamedElement(errorMarkerDataType);
				if (result == null) result = caseConfigurableObject(errorMarkerDataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ERROR_MARKER_FBN_ELEMENT: {
				ErrorMarkerFBNElement errorMarkerFBNElement = (ErrorMarkerFBNElement)theEObject;
				T result = caseErrorMarkerFBNElement(errorMarkerFBNElement);
				if (result == null) result = caseFBNetworkElement(errorMarkerFBNElement);
				if (result == null) result = caseTypedConfigureableObject(errorMarkerFBNElement);
				if (result == null) result = casePositionableElement(errorMarkerFBNElement);
				if (result == null) result = caseITypedElement(errorMarkerFBNElement);
				if (result == null) result = caseConfigurableObject(errorMarkerFBNElement);
				if (result == null) result = caseINamedElement(errorMarkerFBNElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ERROR_MARKER_INTERFACE: {
				ErrorMarkerInterface errorMarkerInterface = (ErrorMarkerInterface)theEObject;
				T result = caseErrorMarkerInterface(errorMarkerInterface);
				if (result == null) result = caseIInterfaceElement(errorMarkerInterface);
				if (result == null) result = caseITypedElement(errorMarkerInterface);
				if (result == null) result = caseHiddenElement(errorMarkerInterface);
				if (result == null) result = caseINamedElement(errorMarkerInterface);
				if (result == null) result = caseConfigurableObject(errorMarkerInterface);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.EVENT: {
				Event event = (Event)theEObject;
				T result = caseEvent(event);
				if (result == null) result = caseIInterfaceElement(event);
				if (result == null) result = caseICallable(event);
				if (result == null) result = caseITypedElement(event);
				if (result == null) result = caseHiddenElement(event);
				if (result == null) result = caseINamedElement(event);
				if (result == null) result = caseConfigurableObject(event);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.EVENT_CONNECTION: {
				EventConnection eventConnection = (EventConnection)theEObject;
				T result = caseEventConnection(eventConnection);
				if (result == null) result = caseConnection(eventConnection);
				if (result == null) result = caseHiddenElement(eventConnection);
				if (result == null) result = caseConfigurableObject(eventConnection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.FB: {
				FB fb = (FB)theEObject;
				T result = caseFB(fb);
				if (result == null) result = caseFBNetworkElement(fb);
				if (result == null) result = caseICallable(fb);
				if (result == null) result = caseTypedConfigureableObject(fb);
				if (result == null) result = casePositionableElement(fb);
				if (result == null) result = caseITypedElement(fb);
				if (result == null) result = caseConfigurableObject(fb);
				if (result == null) result = caseINamedElement(fb);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.FB_NETWORK: {
				FBNetwork fbNetwork = (FBNetwork)theEObject;
				T result = caseFBNetwork(fbNetwork);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.FB_NETWORK_ELEMENT: {
				FBNetworkElement fbNetworkElement = (FBNetworkElement)theEObject;
				T result = caseFBNetworkElement(fbNetworkElement);
				if (result == null) result = caseTypedConfigureableObject(fbNetworkElement);
				if (result == null) result = casePositionableElement(fbNetworkElement);
				if (result == null) result = caseITypedElement(fbNetworkElement);
				if (result == null) result = caseConfigurableObject(fbNetworkElement);
				if (result == null) result = caseINamedElement(fbNetworkElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.FB_TYPE: {
				FBType fbType = (FBType)theEObject;
				T result = caseFBType(fbType);
				if (result == null) result = caseLibraryElement(fbType);
				if (result == null) result = caseICallable(fbType);
				if (result == null) result = caseINamedElement(fbType);
				if (result == null) result = caseConfigurableObject(fbType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.FUNCTION: {
				Function function = (Function)theEObject;
				T result = caseFunction(function);
				if (result == null) result = caseICallable(function);
				if (result == null) result = caseINamedElement(function);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.FUNCTION_BODY: {
				FunctionBody functionBody = (FunctionBody)theEObject;
				T result = caseFunctionBody(functionBody);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.FUNCTION_FB_TYPE: {
				FunctionFBType functionFBType = (FunctionFBType)theEObject;
				T result = caseFunctionFBType(functionFBType);
				if (result == null) result = caseFBType(functionFBType);
				if (result == null) result = caseLibraryElement(functionFBType);
				if (result == null) result = caseICallable(functionFBType);
				if (result == null) result = caseINamedElement(functionFBType);
				if (result == null) result = caseConfigurableObject(functionFBType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.GLOBAL_CONSTANTS: {
				GlobalConstants globalConstants = (GlobalConstants)theEObject;
				T result = caseGlobalConstants(globalConstants);
				if (result == null) result = caseLibraryElement(globalConstants);
				if (result == null) result = caseINamedElement(globalConstants);
				if (result == null) result = caseConfigurableObject(globalConstants);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.GROUP: {
				Group group = (Group)theEObject;
				T result = caseGroup(group);
				if (result == null) result = caseFBNetworkElement(group);
				if (result == null) result = caseTypedConfigureableObject(group);
				if (result == null) result = casePositionableElement(group);
				if (result == null) result = caseITypedElement(group);
				if (result == null) result = caseConfigurableObject(group);
				if (result == null) result = caseINamedElement(group);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.HIDDEN_ELEMENT: {
				HiddenElement hiddenElement = (HiddenElement)theEObject;
				T result = caseHiddenElement(hiddenElement);
				if (result == null) result = caseConfigurableObject(hiddenElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ICALLABLE: {
				ICallable iCallable = (ICallable)theEObject;
				T result = caseICallable(iCallable);
				if (result == null) result = caseINamedElement(iCallable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.IDENTIFICATION: {
				Identification identification = (Identification)theEObject;
				T result = caseIdentification(identification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.IINTERFACE_ELEMENT: {
				IInterfaceElement iInterfaceElement = (IInterfaceElement)theEObject;
				T result = caseIInterfaceElement(iInterfaceElement);
				if (result == null) result = caseITypedElement(iInterfaceElement);
				if (result == null) result = caseHiddenElement(iInterfaceElement);
				if (result == null) result = caseINamedElement(iInterfaceElement);
				if (result == null) result = caseConfigurableObject(iInterfaceElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.IMPORT: {
				Import import_ = (Import)theEObject;
				T result = caseImport(import_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.INAMED_ELEMENT: {
				INamedElement iNamedElement = (INamedElement)theEObject;
				T result = caseINamedElement(iNamedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.INPUT_PRIMITIVE: {
				InputPrimitive inputPrimitive = (InputPrimitive)theEObject;
				T result = caseInputPrimitive(inputPrimitive);
				if (result == null) result = casePrimitive(inputPrimitive);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.INTERFACE_LIST: {
				InterfaceList interfaceList = (InterfaceList)theEObject;
				T result = caseInterfaceList(interfaceList);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ITYPED_ELEMENT: {
				ITypedElement iTypedElement = (ITypedElement)theEObject;
				T result = caseITypedElement(iTypedElement);
				if (result == null) result = caseINamedElement(iTypedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.IVAR_ELEMENT: {
				IVarElement iVarElement = (IVarElement)theEObject;
				T result = caseIVarElement(iVarElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.LIBRARY_ELEMENT: {
				LibraryElement libraryElement = (LibraryElement)theEObject;
				T result = caseLibraryElement(libraryElement);
				if (result == null) result = caseINamedElement(libraryElement);
				if (result == null) result = caseConfigurableObject(libraryElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.LINK: {
				Link link = (Link)theEObject;
				T result = caseLink(link);
				if (result == null) result = caseINamedElement(link);
				if (result == null) result = caseConfigurableObject(link);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.LOCAL_VARIABLE: {
				LocalVariable localVariable = (LocalVariable)theEObject;
				T result = caseLocalVariable(localVariable);
				if (result == null) result = caseVarDeclaration(localVariable);
				if (result == null) result = caseIInterfaceElement(localVariable);
				if (result == null) result = caseITypedElement(localVariable);
				if (result == null) result = caseHiddenElement(localVariable);
				if (result == null) result = caseINamedElement(localVariable);
				if (result == null) result = caseConfigurableObject(localVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.MAPPING: {
				Mapping mapping = (Mapping)theEObject;
				T result = caseMapping(mapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.MAPPING_TARGET: {
				MappingTarget mappingTarget = (MappingTarget)theEObject;
				T result = caseMappingTarget(mappingTarget);
				if (result == null) result = caseINamedElement(mappingTarget);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.MEMBER_VAR_DECLARATION: {
				MemberVarDeclaration memberVarDeclaration = (MemberVarDeclaration)theEObject;
				T result = caseMemberVarDeclaration(memberVarDeclaration);
				if (result == null) result = caseVarDeclaration(memberVarDeclaration);
				if (result == null) result = caseIInterfaceElement(memberVarDeclaration);
				if (result == null) result = caseITypedElement(memberVarDeclaration);
				if (result == null) result = caseHiddenElement(memberVarDeclaration);
				if (result == null) result = caseINamedElement(memberVarDeclaration);
				if (result == null) result = caseConfigurableObject(memberVarDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.METHOD: {
				Method method = (Method)theEObject;
				T result = caseMethod(method);
				if (result == null) result = caseICallable(method);
				if (result == null) result = caseINamedElement(method);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.MULTIPLEXER: {
				Multiplexer multiplexer = (Multiplexer)theEObject;
				T result = caseMultiplexer(multiplexer);
				if (result == null) result = caseStructManipulator(multiplexer);
				if (result == null) result = caseConfigurableFB(multiplexer);
				if (result == null) result = caseFB(multiplexer);
				if (result == null) result = caseFBNetworkElement(multiplexer);
				if (result == null) result = caseICallable(multiplexer);
				if (result == null) result = caseTypedConfigureableObject(multiplexer);
				if (result == null) result = casePositionableElement(multiplexer);
				if (result == null) result = caseITypedElement(multiplexer);
				if (result == null) result = caseConfigurableObject(multiplexer);
				if (result == null) result = caseINamedElement(multiplexer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ORIGINAL_SOURCE: {
				OriginalSource originalSource = (OriginalSource)theEObject;
				T result = caseOriginalSource(originalSource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.OTHER_ALGORITHM: {
				OtherAlgorithm otherAlgorithm = (OtherAlgorithm)theEObject;
				T result = caseOtherAlgorithm(otherAlgorithm);
				if (result == null) result = caseTextAlgorithm(otherAlgorithm);
				if (result == null) result = caseAlgorithm(otherAlgorithm);
				if (result == null) result = caseICallable(otherAlgorithm);
				if (result == null) result = caseINamedElement(otherAlgorithm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.OTHER_METHOD: {
				OtherMethod otherMethod = (OtherMethod)theEObject;
				T result = caseOtherMethod(otherMethod);
				if (result == null) result = caseTextMethod(otherMethod);
				if (result == null) result = caseMethod(otherMethod);
				if (result == null) result = caseICallable(otherMethod);
				if (result == null) result = caseINamedElement(otherMethod);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.OUTPUT_PRIMITIVE: {
				OutputPrimitive outputPrimitive = (OutputPrimitive)theEObject;
				T result = caseOutputPrimitive(outputPrimitive);
				if (result == null) result = casePrimitive(outputPrimitive);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.POSITION: {
				Position position = (Position)theEObject;
				T result = casePosition(position);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.POSITIONABLE_ELEMENT: {
				PositionableElement positionableElement = (PositionableElement)theEObject;
				T result = casePositionableElement(positionableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.PRIMITIVE: {
				Primitive primitive = (Primitive)theEObject;
				T result = casePrimitive(primitive);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.RESOURCE: {
				Resource resource = (Resource)theEObject;
				T result = caseResource(resource);
				if (result == null) result = caseTypedConfigureableObject(resource);
				if (result == null) result = caseIVarElement(resource);
				if (result == null) result = caseMappingTarget(resource);
				if (result == null) result = caseITypedElement(resource);
				if (result == null) result = caseConfigurableObject(resource);
				if (result == null) result = caseINamedElement(resource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.RESOURCE_TYPE_NAME: {
				ResourceTypeName resourceTypeName = (ResourceTypeName)theEObject;
				T result = caseResourceTypeName(resourceTypeName);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.RESOURCE_TYPE: {
				ResourceType resourceType = (ResourceType)theEObject;
				T result = caseResourceType(resourceType);
				if (result == null) result = caseLibraryElement(resourceType);
				if (result == null) result = caseINamedElement(resourceType);
				if (result == null) result = caseConfigurableObject(resourceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.RESOURCE_TYPE_FB: {
				ResourceTypeFB resourceTypeFB = (ResourceTypeFB)theEObject;
				T result = caseResourceTypeFB(resourceTypeFB);
				if (result == null) result = caseFB(resourceTypeFB);
				if (result == null) result = caseFBNetworkElement(resourceTypeFB);
				if (result == null) result = caseICallable(resourceTypeFB);
				if (result == null) result = caseTypedConfigureableObject(resourceTypeFB);
				if (result == null) result = casePositionableElement(resourceTypeFB);
				if (result == null) result = caseITypedElement(resourceTypeFB);
				if (result == null) result = caseConfigurableObject(resourceTypeFB);
				if (result == null) result = caseINamedElement(resourceTypeFB);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SEGMENT: {
				Segment segment = (Segment)theEObject;
				T result = caseSegment(segment);
				if (result == null) result = caseTypedConfigureableObject(segment);
				if (result == null) result = casePositionableElement(segment);
				if (result == null) result = caseColorizableElement(segment);
				if (result == null) result = caseIVarElement(segment);
				if (result == null) result = caseITypedElement(segment);
				if (result == null) result = caseConfigurableObject(segment);
				if (result == null) result = caseINamedElement(segment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SEGMENT_TYPE: {
				SegmentType segmentType = (SegmentType)theEObject;
				T result = caseSegmentType(segmentType);
				if (result == null) result = caseLibraryElement(segmentType);
				if (result == null) result = caseINamedElement(segmentType);
				if (result == null) result = caseConfigurableObject(segmentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SERVICE: {
				Service service = (Service)theEObject;
				T result = caseService(service);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SERVICE_SEQUENCE: {
				ServiceSequence serviceSequence = (ServiceSequence)theEObject;
				T result = caseServiceSequence(serviceSequence);
				if (result == null) result = caseINamedElement(serviceSequence);
				if (result == null) result = caseConfigurableObject(serviceSequence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SERVICE_TRANSACTION: {
				ServiceTransaction serviceTransaction = (ServiceTransaction)theEObject;
				T result = caseServiceTransaction(serviceTransaction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SERVICE_INTERFACE: {
				ServiceInterface serviceInterface = (ServiceInterface)theEObject;
				T result = caseServiceInterface(serviceInterface);
				if (result == null) result = caseINamedElement(serviceInterface);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SERVICE_INTERFACE_FB_TYPE: {
				ServiceInterfaceFBType serviceInterfaceFBType = (ServiceInterfaceFBType)theEObject;
				T result = caseServiceInterfaceFBType(serviceInterfaceFBType);
				if (result == null) result = caseFBType(serviceInterfaceFBType);
				if (result == null) result = caseLibraryElement(serviceInterfaceFBType);
				if (result == null) result = caseICallable(serviceInterfaceFBType);
				if (result == null) result = caseINamedElement(serviceInterfaceFBType);
				if (result == null) result = caseConfigurableObject(serviceInterfaceFBType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SIMPLE_EC_ACTION: {
				SimpleECAction simpleECAction = (SimpleECAction)theEObject;
				T result = caseSimpleECAction(simpleECAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SIMPLE_EC_STATE: {
				SimpleECState simpleECState = (SimpleECState)theEObject;
				T result = caseSimpleECState(simpleECState);
				if (result == null) result = caseINamedElement(simpleECState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SIMPLE_FB_TYPE: {
				SimpleFBType simpleFBType = (SimpleFBType)theEObject;
				T result = caseSimpleFBType(simpleFBType);
				if (result == null) result = caseBaseFBType(simpleFBType);
				if (result == null) result = caseFBType(simpleFBType);
				if (result == null) result = caseLibraryElement(simpleFBType);
				if (result == null) result = caseICallable(simpleFBType);
				if (result == null) result = caseINamedElement(simpleFBType);
				if (result == null) result = caseConfigurableObject(simpleFBType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ST_ALGORITHM: {
				STAlgorithm stAlgorithm = (STAlgorithm)theEObject;
				T result = caseSTAlgorithm(stAlgorithm);
				if (result == null) result = caseTextAlgorithm(stAlgorithm);
				if (result == null) result = caseAlgorithm(stAlgorithm);
				if (result == null) result = caseICallable(stAlgorithm);
				if (result == null) result = caseINamedElement(stAlgorithm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ST_FUNCTION: {
				STFunction stFunction = (STFunction)theEObject;
				T result = caseSTFunction(stFunction);
				if (result == null) result = caseTextFunction(stFunction);
				if (result == null) result = caseFunction(stFunction);
				if (result == null) result = caseICallable(stFunction);
				if (result == null) result = caseINamedElement(stFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ST_FUNCTION_BODY: {
				STFunctionBody stFunctionBody = (STFunctionBody)theEObject;
				T result = caseSTFunctionBody(stFunctionBody);
				if (result == null) result = caseTextFunctionBody(stFunctionBody);
				if (result == null) result = caseFunctionBody(stFunctionBody);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.ST_METHOD: {
				STMethod stMethod = (STMethod)theEObject;
				T result = caseSTMethod(stMethod);
				if (result == null) result = caseTextMethod(stMethod);
				if (result == null) result = caseMethod(stMethod);
				if (result == null) result = caseICallable(stMethod);
				if (result == null) result = caseINamedElement(stMethod);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SUB_APP: {
				SubApp subApp = (SubApp)theEObject;
				T result = caseSubApp(subApp);
				if (result == null) result = caseFBNetworkElement(subApp);
				if (result == null) result = caseTypedConfigureableObject(subApp);
				if (result == null) result = casePositionableElement(subApp);
				if (result == null) result = caseITypedElement(subApp);
				if (result == null) result = caseConfigurableObject(subApp);
				if (result == null) result = caseINamedElement(subApp);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.STRUCT_MANIPULATOR: {
				StructManipulator structManipulator = (StructManipulator)theEObject;
				T result = caseStructManipulator(structManipulator);
				if (result == null) result = caseConfigurableFB(structManipulator);
				if (result == null) result = caseFB(structManipulator);
				if (result == null) result = caseFBNetworkElement(structManipulator);
				if (result == null) result = caseICallable(structManipulator);
				if (result == null) result = caseTypedConfigureableObject(structManipulator);
				if (result == null) result = casePositionableElement(structManipulator);
				if (result == null) result = caseITypedElement(structManipulator);
				if (result == null) result = caseConfigurableObject(structManipulator);
				if (result == null) result = caseINamedElement(structManipulator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SUB_APP_TYPE: {
				SubAppType subAppType = (SubAppType)theEObject;
				T result = caseSubAppType(subAppType);
				if (result == null) result = caseCompositeFBType(subAppType);
				if (result == null) result = caseFBType(subAppType);
				if (result == null) result = caseLibraryElement(subAppType);
				if (result == null) result = caseICallable(subAppType);
				if (result == null) result = caseINamedElement(subAppType);
				if (result == null) result = caseConfigurableObject(subAppType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.SYSTEM_CONFIGURATION: {
				SystemConfiguration systemConfiguration = (SystemConfiguration)theEObject;
				T result = caseSystemConfiguration(systemConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.TEXT_ALGORITHM: {
				TextAlgorithm textAlgorithm = (TextAlgorithm)theEObject;
				T result = caseTextAlgorithm(textAlgorithm);
				if (result == null) result = caseAlgorithm(textAlgorithm);
				if (result == null) result = caseICallable(textAlgorithm);
				if (result == null) result = caseINamedElement(textAlgorithm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.TEXT_FUNCTION: {
				TextFunction textFunction = (TextFunction)theEObject;
				T result = caseTextFunction(textFunction);
				if (result == null) result = caseFunction(textFunction);
				if (result == null) result = caseICallable(textFunction);
				if (result == null) result = caseINamedElement(textFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.TEXT_FUNCTION_BODY: {
				TextFunctionBody textFunctionBody = (TextFunctionBody)theEObject;
				T result = caseTextFunctionBody(textFunctionBody);
				if (result == null) result = caseFunctionBody(textFunctionBody);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.TEXT_METHOD: {
				TextMethod textMethod = (TextMethod)theEObject;
				T result = caseTextMethod(textMethod);
				if (result == null) result = caseMethod(textMethod);
				if (result == null) result = caseICallable(textMethod);
				if (result == null) result = caseINamedElement(textMethod);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT: {
				TypedConfigureableObject typedConfigureableObject = (TypedConfigureableObject)theEObject;
				T result = caseTypedConfigureableObject(typedConfigureableObject);
				if (result == null) result = caseITypedElement(typedConfigureableObject);
				if (result == null) result = caseConfigurableObject(typedConfigureableObject);
				if (result == null) result = caseINamedElement(typedConfigureableObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.TYPED_SUB_APP: {
				TypedSubApp typedSubApp = (TypedSubApp)theEObject;
				T result = caseTypedSubApp(typedSubApp);
				if (result == null) result = caseSubApp(typedSubApp);
				if (result == null) result = caseFBNetworkElement(typedSubApp);
				if (result == null) result = caseTypedConfigureableObject(typedSubApp);
				if (result == null) result = casePositionableElement(typedSubApp);
				if (result == null) result = caseITypedElement(typedSubApp);
				if (result == null) result = caseConfigurableObject(typedSubApp);
				if (result == null) result = caseINamedElement(typedSubApp);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.UNTYPED_SUB_APP: {
				UntypedSubApp untypedSubApp = (UntypedSubApp)theEObject;
				T result = caseUntypedSubApp(untypedSubApp);
				if (result == null) result = caseSubApp(untypedSubApp);
				if (result == null) result = caseFBNetworkElement(untypedSubApp);
				if (result == null) result = caseTypedConfigureableObject(untypedSubApp);
				if (result == null) result = casePositionableElement(untypedSubApp);
				if (result == null) result = caseITypedElement(untypedSubApp);
				if (result == null) result = caseConfigurableObject(untypedSubApp);
				if (result == null) result = caseINamedElement(untypedSubApp);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.VALUE: {
				Value value = (Value)theEObject;
				T result = caseValue(value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.VAR_DECLARATION: {
				VarDeclaration varDeclaration = (VarDeclaration)theEObject;
				T result = caseVarDeclaration(varDeclaration);
				if (result == null) result = caseIInterfaceElement(varDeclaration);
				if (result == null) result = caseITypedElement(varDeclaration);
				if (result == null) result = caseHiddenElement(varDeclaration);
				if (result == null) result = caseINamedElement(varDeclaration);
				if (result == null) result = caseConfigurableObject(varDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.VERSION_INFO: {
				VersionInfo versionInfo = (VersionInfo)theEObject;
				T result = caseVersionInfo(versionInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LibraryElementPackage.WITH: {
				With with = (With)theEObject;
				T result = caseWith(with);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterConnection(AdapterConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterDeclaration(AdapterDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter FB</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter FB</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterFB(AdapterFB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterType(AdapterType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Algorithm</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Algorithm</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlgorithm(Algorithm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Application</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Application</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplication(Application object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Size</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Size</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArraySize(ArraySize object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttribute(Attribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeDeclaration(AttributeDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Base FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Base FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBaseFBType(BaseFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicFBType(BasicFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Automation System</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Automation System</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAutomationSystem(AutomationSystem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CFB Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CFB Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCFBInstance(CFBInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Color</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Color</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColor(Color object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Colorizable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Colorizable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColorizableElement(ColorizableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComment(Comment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Communication Channel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Communication Channel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommunicationChannel(CommunicationChannel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Communication Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Communication Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommunicationConfiguration(CommunicationConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Communication Mapping Target</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Communication Mapping Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommunicationMappingTarget(CommunicationMappingTarget object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Compiler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Compiler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompiler(org.eclipse.fordiac.ide.model.libraryElement.Compiler object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Compiler Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Compiler Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompilerInfo(CompilerInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composite FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composite FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositeFBType(CompositeFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Configurable Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Configurable Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigurableObject(ConfigurableObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Configurable FB</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Configurable FB</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigurableFB(ConfigurableFB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Configurable Move FB</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Configurable Move FB</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigurableMoveFB(ConfigurableMoveFB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnection(Connection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connection Routing Data</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connection Routing Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectionRoutingData(ConnectionRoutingData object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataConnection(DataConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Demultiplexer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Demultiplexer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDemultiplexer(Demultiplexer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Device</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Device</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDevice(Device object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Device Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Device Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeviceType(DeviceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EC Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EC Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseECAction(ECAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ECC</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ECC</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseECC(ECC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EC State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EC State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseECState(ECState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EC Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EC Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseECTransition(ECTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Marker Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Marker Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorMarkerDataType(ErrorMarkerDataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Marker FBN Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Marker FBN Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorMarkerFBNElement(ErrorMarkerFBNElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Marker Interface</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Marker Interface</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorMarkerInterface(ErrorMarkerInterface object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEvent(Event object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventConnection(EventConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFB(FB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB Network</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB Network</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFBNetwork(FBNetwork object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB Network Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB Network Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFBNetworkElement(FBNetworkElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFBType(FBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunction(Function object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Body</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Body</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionBody(FunctionBody object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionFBType(FunctionFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global Constants</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global Constants</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalConstants(GlobalConstants object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGroup(Group object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Hidden Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Hidden Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHiddenElement(HiddenElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ICallable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ICallable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseICallable(ICallable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentification(Identification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IInterface Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IInterface Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIInterfaceElement(IInterfaceElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Import</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Import</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImport(Import object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedElement(INamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Input Primitive</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Input Primitive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInputPrimitive(InputPrimitive object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Interface List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Interface List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInterfaceList(InterfaceList object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ITyped Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITyped Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITypedElement(ITypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IVar Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IVar Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIVarElement(IVarElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Library Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Library Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLibraryElement(LibraryElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLink(Link object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Local Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Local Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalVariable(LocalVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapping(Mapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mapping Target</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mapping Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMappingTarget(MappingTarget object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Method</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Method</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMethod(Method object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multiplexer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multiplexer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultiplexer(Multiplexer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Original Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Original Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOriginalSource(OriginalSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Other Algorithm</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Other Algorithm</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOtherAlgorithm(OtherAlgorithm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Other Method</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Other Method</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOtherMethod(OtherMethod object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Output Primitive</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Output Primitive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOutputPrimitive(OutputPrimitive object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Position</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Position</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePosition(Position object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Positionable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Positionable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePositionableElement(PositionableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Primitive</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Primitive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrimitive(Primitive object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResource(Resource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Type Name</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Type Name</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceTypeName(ResourceTypeName object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceType(ResourceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Type FB</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Type FB</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceTypeFB(ResourceTypeFB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Segment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Segment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSegment(Segment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Segment Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Segment Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSegmentType(SegmentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseService(Service object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service Sequence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service Sequence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceSequence(ServiceSequence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service Transaction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service Transaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceTransaction(ServiceTransaction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service Interface</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service Interface</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceInterface(ServiceInterface object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service Interface FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service Interface FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceInterfaceFBType(ServiceInterfaceFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple EC Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple EC Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleECAction(SimpleECAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple EC State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple EC State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleECState(SimpleECState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple FB Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleFBType(SimpleFBType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Algorithm</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Algorithm</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTAlgorithm(STAlgorithm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTFunction(STFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Function Body</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Function Body</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTFunctionBody(STFunctionBody object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Method</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Method</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTMethod(STMethod object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub App</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub App</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubApp(SubApp object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Struct Manipulator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Struct Manipulator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStructManipulator(StructManipulator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub App Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub App Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubAppType(SubAppType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>System Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>System Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSystemConfiguration(SystemConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Algorithm</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Algorithm</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextAlgorithm(TextAlgorithm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextFunction(TextFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Function Body</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Function Body</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextFunctionBody(TextFunctionBody object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Method</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Method</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextMethod(TextMethod object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typed Configureable Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typed Configureable Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypedConfigureableObject(TypedConfigureableObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typed Sub App</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typed Sub App</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypedSubApp(TypedSubApp object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Untyped Sub App</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Untyped Sub App</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUntypedSubApp(UntypedSubApp object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValue(Value object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Var Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Var Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVarDeclaration(VarDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionInfo(VersionInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>With</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>With</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWith(With object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Member Var Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Member Var Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMemberVarDeclaration(MemberVarDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataType(DataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //LibraryElementSwitch
