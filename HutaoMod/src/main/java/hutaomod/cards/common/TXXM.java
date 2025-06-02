package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class TXXM extends HuTaoCard {
    public static final String ID = TXXM.class.getSimpleName();

    public TXXM() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        int bbCount = ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID);
        int reduceAmount = bbCount/2;
        if (reduceAmount > 0) {
            addToBot(new ReducePowerAction(m, p, BloodBlossomPower.POWER_ID, reduceAmount));
        }
        addToBot(new GainBlockAction(p, p, block + reduceAmount));
    }
}
