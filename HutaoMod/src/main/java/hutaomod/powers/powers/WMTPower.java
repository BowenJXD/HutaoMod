package hutaomod.powers.powers;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.HuTaoPower;
import hutaomod.powers.PowerPower;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.PrePowerTriggerSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GAMManager;
import hutaomod.utils.GeneralUtil;

import java.util.Objects;

public class WMTPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(WMTPower.class.getSimpleName());
    
    AbstractCard cardCache = null;
    
    public WMTPower(int amount) {
        super(POWER_ID, amount);
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        GAMManager.addParallelAction(POWER_ID, action -> {
            if (action instanceof ReducePowerAction) {
                ReducePowerAction reducePowerAction = (ReducePowerAction) action;
                try {
                    AbstractPower powerInstance = ReflectionHacks.getPrivate(reducePowerAction, ReducePowerAction.class, "powerInstance");
                    if (Objects.equals(powerInstance.ID, BloodBlossomPower.POWER_ID)) {
                        addToBot(new GainBlockAction(owner, action.amount));
                    }
                } catch (Exception e) {
                    HuTaoMod.logger.error("Failed to get power instance from ReducePowerAction", e);
                }
            }
            return false;
        });
    }

    @Override
    public void onRemove() {
        super.onRemove();
        GAMManager.removeParallelAction(POWER_ID);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        if (Objects.equals(power.ID, BloodBlossomPower.POWER_ID) 
                && GAMManager.getInstance().currentCard != cardCache) {
            if (power.amount > 0) {
                cardCache = GAMManager.getInstance().currentCard;
                addToBot(new DrawCardAction(amount));
            } else if (power.amount < 0) {
                addToBot(new GainBlockAction(owner, -power.amount));
            }
        }
    }
}
