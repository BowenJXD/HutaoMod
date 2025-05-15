package hutaomod.cards.base;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;

public class HutaoA extends HuTaoCard {
    public static final String ID = HutaoA.class.getSimpleName();
    
    public static final AbstractGameAction.AttackEffect[] EFFECTS = {
            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
            AbstractGameAction.AttackEffect.SLASH_HEAVY,
            AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
            AbstractGameAction.AttackEffect.SLASH_VERTICAL,
            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
    };
    
    public static int effectIndex = 0;
    
    public HutaoA() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, boolean yinyang) {
        addToBot(new CardDamageAction(m,this, EFFECTS[effectIndex]));
        effectIndex = (effectIndex + 1) % EFFECTS.length;
    }
}
