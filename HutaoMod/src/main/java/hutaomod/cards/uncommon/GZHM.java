package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.GeneralUtil;
import hutaomod.utils.RelicEventHelper;

import java.util.Objects;

public class GZHM extends HuTaoCard {
    public static final String ID = GZHM.class.getSimpleName();

    public GZHM() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        if (upgraded) {
            addToBot(new SelectCardsAction(p.drawPile.group, GeneralUtil.tryFormat(RelicEventHelper.SELECT_TEXT, 1),
                    c -> c.type == CardType.POWER,
                    list -> {
                        for (AbstractCard c : list) {
                            addToBot(new NewQueueCardAction(c, true));
                        }
                    }));
        } else {
            AbstractCard card = GeneralUtil.getRandomElement(AbstractDungeon.player.drawPile.group, AbstractDungeon.cardRandomRng, c -> c.type == CardType.POWER);
            if (card != null) {
                addToBot(new NewQueueCardAction(card, true));
            }
        }
    }
}
