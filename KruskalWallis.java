
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.stat.inference.ChiSquareTest;

public class KruskalWallis {
	
	private List<double[]> data; // raw data groups 
	private double N; // highest rank index
	
	private double H;
	private double pvalue;
	
	public KruskalWallis(List<double[]> data) {
		this.data = data;
		compute();
	}
	
	private void compute() {
		List<Double> alldata = new ArrayList<Double>();
		for (double[] array : data) 
			for (double d : array) alldata.add(d);
		
		// Give a global rank index to all data real number whatever the group they belong to
		Map<Double, Double> dataRanks = rank(alldata);
		double average = .5 * ((double)N + 1d); // average of all ranks
		System.out.println("average= " + average);
		// Compute the sum of ranks and the average rank per group of data
		// And perform the test
		double sum=0;
		double[] sumRank = new double[data.size()]; // Sum of ranks
		for (int i = 0 ; i < data.size();i++) {
			for (double d : data.get(i)) {
				sumRank[i] += dataRanks.get(d);
			}
			
			H += Math.pow( sumRank[i],2d)/((double)data.get(i).length);// Compute the numerator of H : the variance of mean rank
			 // compute H denominator
			sum +=sumRank[i];
		}
		H = H - Math.pow(sum, 2)/N;
		H = H / (N*(N+1d)/12);
		
		// Now compute Chi-square
		ChiSquaredDistribution dist = new ChiSquaredDistribution(data.size() -1);
		pvalue = dist.cumulativeProbability(H);
	}
	
	/**
	 * Return highest rank value
	 * @return
	 */
	public double getN() {
		return N;
	}

	public double getH() {
		return H;
	}

	public double getPvalue() {
		return pvalue;
	}

	/**
	 * Rank data and give the same rank index to tie values
	 * @param list
	 * @return
	 */
	private Map<Double, Double> rank(List<Double> list) {
		Map<Double,Double> result = new HashMap<Double, Double>();
		
		Collections.sort(list);
		double rank = 0d;
		double tied = 0d;
		double lastValue =Double.NaN; 
		for (Double d : list) {
			if (d != lastValue) {
				rank+=1d+tied; // increase rank with 1+number of previous tied values
				tied = 0d;
			} else {
				tied+=1d;
			}
			
			result.put(d, rank + (tied > 0 ? 1d/(tied+1d) : 0d)); // To replace bad values it must be out of the if
			
			lastValue = d;
		}
		N = rank;
		return result;
	}
	
	public static void main(String[] args) {
		double[] a = {6.4, 6.8,	 7.2, 8.3, 8.4, 9.1, 9.4, 9.7};
		double[] b = {2.5, 3.7, 4.9,5.4, 5.9, 8.1, 8.2};
		double[] c = {1.3, 4.1, 4.9, 5.2, 5.5, 8.2 };
		List<double[]> samples = new ArrayList<double[]> ();
		samples.add(a);
		samples.add(b);
		samples.add(c);
		
		KruskalWallis kruskal = new KruskalWallis(samples);
		
		System.out.println("H = (should be ca . 9.84):" + kruskal.getH());
		System.out.println("P-value :" + kruskal.getPvalue());
	}
}
