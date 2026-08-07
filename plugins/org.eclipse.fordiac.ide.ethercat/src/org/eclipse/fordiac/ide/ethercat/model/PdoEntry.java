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
 *   Sichuan Qunyuan Technology Co., Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ethercat.model;

public class PdoEntry {
	public String name;
	public String comment;

	public PdoEntry(final String name, final String comment) {
		this.name = name;
		this.comment = comment;
	}
}
