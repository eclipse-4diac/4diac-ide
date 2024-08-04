package org.eclipse.fordiac.ide.test.ui.swtbot;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;

public class SWTBot4diacNatTable extends SWTBotNatTable {

	public SWTBot4diacNatTable(final NatTable natTable) throws WidgetNotFoundException {
		super(natTable);
	}

	@Override
	public void setCellDataValueByPosition(final int row, final int column, final String text) {
		assertIsLegalCell(row, column);
		Assert.isNotNull(text);
		waitForEnabled();
		Assert.isTrue(!hasStyle(widget, SWT.READ_ONLY));
		asyncExec(() -> {
			final ICellEditor editor = widget.getActiveCellEditor();
			editor.setEditorValue(text);
		});
		notify(SWT.Modify);
	}

}
