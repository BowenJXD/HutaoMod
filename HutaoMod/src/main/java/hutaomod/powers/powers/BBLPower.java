package hutaomod.powers.powers;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.GAMManager;
import hutaomod.utils.ModHelper;

import java.util.Objects;

public class BBLPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(BBLPower.class.getSimpleName());
    
    public BBLPower(int amount) {
        super(POWER_ID, amount);
        this.updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.HP_LOSS && info.owner == owner) {
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1)));
            addToBot(new GainBlockAction(owner, owner, amount));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public int onHeal(int healAmount) {
        int strengthCount = ModHelper.getPowerCount(owner, StrengthPower.POWER_ID);
        if (strengthCount > 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, StrengthPower.POWER_ID));
            return healAmount + strengthCount;
        }
        return super.onHeal(healAmount);
    }
}
