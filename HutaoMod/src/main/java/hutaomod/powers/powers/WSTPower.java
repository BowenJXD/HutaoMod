package hutaomod.powers.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GeneralUtil;

import java.util.List;
import java.util.stream.Collectors;

public class WSTPower extends PowerPower implements CheckYinYangSubscriber {
    public static final String POWER_ID = HuTaoMod.makeID(WSTPower.class.getSimpleName());
    
    public WSTPower() {
        super(POWER_ID, 1);
        loadRegion("InfoPower");
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], amount, amount);
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
        if (SubscriptionManager.checkSubscriber(this) && onUse && yyTime > 0) {
            if (card.yy == HuTaoCard.YYState.YIN) {
                addToBot(new DrawCardAction(1));
            } else if (card.yy == HuTaoCard.YYState.YANG && card.costForTurn > 0) {
                addToBot(new GainEnergyAction(1));
            }
        }
        return yyTime;
    }
}
