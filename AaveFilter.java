package aave;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class AaveFilter {

	ArrayList<Word> words = new ArrayList<Word>();

	public void trainAAVE(String file) throws Exception {
		File file2 = new File(file);
		Scanner sc = new Scanner(file2);
		ArrayList<String> tokens = new ArrayList<String>();
		while (sc.hasNext()) {
			String word = sc.next();
			tokens.add(word);
		}
		
		int aaveTotal = 0;
		
		for (int i = 0; i < tokens.size(); i++) {
			int counter = 0;
			int spot = 0;
			
			String word = tokens.get(i).toLowerCase();
			if(words.size() == 0)
				words.add(new Word(word));
			else{
				for(int j = 0; j < words.size(); j++) {
					if (word.equals(words.get(j).getWord())) {
						spot = j;
						counter++;
					}
				}
			
				if(counter == 0) {
					Word w = new Word(word);
					w.addAAVE();
					words.add(w);
					}
				else{
					words.get(spot).addAAVE();
					}
				aaveTotal++;
			}
		}
		
		for (int i = 0; i < words.size(); i++) {
			words.get(i).aaveProb(aaveTotal);
		}
	}

	
	public void trainStan(String file) throws Exception 
	 { 
		 File file2 = new File(file);
			Scanner sc = new Scanner(file2);
			ArrayList<String> tokens = new ArrayList<String>();
			while (sc.hasNext()) {
				String word = sc.next();
				tokens.add(word);
			}
			int stanTotal = 0;
			
			for (int i = 0; i < tokens.size(); i++) {
				int counter = 0;
				int spot = 0;
				
				String word = tokens.get(i).toLowerCase();
				if(words.size() == 0)
					words.add(new Word(word));
				else{
					for(int j = 0; j < words.size(); j++) {
						if (word.equals(words.get(j).getWord())) {
							spot = j;
							counter++;
						}
					}
				
					if(counter == 0) {
						Word w = new Word(word);
						w.addStan();
						words.add(w);
						}
					else{
						words.get(spot).addStan();
						}
					stanTotal++;
				}
			}

			for (int i = 0; i < words.size(); i++) {
				words.get(i).stanProb(stanTotal);
			}
	 }
	 

	public void finalizeTrain() {
		for (int i = 0; i < words.size(); i++) {
			words.get(i).finalize();
		}
	}

	public boolean analyze(String input) {
		ArrayList<Word> interesting = new ArrayList<Word>();

		String[] tokens = input.split(" ");

		for (int i = 0; i < tokens.length; i++) {
			String word = tokens[i].toLowerCase();

			Word w = new Word(word);
			for (int j = 0; j < words.size(); j++) {
				int counter = 0;
				if (word == words.get(j).getWord()) {
					w = words.get(j);
					counter++;
				}

				if (counter == 0) {
					w.setTotalP(0.5);
				}
			}

			int limit = 15;

			if (interesting.size() < 1)
				interesting.add(w);
			else {
				for (int j = 0; j < interesting.size(); j++) {

					Word nw = interesting.get(j);

					if (w.getWord().equals(nw.getWord()))
						break;
					else if (w.interesting() > nw.interesting()) {
						interesting.add(j, w);
					} else if (j == interesting.size() - 1)
						interesting.add(w);
				}
			}

			while (interesting.size() > limit)
				interesting.remove(interesting.size() - 1);
		}

		double paaveprod = 1.0;
		double pstanprod = 1.0;

		for (int i = 0; i < interesting.size(); i++) {
			Word w = interesting.get(i);
			paaveprod *= w.getTotalP();
			pstanprod *= (1.0 - w.getTotalP());

			// System.out.println(w.getTotalP());
		}

		double pAave = paaveprod / (paaveprod + pstanprod);

		if (pAave >= 0.9)
			return true;
		else
			return false;
	}

	public void getWordCount()
	{
		for(Word x : words)
			System.out.println(x.getWord() + " " + x.getTotalP() + " " + x.getStanP() + " " + x.getAaveP());
	}
}
