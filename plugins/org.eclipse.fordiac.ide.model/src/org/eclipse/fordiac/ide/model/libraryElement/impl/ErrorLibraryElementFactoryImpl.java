/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.ErrorDataTypeFactory;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorAdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorAttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorAutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorDeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorFunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorGlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorLibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorLibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorSegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorSubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

public class ErrorLibraryElementFactoryImpl implements ErrorLibraryElementFactory {

	private final LibraryElementFactory delegate;

	public ErrorLibraryElementFactoryImpl() {
		this(LibraryElementFactory.eINSTANCE);
	}

	public ErrorLibraryElementFactoryImpl(final LibraryElementFactory delegate) {
		this.delegate = delegate;
	}

	@Override
	public ErrorLibraryElement create(final String fullTypeName, final EClass eClass) {
		if (eClass.getEPackage() == DataPackage.eINSTANCE) {
			return ErrorDataTypeFactory.INSTANCE.create(fullTypeName, eClass);
		}
		if (eClass.getEPackage() != LibraryElementPackage.eINSTANCE) {
			throw new IllegalArgumentException("Not a valid class " + eClass); //$NON-NLS-1$
		}
		return switch (eClass.getClassifierID()) {
		case LibraryElementPackage.ADAPTER_TYPE -> createErrorAdapterType(fullTypeName);
		case LibraryElementPackage.ATTRIBUTE_DECLARATION -> createErrorAttributeDeclaration(fullTypeName);
		case LibraryElementPackage.AUTOMATION_SYSTEM -> createErrorAutomationSystem(fullTypeName);
		case LibraryElementPackage.BASE_FB_TYPE -> createErrorFBType(fullTypeName);
		case LibraryElementPackage.BASIC_FB_TYPE -> createErrorFBType(fullTypeName);
		case LibraryElementPackage.COMPOSITE_FB_TYPE -> createErrorFBType(fullTypeName);
		case LibraryElementPackage.DEVICE_TYPE -> createErrorDeviceType(fullTypeName);
		case LibraryElementPackage.ERROR_ADAPTER_TYPE -> createErrorAdapterType(fullTypeName);
		case LibraryElementPackage.ERROR_ATTRIBUTE_DECLARATION -> createErrorAttributeDeclaration(fullTypeName);
		case LibraryElementPackage.ERROR_AUTOMATION_SYSTEM -> createErrorAutomationSystem(fullTypeName);
		case LibraryElementPackage.ERROR_DEVICE_TYPE -> createErrorDeviceType(fullTypeName);
		case LibraryElementPackage.ERROR_FB_TYPE -> createErrorFBType(fullTypeName);
		case LibraryElementPackage.ERROR_FUNCTION_FB_TYPE -> createErrorFunctionFBType(fullTypeName);
		case LibraryElementPackage.ERROR_GLOBAL_CONSTANTS -> createErrorGlobalConstants(fullTypeName);
		case LibraryElementPackage.ERROR_LIBRARY_ELEMENT -> createErrorLibraryElement(fullTypeName);
		case LibraryElementPackage.ERROR_RESOURCE_TYPE -> createErrorResourceType(fullTypeName);
		case LibraryElementPackage.ERROR_SEGMENT_TYPE -> createErrorSegmentType(fullTypeName);
		case LibraryElementPackage.ERROR_SUB_APP_TYPE -> createErrorSubAppType(fullTypeName);
		case LibraryElementPackage.FB_TYPE -> createErrorFBType(fullTypeName);
		case LibraryElementPackage.FUNCTION_FB_TYPE -> createErrorFunctionFBType(fullTypeName);
		case LibraryElementPackage.GLOBAL_CONSTANTS -> createErrorGlobalConstants(fullTypeName);
		case LibraryElementPackage.LIBRARY_ELEMENT -> createErrorLibraryElement(fullTypeName);
		case LibraryElementPackage.RESOURCE_TYPE -> createErrorResourceType(fullTypeName);
		case LibraryElementPackage.SEGMENT_TYPE -> createErrorSegmentType(fullTypeName);
		case LibraryElementPackage.SERVICE_INTERFACE_FB_TYPE -> createErrorFBType(fullTypeName);
		case LibraryElementPackage.SIMPLE_FB_TYPE -> createErrorFBType(fullTypeName);
		case LibraryElementPackage.SUB_APP_TYPE -> createErrorSubAppType(fullTypeName);
		default -> throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		};
	}

