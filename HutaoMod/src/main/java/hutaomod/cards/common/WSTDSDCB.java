/*
package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.buffs.GYSYPower;

public class WSTDSDCB extends HuTaoCard {
    public static final String ID = WSTDSDCB.class.getSimpleName();
    
    public WSTDSDCB() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        selfRetain = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        addToBot(new ApplyPowerAction(p, p, new GYSYPower(p, 1)));
        if (upgraded) {
            addToBot(new DrawCardAction(1));
        }
    }
}
*/
