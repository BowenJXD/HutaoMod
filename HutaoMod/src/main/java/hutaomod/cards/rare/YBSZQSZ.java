package hutaomod.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAllAction;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.powers.BBLPower;
import hutaomod.utils.ModHelper;

public class YBSZQSZ extends HuTaoCard {
    public static final String ID = YBSZQSZ.class.getSimpleName();

    public YBSZQSZ() {
        super(ID);
        GraveField.grave.set(this, true);
    }

    @Override
    public void onMove(CardGroup group, boolean in) {
        super.onMove(group, in);
        if (group.type == CardGroup.CardGroupType.DRAW_PILE && in) {
            ModHelper.addToTopAbstract(() -> {
                group.group.remove(this);
                group.group.add(0, this);
            });
        }
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(magicNumber));
        addToBot(new ScrayAction(c -> true).callback(cards -> {
            for (AbstractCard card : cards) {
                if (card.costForTurn == 0) {
                    addToTop(new NewQueueCardAction(card, true));
                }
            }
        }));
    }
}
