package hutaomod.cards.base;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;

public class HutaoE extends HuTaoCard {
    public static final String ID = HutaoE.class.getSimpleName();
    
    public HutaoE() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, boolean yinyang) {
        addToBot(new BloodBurnAction(p, p, 1));
        addToBot(new GainBlockAction(p, p, block + si));
    }
}
