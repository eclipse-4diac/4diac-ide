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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class SaveBlock extends AbstractBlockModelTask {

	@Override
	protected void modifyBlock(final FBType fb) {
		try {
			fb.getTypeEntry().save();
			log(MessageFormat.format("Save {0}/{1}", projectname, blockname)); //$NON-NLS-1$
		} catch (final CoreException e) {
			throw new BuildException(e);
		}
	}

}
