package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;

public class SYSS extends HuTaoCard {
    public static final String ID = SYSS.class.getSimpleName();
    
    public SYSS() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(2));
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new MakeTempCardInDrawPileAction(new HutaoA(upgraded), 1, true, true));
    }
}
