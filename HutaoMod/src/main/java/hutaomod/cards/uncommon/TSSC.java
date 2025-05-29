package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.buffs.TSSCPower;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.utils.ModHelper;

public class TSSC extends HuTaoCard {
    public static final String ID = TSSC.class.getSimpleName();

    public TSSC() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        if (si > 0) {
            if (upgraded)
                addToBot(new ScrayAction(si));
            addToBot(new ApplyPowerAction(p, p, new TSSCPower(p, si)));
        }
    }
}
