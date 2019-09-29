package Chapter03;

import org.apache.commons.math3.stat.descriptive.AggregateSummaryStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class CT_03_AggregateStats {//여러 개의 분포로부터 요약 통계량 생성하기
	
	//이 레시피에서는 여러 개의 데이터셋에서 전체적인 통계량을 모으기 위해 AggregateSummaryStatistics를 생성하고
	//각 표본 데이터에 대해서는 SummaryStatistics을 사용한다
	
	public static void main(String[] args){
		double[] values1 = {32, 39, 14, 98, 45, 44, 45};
		double[] values2 = {34, 89, 67, 0, 15, 0, 56, 88};
		CT_03_AggregateStats aggStatTest = new CT_03_AggregateStats();
		aggStatTest.getAggregateStats(values1, values2);
	}
	public void getAggregateStats(double[] values1, double[] values2){//1.두 개의 double 타입 배열을 인수로 취하는 메소드를 생성한다. 각배열은 서로 다른 두개의 데이터셋을 의미한다.
		AggregateSummaryStatistics aggregate = new AggregateSummaryStatistics();//2.AggregateSummaryStatistics 객체 생성
		
		SummaryStatistics firstSet = aggregate.createContributingStatistics();
		SummaryStatistics secondSet = aggregate.createContributingStatistics();
		//3.두 개의 서로 다른 분포로부터 요약 통계를 생성하기 위해 두 개의 SummaryStatistics 클래스를 생성한다
		
		for(int i = 0; i < values1.length; i++) {
			firstSet.addValue(values1[i]);
		}
		for(int i = 0; i < values2.length; i++) {
			secondSet.addValue(values2[i]);
		}
		//4.앞에서 생성한 두 객체에 두 분포의 값을 넣어 준다.
		
		double sampleSum = aggregate.getSum();
		double sampleMean = aggregate.getMean();
		double sampleStd= aggregate.getStandardDeviation();
		System.out.println("두 값의 총 합계 : "+sampleSum + "\t 두 값의 평균 : " + sampleMean + "\t 두 값의 표준 편차 : " + sampleStd);
		//5.AggregateSummaryStatistics 클래스의 메소드를 사용해 두 분포로부터 집계된 통계량을 생성한다. 생성된 통계를 출력한 후 메소드를 닫는다
	}
}
