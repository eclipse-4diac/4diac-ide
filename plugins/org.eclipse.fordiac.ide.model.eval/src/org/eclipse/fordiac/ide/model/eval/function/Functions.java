/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;

public interface Functions {

	/** Invoke standard function from {@code clazz} with the given name that is applicable for the arguments
	 *
	 * @param clazz     The class defining a set of standard functions
	 * @param name      The name of the standard function
	 * @param arguments The arguments used to select and invoke the standard function
	 * @return The result of the standard function
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 * @throws Throwable             for any other error during invocation */
	static Value invoke(final Class<? extends Functions> clazz, final String name, final Value... arguments)
			throws Throwable {
		return invoke(clazz, name, List.of(arguments));
	}

	/** Invoke standard function from {@code clazz} with the given name that is applicable for the arguments
	 *
	 * @param clazz     The class defining a set of standard functions
	 * @param name      The name of the standard function
	 * @param arguments The arguments used to select and invoke the standard function
	 * @return The result of the standard function
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match
	 * @throws Throwable             for any other error during invocation */
	static Value invoke(final Class<? extends Functions> clazz, final String name, final List<Value> arguments)
			throws Throwable {
		final Method method = findMethod(clazz, name,
				arguments.stream().map(Object::getClass).collect(Collectors.toList()));
		final MethodHandle handle = MethodHandles.lookup().unreflect(method);
		return (Value) handle.invokeWithArguments(arguments);
	}

	/** Find a standard function from {@code clazz} with the given name that is applicable for the concrete argument
	 * types
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param name   The name of the standard function
	 * @param atypes The argument types used to select the standard function
	 * @return The method reference corresponding to the standard function
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match */
	static Method findMethod(final Class<? extends Functions> clazz, final String name, final Class<?>... atypes) {
		return findMethod(clazz, name, List.of(atypes));
	}

	/** Find a standard function from {@code clazz} with the given name that is applicable for the concrete argument
	 * types
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param name   The name of the standard function
	 * @param atypes The argument types used to select the standard function
	 * @return The method reference corresponding to the standard function
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match */
	static Method findMethod(final Class<? extends Functions> clazz, final String name, final List<Class<?>> atypes) {
		final List<Method> methods = Stream.of(clazz.getMethods())
				.filter(method -> method.getName().equals(name)
						&& isAssignableFrom(List.of(method.getParameterTypes()), atypes, method.isVarArgs()))
				.collect(Collectors.toList());
		if (methods.isEmpty()) {
			throw new NoSuchMethodError(String.format("No method with name %s in class %s", name, clazz.getName())); //$NON-NLS-1$
		} else if (methods.size() > 1) {
			throw new IllegalStateException(String
					.format("Multiple candidates found for method with name %s in class %s", name, clazz.getName())); //$NON-NLS-1$
		}
		return methods.get(0);
	}

	/** Find all standard functions from {@code clazz} with the given name
	 *
	 * @param clazz The class defining a set of standard functions
	 * @param name  The name of the standard function
	 * @return The method references corresponding to the standard functions */
	static List<Method> findMethods(final Class<? extends Functions> clazz, final String name) {
		return Stream.of(clazz.getMethods()).filter(method -> method.getName().equals(name))
				.collect(Collectors.toList());
	}

	/** Find all standard functions from {@code clazz} applicable for the concrete argument types
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param atypes The argument types used to select the standard function
	 * @return The method references corresponding to the standard functions */
	static List<Method> findMethods(final Class<? extends Functions> clazz, final List<Class<?>> atypes) {
		return Stream.of(clazz.getMethods())
				.filter(method -> isAssignableFrom(List.of(method.getParameterTypes()), atypes, method.isVarArgs()))
				.collect(Collectors.toList());
	}

