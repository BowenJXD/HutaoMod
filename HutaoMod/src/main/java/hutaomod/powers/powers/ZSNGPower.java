package hutaomod.powers.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.utils.GeneralUtil;

public class ZSNGPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(ZSNGPower.class.getSimpleName());
    
    public ZSNGPower(int amount) {
        super(POWER_ID, amount);
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], amount, amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.HP_LOSS && info.owner == owner) {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, amount)));
            addToBot(new GainBlockAction(owner, owner, amount));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public int onHeal(int healAmount) {
        return healAmount + amount;
    }
}
