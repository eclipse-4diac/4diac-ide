/*******************************************************************************
 * Copyright (c) 2013, 2024 fortiss GmbH, Profactor GmbH,
 *                          Johannes Kepler University Linz
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
import org.eclipse.fordiac.ide.model.ui.editors.UntypedEditorInput;
import org.eclipse.fordiac.ide.systemconfiguration.Messages;

public class SystemConfigurationEditorInput extends UntypedEditorInput {

	@Override
	public String getName() {
		return MessageFormat.format(Messages.SystemConfigurationEditorInput_SysConfTitleName, super.getName());
	}

	public SystemConfigurationEditorInput(final SystemConfiguration sysConf) {
		super(sysConf, sysConf.getAutomationSystem().getName());
	}

	@Override
	public SystemConfiguration getContent() {
		return (SystemConfiguration) super.getContent();
	}
}
