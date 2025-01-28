/**
 * Copyright (c) 2022, 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval.variable;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.AnyElementaryType;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorCache;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.Messages;
import org.eclipse.fordiac.ide.model.eval.value.FBValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

@SuppressWarnings("java:S1452")
public final class VariableOperations {
	public static Variable<?> newVariable(final String name, final INamedElement type) {
		return switch (type) {
		case final AnyType anyType when GenericTypes.isAnyType(anyType) -> new GenericVariable(name, anyType);
		case final ArrayType arrayType -> new ArrayVariable(name, arrayType);
		case final StructuredType structuredType -> new StructVariable(name, structuredType);
		case final EnumeratedType enumeratedType -> new EnumVariable(name, enumeratedType);
		case final DirectlyDerivedType directlyDerivedType -> new DirectlyDerivedVariable(name, directlyDerivedType);
		case final AnyElementaryType anyElementaryType -> new ElementaryVariable<>(name, anyElementaryType);
		case final FBType fbType -> new FBVariable(name, fbType);
		case null -> throw new NullPointerException(Messages.VariableOperations_TypeMustNotBeNull);
		default -> throw new UnsupportedOperationException(
				MessageFormat.format(Messages.VariableOperations_UnsupportedType, name, type.getName()));
		};
	}

	public static Variable<?> newVariable(final String name, final INamedElement type, final String value) {
		return switch (type) {
		case final AnyType anyType when GenericTypes.isAnyType(anyType) -> new GenericVariable(name, anyType, value);
		case final ArrayType arrayType -> new ArrayVariable(name, arrayType, value);
		case final StructuredType structuredType -> new StructVariable(name, structuredType, value);
		case final EnumeratedType enumeratedType -> new EnumVariable(name, enumeratedType, value);
		case final DirectlyDerivedType directlyDerivedType ->
			new DirectlyDerivedVariable(name, directlyDerivedType, value);
		case final AnyElementaryType anyElementaryType -> new ElementaryVariable<>(name, anyElementaryType, value);
		case final FBType fbType -> new FBVariable(name, fbType, value);
		case null -> throw new NullPointerException(Messages.VariableOperations_TypeMustNotBeNull);
		default -> throw new UnsupportedOperationException(
				MessageFormat.format(Messages.VariableOperations_UnsupportedType, name, type.getName()));
		};
	}

	public static Variable<?> newVariable(final String name, final INamedElement type, final Value value) {
		return switch (type) {
		case final AnyType anyType when GenericTypes.isAnyType(anyType) -> new GenericVariable(name, anyType, value);
		case final ArrayType arrayType -> new ArrayVariable(name, arrayType, value);
		case final StructuredType structuredType -> new StructVariable(name, structuredType, value);
		case final EnumeratedType enumeratedType -> new EnumVariable(name, enumeratedType, value);
		case final DirectlyDerivedType directlyDerivedType ->
			new DirectlyDerivedVariable(name, directlyDerivedType, value);
		case final AnyElementaryType anyElementaryType -> new ElementaryVariable<>(name, anyElementaryType, value);
		case final FBType fbType -> new FBVariable(name, fbType, value);
		case null -> throw new NullPointerException(Messages.VariableOperations_TypeMustNotBeNull);
		default -> throw new UnsupportedOperationException(
				MessageFormat.format(Messages.VariableOperations_UnsupportedType, name, type.getName()));
		};
	}

	public static Variable<?> newVariable(final String name, final Value value) {
		return switch (value.getType()) {
		case final DataType dataType when GenericTypes.isAnyType(dataType) -> throw new UnsupportedOperationException(
				MessageFormat.format(Messages.VariableOperations_UnsupportedType, name, value.getType().getName()));
		case null -> throw new NullPointerException(Messages.VariableOperations_TypeMustNotBeNull);
		default -> newVariable(name, value.getType(), value);
		};
	}

	public static Variable<?> newVariable(final Variable<?> variable) {
		return newVariable(variable.getName(), variable.getType(), variable.getValue());
	}

	public static Variable<?> newVariable(final ITypedElement element) throws EvaluatorException {
		return switch (element) {
		case final VarDeclaration varDeclaration -> newVariable(varDeclaration);
		case final Attribute attribute -> newVariable(attribute);
		case final DirectlyDerivedType directlyDerivedType -> newVariable(directlyDerivedType);
		case final FB fb -> newVariable(fb);
		default ->
			throw new UnsupportedOperationException(MessageFormat.format(Messages.VariableOperations_UnsupportedType,
					element.getName(), element.getType() != null ? element.getType().getName() : null));
		};
	}

	public static Variable<?> newVariable(final ITypedElement element, final String value) throws EvaluatorException {
		return switch (element) {
		case final VarDeclaration varDeclaration -> newVariable(varDeclaration, value);
		case final Attribute attribute -> newVariable(attribute, value);
		case final DirectlyDerivedType directlyDerivedType -> newVariable(directlyDerivedType, value);
		default ->
			throw new UnsupportedOperationException(MessageFormat.format(Messages.VariableOperations_UnsupportedType,
					element.getName(), element.getType() != null ? element.getType().getName() : null));
		};
	}

	public static Variable<?> newVariable(final VarDeclaration varDeclaration) throws EvaluatorException {
		if (hasInitialValue(varDeclaration)) {
			try (EvaluatorCache cache = EvaluatorCache.open()) {
				if (hasDeclaredInitialValue(varDeclaration)) {
					return newVariable(varDeclaration.getName(), evaluateResultType(varDeclaration),
							cache.computeInitialValueIfAbsent(varDeclaration, VariableOperations::doEvaluateValue));
				}
				if (hasInheritedInitialValue(varDeclaration)) {
					// use variable from FB type since the initial value is inherited
					final VarDeclaration typeVariable = getTypeVariable(varDeclaration);
					return newVariable(varDeclaration.getName(), evaluateResultType(varDeclaration),
							cache.computeInitialValueIfAbsent(typeVariable, VariableOperations::doEvaluateValue));
				}
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return newVariable(varDeclaration.getName(), evaluateResultType(varDeclaration));
	}

	private static Value doEvaluateValue(final VarDeclaration varDeclaration)
			throws EvaluatorException, InterruptedException {
		final Evaluator evaluator = EvaluatorFactory.createEvaluator(varDeclaration, VarDeclaration.class, null,
				Collections.emptySet(), null);
		if (evaluator instanceof VariableEvaluator) {
			return evaluator.evaluate();
		}
		throw new UnsupportedOperationException(Messages.VariableOperations_NoEvaluatorForVarDeclaration);
	}

	public static Variable<?> newVariable(final VarDeclaration varDeclaration, final String initialValue)
			throws EvaluatorException {
		return newVariable(withValue(varDeclaration, initialValue));
	}

	public static Variable<?> newVariable(final VarDeclaration varDeclaration, final Value value)
			throws EvaluatorException {
		return newVariable(varDeclaration.getName(), evaluateResultType(varDeclaration), value);
	}

	public static FBVariable newVariable(final FB fb) {
		return new FBVariable(fb.getName(), fb.getType(), new FBValue(fb));
	}

	public static Variable<?> newVariable(final Attribute attribute) throws EvaluatorException {
		if (hasValue(attribute)) {
			try {
				final Evaluator evaluator = EvaluatorFactory.createEvaluator(attribute, Attribute.class, null,
						Collections.emptySet(), null);
				if (evaluator instanceof final VariableEvaluator variableEvaluator) {
					return variableEvaluator.evaluateVariable();
				}
				throw new UnsupportedOperationException(Messages.VariableOperations_NoEvaluatorForAttribute);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return newVariable(attribute.getName(), attribute.getType());
	}

	public static Variable<?> newVariable(final Attribute attribute, final String initialValue)
			throws EvaluatorException {
		return newVariable(withValue(attribute, initialValue));
	}

	public static Variable<?> newVariable(final DirectlyDerivedType type) throws EvaluatorException {
		if (hasInitialValue(type)) {
			try {
				final Evaluator evaluator = EvaluatorFactory.createEvaluator(type, DirectlyDerivedType.class, null,
						Collections.emptySet(), null);
				if (evaluator instanceof final VariableEvaluator variableEvaluator) {
					return variableEvaluator.evaluateVariable();
				}
				throw new UnsupportedOperationException(Messages.VariableOperations_NoEvaluatorForDirectlyDerivedType);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return newVariable(type.getName(), type.getBaseType());
	}

	public static Variable<?> newVariable(final DirectlyDerivedType type, final String initialValue)
			throws EvaluatorException {
		return newVariable(withValue(type, initialValue));
	}

	public static INamedElement evaluateResultType(final VarDeclaration decl) throws EvaluatorException {
		if (decl.isArray()) {
			try (EvaluatorCache cache = EvaluatorCache.open()) {
				return cache.computeResultTypeIfAbsent(decl, VariableOperations::doEvaluateResultType);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return decl.getType();
	}

	private static INamedElement doEvaluateResultType(final VarDeclaration varDeclaration)
			throws EvaluatorException, InterruptedException {
		if (TypeDeclarationParser.isSimpleTypeDeclaration(varDeclaration.getArraySize().getValue())) {
			return TypeDeclarationParser.parseSimpleTypeDeclaration(varDeclaration.getType(),
					varDeclaration.getArraySize().getValue());
		}
		final Evaluator evaluator = EvaluatorFactory.createEvaluator(varDeclaration, VarDeclaration.class, null,
				Collections.emptySet(), null);
		if (evaluator instanceof final VariableEvaluator variableEvaluator) {
			return variableEvaluator.evaluateResultType();
		}
		throw new UnsupportedOperationException(Messages.VariableOperations_NoEvaluatorForVarDeclaration);
	}

	public static String validateValue(final VarDeclaration varDeclaration) {
		if (isSimpleInitialValue(varDeclaration)) {
			return ""; //$NON-NLS-1$
		}
		final Evaluator evaluator = EvaluatorFactory.createEvaluator(varDeclaration, VarDeclaration.class, null,
				Collections.emptySet(), null);
		if (evaluator instanceof VariableEvaluator) {
			try {
				evaluator.prepare();
				return ""; //$NON-NLS-1$
			} catch (final Exception e) {
				return e.getMessage();
			}
		}
		return Messages.VariableOperations_NoEvaluatorForVarDeclaration;
	}

	public static String validateValue(final DirectlyDerivedType type) {
		if (!hasInitialValue(type)) {
			return ""; //$NON-NLS-1$
		}
		final Evaluator evaluator = EvaluatorFactory.createEvaluator(type, DirectlyDerivedType.class, null,
				Collections.emptySet(), null);
		if (evaluator instanceof VariableEvaluator) {
			try {
				evaluator.prepare();
				return ""; //$NON-NLS-1$
			} catch (final Exception e) {
				return e.getMessage();
			}
		}
		return Messages.VariableOperations_NoEvaluatorForDirectlyDerivedType;
	}

	public static boolean validateValue(final VarDeclaration varDeclaration, final List<String> errors,
			final List<String> warnings, final List<String> infos) {
		if (isSimpleInitialValue(varDeclaration)) {
			return true;
		}
		final Evaluator evaluator = EvaluatorFactory.createEvaluator(varDeclaration, VarDeclaration.class, null,
				Collections.emptySet(), null);
		if (evaluator instanceof final VariableEvaluator variableEvaluator) {
			try {
				return variableEvaluator.validateVariable(errors, warnings, infos);
			} catch (final EvaluatorException e) {
				errors.add(e.getMessage());
				return false;
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		errors.add(Messages.VariableOperations_NoEvaluatorForVarDeclaration);
		return false;
	}

	public static boolean validateType(final VarDeclaration varDeclaration, final List<String> errors,
			final List<String> warnings, final List<String> infos) {
		if (!varDeclaration.isArray()
				|| TypeDeclarationParser.isSimpleTypeDeclaration(varDeclaration.getArraySize().getValue())) {
			return true;
		}
		final Evaluator evaluator = EvaluatorFactory.createEvaluator(varDeclaration, VarDeclaration.class, null,
				Collections.emptySet(), null);
		if (evaluator instanceof final VariableEvaluator variableEvaluator) {
			try {
				return variableEvaluator.validateResultType(errors, warnings, infos);
			} catch (final EvaluatorException e) {
				errors.add(e.getMessage());
				return false;
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		errors.add(Messages.VariableOperations_NoEvaluatorForVarDeclaration);
		return false;
	}

	public static boolean validateValue(final Attribute attribute, final List<String> errors,
			final List<String> warnings, final List<String> infos) {
		if (isSimpleAttributeValue(attribute)) {
			return true;
		}
		final Evaluator evaluator = EvaluatorFactory.createEvaluator(attribute, Attribute.class, null,
				Collections.emptySet(), null);
		if (evaluator instanceof final VariableEvaluator variableEvaluator) {
			try {
				return variableEvaluator.validateVariable(errors, warnings, infos);
			} catch (final EvaluatorException e) {
				errors.add(e.getMessage());
				return false;
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		errors.add(Messages.VariableOperations_NoEvaluatorForVarDeclaration);
		return false;
	}

	public static String validateValue(final VarDeclaration varDeclaration, final String initialValue) {
		return validateValue(withValue(varDeclaration, initialValue));
	}

	public static String validateValue(final DataType dataType, final String initialValue) {
		return validateValue(withValue(dataType, initialValue));
	}

	public static Value evaluateValue(final DataType dataType, final String initialValue) throws EvaluatorException {
		return newVariable(withValue(dataType, initialValue)).getValue();
	}

	public static Set<String> getDependencies(final VarDeclaration varDeclaration) {
		if (!isSimpleInitialValue(varDeclaration) || (varDeclaration.isArray()
				&& !TypeDeclarationParser.isSimpleTypeDeclaration(varDeclaration.getArraySize().getValue()))) {
			final Evaluator evaluator = EvaluatorFactory.createEvaluator(varDeclaration, VarDeclaration.class, null,
					Collections.emptySet(), null);
			if (evaluator instanceof final VariableEvaluator variableEvaluator) {
				return variableEvaluator.getDependencies();
			}
			throw new UnsupportedOperationException(Messages.VariableOperations_NoEvaluatorForVarDeclaration);
		}
		return Set.of(PackageNameHelper.getFullTypeName(varDeclaration.getType()));
	}

	public static Set<String> getDependencies(final Attribute attribute) {
		if (InternalAttributeDeclarations.isInternalAttribute(attribute)) {
			return Set.of();
		}
		if (!isSimpleAttributeValue(attribute)) {
			final Evaluator evaluator = EvaluatorFactory.createEvaluator(attribute, VarDeclaration.class, null,
					Collections.emptySet(), null);
			if (evaluator instanceof final VariableEvaluator variableEvaluator) {
				return variableEvaluator.getDependencies();
			}
			throw new UnsupportedOperationException(Messages.VariableOperations_NoEvaluatorForVarDeclaration);
		}
		if (attribute.getAttributeDeclaration() != null) {
			return Set.of(PackageNameHelper.getFullTypeName(attribute.getAttributeDeclaration()));
		}
		return Set.of(PackageNameHelper.getFullTypeName(attribute.getType()));
	}

	public static Set<String> getAllDependencies(final EObject object) {
		final Evaluator evaluator = EvaluatorFactory.createEvaluator(object, object.eClass().getInstanceClass(), null,
				Collections.emptySet(), null);
		if (evaluator != null) {
			return evaluator.getDependencies();
		}
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(object.eAllContents(), 0), false)
				.map(element -> switch (element) {
				case final Attribute attribute -> getDependencies(attribute);
				case final VarDeclaration varDeclaration -> getDependencies(varDeclaration);
				default -> Collections.<String>emptySet();
				}).flatMap(Collection::stream).collect(Collectors.toSet());
	}

	public static boolean hasValue(final Attribute attribute) {
		return attribute.getValue() != null && !attribute.getValue().isEmpty();
	}

	public static boolean hasInitialValue(final DirectlyDerivedType type) {
		return type.getInitialValue() != null && !type.getInitialValue().isEmpty();
	}

	public static boolean hasInitialValue(final VarDeclaration varDeclaration) {
		return hasDeclaredInitialValue(varDeclaration) || hasInheritedInitialValue(varDeclaration);
	}

	public static boolean hasDeclaredInitialValue(final VarDeclaration varDeclaration) {
		return varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null
				&& !varDeclaration.getValue().getValue().isEmpty();
	}

	public static boolean hasInheritedInitialValue(final VarDeclaration varDeclaration) {
		final VarDeclaration typeVariable = getTypeVariable(varDeclaration);
		if (typeVariable != null) {
			return hasDeclaredInitialValue(typeVariable);
		}
		return false;
	}

	public static String getInitialValue(final VarDeclaration varDeclaration) {
		final String declaredInitialValue = getDeclaredInitialValue(varDeclaration);
		if (declaredInitialValue != null) {
			return declaredInitialValue;
		}
		return getInheritedInitialValue(varDeclaration);
	}

	public static String getDeclaredInitialValue(final VarDeclaration varDeclaration) {
		if (hasDeclaredInitialValue(varDeclaration)) {
			return varDeclaration.getValue().getValue();
		}
		return null;
	}

	public static String getInheritedInitialValue(final VarDeclaration varDeclaration) {
		final VarDeclaration typeVariable = getTypeVariable(varDeclaration);
		if (typeVariable != null && hasDeclaredInitialValue(typeVariable)) {
			return typeVariable.getValue().getValue();
		}
		return null;
	}

	private static VarDeclaration getTypeVariable(final VarDeclaration varDeclaration) {
		final FBNetworkElement networkElement = varDeclaration.getFBNetworkElement();
		if (networkElement != null) {
			final FBType type = networkElement.getType();
			if (type != null) {
				final VarDeclaration typeVariable = type.getInterfaceList().getVariable(varDeclaration.getName());
				if (typeVariable != null) {
					return typeVariable;
				}
			}
		}
		return null;
	}

	private static VarDeclaration withValue(final VarDeclaration varDeclaration, final String valueString) {
		if (Objects.equals(valueString, getDeclaredInitialValue(varDeclaration))) {
			return varDeclaration;
		}
		final VarDeclaration copy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		copy.setName(varDeclaration.getName());
		copy.setType(varDeclaration.getType());
		ArraySizeHelper.setArraySize(copy, ArraySizeHelper.getArraySize(varDeclaration));
		final org.eclipse.fordiac.ide.model.libraryElement.Value value = LibraryElementFactory.eINSTANCE.createValue();
		value.setValue(valueString);
		copy.setValue(value);
		if (varDeclaration.eResource() != null) {
			new ResourceImpl(varDeclaration.eResource().getURI()).getContents().add(copy);
		}
		return copy;
	}

	private static Attribute withValue(final Attribute attribute, final String valueString) {
		if (Objects.equals(valueString, attribute.getValue())) {
			return attribute;
		}
		final Attribute copy = LibraryElementFactory.eINSTANCE.createAttribute();
		copy.setName(attribute.getName());
		copy.setType(attribute.getType());
		copy.setAttributeDeclaration(attribute.getAttributeDeclaration());
		copy.setValue(valueString);
		if (attribute.eResource() != null) {
			new ResourceImpl(attribute.eResource().getURI()).getContents().add(copy);
		}
		return copy;
	}

	private static DirectlyDerivedType withValue(final DataType type, final String valueString) {
		if (type instanceof final DirectlyDerivedType directlyDerivedType
				&& Objects.equals(valueString, directlyDerivedType.getInitialValue())) {
			return directlyDerivedType;
		}
		final DirectlyDerivedType copy = DataFactory.eINSTANCE.createDirectlyDerivedType();
		copy.setName(type.getName());
		copy.setBaseType(type);
		copy.setInitialValue(valueString);
		if (type instanceof AnyDerivedType && type.eResource() != null) {
			new ResourceImpl(type.eResource().getURI()).getContents().add(copy);
		}
		return copy;
	}

	private static boolean isSimpleInitialValue(final VarDeclaration varDeclaration) {
		if (varDeclaration.isArray()) {
			return false;
		}
		if (hasDeclaredInitialValue(varDeclaration)) {
			try {
				new TypedValueConverter(varDeclaration.getType(), true)
						.toValue(getDeclaredInitialValue(varDeclaration));
			} catch (final Exception e) {
				return false;
			}
		}
		return true;
	}

	private static boolean isSimpleAttributeValue(final Attribute attribute) {
		if (hasValue(attribute) && attribute.getType() instanceof final AnyType type) {
			try {
				new TypedValueConverter(type, true).toValue(attribute.getValue());
			} catch (final Exception e) {
				return false;
			}
		}
		return true;
	}

	private VariableOperations() {
		throw new UnsupportedOperationException();
	}
}
