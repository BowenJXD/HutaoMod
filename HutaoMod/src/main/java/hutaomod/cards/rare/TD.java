package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.powers.BBLPower;
import hutaomod.powers.powers.TDPower;

public class TD extends HuTaoCard {
    public static final String ID = TD.class.getSimpleName();

    public TD() {
        super(ID);
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ApplyPowerAction(p, p, new TDPower(magicNumber)));
    }
    
}
