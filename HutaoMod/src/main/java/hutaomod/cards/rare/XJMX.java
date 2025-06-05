package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class XJMX extends HuTaoCard {
    public static final String ID = XJMX.class.getSimpleName();

    public XJMX() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        exhaust = false;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        for (AbstractCard c : p.hand.group) {
            if (c instanceof HuTaoCard) {
                HuTaoCard card = (HuTaoCard) c;
                addToBot(new DiscardSpecificCardAction(c));
                if (card.yy == YYState.YANG) {
                    if (c.costForTurn > 0) {
                        addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, c.costForTurn)));
                    }
                }
            }
        }
    }
}
