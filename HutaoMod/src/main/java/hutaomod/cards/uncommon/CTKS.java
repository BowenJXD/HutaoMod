package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class CTKS extends HuTaoCard {
    public static final String ID = CTKS.class.getSimpleName();

    public CTKS() {
        super(ID);
        exhaust = true;
        GraveField.grave.set(this, true);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        if (si > 0)
            addToBot(new CardDamageAction(m, new DamageInfo(p, si), this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (!upgraded) {
            int bbCount = ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID);
            int diff = si - bbCount;
            if (diff > 0) {
                addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, diff)));
            } else if (diff < 0) {
                addToBot(new ReducePowerAction(m, p, BloodBlossomPower.POWER_ID, -diff));
            }
        } else {
            addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, si)));
        }
    }
}
