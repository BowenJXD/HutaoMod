package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;

public class SRWJ extends HuTaoCard {
    public static final String ID = SRWJ.class.getSimpleName();

    public SRWJ() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        addToBot(new ExhaustAction(1, true));
        addToBot(new CardDamageAction(m, damage + (upgraded ? si : 0), this, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (yyTime > 0)
            addToBot(new DrawCardAction(magicNumber * yyTime));
    }
}
