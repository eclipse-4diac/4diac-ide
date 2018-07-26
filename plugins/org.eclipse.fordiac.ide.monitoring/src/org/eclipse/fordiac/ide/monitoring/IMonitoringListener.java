/*******************************************************************************
 * Copyright (c) 2012, 2015, 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import org.eclipse.fordiac.ide.deployment.monitoringBase.PortElement;

public interface IMonitoringListener {

	public void notifyAddPort(PortElement port);

	public void notifyTriggerEvent(PortElement port);

	public void notifyRemovePort(PortElement port);
}
