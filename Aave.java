package aave;

public class Aave {

	public static void main(String[] args) throws Exception {
		AaveFilter filter = new AaveFilter();

		filter.trainAAVE("C:\\Users\\Chris\\Documents\\workspace\\bayesian\\src\\aave\\aavetext.txt");

		filter.trainStan("C:\\Users\\Chris\\Documents\\workspace\\bayesian\\src\\aave\\stan.txt");

		filter.finalizeTrain();
		
		filter.getWordCount();

		String stuff = "chillin";

		boolean aave = filter.analyze(stuff);
		if (aave)
			System.out.println("This is ebonics");
		else
			System.out.println("This is typical American English");
		
	}

}
