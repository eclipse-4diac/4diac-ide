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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;

public interface Functions {

	static Value invoke(final Class<? extends Functions> clazz, final String name, final Value... arguments)
			throws Throwable {
		return invoke(clazz, name, List.of(arguments));
	}

	static Value invoke(final Class<? extends Functions> clazz, final String name, final List<Value> arguments)
			throws Throwable {
		final Method method = findMethod(clazz, name,
				arguments.stream().map(Object::getClass).collect(Collectors.toList()));
		final MethodHandle handle = MethodHandles.lookup().unreflect(method);
		return (Value) handle.invokeWithArguments(arguments);
	}

	static Method findMethod(final Class<? extends Functions> clazz, final String name, final Class<?>... atypes) {
		return findMethod(clazz, name, List.of(atypes));
	}

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

	static Class<? extends Value> inferReturnType(final Class<? extends Functions> clazz, final String name,
			final List<Class<?>> atypes) {
		Class<?> result = null;
		final Method method = findMethod(clazz, name, atypes);
		final Type returnType = method.getGenericReturnType();
		if (returnType instanceof TypeVariable<?>) {
			final List<Type> ptypes = List.of(method.getGenericParameterTypes());
			if (isAssignableFrom(List.of(method.getParameterTypes()), atypes)) { // no vararg special handling necessary
				for (int i = ptypes.size() - 1; i >= 0; --i) {
					final Class<?> inferredType = inferTypeVariable((TypeVariable<?>) returnType, ptypes.get(i),
							atypes.get(i), false);
					result = commonSupertype(result, inferredType);
				}
			} else { // vararg special handling
				for (int i = atypes.size() - 1; i >= 0; --i) {
					final Class<?> inferredType = inferTypeVariable((TypeVariable<?>) returnType,
							ptypes.get(i < ptypes.size() ? i : ptypes.size() - 1), atypes.get(i),
							i >= ptypes.size() - 1);
					result = commonSupertype(result, inferredType);
				}
			}
			if (result != null) {
				return result.asSubclass(Value.class);
			}
		}
		return method.getReturnType().asSubclass(Value.class);
	}

	static Method findMethodFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final DataType... atypes) {
		return findMethodFromDataTypes(clazz, name, List.of(atypes));
	}

	static Method findMethodFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final List<DataType> atypes) {
		return findMethod(clazz, name, atypes.stream().map(ValueOperations::valueType).collect(Collectors.toList()));
	}

	static DataType inferReturnTypeFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final DataType... atypes) {
		return inferReturnTypeFromDataTypes(clazz, name, List.of(atypes));
	}

	static DataType inferReturnTypeFromDataTypes(final Class<? extends Functions> clazz, final String name,
			final List<DataType> atypes) {
		return ValueOperations.dataType(inferReturnType(clazz, name,
				atypes.stream().map(ValueOperations::valueType).collect(Collectors.toList())));
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

	private static Class<?> inferTypeVariable(final TypeVariable<?> typeVariable, final Type ptype,
			final Class<?> atype, final boolean vararg) {
		if (ptype == typeVariable) {
			return atype;
		} else if (ptype instanceof GenericArrayType) {
			return inferTypeVariable(typeVariable, ((GenericArrayType) ptype).getGenericComponentType(),
					vararg ? atype : atype.getComponentType(), false);
		}
		return null;
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
