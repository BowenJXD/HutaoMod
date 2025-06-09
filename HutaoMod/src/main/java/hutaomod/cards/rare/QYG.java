package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.powers.QYGPower;
import hutaomod.powers.powers.WWJSPower;

public class QYG extends HuTaoCard {
    public static final String ID = QYG.class.getSimpleName();

    public QYG() {
        super(ID);
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        AbstractPower power = p.getPower(QYGPower.POWER_ID);
        if (power instanceof QYGPower) {
            ((QYGPower)power).amount2 += 1;
            power.updateDescription();
        } else {
            addToBot(new ApplyPowerAction(p, p, new QYGPower(magicNumber)));
        }
    }
}
