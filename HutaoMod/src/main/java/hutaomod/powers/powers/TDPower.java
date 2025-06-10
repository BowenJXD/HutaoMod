package hutaomod.powers.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.actions.ClairvoirAction;
import hutaomod.actions.ScrayAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.utils.GeneralUtil;
import hutaomod.utils.ModHelper;

public class TDPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(TDPower.class.getSimpleName());
    
    public TDPower(int amount, boolean upgraded) {
        super(POWER_ID, amount, upgraded);
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        if (upgraded) {
            description = GeneralUtil.tryFormat(DESCRIPTIONS[1], amount, amount);
        } else {
            description = GeneralUtil.tryFormat(DESCRIPTIONS[0], amount);
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new ClairvoirAction(amount));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (upgraded) addToBot(new ScrayAction(amount));
    }
}
