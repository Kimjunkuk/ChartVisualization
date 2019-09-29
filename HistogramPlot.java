package ChartVisualization;

import java.util.Random;

import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.EnumeratedData;
import de.erichseifert.gral.data.statistics.Histogram1D;
import de.erichseifert.gral.data.statistics.Statistics;
import de.erichseifert.gral.examples.ExamplePanel;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.util.Insets2D;
import de.erichseifert.gral.util.MathUtils;
import de.erichseifert.gral.util.Orientation;

//ExamplePanel 클래스를 상속받아 HistogramPlot 클래스를 생성한다. serial VersionUID를 설정한다.
public class HistogramPlot extends ExamplePanel{
	
	private static final long serialVerionUID = 4458280577519421950L;//직렬화를 위한 버전 아이디
	//--------------------------------------------------------------->
	
	//1000개의 샘플 데이터 포인트에 대한 히스토그램을 그린다.
	private static final int SAMPLE_COUNT =1000;
	//----------------------------------------->
	
	
	//@SuppressWarnings("unchecked")
	public HistogramPlot() {
		
		//무작위로 1000개의 샘플 데이터 포인트를 만든다. 데이터 포인트는 자바 Random클래스의 Random.nextGaussian()메소드를 사용해 가우스 분포에 따라 생성한다
		//예제 데이터 생성
		Random random = new Random();
		 
		DataTable data = new DataTable(Double.class);
		
		for(int i=0; i<SAMPLE_COUNT; i++) {
			data.add(random.nextGaussian());
		}
		//-------------------------------------------->
		
		//데이터로부터 히스토그램 생성하고, 히스토그램을 위한 2차원 데이터소스를 생성한다
		Histogram1D histogram = new Histogram1D(data, Orientation.VERTICAL,new Number[] {-4.0, -3.2, -2.4, -1.6, -0.8, 0.0, 0.8 , 1.6, 2.4 , 3.2, 4.0});
		
		//히스토그램을 위한 2차원 데이터소스 생성
		DataSource histogram2d = new EnumeratedData(histogram, (-4.0 + -3.2)/2.0, 0.8);
		//----------------------->
		
		//BarPlot객체 - 막대 모양의 객체 생성
		BarPlot plot = new BarPlot(histogram2d);
		
		//플롯 설정
		plot.setInsets(new Insets2D.Double(20.0, 65.0, 50.0 ,40.0));//프레임 내부에서 히스토그램 좌표를 설정한다
		plot.getTitle().setText(String.format("Distribution of %d random samples", data.getRowCount()));//히스토그램의 제목을 설정한다
		plot.setBarWidth(0.78);//히스토그램 막대의 너비를 설정한다
		
		//x축
		plot.getAxisRenderer(BarPlot.AXIS_X).setTickAlignment(0.0);//x축 눈금의 정렬 기준을 설정한다. getAxisRenderer() 메소드의 인수가 x축인 것에 주의할것
		plot.getAxisRenderer(BarPlot.AXIS_X).setTickSpacing(0.8);//눈금의 간격을 설정한다
		plot.getAxisRenderer(BarPlot.AXIS_X).setMinorTicksVisible(false);//마지막으로 작은 눈금은 보이지 않도록 설정한다.
		
		//y축
		plot.getAxis(BarPlot.AXIS_Y).setRange(0.0, MathUtils.ceil(histogram.getStatistics().get(Statistics.MAX)*1.1, 25.0));
		//y축 형식을 설정한다. 이 경우에는 막대가 늘어날 수 있는 최대 높이의 범위를 정의해야 한다.
		
		//x축과 마찬가지로 눈금 정렬,간격,작은 눈금 표시 여부에 대해 설정한다.
		plot.getAxisRenderer(BarPlot.AXIS_Y).setTickAlignment(0.0);
		plot.getAxisRenderer(BarPlot.AXIS_Y).setMinorTicksVisible(false);
		plot.getAxisRenderer(BarPlot.AXIS_Y).setIntersection(-4.4);
		//--------------------------------------------------------->
		
		//막대에 대한 설정을 한다. 막대에 색상을 주고, 막대의 위쪽에 빈도를 표시하도록 히스토그램을 구성한다.
		plot.getPointRenderer(histogram2d).setColor(GraphicsUtils.deriveWithAlpha(COLOR1, 128));
		plot.getPointRenderer(histogram2d).setValueVisible(true);
		
		//swing 컴포넌트에 플롯 추가(플롯의 확대 등 인터렉티브 요소에 대한 설정을 위해)
		InteractivePanel panel = new InteractivePanel(plot);
		panel.setPannable(false);
		panel.setZoomable(false);
		add(panel);
		
		
	}//생성자 닫기

	@Override
	public String getDescription() {
		//ExamplePanel 클래스에 있는 메소드를 구현해야 한다. 간단하게 다음과 같이getTitle()메소드와 getDescription()메소드를 오버라이드한다.
		// TODO Auto-generated method stub
		return "Histogram plot";
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return String.format("Histogram of %d samples", SAMPLE_COUNT);
	}
	public static void main(String[] args) {//메인 메소드 
		new HistogramPlot().showInFrame();
	}

}
