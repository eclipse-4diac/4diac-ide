/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians - added internalFbsSection to Simple FB
 *   Alois Zoitl             - reworked to better handled selection
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb.properties;

import org.eclipse.fordiac.ide.gef.properties.InternalFbsSection;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.gef.EditPart;

public class InternalFbsSectionSimple extends InternalFbsSection {
	@Override
	protected Object getInputType(Object input) {
		if (input instanceof EditPart) {
			input = ((EditPart) input).getModel();
		}
		if (input instanceof BaseFBType) {
			return input;
		}
		return null;
	}
}
