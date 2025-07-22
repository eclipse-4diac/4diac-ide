/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fordiac.ide.contracts.CConnection;
import org.eclipse.fordiac.ide.contracts.ContractComponent;
import org.eclipse.fordiac.ide.contracts.ContractIssue;
import org.eclipse.fordiac.ide.contracts.ContractSystem;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method", "nls" }) // translating doesn't make sense here
class StaticContractCheckTest {

	// === test single events
	@Test
	void singleEventMatchTest() {
		final String g = "EO occurs within [12, 18]ms";
		final String a = "EI occurs within [10, 20]ms";
		final ContractSystem sys = create2ConnectedComponents(a, g);
		sys.performStaticCheck();
		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void singleEventNotMatchTest() {
		final String g = "EO occurs within [10, 18]ms";
		final String a = "EI occurs within [12, 20]ms";
		final ContractSystem sys = create2ConnectedComponents(a, g);
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.SINGLE_EVENT_MATCH, sys);
	}

	// === test repetitions
	@Test
	void repetitionMatchTest() {
		final String g = "EO occurs every [6, 8]ms with offset 4ms";
		final String a = "EI occurs every [5, 10]ms with offset [2, 6]ms";
		final ContractSystem sys = create2ConnectedComponents(a, g);
		sys.performStaticCheck();
		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void repetitionMatchJitterTest() {
		final String g = "EO occurs every [6, 8]ms with offset 5ms and jitter 1ms";
		final String a = "EI occurs every [5, 10]ms with offset [4, 6]ms";
		final ContractSystem sys = create2ConnectedComponents(a, g);
		sys.performStaticCheck();
		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void repetitionIntervalNotMatchTest() {
		final String g = "EO occurs every [1, 6]ms";
		final String a = "EI occurs every [2, 4]ms";
		final ContractSystem sys = create2ConnectedComponents(a, g);
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.REPETITION_MATCH, sys);
	}

	@Test
	void repetitionOffsetNotMatchTest() {
		final String g = "EO occurs every 5ms with offset [4, 10]ms";
		final String a = "EI occurs every 5ms with offset [5, 8]ms";
		final ContractSystem sys = create2ConnectedComponents(a, g);
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.REPETITION_MATCH, sys);
	}

