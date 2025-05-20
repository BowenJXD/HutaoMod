package hutaomod.powers.debuffs;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.actions.TriggerPowerAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.DebuffPower;
import hutaomod.utils.GeneralUtil;
import hutaomod.utils.ModHelper;

public class BloodBlossomPower extends DebuffPower {
    public static final String POWER_ID = HuTaoMod.makeID(BloodBlossomPower.class.getSimpleName());
    
    public AbstractCreature source;
    
    public BloodBlossomPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, owner, amount);
        this.source = source;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], amount);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        addToBot(new TriggerPowerAction(this));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        addToBot(new TriggerPowerAction(this));
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToTop(new ReducePowerAction(owner, source, this, amount/2));
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        if (amount > 0 && ModHelper.check(source)) {
            addToTop(new DamageAction(owner, new DamageInfo(source, amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
        }
    }
}
