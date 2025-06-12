package hutaomod.relics;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.DeepBreath;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.base.HutaoQ;
import hutaomod.cards.special.No;
import hutaomod.cards.special.Yes;
import hutaomod.effects.ButterflySpawner;
import hutaomod.external.RestartRunHelper;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GAMManager;
import hutaomod.utils.ModHelper;
import hutaomod.utils.RelicEventHelper;

import java.util.*;
import java.util.stream.Collectors;

public class PapilioCharontis extends HuTaoRelic {
    public static final String ID = PapilioCharontis.class.getSimpleName();
    boolean c6Available = false;
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
        if (this.counter == 6) {
            c6Available = true;
        }
        refreshDescription();
    }
    
    boolean constellationSoundsPlayed = false;

    @Override
    public void onRest() {
        super.onRest();
        setCounter(counter + 1);
        if (this.counter == 1 || this.counter == 2 || this.counter == 4 || this.counter == 6) {
            if (!constellationSoundsPlayed) ModHelper.playSound("constellation_" + counter);
            if (counter == 6) constellationSoundsPlayed = true;
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
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (Objects.equals(c.cardID, DeepBreath.ID)) {
            addToTop(new WaitAction(0.01f));
        }
    }
    
    @Override
    public int onLoseHpLast(int damageAmount) {
        if (damageAmount > AbstractDungeon.player.currentHealth) {
            if (c6Available) {
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
                setCounter(5);
                return 0;
            } else {
                ArrayList<AbstractCard> group = new ArrayList<>();
                AbstractCard yes = new Yes();
                AbstractCard no = new No();
                group.add(new Yes());
                group.add(new No());
                addToTop(new SelectCardsCenteredAction(group, 1, DESCRIPTIONS[14], cards -> {
                    if (cards.stream().anyMatch(c -> Objects.equals(yes.cardID, c.cardID))) {
                        ModHelper.addToBotAbstract(() -> {
                            AbstractPlayer p = AbstractDungeon.player;
                            p.isDead = true;
                            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
                            p.currentHealth = 0;
                        });
                    } else if (cards.stream().anyMatch(c -> Objects.equals(no.cardID, c.cardID))) {
                        ModHelper.addEffectAbstract(() -> RestartRunHelper.queuedRoomRestart = true);
                    }
                }));
                return 0;
            }
        }
        return super.onLoseHpLast(damageAmount);
    }

    private AbstractCard getUpgradableCard(AbstractCard c) {
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
    
    private void updateSpawners(List<AbstractCard> cards) {
        if (spawners.isEmpty()) {
            for (AbstractCard card : cards) {
                if (getUpgradableCard(card) != null) {
                    spawners.add(new ButterflySpawner(card.hb));
                }
            }
            if (!spawners.isEmpty()) {
                beginLongPulse();
                grayscale = false;
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
            ModHelper.playSound("chest_" + r);
        }
    }
}
