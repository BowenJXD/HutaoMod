package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.ClairvoirAction;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;

public class TDJCMP extends HuTaoCard {
    public static final String ID = TDJCMP.class.getSimpleName();
    
    public TDJCMP() {
        super(ID);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ScrayAction(magicNumber * 2 + (upgraded ? yyTime : 0)));
        addToBot(new DrawCardAction(magicNumber + (upgraded ? yyTime : 0)));
    }
}
