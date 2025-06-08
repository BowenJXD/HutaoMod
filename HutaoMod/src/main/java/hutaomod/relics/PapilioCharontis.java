package hutaomod.relics;

import basemod.abstracts.CustomMultiPageFtue;
import basemod.abstracts.cardbuilder.actionbuilder.AmountActionBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.stances.DivinityStance;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.cards.base.HutaoQ;
import hutaomod.effects.ButterflyEffect;
import hutaomod.effects.ButterflySpawner;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.buffs.BreathPower;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GAMManager;
import hutaomod.utils.ModHelper;
import hutaomod.utils.RelicEventHelper;

import java.util.*;
import java.util.stream.Collectors;

public class PapilioCharontis extends HuTaoRelic {
    public static final String ID = PapilioCharontis.class.getSimpleName();
    boolean c6Available = true;
    boolean fullDesc = false;

    public PapilioCharontis() {
        super(ID, RelicTier.STARTER);
        counter = 0;
    }

    @Override
    public void setCounter(int counter) {
        super.setCounter(counter);
        if (counter == 2) {
            AbstractCard[] cards = AbstractDungeon.player.masterDeck.group.stream()
                    .filter(c -> (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c instanceof HutaoQ) && c.canUpgrade())
                    .toArray(AbstractCard[]::new);
            RelicEventHelper.upgradeCards(cards);
        }
        if (counter == 3) {
            AbstractCard[] cards = AbstractDungeon.player.masterDeck.group.stream()
                    .filter(c -> c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) && c.canUpgrade())
                    .toArray(AbstractCard[]::new);
            RelicEventHelper.upgradeCards(cards);
        }
        if (this.counter > 6) {
            this.counter = 6;
        }
        refreshDescription();
    }

    @Override
    public void onRest() {
        super.onRest();
        setCounter(counter + 1);
        if (this.counter == 1 || this.counter == 2 || this.counter == 4 || this.counter == 6) {
            CardCrawlGame.sound.play("constellation_" + counter);
        }
    }

    @Override
    public String getUpdatedDescription() {
        if (!fullDesc) {
            return DESCRIPTIONS[0];
        }
        int[] indexes;
        switch (counter) {
            case 0:
                indexes = new int[]{0, 1, 3, 5, 7, 9, 11};
                break;
            case 1:
                indexes = new int[]{0, 2, 3, 5, 7, 9, 11};
                break;
            case 2:
                indexes = new int[]{0, 2, 4, 5, 7, 9, 11};
                break;
            case 3:
                indexes = new int[]{0, 2, 4, 6, 7, 9, 11};
                break;
            case 4:
                indexes = new int[]{0, 2, 4, 6, 8, 9, 11};
                break;
            case 5:
                indexes = new int[]{0, 2, 4, 6, 8, 10, 11};
                break;
            case 6:
                indexes = new int[]{0, 2, 4, 6, 8, 10, c6Available ? 12 : 13};
                break;
            default:
                indexes = new int[]{0};
        }
        String desc = Arrays.stream(indexes).mapToObj(i -> DESCRIPTIONS[i]).collect(Collectors.joining(" NL "));
        return desc;
    }

    @Override
    public void onEnterRestRoom() {
        super.onEnterRestRoom();
        if (counter >= 6) {
            c6Available = true;
            beginLongPulse();
            refreshDescription();
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        super.onEnterRoom(room);
        if (isObtained && !fullDesc) {
            fullDesc = true;
            refreshDescription();
        }
    }

    public void refreshDescription() {
        flash();
        description = getUpdatedDescription();
        tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        if (counter >= 1) {
            ModHelper.addToBotAbstract(() -> {
                AbstractDungeon.player.hand.group.stream().filter(c -> {
                    return c.hasTag(AbstractCard.CardTags.STARTER_STRIKE);
                }).findAny().ifPresent(c -> {
                    flash();
                    c.freeToPlayOnce = true;
                });
            });
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        if (counter >= 4) {
            GAMManager.addParallelAction(relicId + 4, action -> {
                if (AbstractDungeon.actionManager.actions != null
                        && !AbstractDungeon.actionManager.actions.isEmpty()
                        && AbstractDungeon.actionManager.actions.get(0) instanceof EmptyDeckShuffleAction) {
                    // addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BreathPower(AbstractDungeon.player, 1)));
                    addToTop(new ClairvoirAction(4));
                    return true;
                }
                return !SubscriptionManager.checkSubscriber(this);
            });
        }
        if (c6Available) {
            beginLongPulse();
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        stopPulse();
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (damageAmount > AbstractDungeon.player.currentHealth && c6Available) {
            flash();
            if (!AbstractDungeon.actionManager.turnHasEnded) {
                addToTop(new ChangeStanceAction(DivinityStance.STANCE_ID));
            } else {
                GAMManager.addParallelAction(relicId + 6, action -> {
                    if (!AbstractDungeon.actionManager.turnHasEnded) {
                        addToTop(new ChangeStanceAction(DivinityStance.STANCE_ID));
                        return true;
                    }
                    return false;
                });
            }
            addToTop(new GainBlockAction(AbstractDungeon.player, 200));
            c6Available = false;
            stopPulse();
            refreshDescription();
            return 0;
        }
        return super.onLoseHpLast(damageAmount);
    }

    AbstractCard getUpgradableCard(AbstractCard c) {
        return AbstractDungeon.player.masterDeck.group.stream().filter(card -> {
            return Objects.equals(card.cardID, c.cardID) && card.canUpgrade();
        }).findAny().orElse(null);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        super.onObtainCard(c);
        AbstractCard upgradableCard = getUpgradableCard(c);
        if (upgradableCard != null) {
            ModHelper.addEffectAbstract(() -> RelicEventHelper.upgradeCards(upgradableCard), false);
            flash();
            ModHelper.addEffectAbstract(() -> RelicEventHelper.purgeCards(c));
        }
    }

    List<ButterflySpawner> spawners = new ArrayList<>();
    
    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD     
                && AbstractDungeon.cardRewardScreen != null 
                && (AbstractDungeon.getCurrRoom().rewardTime || !AbstractDungeon.getCurrRoom().combatEvent)) {
            updateSpawners(AbstractDungeon.cardRewardScreen.rewardGroup);
        } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP 
                && AbstractDungeon.shopScreen != null) {
            updateSpawners(AbstractDungeon.shopScreen.coloredCards);
        } else {
            spawners.clear();
            stopPulse();
        }
    }
    
    void updateSpawners(List<AbstractCard> cards) {
        if (spawners.isEmpty()) {
            for (AbstractCard card : cards) {
                if (getUpgradableCard(card) != null) {
                    spawners.add(new ButterflySpawner(card.hb));
                }
            }
            if (!spawners.isEmpty()) {
                beginLongPulse();
            }
        }
        for (ButterflySpawner spawner : spawners) {
            spawner.update();
        }
    }

    @Override
    public void onChestOpen(boolean bossChest) {
        super.onChestOpen(bossChest);
        int r = MathUtils.random(1, 6);
        if (r <= 3) {
            CardCrawlGame.sound.play("chest_" + r);
        }
    }
}
