package hutaomod.powers.powers;

import hutaomod.actions.ClairvoirAction;
import hutaomod.actions.ScrayAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.utils.GeneralUtil;

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
        flash();
        addToBot(new ClairvoirAction(amount));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        flash();
        if (upgraded) addToBot(new ScrayAction(amount));
    }
}
