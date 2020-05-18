/********************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr - initial implementation
 ********************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.gef.commands.Command;
import org.junit.Test;
// see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

import static org.junit.Assert.*;

public class ChangeAuthorCommandTest {
	private static VersionInfo vInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();
	private static final String DEFAULT_NAME = vInfo.getAuthor();
	private static final String DEFAULT_DATE = vInfo.getDate();
	private static final String DEFAULT_ORG = vInfo.getOrganization();
	private static final String DEFAULT_REMARKS = vInfo.getRemarks();
	private static final String DEFAULT_VERSION = vInfo.getVersion();
	private Command cmd;

	@Test
	public void constructorTest() {
		String newAuthor = "new author";//$NON-NLS-1$
		cmd = new ChangeAuthorCommand(vInfo, newAuthor);
		assertNotNull(cmd);
	}

	@Test
	public void setAuthor() {
		vInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();
		String newAuthor = "new author";//$NON-NLS-1$
		cmd = new ChangeAuthorCommand(vInfo, newAuthor);

		assertTrue(cmd.canExecute());
		cmd.execute();
		assertEquals(vInfo.getAuthor(), newAuthor);
		verifyDefaultValues();

		assertTrue(cmd.canUndo());
		cmd.undo();
		assertEquals(vInfo.getAuthor(), DEFAULT_NAME);
		verifyDefaultValues();

		assertTrue(cmd.canRedo());
		cmd.redo();
		assertEquals(vInfo.getAuthor(), newAuthor);
		verifyDefaultValues();
	}

	private static void verifyDefaultValues() {
		assertEquals(vInfo.getDate(), DEFAULT_DATE);
		assertEquals(vInfo.getOrganization(), DEFAULT_ORG);
		assertEquals(vInfo.getRemarks(), DEFAULT_REMARKS);
		assertEquals(vInfo.getVersion(), DEFAULT_VERSION);
	}

	private static final String date = "1984-08-04"; //$NON-NLS-1$
	private static final String name = "first name surname"; //$NON-NLS-1$
	private static final String org = "4diac"; //$NON-NLS-1$
	private static final String remark = "remark something";//$NON-NLS-1$
	private static final String version = "13.0";//$NON-NLS-1$

	@Test
	public void changeAuthor() {
		vInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();
		setupSampleVersionInfo();

		String newName = "first name married name";//$NON-NLS-1$
		cmd = new ChangeAuthorCommand(vInfo, newName);

		assertTrue(cmd.canExecute());
		cmd.execute();
		assertEquals(vInfo.getAuthor(), newName);
		verifySetValues();

		assertTrue(cmd.canUndo());
		cmd.undo();
		assertEquals(vInfo.getAuthor(), name);
		verifySetValues();

		assertTrue(cmd.canRedo());
		cmd.redo();
		assertEquals(vInfo.getAuthor(), newName);
		verifySetValues();
	}

	private static void setupSampleVersionInfo() {
		vInfo.setAuthor(name);
		vInfo.setDate(date);
		vInfo.setOrganization(org);
		vInfo.setRemarks(remark);
		vInfo.setVersion(version);
	}

	private static void verifySetValues() {
		assertEquals(vInfo.getDate(), date);
		assertEquals(vInfo.getOrganization(), org);
		assertEquals(vInfo.getRemarks(), remark);
		assertEquals(vInfo.getVersion(), version);
	}
}
