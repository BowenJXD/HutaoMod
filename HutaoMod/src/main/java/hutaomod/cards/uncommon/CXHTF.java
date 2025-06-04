package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.buffs.EndTurnClairvoirPower;

public class CXHTF extends HuTaoCard {
    public static final String ID = CXHTF.class.getSimpleName();
    
    public CXHTF() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ClairvoirAction(magicNumber));
        if (yyTime > 0) {
            addToBot(new ApplyPowerAction(p, p, new EndTurnClairvoirPower(p, magicNumber * yyTime)));
        }
    }
}
