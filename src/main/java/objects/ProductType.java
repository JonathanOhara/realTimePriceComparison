package objects;

import interfaces.search.Search;
import interfaces.search.shops.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum ProductType {
    DEFAULT(),
    NDS("ds", ProductType::getDSShops),
    N3DS("3ds", ProductType::get3DSShops),
    PSP("psp", ProductType::getPSPShops),
    PSVITA("vita", ProductType::getVitaShops),
    SWITCH("switch", ProductType::getAllShops);

    private String stringPattern = "";
    private Supplier<List<Search>> shopsSupplier;

    ProductType() {
        this.shopsSupplier = ProductType::getAllShops;
    }
    ProductType(String stringPattern, Supplier<List<Search>> shopsSupplier) {
        this.stringPattern = stringPattern;
        this.shopsSupplier = shopsSupplier;
    }

    public String getStringPattern() {
        return stringPattern;
    }

    public List<Search> getShops(){
        return shopsSupplier.get();
    }

    protected static List<Search> getDebugShops(){
        List<Search> searches = new ArrayList<>();

        searches.add(new AmazonBRSearch());

        return searches;
    }

     protected static List<Search> getAllShops(){
         List<Search> searches = new ArrayList<>();

         searches.add(new AmazonBRSearch());
         searches.add(new AmericanasSearch());
         searches.add(new SubmarinoSearch());
         searches.add(new SoubaratoSearch());
         searches.add(new ShopTimeSeleniumSearch());
         searches.add(new CarrefourSeleniumSearch());
         searches.add(new FastShopSeleniumSearch());

         searches.add(new MagazineLuizaSeleniumSearch());
         searches.add(new KabumSeleniumSearch());
         searches.add(new NetShoesSeleniumSearch());

         searches.add(new CasasBahiaSeleniumSearch());
         searches.add(new ExtraSeleniumSearch());
         searches.add(new PontoFrioSeleniumSearch());

         searches.add(new RiHappySeleniumSearch());
         searches.add(new ShopBSearch());

         searches.add(new AtacadoDosJogosSearch());
         searches.add(new CarvalhoGamesSearch());
         searches.add(new BigBoyGamesSearch());

         searches.add(new TrilogyNintendoSearch());

         searches.add(new GTAGamesSearch());
         searches.add(new IzzyGamesSearch());
         searches.add(new BlueWavesGamesSearch());

         searches.add(new MercadoLivreSeleniumSearch());
         searches.add(new ShopeeSeleniumSearch());

         searches.add(new AmazonUSSearch());
         searches.add(new FuturisticGamesSeleniumSearch());
         searches.add(new VideoGamesPlusSearch());
         searches.add(new PlayAsiaSeleniumSearch());
         searches.add(new EbaySeleniumSearch());
         //easterland
         //cdrstation

         return searches;
    }

    protected static List<Search> getDSShops(){
        List<Search> searches = new ArrayList<>();

        searches.add(new AmazonBRSearch());
        searches.add(new AmericanasSearch());
        searches.add(new SubmarinoSearch());
        searches.add(new SoubaratoSearch());
        searches.add(new ShopTimeSeleniumSearch());
        searches.add(new CarrefourSeleniumSearch());
        searches.add(new FastShopSeleniumSearch());

        searches.add(new MagazineLuizaSeleniumSearch());
        searches.add(new KabumSeleniumSearch());
        searches.add(new NetShoesSeleniumSearch());

        searches.add(new CasasBahiaSeleniumSearch());
        searches.add(new ExtraSeleniumSearch());
        searches.add(new PontoFrioSeleniumSearch());

        searches.add(new RiHappySeleniumSearch());

        searches.add(new CarvalhoGamesSearch());
        searches.add(new BigBoyGamesSearch());

        searches.add(new MercadoLivreSeleniumSearch());
        searches.add(new ShopeeSeleniumSearch());

        searches.add(new AmazonUSSearch());
        searches.add(new FuturisticGamesSeleniumSearch());
        searches.add(new VideoGamesPlusSearch());
        searches.add(new PlayAsiaSeleniumSearch());
        searches.add(new EbaySeleniumSearch());

        return searches;
    }

    protected static List<Search> get3DSShops(){
        List<Search> searches = new ArrayList<>();

        searches.add(new AmazonBRSearch());
        searches.add(new AmericanasSearch());
        searches.add(new SubmarinoSearch());
        searches.add(new SoubaratoSearch());
        searches.add(new ShopTimeSeleniumSearch());
        searches.add(new CarrefourSeleniumSearch());
        searches.add(new FastShopSeleniumSearch());

        searches.add(new MagazineLuizaSeleniumSearch());
        searches.add(new KabumSeleniumSearch());
        searches.add(new NetShoesSeleniumSearch());

        searches.add(new CasasBahiaSeleniumSearch());
        searches.add(new ExtraSeleniumSearch());
        searches.add(new PontoFrioSeleniumSearch());

        searches.add(new RiHappySeleniumSearch());
        searches.add(new ShopBSearch());

        searches.add(new CarvalhoGamesSearch());
        searches.add(new BigBoyGamesSearch());

        searches.add(new GTAGamesSearch());
        searches.add(new BlueWavesGamesSearch());

        searches.add(new MercadoLivreSeleniumSearch());
        searches.add(new ShopeeSeleniumSearch());

        searches.add(new AmazonUSSearch());
        searches.add(new FuturisticGamesSeleniumSearch());
        searches.add(new VideoGamesPlusSearch());
        searches.add(new PlayAsiaSeleniumSearch());
        searches.add(new EbaySeleniumSearch());

        return searches;
    }

    protected static List<Search> getPSPShops(){
        List<Search> searches = new ArrayList<>();

        searches.add(new AmazonBRSearch());
        searches.add(new AmericanasSearch());
        searches.add(new SubmarinoSearch());
        searches.add(new SoubaratoSearch());
        searches.add(new ShopTimeSeleniumSearch());
        searches.add(new CarrefourSeleniumSearch());
        searches.add(new FastShopSeleniumSearch());

        searches.add(new MagazineLuizaSeleniumSearch());
        searches.add(new KabumSeleniumSearch());
        searches.add(new NetShoesSeleniumSearch());

        searches.add(new CasasBahiaSeleniumSearch());
        searches.add(new ExtraSeleniumSearch());
        searches.add(new PontoFrioSeleniumSearch());

        searches.add(new RiHappySeleniumSearch());
        searches.add(new ShopBSearch());

        searches.add(new MercadoLivreSeleniumSearch());
        searches.add(new ShopeeSeleniumSearch());

        searches.add(new AmazonUSSearch());
        searches.add(new FuturisticGamesSeleniumSearch());
        searches.add(new VideoGamesPlusSearch());
        searches.add(new PlayAsiaSeleniumSearch());
        searches.add(new EbaySeleniumSearch());

        return searches;
    }

    protected static List<Search> getVitaShops(){
        List<Search> searches = new ArrayList<>();

        searches.add(new AmazonBRSearch());
        searches.add(new AmericanasSearch());
        searches.add(new SubmarinoSearch());
        searches.add(new SoubaratoSearch());
        searches.add(new ShopTimeSeleniumSearch());
        searches.add(new CarrefourSeleniumSearch());
        searches.add(new FastShopSeleniumSearch());

        searches.add(new MagazineLuizaSeleniumSearch());
        searches.add(new KabumSeleniumSearch());
        searches.add(new NetShoesSeleniumSearch());

        searches.add(new CasasBahiaSeleniumSearch());
        searches.add(new ExtraSeleniumSearch());
        searches.add(new PontoFrioSeleniumSearch());

        searches.add(new RiHappySeleniumSearch());
        searches.add(new ShopBSearch());

        searches.add(new MercadoLivreSeleniumSearch());
        searches.add(new ShopeeSeleniumSearch());

        searches.add(new AmazonUSSearch());
        searches.add(new FuturisticGamesSeleniumSearch());
        searches.add(new VideoGamesPlusSearch());
        searches.add(new PlayAsiaSeleniumSearch());
        searches.add(new EbaySeleniumSearch());

        return searches;
    }
}
