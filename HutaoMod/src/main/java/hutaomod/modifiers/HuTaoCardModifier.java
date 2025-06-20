package hutaomod.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public abstract class HuTaoCardModifier extends AbstractCardModifier {

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        card.flash();
    }

    public void onDieying(AbstractCard card, boolean in) {}

    public void onMove(AbstractCard card, CardGroup group, boolean in) {
        if (group.type == CardGroup.CardGroupType.DISCARD_PILE) {
            onDieying(card, in);
        }
    }
}
