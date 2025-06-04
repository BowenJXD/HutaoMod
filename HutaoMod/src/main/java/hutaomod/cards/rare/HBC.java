package hutaomod.cards.rare;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.powers.BBLPower;

public class HBC extends HuTaoCard {
    public static final String ID = HBC.class.getSimpleName();

    public HBC() {
        super(ID);
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(magicNumber));
        int drawCount = si - (upgraded ? 0 : p.hand.size());
        if (drawCount > 0)
            addToBot(new DrawCardAction(si));
    }
}
