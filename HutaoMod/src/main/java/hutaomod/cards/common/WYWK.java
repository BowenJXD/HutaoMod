package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;

public class WYWK extends HuTaoCard {
    public static final String ID = WYWK.class.getSimpleName();
    
    public WYWK() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(2));
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new MakeTempCardInDrawPileAction(new HutaoA(upgraded), 1, true, true));
    }
}
