/*******************************************************************************
 * Copyright (c) 2026 Dimitrios Kalligaridis
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dimitrios Kalligaridis - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.tests;

/**
 * Coordinates of the shared PackageRenameTest fixture under data, where
 * mypackage holds a struct and a connected producer/consumer FB type, and
 * ControlBlock sits outside mypackage, referenced by the package folder
 * rename test.
 */
public final class PackageRenameTestFixture {

	public static final String PROJECT_NAME = "PackageRenameTest"; //$NON-NLS-1$
	public static final String PROJECT_PATH = "data/PackageRenameTest"; //$NON-NLS-1$
	public static final String SYSTEM_FILE = "PackageRenameTest.sys"; //$NON-NLS-1$
	public static final String APPLICATION_NAME = "App"; //$NON-NLS-1$

	public static final String PACKAGE_FOLDER = "Type Library/mypackage"; //$NON-NLS-1$
	public static final String RENAMED_PACKAGE_NAME = "renamedpackage"; //$NON-NLS-1$
	public static final String RENAMED_PACKAGE_FOLDER = "Type Library/renamedpackage"; //$NON-NLS-1$

	public static final String MY_STRUCT_FILE = "Type Library/mypackage/MyStruct.dtp"; //$NON-NLS-1$
	public static final String MY_STRUCT_RENAMED_FILE = "Type Library/renamedpackage/MyStruct.dtp"; //$NON-NLS-1$
	public static final String MY_STRUCT = "mypackage::MyStruct"; //$NON-NLS-1$
	public static final String MY_STRUCT_RENAMED = "renamedpackage::MyStruct"; //$NON-NLS-1$

	public static final String PRODUCER_FILE = "Type Library/mypackage/Producer.fbt"; //$NON-NLS-1$
	public static final String PRODUCER_RENAMED_FILE = "Type Library/renamedpackage/Producer.fbt"; //$NON-NLS-1$
	public static final String PRODUCER_TYPE = "mypackage::Producer"; //$NON-NLS-1$
	public static final String PRODUCER_TYPE_RENAMED = "renamedpackage::Producer"; //$NON-NLS-1$
	public static final String PRODUCER_INSTANCE = "Producer"; //$NON-NLS-1$

	public static final String CONSUMER_FILE = "Type Library/mypackage/Consumer.fbt"; //$NON-NLS-1$
	public static final String CONSUMER_RENAMED_FILE = "Type Library/renamedpackage/Consumer.fbt"; //$NON-NLS-1$
	public static final String CONSUMER_TYPE = "mypackage::Consumer"; //$NON-NLS-1$
	public static final String CONSUMER_TYPE_RENAMED = "renamedpackage::Consumer"; //$NON-NLS-1$
	public static final String CONSUMER_INSTANCE = "Consumer"; //$NON-NLS-1$

	// Interface pins typed with MyStruct, so a package rename has to repoint their data type.
	public static final String PRODUCER_OUT_PIN = "OUT"; //$NON-NLS-1$
	public static final String CONSUMER_DI_PIN = "DI"; //$NON-NLS-1$
	public static final String CONSUMER_DO1_PIN = "DO1"; //$NON-NLS-1$

	public static final String OUTSIDE_TYPE_FILE = "Type Library/ControlBlock.fbt"; //$NON-NLS-1$
	public static final String OUTSIDE_TYPE = "ControlBlock"; //$NON-NLS-1$

	private PackageRenameTestFixture() {
		throw new UnsupportedOperationException();
	}
}
