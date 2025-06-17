package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.powers.buffs.BreathPower;
import hutaomod.utils.CacheManager;

public class GildedDreams extends HuTaoRelic {
    public static final String ID = GildedDreams.class.getSimpleName();
    
    public GildedDreams() {
        super(ID, RelicTier.RARE);
    }

    @Override
    public void onShuffle() {
        super.onShuffle();
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BreathPower(AbstractDungeon.player, 1)));
    }
}
