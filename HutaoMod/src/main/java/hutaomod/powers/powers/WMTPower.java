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
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], amount, amount);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        GAMManager.addParallelAction(POWER_ID, action -> {
            if (action instanceof ReducePowerAction) {
                ReducePowerAction reducePowerAction = (ReducePowerAction) action;
                try {
                    AbstractPower powerInstance = ReflectionHacks.getPrivate(reducePowerAction, ReducePowerAction.class, "powerInstance");
                    String powerID = ReflectionHacks.getPrivate(reducePowerAction, ReducePowerAction.class, "powerID");
                    if (Objects.equals(powerID, BloodBlossomPower.POWER_ID) || Objects.equals(powerInstance.ID, BloodBlossomPower.POWER_ID)) {
                        addToBot(new GainBlockAction(owner, action.amount * amount));
                        addToBot(new DrawCardAction(amount));
                    }
                } catch (Exception e) {
                    HuTaoMod.logger.error("Failed to get power instance from ReducePowerAction", e);
                }
            }
            return !SubscriptionManager.checkSubscriber(this);
        });
    }

    @Override
    public void onRemove() {
        super.onRemove();
        GAMManager.removeParallelAction(POWER_ID);
    }
}
