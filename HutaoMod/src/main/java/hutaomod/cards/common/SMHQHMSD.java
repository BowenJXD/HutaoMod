package hutaomod.cards.common;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.ModHelper;

public class SMHQHMSD extends HuTaoCard {
    public static final String ID = SMHQHMSD.class.getSimpleName();
    
    public SMHQHMSD() {
        super(ID);
        GraveField.grave.set(this, true);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        AbstractMonster m = ModHelper.betterGetRandomMonster();
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }
}