	@Override
	public ErrorAdapterType createErrorAdapterType(final String fullTypeName) {
		final ErrorAdapterType errorAdapterType = delegate.createErrorAdapterType();
		errorAdapterType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		PackageNameHelper.setFullTypeName(errorAdapterType, fullTypeName);
		return errorAdapterType;
	}

	@Override
	public ErrorAttributeDeclaration createErrorAttributeDeclaration(final String fullTypeName) {
		final ErrorAttributeDeclaration errorAttributeDeclaration = delegate.createErrorAttributeDeclaration();
		final DirectlyDerivedType dataType = DataFactory.eINSTANCE.createDirectlyDerivedType();
		dataType.setName(PackageNameHelper.extractPlainTypeName(fullTypeName));
		dataType.setBaseType(ElementaryTypes.STRING);
		errorAttributeDeclaration.setType(dataType);
		PackageNameHelper.setFullTypeName(errorAttributeDeclaration, fullTypeName);
		return errorAttributeDeclaration;
	}

	@Override
	public ErrorAutomationSystem createErrorAutomationSystem(final String fullTypeName) {
		final ErrorAutomationSystem errorAutomationSystem = delegate.createErrorAutomationSystem();
		errorAutomationSystem.setSystemConfiguration(LibraryElementFactory.eINSTANCE.createSystemConfiguration());
		PackageNameHelper.setFullTypeName(errorAutomationSystem, fullTypeName);
		return errorAutomationSystem;
	}

	@Override
	public ErrorDeviceType createErrorDeviceType(final String fullTypeName) {
		final ErrorDeviceType errorDeviceType = delegate.createErrorDeviceType();
		PackageNameHelper.setFullTypeName(errorDeviceType, fullTypeName);
		return errorDeviceType;
	}

	@Override
	public ErrorFBType createErrorFBType(final String fullTypeName) {
		final ErrorFBType errorFBType = delegate.createErrorFBType();
		errorFBType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		PackageNameHelper.setFullTypeName(errorFBType, fullTypeName);
		return errorFBType;
	}

	@Override
	public ErrorFunctionFBType createErrorFunctionFBType(final String fullTypeName) {
		final ErrorFunctionFBType errorFunctionFBType = delegate.createErrorFunctionFBType();
		errorFunctionFBType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		PackageNameHelper.setFullTypeName(errorFunctionFBType, fullTypeName);
		return errorFunctionFBType;
	}

	@Override
	public ErrorGlobalConstants createErrorGlobalConstants(final String fullTypeName) {
		final ErrorGlobalConstants errorGlobalConstants = delegate.createErrorGlobalConstants();
		PackageNameHelper.setFullTypeName(errorGlobalConstants, fullTypeName);
		return errorGlobalConstants;
	}

	@Override
	public ErrorLibraryElement createErrorLibraryElement(final String fullTypeName) {
		final ErrorLibraryElement errorLibraryElement = delegate.createErrorLibraryElement();
		PackageNameHelper.setFullTypeName(errorLibraryElement, fullTypeName);
		return errorLibraryElement;
	}

	@Override
	public ErrorResourceType createErrorResourceType(final String fullTypeName) {
		final ErrorResourceType errorResourceType = delegate.createErrorResourceType();
		PackageNameHelper.setFullTypeName(errorResourceType, fullTypeName);
		return errorResourceType;
	}

	@Override
	public ErrorSegmentType createErrorSegmentType(final String fullTypeName) {
		final ErrorSegmentType errorSegmentType = delegate.createErrorSegmentType();
		PackageNameHelper.setFullTypeName(errorSegmentType, fullTypeName);
		return errorSegmentType;
	}

	@Override
	public ErrorSubAppType createErrorSubAppType(final String fullTypeName) {
		final ErrorSubAppType errorSubAppType = delegate.createErrorSubAppType();
		errorSubAppType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		errorSubAppType.setFBNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		PackageNameHelper.setFullTypeName(errorSubAppType, fullTypeName);
		return errorSubAppType;
	}
}
