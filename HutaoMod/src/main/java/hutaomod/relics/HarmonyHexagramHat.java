package hutaomod.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.base.HutaoA;
import hutaomod.powers.buffs.EndTurnClairvoirPower;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

public class HarmonyHexagramHat extends HuTaoRelic {
    public static final String ID = HarmonyHexagramHat.class.getSimpleName();
    
    public HarmonyHexagramHat() {
        super(ID, RelicTier.BOSS);
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        flash();
        addToBot(new MakeTempCardInDrawPileAction(new HutaoA(), 1, true, true));
    }
}
