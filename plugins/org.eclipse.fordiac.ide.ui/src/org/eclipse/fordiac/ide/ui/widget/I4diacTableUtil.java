package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.jface.viewers.TableViewer;

public interface I4diacTableUtil {

	TableViewer getViewer();

	Object getEntry(int index);

	void addEntry(Object entry, int index);

	Object removeEntry(int index);

}
