package sukerPkg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class AndroSuker_Graph implements AndroSuker_Definition {	
	private static int maxAge;	
	private static final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new DateAxis("Time"));
	private static XYItemRenderer renderer_main;
	private static XYItemRenderer renderer_core;
	private static XYItemRenderer renderer_clock;
	private static BasicStroke stroke_main;
	private static BasicStroke stroke_core;
	private static BasicStroke stroke_clock;
	
	private static List<Double> feedDataListAxisCPU = new ArrayList<Double>();
	private static List<Double> mNoticeCpu_main = new ArrayList<Double>();
	private static List<Double> mCoreUsage = new ArrayList<Double>();
	private static List<Double> mCoreClock = new ArrayList<Double>();
	private static int coreCountByUser;
	private static int max_core_count;
	
	public static JFreeChart thisChart;

	private Thread genThread;
	private List<TimeSeries> tCategoryPid = new ArrayList<TimeSeries>();
	private List<TimeSeries> tCategoryCore = new ArrayList<TimeSeries>();
	private List<TimeSeries> tCategoryClock = new ArrayList<TimeSeries>();
	
	private final int PLOT_MAIN = 0;
	private final int PLOT_CORE = 1;
	private final int PLOT_CLOCK = 2;
		
	private final int PLOT_COUNT = 3;
	private final int PRIMARY_CATEGORY_NUM = 3;
	private final int SECONDARY_CATEGORY_NUM = 2;
	private final int MAX_CATEGORY_NUM = PRIMARY_CATEGORY_NUM + SECONDARY_CATEGORY_NUM;
	
	public AndroSuker_Graph(int max, List<String> obj, int count) {
		int i = 0;
		maxAge = max;
		max_core_count = coreCountByUser = count;
		
		/*DateAxis domain = new DateAxis("Time");
		domain.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		domain.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		domain.setLowerMargin(5.0);
		domain.setUpperMargin(5.0);	
		domain.setTickMarksVisible(true);
		//domain.setFixedAutoRange(120000);
*/		
		TimeSeriesCollection[] dataset = new TimeSeriesCollection[PLOT_COUNT];	
		
		//-----------------------------------------------------------------------------------------------
		//CPU usage for the pid
		//-----------------------------------------------------------------------------------------------
		if (obj != null)	{
			for (i = 0; i < PRIMARY_CATEGORY_NUM; i++)	{
				this.tCategoryPid.add(i, new TimeSeries(Integer.toString(i)));
				this.tCategoryPid.get(i).setMaximumItemAge(maxAge);
			}
			for (i = PRIMARY_CATEGORY_NUM; i < MAX_CATEGORY_NUM; i++)	{
				this.tCategoryPid.add(i, new TimeSeries(Integer.toString(i)+obj.get(i).toString().trim()));
				this.tCategoryPid.get(i).setMaximumItemAge(maxAge);
			}				
		}	else	{
			for (i = 0; i < PRIMARY_CATEGORY_NUM; i++)	{
				this.tCategoryPid.add(i, new TimeSeries(""));
				this.tCategoryPid.get(i).setMaximumItemAge(maxAge);
			}
			for (i = PRIMARY_CATEGORY_NUM; i < MAX_CATEGORY_NUM; i++)	{
				this.tCategoryPid.add(i, new TimeSeries(""));
				this.tCategoryPid.get(i).setMaximumItemAge(maxAge);
			}
		}
		dataset[PLOT_MAIN] = new TimeSeriesCollection();
		for (i = 0; i < MAX_CATEGORY_NUM; i++)	{
			dataset[PLOT_MAIN].addSeries(this.tCategoryPid.get(i));
		}

		NumberAxis range_main = new NumberAxis("CPU %");
		range_main.setRange(0.0, 105.0);		
		range_main.setAutoRange(false);
		
		renderer_main = new XYLineAndShapeRenderer(true, false);
		
		for (i = 0; i < PRIMARY_CATEGORY_NUM; i++)	{
			renderer_main.setSeriesPaint(i, GRAPH_COLOR_IMPORTANT[i]);
		}
		for (i = PRIMARY_CATEGORY_NUM; i < MAX_CATEGORY_NUM; i++)	{
			renderer_main.setSeriesPaint(i, GRAPH_COLOR[i-PRIMARY_CATEGORY_NUM]);
		}

		stroke_main = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		renderer_main.setBaseStroke(stroke_main);
		
		for (i = 0; i < MAX_CATEGORY_NUM; i++)	{
			renderer_main.setSeriesStroke(i, stroke_main);
		}
		
		final XYPlot plot_main = new XYPlot(dataset[PLOT_MAIN], null, range_main, renderer_main);
		plot_main.setBackgroundPaint(Color.black);
		plot_main.setDomainGridlinePaint(Color.gray);
		plot_main.setRangeGridlinePaint(Color.gray);		
		plot.add(plot_main, 6);
		
		//-----------------------------------------------------------------------------------------------
		// CPU Usage for each
		//-----------------------------------------------------------------------------------------------
		dataset[PLOT_CORE] = new TimeSeriesCollection();
		for (i = 0; i < max_core_count; i++)	{
			this.tCategoryCore.add(i, new TimeSeries(""));
			this.tCategoryCore.get(i).setMaximumItemAge(maxAge);
			dataset[PLOT_CORE].addSeries(this.tCategoryCore.get(i));
		}
		
		NumberAxis range_core = new NumberAxis("Core Usage");
		range_core.setRange(0.0, 105.0);		
		range_core.setAutoRange(false);
		
		renderer_core = new XYLineAndShapeRenderer(true, false);
		
		for (i = 0; i < max_core_count; i++)	{
			renderer_core.setSeriesPaint(i, GRAPH_COLOR_CORE_USAGE[i]);
		}

		stroke_core = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		renderer_core.setBaseStroke(stroke_core);
		
		for (i = 0; i < max_core_count; i++)	{
			renderer_core.setSeriesStroke(i, stroke_core);
		}
		
		final XYPlot plot_core = new XYPlot(dataset[PLOT_CORE], null, range_core, renderer_core);// new StandardXYItemRenderer());
		plot_core.setBackgroundPaint(Color.black);
		plot_core.setDomainGridlinePaint(Color.gray);
		plot_core.setRangeGridlinePaint(Color.gray);
		plot.add(plot_core, 4);
		//-----------------------------------------------------------------------------------------------
		// CPU Thermal and Clock
		//-----------------------------------------------------------------------------------------------
		dataset[PLOT_CLOCK] = new TimeSeriesCollection();
		for (i = 0; i < max_core_count; i++)	{
			this.tCategoryClock.add(i, new TimeSeries(""));
			this.tCategoryClock.get(i).setMaximumItemAge(maxAge);
			dataset[PLOT_CLOCK].addSeries(this.tCategoryClock.get(i));
		}
		
		NumberAxis range_clock = new NumberAxis("Core CLK & Thermal");
		range_clock.setRange(100000.0, 2000000.0);		
		range_clock.setAutoRange(false);
		
		renderer_clock = new XYLineAndShapeRenderer(true, false);
		
		for (i = 0; i < max_core_count; i++)	{
			renderer_clock.setSeriesPaint(i, GRAPH_COLOR_CORE_USAGE[i]);
		}

		stroke_clock = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		renderer_clock.setBaseStroke(stroke_clock);
		
		for (i = 0; i < max_core_count; i++)	{
			renderer_clock.setSeriesStroke(i, stroke_clock);
		}
		
		final XYPlot plot_clock = new XYPlot(dataset[PLOT_CLOCK], null, range_clock, renderer_clock);// new StandardXYItemRenderer());
		plot_clock.setBackgroundPaint(Color.black);
		plot_clock.setDomainGridlinePaint(Color.gray);
		plot_clock.setRangeGridlinePaint(Color.gray);
		plot.add(plot_clock, 4);
		//-----------------------------------------------------------------------------------------------
		
		plot.setGap(30);
		final ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(120000.0);  // 120 seconds
        axis.setAxisLineVisible(true);
        axis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 13));
        axis.setLabelFont(new Font("SansSerif", Font.BOLD, 13));
        axis.setTickMarksVisible(true);

        //-----------------------------------------------------------------------------------------------
		String title = "Android System CPU Usage";
		Font titleFont = new Font(null, BOLD_STYLE, 14);
		boolean createLegend = false;
		JFreeChart chart = new JFreeChart(title, titleFont, plot, createLegend);
		chart.setBackgroundPaint(Color.gray);

		if (AndroSuker_Debug.DEBUG_MODE_ON)
			System.out.print("AndroSuker_Graph create done \n");
		
		thisChart = chart;		
	}

	class DataGenerator extends Thread {
		private Display display = null;
		private int	interval = 0;
		
		DataGenerator(Composite shell, AndroSuker_Graph comp, int interval) {
			this.display = shell.getDisplay();
			this.interval = interval;
		}
		
		@Override
		public void run()	{
			while(!Thread.currentThread().isInterrupted() && !AndroSuker_MainCls.getShellInstance().isDisposed())	{
				display.asyncExec(new Runnable()	{
					public void run()	{
						int i = 0;
						
						if (AndroSuker_Debug.DEBUG_MODE_ON)	{
							System.out.print("Graph->run() \n");
						}
						if (feedDataListAxisCPU.size() > 0)	{
							//primary
							if (mNoticeCpu_main != null && mNoticeCpu_main.size() > 0)	{
								for (i = 0; i < PRIMARY_CATEGORY_NUM; i++)	{									
									double dTemp = mNoticeCpu_main.get(i);						
									tCategoryPid.get(i).addOrUpdate(new Millisecond(), dTemp);
								}
							} else	{								
								tCategoryPid.get(0).addOrUpdate(new Millisecond(), 0.00);
								tCategoryPid.get(1).addOrUpdate(new Millisecond(), 0.00);
								tCategoryPid.get(2).addOrUpdate(new Millisecond(), 0.00);								
							}
							//secondary
							for (i = PRIMARY_CATEGORY_NUM; i < MAX_CATEGORY_NUM; i++)	{
								double dTemp = feedDataListAxisCPU.get(i-PRIMARY_CATEGORY_NUM);
								tCategoryPid.get(i).addOrUpdate(new Millisecond(), dTemp);
							}
						}																	
						for (i = 0; i < coreCountByUser; i++)	{
							double dTemp = mCoreUsage.get(i);
							tCategoryCore.get(i).addOrUpdate(new Millisecond(), dTemp);
						}
						for (i = 0; i < coreCountByUser; i++)	{
							double dTemp = mCoreClock.get(i);
							tCategoryClock.get(i).addOrUpdate(new Millisecond(), dTemp);
						}	
					}
				});

				try {
					Thread.sleep(this.interval);
				} catch (InterruptedException e)	{
					Thread.currentThread().interrupt();
					return ;
				}
			}
		}
	}

	public void clearXYPlots()	{
		for (int i = 0; i < MAX_CATEGORY_NUM; i++)	{
			this.tCategoryPid.get(i).clear();
		}
		for (int i = 0; i < max_core_count; i++)	{
			this.tCategoryCore.get(i).clear();
			this.tCategoryClock.get(i).clear();
		}
	}

	public static void setFeedData(List<Double> cpu, List<Double> notice_main, List<Double> coreUsage, List<Double> clockUsage)	{
		if (AndroSuker_Debug.DEBUG_MODE_ON)	{
			System.out.print("setFeedData \n");
		}
		feedDataListAxisCPU = cpu;
		mNoticeCpu_main = notice_main;
		mCoreUsage = coreUsage;
		mCoreClock = clockUsage;
	}
	
	public void drawStart(Composite shell, AndroSuker_Graph comp, int count)	{
		coreCountByUser = count;
		if (coreCountByUser != max_core_count)	{
			for (int i = coreCountByUser; i < max_core_count; i++)	{
				this.tCategoryCore.get(i).addOrUpdate(new Millisecond(), 0.0);
				this.tCategoryClock.get(i).addOrUpdate(new Millisecond(), 0.0);
			}
		}
		if (genThread != null && genThread.isAlive())	{
			plot.setRenderer(0, renderer_main);
			plot.setRenderer(1, renderer_core);
			plot.setRenderer(2, renderer_clock);
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("genThread is Alive \n");
			}
		}	else	{
			genThread = new DataGenerator(shell, comp, 1000);
			genThread.start();
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("genThread is null, so thread start \n");				
			}
		}
	}
	public void drawStop()	{
		if (genThread != null && genThread.isAlive()){			
			genThread.interrupt();
		}
	}
	public void drawDestroy()	{
		clearXYPlots();
		drawStop();
	}
	public static JFreeChart getChartPanel() {
		return thisChart;
	}
}