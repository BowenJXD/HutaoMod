package hutaomod.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.DivinityStance;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.cards.base.HutaoQ;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.CacheManager;
import hutaomod.utils.GAMManager;
import hutaomod.utils.ModHelper;
import hutaomod.utils.RelicEventHelper;

import java.util.Objects;

public class StaffOfHoma extends HuTaoRelic {
    public static final String ID = StaffOfHoma.class.getSimpleName();
    
    public StaffOfHoma() {
        super(ID, RelicTier.BOSS);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (CacheManager.getBool(CacheManager.Key.HALF_HP)) {
            flash();
            addToBot(new GainEnergyAction(1));
        }
    }
}
