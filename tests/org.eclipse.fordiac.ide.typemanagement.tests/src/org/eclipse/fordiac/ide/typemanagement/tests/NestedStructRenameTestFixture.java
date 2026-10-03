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
 * Coordinates of the NestedStructRenameTest fixture under data: a three-level
 * Root -> Middle -> Leaf struct hierarchy with a connected, expanded Root-typed
 * instance, referenced by the nested struct rename test.
 */
public final class NestedStructRenameTestFixture {

	public static final String PROJECT_NAME = "NestedStructRenameTest"; //$NON-NLS-1$
	public static final String PROJECT_PATH = "data/NestedStructRenameTest"; //$NON-NLS-1$
	public static final String SYSTEM_FILE = "NestedStructRenameTest.sys"; //$NON-NLS-1$
	public static final String APPLICATION_NAME = "App"; //$NON-NLS-1$

	public static final String LEAF_STRUCT_FILE = "Type Library/mypackage/Leaf.dtp"; //$NON-NLS-1$
	public static final String MIDDLE_STRUCT_FILE = "Type Library/mypackage/Middle.dtp"; //$NON-NLS-1$
	public static final String ROOT_STRUCT_FILE = "Type Library/mypackage/Root.dtp"; //$NON-NLS-1$

	public static final String LEAF_STRUCT = "mypackage::Leaf"; //$NON-NLS-1$
	public static final String LEAF_STRUCT_RENAMED = "mypackage::LeafRenamed"; //$NON-NLS-1$
	public static final String MIDDLE_STRUCT = "mypackage::Middle"; //$NON-NLS-1$
	public static final String ROOT_STRUCT = "mypackage::Root"; //$NON-NLS-1$

	/** Member of Root typed Middle. */
	public static final String ROOT_MIDDLE_MEMBER = "middle"; //$NON-NLS-1$
	/** Member of Middle typed Leaf. */
	public static final String MIDDLE_LEAF_MEMBER = "leaf"; //$NON-NLS-1$

	public static final String PRODUCER_TYPE = "RootProducer"; //$NON-NLS-1$
	public static final String PRODUCER_INSTANCE = "Producer"; //$NON-NLS-1$
	public static final String PRODUCER_OUT_PIN = "OUT"; //$NON-NLS-1$

	private NestedStructRenameTestFixture() {
		throw new UnsupportedOperationException();
	}
}
