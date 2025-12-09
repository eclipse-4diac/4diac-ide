/*******************************************************************************
 * Copyright (c) 2009 Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export;

/**
 * The Class ExportException.
 */
public class ExportException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new export exception.
	 *
	 * @param message the message
	 */
	public ExportException(final String message) {
		super(message);
	}

	public static class UserInteraction extends ExportException {

		private static final long serialVersionUID = 1L;

		public UserInteraction(final String message) {
			super(message);
		}

	}

	public static class OverwriteAll extends UserInteraction {

		private static final long serialVersionUID = 1L;

		public OverwriteAll() {
			super(Messages.TemplateExportFilter_OVERWRITE_ALL_LABEL_STRING);
		}

	}

	public static class CancelAll extends UserInteraction {

		private static final long serialVersionUID = 1L;

		public CancelAll() {
			super(Messages.TemplateExportFilter_OVERWRITE_ALL_LABEL_STRING);
		}

	}

}