/*******************************************************************************
 * Copyright (c) 2011, 2012 Profactor GbmH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Hofmann, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.dnd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.swt.graphics.Image;

public class TransferDataSelectionOfFb {
	private FBTypePaletteEntry typePaletteEntry;
	private String fbTypeName;
	private String selectionLabel;
	private Image selectionImage;

	private List<TransferDataSelectionFBParameter> fbParameters;

	public String getFbTypeName() {
		return fbTypeName;
	}

	public void setFbTypeName(final String fbTypeName) {
		this.fbTypeName = fbTypeName;
	}

	public FBTypePaletteEntry getTypePaletteEntry() {
		return typePaletteEntry;
	}

	public void setTypePaletteEntry(final FBTypePaletteEntry typePaletteEntry) {
		this.typePaletteEntry = typePaletteEntry;
	}

	public String getSelectionLabel() {
		return selectionLabel;
	}

	public void setSelectionLabel(final String selectionLabel) {
		this.selectionLabel = selectionLabel;
	}

	public Image getSelectionImage() {
		return selectionImage;
	}

	public void setSelectionImage(final Image selectionImage) {
		this.selectionImage = selectionImage;
	}

	public List<TransferDataSelectionFBParameter> getFbParameters() {
		return fbParameters;
	}

	public void setFbParameters(final List<TransferDataSelectionFBParameter> fbParameters) {
		this.fbParameters = fbParameters;
	}

	public TransferDataSelectionOfFb() {
		typePaletteEntry = null;
		selectionLabel = null;
		selectionImage = null;
		fbParameters = new ArrayList<>();
	}
}
