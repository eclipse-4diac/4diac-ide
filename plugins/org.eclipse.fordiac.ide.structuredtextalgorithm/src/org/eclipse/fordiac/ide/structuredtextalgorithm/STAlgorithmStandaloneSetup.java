/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm;

import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage;
import org.eclipse.fordiac.ide.structuredtextcore.STCoreStandaloneSetup;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without Equinox extension
 * registry.
 */
public class STAlgorithmStandaloneSetup extends STAlgorithmStandaloneSetupGenerated {

	public static void doSetup() {
		new STAlgorithmStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	@Override
	public void register(final Injector injector) {
		STAlgorithmPackage.eINSTANCE.eClass();
		super.register(injector);
		STCoreStandaloneSetup.registerFileExtensions(injector, TypeLibraryTags.FB_TYPE_FILE_ENDING,
				TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING, TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING,
				TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING, TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING,
				TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING, TypeLibraryTags.DEVICE_TYPE_FILE_ENDING,
				TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING, TypeLibraryTags.DATA_TYPE_FILE_ENDING);
	}
}
