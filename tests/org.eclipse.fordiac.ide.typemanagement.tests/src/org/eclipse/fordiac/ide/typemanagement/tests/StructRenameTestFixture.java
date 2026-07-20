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
 * Coordinates of the shared StructRenameTest fixture under data, referenced by
 * the struct rename, cascade and delete tests.
 */
public final class StructRenameTestFixture {

	public static final String PROJECT_NAME = "StructRenameTest"; //$NON-NLS-1$
	public static final String PROJECT_PATH = "data/StructRenameTest"; //$NON-NLS-1$
	public static final String SYSTEM_FILE = "StructRenameTest.sys"; //$NON-NLS-1$
	public static final String APPLICATION_NAME = "App"; //$NON-NLS-1$

	public static final String INNER_STRUCT_FILE = "Type Library/mypackage/InnerStruct.dtp"; //$NON-NLS-1$
	public static final String INNER_STRUCT = "mypackage::InnerStruct"; //$NON-NLS-1$
	public static final String INNER_STRUCT_RENAMED = "mypackage::InnerStructRenamed"; //$NON-NLS-1$
	public static final String OUTER_STRUCT = "mypackage::OuterStruct"; //$NON-NLS-1$

	public static final String PRODUCER_TYPE = "StructProducer"; //$NON-NLS-1$
	public static final String PRODUCER_OUT_PIN = "OUT"; //$NON-NLS-1$

	private StructRenameTestFixture() {
		throw new UnsupportedOperationException();
	}
}
