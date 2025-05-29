package hutaomod.cards.base;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.characters.HuTao;

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
        super(ID, HuTao.PlayerColorEnum.HUTAO_RED);
        tags.add(CardTags.STARTER_STRIKE);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAction(m,this, EFFECTS[effectIndex]));
        effectIndex = (effectIndex + 1) % EFFECTS.length;
    }
    
    public void changeToBloodCost(int bloodCost) {
        modifyCostForCombat(-bloodCost);
        rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
}
