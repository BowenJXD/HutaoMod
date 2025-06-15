/*
package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.powers.ZSNGPower;

public class WWJS extends HuTaoCard {
    public static final String ID = WWJS.class.getSimpleName();

    public WWJS() {
        super(ID);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        isInnate = true;
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        AbstractPower power = p.getPower(ZSNGPower.POWER_ID);
        if (power instanceof ZSNGPower) {
            ((ZSNGPower)power).amount2 += 2; 
            power.updateDescription();
        } else {
            addToBot(new ApplyPowerAction(p, p, new ZSNGPower(magicNumber)));
        }
    }
}
*/
