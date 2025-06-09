package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.SiPower;

public class GYC extends HuTaoCard {
    public static final String ID = GYC.class.getSimpleName();

    public GYC() {
        super(ID);
        isInnate = true;
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ApplyPowerAction(p, p, new SiPower(p, magicNumber)));
        addToBot(new HealAction(p, p, magicNumber * (upgraded ? 2 : 1)));
        addToBot(new DrawCardAction(1));
    }
}
