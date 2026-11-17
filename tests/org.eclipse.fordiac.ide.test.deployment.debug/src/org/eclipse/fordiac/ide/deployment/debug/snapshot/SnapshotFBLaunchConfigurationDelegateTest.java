/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.snapshot;

import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createEdgeFBType;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createInternalFB;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createToggleFBType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.model.eval.variable.ECStateVariable;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.junit.jupiter.api.Test;

class SnapshotFBLaunchConfigurationDelegateTest {

	@Test
	void setsTheValuesOfInternalFBs() throws CoreException {
		final SimpleFBType innerType = createEdgeFBType();
		innerType.getInternalFbs().add(createInternalFB("toggle", createToggleFBType())); //$NON-NLS-1$
		// no initial values, their evaluation is not part of this test
		final SimpleFBType type = createEdgeFBType();
		type.getInternalFbs().add(createInternalFB("edge", innerType)); //$NON-NLS-1$
		final List<Variable<?>> arguments = FBLaunchConfigurationDelegate.getDefaultArguments(type);

		// values of unknown internal function blocks are skipped
		SnapshotFBLaunchConfigurationDelegate.setInternalFBValues(arguments,
				Map.of("edge.MEM", "TRUE", "edge.toggle.count", "3", "edge.toggle.__STATE", "HIGH", "unknown.Q", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
						"TRUE")); //$NON-NLS-1$

		final FBVariable edge = (FBVariable) arguments.stream().filter(argument -> "edge".equals(argument.getName())) //$NON-NLS-1$
				.findFirst().orElseThrow();
		assertEquals("TRUE", edge.getMembers().get("MEM").toString()); //$NON-NLS-1$ //$NON-NLS-2$
		final FBVariable toggle = (FBVariable) edge.getMembers().get("toggle"); //$NON-NLS-1$
		assertEquals("3", toggle.getMembers().get("count").toString()); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("HIGH", //$NON-NLS-1$
				((ECStateVariable) toggle.getMembers().get(ECStateVariable.NAME)).getValue().getState().getName());
	}

	@Test
	void storesTheSnapshotFlat() {
		final SimpleFBType innerType = createEdgeFBType();
		innerType.getInternalFbs().add(createInternalFB("toggle", createToggleFBType())); //$NON-NLS-1$
		final SimpleFBType type = createEdgeFBType();
		type.getInternalFbs().add(createInternalFB("edge", innerType)); //$NON-NLS-1$
		final FBVariable variable = new FBVariable("Counter", type); //$NON-NLS-1$
		variable.getMembers().get("CLK").setValue("TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		final FBVariable edge = (FBVariable) variable.getMembers().get("edge"); //$NON-NLS-1$
		edge.getMembers().get("MEM").setValue("TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		((FBVariable) edge.getMembers().get("toggle")).getMembers().get(ECStateVariable.NAME).setValue("HIGH"); //$NON-NLS-1$ //$NON-NLS-2$
		final Map<String, Object> attributes = new HashMap<>();
		final ILaunchConfigurationWorkingCopy configuration = (ILaunchConfigurationWorkingCopy) Proxy.newProxyInstance(
				getClass().getClassLoader(), new Class<?>[] { ILaunchConfigurationWorkingCopy.class },
				(proxy, method, args) -> {
					attributes.put((String) args[0], args[1]);
					return null;
				});

		SnapshotFBLaunchConfigurationAttributes.setSnapshot(configuration,
				new FBSnapshot(variable, List.of("edge.Q"))); //$NON-NLS-1$

		assertEquals(Map.of("CLK", "TRUE", "Q", "FALSE", "MEM", "FALSE"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
				attributes.get(SnapshotFBLaunchConfigurationAttributes.ARGUMENTS));
		assertEquals(Map.of("edge.CLK", "FALSE", "edge.Q", "FALSE", "edge.MEM", "TRUE", "edge.toggle.Q", "FALSE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
				"edge.toggle.count", "0", "edge.toggle.__STATE", "HIGH"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				attributes.get(SnapshotFBLaunchConfigurationAttributes.SNAPSHOT_INTERNAL_FB_VALUES));
		assertEquals(List.of("edge.Q"), attributes.get(SnapshotFBLaunchConfigurationAttributes.SNAPSHOT_MISSING)); //$NON-NLS-1$
	}
}
