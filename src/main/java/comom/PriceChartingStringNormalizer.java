package comom;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PriceChartingStringNormalizer {

    private static Map<String, String> titleMap;
    private static Map<String, String> regionMap;
    private static Map<String, String> platformMap;

    private PriceChartingStringNormalizer(){}

    static{
        titleMap = new HashMap<>();
        titleMap.put("Arietta of Spirits:Switch", "Arietta of Spirits Red Edition");
        titleMap.put("Baldur's Gate and Baldur's Gate II: Enhanced Editions:Switch", "Baldur's Gate 1 & 2 Enhanced Edition");
        titleMap.put("Bayonetta 2:Switch", "Bayonetta 2 + Bayonetta");
        titleMap.put("Legend of Mana:Switch", "Legend Of Mana Remastered");
        titleMap.put("Little Town Hero:Switch", "Little Town Hero [Big Idea Edition]");
        titleMap.put("Shantae Half-Genie Hero Ultimate Edition [Day One]:Switch", "Shantae Half-Genie Hero Ultimate Edition");
        titleMap.put("Shin Megami Tensei lll: Nocturne HD Remaster:Switch", "Shin Megami Tensei III: Nocturne HD Remaster");
        titleMap.put("Sword Art Online FATAL BULLET Complete Edition:Switch", "Sword Art Online: Fatal Bullet");
        titleMap.put("Wargroove:Switch", "Wargroove Deluxe Edition");
        titleMap.put("Unrailed! (SRG#49):Switch", "Unrailed");

        titleMap.put("Harvest Moon 3D: The Tale of Two Towns:3DS", "Harvest Moon: The Tale Of Two Towns");

        titleMap.put("Ninjatown:NDS", "Ninja Town");

        titleMap.put("PixelJunk Monsters Deluxe:PSP", "Pixel Junk Monsters Deluxe");
        titleMap.put("Sid Meier's Pirates!:PSP", "Sid Meiers Pirates Live The Life");
        titleMap.put("Super Robot Wars MX Portable:PSP", "Super Robot Taisen MX Portable (PSP the Best)");


        regionMap = new HashMap<>();
        regionMap.put("A Short Hike:Switch", "PAL");
        regionMap.put("Arietta of Spirits:Switch", "PAL");
        regionMap.put("Astral Chain:Switch", "PAL");
        regionMap.put("Brigandine: The Legend of Runersia:Switch", "PAL");
        regionMap.put("Blasphemous: Deluxe Edition:Switch", "PAL");
        regionMap.put("Bravely Default II:Switch", "PAL");
        regionMap.put("Cadence of Hyrule: Crypt of the NecroDancer featuring The Legend of Zelda:Switch", "PAL");
        regionMap.put("CrossCode:Switch", "PAL");
        regionMap.put("Crysis Remastered:Switch", "PAL");
        regionMap.put("Daemon X Machina:Switch", "PAL");
        regionMap.put("Deadly Premonition 2: A Blessing in Disguise:Switch", "PAL");
        regionMap.put("Disgaea 4 Complete+:Switch", "PAL");
        regionMap.put("Dragon Quest Builders:Switch", "PAL");
        regionMap.put("Fire Emblem:Three Houses:Switch", "PAL");
        regionMap.put("Heaven Dust Collection:Switch", "PAL");
        regionMap.put("Kirby and the Forgotten Land:Switch", "PAL");
        regionMap.put("Maglam Lord", "PAL");
        regionMap.put("Metroid Dread:Switch", "PAL");
//        regionMap.put("Monster Harvest:Switch", "PAL");
        regionMap.put("Monster Hunter Rise:Switch", "PAL");
//        regionMap.put("Rune Factory 4 Special:Switch", "PAL");
        regionMap.put("Short Hike:Switch", "PAL");
        regionMap.put("Skul:The Hero Slayer:Switch", "PAL");
        regionMap.put("Souldiers:Switch", "PAL");
        regionMap.put("Stardew Valley:Switch", "PAL");
        regionMap.put("Sword Art Online FATAL BULLET Complete Edition:Switch", "PAL");
        regionMap.put("The Touryst:Switch", "PAL");
        regionMap.put("Titan Quest:Switch", "PAL");
        regionMap.put("Travis Strikes Again: No More Heroes:Switch", "PAL");
        regionMap.put("Unrailed! (SRG#49):Switch", "PAL");
        regionMap.put("Wargroove:Switch", "PAL");
        regionMap.put("Ys Origin:Switch", "PAL");
        regionMap.put("Ys VIII: Lacrimosa of DANA:Switch", "PAL");
        regionMap.put("Ys IX: Monstrum Nox - Pact Edition:Switch", "PAL");
        regionMap.put("Valkyria Chronicles 4:Switch", "PAL");

        regionMap.put("Digimon World: Next Order:PS Vita", "JP");
        regionMap.put("The Legend of Heroes: Trails of Cold Steel:PS Vita", "PAL");
        regionMap.put("Natural Doctrine:PS Vita", "PAL");

        regionMap.put("Digimon Adventure:PSP", "JP");
        regionMap.put("Digimon World Re:Digitize:PSP", "JP");
        regionMap.put("Gundam Battle Royale:PSP", "JP");
        regionMap.put("Grand Theft Auto: Vice City Stories:PSP", "PAL ");
        regionMap.put("Super Robot Wars MX Portable:PSP", "JP");
        regionMap.put("Warhammer 40,000: Squad Command:PSP", "PAL");

        regionMap.put("The Legend of Zelda: Spirit Tracks:NDS", "PAL");


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

    private static String getPriceChartingTitle(String title, String platform) {
        return titleMap.getOrDefault(title + ":" + platform, title);
    }

    private static String getPriceChartingPlatform(String platform) {
        return platformMap.getOrDefault(platform, platform);
    }

    private static Optional<String> getRegionByTitle(String title, String platform) {
        return Optional.ofNullable(regionMap.get(title + ":" + platform));
    }

}
