package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.powers.BBLPower;

public class BBL extends HuTaoCard {
    public static final String ID = BBL.class.getSimpleName();

    public BBL() {
        super(ID);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ApplyPowerAction(p, p, new BBLPower(magicNumber)));
    }
}
