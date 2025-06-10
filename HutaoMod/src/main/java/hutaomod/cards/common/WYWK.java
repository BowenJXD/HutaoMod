/*
package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.buffs.BreathPower;

public class WYWK extends HuTaoCard {
    public static final String ID = WYWK.class.getSimpleName();
    
    public WYWK() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(magicNumber));
        addToBot(new CardDamageAction(m, si + damage, this, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (yyTime > 0)
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber * yyTime, false)));
    }
}
*/
