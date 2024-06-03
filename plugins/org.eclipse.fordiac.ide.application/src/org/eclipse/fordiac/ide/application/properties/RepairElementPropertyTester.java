package org.eclipse.fordiac.ide.application.properties;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.handlers.RepairCommandHandler;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.gef.EditPart;
import org.eclipse.ui.views.markers.MarkerItem;

public class RepairElementPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		return isSupported(receiver);
	}

	protected boolean isSupported(final Object element) {

		// the error marker from the problem view are always coming in a list
		if (element instanceof final List markerList) {
			return isMarkerFromProblemsView(markerList);
		}

		if (element instanceof final EditPart editPart) {
			return isErrorMarkerFromEditor(editPart.getModel());
		}

		return false;
	}

	private boolean isErrorMarkerFromEditor(final Object model) {
		if(model instanceof ErrorMarkerDataType){
			
		}
		return false;
	}

	private static boolean isMarkerFromProblemsView(final List<?> markerList) {
		for (final var marker : markerList) {
			if (!(marker instanceof final MarkerItem markerItem)) {
				return false;
			}
			final EObject target = RepairCommandHandler.getEObjectFromMarkerItem(markerItem);
			if (target == null) {
				return false;
			}
		}
		return true;
	}

}
