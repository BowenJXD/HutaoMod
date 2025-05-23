package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
        addToBot(new CardDamageAction(m, new DamageInfo(p, (int) (damage * Math.pow(2, yyTime))), this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}
