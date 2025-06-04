package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

public class PlumBranch extends HuTaoRelic implements CheckYinYangSubscriber {
    public static final String ID = PlumBranch.class.getSimpleName();
    
    public PlumBranch() {
        super(ID, RelicTier.BOSS);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
        }
    }

    @Override
    public int checkYinYang(HuTaoCard card, int yyTime, boolean onUse) {
        if (SubscriptionManager.checkSubscriber(this) && onUse && card.tags.contains(CustomEnum.YIN_YANG)) {
            AbstractMonster m = ModHelper.betterGetRandomMonster();
            if (m != null)
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new BloodBlossomPower(m, AbstractDungeon.player, yyTime)));
        }
        return yyTime;
    }
}
