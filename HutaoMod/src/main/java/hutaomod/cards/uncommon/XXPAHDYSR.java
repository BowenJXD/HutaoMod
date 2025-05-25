package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.cards.HuTaoCard;

public class XXPAHDYSR extends HuTaoCard {
    public static final String ID = XXPAHDYSR.class.getSimpleName();

    public XXPAHDYSR() {
        super(ID);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        isInnate = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        if (si > 0) {
            addToBot(new GainBlockAction(p, p, si + (upgraded ? block : 0)));
            addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, si)));
        }
    }
}
