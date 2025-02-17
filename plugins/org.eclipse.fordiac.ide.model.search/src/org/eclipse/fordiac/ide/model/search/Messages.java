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
 *   Dunja Životin
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import org.eclipse.osgi.util.NLS;

/** The Class Messages. */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "plugin"; //$NON-NLS-1$
	public static String InstanceName;
	public static String SearchHeaderName;
	public static String ProjectScope;
	public static String SearchFactoryRegistryReader_AlreadyRegistered;
	public static String SearchFactoryRegistryReader_CannotCreateInstance;
	public static String SearchFactoryRegistryReader_LogMessage;
	public static String SearchFactoryRegistryReader_SourceInvalidClass;
	public static String SearchFor;
	public static String PinName;
	public static String Comment;
	public static String ContainingText;
	public static String TextMatch_Location;
	public static String TypeQuery;
	public static String Jokers;
	public static String CaseSensitive;
	public static String ExactNameMatching;
	public static String Scope;
	public static String WorkspaceScope;
	public static String Warning;
	public static String ErrorMessageSearch;
	public static String Element;
	public static String Location;
	public static String SearchForTypeReferences;
	public static String STSearchErrorDialog_Title;
	public static String STSearchErrorDialog_Body;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
