package hutaomod.cards.common;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.RefundFields;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;

public class SRZT extends HuTaoCard {
    public static final String ID = SRZT.class.getSimpleName();
    
    public SRZT() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        RefundFields.refund.set(this, yyTime);
        if (upgraded && yyTime > 0) {
            addToBot(new DrawCardAction(yyTime));
        }
    }
}
