package hutaomod.powers.debuffs;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.actions.TriggerPowerAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.DebuffPower;
import hutaomod.utils.ModHelper;

public class BloodBlossomPower extends DebuffPower {
    public static final String POWER_ID = HuTaoMod.makeID(BloodBlossomPower.class.getSimpleName());

    public AbstractCreature source;

    public BloodBlossomPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, owner, amount);
        this.source = source;
        updateDescription();
    }

    public BloodBlossomPower(AbstractCreature owner, int amount) {
        this(owner, AbstractDungeon.player, amount);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        addToTop(new TriggerPowerAction(this));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        addToTop(new TriggerPowerAction(this));
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        if (amount == 1)
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        else
            fadePower();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        addToTop(new TriggerPowerAction(this));
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        flash();
        if (amount > 0 && ModHelper.check(source)) {
            addToTop(new DamageAction(owner, new DamageInfo(source, amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public void fadePower() {
        addToTop(new ReducePowerAction(owner, source, this, amount / (upgraded ? 4 : 2)));
    }

    public static void fadePower(AbstractCreature c) {
        if (c.hasPower(POWER_ID)) {
            AbstractPower power = c.getPower(POWER_ID);
            if (power instanceof BloodBlossomPower) {
                BloodBlossomPower bb = (BloodBlossomPower) power;
                bb.fadePower();
            }
        }
    }
}
