package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataToClipboardCommand;
import org.eclipse.nebula.widgets.nattable.edit.command.DeleteSelectionCommand;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.events.KeyEvent;

public class CutDataFromTableAction implements IKeyAction {

	@Override
	public void run(final NatTable natTable, final KeyEvent event) {
		natTable.doCommand(new CopyDataToClipboardCommand("\t", System.getProperty("line.separator"), //$NON-NLS-1$ //$NON-NLS-2$
				natTable.getConfigRegistry()));
		natTable.doCommand(new DeleteSelectionCommand(natTable.getConfigRegistry()));
	}
}
