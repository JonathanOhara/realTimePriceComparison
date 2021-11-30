package comom;

import interfaces.Filter;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DefaultFilters {
	
	private static boolean useDenyWords = true;
	private static String[] denyWords = {"Adesivo", "Alug", "Amiibo", "Bateria", "Blusa", "Bolsa", "Boneca", "Boneco", "Broche", "Calça", "Camisa", "Camiseta", "Caneca", "Capa", "Capinha", "Carregador", "Chaveiro", "Controle", "Cosplay", "Costume", "Deck", "Decoration", "Detonado", "Digital", "Diorama", "E-shop", "Eshop", "Estatueta", "Friccao", "Funko", "Gamepad", "Gift", "Guide", "Hoodie", "Impressao", "Livro", "Locacao", "Miniatura", "Moletom", "Mousepad", "Mug", "Novel", "Oculos", "PC", "PS2", "PS3", "PS4", "PS5", "Pelicula", "Pelucia", "Placa", "Plush", "Poster", "Quadro", "Revista", "Skin", "Statue", "Steam", "Suporte", "T-Shirt", "Tenis", "Volante", "Walkthrough", "Wii", "WiiU", "X360", "XBOX", "Xone", "Luminária", "Toalha"};
	private static String[] ignoreWords = {"&", "3ds", "a", "and", "ds", "in", "of", "on", "psp", "switch", "the", "vita"};

	public static Filter noFilter(){
		return new Filter(){
			@Override
			public boolean filter(String productName, String searchedName, String shopName, boolean ignoreMatchWords) {
				return true;
			}
		};
	}
	
	public static Filter contains(){
		return new Filter(){
			@Override
			public boolean filter(String productName, String searchedName, String shopName, boolean ignoreMatchWords) {
				return productName.toLowerCase().contains( searchedName.toLowerCase() );
			}
		};
	}
	
	public static Filter containAllWords(){
		return new Filter(){
			@Override
			public boolean filter(String productName, String searchedName, String shopName, boolean ignoreMatchWords) {
				List<String> productNameWords = Arrays.asList( productName.toLowerCase().split(" ") );
				String[] searchedNameWords = searchedName.toLowerCase().split(" ");
				boolean matches = true;

				if(!ignoreMatchWords) {
					for( String word: searchedNameWords ){
						if (Arrays.binarySearch(ignoreWords, word.toLowerCase()) > -1) {
							continue;
						}
						if (!wordContainsInList(productNameWords, word)) {
							String alternativeWord = getAlternativeWord(word);
							if(alternativeWord != null){
								if(!wordContainsInList(productNameWords, alternativeWord)) {
									ErrorsUtil.addMatchWordsError("[" +shopName+ "] " + productName + ", no: "+word+ " or "+alternativeWord);
									matches = false;
									break;
								}
							}else{
								ErrorsUtil.addMatchWordsError("[" +shopName+ "] " + productName + ", no: "+word);
								matches = false;
								break;
							}

						}
					}
				}
				
				if( matches && useDenyWords ){
					for( String word: denyWords ){
						
						if( wordContainsInList( productNameWords, word) ){
							ErrorsUtil.addDenyWordError("[" +shopName+ "] " + productName + ", denied word: "+word);
							matches = false;
							break;
						}
					}	
				}
				return matches;
			}

			private boolean wordContainsInList(List<String> productNameWords, String word) {
				boolean matches = false;

				for(String s : productNameWords){
					s = unaccent(s);

					if( s.contains(word.toLowerCase()) ){
						matches = true;
						break;
					}
				}
				return matches;
			}
		};
	}

	private static String getAlternativeWord(String word) {
		String alternativeWord = null;

		if(isInteger(word)){
			if(Integer.parseInt(word) < 50) {
				alternativeWord = integerToRoman1(Integer.parseInt(word));
			}
		}else if(isRomanNumeral(word.toUpperCase(Locale.ROOT))){
			alternativeWord = String.valueOf(romanToDecimal(word.toUpperCase(Locale.ROOT)));
		}

		return alternativeWord;
	}

	static int value(char r)
	{
		if (r == 'I')
			return 1;
		if (r == 'V')
			return 5;
		if (r == 'X')
			return 10;
		if (r == 'L')
			return 50;
		if (r == 'C')
			return 100;
		if (r == 'D')
			return 500;
		if (r == 'M')
			return 1000;
		return -1;
	}

	// Finds decimal value of a
	// given romal numeral
	static int romanToDecimal(String str) {
		// Initialize result
		int res = 0;

		for (int i = 0; i < str.length(); i++)
		{
			// Getting value of symbol s[i]
			int s1 = value(str.charAt(i));

			// Getting value of symbol s[i+1]
			if (i + 1 < str.length())
			{
				int s2 = value(str.charAt(i + 1));

				// Comparing both values
				if (s1 >= s2)
				{
					// Value of current symbol
					// is greater or equalto
					// the next symbol
					res = res + s1;
				}
				else
				{
					// Value of current symbol is
					// less than the next symbol
					res = res + s2 - s1;
					i++;
				}
			}
			else {
				res = res + s1;
			}
		}

		return res;
	}

	private static boolean isRomanNumeral(String s) {
		return s.matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
	}

	public static String integerToRoman1(int number) {
		StringBuilder s = new StringBuilder();
		while (number >= 1000) {
			s.append("M");
			number -= 1000;
		}
		while (number >= 900) {
			s.append("CM");
			number -= 900;
		}
		while (number >= 500) {
			s.append("D");
			number -= 500;
		}
		while (number >= 400) {
			s.append("CD");
			number -= 400;
		}
		while (number >= 100) {
			s.append("C");
			number -= 100;
		}
		while (number >= 90) {
			s.append("XC");
			number -= 90;
		}
		while (number >= 50) {
			s.append("L");
			number -= 50;
		}
		while (number >= 40) {
			s.append("XL");
			number -= 40;
		}
		while (number >= 10) {
			s.append("X");
			number -= 10;
		}
		while (number >= 9) {
			s.append("IX");
			number -= 9;
		}
		while (number >= 5) {
			s.append("V");
			number -= 5;
		}
		while (number >= 4) {
			s.append("IV");
			number -= 4;
		}
		while (number >= 1) {
			s.append("I");
			number -= 1;
		}
		return s.toString();
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch(NumberFormatException e) {
			return false;
		} catch(NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public static String unaccent(String src) {
		return Normalizer
				.normalize(src, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "");
	}	
}
