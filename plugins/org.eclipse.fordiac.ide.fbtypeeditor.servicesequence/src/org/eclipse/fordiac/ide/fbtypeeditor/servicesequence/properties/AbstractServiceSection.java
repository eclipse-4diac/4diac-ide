/*******************************************************************************
 * Copyright (c) 2014 - 2015 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - cleaned command stack handling for property sections
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;

public abstract class AbstractServiceSection extends AbstractSection {

	protected boolean isLeftInterface(final Primitive primitive) {
		if (null != primitive) {
			return primitive.getInterface().isLeftInterface();
		}
		return false;
	}
}
