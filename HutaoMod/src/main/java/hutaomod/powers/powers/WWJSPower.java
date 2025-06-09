package hutaomod.powers.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.utils.GeneralUtil;

public class WWJSPower extends PowerPower implements NonStackablePower {
    public static final String POWER_ID = HuTaoMod.makeID(WWJSPower.class.getSimpleName());
    
    public int amount2 = 2;
    
    public WWJSPower(int amount) {
        super(POWER_ID, amount);
        limit = 5;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], limit, amount2, amount2, amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.HP_LOSS && info.owner == owner) {
            stackPower(info.output);
            updateDescription();
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onLimitReached() {
        super.onLimitReached();
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount2)));
        addToBot(new HealAction(owner, owner, amount2));
        reducePower(amount);
        updateDescription();
    }
}
