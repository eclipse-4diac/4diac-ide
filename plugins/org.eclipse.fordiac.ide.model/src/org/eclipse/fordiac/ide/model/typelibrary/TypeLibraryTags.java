/********************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Waldemar Eisenmenger
 *    - initial API and implementation and/or initial documentation
 *  Daniel Lindhuber - Added system type file ending
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

public final class TypeLibraryTags {

	/** The Constant for the file ending of FB type files. */
	public static final String FB_TYPE_FILE_ENDING = "FBT"; //$NON-NLS-1$

	public static final String FB_TYPE_FILE_ENDING_WITH_DOT = "." + FB_TYPE_FILE_ENDING; //$NON-NLS-1$

	public static final String ADAPTER_TYPE_FILE_ENDING = "ADP"; //$NON-NLS-1$

	public static final String ADAPTER_TYPE_FILE_ENDING_WITH_DOT = "." + ADAPTER_TYPE_FILE_ENDING; //$NON-NLS-1$

	public static final String ATTRIBUTE_TYPE_FILE_ENDING = "ATP"; //$NON-NLS-1$

	public static final String ATTRIBUTE_TYPE_FILE_ENDING_WITH_DOT = "." + ATTRIBUTE_TYPE_FILE_ENDING; //$NON-NLS-1$

	public static final String DEVICE_TYPE_FILE_ENDING = "DEV"; //$NON-NLS-1$

	public static final String DEVICE_TYPE_FILE_ENDING_WITH_DOT = "." + DEVICE_TYPE_FILE_ENDING; //$NON-NLS-1$

	public static final String DATA_TYPE_FILE_ENDING = "DTP"; //$NON-NLS-1$

	public static final String DATA_TYPE_FILE_ENDING_WITH_DOT = "." + DATA_TYPE_FILE_ENDING; //$NON-NLS-1$

	public static final String RESOURCE_TYPE_FILE_ENDING = "RES"; //$NON-NLS-1$

	public static final String RESOURCE_TYPE_FILE_ENDING_WITH_DOT = "." + RESOURCE_TYPE_FILE_ENDING; //$NON-NLS-1$

	public static final String SEGMENT_TYPE_FILE_ENDING = "SEG"; //$NON-NLS-1$

	public static final String SEGMENT_TYPE_FILE_ENDING_WITH_DOT = "." + SEGMENT_TYPE_FILE_ENDING; //$NON-NLS-1$

	public static final String SUBAPP_TYPE_FILE_ENDING = "SUB"; //$NON-NLS-1$

	public static final String SUBAPP_TYPE_FILE_ENDING_WITH_DOT = "." + SUBAPP_TYPE_FILE_ENDING; //$NON-NLS-1$

	public static final String SYSTEM_TYPE_FILE_ENDING = "SYS"; //$NON-NLS-1$

	public static final String SYSTEM_TYPE_FILE_ENDING_WITH_DOT = "." + SYSTEM_TYPE_FILE_ENDING; //$NON-NLS-1$

	/** The Constant TYPE_LIBRARY. */
	public static final String TYPE_LIBRARY = "typelibrary";//$NON-NLS-1$

	/** The Constant TOOL_TYPE_DIR. */
	public static final String TOOL_TYPE_DIR = TYPE_LIBRARY + "/";//$NON-NLS-1$

	/** The Constant for the file ending of FUNC files. */
	public static final String FC_TYPE_FILE_ENDING = "FCT"; //$NON-NLS-1$

	public static final String FC_TYPE_FILE_ENDING_WITH_DOT = "." + FC_TYPE_FILE_ENDING; //$NON-NLS-1$

	/** The Constant for the file ending of global constants files. */
	public static final String GLOBAL_CONST_FILE_ENDING = "GCF"; //$NON-NLS-1$

	public static final String GLOBAL_CONST_FILE_ENDING_WITH_DOT = "." + GLOBAL_CONST_FILE_ENDING; //$NON-NLS-1$

	/** The Constant for the IEventBroker topic called at type library creation */
	public static final String TYPE_LIBRARY_CREATION_TOPIC = "org/eclipse/fordiac/event/type_library_creation"; //$NON-NLS-1$

	private TypeLibraryTags() {
		throw new UnsupportedOperationException("Helper class TypeLibraryTags can not be instantiated."); //$NON-NLS-1$
	}

}
