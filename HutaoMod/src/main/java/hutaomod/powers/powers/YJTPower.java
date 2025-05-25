package hutaomod.powers.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.utils.ModHelper;

public class YJTPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(YJTPower.class.getSimpleName());
    
    public YJTPower(int amount, boolean upgraded) {
        super(POWER_ID, amount, upgraded);
        this.updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (upgraded && info.type == DamageInfo.DamageType.HP_LOSS && info.owner == owner) {
            addToBot(new DrawCardAction(amount));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        addToBot(new DrawCardAction(amount));
    }
}
