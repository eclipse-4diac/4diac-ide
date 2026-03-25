/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.osgi.framework.Version;

public class LibraryChangeAction {

	public enum ActionType {
		EMPTY, REMOVE, UPDATE, DOWNGRADE
	}

	private final String targetVersion;
	private final ActionType actionType;
	private static final LibraryChangeAction emptyAction = new LibraryChangeAction(Version.emptyVersion.toString(),
			ActionType.EMPTY);
	private static final LibraryChangeAction removeAction = new LibraryChangeAction(Version.emptyVersion.toString(),
			ActionType.REMOVE);

	public static LibraryChangeAction createAction(final LibraryDescriptorNode node, final String targetVersion) {
		final int result = new VersionComparator().compare(node.getActiveVersion(), targetVersion);
		if (result == 0) {
			return emptyAction;
		}
		return result < 0 ? new LibraryChangeAction(targetVersion, ActionType.UPDATE)
				: new LibraryChangeAction(targetVersion, ActionType.DOWNGRADE);
	}

	public static LibraryChangeAction emptyAction() {
		return emptyAction;
	}

	public static LibraryChangeAction removeAction() {
		return removeAction;
	}

	public ActionType getType() {
		return this.actionType;
	}

	public String getTargetVersion() {
		return targetVersion;
	}

	private LibraryChangeAction(final String targetVersion, final ActionType actionType) {
		this.targetVersion = targetVersion;
		this.actionType = actionType;
	}

	public static String getActionText(final LibraryChangeAction action) {
		if (action == null) {
			return ""; //$NON-NLS-1$
		}

		return switch (action.getType()) {
		case REMOVE: {
			yield Messages.LibraryChangeAction_Remove;
		}
		case UPDATE: {
			yield MessageFormat.format(Messages.LibraryChangeAction_Update, action.getTargetVersion());
		}
		case DOWNGRADE: {
			yield MessageFormat.format(Messages.LibraryChangeAction_Downgrade, action.getTargetVersion());
		}
		case EMPTY: {
			yield Messages.LibraryChangeAction_Empty + " ..";//$NON-NLS-1$
		}
		default:
			yield ""; //$NON-NLS-1$
		};
	}

}
