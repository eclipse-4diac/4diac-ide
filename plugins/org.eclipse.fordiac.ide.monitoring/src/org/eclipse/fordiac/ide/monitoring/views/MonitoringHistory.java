/*******************************************************************************
 * Copyright (c) 2012, 2015, 2016 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


public class MonitoringHistory extends ViewPart {
//	private static final double[] xSeries = { 0.0, 2.6, 6.5, 4.4, 5.6, 4.3,
//			3.4, 10.8, 2.1, 8.9 };
//	private static final double[] ySeries = { 1.3, 0.0, 3.9, 2.6, 1.1, 0.6,
//			3.1, 3.5, 5.6, 4.4 };
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.fordiac.ide.monitoring.views.MonitoringHistory"; //$NON-NLS-1$
//	private boolean syncSlider = false;

//	ArrayList<Slider> sliderList = new ArrayList<Slider>();
//	Hashtable<Slider, MonitoringSelectionListener> sliderListenerMapping = new Hashtable<Slider, MonitoringSelectionListener>();
//	private Hashtable<MonitoringElement, Color> colorMapping = new Hashtable<MonitoringElement, Color>();
//	private Hashtable<MonitoringElement, ILineSeries> elementSeriesMapping = new Hashtable<MonitoringElement, ILineSeries>();
//	
//	
//	private Chart chart;
//	private Composite container;
	
	/**
	 * The constructor.
	 */
	public MonitoringHistory() {
	}

//	/**
//	 * This is a callback that will allow us to create the viewer and initialize
//	 * it.
//	 */
	public void createPartControl(Composite parent) {
//		container = new Composite(parent, SWT.NONE);
//		container.setLayout(new GridLayout(3, false));
//		Label label = new Label(container, SWT.None);
//		label.setText("Synchronize");
//		final org.eclipse.swt.widgets.Button sync = new org.eclipse.swt.widgets.Button(
//				container, SWT.CHECK);
//		sync.addSelectionListener(new SelectionListener() {
//
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				syncSlider = sync.getSelection();
//			}
//
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//		
//		GridData syncGridData = new GridData();
//		syncGridData.horizontalSpan = 2;
//		sync.setLayoutData(syncGridData);
//
//		HashSet<String> elements = MonitoringManager.getInstance()
//				.getElementsToMonitor();
//		for (String string : elements) {
//			createSliderForMonitoringElement(container, string);
//		}
//
//		chart = new Chart(container, SWT.None);
//		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
//		gd.horizontalSpan = 3;
//		chart.setLayoutData(gd);
//
//		chart.getTitle().setText("Distributed Monitoring");
//		chart.getAxisSet().getXAxis(0).getTitle().setText("Time");
//
//		chart.getAxisSet().getYAxis(0).getTitle().setText("Score B");
//
//		// create scatter series
//
//
////		for (String string : elements) {
////			MonitoringElement element = MonitoringManager.getInstance()
////					.getMonitoringElement(string);
////			addSeries(chart, values, positions, string, element);
////		}
//
//		// adjust the axis range
//		chart.getAxisSet().adjustRange();
//		
//		MonitoringManager.getInstance().registerMonitoringListener(new IMonitoringListener() {
//			
//			@Override
//			public void notifyTriggerEvent(String port) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void notifyRemovePort(String port) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void notifyAddPort(String port) {
//					createSliderForMonitoringElement(container, port);
//					container.redraw();
//					container.update();
//			}
//		});
//
	}
