package hutaomod.powers.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.utils.CacheManager;

public class XZZHPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(XZZHPower.class.getSimpleName());
    
    public XZZHPower(int amount, boolean upgraded) {
        super(POWER_ID, amount, upgraded);
        this.updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (CacheManager.getBool(CacheManager.Key.DYING)) damage += amount;
        if (CacheManager.getBool(CacheManager.Key.HALF_HP)) damage += 1;
        return super.atDamageGive(damage, type);
    }

    @Override
    public float modifyBlock(float blockAmount) {
        if (CacheManager.getBool(CacheManager.Key.DYING)) blockAmount += amount;
        if (CacheManager.getBool(CacheManager.Key.HALF_HP)) blockAmount += 1;
        return super.modifyBlock(blockAmount);
    }
}
