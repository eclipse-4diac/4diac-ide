/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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

import org.eclipse.fordiac.ide.application.commands.CutAndPasteFromSubAppCommand;
import org.eclipse.gef.commands.CompoundCommand;

public class CopyPasteMessage {

	private CopyStatus copyStatus;

	private final List<Object> data;
	private CutAndPasteFromSubAppCommand cutAndPasteFromSubAppCommandos;
	private CompoundCommand deleteCommandos;

	public enum CopyStatus {
		CUT_FROM_SUBAPP, COPY, CUT
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

	public boolean isCutFromSubApp() {
		return getCopyStatus() == CopyStatus.CUT_FROM_SUBAPP && cutAndPasteFromSubAppCommandos != null;
	}

	public CutAndPasteFromSubAppCommand getCutAndPasteFromSubAppCommandos() {
		return cutAndPasteFromSubAppCommandos;
	}

	public void setCutAndPasteFromSubAppCommandos(final CutAndPasteFromSubAppCommand cutAndPasteFromSubAppCommandos) {
		this.cutAndPasteFromSubAppCommandos = cutAndPasteFromSubAppCommandos;
	}

	public CompoundCommand getDeleteCommandos() {
		return deleteCommandos;
	}

	public void setDeleteCommandos(final CompoundCommand deleteCommandos) {
		this.deleteCommandos = deleteCommandos;
	}

}
