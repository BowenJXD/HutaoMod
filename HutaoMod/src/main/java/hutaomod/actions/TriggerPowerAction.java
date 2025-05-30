package hutaomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TriggerPowerAction extends AbstractGameAction {
    AbstractPower power;
    
    public TriggerPowerAction(AbstractPower power) {
        this.power = power;
        this.actionType = ActionType.POWER;
    }

    @Override
    public void update() {
        if (power != null) {
            power.onSpecificTrigger();
        }
        isDone = true;
    }
}
