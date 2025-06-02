package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class YBTZZ extends HuTaoCard {
    public static final String ID = YBTZZ.class.getSimpleName();

    public YBTZZ() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        int bbCount = ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID);
        addToBot(new CardDamageAction(m, new DamageInfo(p, (upgraded ? damage : 0) + bbCount), this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -bbCount)));
        addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, bbCount)));
    }
}
