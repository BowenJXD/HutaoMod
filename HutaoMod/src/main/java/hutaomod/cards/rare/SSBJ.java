package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.powers.powers.SSBJPower;

public class SSBJ extends HuTaoCard {
    public static final String ID = SSBJ.class.getSimpleName();
    
    public SSBJ() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ApplyPowerAction(p, p, new SSBJPower(magicNumber)));
    }
}
