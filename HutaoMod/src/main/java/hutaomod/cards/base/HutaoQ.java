package hutaomod.cards.base;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;

public class HutaoQ extends HuTaoCard {
    public static final String ID = HutaoQ.class.getSimpleName();
    
    public HutaoQ() {
        super(ID);
        exhaust = true;
        GraveField.grave.set(this, true);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, boolean yinyang) {
        addToBot(new BloodBurnAction(p, p, 1));
        addToBot(new GainBlockAction(p, p, block + si));
    }
}
