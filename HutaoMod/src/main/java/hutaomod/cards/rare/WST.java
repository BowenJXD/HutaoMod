package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.powers.WSTPower;

public class WST extends HuTaoCard {
    public static final String ID = WST.class.getSimpleName();
    
    public WST() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        AbstractPower power = p.getPower(WSTPower.POWER_ID);
        if (power instanceof WSTPower) {
            ((WSTPower)power).amount2 += 1;
            power.updateDescription();
        } else {
            addToBot(new ApplyPowerAction(p, p, new WSTPower(magicNumber)));
        }
    }
}
