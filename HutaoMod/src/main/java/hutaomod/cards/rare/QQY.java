package hutaomod.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BounceAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.SiPower;

public class QQY extends HuTaoCard {
    public static final String ID = QQY.class.getSimpleName();
    
    public QQY() {
        super(ID);
        exhaust = true;
        GraveField.grave.set(this, true);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        if (si <= 0) return;
        addToBot(new GainBlockAction(p, p, si * magicNumber));
        addToBot(new ApplyPowerAction(p, p, new SiPower(p, si)));
    }
}
