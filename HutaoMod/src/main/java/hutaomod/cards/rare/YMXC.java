package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;

public class YMXC extends HuTaoCard {
    public static final String ID = YMXC.class.getSimpleName();

    public YMXC() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        p.hand.group.stream().filter(c -> c.hasTag(CardTags.STARTER_STRIKE)).forEach(c -> {
            if (upgraded) 
                addToBot(new UpgradeSpecificCardAction(c));
            addToBot(new ReduceCostForTurnAction(c, 1));
        });
    }
}
