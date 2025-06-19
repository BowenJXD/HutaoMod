package hutaomod.powers.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GeneralUtil;

public class QYGPower extends PowerPower implements CheckYinYangSubscriber, NonStackablePower {
    public static final String POWER_ID = HuTaoMod.makeID(QYGPower.class.getSimpleName());
    
    public int amount2 = 1;
    
    public QYGPower(int limit) {
        super(POWER_ID, 0);
        this.limit = limit;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount2 > 1)
            description = GeneralUtil.tryFormat(DESCRIPTIONS[1], limit, amount2, amount2, amount);
        else
            description = GeneralUtil.tryFormat(DESCRIPTIONS[0], limit, amount2, amount);
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

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
    }

    @Override
    public void onLimitReached() {
        super.onLimitReached();
        flash();
        addToBot(new GainEnergyAction(amount2));
        addToBot(new DrawCardAction(amount2));
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
