package hutaomod.powers.debuffs;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.DebuffPower;

public class SiPower extends DebuffPower {
    public static final String POWER_ID = HuTaoMod.makeID(SiPower.class.getSimpleName());
    
    public SiPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        onSpecificTrigger();
        remove(1);
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        addToBot(new VFXAction(new OfferingEffect()));
        if (isDying(amount)) {
            addToBot(new LoseHPAction(owner, owner, 99999));
        } else {
            addToBot(new LoseHPAction(owner, owner, amount));
        }
    }

    public static boolean isDying(int amount) {
        if (amount <= 0) return false;
        return String.valueOf(amount).contains("1");
    }
}
