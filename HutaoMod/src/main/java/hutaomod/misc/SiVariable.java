package hutaomod.misc;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import hutaomod.cards.HuTaoCard;

public class SiVariable extends DynamicVariable {
    @Override
    public String key() {
        return "Y";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((HuTaoCard)card).isSiModified;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        ((HuTaoCard)card).isSiModified = v;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof HuTaoCard) {
            return ((HuTaoCard) card).si;
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        return false;
    }
}
