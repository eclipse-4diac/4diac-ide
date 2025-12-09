/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.scoping;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.eval.function.Comment;
import org.eclipse.fordiac.ide.model.eval.function.Functions;
import org.eclipse.fordiac.ide.model.eval.function.OnlySupportedBy;
import org.eclipse.fordiac.ide.model.eval.function.ReturnValueComment;
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.impl.CallableAnnotations;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescriptionStrategy;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreRegionString;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.Scopes;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.inject.Singleton;

@Singleton
public class STStandardFunctionProvider {
	public static final URI STANDARD_FUNCTIONS_URI = URI.createURI("__st_standard_functions.stfunc"); //$NON-NLS-1$

	private final Resource functionResource;
	private final Class<? extends Functions> functions;
	private final Map<Method, STStandardFunction> standardFunctions;
	private final List<IEObjectDescription> standardFunctionDescriptions;
	private final LoadingCache<StandardFunctionLookupKey, Optional<STStandardFunction>> standardFunctionLookup;
	private final LoadingCache<StandardFunctionLookupKey, Optional<IEObjectDescription>> standardFunctionDescriptionLookup;

	/**
	 * Create a new instance using the default set of standard functions
	 */
	public STStandardFunctionProvider() {
		this(StandardFunctions.class);
	}

	/**
	 * Create a new instance using the given set of standard functions
	 */
	public STStandardFunctionProvider(final Class<? extends Functions> functions) {
		this.functions = functions;
		functionResource = new ResourceImpl();
		functionResource.setURI(STANDARD_FUNCTIONS_URI);
		standardFunctions = Functions.getMethods(functions).stream()
				.collect(Collectors.toMap(Function.identity(), this::createStandardFunction));
		standardFunctionDescriptions = StreamSupport
				.stream(Scopes.scopedElementsFor(standardFunctions.values()).spliterator(), false).toList();
		standardFunctionLookup = CacheBuilder.newBuilder().maximumSize(standardFunctions.size() * 10L)
				.build(CacheLoader.from(this::findInternal));
		standardFunctionDescriptionLookup = CacheBuilder.newBuilder().maximumSize(standardFunctions.size() * 10L)
				.build(CacheLoader.from(this::findDescriptionInternal));
	}

	/**
	 * Get a list of all standard functions known to this provider
	 */
	public Iterable<STStandardFunction> get() {
		return standardFunctions.values();
	}

	/**
	 * Find a standard function matching the given name and argument types
	 */
	public Optional<STStandardFunction> find(final String name, final List<DataType> argumentTypes) {
		try {
			return standardFunctionLookup.get(new StandardFunctionLookupKey(name, argumentTypes));
		} catch (final ExecutionException e) {
			return Optional.empty();
		}
	}

	protected Optional<STStandardFunction> findInternal(final StandardFunctionLookupKey key) {
		try {
			return Optional.of(standardFunctions
					.get(Functions.findMethodFromDataTypes(functions, key.getName(), key.getArgumentTypes())));
		} catch (NoSuchMethodException | IllegalStateException e) {
			return Functions.findMethods(functions, key.getName()).stream().map(standardFunctions::get).findFirst();
		}
	}

	/**
	 * Get a list of all standard function descriptions known to this provider
	 */
	public List<IEObjectDescription> getDescriptions() {
		return standardFunctionDescriptions;
	}

	/**
	 * Get a list of all standard function descriptions matching the given argument
	 * types
	 */
	public Optional<IEObjectDescription> findDescription(final String name, final List<DataType> argumentTypes) {
		try {
			return standardFunctionDescriptionLookup.get(new StandardFunctionLookupKey(name, argumentTypes));
		} catch (final ExecutionException e) {
			return Optional.empty();
		}
	}

	protected Optional<IEObjectDescription> findDescriptionInternal(final StandardFunctionLookupKey key) {
		try {
			return standardFunctionLookup.get(key).map(STStandardFunctionProvider::createStandardFunctionDescription);
		} catch (final ExecutionException e) {
			return Optional.empty();
		}
	}

	/**
	 * Convert a method to a standard function with concrete return and parameter
	 * types
	 */
	protected STStandardFunction createStandardFunction(final Method method) {
		final STStandardFunction result = STCoreFactory.eINSTANCE.createSTStandardFunction();
		result.setJavaMethod(method);
		result.setName(method.getName());
		result.setComment(extractComment(method));
		result.setReturnValueComment(extractReturnValueComment(method));
		result.setReturnType(
				method.getReturnType() != void.class ? ValueOperations.dataType(method.getReturnType()) : null);
		result.getInputParameters().addAll(STCoreUtil.computeInputParameters(result, Collections.emptyList()));
		result.getOutputParameters().addAll(STCoreUtil.computeOutputParameters(result, Collections.emptyList()));
		result.setVarargs(method.isVarArgs());
		result.setSignature(CallableAnnotations.getSignature(result)); // call annotation directly as an exception
		Stream.of(method.getAnnotationsByType(OnlySupportedBy.class)).map(OnlySupportedBy::value).flatMap(Stream::of)
				.forEachOrdered(result.getOnlySupportedBy()::add);
		functionResource.getContents().add(result);
		return result;
	}

	protected static String extractComment(final Method method) {
		if (method.isAnnotationPresent(Comment.class)) {
			return method.getAnnotation(Comment.class).value();
		}
		return ""; //$NON-NLS-1$
	}

	protected static String extractReturnValueComment(final Method method) {
		if (method.isAnnotationPresent(ReturnValueComment.class)) {
			return method.getAnnotation(ReturnValueComment.class).value();
		}
		return ""; //$NON-NLS-1$
	}

	protected static IEObjectDescription createStandardFunctionDescription(final STStandardFunction standardFunction) {
		final STCoreRegionString regionString = STCoreResourceDescriptionStrategy
				.getCallableParameterProposal(standardFunction);
		return new EObjectDescription(QualifiedName.create(standardFunction.getName()), standardFunction,
				Map.of(STCoreResourceDescriptionStrategy.DISPLAY_STRING, standardFunction.getSignature(),
						STCoreResourceDescriptionStrategy.PARAMETER_PROPOSAL, regionString.toString(),
						STCoreResourceDescriptionStrategy.PARAMETER_PROPOSAL_REGIONS,
						regionString.getRegions().toString()));
	}

	protected static class StandardFunctionLookupKey {
		private final String name;
		private final List<DataType> argumentTypes;
		private final List<String> argumentTypeNames;
		private final int hashCode;

		public StandardFunctionLookupKey(final String name, final List<DataType> argumentTypes) {
			this.name = name;
			this.argumentTypes = ImmutableList.copyOf(argumentTypes);
			argumentTypeNames = argumentTypes.stream().map(PackageNameHelper::getFullTypeName).toList();
			hashCode = Objects.hash(name, argumentTypeNames);
		}

		public String getName() {
			return name;
		}

		public List<DataType> getArgumentTypes() {
			return argumentTypes;
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final StandardFunctionLookupKey other = (StandardFunctionLookupKey) obj;
			return Objects.equals(name, other.name) && Objects.equals(argumentTypeNames, other.argumentTypeNames);
		}
	}
}
