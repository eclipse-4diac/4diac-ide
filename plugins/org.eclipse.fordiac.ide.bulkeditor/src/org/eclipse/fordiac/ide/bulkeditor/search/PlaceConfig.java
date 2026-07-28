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

import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

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

		public boolean matches(final String name, final String comment) {
			return !constraint.isSelected() || constraint.matches(name, null, comment, null);
		}

		public boolean matchesAttribute(final ConfigurableObject confObj) {
			return !attributeConstraint.isSelected()
					|| confObj.getAttributes().stream().anyMatch(att -> attributeConstraint.matches(att.getName(),
							att.getTypeName(), att.getComment(), att.getValue()));
		}
	}

	public record OccurrenceConfig(boolean selected, FilterRecord constraint, FilterRecord attributeConstraint) {
		public static final OccurrenceConfig INACTIVE = new OccurrenceConfig(false, FilterRecord.INACTIVE,
				FilterRecord.INACTIVE);

		public boolean matchesContext(final INamedElement context) {
			if (!selected) {
				return false;
			}
			return !constraint.isSelected() || constraint.matches(context.getName(), null, context.getComment(), null);
		}

		public boolean matchesContextAttribute(final INamedElement context) {
			if (!selected || !attributeConstraint.isSelected()) {
				return true;
			}
			if (context instanceof final ConfigurableObject confObj) {
				return confObj.getAttributes().stream().anyMatch(att -> attributeConstraint.matches(att.getName(),
						att.getTypeName(), att.getComment(), att.getValue()));
			}
			return true;
		}
	}

	public record InstanceConfig(boolean selected, FilterRecord constraint, FilterRecord attributeConstraint,
			OccurrenceConfig application, OccurrenceConfig compositeFBOcc, OccurrenceConfig typedSubappOcc,
			PinConfig pin) {
		public static final InstanceConfig INACTIVE = new InstanceConfig(false, FilterRecord.INACTIVE,
				FilterRecord.INACTIVE, OccurrenceConfig.INACTIVE, OccurrenceConfig.INACTIVE, OccurrenceConfig.INACTIVE,
				PinConfig.INACTIVE);

		private boolean noOccurrenceRestriction() {
			return !application.selected() && !compositeFBOcc.selected() && !typedSubappOcc.selected();
		}

		public boolean hasOccurrence(final String kind) {
			if (!selected) {
				return false;
			}
			return noOccurrenceRestriction() || occurrenceFor(kind).selected();
		}

		public boolean matchesOccurrence(final String kind, final INamedElement context) {
			if (!selected) {
				return false;
			}
			if (noOccurrenceRestriction()) {
				return true;
			}
			final OccurrenceConfig occ = occurrenceFor(kind);
			return occ.matchesContext(context) && occ.matchesContextAttribute(context);
		}

		private OccurrenceConfig occurrenceFor(final String kind) {
			return switch (kind) {
			case OCC_APPLICATION -> application;
			case OCC_COMPOSITE_FB -> compositeFBOcc;
			case OCC_TYPED_SUBAPP -> typedSubappOcc;
			case null, default -> OccurrenceConfig.INACTIVE;
			};
		}

		public boolean matches(final String name, final String type, final String comment) {
			return !constraint.isSelected() || constraint.matches(name, type, comment, null);
		}

		public boolean matchesAttribute(final ConfigurableObject confObj) {
			return !attributeConstraint.isSelected()
					|| confObj.getAttributes().stream().anyMatch(att -> attributeConstraint.matches(att.getName(),
							att.getTypeName(), att.getComment(), att.getValue()));
		}
	}

	public record PinConfig(boolean active, FilterRecord constraint, FilterRecord attributeConstraint) {
		public static final PinConfig INACTIVE = new PinConfig(false, FilterRecord.INACTIVE, FilterRecord.INACTIVE);
		public static final PinConfig ACTIVE_UNFILTERED = new PinConfig(true, FilterRecord.INACTIVE,
				FilterRecord.INACTIVE);

		public boolean includePin(final String name, final String type, final String comment, final String value) {
			return !constraint.isSelected() || constraint.matches(name, type, comment, value);
		}

		public boolean matchesAttribute(final ConfigurableObject confObj) {
			return !attributeConstraint.isSelected()
					|| confObj.getAttributes().stream().anyMatch(att -> attributeConstraint.matches(att.getName(),
							att.getTypeName(), att.getComment(), att.getValue()));
		}
	}
}