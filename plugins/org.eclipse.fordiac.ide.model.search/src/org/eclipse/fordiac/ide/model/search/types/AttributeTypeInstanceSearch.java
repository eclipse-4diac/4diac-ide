/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.search.types;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.search.LiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;

public class AttributeTypeInstanceSearch extends IEC61499ElementSearch {

	public AttributeTypeInstanceSearch(final AttributeTypeEntry attEntry) {
		super(new LiveSearchContext(attEntry.getTypeLibrary()), createSearchFilter(attEntry),
				new AttributeTypeInstanceSearchChildrenProvider());
	}

	public AttributeTypeInstanceSearch(final LibraryElement typeEditable, final AttributeTypeEntry attEntry) {
		super(new LibraryElementSearchContext(typeEditable), createSearchFilter(attEntry),
				new AttributeTypeInstanceSearchChildrenProvider());
	}

	private static IEC61499SearchFilter createSearchFilter(final AttributeTypeEntry attEntry) {
		return searchCandidate -> {
			if (searchCandidate instanceof final ConfigurableObject confObj) {
				final Attribute attribute = confObj.getAttribute(attEntry.getTypeName());
				if (attribute != null) {
					final AttributeDeclaration attributeDecl = attribute.getAttributeDeclaration();
					if (attributeDecl != null) {
						return attributeDecl.getTypeEntry() == attEntry;
					}
				}
			}
			return false;
		};
	}

	private static final class AttributeTypeInstanceSearchChildrenProvider implements ISearchChildrenProvider {
		@Override
		public boolean hasChildren(final EObject obj) {
			//@formatter:off
			return 	obj instanceof AutomationSystem ||
					obj instanceof Application ||
					obj instanceof FBType ||
					obj instanceof FBNetworkElement ||
					obj instanceof StructuredType ||
					obj instanceof AttributeDeclaration ||
					obj instanceof DeviceType ||
					obj instanceof ResourceType ||
					obj instanceof SegmentType ||
					obj instanceof Device ||
					obj instanceof Resource ||
					obj instanceof Segment ||
					obj instanceof GlobalConstants;
			//@formatter:on
		}

		@Override
		public Stream<? extends EObject> getChildren(final EObject obj) {
			return switch (obj) {
			case final AutomationSystem system -> {
				Stream<? extends EObject> stream = system.getApplication().stream();
				stream = Stream.concat(stream, system.getSystemConfiguration().getDevices().stream());
				stream = Stream.concat(stream, system.getSystemConfiguration().getLinks().stream());
				stream = Stream.concat(stream, system.getSystemConfiguration().getSegments().stream());
				yield stream;
			}
			case final Application application -> {
				Stream<? extends EObject> stream = application.getFBNetwork().getNetworkElements().stream();
				stream = Stream.concat(stream, application.getFBNetwork().getAdapterConnections().stream());
				stream = Stream.concat(stream, application.getFBNetwork().getDataConnections().stream());
				stream = Stream.concat(stream, application.getFBNetwork().getEventConnections().stream());
				yield stream;
			}
			case final FBType fbType -> SearchChildrenProviderHelper.getFBTypeChildren(fbType);
			case final UntypedSubApp untypedSubapp ->
				SearchChildrenProviderHelper.getUntypedSubappChildren(untypedSubapp);
			case final FBNetworkElement fbnElement ->
				SearchChildrenProviderHelper.getInterfaceListChildren(fbnElement.getInterface());
			case final StructuredType structType -> SearchChildrenProviderHelper.getStructChildren(structType);
			case final AttributeDeclaration attrdecl -> SearchChildrenProviderHelper.getAttributeDeclChildren(attrdecl);
			case final DeviceType deviceType -> deviceType.getVarDeclaration().stream();
			case final ResourceType resourceType -> resourceType.getVarDeclaration().stream();
			case final SegmentType segmentType -> segmentType.getVarDeclaration().stream();
			case final Device device -> {
				Stream<? extends EObject> stream = device.getVarDeclarations().stream();
				stream = Stream.concat(stream, device.getResource().stream());
				yield stream;
			}
			case final Resource resource -> resource.getVarDeclarations().stream();
			case final Segment segment -> segment.getVarDeclarations().stream();
			case final GlobalConstants globalConstants -> globalConstants.getConstants().stream();
			default -> null;
			};
		}
	}
}
