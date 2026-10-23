/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public record PlaceConfig( //@formatter:off
        TypeConfig simpleType,
        TypeConfig basicType,
        TypeConfig compositeType,
        TypeConfig serviceInterfaceType,
        TypeConfig functionType,
        TypeConfig subappType,
        TypeConfig structType,
        TypeConfig attributeType,
        InstanceConfig simpleFB,
        InstanceConfig basicFB,
        InstanceConfig compositeFB,
        InstanceConfig serviceInterfaceFB,
        InstanceConfig functionFB,
        InstanceConfig typedSubapp,
        InstanceConfig untypedSubapp,
        ConnectionConfig connection,
        boolean ignoreLinkedLibraries,
        boolean subappHierarchyScope,
        List<String> subappHierarchy) {
		// @formatter:on

	public static final String OCC_APPLICATION = "Application"; //$NON-NLS-1$
	public static final String OCC_COMPOSITE_FB = "CompositeFB"; //$NON-NLS-1$
	public static final String OCC_TYPED_SUBAPP = "TypedSubapp"; //$NON-NLS-1$

	public boolean needsSystems() {
		return hasOccurrence(OCC_APPLICATION);
	}

	public boolean needsCompositeFBTypes() {
		return hasOccurrence(OCC_COMPOSITE_FB);
	}

	public boolean needsSubappTypesForInstances() {
		return hasOccurrence(OCC_TYPED_SUBAPP);
	}

	public boolean anyInstanceSelected() {
		return simpleFB.selected() || basicFB.selected() || compositeFB.selected() || serviceInterfaceFB.selected()
				|| functionFB.selected() || typedSubapp.selected() || untypedSubapp.selected();
	}

	private boolean hasOccurrence(final String occurrence) {
		return simpleFB.hasOccurrence(occurrence) || basicFB.hasOccurrence(occurrence)
				|| compositeFB.hasOccurrence(occurrence) || serviceInterfaceFB.hasOccurrence(occurrence)
				|| functionFB.hasOccurrence(occurrence) || typedSubapp.hasOccurrence(occurrence)
				|| untypedSubapp.hasOccurrence(occurrence) || connection.hasOccurrence(occurrence);
	}

	public record TypeConfig(boolean selected, FilterRecord constraint, PinConfig pin) {
		public static final TypeConfig INACTIVE = new TypeConfig(false, FilterRecord.INACTIVE, PinConfig.INACTIVE);

		public boolean matches(final MatchTarget target) {
			return constraint.accepts(target);
		}
	}

	public record OccurrenceConfig(boolean selected, FilterRecord constraint) {
		public static final OccurrenceConfig INACTIVE = new OccurrenceConfig(false, FilterRecord.INACTIVE);

		public boolean matchesContext(final INamedElement context) {
			if (!selected) {
				return false;
			}
			final ConfigurableObject owner = context instanceof final ConfigurableObject co ? co : null;
			return constraint.accepts(MatchTarget.of(context.getName(), null, context.getComment(), null, owner));
		}
	}

	public record OccurrencesConfig(OccurrenceConfig application, OccurrenceConfig compositeFB,
			OccurrenceConfig typedSubapp) {
		public static final OccurrencesConfig INACTIVE = new OccurrencesConfig(OccurrenceConfig.INACTIVE,
				OccurrenceConfig.INACTIVE, OccurrenceConfig.INACTIVE);

		private boolean noRestriction() {
			return !application.selected() && !compositeFB.selected() && !typedSubapp.selected();
		}

		public boolean has(final String kind) {
			return noRestriction() || occurrenceFor(kind).selected();
		}

		public boolean matches(final String kind, final INamedElement context) {
			return noRestriction() || occurrenceFor(kind).matchesContext(context);
		}

		private OccurrenceConfig occurrenceFor(final String kind) {
			return switch (kind) {
			case OCC_APPLICATION -> application;
			case OCC_COMPOSITE_FB -> compositeFB;
			case OCC_TYPED_SUBAPP -> typedSubapp;
			case null, default -> OccurrenceConfig.INACTIVE;
			};
		}
	}

	public interface NetworkElementConfig {
		boolean selected();
		FilterRecord constraint();
		OccurrencesConfig occurrences();

		default boolean hasOccurrence(final String kind) {
			return selected() && occurrences().has(kind);
		}

		default boolean matchesOccurrence(final String kind, final INamedElement context) {
			return selected() && occurrences().matches(kind, context);
		}

		default boolean matches(final MatchTarget target) {
			return constraint().accepts(target);
		}
	}

	public record InstanceConfig(boolean selected, FilterRecord constraint, OccurrencesConfig occurrences,
			PinConfig pin) implements NetworkElementConfig {
		public static final InstanceConfig INACTIVE = new InstanceConfig(false, FilterRecord.INACTIVE,
				OccurrencesConfig.INACTIVE, PinConfig.INACTIVE);
	}

	public record ConnectionConfig(boolean selected, FilterRecord constraint, OccurrencesConfig occurrences)
			implements NetworkElementConfig {
		public static final ConnectionConfig INACTIVE = new ConnectionConfig(false, FilterRecord.INACTIVE,
				OccurrencesConfig.INACTIVE);
	}

	public record PinConfig(boolean active, FilterRecord constraint) {
		public static final PinConfig INACTIVE = new PinConfig(false, FilterRecord.INACTIVE);
		public static final PinConfig ACTIVE_UNFILTERED = new PinConfig(true, FilterRecord.INACTIVE);

		public boolean includePin(final MatchTarget target) {
			return constraint.accepts(target);
		}
	}
}
