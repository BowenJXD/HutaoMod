package hutaomod.powers.arcaneLegends;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.CacheManager;
import hutaomod.utils.GAMManager;

import java.util.Objects;

public class PYMQPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(PYMQPower.class.getSimpleName());

    public PYMQPower() {
        super(POWER_ID);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        GAMManager.addParallelAction(POWER_ID, action -> {
            if (action instanceof ReducePowerAction) {
                try {
                    ReducePowerAction reducePowerAction = (ReducePowerAction) action;
                    AbstractPower powerInstance = ReflectionHacks.getPrivate(reducePowerAction, ReducePowerAction.class, "powerInstance");
                    String powerID = ReflectionHacks.getPrivate(reducePowerAction, ReducePowerAction.class, "powerID");
                    if (Objects.equals(powerID, BloodBlossomPower.POWER_ID) || Objects.equals(powerInstance.ID, BloodBlossomPower.POWER_ID)) {
                        AbstractPower power = action.target.getPower(BloodBlossomPower.POWER_ID);
                        int si = CacheManager.getInt(CacheManager.Key.PLAYER_SI);
                        if (power != null && power.amount - action.amount < si) {
                            action.amount = Math.max(0, power.amount - si);
                        }
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
}
