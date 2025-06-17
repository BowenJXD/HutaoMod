package hutaomod.powers.buffs;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import hutaomod.actions.ClairvoirAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.BuffPower;
import hutaomod.utils.ModHelper;

public class EndTurnClairvoirPower extends BuffPower {
    public static final String POWER_ID = HuTaoMod.makeID(EndTurnClairvoirPower.class.getSimpleName());
    
    public EndTurnClairvoirPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        flash();
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        ModHelper.addToBotAbstract(() -> addToBot(new ClairvoirAction(amount)));
    }
}
