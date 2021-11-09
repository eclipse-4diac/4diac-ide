/*******************************************************************************
 * Copyright (c) 2021 Primemetals Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.changelistener;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

public interface IEditorFileChangeListener {

	int INIT = -1;
	int YES = 0;
	int YES_TO_ALL = 1;
	int NO = 2;
	int NO_TO_ALL = 3;

	void reloadFile();

	IFile getFile();

	/* update the editor input to be used by this editor. This can occur if directories or projects are renamed or a
	 * file is moved to a different location and the editor is open. It will not result in a reload of the content. */
	void updateEditorInput(FileEditorInput newInput);

}
