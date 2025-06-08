package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;

public class HMFJSDYM extends HuTaoCard {
    public static final String ID = HMFJSDYM.class.getSimpleName();

    public HMFJSDYM() {
        super(ID);;
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ScrayAction(c -> c.type == CardType.ATTACK).callback(cards -> {
            addToTop(new CardDamageAction(m, damage + cards.size() * magicNumber * yyTime, this, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }));
    }
}
