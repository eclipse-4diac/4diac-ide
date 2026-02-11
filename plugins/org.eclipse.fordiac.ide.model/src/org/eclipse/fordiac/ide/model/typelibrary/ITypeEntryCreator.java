/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Waldemar Eisenmenger
 *     - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst
 *     - add can handle for classes
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EClass;

/**
 * Objects implementing this element can create a palette entry if the file type
 * can be handled.
 *
 * @author eisenmenger
 *
 */
public interface ITypeEntryCreator {

	/**
	 * Tests whether the file type can be handled.
	 *
	 * @param file file type
	 * @return true if the file can handled and false if not.
	 */
	boolean canHandle(IFile file);

	/**
	 * Tests whether the class can be handled.
	 *
	 * @param eClass class
	 * @return true if the class can handled and false if not.
	 */
	boolean canHandle(EClass eClass);

	/**
	 * Creates the type entry
	 *
	 * @return the created TypeEntry
	 */
	TypeEntry createTypeEntry();

}
