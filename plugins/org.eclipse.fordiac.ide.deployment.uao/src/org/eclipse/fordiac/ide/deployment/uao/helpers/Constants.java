/*******************************************************************************
 * Copyright (c) 2024 AIMIRIM STI - https://en.aimirimsti.com.br/
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pedro Ricardo
 *   Felipe Adriano
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.uao.helpers;

import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;

public class Constants {

	public static final Response EMPTY_RESPONSE;
	public static final String FB_NAME_FORMAT = "{0}{1}"; //$NON-NLS-1$

	static {
		// ensure that all entries in the empty response return appropriate empty values
		EMPTY_RESPONSE = DevResponseFactory.eINSTANCE.createResponse();
		EMPTY_RESPONSE.setFblist(DevResponseFactory.eINSTANCE.createFBList());
		EMPTY_RESPONSE.setID("0"); //$NON-NLS-1$
		EMPTY_RESPONSE.setWatches(DevResponseFactory.eINSTANCE.createWatches());
	}

	private Constants() {
		// empty private constructor
	}
}
