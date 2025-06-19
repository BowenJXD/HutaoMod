package hutaomod.powers.powers;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.utils.GeneralUtil;

public class BBLPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(BBLPower.class.getSimpleName());
    
    public BBLPower(int amount) {
        super(POWER_ID, amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], amount, amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount >= owner.currentHealth && AbstractDungeon.player.discardPile.size() >= amount) {
            flash();
            addToTop(new HealAction(owner, owner, amount));
            addToTop(new MoveCardsAction(AbstractDungeon.player.exhaustPile, AbstractDungeon.player.discardPile, amount));
            addToTop(new ApplyPowerAction(owner, owner, this));
            addToTop(new VFXAction(new AdrenalineEffect()));
            return 0;
        }
        return super.onAttacked(info, damageAmount);
    }
}
