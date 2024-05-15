/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.errormessages;

public class ErrorMessage {

	private static final int DEFAULT_DISABLE_AFTER_MS = 3000;

	private final String message;
	private final int timeout;
	private long enableTime = System.currentTimeMillis();
	private boolean valid = true;

	ErrorMessage(final String message, final int timeout) {
		this.message = message;
		this.timeout = timeout > 0 ? timeout : DEFAULT_DISABLE_AFTER_MS;
	}

	public void setInvalid() {
		valid = false;
	}

	public boolean isStillValid() {
		return valid && ((System.currentTimeMillis() - enableTime) < timeout);
	}

	public String getMessage() {
		return message;
	}

	public int getTimeout() {
		return timeout;
	}

	public void updateEnabled() {
		enableTime = System.currentTimeMillis();
	}

	@Override
	public int hashCode() {
		return message.hashCode();
	}

	@Override
	public boolean equals(final Object obj) { // equals with side-effect: the object that checks for equality will
												// update it's timestamp
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ErrorMessage other = (ErrorMessage) obj;
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		this.updateEnabled();
		((ErrorMessage) obj).updateEnabled();
		return true;
	}

}
