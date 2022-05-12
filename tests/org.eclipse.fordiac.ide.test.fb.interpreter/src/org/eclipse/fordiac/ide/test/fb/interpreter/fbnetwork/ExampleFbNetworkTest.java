package org.eclipse.fordiac.ide.test.fb.interpreter.fbnetwork;

import static org.junit.Assert.assertNotNull;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

/** small fb network consisting of e_sr, e_switch, and e_ctud */
public class ExampleFbNetworkTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final FBNetwork network = loadFbNetwork("ExampleFbNetwork", "ExampleFbNetwork");
		assertNotNull(network);

		runFBNetworkTest(network);

	}
}
