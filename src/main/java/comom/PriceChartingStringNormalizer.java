package comom;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

public class PriceChartingStringNormalizer {

    private static Map<String, String> titleMap;
    private static Map<String, String> regionMap;
    private static Map<String, String> platformMap;

    private static Set<String> titleMapChecker = new HashSet<>();
    private static Set<String> regionMapChecker = new HashSet<>();


    private PriceChartingStringNormalizer(){}

    static{
        createTitleReplaceMap();
        createRegionMap();
        createPlatformNameMap();
    }

    private static void createTitleReplaceMap() {
        titleMap = new HashMap<>();
        titleMap.put("Arietta of Spirits:Switch", "Arietta of Spirits Red Edition");
        titleMap.put("Baldur's Gate and Baldur's Gate II: Enhanced Editions:Switch", "Baldur's Gate 1 & 2 Enhanced Edition");
        titleMap.put("Bayonetta 2:Switch", "Bayonetta 2 + Bayonetta");
        titleMap.put("Legend of Mana:Switch", "Legend Of Mana Remastered");
        titleMap.put("Little Town Hero:Switch", "Little Town Hero [Big Idea Edition]");
        titleMap.put("Shin Megami Tensei lll: Nocturne HD Remaster:Switch", "Shin Megami Tensei III: Nocturne HD Remaster");
        titleMap.put("Sword Art Online FATAL BULLET Complete Edition:Switch", "Sword Art Online: Fatal Bullet");
        titleMap.put("Wargroove:Switch", "Wargroove Deluxe Edition");
        titleMap.put("Unrailed! (SRG#49):Switch", "Unrailed");

        titleMap.put("Harvest Moon 3D: The Tale of Two Towns:3DS", "Harvest Moon: The Tale Of Two Towns");
        titleMap.put("Metal Gear Solid Snake Eater 3D:3DS", "Metal Gear Solid 3D");

        titleMap.put("Ninjatown:NDS", "Ninja Town");

        titleMap.put("PixelJunk Monsters Deluxe:PSP", "Pixel Junk Monsters Deluxe");
        titleMap.put("Sid Meier's Pirates!:PSP", "Sid Meiers Pirates Live The Life");
        titleMap.put("Super Robot Wars MX Portable:PSP", "Super Robot Taisen MX Portable (PSP the Best)");
    }

    //J-Stars Victory Vs+ Playstation Vita CIB
    //J-Stars Victory VS+ PAL PLaystation vita


    private static void createRegionMap() {
        regionMap = new HashMap<>();
        regionMap.put("A Short Hike:Switch", "PAL");
        regionMap.put("Arietta of Spirits:Switch", "PAL");
        regionMap.put("Blue Reflection: Second Light:Switch", "PAL");
        regionMap.put("Brigandine: The Legend of Runersia:Switch", "PAL");
        regionMap.put("Cadence of Hyrule: Crypt of the NecroDancer featuring The Legend of Zelda:Switch", "PAL");
        regionMap.put("Crisis Core: Final Fantasy VII Reunion:Switch", "PAL");
        regionMap.put("CrossCode:Switch", "PAL");
        regionMap.put("Crysis Remastered:Switch", "PAL");
        regionMap.put("CRYSTAR:Switch", "PAL");
        regionMap.put("Daemon X Machina:Switch", "PAL");
        regionMap.put("Deadly Premonition 2: A Blessing in Disguise:Switch", "PAL");
        regionMap.put("Dragon Quest Builders:Switch", "PAL");
        regionMap.put("Dungeon Defenders: Awakened:Switch", "PAL");
        regionMap.put("Harvestella:Switch", "PAL");
        regionMap.put("Heaven Dust Collection:Switch", "PAL");
        regionMap.put("Langrisser I & II:Switch", "PAL");
        regionMap.put("Maglam Lord:Switch", "PAL");
        regionMap.put("Skul: The Hero Slayer:Switch", "PAL");
        regionMap.put("Souldiers:Switch", "PAL");
        regionMap.put("Source of Madness:Switch", "PAL");
        regionMap.put("Stardew Valley:Switch", "PAL");
        regionMap.put("Sword Art Online FATAL BULLET Complete Edition:Switch", "PAL");
        regionMap.put("The Touryst:Switch", "PAL");
        regionMap.put("Titan Quest:Switch", "PAL");
        regionMap.put("Travis Strikes Again: No More Heroes:Switch", "PAL");
        regionMap.put("Ultra Kaiju Monster Rancher:Switch", "Asian English");
        regionMap.put("Unrailed! (SRG#49):Switch", "PAL");
        regionMap.put("Wargroove:Switch", "PAL");
        regionMap.put("Ys Origin:Switch", "PAL");
        regionMap.put("Ys VIII: Lacrimosa of DANA:Switch", "PAL");
        regionMap.put("Ys IX Monstrum Nox - Pact Edition:Switch", "PAL");
        regionMap.put("Valkyria Chronicles 4:Switch", "PAL");

        regionMap.put("Digimon World: Next Order:PS Vita", "JP");
        regionMap.put("J-Stars Victory Vs+:PS Vita", "PAL");
        regionMap.put("The Legend of Heroes: Trails of Cold Steel:PS Vita", "PAL");
        regionMap.put("Natural Doctrine:PS Vita", "PAL");
        regionMap.put("Tales of Hearts R:PS Vita", "PAL");

        regionMap.put("Digimon Adventure:PSP", "JP");
        regionMap.put("Digimon World Re:Digitize:PSP", "JP");
        regionMap.put("Gundam Battle Royale:PSP", "JP");
        regionMap.put("Grand Theft Auto: Vice City Stories:PSP", "PAL ");
        regionMap.put("Super Robot Wars MX Portable:PSP", "JP");
        regionMap.put("Warhammer 40,000: Squad Command:PSP", "PAL");

        regionMap.put("Dragon Quest IX: Sentinels of the Starry Skies:NDS", "PAL");
        regionMap.put("Guitar Hero: On Tour:NDS", "PAL");
        regionMap.put("Guitar Hero: On Tour Decades:NDS", "PAL");
        regionMap.put("The Legend of Zelda: Spirit Tracks:NDS", "PAL");
    }

    private static void createPlatformNameMap() {
        platformMap = new HashMap<>();
        platformMap.put("NDS", "Nintendo DS");
        platformMap.put("PS Vita", "Playstation Vita");
        platformMap.put("Switch", "Nintendo Switch");
    }

    public static String generateProductOutput(final String title, final String platform, final String state){

        Optional<String> optionalRegion = getRegionByTitle(title, platform);

        StringBuilder priceChartingProduct = new StringBuilder();

        priceChartingProduct.append(getPriceChartingTitle(title, platform)).append(" ");
        optionalRegion.ifPresent(region -> priceChartingProduct.append(region).append(" "));
        priceChartingProduct.append(getPriceChartingPlatform(platform)).append(" ");
        priceChartingProduct.append(state);

        return priceChartingProduct.toString();
    }

    public static void debugPrint() {

        Set<String> diff = new HashSet<>(CollectionUtils.removeAll(titleMap.keySet(), titleMapChecker));

        if(diff.size() > 0){
            System.err.println("TitleMap diff");
            System.err.println(diff);
        }

        diff = new HashSet<>(CollectionUtils.removeAll(regionMap.keySet(), regionMapChecker));

        if(diff.size() > 0){
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

    private static Optional<String> getRegionByTitle(String title, String platform) {
        String key = title + ":" + platform;
        if(regionMap.containsKey(key)){
            regionMapChecker.add(key);
        }
        return Optional.ofNullable(regionMap.get(key));
    }

}
