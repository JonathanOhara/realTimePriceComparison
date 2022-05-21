package comom;

import com.google.common.net.UrlEscapers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Util {
	
	private static String projectPath = null;
	private static String reportsPath = null;
	private static String priceChartingPath = null;

	static{
		try {
			String data, hora;
			data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			
			DateFormat df = new SimpleDateFormat("HH");
			Date myDate = new Date(System.currentTimeMillis());
			hora = df.format(myDate);
			
			projectPath = new File(".").getCanonicalPath();
			
			File reportFile = new File("./reports/" + data + " " + hora );
			if( !reportFile.exists() ){
				reportFile.mkdir();
			}
			
			reportsPath = reportFile.getCanonicalPath();

			File priceChartingFile = new File("./priceCharting");
			if( !priceChartingFile.exists() ){
				priceChartingFile.mkdir();
			}

			priceChartingPath = priceChartingFile.getCanonicalPath();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static List<String> ler(File file) throws IOException { 
		BufferedReader buffRead = new BufferedReader(new FileReader(file));
		String linha = "";
		List<String> stringList = new ArrayList<String>();
		
		while (true) {
			if (linha == null) break;
			linha = buffRead.readLine();
			if (linha != null) stringList.add(linha);
		}
		
		buffRead.close();
		
		return stringList;
	}
	
	public static String getProjectPath() throws IOException{
		return projectPath;
	}
	
	public static String getReportsPath() throws IOException{
		return reportsPath;
	}

	public static String getPriceChartingPath() throws IOException{
		return priceChartingPath;
	}
	

	public static String stringToUrl(String urlStr){
		return URLEncoder.encode(urlStr);
	}

//	public static Document readUrlDocument(String url, String language) throws IOException{
//		System.out.println(url);
//		Document doc = null;
//
//		try{
//			doc = Jsoup.connect(url)
//					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//					.header("Accept-Language", language)
//					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36 OPR/81.0.4196.37")
//					.ignoreHttpErrors(true)
//					.referrer("http://www.google.com")
//					.timeout(200000)
//					.get();
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.println("Tentando conectar Na Url de outro modo");
//			doc = parseDocument( readUrlNew(url, null) );
//		}
//		return doc;
//	}

	public static Document readUrlDocument(String url) throws IOException{
		Document doc = null;

		try{
			doc = Jsoup.connect(url)
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36 OPR/81.0.4196.37")
//					.header("Accept-Language", "zh-CN,zh;q=0.8")
					.ignoreHttpErrors(true)
					.referrer("http://www.google.com")
					.timeout(200000)
					.get();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Tentando conectar Na Url de outro modo");
			doc = parseDocument( readUrlNew(url, null) );
		}
		return doc;
	}

	public static String readUrlNew(String url, String charset){
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET() // GET is default
				.build();

		HttpResponse<String> response = null;
		try {
			response = client.send(request,
					HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return response.body();
	}

	public static String readUrl(String url, String charset){
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader in = getPageBuffer(url, charset);

			if( in != null){
				String inputLine;
		        while ((inputLine = in.readLine()) != null){
		            builder.append(inputLine);
		        }
		        in.close();
			}
		}catch (MalformedURLException e) { 
			e.printStackTrace();
		}catch (IOException e) {   
			e.printStackTrace();
		}
		
		if( builder.toString().contains("") ){
    		System.out.println("Possivelmente Ocorreu Erro ao ler a pagina");
    	}
		
		return builder.toString();
	}
	
	private static BufferedReader getPageBuffer(String url, String charset)throws IOException {
		URL oracle = null;
		URLConnection yc = null;
		BufferedReader in = null;
		
		for( int i = 0; i < 10; i++ ){
			try{
				oracle = new URL( url );
				yc = oracle.openConnection();
				if( charset == null || charset.isEmpty() ){
					in = new BufferedReader(new InputStreamReader( yc.getInputStream() ));
				}else{
					in = new BufferedReader(new InputStreamReader( yc.getInputStream(), charset ));
				}
				
				break;
			}catch( ConnectException e){
				System.out.println("ConnectException - Tentando novamente["+i+"]: "+url);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
			}catch( UnknownHostException e){
				System.out.println("UnknownHostException - Tentando novamente["+i+"]: "+url);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
			}
			
		}
		
		return in;
	}
	
	public static Document parseDocument(String html){
		Document document = null;
		try{
			document = Jsoup.parse(html);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	public static String prepareUrlMode1(String searchPattern,	String productName) throws MalformedURLException, URISyntaxException {
		return searchPattern.replace(Keys.BUSCA, stringToUrl( productName ) );
	}

	public static String prepareUrlMode2(String searchPattern, String productName) {
		return searchPattern.replace(Keys.BUSCA, UrlEscapers.urlFragmentEscaper().escape(productName) );
	}

	public static String prepareUrlMode3(String searchPattern, String productName) {
		return searchPattern.replace(Keys.BUSCA, productName.replaceAll(" ", "-") );
	}

	public static String sanitizeFilename(String fileName){
		return fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
	}
	
	public static String makeAbsoluteURL(String baseUrl, String individualUrl){
		String absoluteUrl = individualUrl;
		
		if( !individualUrl.contains( baseUrl ) ){
			if(individualUrl.charAt(0) == '/'){
				absoluteUrl = baseUrl + individualUrl.substring(1);	
			}else{
				absoluteUrl = baseUrl + individualUrl;
			}
			
		}
		
		return absoluteUrl;
	}
	
	public static void copyFolder(File src, File dest) throws IOException{
	 
    	if(src.isDirectory()){
 
    		//if directory not exists, create it
    		if(!dest.exists()){
    		   dest.mkdir();
    		   System.out.println("Directory copied from " 
                              + src + "  to " + dest);
    		}
 
    		//list all the directory contents
    		String files[] = src.list();
 
    		for (String file : files) {
    		   //construct the src and dest file structure
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile);
    		}
 
    	}else{
    		//if file, then copy it
    		//Use bytes stream to support all file types
    		InputStream in = new FileInputStream(src);
    		OutputStream out = new FileOutputStream(dest); 
 
    	    byte[] buffer = new byte[1024];
 
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = in.read(buffer)) > 0){
    	    	out.write(buffer, 0, length);
    	    }
 
    	    in.close();
    	    out.close();
    	    System.out.println("File copied from " + src + " to " + dest);
    	}
    }
}
