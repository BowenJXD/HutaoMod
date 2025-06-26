package hutaomod.cards.rare;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modifiers.HuTaoCardModifier;
import hutaomod.utils.ModHelper;

public class YBSZQSZ extends HuTaoCard {
    public static final String ID = YBSZQSZ.class.getSimpleName();
    
    boolean bottleTriggered = false;

    public YBSZQSZ() {
        super(ID);
    }

    @Override
    public void onMove(CardGroup group, boolean in) {
        super.onMove(group, in);
        if (group.type == CardGroup.CardGroupType.DRAW_PILE && in && upgraded) {
            if (inBottleLightning && !bottleTriggered) {
                bottleTriggered = true;
            }
            ModHelper.addToTopAbstract(() -> {
                group.group.remove(this);
                group.group.add(0, this);
            });
        }
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(magicNumber));
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SMASH));
        addToBot(new ScrayAction(c -> true).callback(cards -> {
            for (AbstractCard card : cards) {
                if (card instanceof HuTaoCard) {
                    ((HuTaoCard) card).onDieying(false);
                }
                for (AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
                    if (mod instanceof HuTaoCardModifier) {
                        ((HuTaoCardModifier) mod).onDieying(card, false);
                    }
                }
            }
        }));
    }
}
