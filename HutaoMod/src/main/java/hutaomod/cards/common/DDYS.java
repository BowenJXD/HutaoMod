package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;

public class DDYS extends HuTaoCard {
    public static final String ID = DDYS.class.getSimpleName();
    
    public DDYS() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new MakeTempCardInDrawPileAction(new HutaoA(upgraded), magicNumber, true, true));
    }
}
