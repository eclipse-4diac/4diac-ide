/*******************************************************************************
 * Copyright (c) 2013, 2015, 2016 fortiss GbmH, Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editors;

import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.ui.editors.PersistableUntypedEditorInput;
import org.eclipse.ui.IMemento;

public class ResourceEditorInput extends PersistableUntypedEditorInput {

	public ResourceEditorInput(Resource res) {
		super(res, getResourceEditorName(res));
	}

	@Override
	public void saveState(IMemento memento) {
		ResourceEditorInputFactory.saveState(memento, this);

	}

	@Override
	public String getFactoryId() {
		return ResourceEditorInputFactory.getFactoryId();
	}

	@Override
	public Resource getContent() {
		return (Resource) super.getContent();
	}

	public static String getResourceEditorName(Resource res) {
		return res.getDevice().getName() + "." + res.getName(); //$NON-NLS-1$
	}

}