	@Test
	void repetitionJitterNotMatchTest() {
		final String g = "EO occurs every 5ms with jitter 4ms";
		final String a = "EI occurs every 5ms with jitter 2ms";
		final ContractSystem sys = create2ConnectedComponents(a, g);
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.REPETITION_MATCH, sys);
	}

	// === test single event -- repetition
	@Test
	void singleEventRepetitionTest() {
		final String g = "EO occurs within 10ms";
		final String a = "EI occurs every 10ms";
		final ContractSystem sys = create2ConnectedComponents(a, g);
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.TYPE_MATCH, sys);
	}

	@Test
	void repetitionSingleEventTest() {
		final String g = "EO occurs every 10ms";
		final String a = "EI occurs within 10ms";
		final ContractSystem sys = create2ConnectedComponents(a, g);
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.TYPE_MATCH, sys);
	}

	// === test self loops
	@Test
	void selfLoopMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		comp1.addInput(comp1, "EO", "EI");

		final String contract = "EO occurs within [8, 9]ms " //
				+ "EI occurs within [7, 10]ms";
		sys.addComponent(comp1, contract, List.of("EI"), List.of("EO"));
		sys.performStaticCheck();
		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void selfLoopNotMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		comp1.addInput(comp1, "EO", "EI");

		final String contract = "EO occurs within [7, 10]ms " //
				+ "EI occurs within [8, 9]ms";
		sys.addComponent(comp1, contract, List.of("EI"), List.of("EO"));
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.SINGLE_EVENT_MATCH, sys);
	}

	@Test
	void selfLoopReactionTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		comp1.addInput(comp1, "EO", "EI");

		final String contract = "whenever EI occurs then EO occurs within 10ms";
		sys.addComponent(comp1, contract, List.of("EI"), List.of("EO"));
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.UNRESOLVED_REACTION, sys);
	}

	// === test direct inner connections
	@Test
	void directInnerMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		comp1.addInput(comp1, "EI", "EO"); // inner connection from input to output

		sys.addComponent(comp1, "EI occurs within [5, 10]ms " //
				+ "EO occurs within [4, 12]ms", List.of("EI"), List.of("EO"));
		sys.performStaticCheck();
		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void directInnerNotMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		comp1.addInput(comp1, "EI", "EO"); // inner connection from input to output

		sys.addComponent(comp1, "EI occurs within [5, 10]ms " //
				+ "EO occurs within [6, 8]ms", List.of("EI"), List.of("EO"));
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.SINGLE_EVENT_MATCH, sys);
	}

	// === test single event with multiple ports
	@Test
	void singleEventMultiplePortsTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		comp2.addInput(comp1, "EO1", "EI1");
		comp2.addInput(comp1, "EO2", "EI2");

		sys.addComponent(comp1, "EO1, EO2 occurs within 10ms", null, List.of("EO1", "EO2"));
		sys.addComponent(comp2, "EI1, EI2 occurs within 10ms", List.of("EI1", "EI2"), null);
		sys.performStaticCheck();
		assertTrue(sys.getIssues().isEmpty());
	}

	// === test one guarantee fulfilling assumptions for 2 components
	@Test
	void oneGuaranteeTwoAssumptionsTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		final ContractComponent comp3 = new ContractComponent("component3");
		comp2.addInput(comp1, "EO", "EI");
		comp3.addInput(comp1, "EO", "EI");

		sys.addComponent(comp1, "EO occurs within 10ms", null, List.of("EO"));
		sys.addComponent(comp2, "EI occurs within 10ms", List.of("EI"), null);
		sys.addComponent(comp3, "EI occurs within 10ms", List.of("EI"), null);
		sys.performStaticCheck();
		assertTrue(sys.getIssues().isEmpty());
	}

	// === test conflicting assumption/guarantees of one component
	@Test
	void conflictingAssumptionsTest() {
		final ContractSystem sys = new ContractSystem();
		final ContractComponent comp1 = new ContractComponent("component1");

		sys.addComponent(comp1, "EI occurs within 10ms " //
				+ "EI occurs within 20ms", List.of("EI"), null);
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.CONFLICTING_ASSUMPTIONS, sys);
	}

	@Test
	void conflictingAssumptions2Test() {
		final ContractSystem sys = new ContractSystem();
		final ContractComponent comp1 = new ContractComponent("component1");

		sys.addComponent(comp1, "EI occurs within 10ms " //
				+ "EI occurs every 10ms", List.of("EI"), null);
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.CONFLICTING_ASSUMPTIONS, sys);
	}

	@Test
	void conflictingGuaranteesTest() {
		final ContractSystem sys = new ContractSystem();
		final ContractComponent comp1 = new ContractComponent("component1");

		sys.addComponent(comp1, "EO occurs within 10ms " //
				+ "EO occurs every 20ms", null, List.of("EO"));
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.CONFLICTING_GUARANTEES, sys);
	}

	@Test
	void conflictingGuarantees2Test() {
		final ContractSystem sys = new ContractSystem();
		final ContractComponent comp1 = new ContractComponent("component1");

		sys.addComponent(comp1, "EO occurs every 10ms " //
				+ "EO occurs every 10ms", null, List.of("EO"));
		sys.performStaticCheck();
		assertOneIssue(ContractIssue.Code.CONFLICTING_GUARANTEES, sys);
	}

	// === test assumption being violated because of 2 connected components
	@Test
	void twoGuaranteesOneAssumptionTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		final ContractComponent comp3 = new ContractComponent("component3");
		comp3.addInput(comp1, "EO", "EI");
		comp3.addInput(comp2, "EO", "EI");

		sys.addComponent(comp1, "EO occurs within 10ms", null, List.of("EO"));
		sys.addComponent(comp2, "EO occurs within 10ms", null, List.of("EO"));
		sys.addComponent(comp3, "EI occurs within 10ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertOneIssue(ContractIssue.Code.MULTIPLE_FULFILL, sys);
	}

	// === test reaction resolving
	@Test
	void simpleReactionResolveMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		comp2.addInput(comp1, "EO", "EI");

		sys.addComponent(comp1, "EI occurs within 10ms", List.of("EI"), null);
		sys.addComponent(comp1, "whenever EI occurs then EO occurs within 5ms", List.of("EI"), List.of("EO"));
		sys.addComponent(comp2, "EI occurs within 15ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void simpleReactionResolveNotMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		comp2.addInput(comp1, "EO", "EI");

		sys.addComponent(comp1, "EI occurs within 10ms", List.of("EI"), null);
		sys.addComponent(comp1, "whenever EI occurs then EO occurs within 8ms", List.of("EI"), List.of("EO"));
		sys.addComponent(comp2, "EI occurs within 15ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertOneIssue(ContractIssue.Code.SINGLE_EVENT_MATCH, sys);
	}

	@Test
	void reactionResolveMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		final ContractComponent comp3 = new ContractComponent("component3");
		comp2.addInput(comp1, "EO", "EI");
		comp3.addInput(comp2, "EO", "EI");

		sys.addComponent(comp1, "EO occurs every [5, 10]ms", null, List.of("EO"));
		sys.addComponent(comp2, "whenever EI occurs then EO occurs within 5ms", List.of("EI"), List.of("EO"));
		sys.addComponent(comp3, "EI occurs every [5, 10]ms with offset 5ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void reactionResolveNotMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		final ContractComponent comp3 = new ContractComponent("component3");
		comp2.addInput(comp1, "EO", "EI");
		comp3.addInput(comp2, "EO", "EI");

		sys.addComponent(comp1, "EO occurs every [5, 10]ms", null, List.of("EO"));
		sys.addComponent(comp2, "whenever EI occurs then EO occurs within 2ms", List.of("EI"), List.of("EO"));
		sys.addComponent(comp3, "EI occurs every [5, 10]ms with offset 5ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertOneIssue(ContractIssue.Code.REPETITION_MATCH, sys);
	}

	@Test
	void doubleResolveMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		final ContractComponent comp3 = new ContractComponent("component3");
		final ContractComponent comp4 = new ContractComponent("component4");
		comp2.addInput(comp1, "EO", "EI");
		comp3.addInput(comp2, "EO", "EI");
		comp4.addInput(comp3, "EO", "EI");

		sys.addComponent(comp1, "EO occurs every [2, 3]ms", null, List.of("EO"));
		sys.addComponent(comp2, "whenever EI occurs then EO occurs within 2ms", List.of("EI"), List.of("EO"));
		sys.addComponent(comp3, "whenever EI occurs then EO occurs within 3ms", List.of("EI"), List.of("EO"));
		sys.addComponent(comp4, "EI occurs every [2, 3]ms with offset 5ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void doubleResolveNotMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		final ContractComponent comp3 = new ContractComponent("component3");
		final ContractComponent comp4 = new ContractComponent("component4");
		comp2.addInput(comp1, "EO", "EI");
		comp3.addInput(comp2, "EO", "EI");
		comp4.addInput(comp3, "EO", "EI");

		sys.addComponent(comp1, "EO occurs every [2, 3]ms", null, List.of("EO"));
		sys.addComponent(comp2, "whenever EI occurs then EO occurs within 5ms", List.of("EI"), List.of("EO"));
		sys.addComponent(comp3, "whenever EI occurs then EO occurs within 5ms", List.of("EI"), List.of("EO"));
		sys.addComponent(comp4, "EI occurs every [2, 3]ms with offset 5ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertOneIssue(ContractIssue.Code.REPETITION_MATCH, sys);
	}

	// === test multiple resolve error
	@Test
	void multipleReactionResolveTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		final ContractComponent comp3 = new ContractComponent("component3");
		comp3.addInput(comp1, "EO", "EI");
		comp3.addInput(comp2, "EO", "EI");

		sys.addComponent(comp1, "EO occurs within 10ms", null, List.of("EO"));
		sys.addComponent(comp2, "EO occurs within 20ms", null, List.of("EO"));
		sys.addComponent(comp3, "whenever EI occurs then EO occurs within 5ms", List.of("EI"), List.of("EO"));
		sys.performStaticCheck();

		assertOneIssue(ContractIssue.Code.MULTIPLE_RESOLVE, sys);
	}

	@Test
	void multipleReactionResolveNestedTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent outer = new ContractComponent("outer");
		final ContractComponent inner1 = new ContractComponent("inner1");
		final ContractComponent inner2 = new ContractComponent("inner2");
		inner1.addInput(outer, "EI", "EI", CConnection.Type.FROM_OUTER);
		inner1.addInput(inner2, "EO", "EI");

		sys.addComponent(outer, "EI occurs within 10ms", List.of("EI"), null);
		sys.addComponent(inner1, "whenever EI occurs then EO occurs within 5ms", List.of("EI"), List.of("EO"));
		sys.addComponent(inner2, "EO occurs within 20ms", null, List.of("EO"));
		sys.performStaticCheck();

		assertOneIssue(ContractIssue.Code.MULTIPLE_RESOLVE, sys);
	}

	// === test nested SubApps
	@Test
	void fromInnerConnectionMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent outer = new ContractComponent("outer");
		final ContractComponent inner = new ContractComponent("inner");
		outer.addInput(inner, "EO", "EO", CConnection.Type.FROM_INNER);

		sys.addComponent(outer, "EO occurs every 10ms", null, List.of("EO"));
		sys.addComponent(inner, "EO occurs every 10ms", null, List.of("EO"));
		sys.performStaticCheck();

		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void fromInnerConnectionNotMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent outer = new ContractComponent("outer");
		final ContractComponent inner = new ContractComponent("inner");
		outer.addInput(inner, "EO", "EO", CConnection.Type.FROM_INNER);

		sys.addComponent(outer, "EO occurs every 10ms", null, List.of("EO"));
		sys.addComponent(inner, "EO occurs every 5ms", null, List.of("EO"));
		sys.performStaticCheck();

		assertOneIssue(ContractIssue.Code.REPETITION_MATCH, sys);
	}

	@Test
	void fromOuterConnectionMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent outer = new ContractComponent("outer");
		final ContractComponent inner = new ContractComponent("inner");
		inner.addInput(outer, "EI", "EI", CConnection.Type.FROM_OUTER);

		sys.addComponent(outer, "EI occurs every 10ms", List.of("EI"), null);
		sys.addComponent(inner, "EI occurs every 10ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void fromOuterConnectionNotMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent outer = new ContractComponent("outer");
		final ContractComponent inner = new ContractComponent("inner");
		inner.addInput(outer, "EI", "EI", CConnection.Type.FROM_OUTER);

		sys.addComponent(outer, "EI occurs every 10ms", List.of("EI"), null);
		sys.addComponent(inner, "EI occurs every 5ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertOneIssue(ContractIssue.Code.REPETITION_MATCH, sys);
	}

	@Test
	void noResolveFromInnerTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent outer = new ContractComponent("outer");
		final ContractComponent inner = new ContractComponent("inner");
		outer.addInput(inner, "EO", "EO", CConnection.Type.FROM_INNER);

		sys.addComponent(outer, "whenever EI occurs then EO occurs within 5ms", List.of("EI"), List.of("EO"));
		sys.addComponent(inner, "EO occurs within 10ms", null, List.of("EO"));
		sys.performStaticCheck();

		assertOneIssue(ContractIssue.Code.UNRESOLVED_REACTION, sys);
	}

	@Test
	void resolveFromOuterMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent outer = new ContractComponent("outer");
		final ContractComponent inner1 = new ContractComponent("inner1");
		final ContractComponent inner2 = new ContractComponent("inner2");
		inner1.addInput(outer, "EI", "EI", CConnection.Type.FROM_OUTER);
		inner2.addInput(inner1, "EO", "EI");

		sys.addComponent(outer, "EI occurs within 10ms", List.of("EI"), null);
		sys.addComponent(inner1, "whenever EI occurs then EO occurs within 5ms", List.of("EI"), List.of("EO"));
		sys.addComponent(inner2, "EI occurs within 15ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void resolveFromOuterNotMatchTest() {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent outer = new ContractComponent("outer");
		final ContractComponent inner1 = new ContractComponent("inner1");
		final ContractComponent inner2 = new ContractComponent("inner2");
		inner1.addInput(outer, "EI", "EI", CConnection.Type.FROM_OUTER);
		inner2.addInput(inner1, "EO", "EI");

		sys.addComponent(outer, "EI occurs within 10ms", List.of("EI"), null);
		sys.addComponent(inner1, "whenever EI occurs then EO occurs within 5ms", List.of("EI"), List.of("EO"));
		sys.addComponent(inner2, "EI occurs within 20ms", List.of("EI"), null);
		sys.performStaticCheck();

		assertOneIssue(ContractIssue.Code.SINGLE_EVENT_MATCH, sys);
	}

	// === helper methods
	/**
	 * creates a system of two connected components, one with assumptions and the
	 * other with guarantees (so that guarantee ->- assumption)
	 */
	private static ContractSystem create2ConnectedComponents(final String assumption, final String guarantee) {
		final ContractSystem sys = new ContractSystem();

		final ContractComponent comp1 = new ContractComponent("component1");
		final ContractComponent comp2 = new ContractComponent("component2");
		comp2.addInput(comp1, "EO", "EI");

		sys.addComponent(comp1, guarantee, null, List.of("EO"));
		sys.addComponent(comp2, assumption, List.of("EI"), null);
		return sys;
	}

	private static void assertOneIssue(final ContractIssue.Code code, final ContractSystem sys) {
		assertEquals(1, sys.getIssues().size());
		assertEquals(code, sys.getIssues().get(0).getCode());
	}
}
