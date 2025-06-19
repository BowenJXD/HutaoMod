package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;

public class YMXC extends HuTaoCard {
    public static final String ID = YMXC.class.getSimpleName();

    public YMXC() {
        super(ID);
        selfRetain = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        p.hand.group.stream().filter(c -> c instanceof HutaoA).forEach(c -> {
            if (upgraded) 
                addToBot(new UpgradeSpecificCardAction(c));
            ((HutaoA) c).changeToBloodCost(1);
        });
    }
}
