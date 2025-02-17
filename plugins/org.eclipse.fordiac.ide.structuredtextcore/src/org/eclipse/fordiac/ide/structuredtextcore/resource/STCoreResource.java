/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.ArraySize;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResource;
import org.eclipse.fordiac.ide.model.resource.TypeImportDiagnostic;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;

public class STCoreResource extends LibraryElementXtextResource implements STResource {
	public static final String OPTION_PLAIN_ST = STCoreResource.class.getName() + ".PLAIN_ST"; //$NON-NLS-1$

	private static final String EXPECTED_TYPE_CACHE_KEY = "EXPECTED_TYPE"; //$NON-NLS-1$

	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Inject
	private STCorePartitioner partitioner;

	@Inject
	private STCoreReconciler reconciler;

	@Inject
	private STCoreGrammarAccess grammarAccess;

	private INamedElement expectedType;

	@Override
	protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
		final Map<?, ?> actualOptions = Objects.requireNonNullElse(options, getDefaultLoadOptions());
		if (isLoadPlainST(actualOptions, inputStream)) {
			setExpectedType((INamedElement) actualOptions.get(OPTION_EXPECTED_TYPE));
			super.doLoad(inputStream, actualOptions);
			if (getLibraryElement() == null) {
				final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri);
				if (typeEntry != null) {
					// use type without copying, since we make an internal copy anyway
					setLibraryElement(typeEntry.getType());
				}
			} else {
				updateInternalLibraryElement();
			}
		} else { // inputStream contains full XML for library element
			// chain load library element from inputStream
			final LibraryElement libraryElement = loadLibraryElement(inputStream);
			if (libraryElement == null) {
				return;
			}
			// then load from combined source
			final String text = getSource(libraryElement);
			super.doLoad(new LazyStringInputStream(text, getEncoding()), actualOptions);
			// and add library element to contents
			addInternalLibraryElement(libraryElement);
		}
	}

	protected LibraryElement loadLibraryElement(final InputStream inputStream) {
		try {
			final Resource typeResource;
			if (uri.hasQuery() && resourceSet != null) {
				typeResource = resourceSet.getResource(uri.trimQuery(), true);
			} else {
				typeResource = new FordiacTypeResource(uri);
				typeResource.load(inputStream, Collections.emptyMap());
			}
			getErrors().addAll(typeResource.getErrors());
			getWarnings().addAll(typeResource.getWarnings());
			return findInternalLibraryElement(typeResource);
		} catch (final Exception e) {
			handleTypeLoadException(e);
		}
		return null;
	}

	protected void addInternalLibraryElement(final LibraryElement libraryElement) {
		if (contents != null && !contents.isEmpty()) {
			if (libraryElement.eResource().getResourceSet() != null) {
				// copy library element if we loaded from the resource set
				contents.add(copyLibraryElement(libraryElement, !isIncludeInternalLibraryElement()));
			} else {
				// or directly add loaded library element to contents
				// (move from typeResource instead of copying)
				// if we chain loaded using a temporary resource
				contents.add(libraryElement);
			}
			contents.addAll(EcoreUtil.copyAll(getAdditionalContent()));
			relink();
		}
	}

	protected String getSource(final LibraryElement libraryElement) throws IOException {
		if (uri.hasQuery()) {
			final EObject sourceElement = getSourceElement(libraryElement);
			setIncludeInternalLibraryElement(needsInternalLibraryElement(libraryElement, sourceElement));
			if (sourceElement instanceof final Attribute attribute) {
				setEntryPoint(grammarAccess.getSTInitializerExpressionSourceRule());
				setExpectedType(STCoreUtil.getFeatureType(attribute));
				return Objects.requireNonNullElse(attribute.getValue(), ""); //$NON-NLS-1$
			}
			if (sourceElement instanceof final ArraySize arraySize
					&& arraySize.eContainer() instanceof final VarDeclaration varDeclaration) {
				setEntryPoint(grammarAccess.getSTTypeDeclarationRule());
				return Objects.requireNonNullElse(varDeclaration.getFullTypeName(), ""); //$NON-NLS-1$
			}
			if (sourceElement instanceof final Value value
					&& value.eContainer() instanceof final VarDeclaration varDeclaration) {
				setEntryPoint(grammarAccess.getSTInitializerExpressionSourceRule());
				setExpectedType(STCoreUtil.getFeatureType(varDeclaration));
				return Objects.requireNonNullElse(value.getValue(), ""); //$NON-NLS-1$
			}
			throw new IOException("Invalid query in ST resource: " + uri.query()); //$NON-NLS-1$
		}
		setEntryPoint(null);
		setIncludeInternalLibraryElement(needsInternalLibraryElement(libraryElement, libraryElement));
		return partitioner.combine(libraryElement);
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected boolean needsInternalLibraryElement(final LibraryElement libraryElement, final EObject sourceElement) {
		// we need an internal library element only if it is the source element itself
		// (meaning there is no query) for chain loading of resources with a query and
		// also for proper validation during build
		return sourceElement == libraryElement;
	}

	@Override
	public void doSave(final OutputStream outputStream, final Map<?, ?> options) throws IOException {
		if (isSavePlainST(options)) {
			super.doSave(outputStream, options);
		} else { // outputStream shall contain full XML for library element
			final LibraryElement internalLibraryElement = getInternalLibraryElement();
			// perform checks
			if (getContents().isEmpty() || !isIncludeInternalLibraryElement() || internalLibraryElement == null) {
				throw new IOException(
						"The ST core resource must contain at least one element and have an associated library element"); //$NON-NLS-1$
			}
			// copy the internal library element
			final LibraryElement libraryElement = EcoreUtil.copy(internalLibraryElement);
			// reconcile
			reconcile(libraryElement, options);
			// chain save library element to outputStream
			try {
				final FordiacTypeResource typeResource = new FordiacTypeResource(uri);
				typeResource.getContents().add(libraryElement);
				typeResource.save(outputStream, options);
			} catch (final Exception e) {
				throw new IOException("Error saving to library element: " + e.getMessage(), e); //$NON-NLS-1$
			}
		}
	}

	protected void reconcile(final LibraryElement libraryElement, final Map<?, ?> options) throws IOException {
		try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			super.doSave(stream, options);
			final String text = new String(stream.toByteArray(), getEncoding());
			if (uri.hasQuery()) {
				final EObject sourceElement = getSourceElement(libraryElement);
				switch (sourceElement) {
				case final Attribute attribute -> attribute.setValue(text);
				case final ArraySize arraySize -> arraySize.setValue(text);
				case final Value value -> value.setValue(text);
				default -> throw new IOException("Invalid query in ST resource: " + uri.query()); //$NON-NLS-1$
				}
			} else {
				// reparse
				super.doLoad(new LazyStringInputStream(text, getEncoding()), null);
				// partition
				final var partition = partitioner.partition(this);
				if (partition.isEmpty()) {
					throw new IOException("Cannot partition source"); //$NON-NLS-1$
				}
				// reconcile
				reconciler.reconcile(libraryElement, partition);
			}
		} catch (final IOException e) {
			throw e;
		} catch (final Exception e) {
			throw new IOException("Cannot reconcile source", e); //$NON-NLS-1$
		}
	}

	@Override
	public void updateInternalLibraryElement() {
		super.updateInternalLibraryElement();
		updateExpectedType();
	}

	public void updateExpectedType() {
		final EObject sourceElement = getSourceElement();
		if (sourceElement instanceof final Attribute attribute) {
			setExpectedType(STCoreUtil.getFeatureType(attribute));
		} else if (sourceElement instanceof final Value value
				&& value.eContainer() instanceof final VarDeclaration varDeclaration) {
			setExpectedType(STCoreUtil.getFeatureType(varDeclaration));
		}
	}

	protected boolean isLoadPlainST(final Map<?, ?> options, final InputStream inputStream) {
		return uri.fileExtension() == null
				|| fileExtensionProvider.getPrimaryFileExtension().equalsIgnoreCase(uri.fileExtension())
				|| Boolean.TRUE.equals(options.get(OPTION_PLAIN_ST)) || inputStream instanceof LazyStringInputStream;
	}

	protected boolean isSavePlainST(final Map<?, ?> options) {
		return uri.fileExtension() == null
				|| fileExtensionProvider.getPrimaryFileExtension().equalsIgnoreCase(uri.fileExtension())
				|| (options != null && Boolean.TRUE.equals(options.get(OPTION_PLAIN_ST)));
	}

	protected void handleTypeLoadException(final Throwable e) {
		getErrors().add(new TypeImportDiagnostic(e.getMessage(), Messages.STCoreResource_TypeLoadError));
	}

	public Map<Object, Object> getDefaultLoadOptions() {
		if (defaultLoadOptions == null) {
			defaultLoadOptions = new HashMap<>();
		}
		return defaultLoadOptions;
	}

	public EObject getSourceElement() {
		// try source element from current resource
		final EObject sourceElement = getInternalSourceElement();
		if (sourceElement == null && uri.hasQuery() && resourceSet != null) {
			// OR attempt source element from resource set if present
			// (e.g., if we only have a shallow internal library element)
			return getSourceElement(findInternalLibraryElement(resourceSet.getResource(uri.trimQuery(), true)));
		}
		return sourceElement;
	}

	protected EObject getSourceElement(final LibraryElement libraryElement) {
		if (libraryElement != null && uri.hasQuery()) {
			final Resource resource = libraryElement.eResource();
			final String fragment;
			if (resource instanceof STCoreResource) {
				fragment = toInternalFragment(uri.query());
			} else {
				fragment = toExternalFragment(uri.query());
			}
			return resource.getEObject(fragment);
		}
		return libraryElement;
	}

	public EObject getInternalSourceElement() {
		if (uri.hasQuery()) {
			return getEObject(toInternalFragment(uri.query()));
		}
		return getInternalLibraryElement();
	}

	@Override
	public INamedElement getExpectedType() {
		return expectedType;
	}

	@Override
	public void setExpectedType(final INamedElement expectedType) {
		this.expectedType = expectedType;
	}

	@Override
	public INamedElement getExpectedType(final STExpression expression) {
		return getCache().get(Tuples.create(EXPECTED_TYPE_CACHE_KEY, expression), this,
				() -> STCoreUtil.computeExpectedType(expression));
	}

	@Override
	public INamedElement getExpectedType(final STInitializerExpression expression) {
		return getCache().get(Tuples.create(EXPECTED_TYPE_CACHE_KEY, expression), this,
				() -> STCoreUtil.computeExpectedType(expression));
	}
}
