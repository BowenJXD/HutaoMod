package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.powers.buffs.EndTurnClairvoirPower;
import hutaomod.utils.CacheManager;

public class ShimenawasReminiscence extends HuTaoRelic {
    public static final String ID = ShimenawasReminiscence.class.getSimpleName();
    
    public ShimenawasReminiscence() {
        super(ID, RelicTier.RARE);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.HP_LOSS) {
            flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EndTurnClairvoirPower(AbstractDungeon.player, 1)));
        }
        return super.onAttacked(info, damageAmount);
    }
}
