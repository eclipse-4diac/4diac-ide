/*******************************************************************************
 * Copyright (c) 2008, 2016 Profactor GbmH
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
package org.eclipse.fordiac.ide.systemconfiguration.policies;

import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.systemconfiguration.commands.SegmentDeleteCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

/**
 * An EditPolicy which returns a command for deleting.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class DeleteSegmentEditPolicy extends org.eclipse.gef.editpolicies.ComponentEditPolicy {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.
	 * eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(final GroupRequest request) {
		if (getHost().getModel() instanceof Segment) {
			Segment segment = (Segment) getHost().getModel();
			SegmentDeleteCommand c = new SegmentDeleteCommand();
			c.setSegment(segment);

			return c;
		}
		return null;
	}

}
