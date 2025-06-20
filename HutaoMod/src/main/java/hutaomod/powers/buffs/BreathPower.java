package hutaomod.powers.buffs;

import com.megacrit.cardcrawl.core.AbstractCreature;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.BuffPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;

public class BreathPower extends BuffPower implements CheckYinYangSubscriber {
    public static final String POWER_ID = HuTaoMod.makeID(BreathPower.class.getSimpleName());
    
    public BreathPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        SubscriptionManager.subscribe(this, true);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        SubscriptionManager.unsubscribe(this);
    }

    @Override
    public int checkYinYang(HuTaoCard card, int yyTime, boolean onUse) {
        if (SubscriptionManager.checkSubscriber(this) && yyTime == 0) {
            yyTime++;
            if (onUse && card.hasTag(CustomEnum.YIN_YANG)) {
                flash();
                remove(1);
            }
        }
        return yyTime;
    }
}
