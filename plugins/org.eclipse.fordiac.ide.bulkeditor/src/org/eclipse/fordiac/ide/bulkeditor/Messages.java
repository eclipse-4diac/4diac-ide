/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "plugin"; //$NON-NLS-1$

	public static String BulkEditor_ProblemOpeningSearchResult;

	public static String Name;
	public static String Type;
	public static String Comment;
	public static String InitialValue;

	public static String CaseSensitive;
	public static String WholeWord;
	public static String ExactMatch;
	public static String RegularExpression;

	public static String Search;
	public static String Load;
	public static String SaveQuery;
	public static String LoadQuery;
	public static String QueryFileFilterName;

	public static String AddChild;
	public static String RemoveChild;

	public static String Search_Changes;
	public static String NoUsage;
	public static String Unsaved_Changes;
	public static String Save;
	public static String Discard;
	public static String Cancel;

	public static String AddElementDialog_InvalidType;
	public static String AddElementDialog_InvalidSelection;
	public static String AddElementDialog_EmptyName;
	public static String AddElementDialog_DuplicateName;

	public static String Tab_Result;
	public static String Tab_Query;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