//
//	private void createSliderForMonitoringElement(Composite container,
//			String string) {
//		MonitoringElement element = MonitoringManager.getInstance()
//				.getMonitoringElement(string);
//		Label l = new Label(container, SWT.None);
//		l.setText(string);
//		Button bt = new Button(container, SWT.CHECK);
//		bt.setData(element);
//		bt.addSelectionListener(new SelectionListener() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if (e.getSource() instanceof Button) {
//					Button bt = (Button)e.getSource();
//					if (bt.getData() instanceof MonitoringElement) {
//						MonitoringElement element = (MonitoringElement)bt.getData();
//						if (bt.getSelection()) {
//							addSeries(chart, element);
//						} else {
//							removeSeries(chart, element);
//						}
//					}
//				}
//			}
//			
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		Slider sl = new Slider(container, SWT.NONE);
//		sl.setMinimum(0);
//		sl.setMaximum(130);
//		sl.setSelection(element.getCurrentPos());
//		sl
//				.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,
//						false));
//		MonitoringSelectionListener msl = new MonitoringSelectionListener(
//				element, sl, this);
//		sl.addSelectionListener(msl);
//		sliderListenerMapping.put(sl, msl);
//		sl.setIncrement(1);
//		sl.setPageIncrement(1);
//
//		sliderList.add(sl);
//	}
//	
//	
//	private void removeSeries(Chart chart, MonitoringElement element) {
//		chart.getSeriesSet().deleteSeries(element.getMonitoringElement());
//		chart.getAxisSet().adjustRange();
//		elementSeriesMapping.remove(element);
//	}
//
//	private void addSeries(Chart chart, MonitoringElement element) {
//		
//		ILineSeries scatterSeries = (ILineSeries) chart.getSeriesSet()
//		.createSeries(SeriesType.LINE, element.getMonitoringElement());
//		
//		if (!colorMapping.containsKey(element)) {
//			Color c = new Color(null, RandomColorGenerator.createRandomColor());
//			colorMapping.put(element, c);
//		}
//		Color c = colorMapping.get(element);
//		
//		scatterSeries.setLineColor(c);
//		scatterSeries.enableArea(true);
//
//		scatterSeries.setSymbolColor(c);
//		scatterSeries.setLineStyle(LineStyle.SOLID);
//		elementSeriesMapping.put(element, scatterSeries);
//		updateSeriesValues(chart, element, scatterSeries);
//	}
//
//	private void updateSeriesValues(Chart chart, MonitoringElement element,
//			ILineSeries scatterSeries) {
//		ArrayList<Double> values = new ArrayList<Double>();
//		ArrayList<Long> positions = new ArrayList<Long>();
//		for (int i = 0; i < 120; i++) {
//			try {
//				double d = Double.parseDouble(element.getHistoryValue(i, false));
//				values.add(d);
//				positions.add(element.getHistorySec(i) * 1000 + element.getHistoryUSec(i));
//			} catch (NumberFormatException nfe) {
////				values.add(-1.0);
////				positions.add(new Long(i));
//			}
//
//		}
//		double da[] = new double[values.size()];
//		for (int i = 0; i < values.size(); i++) {
//			double d = values.get(i);
//			da[i] = d;
//			
//		}
//		double pos[] = new double[positions.size()];
//		for (int i = 0; i < positions.size(); i++) {
//			double d = positions.get(i) / 1000.0;
//			pos[i] = d;
//		}
//		scatterSeries.setXSeries(pos);
//		scatterSeries.setYSeries(da);
//		chart.getAxisSet().adjustRange();
//		chart.update();
//		chart.updateLayout();
//		chart.redraw();
//	}
//
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
//
//	class MonitoringSelectionListener implements SelectionListener {
//
//		private MonitoringElement element;
//		private Slider slider;
//		private MonitoringHistory mh;
//
//		private int lastPos = 0;
//
//		public MonitoringSelectionListener(MonitoringElement element,
//				Slider sl, MonitoringHistory mh) {
//			this.element = element;
//			this.slider = sl;
//			lastPos = sl.getSelection();
//			this.mh = mh;
//		}
//
//		public void update() {
//			lastPos = slider.getSelection();
//			Display.getDefault().asyncExec(new Runnable() {
//
//				@Override
//				public void run() {
//					element.updateValue(element.getHistoryValue(slider
//							.getSelection(), true));
//
//				}
//			});
//		}
//
//		@Override
//		public void widgetDefaultSelected(SelectionEvent e) {
//
//		}
//
//		@Override
//		public void widgetSelected(SelectionEvent e) {
//
//
//			int curPos = slider.getSelection();
//			int dif = lastPos - curPos;
//			mh.updateSyncedSliders(slider, dif);
//
//			update();
//
//		}
//
//	}
//
//	public void updateSyncedSliders(Slider source, final int dif) {
//
//		if (syncSlider && dif != 0) {
//			for (final Slider slider : sliderList) {
//				if (!source.equals(slider)) {
//					Display.getDefault().asyncExec(new Runnable() {
//
//						@Override
//						public void run() {
//
//							if (slider.getSelection() - dif == 0) {
//								slider.setSelection(120);
//							} else if (slider.getSelection() - dif == 121) {
//								slider.setSelection(0);
//							} else {
//								slider
//										.setSelection(((slider.getSelection() - dif)));
//							}
//							MonitoringSelectionListener msl = sliderListenerMapping
//									.get(slider);
//							if (msl != null) {
//								msl.update();
//							}
//						}
//					});
//				}
//			}
//		}
//
//	}
}