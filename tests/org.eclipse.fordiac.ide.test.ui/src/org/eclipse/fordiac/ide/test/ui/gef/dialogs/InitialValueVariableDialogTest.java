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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui.gef.dialogs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.fordiac.ide.gef.dialogs.InitialValueVariableDialog;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable;
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method", "nls" })
class InitialValueVariableDialogTest {

	@Test
	void testSerializesOnlyExplicitStructMembers() {
		final StructVariable variable = newStructVariable("DI", newStructType("TestStruct",
				newMember("a", ElementaryTypes.BOOL), newMember("b", ElementaryTypes.BOOL)));
		final Variable<?> memberA = variable.getMembers().get("a");
		final Variable<?> memberB = variable.getMembers().get("b");
		memberA.setValue("TRUE");
		memberB.setValue("TRUE");

		assertEquals("(a := TRUE)", serialize(variable, Set.of(memberA)::contains));
	}

	@Test
	void testSerializesExplicitFalseEqualToDefaultValue() {
		final StructVariable variable = newStructVariable("DI", newStructType("TestExplicitFalseStruct",
				newMember("a", ElementaryTypes.BOOL), newMember("b", ElementaryTypes.BOOL)));
		final Variable<?> memberA = variable.getMembers().get("a");

		assertEquals("(a := FALSE)", serialize(variable, Set.of(memberA)::contains));
	}

	@Test
	void testSerializesNestedExplicitStructMember() {
		final StructuredType innerType = newStructType("TestInnerStruct", newMember("flag", ElementaryTypes.BOOL),
				newMember("other", ElementaryTypes.BOOL));
		final StructVariable variable = newStructVariable("DI",
				newStructType("TestOuterStruct", newMember("inner", innerType), newMember("flag", ElementaryTypes.BOOL)));
		final Variable<?> explicitMember = ((StructVariable) variable.getMembers().get("inner")).getMembers().get("flag");

		assertEquals("(inner := (flag := FALSE))", serialize(variable, Set.of(explicitMember)::contains));
	}

	@Test
	void testSerializesArrayThroughLastExplicitElement() {
		final StructuredType elementType = newStructType("TestArrayElement", newMember("x", ElementaryTypes.DINT),
				newMember("y", ElementaryTypes.DINT));
		final ArrayVariable variable = new ArrayVariable("DI_ARRAY",
				ArrayVariable.newArrayType(elementType, ArrayVariable.newSubrange(0, 2)));
		setStructValues(getElement(variable, 0), "10", "11");
		setStructValues(getElement(variable, 1), "20", "21");
		setStructValues(getElement(variable, 2), "42", "31");
		final Variable<?> explicitMember = getElement(variable, 2).getMembers().get("x");

		assertEquals("[(x := 10, y := 11), (x := 20, y := 21), (x := 42)]",
				serialize(variable, Set.of(explicitMember)::contains));
	}

	@Test
	void testSerializesNestedExplicitArrayMemberThroughLastExplicitElement() {
		final StructuredType innerType = newStructType("TestNestedArrayInnerStruct",
				newMember("flag", ElementaryTypes.BOOL), newMember("other", ElementaryTypes.BOOL));
		final StructuredType elementType = newStructType("TestNestedArrayElementStruct",
				newMember("inner", innerType));
		final StructVariable variable = newStructVariable("DI",
				newStructType("TestNestedArrayOuterStruct", newArrayMember("items", elementType, "0..2")));
		final ArrayVariable array = (ArrayVariable) variable.getMembers().get("items");
		final String firstElement = array.getElements().get(0).toString();
		final String secondElement = array.getElements().get(1).toString();
		final Variable<?> explicitMember = ((StructVariable) getElement(array, 2).getMembers().get("inner")).getMembers()
				.get("flag");

		assertEquals("(items := [" + firstElement + ", " + secondElement + ", (inner := (flag := FALSE))])",
				serialize(variable, Set.of(explicitMember)::contains));
	}

	@Test
	void testReturnsEmptyInitialValueWithoutExplicitMembers() {
		final StructVariable variable = newStructVariable("DI",
				newStructType("TestInheritedStruct", newMember("a", ElementaryTypes.BOOL)));
		variable.getMembers().get("a").setValue("TRUE");

		assertEquals("", serialize(variable, _ -> false));
	}

	private static String serialize(final Variable<?> variable, final Predicate<Variable<?>> explicitPredicate) {
		return TestInitialValueVariableDialog.serialize(variable, explicitPredicate);
	}

	private static StructVariable newStructVariable(final String name, final StructuredType type) {
		return new StructVariable(name, type);
	}

	private static StructuredType newStructType(final String name, final VarDeclaration... members) {
		final StructuredType type = DataFactory.eINSTANCE.createStructuredType();
		type.setName(name);
		type.getMemberVariables().addAll(List.of(members));
		return type;
	}

	private static VarDeclaration newMember(final String name, final DataType type) {
		final VarDeclaration member = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		member.setName(name);
		member.setType(type);
		return member;
	}

	private static VarDeclaration newArrayMember(final String name, final DataType type, final String arraySize) {
		final VarDeclaration member = newMember(name, type);
		ArraySizeHelper.setArraySize(member, arraySize);
		return member;
	}

	private static StructVariable getElement(final ArrayVariable variable, final int index) {
		return (StructVariable) variable.getElements().get(index);
	}

	private static void setStructValues(final StructVariable variable, final String x, final String y) {
		variable.getMembers().get("x").setValue(x);
		variable.getMembers().get("y").setValue(y);
	}

	private static final class TestInitialValueVariableDialog extends InitialValueVariableDialog {

		private TestInitialValueVariableDialog() {
			super(null, null, null, null, Set.of());
		}

		private static String serialize(final Variable<?> variable,
				final Predicate<Variable<?>> explicitPredicate) {
			return toExplicitInitialValue(variable, explicitPredicate);
		}
	}
}
