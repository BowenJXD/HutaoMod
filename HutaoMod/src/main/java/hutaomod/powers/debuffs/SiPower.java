package hutaomod.powers.debuffs;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import hutaomod.effects.ButterflySpawner;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.DebuffPower;
import hutaomod.utils.GeneralUtil;

public class SiPower extends DebuffPower {
    public static final String POWER_ID = HuTaoMod.makeID(SiPower.class.getSimpleName());
    
    ButterflySpawner spawner;
    
    public SiPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        updateDescription();
        spawner = new ButterflySpawner(owner.hb, false);
        priority = 1;
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], amount, amount);
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
            addToBot(new LoseHPAction(owner, owner, owner.currentHealth + TempHPField.tempHp.get(owner)));
        } else {
            addToBot(new LoseHPAction(owner, owner, amount));
            addToBot(new GainBlockAction(owner, owner, amount));
        }
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if (isDying(amount) && spawner != null) {
            spawner.update();
        }
    }

    public static boolean isDying(int amount) {
        if (amount <= 0) return false;
        return String.valueOf(amount).contains("1");
    }
}
