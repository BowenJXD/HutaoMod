package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
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
        exhaust = true;
        GraveField.grave.set(this, true);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        selfRetain = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        if (si > 0) {
            if (upgraded) addToBot(new GainBlockAction(p, p, si + block));
            addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, si)));
        }
    }
}
