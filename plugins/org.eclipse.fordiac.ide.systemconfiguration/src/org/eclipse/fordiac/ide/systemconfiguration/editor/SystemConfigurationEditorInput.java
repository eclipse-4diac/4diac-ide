/*******************************************************************************
 * Copyright (c) 2013, 2015 fortiss GmbH, Profactor GbmH
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
package org.eclipse.fordiac.ide.systemconfiguration.editor;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.ui.editors.PersistableUntypedEditorInput;
import org.eclipse.fordiac.ide.systemconfiguration.Messages;
import org.eclipse.ui.IMemento;

public class SystemConfigurationEditorInput extends PersistableUntypedEditorInput {

	@Override
	public String getName() {
		return MessageFormat.format(Messages.SystemConfigurationEditorInput_SysConfTitleName, super.getName());
	}

	public SystemConfigurationEditorInput(SystemConfiguration sysConf) {
		super(sysConf, sysConf.getAutomationSystem().getName());
	}

	@Override
	public void saveState(IMemento memento) {
		SystemConfigurationEditorInputFactory.saveState(memento, this);
	}

	@Override
	public String getFactoryId() {
		return SystemConfigurationEditorInputFactory.getFactoryId();
	}

	@Override
	public SystemConfiguration getContent() {
		return (SystemConfiguration) super.getContent();
	}
}
