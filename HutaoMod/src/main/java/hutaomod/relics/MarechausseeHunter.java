package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.actions.ScrayAction;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

public class MarechausseeHunter extends HuTaoRelic {
    public static final String ID = MarechausseeHunter.class.getSimpleName();
    
    public MarechausseeHunter() {
        super(ID, RelicTier.UNCOMMON);
    }

    /*@Override
    public void onCardDraw(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);
        if (drawnCard.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
            addToBot(new DrawCardAction(1));
        }
    }*/

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        ModHelper.addToBotAbstract(() -> {
            flash();
            addToTop(new ScrayAction(AbstractDungeon.player.drawPile.size() / 2));
        });
    }
}
