package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.utils.ModHelper;

public class DDD extends HuTaoCard {
    public static final String ID = DDD.class.getSimpleName();

    public DDD() {
        super(ID);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        selfRetain = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        if (!p.hasPower(BufferPower.POWER_ID))
            addToBot(new ApplyPowerAction(p, p, new BufferPower(p, 1)));
    }
}
