import comom.*;
import interfaces.search.Search;
import interfaces.search.shops.*;
import objects.Game;
import objects.PriceCharting;
import objects.ProductType;
import objects.Shop;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static objects.ProductType.*;


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
			String gameInput = ler.get(i);

			Game game = extractGame(gameInput, true);
			PrintStream printStream = configurarSaida(game.getName());

			System.out.println("Game: " + game.getName());
			generateHtmlReport(i, game, shops);
			SeleniumUtil.closeDriver();
			printStream.flush();
			printStream.close();
		}

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

			if(parseJson) {
				game.setPriceCharting(extractPriceCharting(json));
			}
		}

		game.setName(gameName.replaceAll("[//:-]*", ""));

		return game;
	}

	private static PriceCharting extractPriceCharting(String url) throws InterruptedException {
		PriceCharting priceCharting = new PriceCharting(url);

		WebDriver driver = SeleniumUtil.getDriver();

		Thread.sleep(750);

		driver.get(url);

		if( !SeleniumUtil.isUsingChromeProfile() ) {
			driver.findElement(By.cssSelector("#dropdown_selected_currency")).click();
			Thread.sleep(500);
			driver.findElement(By.cssSelector("a[data-currency='BRL']")).click();
		}

		Thread.sleep(750);

		String completePrice = driver.findElement(By.cssSelector("#complete_price > span.price.js-price")).getText();
		String newPrice = driver.findElement(By.cssSelector("#new_price > span.price.js-price")).getText();

		priceCharting.setCompletePrice(completePrice.replace("R ", "").trim());
		priceCharting.setNewPrice(newPrice.replace("R ", "").trim());

		System.out.println("CIB Price: "+completePrice);
		System.out.println("NEW Price: "+newPrice);

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

		System.out.println("Product Name: "+game.getName());
		
		GamesReport htmlReport = new GamesReport(game.getName());

		String data = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

		DateFormat df = new SimpleDateFormat("HH:mm");
		Date myDate = new Date(System.currentTimeMillis());
		String hora = df.format(myDate);

		if(game.getPriceCharting() != null) {
			htmlReport.addPriceChartingData(game.getPriceCharting());
		}

		for(Shop shop: shops){
			htmlReport.addReport(shop, shop.searchProduct(game, DefaultFilters.containAllWords() ) );
		}

		htmlReport.addOtherSeekers(game.getName());
		
		htmlReport.addMetaData(game.getName(), (System.currentTimeMillis() - time), data, hora);
		
		htmlReport.addLogTab( game.getName() );
		
		htmlReport.closeAndWriteFile( index, Util.sanitizeFilename(game.getName()) );
		
		totalsReport.generateContent(shops, game.getName());
		
		System.out.println("Tempo total: "+(System.currentTimeMillis() - time));
	}

//	private static List<Shop> getAllShopsConfig(ProductType productType) {
//		List<Shop> shops = new ArrayList<>();
//
//		shops.add(buildShop(new VideoGamesPlusSearch(), productType));
//
//		return shops;
//	}

	private static List<Shop> getAllShopsConfig(ProductType productType) {
		List<Shop> shops = new ArrayList<>();

		shops.add( buildShop( new AmericanasSearch(), productType ) );
//		shops.add( buildShop( new SubmarinoSearch(), productType ) );
//		shops.add( buildShop( new SoubaratoSearch(), productType ) );
//		shops.add( buildShop( new ShopTimeSeleniumSearch(), productType ) );
//		shops.add( buildShop( new CarrefourSeleniumSearch(), productType ) );
//		shops.add( buildShop( new FastShopSeleniumSearch(), productType ) );

		shops.add( buildShop( new KabumSeleniumSearch(), productType ) );

		shops.add( buildShop( new MagazineLuizaSeleniumSearch(), productType ) );
//		shops.add( buildShop( new NetShoesSeleniumSearch(), productType ) );

		if(productType.equals(SWITCH) || productType.equals(PSVITA) || productType.equals(N3DS) || productType.equals(DEFAULT)) {
			shops.add(buildShop(new CasasBahiaSeleniumSearch(), productType));
//			shops.add(buildShop(new ExtraSeleniumSearch(), productType));
//			shops.add(buildShop(new PontoFrioSeleniumSearch(), productType));
		}

//		shops.add( buildShop( new RiHappySeleniumSearch(), productType ) );
		shops.add( buildShop( new ShopBSearch(), productType ) );
		shops.add( buildShop( new AmazonBRSearch(), productType ) );

		shops.add( buildShop( new AtacadoDosJogosSearch(), productType ) );
		shops.add( buildShop( new BigBoyGamesSearch(), productType ) );

		if(productType.equals(N3DS) || productType.equals(NDS) || productType.equals(SWITCH) || productType.equals(DEFAULT)) {
//			shops.add( buildShop( new CogumeloShopSearch(), productType ) );
			shops.add( buildShop( new TrilogyNintendoSearch(), productType) );
		}

		shops.add( buildShop( new GTAGamesSearch(), productType ) );
		shops.add( buildShop( new IzzyGamesSearch(), productType ) );
		shops.add( buildShop( new BlueWavesGamesSearch(), productType ) );

		shops.add( buildShop( new MercadoLivreSeleniumSearch(), productType ) );
		shops.add( buildShop( new ShopeeSeleniumSearch(), productType ) );

		shops.add( buildShop( new VideoGamesPlusSearch(), productType ) );
		shops.add( buildShop( new Shop4BRSearch(), productType ) );
		shops.add( buildShop( new PlayAsiaSeleniumSearch(), productType ) );
		shops.add( buildShop( new EbaySeleniumSearch(), productType ) );

//		shops.add( buildShop( new AmazonUSSearch(), productType ) ); TODO FIX currency conversion
//		shops.add( buildShop( new FuturisticGamesSeleniumSearch(), productType ) );
		//easterland
		//cdrstation

		return shops;
	}

	private static Shop buildShop(Search search, ProductType productType) {
		return new Shop(search.getName(), search.getMainUrl(), search.getSearchPattern(productType), search);
	}

	private static File readFile() {
		return new File(gameListAddress);
	}
}
