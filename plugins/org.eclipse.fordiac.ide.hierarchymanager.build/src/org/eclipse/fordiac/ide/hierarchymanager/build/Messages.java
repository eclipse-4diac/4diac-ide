/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.hierarchymanager.build;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.hierarchymanager.build.messages"; //$NON-NLS-1$
	public static String HierarchyBuilderParticipant_CannotLoadContainerFile;
	public static String HierarchyBuilderParticipant_DuplicateReferencedElement;
	public static String HierarchyBuilderParticipant_NoSuchContainerFile;
	public static String HierarchyBuilderParticipant_NoSuchLibraryElement;
	public static String HierarchyBuilderParticipant_NoSuchReferencedElement;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
