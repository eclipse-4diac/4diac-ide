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

import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class SetBlockDocumentation extends AbstractBlockModelTask {

	public void setDocumentation(final String documentation) {
		this.documentation = nullCheckString(documentation);
	}

	public void addText(final String msg) {
		this.documentation += nullCheckString(getProject().replaceProperties(msg));
	}

	@Override
	protected void modifyBlock(final FBType fb) {
		fb.setDocumentation(documentation);

		log(MessageFormat.format("Adding documentation to {0}/{1}", //$NON-NLS-1$
				projectname, blockname));
	}

	protected String documentation = EMPTY_STRING;

}
