/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.editors;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.editors.FilterComposite.Filter;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
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

// TODO cleanup, rename class and record, better way to handle paramters
public class SearchHelper {
	public record FilterRecord(boolean selected, Filter nameFilter, Filter typeFilter, Filter commentFilter) {
	}

	private final Pattern fbSubappTypesNamePattern;
	private final Pattern fbSubappTypesTypePattern;
	private final Pattern fbSubappTypesCommentPattern;
	private final Pattern fbTypedSubappInstanceNamePattern;
	private final Pattern fbTypedSubappInstanceTypePattern;
	private final Pattern fbTypedSubappInstanceCommentPattern;
	private final Pattern untypedSubappNamePattern;
	private final Pattern untypedSubappTypePattern;
	private final Pattern untypedSubappCommentPattern;
	private final Pattern dataTypeNamePattern;
	private final Pattern dataTypeTypePattern;
	private final Pattern dataTypeCommentPattern;
	private final Pattern attributeDeclNamePattern;
	private final Pattern attributeDeclTypePattern;
	private final Pattern attributeDeclCommentPattern;

	final FilterRecord fbSubappTypesRecord;
	final FilterRecord fbTypedSubappInstanceRecord;
	final FilterRecord untypedSubappRecord;
	final FilterRecord dataTypesRecord;
	final FilterRecord attributeTypesRecord;

	public SearchHelper(final FilterRecord fbSubappTypesRecord, final FilterRecord fbTypedSubappInstanceRecord,
			final FilterRecord untypedSubappRecord, final FilterRecord dataTypesRecord,
			final FilterRecord attributeTypesRecord) {

		this.fbSubappTypesRecord = fbSubappTypesRecord;
		this.fbTypedSubappInstanceRecord = fbTypedSubappInstanceRecord;
		this.untypedSubappRecord = untypedSubappRecord;
		this.dataTypesRecord = dataTypesRecord;
		this.attributeTypesRecord = attributeTypesRecord;

		fbSubappTypesNamePattern = createPattern(fbSubappTypesRecord.nameFilter());
		fbSubappTypesTypePattern = createPattern(fbSubappTypesRecord.typeFilter());
		fbSubappTypesCommentPattern = createPattern(fbSubappTypesRecord.commentFilter());
		fbTypedSubappInstanceNamePattern = createPattern(fbTypedSubappInstanceRecord.nameFilter());
		fbTypedSubappInstanceTypePattern = createPattern(fbTypedSubappInstanceRecord.typeFilter());
		fbTypedSubappInstanceCommentPattern = createPattern(fbTypedSubappInstanceRecord.commentFilter());
		untypedSubappNamePattern = createPattern(untypedSubappRecord.nameFilter());
		untypedSubappTypePattern = createPattern(untypedSubappRecord.typeFilter());
		untypedSubappCommentPattern = createPattern(untypedSubappRecord.commentFilter());
		dataTypeNamePattern = createPattern(dataTypesRecord.nameFilter());
		dataTypeTypePattern = createPattern(dataTypesRecord.typeFilter());
		dataTypeCommentPattern = createPattern(dataTypesRecord.commentFilter());
		attributeDeclNamePattern = createPattern(attributeTypesRecord.nameFilter());
		attributeDeclTypePattern = createPattern(attributeTypesRecord.typeFilter());
		attributeDeclCommentPattern = createPattern(attributeTypesRecord.commentFilter());
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
				Stream<URI> s = Stream.empty();
				if (fbSubappTypesRecord.selected()) {
					final Predicate<TypeEntry> filter = entry -> (matchesString(entry.getFullTypeName(),
							fbSubappTypesRecord.nameFilter(), fbSubappTypesNamePattern)
							&& matchesString(entry.getTypeName(), fbSubappTypesRecord.typeFilter(),
									fbSubappTypesTypePattern)
							&& matchesString(entry.getComment(), fbSubappTypesRecord.commentFilter(),
									fbSubappTypesCommentPattern));
					s = Stream.concat(s,
							Stream.concat(getTypelib().getFbTypes().stream().filter(filter).map(TypeEntry::getURI),
									getTypelib().getSubAppTypes().stream().filter(filter).map(TypeEntry::getURI)));
				}
				if (fbTypedSubappInstanceRecord.selected() || untypedSubappRecord.selected()) {
					s = Stream.concat(s, getTypelib().getSystems().stream().map(TypeEntry::getURI));
				}
				if (dataTypesRecord.selected()) {
					s = Stream.concat(s,
							getTypelib().getDataTypeLibrary().getDerivedDataTypes().stream()
									.filter(dtEntry -> (matchesString(dtEntry.getFullTypeName(),
											dataTypesRecord.nameFilter(), dataTypeNamePattern)
											&& matchesString(dtEntry.getTypeName(), dataTypesRecord.typeFilter(),
													dataTypeTypePattern)
											&& matchesString(dtEntry.getComment(), dataTypesRecord.commentFilter(),
													dataTypeCommentPattern)))
									.map(TypeEntry::getURI));
				}
				if (attributeTypesRecord.selected()) {
					s = Stream.concat(s,
							getTypelib().getAttributeTypes().stream()
									.filter(atEntry -> (matchesString(atEntry.getFullTypeName(),
											attributeTypesRecord.nameFilter(), attributeDeclNamePattern)
											&& matchesString(atEntry.getTypeName(), attributeTypesRecord.typeFilter(),
													attributeDeclTypePattern)
											&& matchesString(atEntry.getComment(), attributeTypesRecord.commentFilter(),
													attributeDeclCommentPattern)))
									.map(TypeEntry::getURI));
				}
				return s.filter(Objects::nonNull);
			}

