/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.function;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public interface Functions {

	/**
	 * Invoke function from {@code clazz} with the given name that is applicable for
	 * the arguments
	 *
	 * @param clazz     The class defining a set of functions
	 * @param name      The name of the function
	 * @param arguments The arguments used to select and invoke the function
	 * @return The result of the function
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 * @throws Throwable             for any other error during invocation
	 */
	static Value invoke(final Class<? extends Functions> clazz, final String name, final Object... arguments)
			throws Throwable {
		return invoke(clazz, name, List.of(arguments));
	}

	/**
	 * Invoke function from {@code clazz} with the given name that is applicable for
	 * the arguments
	 *
	 * @param clazz     The class defining a set of functions
	 * @param name      The name of the function
	 * @param arguments The arguments used to select and invoke the function
	 * @return The result of the function
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 * @throws Throwable             for any other error during invocation
	 */
	static Value invoke(final Class<? extends Functions> clazz, final String name, final List<Object> arguments)
			throws Throwable {
		final Method method = findMethod(clazz, name,
				arguments.stream().map(argument -> argument != null ? argument.getClass() : Object.class).toList());
		final MethodHandle handle = MethodHandles.lookup().unreflect(method);
		return (Value) handle.invokeWithArguments(arguments);
	}

	/**
	 * Find a function from {@code clazz} with the given name that is applicable for
	 * the concrete argument types
	 *
	 * @param clazz         The class defining a set of functions
	 * @param name          The name of the function
	 * @param argumentTypes The argument types used to select the function
	 * @return The method reference corresponding to the function
	 * @throws SecurityException     if access to the method is denied
	 * @throws NoSuchMethodException if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 */
	static Method findMethod(final Class<? extends Functions> clazz, final String name, final Class<?>... argumentTypes)
			throws NoSuchMethodException, SecurityException {
		return findMethod(clazz, name, List.of(argumentTypes));
	}

	/**
	 * Find a function from {@code clazz} with the given name that is applicable for
	 * the concrete argument types
	 *
	 * @param clazz         The class defining a set of functions
	 * @param name          The name of the function
	 * @param argumentTypes The argument types used to select the function
	 * @return The method reference corresponding to the function
	 * @throws SecurityException     if access to the method is denied
	 * @throws NoSuchMethodException if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 */
	static Method findMethod(final Class<? extends Functions> clazz, final String name,
			final List<Class<?>> argumentTypes) throws NoSuchMethodException, SecurityException {
		try { // try exact match first
			return clazz.getMethod(name, argumentTypes.toArray(new Class[argumentTypes.size()]));
		} catch (final NoSuchMethodException e) { // try inexact match
			final List<Method> methods = Stream.of(clazz.getMethods())
					.filter(method -> method.getName().equals(name)
							&& isAssignableFrom(List.of(method.getParameterTypes()), argumentTypes, method.isVarArgs()))
					.toList();
			if (methods.isEmpty()) {
				throw new NoSuchMethodException(
						String.format("No method with name %s in class %s", name, clazz.getName())); //$NON-NLS-1$
			}
			if (methods.size() > 1) {
				throw new IllegalStateException(String.format(
						"Multiple candidates found for method with name %s in class %s", name, clazz.getName())); //$NON-NLS-1$
			}
			return methods.get(0);
		}
	}

	/**
	 * Find all functions from {@code clazz} with the given name
	 *
	 * @param clazz The class defining a set of functions
	 * @param name  The name of the function
	 * @return The method references corresponding to the functions
	 */
	static List<Method> findMethods(final Class<? extends Functions> clazz, final String name) {
		return Stream.of(clazz.getMethods()).filter(method -> method.getName().equals(name)).toList();
	}

	/**
	 * Find all functions from {@code clazz} applicable for the concrete argument
	 * types
	 *
	 * @param clazz         The class defining a set of functions
	 * @param argumentTypes The argument types used to select the function
	 * @return The method references corresponding to the functions
	 */
	static List<Method> findMethods(final Class<? extends Functions> clazz, final List<Class<?>> argumentTypes) {
		final Map<String, Method> exactMatches = Stream.of(clazz.getMethods())
				.filter(method -> List.of(method.getParameterTypes()).equals(argumentTypes))
				.collect(Collectors.toMap(Method::getName, Function.identity()));
		return Stream.concat(exactMatches.values().stream(), // first add exact matches
				// add inexact matches only if there is no exact match
				Stream.of(clazz.getMethods()).filter(method -> !exactMatches.containsKey(method.getName())
						&& isAssignableFrom(List.of(method.getParameterTypes()), argumentTypes, method.isVarArgs())))
				.toList();
	}

	/**
	 * Find all functions from {@code clazz}
	 *
	 * @param clazz The class defining a set of functions
	 * @return The method references corresponding to the functions
	 */
	static List<Method> getMethods(final Class<? extends Functions> clazz) {
		return List.of(clazz.getMethods());
	}

	/**
	 * Infer the concrete return type (i.e., resolve generic type) for a function
	 *
	 * @param clazz         The class defining a set of functions
	 * @param name          The name of the function
	 * @param argumentTypes The argument types used to select the function and infer
	 *                      the return type
	 * @return The concrete return type of the function based on the argument types
	 * @throws SecurityException     if access to the method is denied
	 * @throws NoSuchMethodException if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 */
	static Class<?> inferReturnType(final Class<? extends Functions> clazz, final String name,
			final List<Class<?>> argumentTypes) throws NoSuchMethodException, SecurityException {
		return inferReturnType(findMethod(clazz, name, argumentTypes), argumentTypes);
	}

	/**
	 * Infer the concrete return type (i.e., resolve generic type) for a function
	 *
	 * @param method        The method reference corresponding to the function
	 * @param argumentTypes The argument types used to infer the return type
	 * @return The concrete return type of the function based on the argument types
	 */
	static Class<?> inferReturnType(final Method method, final List<Class<?>> argumentTypes) {
		try {
			final Type returnType = method.getGenericReturnType();
			if (returnType instanceof TypeVariable<?>) {
				Class<?> result = null;
				for (int i = 0; i < argumentTypes.size(); ++i) {
					final Type parameterTypeForArgument = getGenericParameterValueType(method, i);
					if (parameterTypeForArgument != null && parameterTypeForArgument.equals(returnType)) {
						result = commonSupertype(result, argumentTypes.get(i));
					}
				}
				if (result != null && method.getReturnType().isAssignableFrom(result)) {
					return result;
				}
			}
		} catch (final Exception e) {
			// ignore
		}
		return method.getReturnType() != void.class ? method.getReturnType() : null;
	}

	/**
	 * Infer the concrete parameter types (i.e., resolve generic types) for a
	 * function
	 *
	 * @param clazz         The class defining a set of functions
	 * @param name          The name of the function
	 * @param argumentTypes The argument types used to select the function and infer
	 *                      the return type
	 * @return The concrete parameter types of the function based on the argument
	 *         types
	 * @throws SecurityException     if access to the method is denied
	 * @throws NoSuchMethodException if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 */
	static List<Class<?>> inferParameterTypes(final Class<? extends Functions> clazz, final String name,
			final List<Class<?>> argumentTypes) throws NoSuchMethodException, SecurityException {
		return inferParameterTypes(findMethod(clazz, name, argumentTypes), argumentTypes);
	}

	/**
	 * Infer the concrete parameter types (i.e., resolve generic types) for a
	 * function
	 *
	 * @param method        The method reference corresponding to the function
	 * @param argumentTypes The argument types used to infer the return type
	 * @return The concrete parameter types of the function based on the argument
	 *         types
	 */
	static List<Class<?>> inferParameterTypes(final Method method, final List<Class<?>> argumentTypes) {
		return IntStream.range(0, Math.max(method.getParameterCount(), argumentTypes.size()))
				.<Class<?>>mapToObj(i -> inferParameterType(method, argumentTypes, i)).filter(Objects::nonNull)
				.toList();
	}

	/**
	 * Infer a single concrete parameter type (i.e., resolve generic type) for a
	 * function
	 *
	 * @param method        The method reference corresponding to the function
	 * @param argumentTypes The argument types used to infer the return type
	 * @param index         The parameter index
	 * @return The concrete parameter type of the function based on the argument
	 *         types
	 */
	static Class<?> inferParameterType(final Method method, final List<Class<?>> argumentTypes, final int index) {
		try {
			final Type parameterType = getGenericParameterValueType(method, index);
			if (parameterType instanceof final TypeVariable<?> parameterTypeVariable) {
				final var parameterTypeBound = getCommonTypeBound(parameterTypeVariable);
				if (parameterTypeBound != null) {
					Class<?> result = null;
					for (int i = 0; i < argumentTypes.size(); ++i) {
						final Type parameterTypeForArgument = getGenericParameterValueType(method, i);
						if (parameterTypeForArgument != null && parameterTypeForArgument.equals(parameterType)) {
							result = commonSupertype(result, argumentTypes.get(i));
						}
					}
					if (result != null && parameterTypeBound.isAssignableFrom(result)) {
						return result;
					}
					return parameterTypeBound;
				}
			} else if (parameterType instanceof final Class<?> parameterClass) {
				return parameterClass;
			}
		} catch (final Exception e) {
			// ignore
		}
		return getParameterValueType(method, index);
	}

	/**
	 * Infer the expected parameter type (i.e., resolve generic type) for a function
	 * and expected return type
	 *
	 * @param method             The method reference corresponding to the function
	 * @param expectedReturnType The expected return type used to infer the expected
	 *                           parameter type (may be null)
	 * @param index              The parameter index
	 * @return The expected parameter type of the function based on the expected
	 *         return type
	 */
	static Class<?> inferExpectedParameterType(final Method method, final Class<?> expectedReturnType,
			final int index) {
		try {
			final Type parameterType = getGenericParameterValueType(method, index);
			if (parameterType instanceof final TypeVariable<?> parameterTypeVariable) {
				final var parameterTypeBound = getCommonTypeBound(parameterTypeVariable);
				if (parameterTypeBound != null) {
					final Type returnType = method.getGenericReturnType();
					if (returnType instanceof TypeVariable<?> && parameterType.equals(returnType)
							&& expectedReturnType != null && parameterTypeBound.isAssignableFrom(expectedReturnType)) {
						return expectedReturnType;
					}
					return parameterTypeBound;
				}
			} else if (parameterType instanceof final Class<?> parameterClass) {
				return parameterClass;
			}
		} catch (final Exception e) {
			// ignore
		}
		return getParameterValueType(method, index);
	}

	/**
	 * Find a function from {@code clazz} with the given name that is applicable for
	 * the concrete argument types
	 *
	 * @param clazz         The class defining a set of functions
	 * @param name          The name of the function
	 * @param argumentTypes The argument types used to select the function
	 * @return The method reference corresponding to the function
	 * @throws SecurityException     if access to the method is denied
	 * @throws NoSuchMethodException if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 */
	static Method findMethodFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final DataType... argumentTypes) throws NoSuchMethodException, SecurityException {
		return findMethodFromDataTypes(clazz, name, List.of(argumentTypes));
	}

	/**
	 * Find a function from {@code clazz} with the given name that is applicable for
	 * the concrete argument types
	 *
	 * @param clazz         The class defining a set of functions
	 * @param name          The name of the function
	 * @param argumentTypes The argument types used to select the function
	 * @return The method reference corresponding to the function
	 * @throws SecurityException     if access to the method is denied
	 * @throws NoSuchMethodException if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 */
	static Method findMethodFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final List<DataType> argumentTypes) throws NoSuchMethodException, SecurityException {
		return findMethod(clazz, name, getValueTypes(argumentTypes));
	}

	/**
	 * Find all functions from {@code clazz} applicable for the concrete argument
	 * types
	 *
	 * @param clazz         The class defining a set of functions
	 * @param argumentTypes The argument types used to select the function
	 * @return The method references corresponding to the functions
	 */
	static List<Method> findMethodsFromDataTypes(final Class<? extends Functions> clazz,
			final List<DataType> argumentTypes) {
		return findMethods(clazz, getValueTypes(argumentTypes));
	}

	/**
	 * Infer the concrete return type (i.e., resolve generic type) for a function
	 *
	 * @param clazz         The class defining a set of functions
	 * @param name          The name of the function
	 * @param argumentTypes The argument types used to select the function and infer
	 *                      the return type
	 * @return The concrete return type of the function based on the argument types
	 * @throws SecurityException     if access to the method is denied
	 * @throws NoSuchMethodException if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 */
	static DataType inferReturnTypeFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final DataType... argumentTypes) throws NoSuchMethodException, SecurityException {
		return inferReturnTypeFromDataTypes(clazz, name, List.of(argumentTypes));
	}

	/**
	 * Infer the concrete return type (i.e., resolve generic type) for a function
	 *
	 * @param clazz              The class defining a set of functions
	 * @param name               The name of the function
	 * @param expectedReturnType The expected return type used to infer the return
	 *                           type (may be null)
	 * @param argumentTypes      The argument types used to select the function and
	 *                           infer the return type
	 * @return The concrete return type of the function based on the argument types
	 * @throws SecurityException     if access to the method is denied
	 * @throws NoSuchMethodException if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 */
	static DataType inferReturnTypeFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final List<DataType> argumentTypes) throws NoSuchMethodException, SecurityException {
		return inferReturnTypeFromDataTypes(findMethodFromDataTypes(clazz, name, argumentTypes), null,
				() -> argumentTypes);
	}

	/**
	 * Infer the concrete return type (i.e., resolve generic type) for a function
	 *
	 * @param method                     The method reference corresponding to the
	 *                                   function
	 * @param expectedReturnTypeSupplier The expected return type used to infer the
	 *                                   return type (may be null and return null)
	 * @param argumentTypesSupplier      The argument types used to infer the return
	 *                                   type (may neither be null nor return null)
	 * @return The concrete return type of the function based on the argument types
	 */
	static DataType inferReturnTypeFromDataTypes(final Method method,
			final Supplier<DataType> expectedReturnTypeSupplier, final Supplier<List<DataType>> argumentTypesSupplier) {
		try {
			final Type returnType = method.getGenericReturnType();
			if (returnType instanceof TypeVariable<?>) {
				DataType result = null;
				final List<DataType> argumentTypes = argumentTypesSupplier.get();
				for (int i = 0; i < argumentTypes.size(); ++i) {
					final Type parameterTypeForArgument = getGenericParameterValueType(method, i);
					if (parameterTypeForArgument != null && parameterTypeForArgument.equals(returnType)) {
						result = commonSupertype(result, argumentTypes.get(i));
					}
				}
				if (result != null && ValueOperations.dataType(method.getReturnType()).isAssignableFrom(result)) {
					return result;
				}
			} else if (returnType instanceof final Class<?> returnTypeClass && returnType != void.class) {
				final DataType result = ValueOperations.dataType(returnTypeClass);
				if (GenericTypes.isAnyType(result) && expectedReturnTypeSupplier != null) {
					final DataType expectedReturnType = expectedReturnTypeSupplier.get();
					if (expectedReturnType != null && result.isAssignableFrom(expectedReturnType)) {
						return expectedReturnType;
					}
				}
				return result;
			}
		} catch (final Exception e) {
			// ignore
		}
		return method.getReturnType() != void.class ? ValueOperations.dataType(method.getReturnType()) : null;
	}

	/**
	 * Infer the concrete return type (i.e., resolve generic type) for a function
	 *
	 * @param method        The method reference corresponding to the function
	 * @param argumentTypes The argument types used to infer the return type
	 * @return The concrete return type of the function based on the argument types
	 * @throws SecurityException     if access to the method is denied
	 * @throws NoSuchMethodException if no applicable method could be found
	 */
	static List<DataType> inferParameterTypesFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final DataType... argumentTypes) throws NoSuchMethodException, SecurityException {
		return inferParameterTypesFromDataTypes(clazz, name, List.of(argumentTypes));
	}

	/**
	 * Infer the concrete parameter types (i.e., resolve generic types) for a
	 * function
	 *
	 * @param clazz         The class defining a set of functions
	 * @param name          The name of the function
	 * @param argumentTypes The argument types used to select the function and infer
	 *                      the return type
	 * @return The concrete parameter types of the function based on the argument
	 *         types
	 * @throws SecurityException     if access to the method is denied
	 * @throws NoSuchMethodException if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 */
	static List<DataType> inferParameterTypesFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final List<DataType> argumentTypes) throws NoSuchMethodException, SecurityException {
		return inferParameterTypesFromDataTypes(findMethodFromDataTypes(clazz, name, argumentTypes), argumentTypes);
	}

	/**
	 * Infer the concrete parameter types (i.e., resolve generic types) for a
	 * function
	 *
	 * @param method        The method reference corresponding to the function
	 * @param argumentTypes The argument types used to infer the return type
	 * @return The concrete parameter types of the function based on the argument
	 *         types
	 */
	static List<DataType> inferParameterTypesFromDataTypes(final Method method, final List<DataType> argumentTypes) {
		return IntStream.range(0, Math.max(method.getParameterCount(), argumentTypes.size()))
				.mapToObj(i -> inferParameterTypeFromDataType(method, argumentTypes, i)).filter(Objects::nonNull)
				.toList();
	}

	/**
	 * Infer a single concrete parameter type (i.e., resolve generic type) for a
	 * function
	 *
	 * @param method        The method reference corresponding to the function
	 * @param argumentTypes The argument types used to infer the return type
	 * @param index         The parameter index
	 * @return The concrete parameter type of the function based on the argument
	 *         types
	 */
	static DataType inferParameterTypeFromDataType(final Method method, final List<DataType> argumentTypes,
			final int index) {
		try {
			final Type parameterType = getGenericParameterValueType(method, index);
			if (parameterType instanceof final TypeVariable<?> parameterTypeVariable) {
				final var parameterTypeBound = ValueOperations.dataType(getCommonTypeBound(parameterTypeVariable));
				if (parameterTypeBound != null) {
					DataType result = null;
					for (int i = 0; i < argumentTypes.size(); ++i) {
						final Type parameterTypeForArgument = getGenericParameterValueType(method, i);
						if (parameterTypeForArgument != null && parameterTypeForArgument.equals(parameterType)) {
							result = commonSupertype(result, argumentTypes.get(i));
						}
					}
					if (result != null && parameterTypeBound.isAssignableFrom(result)) {
						return result;
					}
					return parameterTypeBound;
				}
			} else if (parameterType instanceof final Class<?> parameterClass) {
				return ValueOperations.dataType(parameterClass);
			}
		} catch (final Exception e) {
			// ignore
		}
		return ValueOperations.dataType(getParameterValueType(method, index));
	}

	/**
	 * Infer the expected parameter type (i.e., resolve generic type) for a function
	 * and expected return type
	 *
	 * @param method             The method reference corresponding to the function
	 * @param expectedReturnType The expected return type used to infer the expected
	 *                           parameter type (may be null)
	 * @param argumentTypes      The argument types used to infer the expected
	 *                           parameter type (may contain null)
	 * @param index              The parameter index
	 * @return The expected parameter type of the function based on the expected
	 *         return type
	 */
	static DataType inferExpectedParameterTypeFromDataType(final Method method, final DataType expectedReturnType,
			final List<DataType> argumentTypes, final int index) {
		try {
			final Type parameterType = getGenericParameterValueType(method, index);
			if (parameterType instanceof final TypeVariable<?> parameterTypeVariable) {
				final var parameterTypeBound = ValueOperations.dataType(getCommonTypeBound(parameterTypeVariable));
				if (parameterTypeBound != null) {
					// try expected return type
					final Type returnType = method.getGenericReturnType();
					if (returnType instanceof TypeVariable<?> && parameterType.equals(returnType)
							&& expectedReturnType != null && parameterTypeBound.isAssignableFrom(expectedReturnType)) {
						return expectedReturnType;
					}
					// try other parameters
					DataType result = null;
					for (int i = 0; i < argumentTypes.size(); ++i) {
						final Type parameterTypeForArgument = getGenericParameterValueType(method, i);
						if (parameterTypeForArgument != null && parameterTypeForArgument.equals(parameterType)) {
							result = commonSupertype(result, argumentTypes.get(i));
						}
					}
					if (result != null && parameterTypeBound.isAssignableFrom(result)) {
						return result;
					}
					return parameterTypeBound;
				}
			} else if (parameterType instanceof final Class<?> parameterClass) {
				return ValueOperations.dataType(parameterClass);
			}
		} catch (final Exception e) {
			// ignore
		}
		return ValueOperations.dataType(getParameterValueType(method, index));
	}

	/**
	 * Get the generic parameter value type
	 *
	 * @param method The method reference corresponding to the function
	 * @param index  The index of the parameter
	 * @return The generic parameter type
	 */
	static Type getGenericParameterValueType(final Method method, final int index) {
		final Type type = getGenericParameterType(method, index);
		if (type instanceof final ParameterizedType parameterizedType
				&& parameterizedType.getRawType() == Variable.class) {
			return parameterizedType.getActualTypeArguments()[0];
		}
		return type;
	}

	/**
	 * Get the generic parameter type
	 *
	 * @param method The method reference corresponding to the function
	 * @param index  The index of the parameter
	 * @return The generic parameter type
	 */
	static Type getGenericParameterType(final Method method, final int index) {
		if (method.isVarArgs() && index >= method.getParameterCount() - 1) {
			final Type varargsType = method.getGenericParameterTypes()[method.getParameterCount() - 1];
			if (varargsType instanceof Class<?>) {
				return ((Class<?>) varargsType).getComponentType();
			}
			if (varargsType instanceof final GenericArrayType genericArrayType) {
				return genericArrayType.getGenericComponentType();
			}
			throw new IllegalStateException("Unsupported varargs type"); //$NON-NLS-1$
		}
		if (index < method.getParameterCount()) {
			return method.getGenericParameterTypes()[index];
		}
		return null;
	}

	/**
	 * Get the parameter value type
	 *
	 * @param method The method reference corresponding to the function
	 * @param index  The index of the parameter
	 * @return The parameter type
	 */
	static Class<?> getParameterValueType(final Method method, final int index) {
		final Type parameterType = getGenericParameterValueType(method, index);
		if (parameterType instanceof final TypeVariable<?> parameterTypeVariable) {
			final var parameterTypeBound = getCommonTypeBound(parameterTypeVariable);
			if (parameterTypeBound != null) {
				return parameterTypeBound;
			}
		} else if (parameterType instanceof final Class<?> parameterClass) {
			return parameterClass;
		}
		// fallback to non-generic lookup
		return getParameterType(method, index);
	}

	/**
	 * Get the parameter type
	 *
	 * @param method The method reference corresponding to the function
	 * @param index  The index of the parameter
	 * @return The parameter type
	 */
	static Class<?> getParameterType(final Method method, final int index) {
		if (method.isVarArgs() && index >= method.getParameterCount() - 1) {
			return method.getParameterTypes()[method.getParameterCount() - 1].getComponentType();
		}
		if (index < method.getParameterCount()) {
			return method.getParameterTypes()[index];
		}
		return null;
	}

	/**
	 * Get the parameter
	 *
	 * @param method The method reference corresponding to the function
	 * @param index  The index of the parameter
	 * @return The parameter
	 */
	static Parameter getParameter(final Method method, final int index) {
		if (method.isVarArgs() && index >= method.getParameterCount() - 1) {
			return method.getParameters()[method.getParameterCount() - 1];
		}
		if (index < method.getParameterCount()) {
			return method.getParameters()[index];
		}
		return null;
	}

	/**
	 * Get the common type bound
	 *
	 * @param typeVariable The type variable
	 * @return The common type bound
	 */
	private static Class<?> getCommonTypeBound(final TypeVariable<?> typeVariable) {
		return Stream.of(typeVariable.getBounds()).filter(Class.class::isInstance).map(Class.class::cast)
				.reduce(Functions::commonSubtype).orElse(null);
	}

	private static boolean isAssignableFrom(final List<Class<?>> parameterTypes, final List<Class<?>> argumentTypes,
			final boolean varargs) {
		if (isAssignableFrom(parameterTypes, argumentTypes)) {
			return true;
		}
		if (!varargs || parameterTypes.isEmpty()) {
			return false;
		}
		if (argumentTypes.size() < parameterTypes.size() - 1) { // varargs parameter may have zero arguments
			return false;
		}
		// check all parameters up to varargs
		for (int i = parameterTypes.size() - 2; i >= 0; --i) {
			if (!isAssignableFrom(parameterTypes.get(i), argumentTypes.get(i))) {
				return false;
			}
		}
		// check varargs
		final Class<?> varargsType = parameterTypes.get(parameterTypes.size() - 1);
		final Class<?> varargsComponentType = varargsType.getComponentType();
		for (int i = argumentTypes.size() - 1; i >= parameterTypes.size() - 1; --i) {
			if (!isAssignableFrom(varargsComponentType, argumentTypes.get(i))) {
				return false;
			}
		}
		return true;
	}

	private static boolean isAssignableFrom(final List<Class<?>> parameterTypes, final List<Class<?>> argumentTypes) {
		if (argumentTypes.size() != parameterTypes.size()) {
			return false;
		}
		for (int i = parameterTypes.size() - 1; i >= 0; --i) {
			if (!isAssignableFrom(parameterTypes.get(i), argumentTypes.get(i))) {
				return false;
			}
		}
		return true;
	}

	private static boolean isAssignableFrom(final Class<?> parameterType, final Class<?> argumentType) {
		if (parameterType.isAssignableFrom(argumentType) || Variable.class.isAssignableFrom(parameterType)) {
			return true;
		}
		final DataType type1 = ValueOperations.dataType(parameterType);
		final DataType type2 = ValueOperations.dataType(argumentType);
		return type1 != null && type2 != null && type1.isAssignableFrom(type2);
	}

	private static Class<?> commonSupertype(final Class<?> clazz1, final Class<?> clazz2) {
		if (clazz1 == null) {
			return clazz2;
		}
		if (clazz2 == null) {
			return clazz1;
		}
		if (clazz1.isAssignableFrom(clazz2)) {
			return clazz1;
		}
		if (clazz2.isAssignableFrom(clazz1)) {
			return clazz2;
		}
		final DataType type1 = ValueOperations.dataType(clazz1);
		final DataType type2 = ValueOperations.dataType(clazz2);
		return ValueOperations.valueType(commonSupertype(type1, type2));
	}

	private static DataType commonSupertype(final DataType type1, final DataType type2) {
		if (type1 == null) {
			return type2;
		}
		if (type2 == null) {
			return type1;
		}
		if (type1.isAssignableFrom(type2)) {
			return type1;
		}
		if (type2.isAssignableFrom(type1)) {
			return type2;
		}
		return null;
	}

	private static Class<?> commonSubtype(final Class<?> clazz1, final Class<?> clazz2) {
		if (clazz1 == null) {
			return clazz2;
		}
		if (clazz2 == null) {
			return clazz1;
		}
		if (clazz1.isAssignableFrom(clazz2)) {
			return clazz2;
		}
		if (clazz2.isAssignableFrom(clazz1)) {
			return clazz1;
		}
		final DataType type1 = ValueOperations.dataType(clazz1);
		final DataType type2 = ValueOperations.dataType(clazz2);
		return ValueOperations.valueType(commonSubtype(type1, type2));
	}

	private static DataType commonSubtype(final DataType type1, final DataType type2) {
		if (type1 == null) {
			return type2;
		}
		if (type2 == null) {
			return type1;
		}
		if (type1.isAssignableFrom(type2)) {
			return type2;
		}
		if (type2.isAssignableFrom(type1)) {
			return type1;
		}
		return null;
	}

	private static List<Class<?>> getValueTypes(final List<DataType> argumentTypes) {
		return argumentTypes.stream().map(ValueOperations::valueType)
				.<Class<?>>map(c -> Objects.requireNonNullElse(c, Object.class)).toList();
	}
}
