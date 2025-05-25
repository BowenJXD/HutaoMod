package hutaomod.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.actions.ClairvoirAction;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

public class HarmonyHexagramHat extends HuTaoRelic {
    public static final String ID = HarmonyHexagramHat.class.getSimpleName();
    boolean subscribed = false;
    boolean c6Available = false;
    
    public HarmonyHexagramHat() {
        super(ID, RelicTier.BOSS);
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        ModHelper.addToBotAbstract(() -> {
            int yinCount = CacheManager.getInt(CacheManager.Key.YIN_CARDS);
            int yangCount = CacheManager.getInt(CacheManager.Key.YANG_CARDS);
            if (yinCount > yangCount) {
                addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, 4));
            } else if (yinCount < yangCount) {
                addToBot(new ClairvoirAction(4));
            }
        });
    }
}
