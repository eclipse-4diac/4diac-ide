package org.eclipse.fordiac.ide.systemconfiguration.segment.providers;

import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TsnWindowLabelProvider extends LabelProvider implements ITableLabelProvider {
	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof TsnWindow) {
			final TsnWindow window = (TsnWindow) element;
			switch (columnIndex) {
			case 0:
				if (!(window.eContainer() instanceof TsnConfiguration)) {
					break;
				}
				return "TsnWindow" + ((TsnConfiguration) window.eContainer()).getWindows().indexOf(window);
			case 1:
				return "TIME#" + String.valueOf(window.getDuration()) + "ms";
			case 2:
				return "TIME"; ////$NON-NLS-1$
			case 3:
				return ((TsnWindow) element).getComment() != null ? ((TsnWindow) element).getComment() : "empty"; //$NON-NLS-1$
			default:
				break;
			}
		}
		return element.toString();
	}
}