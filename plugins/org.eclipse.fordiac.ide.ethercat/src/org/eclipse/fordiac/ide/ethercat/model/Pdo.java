/*******************************************************************************
 * Copyright (c) 2026 Sichuan Qunyuan Technology Co., Ltd.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Zijun Tang - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ethercat.model;

import java.util.ArrayList;

public class Pdo {
	public String name;
	public PdoType pdoType;
	public ArrayList<PdoEntry> pdoEntries;

	public enum PdoType {
		RxPdo,
		TxPdo
	}

	public Pdo(final String name, final PdoType pdoType) {
		this.name = name;
		this.pdoType = pdoType;
		pdoEntries = new ArrayList<>();
	}

	public void addEntry(final PdoEntry entry) {
		pdoEntries.add(entry);
	}
}
