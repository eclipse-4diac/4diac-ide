/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lukas Wais - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;

class HiddenConnectionTest {

	private static FordiacProjectLoader loader = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Path projectPath = new Path("data/HiddenConnectionTest");
		Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.model");
		loader = new FordiacProjectLoader(bundle, projectPath);
	}

	@Test
	@SuppressWarnings("static-method")
	void testLoader() {
		Assert.isNotNull(loader.getAutomationSystem("HiddenConnectionTest"));
	}
}