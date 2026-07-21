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

import java.util.Set;

import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;

public record PlaceConfig( //@formatter:off
        TypeConfig simpleType,
        TypeConfig basicType,
        TypeConfig compositeType,
        TypeConfig serviceInterfaceType,
        TypeConfig subappType,
        TypeConfig structType,
        TypeConfig attributeType,
        InstanceConfig simpleFB,
        InstanceConfig basicFB,
        InstanceConfig compositeFB,
        InstanceConfig serviceInterfaceFB,
        InstanceConfig typedSubapp,
        InstanceConfig untypedSubapp,
        boolean ignoreLinkedLibraries) {
		// @formatter:on

	public boolean needsSystems() {
		return hasOccurrence(QueryModelHelper.OCC_APPLICATION);
	}

	public boolean needsCompositeFBTypes() {
		return hasOccurrence(QueryModelHelper.OCC_COMPOSITE_FB);
	}

	public boolean needsSubappTypesForInstances() {
		return hasOccurrence(QueryModelHelper.OCC_TYPED_SUBAPP);
	}

	public boolean anyInstanceSelected() {
		return simpleFB.selected() || basicFB.selected() || compositeFB.selected() || serviceInterfaceFB.selected()
				|| typedSubapp.selected() || untypedSubapp.selected();
	}

	private boolean hasOccurrence(final String occurrence) {
		return simpleFB.hasOccurrence(occurrence) || basicFB.hasOccurrence(occurrence)
				|| compositeFB.hasOccurrence(occurrence) || serviceInterfaceFB.hasOccurrence(occurrence)
				|| typedSubapp.hasOccurrence(occurrence) || untypedSubapp.hasOccurrence(occurrence);
	}

	public record TypeConfig(boolean selected, FilterRecord constraint, FilterRecord attributeConstraint,
			PinConfig pin) {
		public static final TypeConfig INACTIVE = new TypeConfig(false, FilterRecord.INACTIVE, FilterRecord.INACTIVE,
				PinConfig.INACTIVE);

		public boolean matches(final String name, final String type, final String comment) {
			return !constraint.isSelected() || constraint.matches(name, type, comment);
		}

		public boolean matchesAttribute(final ConfigurableObject confObj) {
			return !attributeConstraint.isSelected() || confObj.getAttributes().stream()
					.anyMatch(att -> attributeConstraint.matches(att.getName(), att.getTypeName(), att.getComment()));
		}
	}

	public record InstanceConfig(boolean selected, FilterRecord constraint, FilterRecord attributeConstraint,
			Set<String> occurrences, PinConfig pin) {
		public static final InstanceConfig INACTIVE = new InstanceConfig(false, FilterRecord.INACTIVE,
				FilterRecord.INACTIVE, Set.of(), PinConfig.INACTIVE);

		public boolean hasOccurrence(final String occurrence) {
			return selected && occurrences.contains(occurrence);
		}

		public boolean matches(final String name, final String type, final String comment) {
			return !constraint.isSelected() || constraint.matches(name, type, comment);
		}

		public boolean matchesAttribute(final ConfigurableObject confObj) {
			return !attributeConstraint.isSelected() || confObj.getAttributes().stream()
					.anyMatch(att -> attributeConstraint.matches(att.getName(), att.getTypeName(), att.getComment()));
		}
	}

	public record PinConfig(boolean active, FilterRecord constraint, FilterRecord attributeConstraint) {
		public static final PinConfig INACTIVE = new PinConfig(false, FilterRecord.INACTIVE, FilterRecord.INACTIVE);
		public static final PinConfig ACTIVE_UNFILTERED = new PinConfig(true, FilterRecord.INACTIVE,
				FilterRecord.INACTIVE);

		public boolean includePin(final String name, final String type, final String comment) {
			return !constraint.isSelected() || constraint.matches(name, type, comment);
		}

		public boolean matchesAttribute(final ConfigurableObject confObj) {
			return !attributeConstraint.isSelected() || confObj.getAttributes().stream()
					.anyMatch(att -> attributeConstraint.matches(att.getName(), att.getTypeName(), att.getComment()));
		}
	}
}