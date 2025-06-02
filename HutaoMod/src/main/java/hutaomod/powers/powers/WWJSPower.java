package hutaomod.powers.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;

public class WWJSPower extends PowerPower implements NonStackablePower {
    public static final String POWER_ID = HuTaoMod.makeID(WWJSPower.class.getSimpleName());
    
    public WWJSPower(int amount, boolean upgraded) {
        super(POWER_ID, amount, upgraded);
        if (upgraded) limit = 10;
        this.updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (upgraded && info.type == DamageInfo.DamageType.HP_LOSS && info.owner == owner) {
            stackPower(info.output);
            updateDescription();
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onLimitReached() {
        super.onLimitReached();
        addToBot(new GainEnergyAction(1));
        reducePower(amount);
        updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        addToBot(new DrawCardAction(1));
    }
}
