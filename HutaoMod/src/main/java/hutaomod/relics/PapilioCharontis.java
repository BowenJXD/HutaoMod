package hutaomod.relics;

import basemod.abstracts.CustomMultiPageFtue;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.DivinityStance;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.cards.base.HutaoQ;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GAMManager;
import hutaomod.utils.ModHelper;
import hutaomod.utils.RelicEventHelper;

import java.util.ArrayList;
import java.util.Objects;

public class PapilioCharontis extends HuTaoRelic implements CheckYinYangSubscriber {
    public static final String ID = PapilioCharontis.class.getSimpleName();
    boolean subscribed = false;
    boolean c6Available = false;
    boolean fullDesc = false;
    public String modNameCache = null;
    
    public PapilioCharontis() {
        super(ID, RelicTier.STARTER);
    }

    @Override
    public void setCounter(int counter) {
        super.setCounter(counter);
        if (counter >= 3 && !subscribed) {
            SubscriptionManager.subscribe(this);
            subscribed = true;
        }
        if (counter >= 4) {
            GAMManager.addParallelAction(ID, action -> {
                if (action instanceof ClairvoirAction) {
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1)));
                }
                return false;
            });
        }
        if (counter > 6) {
            this.counter = 6;
        }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        super.onObtainCard(c);
        AbstractDungeon.player.masterDeck.group.stream().filter(card -> {
            return Objects.equals(card.cardID, c.cardID) && card.canUpgrade();
        }).findAny().ifPresent(card -> {
            ModHelper.addEffectAbstract(() -> RelicEventHelper.upgradeCards(card));
            flash();
            ModHelper.addEffectAbstract(() -> AbstractDungeon.player.masterDeck.group.remove(c));
        });
    }

    @Override
    public void onRest() {
        super.onRest();
        setCounter(counter + 1);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[fullDesc ? 1 : 0];
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        if (isObtained && !fullDesc) {
            flash();
            fullDesc = true;
            description = getUpdatedDescription();
            tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            initializeTips();
        }
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (counter >= 1) {
            AbstractDungeon.player.hand.group.stream().filter(c -> {
                return c.tags.contains(AbstractCard.CardTags.STARTER_STRIKE);
            }).findAny().ifPresent(c -> {
                flash();
                c.freeToPlayOnce = true;
            });
        }
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (counter >= 2) {
            if (Objects.equals(c.cardID, HutaoA.ID)) {
                flash();
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new BloodBlossomPower(m, AbstractDungeon.player, 1)));
            }
            if (Objects.equals(c.cardID, HutaoQ.ID)) {
                flash();
                AbstractDungeon.getMonsters().monsters.stream().filter(ModHelper::check).forEach(m2 -> {
                    addToBot(new ApplyPowerAction(m2, AbstractDungeon.player, new BloodBlossomPower(m2, AbstractDungeon.player, 1)));
                });
            }
        }
    }

    @Override
    public int checkYinYang(HuTaoCard card, int yyTime, boolean onUse) {
        if (SubscriptionManager.checkSubscriber(this) 
                && counter >= 3
                && card.tags.contains(AbstractCard.CardTags.STARTER_STRIKE) 
                && yyTime > 1 
                && onUse) {
            addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, card.block));
        }
        return 0;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        if (counter >= 6) {
            c6Available = true;
        }
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (damageAmount > AbstractDungeon.player.currentHealth && c6Available) {
            flash();
            addToTop(new ChangeStanceAction(DivinityStance.STANCE_ID));
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntangiblePlayerPower(AbstractDungeon.player, 1)));
            return 0;
        }
        return super.onLoseHpLast(damageAmount);
    }
}
