package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class SNYC extends HuTaoCard {
    public static final String ID = SNYC.class.getSimpleName();
    
    public SNYC() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (m.hasPower(BloodBlossomPower.POWER_ID)) {
            addToBot(new DrawCardAction(magicNumber));
        }
    }
}
