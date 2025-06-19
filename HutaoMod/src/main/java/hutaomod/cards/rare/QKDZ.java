package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BetterMoveCardsAction;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;

import java.util.ArrayList;
import java.util.List;

public class QKDZ extends HuTaoCard {
    public static final String ID = QKDZ.class.getSimpleName();

    public QKDZ() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        isInnate = true;
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(magicNumber));
        List<AbstractCard> discard = new ArrayList<>(p.discardPile.group);  
        List<AbstractCard> draw = new ArrayList<>(p.drawPile.group);
        AbstractGameAction drawToDiscard = new BetterMoveCardsAction(p.discardPile, p.drawPile, draw::contains, 999);
        AbstractGameAction discardToDraw = new BetterMoveCardsAction(p.drawPile, p.discardPile, discard::contains,999);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                drawToDiscard.update();
                discardToDraw.update();
                isDone = drawToDiscard.isDone && discardToDraw.isDone;
            }
        });
        addToBot(new DrawCardAction(magicNumber));
    }
}
