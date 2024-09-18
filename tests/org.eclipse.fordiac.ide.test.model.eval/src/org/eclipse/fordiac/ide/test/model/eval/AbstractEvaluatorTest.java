/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.eval;

import java.util.Collection;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.value.AnyElementaryValue;
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup;
import org.eclipse.fordiac.ide.test.model.typelibrary.AttributeTypeEntryMock;
import org.eclipse.fordiac.ide.test.model.typelibrary.DataTypeEntryMock;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractEvaluatorTest {

	protected static TypeLibrary typeLib;

	@BeforeAll
	public static void setupXtext() {
		typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(null);
		GlobalConstantsStandaloneSetup.doSetup();
		STFunctionStandaloneSetup.doSetup();
		STAlgorithmStandaloneSetup.doSetup();
		StructuredTextEvaluatorFactory.register();
		FBEvaluatorFactory.register();
	}

	public static FB newFB(final String instanceName, final FBType instanceType) {
		final FB fb = LibraryElementFactory.eINSTANCE.createFB();
		fb.setName(instanceName);
		fb.setTypeEntry(instanceType.getTypeEntry());
		fb.setInterface(InterfaceListCopier.copy(instanceType.getInterfaceList()));
		return fb;
	}

	public static InterfaceList newInterfaceList(final Collection<Event> events,
			final Collection<VarDeclaration> vars) {
		final InterfaceList iface = LibraryElementFactory.eINSTANCE.createInterfaceList();
		events.stream().filter(IInterfaceElement::isIsInput).forEachOrdered(iface.getEventInputs()::add);
		events.stream().filter(Predicate.not(IInterfaceElement::isIsInput))
				.forEachOrdered(iface.getEventOutputs()::add);
		vars.stream().filter(IInterfaceElement::isIsInput).forEachOrdered(iface.getInputVars()::add);
		vars.stream().filter(Predicate.not(IInterfaceElement::isIsInput)).forEachOrdered(iface.getOutputVars()::add);
		return iface;
	}

	public static Event newEvent(final String name, final boolean input) {
		final Event event = LibraryElementFactory.eINSTANCE.createEvent();
		event.setName(name);
		event.setIsInput(input);
		return event;
	}

	public static VarDeclaration newVarDeclaration(final String name, final DataType type, final boolean input) {
		final VarDeclaration decl = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		decl.setName(name);
		decl.setType(type);
		decl.setIsInput(input);
		return decl;
	}

	public static VarDeclaration newVarDeclaration(final String name, final DataType type, final boolean input,
			final String defaultValue) {
		final VarDeclaration decl = newVarDeclaration(name, type, input);
		decl.setValue(newValue(defaultValue));
		return decl;
	}

	protected static Value newValue(final String defaultValue) {
		final Value value = LibraryElementFactory.eINSTANCE.createValue();
		value.setValue(defaultValue);
		return value;
	}

	public static Attribute newAttribute(final AttributeDeclaration attributeDeclaration) {
		final Attribute attr = LibraryElementFactory.eINSTANCE.createAttribute();
		attr.setName(attributeDeclaration.getName());
		attr.setType(attributeDeclaration.getType());
		attr.setAttributeDeclaration(attributeDeclaration);
		return attr;
	}

	public static Attribute newAttribute(final AttributeDeclaration attributeDeclaration, final String value) {
		final Attribute attr = newAttribute(attributeDeclaration);
		attr.setValue(value);
		return attr;
	}

	public static AttributeDeclaration newAttributeDeclaration(final String name,
			final Collection<VarDeclaration> vars) {
		final StructuredType structType = DataFactory.eINSTANCE.createStructuredType();
		structType.setName(name);
		structType.getMemberVariables().addAll(vars);
		final AttributeDeclaration attributeDeclaration = LibraryElementFactory.eINSTANCE.createAttributeDeclaration();
		attributeDeclaration.setName(name);
		attributeDeclaration.setType(structType);
		final AttributeTypeEntryMock typeEntry = new AttributeTypeEntryMock(attributeDeclaration, typeLib, null);
		attributeDeclaration.setTypeEntry(typeEntry);
		typeLib.addTypeEntry(typeEntry);
		new ResourceImpl().getContents().add(attributeDeclaration);
		return attributeDeclaration;
	}

	public static AttributeDeclaration newAttributeDeclaration(final String name, final DataType baseType,
			final String defaultValue) {
		final DirectlyDerivedType derivedType = DataFactory.eINSTANCE.createDirectlyDerivedType();
		derivedType.setName(name);
		derivedType.setBaseType(baseType);
		derivedType.setInitialValue(defaultValue);
		final AttributeDeclaration attributeDeclaration = LibraryElementFactory.eINSTANCE.createAttributeDeclaration();
		attributeDeclaration.setName(name);
		attributeDeclaration.setType(derivedType);
		final AttributeTypeEntryMock typeEntry = new AttributeTypeEntryMock(attributeDeclaration, typeLib, null);
		attributeDeclaration.setTypeEntry(typeEntry);
		typeLib.addTypeEntry(typeEntry);
		new ResourceImpl().getContents().add(attributeDeclaration);
		return attributeDeclaration;
	}

	public static DirectlyDerivedType newDirectlyDerivedType(final String name, final DataType baseType,
			final String defaultValue) {
		final DirectlyDerivedType derivedType = DataFactory.eINSTANCE.createDirectlyDerivedType();
		derivedType.setName(name);
		derivedType.setBaseType(baseType);
		derivedType.setInitialValue(defaultValue);
		final DataTypeEntryMock typeEntry = new DataTypeEntryMock(derivedType, typeLib, null);
		derivedType.setTypeEntry(typeEntry);
		typeLib.addTypeEntry(typeEntry);
		new ResourceImpl().getContents().add(derivedType);
		return derivedType;
	}

	protected StructuredType newStructuredType(final String name, final Collection<VarDeclaration> vars) {
		final StructuredType structType = DataFactory.eINSTANCE.createStructuredType();
		structType.setName(name);
		structType.getMemberVariables().add(newVarDeclaration("a", ElementaryTypes.DINT, false));
		structType.getMemberVariables().add(newVarDeclaration("b", ElementaryTypes.DINT, false));
		typeLib.addTypeEntry(new DataTypeEntryMock(structType, typeLib, null));
		final ResourceImpl structResource = new ResourceImpl();
		structResource.getContents().add(structType);
		return structType;
	}

	public static STAlgorithm newSTAlgorithm(final CharSequence text, final String name) {
		final STAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		alg.setName(name);
		alg.setText(text.toString());
		return alg;
	}

	public static STMethod newSTMethod(final CharSequence text, final String name) {
		final STMethod method = LibraryElementFactory.eINSTANCE.createSTMethod();
		method.setName(name);
		method.setText(text.toString());
		return method;
	}

	public static ElementaryVariable<AnyElementaryValue> newVariable(final AnyElementaryValue value,
			final String name) {
		return new ElementaryVariable<>(name, value.getType(), value);
	}

	public static SimpleFBType newSimpleFBType(final String name, final Collection<Event> events,
			final Collection<VarDeclaration> vars) {
		final SimpleFBType simpleType = LibraryElementFactory.eINSTANCE.createSimpleFBType();
		simpleType.setName(name);
		simpleType.setInterfaceList(newInterfaceList(events, vars));
		final FBTypeEntryMock typeEntry = new FBTypeEntryMock(simpleType, typeLib, null);
		simpleType.setTypeEntry(typeEntry);
		typeLib.addTypeEntry(typeEntry);
		new ResourceImpl().getContents().add(simpleType);
		return simpleType;
	}
}
