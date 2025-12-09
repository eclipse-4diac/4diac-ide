/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class CommentSectionFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return (commentFromSelectedObject(toTest) != null);
	}

	static Comment commentFromSelectedObject(final Object selObj) {
		Object retval = selObj;
		if (retval instanceof EditPart) {
			retval = ((EditPart) retval).getModel();
		}

		if (retval instanceof Comment) {
			return (Comment) retval;
		}
		return null;
	}

}
