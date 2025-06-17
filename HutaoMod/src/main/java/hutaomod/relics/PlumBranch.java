package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class PlumBranch extends HuTaoRelic {
    public static final String ID = PlumBranch.class.getSimpleName();
    
    public PlumBranch() {
        super(ID, RelicTier.BOSS);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.NORMAL) {
            flash();
            addToBot(new GainEnergyAction(1));
            return 0;
        }
        return super.onAttackedToChangeDamage(info, damageAmount);
    }
}
