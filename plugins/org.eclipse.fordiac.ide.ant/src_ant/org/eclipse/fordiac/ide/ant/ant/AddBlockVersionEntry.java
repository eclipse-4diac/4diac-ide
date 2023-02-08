/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.text.MessageFormat;

import org.apache.tools.ant.BuildException;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class AddBlockVersionEntry extends AbstractBlockModelTask {

	public void setVersion(final String version) {
		this.version = nullCheckString(version);
	}

	public void setAuthor(final String author) {
		this.author = nullCheckString(author);
	}

	public void setDate(final String date) {
		this.date = nullCheckString(date);
	}

	public void setRemarks(final String remarks) {
		this.remarks = nullCheckString(remarks);
	}

	public void setOrganization(final String organization) {
		this.organization = nullCheckString(organization);
	}

	public void setPosition(final int position) {
		this.position = position;
	}

	@Override
	protected void modifyBlock(final FBType fb) {
		final var info = LibraryElementFactory.eINSTANCE.createVersionInfo();
		info.setDate(date);
		info.setAuthor(author);
		info.setOrganization(organization);
		info.setVersion(version);
		info.setRemarks(remarks);

		final var vi = fb.getVersionInfo();
		if (vi == null) {
			throw new BuildException(MessageFormat.format("VersionInfo broken for {0}", blockname)); //$NON-NLS-1$
		}

		position = position < 0 ? position + vi.size() + 1 : position; // add from the "bottom" if negative value is
																		 // passed
		vi.add(limit(position, vi.size()), info);

		log(MessageFormat.format("Adding version {2} to {0}/{1}", // //$NON-NLS-1$
				projectname, blockname, version));
	}

	protected static int limit(final int p, final int maximum) {
		final int pos = p > maximum ? maximum : p;
		return pos < 0 ? 0 : pos;
	}

	protected String version = EMPTY_STRING;
	protected String author = EMPTY_STRING;
	protected String date = EMPTY_STRING;
	protected String remarks = EMPTY_STRING;
	protected String organization = EMPTY_STRING;
	protected int position = 0;
}
