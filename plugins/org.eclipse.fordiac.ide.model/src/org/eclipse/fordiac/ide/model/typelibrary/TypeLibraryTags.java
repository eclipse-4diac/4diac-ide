/********************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse  License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Waldemar Eisenmenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

public interface TypeLibraryTags {

	 static final String TOOL_LIBRARY_PROJECT_NAME = "Tool Library"; //$NON-NLS-1$

	/** The Constant for the file ending of FB type files. */
	 static final String FB_TYPE_FILE_ENDING = "FBT"; //$NON-NLS-1$

	 static final String FB_TYPE_FILE_ENDING_WITH_DOT = "."+ FB_TYPE_FILE_ENDING; //$NON-NLS-1$

	 static final String ADAPTER_TYPE_FILE_ENDING = "ADP"; //$NON-NLS-1$

	 static final String ADAPTER_TYPE_FILE_ENDING_WITH_DOT = "." + ADAPTER_TYPE_FILE_ENDING; //$NON-NLS-1$

	 static final String DEVICE_TYPE_FILE_ENDING = "DEV"; //$NON-NLS-1$

	 static final String DEVICE_TYPE_FILE_ENDING_WITH_DOT = "." + DEVICE_TYPE_FILE_ENDING; //$NON-NLS-1$

	 static final String RESOURCE_TYPE_FILE_ENDING = "RES"; //$NON-NLS-1$

	 static final String RESOURCE_TYPE_FILE_ENDING_WITH_DOT = "." + RESOURCE_TYPE_FILE_ENDING; //$NON-NLS-1$

	 static final String SEGMENT_TYPE_FILE_ENDING = "SEG"; //$NON-NLS-1$

	 static final String SEGMENT_TYPE_FILE_ENDING_WITH_DOT = "." + SEGMENT_TYPE_FILE_ENDING; //$NON-NLS-1$

	 static final String SUBAPP_TYPE_FILE_ENDING = "SUB"; //$NON-NLS-1$

	 static final String SUBAPP_TYPE_FILE_ENDING_WITH_DOT = "." + SUBAPP_TYPE_FILE_ENDING; //$NON-NLS-1$

	/** The Constant TYPE_LIBRARY. */
	 static final String TYPE_LIBRARY = "typelibrary";//$NON-NLS-1$

	/** The Constant TOOL_TYPE_DIR. */
	 static final String TOOL_TYPE_DIR = TYPE_LIBRARY + "/";//$NON-NLS-1$
	
}
