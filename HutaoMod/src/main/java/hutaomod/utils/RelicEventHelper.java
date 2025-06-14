package hutaomod.utils;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hutaomod.modcore.HuTaoMod;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RelicEventHelper {

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(HuTaoMod.makeID(RelicEventHelper.class.getSimpleName()));
    public static String SELECT_TEXT = uiStrings.TEXT[0];
    public static String UPGRADE_TEXT = uiStrings.TEXT[1];
    public static String PURGE_TEXT = uiStrings.TEXT[2];
    public static String OBTAIN_TEXT = uiStrings.TEXT[3];
    public static String TRANSFORM_TEXT = uiStrings.TEXT[4];
    public static String REWARD_TEXT = uiStrings.TEXT[5];
    public static String DISCARD_TEXT = uiStrings.TEXT[6];

    public static void upgradeCards(int amount) {
        int count = 0;
        List<String> upgradedCards = new ArrayList<>();
        List<AbstractCard> list = AbstractDungeon.player.masterDeck.group;
        Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));

        for (AbstractCard c : list) {
            if (c.canUpgrade() && !upgradedCards.contains(c.cardID)) {
                upgradedCards.add(c.cardID);
                c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);

                ++count;
                if (count <= 20) {
                    float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                    float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                    AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineEffect(x, y));
                }

                if (count >= amount) {
                    break;
                }
            }
        }
    }

    public static void upgradeCards(AbstractCard... card) {
        for (AbstractCard c : card) {
            if (c.canUpgrade()) {
                c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineEffect(x, y));
            }
        }
    }

    public static void gainCards(AbstractCard... cards) {
        if (cards.length == 0) {
            HuTaoMod.logger.error("RELIC_EVENT_HELPER: No cards to gain.");
            return;
        } else if (cards.length == 1)
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cards[0], (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        else if (cards.length <= 5) {
            float x = 0;
            for (AbstractCard card : cards) {
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (float) Settings.WIDTH / 6.0F + x, (float) Settings.HEIGHT / 2.0F));
                x += (float) Settings.WIDTH / 6.0F;
            }
        } else {
            for (AbstractCard card : cards) {
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, Settings.WIDTH * MathUtils.random(0.2f, 0.8f), Settings.HEIGHT * MathUtils.random(0.2f, 0.8f)));
            }
        }
    }

    public static void purgeCards(AbstractCard... cards) {
        if (cards.length == 0) {
            HuTaoMod.logger.error("RELIC_EVENT_HELPER: No cards to purge.");
            return;
        } else if (cards.length == 1) {
            AbstractDungeon.topLevelEffectsQueue.add(new PurgeCardEffect(cards[0], (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        } else if (cards.length <= 5) {
            float x = 0;
            for (AbstractCard card : cards) {
                AbstractDungeon.topLevelEffectsQueue.add(new PurgeCardEffect(card, (float) Settings.WIDTH / 6.0F + x, (float) Settings.HEIGHT / 2.0F));
                x += (float) Settings.WIDTH / 6.0F;
            }
        } else {
            for (AbstractCard card : cards) {
                AbstractDungeon.topLevelEffectsQueue.add(new PurgeCardEffect(card, Settings.WIDTH * MathUtils.random(0.2f, 0.8f), Settings.HEIGHT * MathUtils.random(0.2f, 0.8f)));
            }
        }
        AbstractDungeon.player.masterDeck.group.removeAll(Arrays.asList(cards));
    }

    public static void gainRelics(int amount, Predicate<AbstractRelic> predicate) {
        List<AbstractRelic> relics = getRelics(amount, predicate);
        for (AbstractRelic relic : relics) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relic);
        }
    }

    public static List<AbstractRelic> getRelics(int amount, Predicate<AbstractRelic> predicate) {
        List<AbstractRelic> relics = new ArrayList<>();
        for (int i = 0, j = 0; i < amount && j < 99; ++i, ++j) {
            AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
            if (!predicate.test(r)) {
                --i;
                addRelicToPool(r);
            } else {
                relics.add(r);
            }
        }
        return relics;
    }

    public static void gainRelics(AbstractRelic... relics) {
        for (AbstractRelic relic : relics) {
            if (relic == null) {
                HuTaoMod.logger.error("RELIC_EVENT_HELPER: Null relic to gain.");
                continue;
            }
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relic);
        }
    }

    public static void gainRelics(String... relicIDs) {
        for (String relicId : relicIDs) {
            RelicEventHelper.removeRelicFromPool(relicId);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), RelicLibrary.getRelic(relicId).makeCopy());
        }
    }

    public static void removeRelicFromPool(String relicID) {
        AbstractDungeon.commonRelicPool.remove(relicID);
        AbstractDungeon.uncommonRelicPool.remove(relicID);
        AbstractDungeon.rareRelicPool.remove(relicID);
    }

    public static void addRelicToPool(AbstractRelic relic) {
        switch (relic.tier) {
            case COMMON:
                AbstractDungeon.commonRelicPool.add(relic.relicId);
                break;
            case UNCOMMON:
                AbstractDungeon.uncommonRelicPool.add(relic.relicId);
                break;
            case RARE:
                AbstractDungeon.rareRelicPool.add(relic.relicId);
                break;
            case SHOP:
                AbstractDungeon.shopRelicPool.add(relic.relicId);
                break;
            case BOSS:
                AbstractDungeon.bossRelicPool.add(relic.relicId);
                break;
        }
    }
}
