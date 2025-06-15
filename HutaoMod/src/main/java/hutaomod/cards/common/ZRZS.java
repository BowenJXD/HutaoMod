package hutaomod.cards.common;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.RefundFields;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.characters.HuTao;
import hutaomod.modcore.CustomEnum;

public class ZRZS extends HuTaoCard {
    public static final String ID = ZRZS.class.getSimpleName();
    
    public ZRZS() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new GainBlockAction(p, p, block));
        RefundFields.refund.set(this, yyTime);
        if (upgraded && yyTime > 0) {
            addToBot(new DrawCardAction(yyTime));
        }
    }
}
