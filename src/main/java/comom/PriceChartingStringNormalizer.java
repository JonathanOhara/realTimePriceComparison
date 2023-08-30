package comom;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

public class PriceChartingStringNormalizer {

    private static Map<String, String> titleMap;
    private static Map<String, String> regionMap;
    private static Map<String, String> platformMap;
    private static Map<String, String> countryMap;

    private static Set<String> titleMapChecker = new HashSet<>();
    private static Set<String> regionMapChecker = new HashSet<>();


    private PriceChartingStringNormalizer(){}

    static{
        createTitleReplaceMap();
        createRegionMap();
        createPlatformNameMap();
        createCountryMap();
    }

    private static void createCountryMap() {
        countryMap = new HashMap<>();

        countryMap.put("United States of America", "");
        countryMap.put("United Kingdom", "PAL");
        countryMap.put("Japan", "JP");
        countryMap.put("China", "");
        countryMap.put("World", "");
    }

    private static void createTitleReplaceMap() {
        titleMap = new HashMap<>();

        titleMap.put("Baldur's Gate and Baldur's Gate II: Enhanced Editions:Nintendo Switch", "Baldur's Gate 1 & 2 Enhanced Edition");
        titleMap.put("Bayonetta 2:Nintendo Switch", "Bayonetta 2 + Bayonetta");
        titleMap.put("Legend of Mana:Nintendo Switch", "Legend Of Mana Remastered");
        titleMap.put("Little Town Hero:Nintendo Switch", "Little Town Hero [Big Idea Edition]");
        titleMap.put("Pokémon Scarlet & Pokémon Violet Dual Pack Steelbook Edition:Nintendo Switch", "Pokemon Scarlet & Violet Double Pack [SteelBook Edition]");
        titleMap.put("Shin Megami Tensei lll: Nocturne HD Remaster:Nintendo Switch", "Shin Megami Tensei III: Nocturne HD Remaster");
        titleMap.put("Sword Art Online FATAL BULLET Complete Edition:Nintendo Switch", "Sword Art Online: Fatal Bullet");
        titleMap.put("Wargroove:Nintendo Switch", "Wargroove Deluxe Edition");
        titleMap.put("Unrailed! (SRG#49):Nintendo Switch", "Unrailed");

        titleMap.put("Harvest Moon 3D: The Tale of Two Towns:Nintendo 3DS", "Harvest Moon: The Tale Of Two Towns");
        titleMap.put("Metal Gear Solid: Snake Eater 3D:Nintendo 3DS", "Metal Gear Solid 3D: Snake Eater");

        titleMap.put("Ninjatown:Nintendo DS", "Ninja Town");

        titleMap.put("Monster Hunter Freedom 2 + Monster Hunter Freedom Unite UMD Dual Pack:Sony PSP", "Monster Hunter [Dual Pack]");
        titleMap.put("PixelJunk Monsters Deluxe:Sony PSP", "Pixel Junk Monsters Deluxe");
        titleMap.put("Sid Meier's Pirates!:Sony PSP", "Sid Meiers Pirates Live The Life");
        titleMap.put("Super Robot Wars MX Portable:Sony PSP", "Super Robot Taisen MX Portable (PSP the Best)");

        titleMap.put("Hyperdimension Neptunia Re;Birth1:Sony PS Vita", "Hyperdimension Neptunia Re;Birth 1");
        titleMap.put("Hyperdimension Neptunia Re;Birth3: V Generation:Sony PS Vita", "Hyperdimension Neptunia Re;Birth 3: V Generation");
    }
    
    private static void createRegionMap() {
        regionMap = new HashMap<>();

        regionMap.put("Crysis Remastered:Nintendo Switch", "PAL");
        regionMap.put("CRYSTAR:Nintendo Switch", "PAL");
        regionMap.put("Daemon X Machina:Nintendo Switch", "PAL");
        regionMap.put("Darksiders: Warmastered Edition:Nintendo Switch", "PAL");
        regionMap.put("Labyrinth Of Refrain: Coven Of Dusk:Nintendo Switch", "");
        regionMap.put("Langrisser I & II:Nintendo Switch", "PAL");
        regionMap.put("Maglam Lord:Nintendo Switch", "PAL");
        regionMap.put("Monster Harvest:Nintendo Switch", "PAL");
        regionMap.put("No More Heroes 1+2:Nintendo Switch", "Asian English");
        regionMap.put("Record of Lodoss War: Deedlit in Wonder Labyrinth:Nintendo Switch", "");
        regionMap.put("Rune Factory 4 Special:Nintendo Switch", "PAL");
        regionMap.put("Skul: The Hero Slayer:Nintendo Switch", "PAL");
        regionMap.put("Titan Quest:Nintendo Switch", "PAL");
        regionMap.put("Ultra Kaiju Monster Rancher:Nintendo Switch", "Asian English");
        regionMap.put("Valkyria Chronicles 4:Nintendo Switch", "PAL");
        regionMap.put("Wargroove:Nintendo Switch", "PAL");
        regionMap.put("Ys Origin:Nintendo Switch", "PAL");

//        regionMap.put(":Sony PS Vita", "JP");

//        regionMap.put(":Sony PSP", "JP");

//        regionMap.put(":Nintendo DS", "PAL");

    }

    private static void createPlatformNameMap() {
        platformMap = new HashMap<>();

        platformMap.put("NDS", "Nintendo DS");
        platformMap.put("PS Vita", "Playstation Vita");
        platformMap.put("Switch", "Nintendo Switch");
    }

    public static String generateProductOutput(final String title, final String country, final String platform, final String state){

        Optional<String> optionalRegion = getRegionByCountryOrTitle(title, country, platform);

        StringBuilder priceChartingProduct = new StringBuilder();

        priceChartingProduct.append(getPriceChartingTitle(title, platform)).append(" ");
        optionalRegion.ifPresent(region -> priceChartingProduct.append(region).append(" "));
        priceChartingProduct.append(getPriceChartingPlatform(platform)).append(" ");
        priceChartingProduct.append(state);

        return priceChartingProduct.toString();
    }

    public static void debugPrint() {

        Set<String> diff = new HashSet<>(CollectionUtils.removeAll(titleMap.keySet(), titleMapChecker));

        if(!diff.isEmpty()){
            System.err.println("TitleMap diff");
            System.err.println(diff);
        }

        diff = new HashSet<>(CollectionUtils.removeAll(regionMap.keySet(), regionMapChecker));

        if(!diff.isEmpty()){
            System.err.println("regionMap diff");
            System.err.println(diff);
        }
    }

    private static String getPriceChartingTitle(String title, String platform) {
        String key = title + ":" + platform;
        if(titleMap.containsKey(key)){
            titleMapChecker.add(key);
        }
        return titleMap.getOrDefault(key, title);
    }

    private static String getPriceChartingPlatform(String platform) {
        return platformMap.getOrDefault(platform, platform);
    }

    private static Optional<String> getRegionByCountryOrTitle(String title, String country, String platform) {
        String key = title + ":" + platform;

        if(regionMap.containsKey(key)){
            regionMapChecker.add(key);
        }

        return Optional.ofNullable(regionMap.getOrDefault(key, countryMap.get(country)));
    }

}
