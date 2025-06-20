package hutaomod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.modifiers.BloodCostModifier;

public class WSY extends HuTaoCard {
    public static final String ID = WSY.class.getSimpleName();

    public WSY() {
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
        addToBot(new BloodBurnAction(1));
        addToBot(new ClairvoirAction(c -> c.hasTag(CardTags.STARTER_STRIKE)).callback(list -> {
            for (AbstractCard card : list) {
                CardModifierManager.addModifier(card, new BloodCostModifier());
            }
        }));
    }
}
