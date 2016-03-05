package AdbUtilPkg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class AdbUtil_Graph extends JPanel {	
	/**
	  * 
	  */
	private final int MAX_CATEGORY_NUM = 3;
	private static final int NOTICE_CNT = 3;
	
	public static ChartPanel thisPanel;
	private static final long serialVersionUID = 1L;
	private DataGenerator genTimer;
	private List<TimeSeries> tCategory = new ArrayList<TimeSeries>();	

	private static List<String> feedDataListAxisCPU = new ArrayList<String>();	
	private static int maxAge;	
	private static XYPlot plot;
	private static RectangleInsets rectInset;
	private static XYItemRenderer renderer;
	private static BasicStroke stroke;
	private static Dimension dChartDimension;
	private static List<String> mNoticeCpu = new ArrayList<String>();
	
	public AdbUtil_Graph(int max, List<String> obj) {
		AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
		int i = 0;
		maxAge = max;
		String tempStr = "";
		
		for (i = 0; i < MAX_CATEGORY_NUM; i++)	{
			this.tCategory.add(i, new TimeSeries(tempStr+obj.get(i).toString().trim(), Millisecond.class));
			this.tCategory.get(i).setMaximumItemAge(maxAge);
		}
		tempStr = "notice";
		for (i = MAX_CATEGORY_NUM; i < MAX_CATEGORY_NUM+NOTICE_CNT; i++)	{
			this.tCategory.add(i, new TimeSeries(tempStr+(i-MAX_CATEGORY_NUM), Millisecond.class));
			this.tCategory.get(i).setMaximumItemAge(maxAge);
		}
		
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();		
		for (i = 0; i < MAX_CATEGORY_NUM+NOTICE_CNT; i++)	{
			dataset.addSeries(this.tCategory.get(i));
		}
		
		DateAxis domain = new DateAxis("Time");
		NumberAxis range = new NumberAxis("CPU %");
		domain.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		range.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		domain.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
		range.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
		range.setRange(0, 100);
		
		renderer = new XYLineAndShapeRenderer(true, false);
		
		for (i = 0; i < MAX_CATEGORY_NUM; i++)	{
			renderer.setSeriesPaint(i, layout.GRAPH_COLOR[i]);
		}
		for (i = MAX_CATEGORY_NUM; i < MAX_CATEGORY_NUM+NOTICE_CNT; i++)	{
			renderer.setSeriesPaint(i, layout.GRAPH_COLOR_IMPORTANT[i-MAX_CATEGORY_NUM]);
		}

		stroke = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		renderer.setBaseStroke(stroke);
		
		for (i = 0; i < MAX_CATEGORY_NUM+NOTICE_CNT; i++)	{
			renderer.setSeriesStroke(i, stroke);
		}
		
		plot = new XYPlot(dataset, domain, range, renderer);
		plot.setBackgroundPaint(Color.black);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);		
		rectInset = new RectangleInsets(5.0, 5.0, 5.0, 5.0);
		plot.setAxisOffset(rectInset);

		//domain.setAutoRange(true);
		domain.setLowerMargin(5.0);
		domain.setUpperMargin(5.0);
		//domain.setTickLabelsVisible(true);
		domain.setFixedAutoRange(120000);
		domain.setTickMarksVisible(false);
		
		range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		String title = "android system CPU %";
		Font titleFont = new Font("SansSerif", Font.BOLD, 15);
		boolean createLegend = false;
		JFreeChart chart = new JFreeChart(title, titleFont, plot, createLegend);
		chart.setBackgroundPaint(Color.white);

		dChartDimension = new Dimension(layout.CHART_WIDTH, layout.CHART_HEIGHT);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		thisPanel = chartPanel;
		setSubPanelBounds();
		add(chartPanel);
	}

	private void addObservation(int i, double y) {
		this.tCategory.get(i).add(new Millisecond(), y);
	}

	class DataGenerator extends Timer implements ActionListener {
		/**
	   * 
	   */
		private static final long serialVersionUID = 1L;

		DataGenerator(int interval) {
			super(interval, null);

			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = 0;
			
			if (feedDataListAxisCPU.size() > 0)	{
				for (i = 0; i < MAX_CATEGORY_NUM; i++)	{
					String temp = feedDataListAxisCPU.get(i).toString().trim();
					double dTemp = Double.parseDouble(temp);
					addObservation(i, dTemp);
				}
				if (mNoticeCpu != null && mNoticeCpu.size() > 0)	{
					for (i = MAX_CATEGORY_NUM; i < MAX_CATEGORY_NUM+mNoticeCpu.size(); i++)	{
						String temp = mNoticeCpu.get(i-MAX_CATEGORY_NUM);
						double dTemp = Double.parseDouble(temp);
						addObservation(i, dTemp);
					}					
				} else	{
					addObservation(MAX_CATEGORY_NUM, 0.00);
					addObservation(MAX_CATEGORY_NUM+1, 0.00);
					addObservation(MAX_CATEGORY_NUM+2, 0.00);
				}
			}
		}
	}
	
	public static void setSubPanelBounds()	{
		thisPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		thisPanel.setLocation(0, 0);
		thisPanel.setPreferredSize(dChartDimension);
	}
	
	public static void setFeedData(List<String> cpu, List<String> notice)	{
		feedDataListAxisCPU = cpu;
		mNoticeCpu = notice;
	}
		
	public void drawStart(AdbUtil_Graph comp)	{
		if (genTimer != null && genTimer.isRunning())	{
			plot.setRenderer(renderer);
			genTimer.restart();
		}	else	{
			AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
			thisPanel.setBounds(0, 0, layout.CHART_WIDTH, layout.CHART_HEIGHT);			
			this.repaint();			
			genTimer = comp.new DataGenerator(500);
			genTimer.start();
		}
	}
	public void drawStop()	{
		if (genTimer != null && genTimer.isRunning()){
			genTimer.stop();
		}
	}
	public void drawDestroy()	{
		drawStop();
		thisPanel.removeAll();
		this.removeAll();	
	}
	public static ChartPanel getChartPanel() {
		return thisPanel;
	}
}