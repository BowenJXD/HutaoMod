package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BounceAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.characters.HuTao;

public class QKHQL extends HuTaoCard {
    public static final String ID = QKHQL.class.getSimpleName();
    
    public QKHQL() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BounceAction(m, this, magicNumber, mon->{
            addToTop(new CardDamageAction(mon, damage + si, this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }));
    }
}
