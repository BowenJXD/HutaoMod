package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.powers.buffs.EndTurnClairvoirPower;
import hutaomod.utils.CacheManager;

public class ShimenawasReminiscence extends HuTaoRelic {
    public static final String ID = ShimenawasReminiscence.class.getSimpleName();
    
    public ShimenawasReminiscence() {
        super(ID, RelicTier.RARE);
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);
        if (drawnCard.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
            flash();
            addToBot(new DrawCardAction(1));
        }
    }
}
