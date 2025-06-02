package hutaomod.powers.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GeneralUtil;

public class QYGPower extends PowerPower implements CheckYinYangSubscriber, NonStackablePower {
    public static final String POWER_ID = HuTaoMod.makeID(QYGPower.class.getSimpleName());
    
    public QYGPower(int limit) {
        super(POWER_ID, 0);
        this.limit = limit;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], limit, amount);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        SubscriptionManager.subscribe(this);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        SubscriptionManager.unsubscribe(this);
    }

    @Override
    public void onLimitReached() {
        super.onLimitReached();
        addToBot(new GainEnergyAction(1));
        addToBot(new DrawCardAction(1));
        reducePower(amount);
        updateDescription();
    }

    @Override
    public int checkYinYang(HuTaoCard card, int yyTime, boolean onUse) {
        if (SubscriptionManager.checkSubscriber(this) && onUse && yyTime > 0) {
            stackPower(1);
            updateDescription();
        }
        return yyTime;
    }
}
