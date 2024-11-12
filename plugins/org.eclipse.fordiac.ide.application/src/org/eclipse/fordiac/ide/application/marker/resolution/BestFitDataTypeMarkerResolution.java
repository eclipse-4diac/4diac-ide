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
 *   Patrick Aigner
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Image;

public class BestFitDataTypeMarkerResolution extends AbstractCommandMarkerResolution<ITypedElement> {

	private final DataType selectedType;

	public BestFitDataTypeMarkerResolution(final IMarker marker, final DataType type) {
		super(marker, ITypedElement.class);
		selectedType = type;
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		return true;
	}

	@Override
	protected Command createCommand(final ITypedElement element, final IProgressMonitor monitor) throws CoreException {
		return switch (element) {
		case final IInterfaceElement interfaceElement ->
			ChangeDataTypeCommand.forDataType(interfaceElement, selectedType);
		case final StructManipulator fb when selectedType instanceof StructuredType ->
			new ChangeStructCommand(fb, selectedType);
		case final ConfigurableFB fb -> new ConfigureFBCommand(fb, selectedType);
		default -> null;
		};
	}

	@Override
	protected boolean isApplicable(final IMarker other) {
		final int code = FordiacErrorMarker.getCode(other);
		return (code == LibraryElementValidator.ITYPED_ELEMENT__VALIDATE_TYPE
				|| code == LibraryElementValidator.CONFIGURABLE_FB__VALIDATE_DATA_TYPE)
				&& Arrays.equals(FordiacErrorMarker.getData(other), FordiacErrorMarker.getData(getMarker()));
	}

	@Override
	public String getDescription() {
		return MessageFormat.format(FordiacMessages.Repair_Dialog_BestFitDataType,
				selectedType.getTypeEntry().getFullTypeName());
	}

	@Override
	public String getLabel() {
		return MessageFormat.format(FordiacMessages.Repair_Dialog_BestFitDataType,
				selectedType.getTypeEntry().getFullTypeName());
	}

	@Override
	public Image getImage() {
		return null;
	}

	public static Stream<BestFitDataTypeMarkerResolution> createResolutions(final IMarker marker) {
		final String typeName = FordiacErrorMarker.getData(marker)[0];
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(marker.getResource().getProject());

		return typeLibrary.findUnqualified(typeName).stream().map(TypeEntry::getType).filter(DataType.class::isInstance)
				.map(DataType.class::cast).map(type -> new BestFitDataTypeMarkerResolution(marker, type));
	}
}
