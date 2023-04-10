/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.List;

public class CopyPasteMessage {

	private CopyStatus copyStatus;

	private final List<Object> data;

	public enum CopyStatus {
		COPY, CUT_FROM_ROOT, CUT_PASTED
	}

	public CopyPasteMessage(final CopyStatus copyInfo, final List<Object> templates) {
		this.copyStatus = copyInfo;
		this.data = templates;
	}

	public CopyStatus getCopyStatus() {
		return copyStatus;
	}

	public List<Object> getData() {
		return data;
	}

	public void setCopyInfo(final CopyStatus copyInfo) {
		this.copyStatus = copyInfo;
	}

}
