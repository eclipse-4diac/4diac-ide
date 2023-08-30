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
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.providers;

import org.eclipse.jface.dialogs.MessageDialog;

/** Interface suitable for providing MessageDialog via a lambda to some executor */
public interface MessageDialogProvider {

	/** Provide a command for the given reference element
	 *
	 * @param refElement reference element for the command
	 * @return the MessageDialog */
	MessageDialog getMessageDialog(Object refElement);

}
