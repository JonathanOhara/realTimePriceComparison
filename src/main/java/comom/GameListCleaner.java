package comom;

import comom.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class GameListCleaner {

	public static File clean(File gameListFile) throws IOException{
		List<String> gameList = null;
		
		gameList = Util.ler(gameListFile);
		
		System.out.println("Original File size = "+gameList.size()+" games");
		
		gameList = removeNullAndEmpty(gameList);
		
		System.out.println("Without null and empty lines "+gameList.size()+" games");
		
		gameList = removeSquareBracket(gameList);
		
		return writeFinalFile(gameList);
	}

	private static File writeFinalFile(List<String> gameList) throws IOException {
		File file = new File(Util.getProjectPath() + "/resources/finalgamelist.txt");
		 
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for( int i = 0; i < gameList.size(); i++ ){
			String name = gameList.get(i);
			bw.write(name);
			if( i + 1 < gameList.size() )
				bw.write("\n");
		}
		bw.close();
		
		return file;
	}

	private static List<String> removeSquareBracket(List<String> gameList) {
		
		for( int i = 0; i < gameList.size(); i++ ){
			String name = gameList.get(i);
			name = name.replaceAll(" *\\[\\d+\\] *", " ");
			gameList.set(i, name.trim());
		}
		
		return gameList;
	}

	private static List<String> removeNullAndEmpty(List<String> gameList) {
		String name;
		for( int i = 0; i < gameList.size(); i++ ){
			name = gameList.get(i);
			
			if( name == null || name.trim().isEmpty() ){
				gameList.remove(i--);
			}
		}
		return gameList;
	}
}
