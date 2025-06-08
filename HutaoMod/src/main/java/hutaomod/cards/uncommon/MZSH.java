package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class MZSH extends HuTaoCard {
    public static final String ID = MZSH.class.getSimpleName();

    public MZSH() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        int amt;
        if (upgraded)
            amt = magicNumber + ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID) * yyTime;
        else
            amt = yyTime <= 0 ? magicNumber : ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID);
        if (amt > 0)
            addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, amt)));
    }
}
