package comom;

import interfaces.Filter;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;

public class DefaultFilters {
	
	private static boolean useDenyWords = true;
	private static String[] denyWords = {"Adesivo", "Alug", "Amiibo", "Bateria", "Blusa", "Bolsa", "Boneco", "Broche", "Calça", "Camiseta", "Caneca", "Capa", "Capinha", "Carregador", "Chaveiro", "Cosplay", "Costume", "Deck", "Decoration", "Detonado", "Digital", "Diorama", "Eshop", "Estatueta", "Friccao", "Funko", "Gamepad", "Gift", "Guide", "Hoodie", "Impressao", "Livro", "Locacao", "Miniatura", "Moletom", "Mug", "Novel", "Oculos", "PC", "PS2", "PS3", "PS4", "PS5", "Pelicula", "Pelucia", "Placa", "Plush", "Poster", "Quadro", "Revista", "Skin", "Steam", "Suporte", "T-Shirt", "Tenis", "Volante", "Walkthrough", "Wii", "WiiU", "X360", "XBOX", "Xone", "Controle", "Camisa", "Statue", "Mousepad", "Boneca"};
	private static String[] ignoreWords = {"and", "a", "of", "the", "in", "on", "&", "psp", "vita", "ds", "3ds", "switch"};

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
							ErrorsUtil.addMatchWordsError("[" +shopName+ "] " + productName + ", no: "+word);
							matches = false;
							break;
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
					if( unaccent(s).contains(word.toLowerCase()) ){
						matches = true;
						break;
					}
				}
				return matches;
			}
		};
	}

	public static String unaccent(String src) {
		return Normalizer
				.normalize(src, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "");
	}	
}
