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
 * Coordinates of the shared FBTypeDeleteNestedSubAppTest fixture under data,
 * where MyBlock is instanced at three nesting levels, referenced by the FB type
 * delete and rename tests.
 */
public final class NestedSubAppTestFixture {

	public static final String PROJECT_NAME = "FBTypeDeleteNestedSubAppTest"; //$NON-NLS-1$
	public static final String PROJECT_PATH = "data/FBTypeDeleteNestedSubAppTest"; //$NON-NLS-1$
	public static final String SYSTEM_FILE = "FBTypeDeleteNestedSubAppTest.sys"; //$NON-NLS-1$
	public static final String APPLICATION_NAME = "App"; //$NON-NLS-1$

	public static final String MY_BLOCK_FILE = "Type Library/mypackage/MyBlock.fbt"; //$NON-NLS-1$
	public static final String MY_BLOCK = "mypackage::MyBlock"; //$NON-NLS-1$

	public static final String TOP_INSTANCE = "TopInstance"; //$NON-NLS-1$
	public static final String CONTAINER_SUBAPP = "Container"; //$NON-NLS-1$
	public static final String NESTED_INSTANCE = "NestedInstance"; //$NON-NLS-1$
	public static final String DEEP_CONTAINER_SUBAPP = "DeepContainer"; //$NON-NLS-1$
	public static final String DEEPLY_NESTED_INSTANCE = "DeeplyNestedInstance"; //$NON-NLS-1$
	public static final String TOP_INSTANCE_SINK = "TopInstanceSink"; //$NON-NLS-1$
	public static final String NESTED_INSTANCE_SINK = "NestedInstanceSink"; //$NON-NLS-1$
	public static final String DEEPLY_NESTED_INSTANCE_SINK = "DeeplyNestedInstanceSink"; //$NON-NLS-1$

	private NestedSubAppTestFixture() {
		throw new UnsupportedOperationException();
	}
}
