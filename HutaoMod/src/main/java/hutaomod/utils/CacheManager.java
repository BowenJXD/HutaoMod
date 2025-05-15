package hutaomod.utils;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.debuffs.SiPower;

import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Used to cache conditions within one single update, to avoid repeated calculations.
 * Realised based on CardCrawlGame.playtime.
 */
public class CacheManager {
    public static float cachedPlayTime;

    public static HashMap<Key, Supplier<Integer>> integerSuppliers = new HashMap<Key, Supplier<Integer>>() {
        {
            put(Key.PLAYER_SI, () -> {
                return ModHelper.getPowerCount(AbstractDungeon.player, SiPower.POWER_ID);
            });
        }
    };
    public static HashMap<Key, Integer> integerCaches = new HashMap<>();

    public static HashMap<Key, BooleanSupplier> booleanSuppliers = new HashMap<Key, BooleanSupplier>() {
        {
            put(Key.DYING, () -> {
                return SiPower.isDying(ModHelper.getPowerCount(AbstractDungeon.player, SiPower.POWER_ID));
            });
        }
    };
    public static HashMap<Key, Boolean> booleanCaches = new HashMap<>();

    public static int getInteger(Key key) {
        if (!integerSuppliers.containsKey(key)) {
            HuTaoMod.logger.error("CachedCondition: Integer Key not found: {}", key);
            return 0;
        }
        if (CardCrawlGame.playtime != cachedPlayTime) {
            cachedPlayTime = CardCrawlGame.playtime;
            integerSuppliers.forEach((k, v) -> integerCaches.put(k, v.get()));
        }
        return integerCaches.get(key);
    }

    public static boolean getBoolean(Key key) {
        if (!booleanSuppliers.containsKey(key)) {
            HuTaoMod.logger.error("CachedCondition: Boolean Key not found: {}", key);
            return false;
        }
        if (CardCrawlGame.playtime != cachedPlayTime) {
            cachedPlayTime = CardCrawlGame.playtime;
            booleanSuppliers.forEach((k, v) -> booleanCaches.put(k, v.getAsBoolean()));
        }
        return booleanCaches.get(key);
    }

    public enum Key {
        // Integers
        PLAYER_SI,
        // Booleans
        DYING
    }
}