			@Override
			public LibraryElement getLibraryElement(final URI uri) {
				final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
				return typeEntry.getType(); // use original for search
			}

			@Override
			public Collection<URI> getSubappTypes() {
				return Collections.emptyList();
			}

			@Override
			public Collection<URI> getFBTypes() {
				return Collections.emptyList();
			}

			@Override
			public Collection<URI> getAllTypes() {
				return Collections.emptyList();
			}
		};
	}

	public static IEC61499SearchFilter createSearchFilter(final int mode, final List<Filter> filters) {
		return new IEC61499SearchFilter() {
			private final Pattern namePattern = createPattern(filters.get(0));
			private final Pattern typePattern = createPattern(filters.get(1));
			private final Pattern commentPattern = createPattern(filters.get(2));
			private final Pattern valuePattern = createPattern(filters.get(3));

			@Override
			public boolean apply(final EObject searchCandidate) {
				if (!isValidCandidate(searchCandidate)) {
					return false;
				}

				final ITypedElement typedElement = (ITypedElement) searchCandidate;

				return matchesString(typedElement.getName(), filters.get(0), namePattern)
						&& matchesString(typedElement.getTypeName(), filters.get(1), typePattern)
						&& matchesString(typedElement.getComment(), filters.get(2), commentPattern)
						&& matchesString(InitialValueHelper.getInitialOrDefaultValue(typedElement), filters.get(3),
								valuePattern);
			}

			private boolean isValidCandidate(final Object searchCandidate) {
				return (searchCandidate instanceof VarDeclaration && mode == 0)
						|| (searchCandidate instanceof Attribute && mode == 1);
			}
		};
	}

	public ISearchChildrenProvider createChildrenSearchProvider() {
		return new ISearchChildrenProvider() {
			@Override
			public boolean hasChildren(final EObject obj) {
				return (obj instanceof FBType) || (obj instanceof AutomationSystem)
						|| (untypedSubappRecord.selected() && obj instanceof UntypedSubApp)
						|| (dataTypesRecord.selected() && obj instanceof StructuredType)
						|| (attributeTypesRecord.selected() && obj instanceof AttributeDeclaration)
						|| (obj instanceof final Application)
						|| (fbTypedSubappInstanceRecord.selected() && obj instanceof FBNetworkElement)
						|| (obj instanceof IInterfaceElement);
			}

			@Override
			public Stream<? extends EObject> getChildren(final EObject obj) {
				return switch (obj) {
				case final FBType fbType -> SearchChildrenProviderHelper.getFBTypeChildren(fbType);
				case final AutomationSystem system ->
					Stream.concat(system.getAttributes().stream(), system.getApplication().stream());
				case final Application application -> {
					if (untypedSubappRecord.selected()) {
						yield application.getFBNetwork().getNetworkElements().stream()
								.filter(fbne -> (fbne instanceof UntypedSubApp
										&& matchesString(fbne.getName(), untypedSubappRecord.nameFilter(),
												untypedSubappNamePattern)
										&& matchesString(fbne.getTypeName(), untypedSubappRecord.typeFilter(),
												untypedSubappTypePattern)
										&& matchesString(fbne.getComment(), untypedSubappRecord.commentFilter(),
												untypedSubappCommentPattern)));
					}
					if (fbTypedSubappInstanceRecord.selected()) {
						yield application.getFBNetwork().getNetworkElements().stream()
								.filter(fbne -> (fbne instanceof TypedSubApp || fbne instanceof FB)
										&& matchesString(fbne.getName(), fbTypedSubappInstanceRecord.nameFilter(),
												fbTypedSubappInstanceNamePattern)
										&& matchesString(fbne.getTypeName(), fbTypedSubappInstanceRecord.typeFilter(),
												fbTypedSubappInstanceTypePattern)
										&& matchesString(fbne.getComment(), fbTypedSubappInstanceRecord.commentFilter(),
												fbTypedSubappInstanceCommentPattern));
					}
					Stream<? extends EObject> stream = application.getFBNetwork().getNetworkElements().stream();
					stream = Stream.concat(stream, application.getFBNetwork().getAdapterConnections().stream());
					stream = Stream.concat(stream, application.getFBNetwork().getDataConnections().stream());
					stream = Stream.concat(stream, application.getFBNetwork().getEventConnections().stream());
					yield Stream.concat(stream, application.getAttributes().stream());
				}
				case final UntypedSubApp untypedSubapp ->
					SearchChildrenProviderHelper.getUntypedSubappChildren(untypedSubapp);
				case final StructuredType structType -> SearchChildrenProviderHelper.getStructChildren(structType);
				case final AttributeDeclaration attrdecl ->
					SearchChildrenProviderHelper.getAttributeDeclChildren(attrdecl);
				case final FBNetworkElement elem -> Stream.concat(elem.getAttributes().stream(),
						SearchChildrenProviderHelper.getInterfaceListChildren(elem.getInterface()));
				case final ConfigurableObject configurableObject -> configurableObject.getAttributes().stream();
				default -> null;
				};
			}
		};
	}

	private static boolean matchesString(final String toCheck, final FilterComposite.Filter filter,
			final Pattern pattern) {
		return !filter.selected.getSelection() || compareStrings(filter, pattern, toCheck);
	}

	private static boolean compareStrings(final FilterComposite.Filter filter, final Pattern pattern, String element) {
		String search = filter.textField.getText();
		if (search == null || element == null) {
			return false;
		}

		if (!filter.caseSensitive.getSelection()) {
			element = element.toLowerCase();
			search = search.toLowerCase();
		}
		if (filter.regularExpression.getSelection() && pattern != null) {
			return pattern.matcher(element).find();
		}
		if (filter.wholeWord.getSelection()) {
			final String searchString = search;
			return Arrays.stream(element.split("\\W+")).anyMatch(word -> word.equals(searchString)); //$NON-NLS-1$
		}
		if (filter.exactMatch.getSelection()) {
			return element.equals(search);
		}
		return element.contains(search);
	}

	private static Pattern createPattern(final Filter filter) {
		String query = filter.textField.getText();
		if (!filter.regularExpression.getSelection()) {
			return null;
		}
		if (!filter.caseSensitive.getSelection()) {
			query = query.toLowerCase();
		}
		if (filter.exactMatch.getSelection()) {
			query = "^" + query + "$"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		try {
			return Pattern.compile(query);
		} catch (final PatternSyntaxException exception) {
			return null;
		}
	}
}
