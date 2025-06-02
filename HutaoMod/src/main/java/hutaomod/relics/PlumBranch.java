package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

public class PlumBranch extends HuTaoRelic {
    public static final String ID = PlumBranch.class.getSimpleName();
    
    public PlumBranch() {
        super(ID, RelicTier.BOSS);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new BloodBlossomPower(m, AbstractDungeon.player, 2)));
        }
    }
}
