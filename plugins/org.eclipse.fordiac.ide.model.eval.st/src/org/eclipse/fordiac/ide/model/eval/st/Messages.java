/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.st;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008")
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.model.eval.st.messages"; //$NON-NLS-1$
	public static String STCallableEvaluator_InterruptedInInitializer;
	public static String STCallableEvaluator_MissingArgument;
	public static String StructuredTextEvaluator_CannotCreateEvaluator;
	public static String StructuredTextEvaluator_DeclarationNotAConstant;
	public static String StructuredTextEvaluator_ElementNotSupported;
	public static String StructuredTextEvaluator_FeatureNotSupported;
	public static String StructuredTextEvaluator_NoSuchTypeEvent;
	public static String StructuredTextEvaluator_RangeNotSupported;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