	/** Find all standard functions from {@code clazz}
	 *
	 * @param clazz The class defining a set of standard functions
	 * @return The method references corresponding to the standard functions */
	static List<Method> getMethods(final Class<? extends Functions> clazz) {
		return List.of(clazz.getMethods());
	}

	/** Infer the concrete return type (i.e., resolve generic type) for a standard function
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param name   The name of the standard function
	 * @param atypes The argument types used to select the standard function and infer the return type
	 * @return The concrete return type of the standard function based on the argument types
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match */
	static Class<? extends Value> inferReturnType(final Class<? extends Functions> clazz, final String name,
			final List<Class<?>> atypes) {
		return inferReturnType(findMethod(clazz, name, atypes), atypes);
	}

	/** Infer the concrete return type (i.e., resolve generic type) for a standard function
	 *
	 * @param method The method reference corresponding to the standard function
	 * @param atypes The argument types used to infer the return type
	 * @return The concrete return type of the standard function based on the argument types */
	static Class<? extends Value> inferReturnType(final Method method, final List<Class<?>> atypes) {
		try {
			final Type returnType = method.getGenericReturnType();
			if (returnType instanceof TypeVariable<?>) {
				Class<?> result = null;
				for (int i = 0; i < atypes.size(); ++i) {
					final Type ptype = getGenericParameterType(method, i);
					if (ptype == returnType) {
						result = commonSupertype(result, atypes.get(i));
					}
				}
				if (result != null) {
					return result.asSubclass(Value.class);
				}
			}
		} catch (final Exception e) {
			// ignore
		}
		return method.getReturnType() != void.class ? method.getReturnType().asSubclass(Value.class) : null;
	}

	/** Infer the concrete parameter types (i.e., resolve generic types) for a standard function
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param name   The name of the standard function
	 * @param atypes The argument types used to select the standard function and infer the return type
	 * @return The concrete parameter types of the standard function based on the argument types
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match */
	static List<Class<? extends Value>> inferParameterTypes(final Class<? extends Functions> clazz, final String name,
			final List<Class<?>> atypes) {
		return inferParameterTypes(findMethod(clazz, name, atypes), atypes);
	}

	/** Infer the concrete parameter types (i.e., resolve generic types) for a standard function
	 *
	 * @param method The method reference corresponding to the standard function
	 * @param atypes The argument types used to infer the return type
	 * @return The concrete parameter types of the standard function based on the argument types */
	static List<Class<? extends Value>> inferParameterTypes(final Method method, final List<Class<?>> atypes) {
		final List<Class<? extends Value>> result = new ArrayList<>(atypes.size());
		for (int i = 0; i < atypes.size(); ++i) {
			result.add(inferParameterType(method, atypes, i));
		}
		return result;
	}

	/** Infer a single concrete parameter type (i.e., resolve generic type) for a standard function
	 *
	 * @param method The method reference corresponding to the standard function
	 * @param atypes The argument types used to infer the return type
	 * @param index  The parameter index
	 * @return The concrete parameter type of the standard function based on the argument types */
	static Class<? extends Value> inferParameterType(final Method method, final List<Class<?>> atypes,
			final int index) {
		try {
			final Type parameterType = getGenericParameterType(method, index);
			if (parameterType instanceof TypeVariable<?>) {
				Class<?> result = null;
				for (int i = 0; i < atypes.size(); ++i) {
					final Type ptype = getGenericParameterType(method, i);
					if (ptype == parameterType) {
						result = commonSupertype(result, atypes.get(i));
					}
				}
				if (result != null) {
					return result.asSubclass(Value.class);
				}
			}
		} catch (final Exception e) {
			// ignore
		}
		return getParameterType(method, index).asSubclass(Value.class);
	}

