package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;

public class DXZTZEL extends HuTaoCard {
    public static final String ID = DXZTZEL.class.getSimpleName();
    
    public DXZTZEL() {
        super(ID);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        selfRetain = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractCard card = new HutaoA();
        if (upgraded) card.upgrade();
        addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
    }

    @Override
    public void tookDamage() {
        super.tookDamage();
        addToBot(new ReduceCostForTurnAction(this, 1));
    }
}
