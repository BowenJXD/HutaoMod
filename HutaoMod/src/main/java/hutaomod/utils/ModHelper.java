package hutaomod.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import hutaomod.modcore.HuTaoMod;

import java.util.*;
import java.util.function.Predicate;

public class ModHelper {
    public static void addToBotAbstract(Lambda func) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                func.run();
                isDone = true;
            }
        });
    }

    public static void addToTopAbstract(Lambda func) {
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                func.run();
                isDone = true;
            }
        });
    }
    
    public static void addEffectAbstract(Lambda func) {
        addEffectAbstract(func, true);
    }

    public static void addEffectAbstract(Lambda func, boolean topLevel) {
        AbstractGameEffect effect = new AbstractGameEffect() {
            @Override
            public void update() {
                func.run();
                isDone = true;
            }

            @Override
            public void render(SpriteBatch spriteBatch) {
            }

            @Override
            public void dispose() {
            }
        };
        
        if (topLevel) {
            AbstractDungeon.topLevelEffectsQueue.add(effect);
        } else {
            AbstractDungeon.effectsQueue.add(effect);
        }
    }

    public interface Lambda extends Runnable {
    }

    public static int getPowerCount(AbstractCreature creature, String powerID) {
        return creature.hasPower(powerID) ? creature.getPower(powerID).amount : 0;
    }

    public static List<FindResult> findCardsInGroup(Predicate<AbstractCard> predicate, CardGroup group) {
        List<FindResult> result = new ArrayList<>();
        for (AbstractCard card : group.group) {
            if (predicate.test(card)) {
                FindResult findResult = new FindResult();
                findResult.card = card;
                findResult.group = group;
                result.add(findResult);
            }
        }
        return result;
    }

    public static List<FindResult> findCards(Predicate<AbstractCard> predicate, CardGroup.CardGroupType... types) {
        List<FindResult> result = new ArrayList<>();
        List<CardGroup.CardGroupType> typeList = Arrays.asList(types);
        if (typeList.contains(CardGroup.CardGroupType.HAND)) result.addAll(findCardsInGroup(predicate, AbstractDungeon.player.hand));
        if (typeList.contains(CardGroup.CardGroupType.DISCARD_PILE)) result.addAll(findCardsInGroup(predicate, AbstractDungeon.player.discardPile));
        if (typeList.contains(CardGroup.CardGroupType.DRAW_PILE)) result.addAll(findCardsInGroup(predicate, AbstractDungeon.player.drawPile));
        if (typeList.contains(CardGroup.CardGroupType.EXHAUST_PILE)) result.addAll(findCardsInGroup(predicate, AbstractDungeon.player.exhaustPile));
        if (typeList.contains(CardGroup.CardGroupType.MASTER_DECK)) result.addAll(findCardsInGroup(predicate, AbstractDungeon.player.masterDeck));
        return result;
    }

    public static List<FindResult> findCards(Predicate<AbstractCard> predicate, boolean shuffle) {
        List<FindResult> result = findCards(predicate, CardGroup.CardGroupType.HAND, CardGroup.CardGroupType.DISCARD_PILE,
                CardGroup.CardGroupType.DRAW_PILE, CardGroup.CardGroupType.EXHAUST_PILE);
        if (shuffle) {
            Collections.shuffle(result, new java.util.Random(AbstractDungeon.cardRandomRng.randomLong()));
        }
        return result;
    }

    public static List<FindResult> findCards(Predicate<AbstractCard> predicate) {
        return findCards(predicate, CardGroup.CardGroupType.HAND, CardGroup.CardGroupType.DISCARD_PILE,
                CardGroup.CardGroupType.DRAW_PILE, CardGroup.CardGroupType.EXHAUST_PILE);
    }
    
    public static List<FindResult> findCards(String cardID) {
        return findCards(c -> Objects.equals(c.cardID, cardID));
    }

    public static class FindResult {
        public AbstractCard card;
        public CardGroup group;
    }

    public static AbstractMonster betterGetRandomMonster() {
        return getRandomMonster(ModHelper::check, true);
    }
    
    public static boolean check(AbstractCreature m) {
        return !(m == null || m.isDying || m.isEscaping || m.halfDead || m.currentHealth <= 0);
    }

    public static AbstractMonster getRandomMonster(Predicate<AbstractMonster> predicate, boolean aliveOnly) {
        MonsterGroup group = AbstractDungeon.getCurrRoom().monsters;
        Random rng = AbstractDungeon.cardRandomRng;
        if (group.areMonstersBasicallyDead()) {
            return null;
        } else {
            ArrayList tmp;
            Iterator var5;
            AbstractMonster m;
            if (predicate == null) {
                if (aliveOnly) {
                    tmp = new ArrayList();
                    var5 = group.monsters.iterator();

                    while (var5.hasNext()) {
                        m = (AbstractMonster) var5.next();
                        if (check(m)) {
                            tmp.add(m);
                        }
                    }

                    if (tmp.size() <= 0) {
                        return null;
                    } else {
                        return (AbstractMonster) tmp.get(rng.random(0, tmp.size() - 1));
                    }
                } else {
                    return (AbstractMonster) group.monsters.get(rng.random(0, group.monsters.size() - 1));
                }
            } else if (group.monsters.size() == 1) {
                if (predicate.test((AbstractMonster) group.monsters.get(0))) {
                    return (AbstractMonster) group.monsters.get(0);
                } else {
                    return null;
                }
            } else if (aliveOnly) {
                tmp = new ArrayList();
                var5 = group.monsters.iterator();

                while (var5.hasNext()) {
                    m = (AbstractMonster) var5.next();
                    if (!m.halfDead && !m.isDying && !m.isEscaping && predicate.test(m)) {
                        tmp.add(m);
                    }
                }

                if (tmp.size() == 0) {
                    return null;
                } else {
                    return (AbstractMonster) tmp.get(rng.random(0, tmp.size() - 1));
                }
            } else {
                tmp = new ArrayList();
                var5 = group.monsters.iterator();

                while (var5.hasNext()) {
                    m = (AbstractMonster) var5.next();
                    if (predicate.test(m)) {
                        tmp.add(m);
                    }
                }

                return (AbstractMonster) tmp.get(rng.random(0, tmp.size() - 1));
            }
        }
    }
    
    public static AbstractMonster getMonsterWithMaxHealth() {
        if (AbstractDungeon.currMapNode == null
                || AbstractDungeon.getMonsters() == null
                || AbstractDungeon.getMonsters().monsters == null) {
            return null;
        }
        return AbstractDungeon.getCurrRoom().monsters.monsters.stream()
                .filter(ModHelper::check)
                .max(Comparator.comparingInt(m -> m.maxHealth))
                .orElse(null);
    }

    public static boolean hasRelic(String relicID) {
        return AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(HuTaoMod.makeID(relicID));
    }

    public static void applyEnemyPowersOnly(DamageInfo info, AbstractCreature target, boolean reset) {
        if (reset) {
            info.output = info.base;
            info.isModified = false;
        }
        float tmp = (float) info.output;
        Iterator var3 = target.powers.iterator();

        AbstractPower p;
        while (var3.hasNext()) {
            p = (AbstractPower) var3.next();
            tmp = p.atDamageReceive(tmp, info.type);
            if (info.base != info.output) {
                info.isModified = true;
            }
        }

        var3 = target.powers.iterator();

        while (var3.hasNext()) {
            p = (AbstractPower) var3.next();
            tmp = p.atDamageFinalReceive(tmp, info.type);
            if (info.base != info.output) {
                info.isModified = true;
            }
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        info.output = MathUtils.floor(tmp);
    }

    public static void killAllMinions() {
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while (var1.hasNext()) {
            AbstractMonster m = (AbstractMonster) var1.next();
            if (!m.isDead && !m.isDying && (m.hasPower(MinionPower.POWER_ID) || m.halfDead)) {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
            }
        }
    }

    public static boolean moreDamageAscension(AbstractMonster.EnemyType type) {
        int level = 2;
        switch (type) {
            case NORMAL:
                level = 2;
                break;
            case ELITE:
                level = 3;
                break;
            case BOSS:
                level = 4;
                break;
        }
        return AbstractDungeon.ascensionLevel >= level;
    }

    public static boolean moreHPAscension(AbstractMonster.EnemyType type) {
        int level = 7;
        switch (type) {
            case NORMAL:
                level = 7;
                break;
            case ELITE:
                level = 8;
                break;
            case BOSS:
                level = 9;
                break;
        }
        return AbstractDungeon.ascensionLevel >= level;
    }

    public static boolean specialAscension(AbstractMonster.EnemyType type) {
        int level = 17;
        switch (type) {
            case NORMAL:
                level = 17;
                break;
            case ELITE:
                level = 18;
                break;
            case BOSS:
                level = 19;
                break;
        }
        return AbstractDungeon.ascensionLevel >= level;
    }
    
    public static boolean eventAscension() {
        return AbstractDungeon.ascensionLevel >= 15;
    }
    
    public static void playSound(String key, int volumeMod) {
        CardCrawlGame.sound.playV(key, volumeMod);
    }
    
    public static void playSound(String key) {
        playSound(key, 2);
    }
}
