/*******************************************************************************
 * Copyright (c) 2019, 2024 Profactor GbmH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl - initial API and implementation and/or
 *   								  initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.print;

import java.util.regex.Pattern;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PrintFigureOperation;
import org.eclipse.draw2d.PrinterGraphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A print preview Dialog where the user can specify some print options. Setting
 * / changing the properties of the printer lead to recalculating the "print"
 * area. Therefore, the preview should represent the output of the printer.
 */
public class PrintPreview extends Dialog {

	private static final String ONLY_DIGIT_REGEX = "^\\d*$"; //$NON-NLS-1$
	private static final Pattern ONLY_DIGIT_PATTERN = Pattern.compile(ONLY_DIGIT_REGEX, Pattern.MULTILINE);

	/**
	 * The current page shown in the print preview. Always starting with 1.
	 */
	private int currentPage = 1;

	/**
	 * The number of pages that would be printed with the current settings.
	 */
	private int numberOfPages = 1;

	private Label numberOfPagesLabel;
	private Text currentPageText;

	private Button printBorder;

	private Combo scaleSelection;
	private Combo combo;

	private PrintMargin margin;

	private Printer printer;

	private Canvas canvas;

	private boolean blockCurrentPageUpdate = false;

	private final String printName;
	private final IFigure figure;

	/**
	 * Instantiates a new prints the preview.
	 *
	 * @param shell     the shell
	 * @param viewer    the viewer
	 * @param printName the print name
	 */
	public PrintPreview(final Shell shell, final GraphicalViewer viewer, final String printName) {
		super(shell);
		this.printName = printName;
		final LayerManager lm = (LayerManager) viewer.getEditPartForModel(LayerManager.ID);
		figure = lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		newShell.setText(Messages.PrintPreview_LABEL_PrintPreview);
		super.configureShell(newShell);
	}

	@Override
	protected int getShellStyle() {
		return SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL;
	}

	/**
	 * Adds some GUI elements for defining some print options to the specified
	 * composite
	 *
	 * @param composite The container of the elements
	 */
	private void createOptionsGUI(final Composite parent) {
		final GridLayout layout = new GridLayout(6, false);
		layout.marginHeight = 0;
		parent.setLayout(layout);

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Scale);
		scaleSelection = new Combo(parent, SWT.READ_ONLY);
		scaleSelection.add(Messages.PrintPreview_LABEL_Tile);
		scaleSelection.add(Messages.PrintPreview_LABEL_FitPage);
		scaleSelection.add(Messages.PrintPreview_LABEL_FitWidth);
		scaleSelection.add(Messages.PrintPreview_LABEL_FitHeight);
		scaleSelection.select(0);
		scaleSelection.addListener(SWT.Selection, ev -> {
			updatePageNumbers();
			canvas.redraw();
		});

