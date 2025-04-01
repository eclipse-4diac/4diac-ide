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

	public static String BulkEditor;

	public static String Variable;
	public static String Attribute;

	public static String Name;
	public static String Type;
	public static String Comment;
	public static String InitialValue;

	public static String CaseSensitve;
	public static String WholeWord;
	public static String ExactMatch;
	public static String RegularExpression;

	public static String SearchFor;
	public static String SearchWhere;
	public static String SearchIn;

	public static String FBandSubappTypes;
	public static String FBandSubappInstances;
	public static String UntypedSubapps;
	public static String DataTypes;
	public static String AttributeTypes;

	public static String Scope;
	public static String Project;
	public static String Workspace;

	public static String Search;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
