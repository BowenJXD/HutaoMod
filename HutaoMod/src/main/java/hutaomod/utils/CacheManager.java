package hutaomod.utils;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.cards.HuTaoCard;
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
    public static float integerUpdateTime;
    public static float booleanUpdateTime;

    public static HashMap<Key, Supplier<Integer>> integerSuppliers = new HashMap<Key, Supplier<Integer>>() {
        {
            put(Key.PLAYER_SI, () -> {
                return ModHelper.getPowerCount(AbstractDungeon.player, SiPower.POWER_ID);
            });
            put(Key.YIN_CARDS, () -> {
                return AbstractDungeon.player.hand.group.stream().mapToInt(c -> HuTaoCard.isYin(c) ? 1 : 0).sum();
            });
            put(Key.YANG_CARDS, () -> {
                return AbstractDungeon.player.hand.group.stream().mapToInt(c -> HuTaoCard.isYang(c) ? 1 : 0).sum();
            });
        }
    };
    public static HashMap<Key, Integer> integerCaches = new HashMap<>();

    public static HashMap<Key, BooleanSupplier> booleanSuppliers = new HashMap<Key, BooleanSupplier>() {
        {
            put(Key.DYING, () -> {
                return SiPower.isDying(ModHelper.getPowerCount(AbstractDungeon.player, SiPower.POWER_ID));
            });
            put(Key.HALF_HP, () -> {
                return AbstractDungeon.player.currentHealth <= AbstractDungeon.player.maxHealth / 2;
            });
        }
    };
    public static HashMap<Key, Boolean> booleanCaches = new HashMap<>();

    public static int getInt(Key key) {
        if (!integerSuppliers.containsKey(key)) {
            HuTaoMod.logger.error("CachedCondition: Integer Key not found: {}", key);
            return 0;
        }
        if (CardCrawlGame.playtime != integerUpdateTime) {
            integerUpdateTime = CardCrawlGame.playtime;
            integerSuppliers.forEach((k, v) -> {
                try {
                    integerCaches.put(k, v.get());                
                } catch (Exception e) {
                    HuTaoMod.logger.error("CachedCondition: Error while getting integer key: {}", k);
                    HuTaoMod.logger.error(e);
                }
            });
        }
        if (!integerCaches.containsKey(key)) return 0;
        return integerCaches.get(key);
    }

    public static boolean getBool(Key key) {
        if (!booleanSuppliers.containsKey(key)) {
            HuTaoMod.logger.error("CachedCondition: Boolean Key not found: {}", key);
            return false;
        }
        if (CardCrawlGame.playtime != booleanUpdateTime || !booleanCaches.containsKey(key)) {
            booleanUpdateTime = CardCrawlGame.playtime;
            booleanSuppliers.forEach((k, v) -> {
                try {
                    booleanCaches.put(k, v.getAsBoolean());
                } catch (Exception e) {
                    HuTaoMod.logger.error("CachedCondition: Error while getting boolean key: {}", k);
                    HuTaoMod.logger.error(e);
                }
            });
        }
        if (!booleanCaches.containsKey(key)) return false;
        return booleanCaches.get(key);
    }

    public enum Key {
        // Integers
        PLAYER_SI,
        YIN_CARDS,
        YANG_CARDS,
        // Booleans
        DYING,
        HALF_HP,
    }
}
