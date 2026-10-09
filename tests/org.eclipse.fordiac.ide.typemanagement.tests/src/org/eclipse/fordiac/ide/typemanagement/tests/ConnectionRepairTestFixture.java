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
 * Coordinates of the ConnectionRepairTest fixture under data: a system whose
 * single data connection references a struct member that the struct no longer
 * declares, so it loads with a broken (error-marker) source pin to repair.
 */
public final class ConnectionRepairTestFixture {

	public static final String PROJECT_NAME = "ConnectionRepairTest"; //$NON-NLS-1$
	public static final String PROJECT_PATH = "data/ConnectionRepairTest"; //$NON-NLS-1$
	public static final String SYSTEM_FILE = "ConnectionRepairTest.sys"; //$NON-NLS-1$
	public static final String APPLICATION_NAME = "App"; //$NON-NLS-1$

	public static final String REPAIR_STRUCT = "mypackage::RepairStruct"; //$NON-NLS-1$
	/** Surviving struct member the repair routes the connection through. */
	public static final String REPAIR_STRUCT_MEMBER = "keep"; //$NON-NLS-1$
	public static final String STRUCT_DEMUX_TYPE = "STRUCT_DEMUX"; //$NON-NLS-1$

	public static final String PRODUCER_INSTANCE = "Producer"; //$NON-NLS-1$
	public static final String CONSUMER_INSTANCE = "Consumer"; //$NON-NLS-1$
	public static final String CONSUMER_INPUT_PIN = "DI"; //$NON-NLS-1$

	private ConnectionRepairTestFixture() {
		throw new UnsupportedOperationException();
	}
}
