package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.ClairvoirAction;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.CacheManager;
import hutaomod.utils.GAMManager;
import hutaomod.utils.ModHelper;

public class CrimsonWitchOfFlames extends HuTaoRelic {
    public static final String ID = CrimsonWitchOfFlames.class.getSimpleName();
    
    public CrimsonWitchOfFlames() {
        super(ID, RelicTier.RARE);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        flash();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new BloodBlossomPower(monster, AbstractDungeon.player, 1)));
        }
    }
}
