/*******************************************************************************
 * Copyright (c) 2020 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl
 *     - Externalized all translatable strings
 *******************************************************************************/

package org.eclipse.fordiac.ide.export;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.export.messages"; //$NON-NLS-1$

	public static String ExportTemplate_ExportTemplate;

	public static String TemplateExportFilter_CANCEL_ALL_LABEL_STRING;

	public static String TemplateExportFilter_ErrorDuringTemplateGeneration;

	public static String TemplateExportFilter_EXPORT_CANCELED;

	public static String TemplateExportFilter_FILE_EXISTS;

	public static String TemplateExportFilter_FILE_IGNORED;

	public static String TemplateExportFilter_LIST_FOUR_OR_MORE_ELEMENTS;

	public static String TemplateExportFilter_LIST_ONE_ELEMENT;

	public static String TemplateExportFilter_LIST_THREE_ELEMENTS;

	public static String TemplateExportFilter_LIST_TWO_ELEMENTS;

	public static String TemplateExportFilter_MERGE_EDITOR_FAILED;

	public static String TemplateExportFilter_MERGE_LABEL_STRING;

	public static String TemplateExportFilter_OVERWRITE_LABEL_STRING;

	public static String TemplateExportFilter_OVERWRITE_REQUEST;

	public static String TemplateExportFilter_PREFIX_ERRORMESSAGE_WITH_TYPENAME;

	public static String TemplateExportFilter_OVERWRITE_ALL_LABEL_STRING;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
