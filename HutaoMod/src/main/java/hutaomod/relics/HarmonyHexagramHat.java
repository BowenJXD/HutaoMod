package hutaomod.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.actions.ClairvoirAction;
import hutaomod.powers.buffs.EndTurnClairvoirPower;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

public class HarmonyHexagramHat extends HuTaoRelic {
    public static final String ID = HarmonyHexagramHat.class.getSimpleName();
    
    public HarmonyHexagramHat() {
        super(ID, RelicTier.BOSS);
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        int si = CacheManager.getInt(CacheManager.Key.PLAYER_SI);
        if (si > 0) {
            flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EndTurnClairvoirPower(AbstractDungeon.player, si)));
        }
    }
}