		printBorder = new Button(parent, SWT.CHECK);
		printBorder.setText(Messages.PrintPreview_LABEL_PrintBorder);
		printBorder.setSelection(true);
		printBorder.addListener(SWT.Selection, ev -> canvas.redraw());

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Margin);
		combo = new Combo(parent, SWT.READ_ONLY);
		combo.add("0.5"); //$NON-NLS-1$
		combo.add("1.0"); //$NON-NLS-1$
		combo.add("1.5"); //$NON-NLS-1$
		combo.add("2.0"); //$NON-NLS-1$
		combo.add("2.5"); //$NON-NLS-1$
		combo.add("3.0"); //$NON-NLS-1$
		combo.select(1);
		combo.addListener(SWT.Selection, ev -> {
			final double value = Double.parseDouble(combo.getItem(combo.getSelectionIndex()));
			// calculate from cm to inches
			setPrinter(printer, value / 2.54);
		});
		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_CM);
	}

	/**
	 * Checks which print option (Tile, Fit Page, ...) is selected.
	 *
	 * @return the PrintFigureOperation
	 */
	private int getOptionsSelection() {
		return scaleSelection.getSelectionIndex() + 1;
	}

	@Override
	protected Control createContents(final Composite parent) {
		parent.setSize(800, 600);

		final Composite composite = (Composite) super.createDialogArea(parent);
		createButtonArea(composite);

		final Label seperator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		seperator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		/* the preview */
		canvas = new Canvas(composite, SWT.NONE);
		final GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 4;
		canvas.setLayoutData(gridData);

		canvas.addPaintListener(e -> {
			if (printer == null || printer.isDisposed()) {
				return;
			}
			final Rectangle printerBounds = printer.getBounds();
			final Point canvasSize = canvas.getSize();

			double viewScaleFactor = canvasSize.x * 1.0 / printerBounds.width;
			viewScaleFactor = Math.min(viewScaleFactor, canvasSize.y * 1.0 / printerBounds.height);

			final int offsetX = (canvasSize.x - (int) (viewScaleFactor * printerBounds.width)) / 2;
			final int offsetY = (canvasSize.y - (int) (viewScaleFactor * printerBounds.height)) / 2;

			e.gc.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			// draws the page layout
			e.gc.fillRectangle(offsetX, offsetY, (int) (viewScaleFactor * printerBounds.width),
					(int) (viewScaleFactor * printerBounds.height));

			final int marginOffsetX = offsetX + (int) (viewScaleFactor * margin.getLeft());
			final int marginOffsetY = offsetY + (int) (viewScaleFactor * margin.getTop());

			final double scale = getScale();
			final double previewScaleFactor = viewScaleFactor * scale;

			final Graphics g = new SWTGraphics(e.gc);
			g.scale(previewScaleFactor);
			g.translate((int) (marginOffsetX / previewScaleFactor), (int) (marginOffsetY / previewScaleFactor));

			drawOnePage(scale, g, currentPage);

			g.dispose();

		});
		final double value = Double.parseDouble(combo.getItem(combo.getSelectionIndex()));
		setPrinter(null, value / 2.54); // calculate from cm to inches

		return composite;
	}

	private org.eclipse.draw2d.geometry.Point getClipRectLocationForPage(int page, final double scale) {
		final org.eclipse.draw2d.geometry.Rectangle bounds = figure.getBounds();
		final double scaledPageWidth = margin.getWidth() / scale;
		final double scaledPageHeight = margin.getHeight() / scale;
		page -= 1;

		final int cols = (int) Math.ceil((bounds.width()) / scaledPageWidth);
		final int currentColumn = page % cols;
		final int currentRow = page / cols;
		return new org.eclipse.draw2d.geometry.Point((int) (bounds.x + currentColumn * scaledPageWidth),
				(int) (bounds.y + currentRow * scaledPageHeight));
	}

	private void updatePageNumbers() {

		final org.eclipse.draw2d.geometry.Rectangle rectangle = figure.getBounds();

		final double scale = getScale();
		numberOfPages = (int) (Math.ceil((rectangle.preciseWidth() * scale) / margin.getWidth())
				* Math.ceil((rectangle.preciseHeight() * scale) / margin.getHeight()));
		numberOfPagesLabel.setText(String.valueOf(numberOfPages));
		if (currentPage > numberOfPages) {
			setCurrentPage(numberOfPages);
		}
	}

	private double getScale() {
		double scale = printer.getDPI().x * 1.0 / Display.getCurrent().getDPI().x * 1.0;

		switch (getOptionsSelection()) {
		case PrintFigureOperation.FIT_PAGE:
			scale *= Math.min(margin.getWidth() / (scale * figure.getBounds().width),
					margin.getHeight() / (scale * figure.getBounds().height));
			break;
		case PrintFigureOperation.FIT_WIDTH:
			scale *= (margin.getWidth() / (scale * figure.getBounds().width));
			break;
		case PrintFigureOperation.FIT_HEIGHT:
			scale *= (margin.getHeight() / (scale * figure.getBounds().height));
			break;
		case PrintFigureOperation.TILE: // when tile is selected we keep the default printer scale factor
		default:
			break;
		}

		return scale;
	}

	private void createButtonArea(final Composite parent) {
		final Composite buttonArea = new Composite(parent, SWT.NONE);
		final GridLayout buttonAreaLayout = new GridLayout(7, false);
		buttonAreaLayout.marginHeight = 0;
		buttonArea.setLayout(buttonAreaLayout);
		final GridData buttonLayoutData = new GridData();
		buttonLayoutData.horizontalAlignment = SWT.FILL; /* grow to fill available width */
		buttonArea.setLayoutData(buttonLayoutData);

		final Button buttonPrint = new Button(buttonArea, SWT.PUSH);
		buttonPrint.setText(Messages.PrintPreview_LABEL_Print);
		buttonPrint.addListener(SWT.Selection, ev -> performPrinting());

		new Label(buttonArea, SWT.SEPARATOR | SWT.VERTICAL).setLayoutData(new GridData(GridData.FILL_VERTICAL));
		createPageNavigation(new Composite(buttonArea, SWT.NONE));

		new Label(buttonArea, SWT.SEPARATOR | SWT.VERTICAL).setLayoutData(new GridData(GridData.FILL_VERTICAL));
		createOptionsGUI(new Composite(buttonArea, SWT.NONE));

		new Label(buttonArea, SWT.SEPARATOR | SWT.VERTICAL).setLayoutData(new GridData(GridData.FILL_BOTH));

		final Composite closeArea = new Composite(buttonArea, SWT.NONE);
		final GridLayout closeAreaLayout = new GridLayout(2, false);
		closeAreaLayout.marginHeight = 0;
		closeAreaLayout.marginWidth = 0;
		closeArea.setLayout(closeAreaLayout);

		closeArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(closeArea, SWT.NONE).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Button closeButton = new Button(closeArea, SWT.PUSH);
		closeButton.setText(Messages.PrintPreview_LABEL_Close);
		closeButton.addListener(SWT.Selection, ev -> close());
		closeButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
	}

	private void createPageNavigation(final Composite parent) {
		final GridLayout layout = new GridLayout(6, false);
		layout.marginHeight = 0;
		parent.setLayout(layout);
		final Label pageLabel = new Label(parent, SWT.NONE);
		pageLabel.setText(Messages.PrintPreview_LABEL_Page);

		final Button left = new Button(parent, SWT.ARROW | SWT.LEFT);
		left.addListener(SWT.Selection, ev -> {
			if (currentPage > 1) {
				setCurrentPage(currentPage - 1);
			}
		});

		currentPageText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		final GC gc = new GC(currentPageText);
		final FontMetrics fm = gc.getFontMetrics();
		final int width = (int) (3 * fm.getAverageCharacterWidth());
		final int height = fm.getHeight();
		gc.dispose();
		currentPageText.setSize(currentPageText.computeSize(width, height));
		currentPageText.setText(String.valueOf(currentPage));
		currentPageText.addListener(SWT.Modify, ev -> {
			try {
				final int newCurrentPage = Integer.parseInt(currentPageText.getText());
				if (0 < newCurrentPage && newCurrentPage <= numberOfPages) {
					setCurrentPage(newCurrentPage);
				}
			} catch (final Exception e) {
				// as we have a verify listener we should never be here, still just ignore it
			}
		});
		currentPageText.addListener(SWT.Verify, this::pageNumberVerifier);

		final Label of = new Label(parent, SWT.NONE);
		of.setText(Messages.PrintPreview_LABEL_Of);

		numberOfPagesLabel = new Label(parent, SWT.NONE);
		numberOfPagesLabel.setText(String.valueOf(numberOfPages));

		final Button right = new Button(parent, SWT.ARROW | SWT.RIGHT);
		right.addListener(SWT.Selection, ev -> {
			if (currentPage < numberOfPages) {
				setCurrentPage(currentPage + 1);
			}
		});

	}

	private void pageNumberVerifier(final Event ev) {
		if (!ev.doit) {
			// other verifylisteners which are first can already set it
			return;
		}

		if (ev.keyCode == SWT.DEL || ev.keyCode == SWT.BS) {
			return;
		}

		if (ev.character == SWT.NULL) {
			ev.doit = true;
		} else {
			final String currentValue = ((Text) ev.widget).getText();
			String resultingValue = currentValue.substring(0, ev.start) + ev.text + currentValue.substring(ev.end);
			if (resultingValue.isEmpty()) {
				resultingValue = String.valueOf(ev.character);
			}
			ev.doit = ONLY_DIGIT_PATTERN.matcher(resultingValue).matches();
			// TODO consider if we should allow only numbers in the valid range
		}
	}

	private void setCurrentPage(final int newCurrentPage) {
		if (!blockCurrentPageUpdate) {
			blockCurrentPageUpdate = true;
			currentPage = newCurrentPage;
			currentPageText.setText(String.valueOf(currentPage));
			canvas.redraw();
			blockCurrentPageUpdate = false;
		}
	}

	private void performPrinting() {
		final PrintDialog dialog = new PrintDialog(getShell());
		// Prompts the printer dialog to let the user select a printer.
		final PrinterData printerData = dialog.open();

		if (printerData == null) {
			return;
		}
		// Loads the printer.
		final Printer newPrinter = new Printer(printerData);
		final double value = Double.parseDouble(combo.getItem(combo.getSelectionIndex()));
		// calculate from cm to inches
		setPrinter(newPrinter, value / 2.54);
		// print the document
		print(newPrinter);
		printer.dispose();
		close();
	}

	/**
	 * Prints the figure current displayed to the specified printer.
	 *
	 * @param printer the printer
	 */
	void print(final Printer printer) {

		if (!printer.startJob(printName)) {
			FordiacLogHelper.logError(Messages.PrintPreview_ERROR_StartingPrintJob);
			return;
		}

		final GC gc = new GC(printer);
		final SWTGraphics g = new SWTGraphics(gc);
		final PrinterGraphics graphics = new PrinterGraphics(g, printer);

		graphics.setForegroundColor(figure.getForegroundColor());
		graphics.setBackgroundColor(figure.getBackgroundColor());
		graphics.setFont(figure.getFont());

		final double scale = getScale();

		graphics.scale(scale);
		graphics.translate((int) (margin.getLeft() / scale), (int) (margin.getTop() / scale));

		for (int i = 1; i <= numberOfPages; i++) {
			if (!printer.startPage()) {
				FordiacLogHelper.logError(Messages.PrintPreview_ERROR_StartingNewPage);
				return;
			}
			graphics.pushState();

			drawOnePage(scale, graphics, i);

			printer.endPage();
			graphics.popState();
		}

		printer.endJob();

		gc.dispose();
	}

	/**
	 * Sets target printer.
	 *
	 * @param newPrinter the printer
	 * @param marginSize the margin size
	 */
	void setPrinter(Printer newPrinter, final double marginSize) {
		if (newPrinter == null) {
			newPrinter = new Printer(Printer.getDefaultPrinterData());
		}
		if (null != printer) {
			printer.dispose();
		}

		printer = newPrinter;
		margin = PrintMargin.getPrintMargin(newPrinter, marginSize);
		updatePageNumbers();
		canvas.redraw();
	}

	private void drawOnePage(final double scale, final Graphics g, final int pageNumber) {
		final org.eclipse.draw2d.geometry.Point p = getClipRectLocationForPage(pageNumber, scale);
		final org.eclipse.draw2d.geometry.Rectangle clipRect = new org.eclipse.draw2d.geometry.Rectangle(p.x, p.y,
				(int) (margin.getWidth() / scale), (int) (margin.getHeight() / scale));

		g.translate(-p.x, -p.y);
		g.setLineStyle(Graphics.LINE_DASH);
		g.setForegroundColor(ColorConstants.black);
		if (printBorder.getSelection()) {
			g.drawRectangle(clipRect);
		}

		g.setLineStyle(Graphics.LINE_SOLID);
		g.clipRect(clipRect);
		figure.paint(g);
	}

}

