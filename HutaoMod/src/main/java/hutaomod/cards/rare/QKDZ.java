package hutaomod.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.powers.BBLPower;
import hutaomod.utils.ModHelper;

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
        selfRetain = true;
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        List<AbstractCard> discard = new ArrayList<>(p.discardPile.group);
        List<AbstractCard> draw = new ArrayList<>(p.drawPile.group);
        AbstractGameAction drawToDiscard = new MoveCardsAction(p.discardPile, p.drawPile, draw::contains, 999);
        AbstractGameAction discardToDraw = new MoveCardsAction(p.drawPile, p.discardPile, discard::contains,999);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                drawToDiscard.update();
                discardToDraw.update();
                isDone = drawToDiscard.isDone && discardToDraw.isDone;
            }
        });
    }
}
