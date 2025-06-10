package hutaomod.powers.debuffs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import hutaomod.effects.ButterflySpawner;
import hutaomod.effects.CustomAuraEffect;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.DebuffPower;
import hutaomod.relics.PapilioCharontis;
import hutaomod.utils.GeneralUtil;

public class SiPower extends DebuffPower {
    public static final String POWER_ID = HuTaoMod.makeID(SiPower.class.getSimpleName());
    
    ButterflySpawner spawner;
    
    public SiPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        updateDescription();
        spawner = new ButterflySpawner(owner.hb);
        priority = 1;
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], amount);
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
