package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class BAHX extends HuTaoCard {
    public static final String ID = BAHX.class.getSimpleName();
    
    public BAHX() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        int bbCount = ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID);
        int reduceAmount = bbCount/2;
        if (reduceAmount > 0) {
            addToBot(new ReducePowerAction(m, p, BloodBlossomPower.POWER_ID, reduceAmount));
        }
        addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, magicNumber)));
    }
}
