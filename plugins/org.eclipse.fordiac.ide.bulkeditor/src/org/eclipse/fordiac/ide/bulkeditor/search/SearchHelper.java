/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH, Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - add AllTypes and AllInstances
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.search;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.InstanceConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.PinConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.TypeConfig;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.ISearchContext;
import org.eclipse.fordiac.ide.model.search.types.ISearchChildrenProvider;
import org.eclipse.fordiac.ide.model.search.types.SearchChildrenProviderHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class SearchHelper {

	private static final char HIERARCHY_NAME_DELIMITER = '.';

	public static final Predicate<TypeEntry> linkedElementsFilter = entry -> {
		for (final String segment : entry.getFile().getFullPath().segments()) {
			if (segment.equals("External Libraries") || segment.equals("Standard Libraries")) { //$NON-NLS-1$ //$NON-NLS-2$
				return false;
			}
		}
		return true;
	};

	private SearchHelper() {
	}

	public static ISearchContext createSearchContext(final IProject project, final PlaceConfig cfg) {
		if (cfg.subappHierarchyScope()) {
			return new SubappHierarchySearchContext(project, cfg.subappHierarchy());
		}
		return new PlaceAwareSearchContext(project, cfg);
	}

	public static ISearchChildrenProvider createChildrenSearchProvider(final PlaceConfig cfg) {
		return new PlaceAwareChildrenProvider(cfg);
	}

	public static EObject resolveElement(final URI uri) {
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri);
		if (typeEntry == null || typeEntry.getType() == null) {
			return null;
		}
		if (!uri.hasFragment()) {
			return typeEntry.getType();
		}
		final Resource resource = typeEntry.getType().eResource();
		return resource != null ? resource.getEObject(uri.fragment()) : null;
	}

	public static String getSubappHierarchyName(final EObject element) {
		final EObject root = EcoreUtil.getRootContainer(element);
		final String rootName = root instanceof final LibraryElement libraryElement
				? PackageNameHelper.getFullTypeName(libraryElement)
				: ""; //$NON-NLS-1$
		if (root != element && element instanceof final INamedElement namedElement) {
			return rootName + HIERARCHY_NAME_DELIMITER + namedElement.getQualifiedName();
		}
		return rootName;
	}

	public static INamedElement resolveSubappHierarchyElement(final TypeLibrary typeLibrary, final String name) {
		final int delimiter = name.indexOf(HIERARCHY_NAME_DELIMITER);
		final String rootName = delimiter < 0 ? name : name.substring(0, delimiter);
		final TypeEntry rootEntry = findSubappHierarchyRoot(typeLibrary, rootName);
		if (rootEntry == null || rootEntry.getType() == null) {
			return null;
		}
		if (delimiter < 0) {
			return rootEntry.getType();
		}
		return rootEntry.getType().findByQualifiedName(name.substring(delimiter + 1))
				.filter(element -> element instanceof Application || element instanceof UntypedSubApp).findFirst()
				.orElse(null);
	}

	private static TypeEntry findSubappHierarchyRoot(final TypeLibrary typeLibrary, final String name) {
		final TypeEntry system = typeLibrary.getSystemEntry(name);
		return system != null ? system : typeLibrary.getSubAppTypeEntry(name);
	}

	private static class SubappHierarchySearchContext extends AbstractLiveSearchContext {
		private final Map<URI, EObject> roots = new LinkedHashMap<>();

		public SubappHierarchySearchContext(final IProject project, final List<String> hierarchy) {
			super(project);
			final List<INamedElement> elements = hierarchy.stream()
					.map(name -> resolveSubappHierarchyElement(getTypelib(), name)).filter(Objects::nonNull).toList();
			// elements inside other elements of the hierarchy are searched with them
			elements.stream().filter(element -> !isInsideOther(element, elements))
					.forEach(element -> roots.put(EcoreUtil.getURI(element), element));
		}

		private static boolean isInsideOther(final EObject element, final List<? extends EObject> others) {
			return others.stream().anyMatch(other -> other != element && EcoreUtil.isAncestor(other, element));
		}

		@Override
		public Stream<URI> getTypes() {
			return roots.keySet().stream();
		}

		@Override
		public EObject mapTypes(final URI uri) {
			return roots.get(uri);
		}
	}

	private static class PlaceAwareSearchContext extends AbstractLiveSearchContext {
		final PlaceConfig cfg;

		public PlaceAwareSearchContext(final IProject project, final PlaceConfig cfg) {
			super(project);
			this.cfg = cfg;
		}

		@Override
		public Stream<URI> getTypes() {
			Stream<TypeEntry> s = Stream.empty();

			// Type nodes
			if (cfg.isSelected(cfg.simpleType())) {
				s = Stream.concat(s, getSimpleTypes(cfg));
			}
			if (cfg.isSelected(cfg.basicType())) {
				s = Stream.concat(s, getBasicTypes(cfg));
			}
			if (cfg.isSelected(cfg.compositeType())) {
				s = Stream.concat(s, getCompositeTypes(cfg));
			}
			if (cfg.isSelected(cfg.serviceInterfaceType())) {
				s = Stream.concat(s, getServiceInterfaceTypes(cfg));
			}
			if (cfg.isSelected(cfg.functionType())) {
				s = Stream.concat(s, getFunctionTypes(cfg));
			}
			if (cfg.isSelected(cfg.subappType())) {
				s = Stream.concat(s, getSubappTypes(cfg));
			}
			if (cfg.isSelected(cfg.structType())) {
				s = Stream.concat(s, getStructTypes(cfg));
			}
			if (cfg.isSelected(cfg.attributeType())) {
				s = Stream.concat(s, getAttributeTypes(cfg));
			}

			// Instance nodes
			if (cfg.needsSystems()) {
				s = Stream.concat(s, getTypelib().getSystems());
			}
			if (cfg.needsCompositeFBTypes()) {
				s = Stream.concat(s,
						getTypelib().getFbTypes().filter(entry -> entry.getType() instanceof CompositeFBType
								&& !(entry.getType() instanceof SubAppType)));
			}
			if (cfg.needsSubappTypesForInstances()) {
				s = Stream.concat(s, getTypelib().getSubAppTypes());
			}

			if (cfg.ignoreLinkedLibraries()) {
				s = s.filter(SearchHelper.linkedElementsFilter);
			}

			return s.distinct().map(TypeEntry::getURI).filter(Objects::nonNull);
		}

		@Override
		public EObject mapTypes(final URI uri) {
			final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
			return typeEntry.getType();
		}

		private static MatchTarget typeTarget(final TypeEntry entry) {
			return MatchTarget.of(entry.getFullTypeName(), null, entry.getComment(), null, entry.getType());
		}

		private Stream<? extends TypeEntry> getSimpleTypes(final PlaceConfig cfg) {
			return getTypelib().getFbTypes().filter(entry -> entry.getType() instanceof SimpleFBType)
					.filter(entry -> cfg.matches(cfg.simpleType(), typeTarget(entry)));
		}

		private Stream<? extends TypeEntry> getBasicTypes(final PlaceConfig cfg) {
			return getTypelib().getFbTypes().filter(entry -> entry.getType() instanceof BasicFBType)
					.filter(entry -> cfg.matches(cfg.basicType(), typeTarget(entry)));
		}

		private Stream<? extends TypeEntry> getCompositeTypes(final PlaceConfig cfg) {
			return getTypelib().getFbTypes().filter(entry -> entry.getType() instanceof CompositeFBType
					&& !(entry.getType() instanceof SubAppType) && cfg.matches(cfg.compositeType(), typeTarget(entry)));
		}

		private Stream<? extends TypeEntry> getServiceInterfaceTypes(final PlaceConfig cfg) {
			return getTypelib().getFbTypes().filter(entry -> entry.getType() instanceof ServiceInterfaceFBType)
					.filter(entry -> cfg.matches(cfg.serviceInterfaceType(), typeTarget(entry)));
		}

		private Stream<? extends TypeEntry> getFunctionTypes(final PlaceConfig cfg) {
			return getTypelib().getFbTypes().filter(entry -> entry.getType() instanceof FunctionFBType)
					.filter(entry -> cfg.matches(cfg.functionType(), typeTarget(entry)));
		}

		private Stream<? extends TypeEntry> getSubappTypes(final PlaceConfig cfg) {
			return getTypelib().getSubAppTypes().filter(entry -> cfg.matches(cfg.subappType(), typeTarget(entry)));
		}

		private Stream<? extends TypeEntry> getStructTypes(final PlaceConfig cfg) {
			return getTypelib().getDataTypeLibrary().getDerivedDataTypes()
					.filter(entry -> entry.getType() instanceof StructuredType)
					.filter(entry -> cfg.matches(cfg.structType(), typeTarget(entry)));
		}

		private Stream<? extends TypeEntry> getAttributeTypes(final PlaceConfig cfg) {
			return getTypelib().getAttributeTypes()
					.filter(entry -> cfg.matches(cfg.attributeType(), typeTarget(entry)));
		}
	}

	private static class PlaceAwareChildrenProvider implements ISearchChildrenProvider {
		private final PlaceConfig cfg;

		private PlaceAwareChildrenProvider(final PlaceConfig cfg) {
			this.cfg = cfg;
		}

		@Override
		public boolean hasChildren(final EObject obj) {
			// Type-level: always expand type definitions
			if (obj instanceof final FBType fbType) {
				return isTypeSelected(fbType) || hasFBNetworkToTraverse(fbType);
			}
			if (cfg.isSelected(cfg.structType()) && obj instanceof StructuredType) {
				return true;
			}
			if (cfg.isSelected(cfg.attributeType()) && obj instanceof AttributeDeclaration) {
				return true;
			}

			// Instance-level: occurrence-driven traversal
			if (obj instanceof AutomationSystem || obj instanceof Application) {
				return cfg.needsSystems();
			}
			if (obj instanceof UntypedSubApp) {
				return true;
			}

			// Shared: elements inside networks need interface expansion
			if (cfg.anyInstanceSelected() && obj instanceof FBNetworkElement) {
				return true;
			}

			return obj instanceof IInterfaceElement || obj instanceof Connection;
		}

		private boolean hasFBNetworkToTraverse(final FBType fbType) {
			if (fbType instanceof SubAppType) {
				return cfg.needsSubappTypesForInstances();
			}
			if (fbType instanceof CompositeFBType) {
				return cfg.needsCompositeFBTypes();
			}
			return false;
		}

		@Override
		public Stream<? extends EObject> getChildren(final EObject obj) {
			return switch (obj) {
			case final FBType fbType -> getFBTypeChildren(fbType);
			case final AutomationSystem system ->
				Stream.concat(system.getAttributes().stream(), system.getApplication().stream());
			case final Application app -> getApplicationChildren(app);
			case final UntypedSubApp subapp -> getUntypedSubappChildren(subapp);
			case final StructuredType structType -> getStructTypeChildren(structType);
			case final AttributeDeclaration attrdecl -> getAttributeDeclChildren(attrdecl);
			case final BlockFBNetworkElement elem -> getInstanceElementChildren(elem);
			case final ConfigurableObject co -> co.getAttributes().stream();
			default -> Stream.empty();
			};
		}

		private Stream<? extends EObject> getInstanceElementChildren(final BlockFBNetworkElement elem) {
			Stream<? extends EObject> children = elem.getAttributes().stream();

			final List<PinConfig> pinCfgs = getInstancePinConfigs(elem);
			children = Stream.concat(children, getFilteredInterfaceChildren(elem.getInterface(), pinCfgs));

			return children;
		}

		/**
		 * PIN configurations of the nodes that accept the instance. A single node is used without matching the
		 * instance again. If no node matches in the occurrence context of the instance, all nodes are used.
		 */
		private List<PinConfig> getInstancePinConfigs(final FBNetworkElement elem) {
			final InstanceConfig category = resolveInstancePinConfig(elem);
			final List<InstanceConfig> branches = cfg.instanceBranches(category);
			if (branches.size() > 1) {
				final OccurrenceContext ctx = resolveOccurrenceContext(elem);
				final List<InstanceConfig> matching = ctx != null
						? getMatchingInstanceBranches(category, instanceTarget(elem), ctx.kind(), ctx.context())
						: List.of();
				if (!matching.isEmpty()) {
					return matching.stream().map(InstanceConfig::pin).toList();
				}
			}
			return branches.stream().map(InstanceConfig::pin).toList();
		}

		private InstanceConfig resolveInstancePinConfig(final FBNetworkElement elem) {
			if (elem instanceof TypedSubApp) {
				return cfg.typedSubapp();
			}
			if (elem instanceof UntypedSubApp) {
				return cfg.untypedSubapp();
			}
			if (elem instanceof final FB fb) {
				final FBType type = fb.getType();
				if (type instanceof CompositeFBType) {
					return cfg.compositeFB();
				}
				if (type instanceof SimpleFBType) {
					return cfg.simpleFB();
				}
				if (type instanceof BasicFBType) {
					return cfg.basicFB();
				}
				if (type instanceof ServiceInterfaceFBType) {
					return cfg.serviceInterfaceFB();
				}
				if (type instanceof FunctionFBType) {
					return cfg.functionFB();
				}
			}
			return InstanceConfig.INACTIVE;
		}

		private static Stream<? extends EObject> getFilteredInterfaceChildren(final InterfaceList iface,
				final List<PinConfig> pinCfgs) {
			if (!isAnyActive(pinCfgs)) {
				return Stream.empty();
			}

			return SearchChildrenProviderHelper.getInterfaceListChildren(iface).filter(pin -> {
				final String value = (pin instanceof final VarDeclaration varDecl) ? varDecl.getValueString() : null;
				return includesPin(pinCfgs,
						MatchTarget.of(pin.getName(), pin.getTypeName(), pin.getComment(), value, pin));
			});
		}

		private static boolean isAnyActive(final List<PinConfig> pinCfgs) {
			return pinCfgs.stream().anyMatch(PinConfig::active);
		}

		private static boolean includesPin(final List<PinConfig> pinCfgs, final MatchTarget target) {
			return pinCfgs.stream().anyMatch(pinCfg -> pinCfg.active() && pinCfg.includePin(target));
		}

		private Stream<? extends EObject> getFBTypeChildren(final FBType fbType) {
			Stream<? extends EObject> children = Stream.empty();

			if (isTypeAccepted(fbType)) {
				final List<PinConfig> pinCfgs = getTypePinConfigs(fbType, getTypeConfig(fbType));

				children = getFilteredInterfaceChildren(fbType.getInterfaceList(), pinCfgs);
				children = Stream.concat(children, fbType.getAttributes().stream());
				if (fbType instanceof final BaseFBType baseFBType && isAnyActive(pinCfgs)) {
					children = Stream.concat(children, baseFBType.getInternalVars().stream());
					children = Stream.concat(children, baseFBType.getInternalConstVars().stream());
				}
			}

			// Instance-level: traverse FBNetwork if occurrence demands it
			// SubAppType before CompositeFBType (SubAppType extends CompositeFBType)
			if (fbType instanceof final SubAppType subappType && cfg.needsSubappTypesForInstances()) {
				children = Stream.concat(children, getFilteredNetworkChildren(subappType.getFBNetwork(),
						PlaceConfig.OCC_TYPED_SUBAPP, subappType));
			} else if (fbType instanceof final CompositeFBType composite && cfg.needsCompositeFBTypes()) {
				children = Stream.concat(children,
						getFilteredNetworkChildren(composite.getFBNetwork(), PlaceConfig.OCC_COMPOSITE_FB, composite));
			}

			return children;
		}

		private boolean isTypeSelected(final FBType fbType) {
			// Specific before general — SubAppType extends CompositeFBType
			if (fbType instanceof SubAppType) {
				return cfg.isSelected(cfg.subappType());
			}
			if (fbType instanceof CompositeFBType) {
				return cfg.isSelected(cfg.compositeType());
			}
			if (fbType instanceof SimpleFBType) {
				return cfg.isSelected(cfg.simpleType());
			}
			if (fbType instanceof BasicFBType) {
				return cfg.isSelected(cfg.basicType());
			}
			if (fbType instanceof ServiceInterfaceFBType) {
				return cfg.isSelected(cfg.serviceInterfaceType());
			}
			if (fbType instanceof FunctionFBType) {
				return cfg.isSelected(cfg.functionType());
			}
			return false;
		}

		/**
		 * Whether a type node accepts the type. Composite and subapp types that are only searched for instances do
		 * not list their own pins and attributes.
		 */
		private boolean isTypeAccepted(final FBType fbType) {
			if (!isTypeSelected(fbType)) {
				return false;
			}
			final TypeEntry entry = fbType.getTypeEntry();
			return entry == null || cfg.matches(getTypeConfig(fbType), PlaceAwareSearchContext.typeTarget(entry));
		}

		private Stream<? extends EObject> getApplicationChildren(final Application application) {
			Stream<? extends EObject> stream = getFilteredNetworkChildren(application.getFBNetwork(),
					PlaceConfig.OCC_APPLICATION, application);

			// Always include application-level attributes
			stream = Stream.concat(stream, application.getAttributes().stream());

			return stream;
		}

		private Stream<? extends EObject> getAttributeDeclChildren(final AttributeDeclaration attrdecl) {
			// Attributes on the declaration — always included
			Stream<? extends EObject> children = attrdecl.getAttributes().stream();
			final List<PinConfig> pinCfgs = getTypePinConfigs(attrdecl, cfg.attributeType());
			if (attrdecl.getType() instanceof final StructuredType structType && isAnyActive(pinCfgs)) {
				// members of attributeType Struct
				final var typeChildren = SearchChildrenProviderHelper.getStructChildren(structType)
						.filter(member -> includesPin(pinCfgs, MatchTarget.of(member.getName(), member.getTypeName(),
								member.getComment(), member.getValueString(), member)));
				children = Stream.concat(children, typeChildren);
			} else if (attrdecl.getType() instanceof DirectlyDerivedType) {
				// TODO: directly derived types (Add special constraint for AttributeType)
			}

			return children;
		}

		private Stream<? extends EObject> getStructTypeChildren(final StructuredType structType) {
			// Attributes on the struct — always included
			Stream<? extends EObject> children = structType.getAttributes().stream();

			// Member variables — gated and filtered by StructType's PIN config
			final List<PinConfig> pinCfgs = getTypePinConfigs(structType, cfg.structType());
			if (isAnyActive(pinCfgs)) {
				final var typeChildren = SearchChildrenProviderHelper.getStructChildren(structType)
						.filter(member -> includesPin(pinCfgs, MatchTarget.of(member.getName(), member.getTypeName(),
								member.getComment(), member.getValueString(), member)));
				children = Stream.concat(children, typeChildren);
			}

			return children;
		}

		private TypeConfig getTypeConfig(final FBType fbType) {
			if (fbType instanceof SubAppType) {
				return cfg.subappType();
			}
			if (fbType instanceof CompositeFBType) {
				return cfg.compositeType();
			}
			if (fbType instanceof SimpleFBType) {
				return cfg.simpleType();
			}
			if (fbType instanceof BasicFBType) {
				return cfg.basicType();
			}
			if (fbType instanceof ServiceInterfaceFBType) {
				return cfg.serviceInterfaceType();
			}
			if (fbType instanceof FunctionFBType) {
				return cfg.functionType();
			}
			return TypeConfig.INACTIVE;
		}

		/**
		 * PIN configurations of the nodes whose constraint accepts the type. Only called for accepted types, so a
		 * single node is used without matching the type again.
		 */
		private List<PinConfig> getTypePinConfigs(final LibraryElement type, final TypeConfig category) {
			final List<TypeConfig> branches = cfg.typeBranches(category);
			if (branches.size() > 1 && type.getTypeEntry() != null) {
				final MatchTarget target = PlaceAwareSearchContext.typeTarget(type.getTypeEntry());
				return branches.stream().filter(branch -> branch.matches(target)).map(TypeConfig::pin).toList();
			}
			return branches.stream().map(TypeConfig::pin).toList();
		}

		private Stream<? extends EObject> getUntypedSubappChildren(final UntypedSubApp untypedSubapp) {
			final OccurrenceContext ctx = resolveOccurrenceContext(untypedSubapp);
			Stream<? extends EObject> stream = Stream.empty();

			final List<InstanceConfig> branches = ctx != null
					? getMatchingInstanceBranches(cfg.untypedSubapp(), MatchTarget.of(untypedSubapp.getName(), null,
							untypedSubapp.getComment(), null, untypedSubapp), ctx.kind(), ctx.context())
					: List.of();
			if (!branches.isEmpty()) {
				final List<PinConfig> pinCfgs = branches.stream().map(InstanceConfig::pin).toList();
				stream = Stream.concat(stream, getFilteredInterfaceChildren(untypedSubapp.getInterface(), pinCfgs));
				stream = Stream.concat(stream, untypedSubapp.getAttributes().stream());
			}
			if (ctx != null) {
				stream = Stream.concat(stream,
						getFilteredNetworkChildren(untypedSubapp.getSubAppNetwork(), ctx.kind(), ctx.context()));
			}

			return stream;
		}

		private Stream<? extends EObject> getFilteredNetworkChildren(final FBNetwork network, final String occurrence,
				final INamedElement context) {
			Stream<? extends EObject> stream = Stream.empty();

			// Network elements filtered by occurrence-aware instance matching
			stream = Stream.concat(stream, network.getNetworkElements().stream()
					.filter(fbne -> matchesInstanceConstraint(fbne, occurrence, context)));

			if (cfg.connection().selected()) {
				final Predicate<Connection> connectionFilter = conn -> matchesConnectionConstraint(conn, occurrence,
						context);
				stream = Stream.concat(stream, network.getAdapterConnections().stream().filter(connectionFilter));
				stream = Stream.concat(stream, network.getDataConnections().stream().filter(connectionFilter));
				stream = Stream.concat(stream, network.getEventConnections().stream().filter(connectionFilter));
			}

			return stream;
		}

		private boolean matchesConnectionConstraint(final Connection connection, final String occurrence,
				final INamedElement context) {
			return cfg.connection().matchesOccurrence(occurrence, context) && cfg.connection()
					.matches(MatchTarget.of(null,
							connection.getSource() != null ? connection.getSource().getTypeName() : null,
							connection.getComment(), null, connection));
		}

		private boolean matchesInstanceConstraint(final FBNetworkElement fbne, final String occurrence,
				final INamedElement context) {
			if (fbne instanceof UntypedSubApp) {
				return true;
			}
			if (fbne instanceof final TypedSubApp tsa) {
				return matchesInstanceBranches(cfg.typedSubapp(), tsa, occurrence, context);
			}
			if (fbne instanceof final FB fb) {
				return matchesFBInstance(fb, occurrence, context);
			}
			return false;
		}

		private boolean matchesFBInstance(final FB fb, final String occurrence, final INamedElement context) {
			final FBType type = fb.getType();
			// Specific before general — SubAppType extends CompositeFBType
			if (type instanceof SubAppType) {
				// SubAppType instances appear as TypedSubApp, not FB — should not reach here
				return false;
			}
			if (type instanceof CompositeFBType) {
				return matchesInstanceBranches(cfg.compositeFB(), fb, occurrence, context);
			}
			if (type instanceof SimpleFBType) {
				return matchesInstanceBranches(cfg.simpleFB(), fb, occurrence, context);
			}
			if (type instanceof BasicFBType) {
				return matchesInstanceBranches(cfg.basicFB(), fb, occurrence, context);
			}
			if (type instanceof ServiceInterfaceFBType) {
				return matchesInstanceBranches(cfg.serviceInterfaceFB(), fb, occurrence, context);
			}
			if (type instanceof FunctionFBType) {
				return matchesInstanceBranches(cfg.functionFB(), fb, occurrence, context);
			}
			return false;
		}

		private boolean matchesInstanceBranches(final InstanceConfig category, final FBNetworkElement elem,
				final String occurrence, final INamedElement context) {
			return !getMatchingInstanceBranches(category, instanceTarget(elem), occurrence, context).isEmpty();
		}

		/** The node of the instance category and the AllInstances node if they accept the instance. */
		private List<InstanceConfig> getMatchingInstanceBranches(final InstanceConfig category,
				final MatchTarget target, final String occurrence, final INamedElement context) {
			return cfg.instanceBranches(category).stream()
					.filter(branch -> branch.matchesOccurrence(occurrence, context) && branch.matches(target))
					.toList();
		}

		private static MatchTarget instanceTarget(final FBNetworkElement elem) {
			return MatchTarget.of(elem.getName(), elem.getTypeName(), elem.getComment(), null, elem);
		}

		private record OccurrenceContext(String kind, INamedElement context) {
		}

		private static OccurrenceContext resolveOccurrenceContext(final EObject obj) {
			EObject current = obj;
			while (current != null) {
				if (current instanceof final Application app) {
					return new OccurrenceContext(PlaceConfig.OCC_APPLICATION, app);
				}
				if (current instanceof final SubAppType subAppType) {
					return new OccurrenceContext(PlaceConfig.OCC_TYPED_SUBAPP, subAppType);
				}
				if (current instanceof final CompositeFBType compositeFBType) {
					return new OccurrenceContext(PlaceConfig.OCC_COMPOSITE_FB, compositeFBType);
				}
				current = current.eContainer();
			}
			return null;
		}
	}
}
