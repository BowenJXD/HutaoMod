package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;

public class HTZYGYLG extends HuTaoCard {
    public static final String ID = HTZYGYLG.class.getSimpleName();
    
    public HTZYGYLG() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new GainEnergyAction(si + magicNumber));
    }
}