	/** Find a standard function from {@code clazz} with the given name that is applicable for the concrete argument
	 * types
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param name   The name of the standard function
	 * @param atypes The argument types used to select the standard function
	 * @return The method reference corresponding to the standard function
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match */
	static Method findMethodFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final DataType... atypes) {
		return findMethodFromDataTypes(clazz, name, List.of(atypes));
	}

	/** Find a standard function from {@code clazz} with the given name that is applicable for the concrete argument
	 * types
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param name   The name of the standard function
	 * @param atypes The argument types used to select the standard function
	 * @return The method reference corresponding to the standard function
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match */
	static Method findMethodFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final List<DataType> atypes) {
		return findMethod(clazz, name, atypes.stream().map(ValueOperations::valueType).collect(Collectors.toList()));
	}

	/** Find all standard functions from {@code clazz} applicable for the concrete argument types
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param atypes The argument types used to select the standard function
	 * @return The method references corresponding to the standard functions */
	static List<Method> findMethodsFromDataTypes(final Class<? extends Functions> clazz, final List<DataType> atypes) {
		return findMethods(clazz, atypes.stream().map(ValueOperations::valueType)
				.map(c -> Objects.requireNonNullElse(c, Object.class)).collect(Collectors.toList()));
	}

	/** Infer the concrete return type (i.e., resolve generic type) for a standard function
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param name   The name of the standard function
	 * @param atypes The argument types used to select the standard function and infer the return type
	 * @return The concrete return type of the standard function based on the argument types
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match */
	static DataType inferReturnTypeFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final DataType... atypes) {
		return inferReturnTypeFromDataTypes(clazz, name, List.of(atypes));
	}

	/** Infer the concrete return type (i.e., resolve generic type) for a standard function
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param name   The name of the standard function
	 * @param atypes The argument types used to select the standard function and infer the return type
	 * @return The concrete return type of the standard function based on the argument types
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match */
	static DataType inferReturnTypeFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final List<DataType> atypes) {
		return ValueOperations.dataType(inferReturnType(clazz, name,
				atypes.stream().map(ValueOperations::valueType).collect(Collectors.toList())));
	}

	/** Infer the concrete return type (i.e., resolve generic type) for a standard function
	 *
	 * @param method The method reference corresponding to the standard function
	 * @param atypes The argument types used to infer the return type
	 * @return The concrete return type of the standard function based on the argument types */
	static DataType inferReturnTypeFromDataTypes(final Method method, final List<DataType> atypes) {
		return ValueOperations.dataType(
				inferReturnType(method, atypes.stream().map(ValueOperations::valueType).collect(Collectors.toList())));
	}

	/** Infer the concrete return type (i.e., resolve generic type) for a standard function
	 *
	 * @param method The method reference corresponding to the standard function
	 * @param atypes The argument types used to infer the return type
	 * @return The concrete return type of the standard function based on the argument types */
	static List<DataType> inferParameterTypesFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final DataType... atypes) {
		return inferParameterTypesFromDataTypes(clazz, name, List.of(atypes));
	}

	/** Infer the concrete parameter types (i.e., resolve generic types) for a standard function
	 *
	 * @param clazz  The class defining a set of standard functions
	 * @param name   The name of the standard function
	 * @param atypes The argument types used to select the standard function and infer the return type
	 * @return The concrete parameter types of the standard function based on the argument types
	 * @throws NoSuchMethodError     if no applicable method could be found
	 * @throws IllegalStateException if multiple candidate methods match */
	static List<DataType> inferParameterTypesFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final List<DataType> atypes) {
		return inferParameterTypes(clazz, name,
				atypes.stream().map(ValueOperations::valueType).collect(Collectors.toList())).stream()
				.map(ValueOperations::dataType).collect(Collectors.toList());
	}

	/** Infer the concrete parameter types (i.e., resolve generic types) for a standard function
	 *
	 * @param method The method reference corresponding to the standard function
	 * @param atypes The argument types used to infer the return type
	 * @return The concrete parameter types of the standard function based on the argument types */
	static List<DataType> inferParameterTypesFromDataTypes(final Method method, final List<DataType> atypes) {
		return inferParameterTypes(method, atypes.stream().map(ValueOperations::valueType).collect(Collectors.toList()))
				.stream().map(ValueOperations::dataType).collect(Collectors.toList());
	}

	private static Type getGenericParameterType(final Method method, final int index) {
		if (method.isVarArgs() && index >= method.getParameterCount() - 1) {
			final Type varargsType = method.getGenericParameterTypes()[method.getParameterCount() - 1];
			if (varargsType instanceof Class<?>) {
				return ((Class<?>) varargsType).getComponentType();
			} else if (varargsType instanceof GenericArrayType) {
				return ((GenericArrayType) varargsType).getGenericComponentType();
			} else {
				throw new IllegalStateException("Unsupported varargs type"); //$NON-NLS-1$
			}
		} else if (index < method.getParameterCount()) {
			return method.getGenericParameterTypes()[index];
		} else {
			throw new IndexOutOfBoundsException("Parameter index out of bounds"); //$NON-NLS-1$
		}
	}

	private static Class<?> getParameterType(final Method method, final int index) {
		if (method.isVarArgs() && index >= method.getParameterCount() - 1) {
			return method.getParameterTypes()[method.getParameterCount() - 1].getComponentType();
		} else if (index < method.getParameterCount()) {
			return method.getParameterTypes()[index];
		} else {
			throw new IndexOutOfBoundsException("Parameter index out of bounds"); //$NON-NLS-1$
		}
	}

	private static boolean isAssignableFrom(final List<Class<?>> ptypes, final List<Class<?>> atypes,
			final boolean varargs) {
		if (isAssignableFrom(ptypes, atypes)) {
			return true;
		}
		if (!varargs || ptypes.isEmpty()) {
			return false;
		}
		if (atypes.size() < ptypes.size() - 1) { // varargs parameter may have zero arguments
			return false;
		}
		// check all parameters up to varargs
		for (int i = ptypes.size() - 2; i >= 0; --i) {
			if (!ptypes.get(i).isAssignableFrom(atypes.get(i))) {
				return false;
			}
		}
		// check varargs
		final Class<?> varargsType = ptypes.get(ptypes.size() - 1);
		final Class<?> varargsComponentType = varargsType.getComponentType();
		for (int i = atypes.size() - 1; i >= ptypes.size() - 1; --i) {
			if (!varargsComponentType.isAssignableFrom(atypes.get(i))) {
				return false;
			}
		}
		return true;
	}

	private static boolean isAssignableFrom(final List<Class<?>> ptypes, final List<Class<?>> atypes) {
		if (atypes.size() != ptypes.size()) {
			return false;
		}
		for (int i = ptypes.size() - 1; i >= 0; --i) {
			if (!ptypes.get(i).isAssignableFrom(atypes.get(i))) {
				return false;
			}
		}
		return true;
	}

	private static Class<?> commonSupertype(final Class<?> clazz1, final Class<?> clazz2) {
		if (clazz1 == null) {
			return clazz2;
		} else if (clazz2 == null) {
			return clazz1;
		} else if (clazz1.isAssignableFrom(clazz2)) {
			return clazz1;
		} else if (clazz2.isAssignableFrom(clazz1)) {
			return clazz2;
		} else if (Value.class.isAssignableFrom(clazz1) && Value.class.isAssignableFrom(clazz2)) {
			final DataType type1 = ValueOperations.dataType(clazz1.asSubclass(Value.class));
			final DataType type2 = ValueOperations.dataType(clazz2.asSubclass(Value.class));
			if (type1 == null) {
				return ValueOperations.valueType(type2);
			} else if (type2 == null) {
				return ValueOperations.valueType(type1);
			} else if (type1.isCompatibleWith(type2)) {
				return ValueOperations.valueType(type2);
			} else if (type2.isCompatibleWith(type1)) {
				return ValueOperations.valueType(type1);
			}
		}
		return null;
	}
}
