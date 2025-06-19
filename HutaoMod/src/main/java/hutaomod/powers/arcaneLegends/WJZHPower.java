package hutaomod.powers.arcaneLegends;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.ModHelper;

public class WJZHPower extends PowerPower implements CheckYinYangSubscriber {
    public static final String POWER_ID = HuTaoMod.makeID(WJZHPower.class.getSimpleName());
    
    public WJZHPower() {
        super(POWER_ID);
        updateDescription();
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
    public int checkYinYang(HuTaoCard card, int yyTime, boolean onUse) {
        if (SubscriptionManager.checkSubscriber(this) && onUse && card.hasTag(CustomEnum.YIN_YANG)) {
            AbstractMonster m = ModHelper.betterGetRandomMonster();
            if (m != null) {
                flash();
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new BloodBlossomPower(m, AbstractDungeon.player, yyTime)));
            }
        }
        return yyTime;
    }
}
