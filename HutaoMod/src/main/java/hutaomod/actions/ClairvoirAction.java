package hutaomod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.modcore.HuTaoMod;

import java.util.function.Predicate;

public class ClairvoirAction extends AbstractGameAction {
    Predicate<AbstractCard> filter;
    
    public ClairvoirAction(int amount, Predicate<AbstractCard> filter) {
        this.amount = amount;
        this.filter = filter;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    
    public ClairvoirAction(int amount) {
        this(amount, null);
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            isDone = true;
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (filter != null) {
            addToTop(new MoveCardsAction(p.hand, p.discardPile, filter, amount));
        } else {
            addToTop(new MoveCardsAction(p.hand, p.discardPile, c -> p.discardPile.group.indexOf(c) >= p.discardPile.size() - amount, 1));
        }
        isDone = true;
    }
}
