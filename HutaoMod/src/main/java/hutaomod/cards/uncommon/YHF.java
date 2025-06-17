package hutaomod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modifiers.DieyingModifier;

public class YHF extends HuTaoCard {
    public static final String ID = YHF.class.getSimpleName();

    public YHF() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ScrayAction(si).callback(cards -> {
            for (AbstractCard card : cards) {
                DieyingModifier modifier = new DieyingModifier();
                CardModifierManager.addModifier(card, modifier);
                if (upgraded) modifier.onDieying(card, true);
            }
        }));
    }
}
