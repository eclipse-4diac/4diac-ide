/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.search;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.ui.FilterComposite.Filter;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.ISearchContext;
import org.eclipse.fordiac.ide.model.search.types.IEC61499SearchFilter;
import org.eclipse.fordiac.ide.model.search.types.ISearchChildrenProvider;
import org.eclipse.fordiac.ide.model.search.types.SearchChildrenProviderHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class SearchHelper {

	public static final Predicate<TypeEntry> linkedElementsFilter = entry -> {
		for (final String segment : entry.getFile().getFullPath().segments()) {
			if (segment.equals("External Libraries") || segment.equals("Standard Libraries")) { //$NON-NLS-1$ //$NON-NLS-2$
				return false;
			}
		}
		return true;
	};

	final FilterRecord blockTypesRecord;
	final FilterRecord blockInstanceRecord;
	final FilterRecord untypedSubappRecord;
	final FilterRecord dataTypesRecord;
	final FilterRecord attributeTypesRecord;
	final boolean ignoreLinkedLibraries;

	public SearchHelper(final FilterRecord fbSubappTypesRecord, final FilterRecord fbTypedSubappInstanceRecord,
			final FilterRecord untypedSubappRecord, final FilterRecord dataTypesRecord,
			final FilterRecord attributeTypesRecord, final boolean ignoreLinkedLibraries) {
		this.blockTypesRecord = fbSubappTypesRecord;
		this.blockInstanceRecord = fbTypedSubappInstanceRecord;
		this.untypedSubappRecord = untypedSubappRecord;
		this.dataTypesRecord = dataTypesRecord;
		this.attributeTypesRecord = attributeTypesRecord;
		this.ignoreLinkedLibraries = ignoreLinkedLibraries;
	}

	public static List<ISearchContext> createSearchContextList(final IProject project, final List<URI> uriList) {
		return List.of(new AbstractLiveSearchContext(project) {
			@Override
			public Stream<URI> getTypes() {
				return uriList.stream();
			}

			@Override
			public EObject mapTypes(final URI uri) {
				final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
				return typeEntry.getType().eResource().getEObject(uri.fragment());
			}
		});
	}

	public List<ISearchContext> createSearchContextList(final boolean workspace, final boolean project,
			final IProject iproject) {
		if (workspace) {
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			return Arrays.stream(root.getProjects()).filter(IProject::isOpen).map(this::createSearchContext)
					.map(ISearchContext.class::cast).toList();
		}
		if (project) {
			return List.of(createSearchContext(iproject));
		}
		return List.of();
	}

	private ISearchContext createSearchContext(final IProject project) {
		return new AbstractLiveSearchContext(project) {
			@Override
			public Stream<URI> getTypes() {
				Stream<TypeEntry> s = Stream.empty();
				if (blockTypesRecord.isSelected()) {
					final Predicate<TypeEntry> filter = entry -> blockTypesRecord.matches(entry.getFullTypeName(),
							entry.getTypeName(), entry.getComment());
					s = Stream.concat(s, Stream.concat(getTypelib().getFbTypes().filter(filter),
							getTypelib().getSubAppTypes().filter(filter)));
				}
				if (blockInstanceRecord.isSelected() || untypedSubappRecord.isSelected()) {
					s = Stream.concat(s, getTypelib().getSystems());
				}
				if (dataTypesRecord.isSelected()) {
					s = Stream.concat(s,
							getTypelib().getDataTypeLibrary().getDerivedDataTypes().filter(entry -> dataTypesRecord
									.matches(entry.getFullTypeName(), entry.getTypeName(), entry.getComment())));
				}
				if (attributeTypesRecord.isSelected()) {
					s = Stream.concat(s, getTypelib().getAttributeTypes().filter(entry -> attributeTypesRecord
							.matches(entry.getFullTypeName(), entry.getTypeName(), entry.getComment())));
				}
				if (ignoreLinkedLibraries) {
					s = s.filter(linkedElementsFilter);
				}
				return s.map(TypeEntry::getURI).filter(Objects::nonNull);
			}

			@Override
			public EObject mapTypes(final URI uri) {
				final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
				return typeEntry.getType(); // use original for search
			}
		};
	}

	public static IEC61499SearchFilter createAttributeDeclarationSearchFilter(
			final AttributeDeclaration attributeDeclaration) {
		return searchCandidate -> searchCandidate instanceof final Attribute attribute
				&& attribute.getAttributeDeclaration() != null
				&& attributeDeclaration.getTypeEntry() == attribute.getAttributeDeclaration().getTypeEntry();
	}

	public static IEC61499SearchFilter createSearchFilter(final int mode, final List<Filter> filters) {
		return new IEC61499SearchFilter() {
			private final Pattern namePattern = StringMatcher.createPattern(filters.get(0));
			private final Pattern typePattern = StringMatcher.createPattern(filters.get(1));
			private final Pattern commentPattern = StringMatcher.createPattern(filters.get(2));
			private final Pattern valuePattern = StringMatcher.createPattern(filters.get(3));

			@Override
			public boolean apply(final EObject searchCandidate) {
				if (!isValidCandidate(searchCandidate)) {
					return false;
				}
				final ITypedElement typedElement = (ITypedElement) searchCandidate;
				return StringMatcher.matches(typedElement.getName(), filters.get(0), namePattern)
						&& StringMatcher.matches(typedElement.getTypeName(), filters.get(1), typePattern)
						&& StringMatcher.matches(typedElement.getComment(), filters.get(2), commentPattern)
						&& StringMatcher.matches(InitialValueHelper.getInitialOrDefaultValue(typedElement),
								filters.get(3), valuePattern);
			}

			private boolean isValidCandidate(final Object searchCandidate) {
				return (searchCandidate instanceof VarDeclaration && mode == 0)
						|| (searchCandidate instanceof Attribute && mode == 1);
			}
		};
	}

	public ISearchChildrenProvider createChildrenSearchProvider() {
		return new SearchChildrenProvider();
	}

	private class SearchChildrenProvider implements ISearchChildrenProvider {
		@Override
		public boolean hasChildren(final EObject obj) {
			return obj instanceof FBType || obj instanceof AutomationSystem
					|| (untypedSubappRecord.isSelected() && obj instanceof UntypedSubApp)
					|| (dataTypesRecord.isSelected() && obj instanceof StructuredType)
					|| (attributeTypesRecord.isSelected() && obj instanceof AttributeDeclaration)
					|| obj instanceof Application
					|| (blockInstanceRecord.isSelected() && obj instanceof FBNetworkElement)
					|| obj instanceof IInterfaceElement;
		}

		@Override
		public Stream<? extends EObject> getChildren(final EObject obj) {
			return switch (obj) {
			case final FBType fbType -> SearchChildrenProviderHelper.getFBTypeChildren(fbType);
			case final AutomationSystem system ->
				Stream.concat(system.getAttributes().stream(), system.getApplication().stream());
			case final Application application -> getApplicationChildren(application);
			case final UntypedSubApp untypedSubapp ->
				SearchChildrenProviderHelper.getUntypedSubappChildren(untypedSubapp);
			case final StructuredType structType -> Stream.concat(
					SearchChildrenProviderHelper.getStructChildren(structType), structType.getAttributes().stream());
			case final AttributeDeclaration attrdecl -> SearchChildrenProviderHelper.getAttributeDeclChildren(attrdecl);
			case final BlockFBNetworkElement elem -> Stream.concat(elem.getAttributes().stream(),
					SearchChildrenProviderHelper.getInterfaceListChildren(elem.getInterface()));
			case final ConfigurableObject configurableObject -> configurableObject.getAttributes().stream();
			default -> Stream.empty();
			};
		}

		private Stream<? extends EObject> getApplicationChildren(final Application application) {
			Stream<? extends EObject> stream = Stream.empty();
			if (untypedSubappRecord.isSelected()) {
				stream = application.getFBNetwork().getNetworkElements().stream()
						.filter(fbne -> fbne instanceof UntypedSubApp
								&& untypedSubappRecord.matches(fbne.getName(), fbne.getTypeName(), fbne.getComment()));
			}
			if (blockInstanceRecord.isSelected()) {
				stream = Stream.concat(stream, application.getFBNetwork().getNetworkElements().stream()
						.filter(fbne -> (fbne instanceof TypedSubApp || fbne instanceof FB)
								&& blockInstanceRecord.matches(fbne.getName(), fbne.getTypeName(), fbne.getComment())));
			}
			stream = Stream.concat(stream, application.getFBNetwork().getAdapterConnections().stream());
			stream = Stream.concat(stream, application.getFBNetwork().getDataConnections().stream());
			stream = Stream.concat(stream, application.getFBNetwork().getEventConnections().stream());
			return Stream.concat(stream, application.getAttributes().stream());
		}
	}
}
