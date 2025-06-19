package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.SiPower;

public class QW extends HuTaoCard {
    public static final String ID = QW.class.getSimpleName();
    
    public QW() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAction(m, damage + si, this, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new SiPower(p, magicNumber)));
    }
}
