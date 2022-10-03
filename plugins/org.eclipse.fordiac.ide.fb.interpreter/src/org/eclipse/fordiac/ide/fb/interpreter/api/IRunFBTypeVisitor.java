/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.api;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;

public interface IRunFBTypeVisitor {

	EList<EventOccurrence> runBasicFBType(BasicFBTypeRuntime fBTypeRuntime);

	EList<EventOccurrence> runSimpleFBType(SimpleFBTypeRuntime fBTypeRuntime);

	EList<EventOccurrence> runFBNetwork(FBNetworkRuntime fBNetworkRuntime);
}
