import comom.*;
import interfaces.search.Search;
import objects.Game;
import objects.PriceCharting;
import objects.ProductType;
import objects.Shop;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;


public class Main {
	private static String gameListAddress = "";
	private static String logAddress = "";
	private static TotalsReport totalsReport;

	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
		gameListAddress = Util.getProjectPath() + "/src/main/resources/search.txt";
		File gameList = readFile();
		List<String> ler = Util.ler(gameList);

		ProductType productType = findProductType(ler);
		List<Shop> shops = getAllShopsConfig(productType);

		totalsReport = new TotalsReport( shops );
		totalsReport.generateHeaders();

		for (int i = 0, lerSize = ler.size(); i < lerSize; i++) {
			PrintStream printStream = null;
			try {
				String gameInput = ler.get(i);

				Game game = extractGame(gameInput, true);
				printStream = configurarSaida(game.getName());

				System.out.println("Game: " + game.getName());
				generateHtmlReport(i, game, shops);

				if (i > 0 && i % 10 == 0) {
					System.out.println("Restarting driver... ");
					SeleniumUtil.closeDriver();
				}
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("Restarting driver... ");
				SeleniumUtil.closeDriver();
			}finally {
				if(printStream != null){
					printStream.flush();
					printStream.close();
				}
				System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
				System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
			}

		}
		SeleniumUtil.closeDriver();

		totalsReport.closeAndWriteFile();

/*
		for (int i = 0, lerSize = ler.size(); i < lerSize; i++) {
			String gameInput = ler.get(i);

			Game game = extractGame(gameInput, false);
			System.out.println(game.getName());
		}
*/

        /*
		String gameName = "GameName";
		configurarSaida(gameName);
		generateHtmlReport(gameName, shops);
		*/
	}

	private static ProductType findProductType(List<String> ler) {

		for (String gameFullName : ler) {
			for (ProductType value : ProductType.values()) {
				String stringPattern = value.getStringPattern();
				if(!stringPattern.isBlank() && gameFullName.contains(" " + stringPattern)){
					System.out.println("Product: "+value);
					return value;
				}
			}
		}

		return ProductType.DEFAULT;
	}

	private static Game extractGame(String gameName, boolean parseJson) throws InterruptedException {
		Game game = new Game();

		if(gameName.indexOf("{") > -1 && gameName.lastIndexOf("}") > -1) {
			String json = gameName.substring(gameName.indexOf("{") + 1, gameName.lastIndexOf("}"));
			gameName = gameName.substring(0, gameName.indexOf("{")).trim();

			if(parseJson && !isEmpty(json)) {
				game.setPriceCharting(extractPriceCharting(json));
			}
		}

		game.setName(gameName.replaceAll("[//:-]*", ""));

		return game;
	}

	private static PriceCharting extractPriceCharting(String url) throws InterruptedException {
		System.out.println("Main.extractPriceCharting");
		PriceCharting priceCharting = new PriceCharting(url);

		WebDriver driver = SeleniumUtil.getDriver();

		System.out.println("Connecting to " + url);

		Thread.sleep(100);

		driver.get(url);

		WebElement currency = driver.findElement(By.cssSelector("#dropdown_selected_currency"));
		if(!"BRL".equalsIgnoreCase(currency.getText())){
			System.out.println("\tChanging currency to BRL");
			currency.click();
			Thread.sleep(500);
			driver.findElement(By.cssSelector("a[data-currency='BRL']")).click();
		}

		Thread.sleep(750);

		System.out.println("\tReading prices...");

		String completePrice = driver.findElement(By.cssSelector("#complete_price > span.price.js-price")).getText();
		String newPrice = driver.findElement(By.cssSelector("#new_price > span.price.js-price")).getText();

		priceCharting.setCompletePrice(completePrice.replace("R ", "").trim());
		priceCharting.setNewPrice(newPrice.replace("R ", "").trim());

		System.out.println("\tCIB Price: "+completePrice);
		System.out.println("\tNEW Price: "+newPrice);

		return priceCharting;
	}

	private static PrintStream configurarSaida(String productName) throws IOException {
		logAddress = Util.getReportsPath() + "/" + Util.sanitizeFilename( productName ) + ".log";
		PrintStream fileStream = new MyPrintStream(new FileOutputStream(logAddress, true ), System.out);

		System.setOut(fileStream);
		System.setErr(fileStream);

		return  fileStream;
	}

	public static void generateHtmlReport(int index, Game game, List<Shop> shops) throws URISyntaxException, IOException{
		long time = System.currentTimeMillis();

		GamesReport htmlReport = new GamesReport(game.getName());

		String data = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

		DateFormat df = new SimpleDateFormat("HH:mm");
		Date myDate = new Date(System.currentTimeMillis());
		String hora = df.format(myDate);

		if(game.getPriceCharting() != null) {
			htmlReport.addPriceChartingData(game.getPriceCharting());
		}

		for(Shop shop: shops){
			if(shop.isEnabled()) {
				htmlReport.addReport(shop, shop.searchProduct(game, DefaultFilters.containAllWords()));
			}else{
				System.out.println("Shop: " + shop.getNome() + " disabled");
			}
		}

		htmlReport.addOtherSeekers(game.getName());

		htmlReport.addMetaData(game.getName(), (System.currentTimeMillis() - time), data, hora);

		htmlReport.addLogTab( game.getName() );

		htmlReport.closeAndWriteFile( index, Util.sanitizeFilename(game.getName()) );

		totalsReport.generateContent(shops, game.getName());

		System.out.println("Tempo total: "+(System.currentTimeMillis() - time));
	}

	private static List<Shop> getAllShopsConfig(ProductType productType) {
		return productType.getShops().stream()
				.map(search -> buildShop(search, productType))
				.collect(Collectors.toList());
	}

	private static Shop buildShop(Search search, ProductType productType) {
		return new Shop(search.getName(), search.getMainUrl(), search.getSearchPattern(productType), search);
	}

	private static File readFile() {
		return new File(gameListAddress);
	}
}
