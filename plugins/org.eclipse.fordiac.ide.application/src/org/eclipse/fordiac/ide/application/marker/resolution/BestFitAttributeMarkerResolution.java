/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeDeclarationCommand;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Image;

public class BestFitAttributeMarkerResolution extends AbstractCommandMarkerResolution<Attribute> {

	private final AttributeTypeEntry selectedAttribute;

	protected BestFitAttributeMarkerResolution(final IMarker marker, final AttributeTypeEntry selectedAttribute) {
		super(marker, Attribute.class);
		this.selectedAttribute = selectedAttribute;
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		return true;
	}

	@Override
	protected Command createCommand(final Attribute element, final IProgressMonitor monitor) throws CoreException {
		return ChangeAttributeDeclarationCommand.forName(element, selectedAttribute.getFullTypeName());

	}

	public static Stream<BestFitAttributeMarkerResolution> createResolutions(final IMarker marker) {
		String attrName = null;
		if (FordiacErrorMarker.getTarget(marker) instanceof final Attribute attr) {
			attrName = PackageNameHelper.extractPlainTypeName(attr.getName());
		}
		if (null == attrName) {
			return Stream.empty();
		}
		final String name = attrName;
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(marker.getResource().getProject());
		return typeLibrary.getAttributeTypes().stream().filter(type -> type.getTypeName().equals(name))
				.map(typeEntry -> new BestFitAttributeMarkerResolution(marker, typeEntry));
	}

	@Override
	public String getDescription() {
		return MessageFormat.format(FordiacMessages.Repair_Dialog_BestFitAttrType, selectedAttribute.getFullTypeName());
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getLabel() {
		return MessageFormat.format(FordiacMessages.Repair_Dialog_BestFitAttrType, selectedAttribute.getFullTypeName());
	}

}