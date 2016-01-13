package aave;

public class Word {

	private String word;
	private int aaveCount;
	private int stanCount;
	private double aaveP;
	private double stanP;
	private double totalP;

	public Word(String s) {
		word = s;
		aaveCount = 0;
		stanCount = 0;
		aaveP = 0.0;
		stanP = 0.0;
		totalP = 0.0;
	}

	public void addAAVE() {
		aaveCount++;
	}

	public void addStan() {
		stanCount++;
	}

	public void stanProb(int total) {
		if (total > 0)
			stanP = stanCount / total;
	}

	public void aaveProb(int total) {
		if (total > 0)
			aaveP = aaveCount / total;
	}

	@Override
	public void finalize() {
		if (stanP + aaveP > 0)
			totalP = aaveP / (aaveP + stanP);
		if (totalP < 0.01)
			totalP = 0.01;
		else if(totalP > 0.99)
			totalP = 0.99;
	}

	public double interesting() {
		return Math.abs(0.5 - totalP);
	}

	public String getWord() {
		return word;
	}

	public double getTotalP() {
		return totalP;
	}

	public void setTotalP(double d) {
		totalP = d;
	}
	
	public double getAaveP()
	{
		return aaveP;
	}
	
	public double getStanP()
	{
			return stanP;
	}
	
	public int getACount()
	{
		return aaveCount;
	}
	
	public int getSCount()
	{
		return stanCount;
	}

}
