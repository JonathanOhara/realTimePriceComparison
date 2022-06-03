import com.opencsv.bean.CsvToBeanBuilder;
import comom.*;
import interfaces.search.Search;
import interfaces.search.shops.*;
import objects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class GameyeToPriceCharting {
	private static final String FILE_NAME = "06_01_2022_ge_collection.csv";
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		String gameyeCsvFile = Util.getProjectPath() + "/src/main/resources/gameye/" + FILE_NAME;

		List<GameyeObject> beans = new CsvToBeanBuilder(new FileReader(gameyeCsvFile))
				.withType(GameyeObject.class).build().parse();

		System.out.println(beans);

		List<String> output = beans.stream().
			map(gameye -> {
				String title = gameye.getTitle();
				String platform = gameye.getPlatform();
				String state = gameye.getOwnership();

				return PriceChartingStringNormalizer.generateProductOutput(title, platform, state);
			}).collect(Collectors.toList());

		generateOutput(String.join("\n", output));
	}

	private static void generateOutput(String output) throws IOException {
		File priceChartingDir = new File( Util.getPriceChartingPath() );
		if (!priceChartingDir.exists()) {
			priceChartingDir.mkdir();
		}

		String csvDir;
		csvDir = priceChartingDir.getAbsoluteFile() + "/priceCharting.txt";

		File file = new File( csvDir );
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(output.toString());

		bw.flush();
		bw.close();

	}
}
