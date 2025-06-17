package hutaomod.powers.arcaneLegends;

import com.megacrit.cardcrawl.cards.DamageInfo;
import hutaomod.actions.ScrayAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;

public class JRAZPower extends PowerPower {
    public static final String ID = HuTaoMod.makeID(JRAZPower.class.getSimpleName());
    
    public JRAZPower() {
        super(ID);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.HP_LOSS) {
            addToBot(new ScrayAction(damageAmount));
        }
        return super.onAttacked(info, damageAmount);
    }
}
