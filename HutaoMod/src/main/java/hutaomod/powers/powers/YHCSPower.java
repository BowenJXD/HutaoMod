package hutaomod.powers.powers;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.BuffPower;
import hutaomod.powers.PowerPower;

public class YHCSPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(YHCSPower.class.getSimpleName());
    
    public YHCSPower(int amount) {
        super(POWER_ID, amount);
        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount >= owner.currentHealth && AbstractDungeon.player.discardPile.size() >= amount) {
            addToTop(new HealAction(owner, owner, amount));
            addToTop(new MoveCardsAction(AbstractDungeon.player.exhaustPile, AbstractDungeon.player.discardPile, amount));
            addToTop(new ApplyPowerAction(owner, owner, this));
            return 0;
        }
        return super.onAttacked(info, damageAmount);
    }
}
