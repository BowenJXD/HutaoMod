/*
package hutaomod.powers.buffs;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.RefundFields;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.BuffPower;

public class TSSCPower extends BuffPower {
    public static final String POWER_ID = HuTaoMod.makeID(TSSCPower.class.getSimpleName());

    public TSSCPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
            addToBot(new DrawCardAction(1));
        }
        remove(1);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
*/