/**
 * Contains margin information (in pixels) for a print job.
 *
 */
class PrintMargin {
	// Margin to the left side, in pixels
	private final int left;

	// Margins to the right side, in pixels
	private final int right;

	// Margins to the top side, in pixels
	private final int top;

	// Margins to the bottom side, in pixels
	private final int bottom;

	private PrintMargin(final int left, final int right, final int top, final int bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getTop() {
		return top;
	}

	public int getBottom() {
		return bottom;
	}

	int getWidth() {
		return right - left;
	}

	int getHeight() {
		return bottom - top;
	}

	/**
	 * Returns a PrintMargin object containing the true border margins for the
	 * specified printer with the given margin in inches. Note: all four sides share
	 * the same margin width.
	 *
	 * @param printer
	 * @param margin
	 * @return
	 */
	static PrintMargin getPrintMargin(final Printer printer, final double margin) {
		return getPrintMargin(printer, margin, margin, margin, margin);
	}

	/**
	 * Returns a PrintMargin object containing the true border margins for the
	 * specified printer with the given margin width (in inches) for each side.
	 */
	static PrintMargin getPrintMargin(final Printer printer, final double marginLeft, final double marginRight,
			final double marginTop, final double marginBottom) {
		final Rectangle clientArea = printer.getClientArea();
		final Rectangle trim = printer.computeTrim(0, 0, 0, 0);

		final Point dpi = printer.getDPI();

		final int leftMargin = (int) (marginLeft * dpi.x) - trim.x;
		final int rightMargin = clientArea.width + trim.width - (int) (marginRight * dpi.x) - trim.x;
		final int topMargin = (int) (marginTop * dpi.y) - trim.y;
		final int bottomMargin = clientArea.height + trim.height - (int) (marginBottom * dpi.y) - trim.y;

		return new PrintMargin(leftMargin, rightMargin, topMargin, bottomMargin);
	}

	@Override
	public String toString() {
		return "Margin { " + left + ", " + right + "; " + top + ", " + bottom //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ " }"; //$NON-NLS-1$
	}
}
