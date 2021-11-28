import comom.Keys;
import comom.Util;
import objects.Product;
import objects.Shop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class TotalsReport {

	private StringBuilder totalsPerGameContent = null;
	private StringBuilder totalsPerShopContent = null;
	
	private Map<String, List<Product>> productsByShop = null; 

	public TotalsReport(List<Shop> shops) {
		totalsPerGameContent = new StringBuilder();
		totalsPerShopContent = new StringBuilder();
		
		productsByShop = new HashMap<String, List<Product>>();
		
		for(Shop shop : shops){
			productsByShop.put( shop.getNome(), new ArrayList<Product>() );
		}
	}

	public void generateHeaders(){
		totalsPerGameContent.append("\"Loja\"").append(Keys.CSV_SEPARATOR).
				append("\"Name Searched\"").append(Keys.CSV_SEPARATOR).
				append("\"Name Found\"").append(Keys.CSV_SEPARATOR).
				append("\"Price\"").append(Keys.CSV_SEPARATOR).
				append("\"Clean Price\"").append(Keys.CSV_SEPARATOR).
				append("\"Price Float\"").append(Keys.CSV_SEPARATOR).
				append("\"URL\"\n");
		
		totalsPerShopContent.append("\"Loja\"").append(Keys.CSV_SEPARATOR).
					  append("\"Quantidade\"\n");
	}
	
	public void generateContent(List<Shop> shops, String nameToSearch){
		NumberFormat z = NumberFormat.getCurrencyInstance();
		for(Shop shop : shops){

			if( shop.getProducts() != null ){
				for( Product product : shop.getProducts() ){
					totalsPerGameContent.append( "\"" ).append( shop.getNome() ).append( "\"" ).append(Keys.CSV_SEPARATOR).
							append( "\"" ).append( nameToSearch ).append( "\"" ).append(Keys.CSV_SEPARATOR).
							append( "\"" ).append( product.getName() ).append( "\"" ).append(Keys.CSV_SEPARATOR).
							append( "\"" ).append( product.getValue() ).append( "\"" ).append(Keys.CSV_SEPARATOR).
							append( "\"" ).append( z.format( product.getFloatValue() ) ).append( "\"" ).append(Keys.CSV_SEPARATOR).
							append( "\"" ).append( product.getFloatValue() ).append( "\"" ).append(Keys.CSV_SEPARATOR).
							append( "\"" ).append( product.getUrl() ).append( "\"\n" );
					
					productsByShop.get( shop.getNome() ).add(product);
				}
			}else{
				totalsPerGameContent.append( "\"" ).append( shop.getNome() ).append( "\"" ).append(Keys.CSV_SEPARATOR).
					append( "\"" ).append( nameToSearch ).append( "\"" ).append(Keys.CSV_SEPARATOR).
					append( "\"" ).append( "-" ).append( "\"" ).append(Keys.CSV_SEPARATOR).
					append( "\"" ).append( "-" ).append( "\"" ).append(Keys.CSV_SEPARATOR).
					append( "\"" ).append( z.format( 9999.99f ) ).append( "\"" ).append(Keys.CSV_SEPARATOR).
					append( "\"" ).append( 9999.99f ).append( "\"" ).append(Keys.CSV_SEPARATOR).
					append( "\"" ).append( shop.getSearchPattern() ).append( "\"\n" );
			}
		}
	}
	
	private void computeShopsTotals() {
		for( Entry<String, List<Product>> entry : productsByShop.entrySet() ){
			totalsPerShopContent.append( "\"" ).append( entry.getKey()).append( "\"" ).append(Keys.CSV_SEPARATOR).
						  append( "\"" ).append( entry.getValue().size() ).append( "\"\n" );
		}
		
	}
	
	public void closeAndWriteFile() throws IOException{
		
		File reportsDir = new File( Util.getReportsPath() ); 
		if (!reportsDir.exists()) {
			reportsDir.mkdir();
		}
		
		File dir = new File(reportsDir.getAbsolutePath() + "/Totals"); 
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		computeShopsTotals();
		
		generateGamesTotalFile(dir);
		generateShopsTotalFile(dir);
	}

	private void generateGamesTotalFile(File dir) throws IOException {
		String csvDir;
		csvDir = dir.getAbsoluteFile() + "/Games_Total.csv";
		
		File file = new File( csvDir );
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(totalsPerGameContent.toString());
		
		bw.flush();
		bw.close();
	}
	
	private void generateShopsTotalFile(File dir) throws IOException {
		String csvDir;
		csvDir = dir.getAbsoluteFile() + "/Shops_Total.csv";
		
		File file = new File( csvDir );
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(totalsPerShopContent.toString());
		
		bw.flush();
		bw.close();
	}
}
