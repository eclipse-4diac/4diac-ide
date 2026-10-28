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
	public static String BulkEditor_QueryName;
	public static String BulkEditor_QuerySaveFailed;
	public static String QueryFileLauncher_NotInProject;

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
	public static String QueryFileExists;

	public static String AddChild;
	public static String RemoveChild;

	public static String Search_Changes;
	public static String NoUsage;
	public static String Save;

	public static String AddElementDialog_InvalidType;
	public static String AddElementDialog_InvalidSelection;
	public static String AddElementDialog_EmptyName;
	public static String AddElementDialog_DuplicateName;

	public static String Tab_Result;
	public static String Tab_Query;

	public static String Negate;

	public static String QueryMenu_AddField;
	public static String QueryMenu_RemoveField;
	public static String QueryMenu_AddConstraint;
	public static String QueryMenu_AddAndConstraint;
	public static String QueryMenu_AddOrConstraint;
	public static String QueryMenu_AddInstance;
	public static String QueryMenu_AddType;
	public static String QueryMenu_AddOccurrence;

	public static String QueryMenu_Name;
	public static String QueryMenu_Type;
	public static String QueryMenu_Comment;
	public static String QueryMenu_Value;
	public static String QueryMenu_Constraint;
	public static String QueryMenu_AttributeConstraint;
	public static String QueryMenu_SimpleFB;
	public static String QueryMenu_BasicFB;
	public static String QueryMenu_CompositeFB;
	public static String QueryMenu_ServiceInterfaceFB;
	public static String QueryMenu_FunctionFB;
	public static String QueryMenu_TypedSubapp;
	public static String QueryMenu_UntypedSubapp;
	public static String QueryMenu_Connection;
	public static String QueryMenu_SimpleType;
	public static String QueryMenu_BasicType;
	public static String QueryMenu_CompositeType;
	public static String QueryMenu_ServiceInterfaceType;
	public static String QueryMenu_FunctionType;
	public static String QueryMenu_SubappType;
	public static String QueryMenu_StructType;
	public static String QueryMenu_AttributeType;
	public static String QueryMenu_ApplicationOccurrence;
	public static String QueryMenu_CompositeFBOccurrence;
	public static String QueryMenu_TypedSubappOccurrence;
	public static String QueryMenu_Pin;
	public static String QueryMenu_Placeholder;
	public static String QueryMenu_PinTarget;
	public static String QueryMenu_Attribute;
	public static String QueryMenu_AttributeDeclaration;

	public static String QueryScope_Project;
	public static String QueryScope_SubappHierarchy;
	public static String QueryScope_SelectSubappHierarchy;
	public static String QueryScope_IgnoreLinkedLibraries;
	public static String QueryScope_NoSubappHierarchy;
	public static String QueryScope_MissingElement;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
