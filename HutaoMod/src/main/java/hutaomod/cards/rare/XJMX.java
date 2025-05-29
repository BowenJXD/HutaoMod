package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.buffs.DEBBJPower;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class XJMX extends HuTaoCard {
    public static final String ID = XJMX.class.getSimpleName();

    public XJMX() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        exhaust = false;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, si)));
    }
}
