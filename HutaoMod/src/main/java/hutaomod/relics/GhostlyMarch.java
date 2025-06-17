package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import hutaomod.utils.CacheManager;

public class GhostlyMarch extends HuTaoRelic {
    public static final String ID = GhostlyMarch.class.getSimpleName();
    
    public GhostlyMarch() {
        super(ID, RelicTier.COMMON);
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (damageAmount >= AbstractDungeon.player.currentHealth && !usedUp) {
            flash();
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 5));
            destroy();
            return 0;
        }
        return super.onLoseHpLast(damageAmount);
    }
}
