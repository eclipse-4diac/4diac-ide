/*******************************************************************************
 * Copyright (c) 2012, 2015, 2016 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.monitoringbase;

public interface IMonitoringListener {

	void notifyAddPort(PortElement port);

	void notifyTriggerEvent(PortElement port);

	void notifyRemovePort(PortElement port);

	void notifyWatchesChanged();
}
