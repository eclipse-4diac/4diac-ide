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

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.InstanceConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.PinConfig;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.ISearchContext;
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

	private SearchHelper() {
	}

	public static ISearchContext createSearchContext(final IProject project, final PlaceConfig cfg) {
		return new PlaceAwareSearchContext(project, cfg);
	}

	public static ISearchChildrenProvider createChildrenSearchProvider(final PlaceConfig cfg) {
		return new PlaceAwareChildrenProvider(cfg);
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
			if (cfg.simpleType().selected()) {
				s = Stream.concat(s, getSimpleTypes(cfg));
			}
			if (cfg.basicType().selected()) {
				s = Stream.concat(s, getBasicTypes(cfg));
			}
			if (cfg.compositeType().selected()) {
				s = Stream.concat(s, getCompositeTypes(cfg));
			}
			if (cfg.serviceInterfaceType().selected()) {
				s = Stream.concat(s, getServiceInterfaceTypes(cfg));
			}
			if (cfg.subappType().selected()) {
				s = Stream.concat(s, getSubappTypes(cfg));
			}
			if (cfg.structType().selected()) {
				s = Stream.concat(s, getStructTypes(cfg));
			}
			if (cfg.attributeType().selected()) {
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

		private Stream<? extends TypeEntry> getSimpleTypes(final PlaceConfig cfg) {
			return getTypelib().getFbTypes().filter(entry -> entry.getType() instanceof SimpleFBType).filter(
					entry -> cfg.simpleType().matches(entry.getFullTypeName(), entry.getTypeName(), entry.getComment())
							&& cfg.simpleType().matchesAttribute(entry.getType()));
		}

		private Stream<? extends TypeEntry> getBasicTypes(final PlaceConfig cfg) {
			return getTypelib().getFbTypes().filter(entry -> entry.getType() instanceof BasicFBType).filter(
					entry -> cfg.basicType().matches(entry.getFullTypeName(), entry.getTypeName(), entry.getComment())
							&& cfg.basicType().matchesAttribute(entry.getType()));
		}

		private Stream<? extends TypeEntry> getCompositeTypes(final PlaceConfig cfg) {
			return getTypelib().getFbTypes()
					.filter(entry -> entry.getType() instanceof CompositeFBType
							&& !(entry.getType() instanceof SubAppType) && cfg.compositeType()
									.matches(entry.getFullTypeName(), entry.getTypeName(), entry.getComment())
							&& cfg.compositeType().matchesAttribute(entry.getType()));
		}

		private Stream<? extends TypeEntry> getServiceInterfaceTypes(final PlaceConfig cfg) {
			return getTypelib().getFbTypes().filter(entry -> entry.getType() instanceof ServiceInterfaceFBType)
					.filter(entry -> cfg.serviceInterfaceType().matches(entry.getFullTypeName(), entry.getTypeName(),
							entry.getComment()) && cfg.serviceInterfaceType().matchesAttribute(entry.getType()));
		}

		private Stream<? extends TypeEntry> getSubappTypes(final PlaceConfig cfg) {
			return getTypelib().getSubAppTypes().filter(
					entry -> cfg.subappType().matches(entry.getFullTypeName(), entry.getTypeName(), entry.getComment())
							&& cfg.subappType().matchesAttribute(entry.getType()));
		}

		private Stream<? extends TypeEntry> getStructTypes(final PlaceConfig cfg) {
			return getTypelib().getDataTypeLibrary().getDerivedDataTypes().filter(
					entry -> cfg.structType().matches(entry.getFullTypeName(), entry.getTypeName(), entry.getComment())
							&& cfg.structType().matchesAttribute(entry.getType()));
		}

		private Stream<? extends TypeEntry> getAttributeTypes(final PlaceConfig cfg) {
			return getTypelib().getAttributeTypes().filter(entry -> cfg.attributeType().matches(entry.getFullTypeName(),
					entry.getTypeName(), entry.getComment()) && cfg.attributeType().matchesAttribute(entry.getType()));
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
			if (cfg.structType().selected() && obj instanceof StructuredType) {
				return true;
			}
			if (cfg.attributeType().selected() && obj instanceof AttributeDeclaration) {
				return true;
			}

			// Instance-level: occurrence-driven traversal
			if (obj instanceof AutomationSystem) {
				return cfg.needsSystems();
			}
			if (obj instanceof Application) {
				return cfg.anyInstanceSelected();
			}
			if (obj instanceof UntypedSubApp) {
				return true;
			}

			// Shared: elements inside networks need interface expansion
			if (cfg.anyInstanceSelected() && obj instanceof FBNetworkElement) {
				return true;
			}

			return obj instanceof IInterfaceElement;
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
			final InstanceConfig instanceConfig = resolveInstancePinConfig(elem);
			if (!instanceConfig.matchesAttribute(elem)) {
				return Stream.empty();
			}

			Stream<? extends EObject> children = elem.getAttributes().stream();

			final PinConfig pinCfg = instanceConfig.pin();
			if (pinCfg.active()) { // should only be there for attribute
				children = Stream.concat(children, getFilteredInterfaceChildren(elem.getInterface(), pinCfg));
			}

			return children;
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
			}
			return InstanceConfig.INACTIVE;
		}

		private static Stream<? extends EObject> getFilteredInterfaceChildren(final InterfaceList iface,
				final PinConfig pinCfg) {
			Stream<? extends EObject> children = SearchChildrenProviderHelper.getInterfaceListChildren(iface);
			// Apply the PIN's constraint to filter which pins to enter
			// constraint
			children = children.filter(child -> {
				if (child instanceof final ITypedElement typed) {
					return pinCfg.includePin(typed.getName(), typed.getTypeName(), typed.getComment());
				}
				return true;
			});
			// attributeConstraint
			children = children.filter(child -> {
				if (child instanceof final ConfigurableObject confObject) {
					return pinCfg.matchesAttribute(confObject);
				}
				return true;
			});

			return children;
		}

		private Stream<? extends EObject> getFBTypeChildren(final FBType fbType) {
			Stream<? extends EObject> children = Stream.empty();

			if (isTypeSelected(fbType)) {
				final PinConfig pinCfg = getTypePinConfig(fbType);
				if (pinCfg.active()) {
					children = getFilteredInterfaceChildren(fbType.getInterfaceList(), pinCfg);
				}
				children = Stream.concat(children, fbType.getAttributes().stream());
				if (fbType instanceof final BaseFBType baseFBType) {
					children = Stream.concat(children, baseFBType.getInternalVars().stream());
					children = Stream.concat(children, baseFBType.getInternalConstVars().stream());
				}
			}

			// Instance-level: traverse FBNetwork if occurrence demands it
			// SubAppType before CompositeFBType (SubAppType extends CompositeFBType)
			if (fbType instanceof final SubAppType subappType && cfg.needsSubappTypesForInstances()) {
				children = Stream.concat(children,
						getFilteredNetworkChildren(subappType.getFBNetwork(), QueryModelHelper.OCC_TYPED_SUBAPP));
			} else if (fbType instanceof final CompositeFBType composite && cfg.needsCompositeFBTypes()) {
				children = Stream.concat(children,
						getFilteredNetworkChildren(composite.getFBNetwork(), QueryModelHelper.OCC_COMPOSITE_FB));
			}

			return children;
		}

		private boolean isTypeSelected(final FBType fbType) {
			// Specific before general — SubAppType extends CompositeFBType
			if (fbType instanceof SubAppType) {
				return cfg.subappType().selected();
			}
			if (fbType instanceof CompositeFBType) {
				return cfg.compositeType().selected();
			}
			if (fbType instanceof SimpleFBType) {
				return cfg.simpleType().selected();
			}
			if (fbType instanceof BasicFBType) {
				return cfg.basicType().selected();
			}
			if (fbType instanceof ServiceInterfaceFBType) {
				return cfg.serviceInterfaceType().selected();
			}
			return false;
		}

		private Stream<? extends EObject> getApplicationChildren(final Application application) {
			Stream<? extends EObject> stream = getFilteredNetworkChildren(application.getFBNetwork(),
					QueryModelHelper.OCC_APPLICATION);

			// Always include application-level attributes
			stream = Stream.concat(stream, application.getAttributes().stream());

			return stream;
		}

		private Stream<? extends EObject> getAttributeDeclChildren(final AttributeDeclaration attrdecl) {
			// Attributes on the declaration — always included
			Stream<? extends EObject> children = attrdecl.getAttributes().stream();

			// Type children (struct members, base type) — gated by AttributeType's PIN
			// config
			final PinConfig pinCfg = cfg.attributeType().pin();
			if (pinCfg.active()) {
				Stream<? extends EObject> typeChildren = Stream.empty();
				if (attrdecl.getType() instanceof final StructuredType structType) {
					typeChildren = SearchChildrenProviderHelper.getStructChildren(structType);
				} else if (attrdecl.getType() instanceof final DirectlyDerivedType directType) {
					typeChildren = Stream.of(directType.getBaseType());
				}
				typeChildren = typeChildren.filter(child -> {
					if (child instanceof final ITypedElement typed) {
						return pinCfg.includePin(typed.getName(), typed.getTypeName(), typed.getComment());
					}
					return true;
				});
				typeChildren = typeChildren.filter(child -> {
					if (child instanceof final ConfigurableObject confObject) {
						return pinCfg.matchesAttribute(confObject);
					}
					return true;
				});

				children = Stream.concat(children, typeChildren);
			}

			return children;
		}

		private Stream<? extends EObject> getStructTypeChildren(final StructuredType structType) {
			// Attributes on the struct — always included
			Stream<? extends EObject> children = structType.getAttributes().stream();

			// Member variables — gated and filtered by StructType's PIN config
			final PinConfig pinCfg = cfg.structType().pin();
			if (pinCfg.active()) {
				Stream<? extends EObject> members = SearchChildrenProviderHelper.getStructChildren(structType);
				members = members.filter(child -> {
					if (child instanceof final ITypedElement typed) {
						return pinCfg.includePin(typed.getName(), typed.getTypeName(), typed.getComment());
					}
					return true;
				});
				members = members.filter(child -> {
					if (child instanceof final ConfigurableObject confObject) {
						return pinCfg.matchesAttribute(confObject);
					}
					return true;
				});
				children = Stream.concat(children, members);
			}

			return children;
		}

		private PinConfig getTypePinConfig(final FBType fbType) {
			if (fbType instanceof SubAppType) {
				return cfg.subappType().pin();
			}
			if (fbType instanceof CompositeFBType) {
				return cfg.compositeType().pin();
			}
			if (fbType instanceof SimpleFBType) {
				return cfg.simpleType().pin();
			}
			if (fbType instanceof BasicFBType) {
				return cfg.basicType().pin();
			}
			if (fbType instanceof ServiceInterfaceFBType) {
				return cfg.serviceInterfaceType().pin();
			}
			return PinConfig.INACTIVE;
		}

		private Stream<? extends EObject> getUntypedSubappChildren(final UntypedSubApp untypedSubapp) {
			final String occurrence = resolveOccurrenceContext(untypedSubapp);
			Stream<? extends EObject> stream = Stream.empty();

			if (cfg.untypedSubapp().hasOccurrence(occurrence) && cfg.untypedSubapp().matches(untypedSubapp.getName(),
					untypedSubapp.getTypeName(), untypedSubapp.getComment())
					&& cfg.untypedSubapp().matchesAttribute(untypedSubapp)) {
				final PinConfig pinCfg = resolveInstancePinConfig(untypedSubapp).pin();
				if (pinCfg.active()) {
					stream = Stream.concat(stream, getFilteredInterfaceChildren(untypedSubapp.getInterface(), pinCfg));
				}
				stream = Stream.concat(stream, untypedSubapp.getAttributes().stream());
			}
			if (occurrence != null) {
				stream = Stream.concat(stream,
						getFilteredNetworkChildren(untypedSubapp.getSubAppNetwork(), occurrence));
			}

			return stream;
		}

		private Stream<? extends EObject> getFilteredNetworkChildren(final FBNetwork network, final String occurrence) {
			Stream<? extends EObject> stream = Stream.empty();

			// Network elements filtered by occurrence-aware instance matching
			stream = Stream.concat(stream,
					network.getNetworkElements().stream().filter(fbne -> matchesInstanceConstraint(fbne, occurrence)));

			// Connections are always included
			stream = Stream.concat(stream, network.getAdapterConnections().stream());
			stream = Stream.concat(stream, network.getDataConnections().stream());
			stream = Stream.concat(stream, network.getEventConnections().stream());

			return stream;
		}

		private boolean matchesInstanceConstraint(final FBNetworkElement fbne, final String occurrence) {
			if (fbne instanceof UntypedSubApp) {
				return true;
			}
			if (fbne instanceof final TypedSubApp tsa) {
				return cfg.typedSubapp().hasOccurrence(occurrence)
						&& cfg.typedSubapp().matches(tsa.getName(), tsa.getTypeName(), tsa.getComment());
			}
			if (fbne instanceof final FB fb) {
				return matchesFBInstance(fb, occurrence);
			}
			return false;
		}

		private boolean matchesFBInstance(final FB fb, final String occurrence) {
			final FBType type = fb.getType();
			// Specific before general — SubAppType extends CompositeFBType
			if (type instanceof SubAppType) {
				// SubAppType instances appear as TypedSubApp, not FB — should not reach here
				return false;
			}
			if (type instanceof CompositeFBType) {
				return cfg.compositeFB().hasOccurrence(occurrence)
						&& cfg.compositeFB().matches(fb.getName(), fb.getTypeName(), fb.getComment());
			}
			if (type instanceof SimpleFBType) {
				return cfg.simpleFB().hasOccurrence(occurrence)
						&& cfg.simpleFB().matches(fb.getName(), fb.getTypeName(), fb.getComment());
			}
			if (type instanceof BasicFBType) {
				return cfg.basicFB().hasOccurrence(occurrence)
						&& cfg.basicFB().matches(fb.getName(), fb.getTypeName(), fb.getComment());
			}
			if (type instanceof ServiceInterfaceFBType) {
				return cfg.serviceInterfaceFB().hasOccurrence(occurrence)
						&& cfg.serviceInterfaceFB().matches(fb.getName(), fb.getTypeName(), fb.getComment());
			}
			return false;
		}

		private static String resolveOccurrenceContext(final EObject obj) {
			EObject current = obj;
			while (current != null) {
				if (current instanceof Application) {
					return QueryModelHelper.OCC_APPLICATION;
				}
				if (current instanceof SubAppType) {
					return QueryModelHelper.OCC_TYPED_SUBAPP;
				}
				if (current instanceof CompositeFBType) {
					return QueryModelHelper.OCC_COMPOSITE_FB;
				}
				current = current.eContainer();
			}
			return null;
		}
	}
}