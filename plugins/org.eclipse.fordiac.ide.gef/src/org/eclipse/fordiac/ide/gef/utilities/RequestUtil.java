/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.utilities;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

public final class RequestUtil {

	public static boolean isMoveRequest(final Request request) {
		final Object type = request.getType();
		return RequestConstants.REQ_MOVE.equals(type) || RequestConstants.REQ_MOVE_CHILDREN.equals(type)
				|| RequestConstants.REQ_ALIGN_CHILDREN.equals(type);
	}

	public static boolean isResizeRequest(final Request request) {
		final Object type = request.getType();
		return RequestConstants.REQ_RESIZE.equals(type) || RequestConstants.REQ_RESIZE_CHILDREN.equals(type);
	}

	public static boolean isAlignmentRequest(final Request request) {
		return request.getType() == RequestConstants.REQ_ALIGN_CHILDREN;
	}

	private RequestUtil() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}
}
