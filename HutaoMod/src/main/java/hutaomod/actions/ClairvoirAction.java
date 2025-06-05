package hutaomod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.modcore.HuTaoMod;
import hutaomod.utils.ModHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ClairvoirAction extends AbstractGameAction {
    Predicate<AbstractCard> filter;
    Consumer<List<AbstractCard>> callback;
    public static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public ClairvoirAction(int amount, Predicate<AbstractCard> filter) {
        this.amount = amount;
        this.filter = filter;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    
    public ClairvoirAction(int amount) {
        this(amount, null);
    }
    
    public ClairvoirAction(Predicate<AbstractCard> filter) {
        this(-1, filter);
    }
    
    public ClairvoirAction callback(Consumer<List<AbstractCard>> callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            isDone = true;
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (filter != null) {
            addToTop(new BetterMoveCardsAction(p.hand, p.discardPile, filter, amount == -1 ? p.discardPile.size() : amount, callback).random(true));
        } else {
            addToTop(new SelectCardsAction(p.discardPile.group, 1, TEXT[0], true, 
                    c -> p.discardPile.group.indexOf(c) < amount, cards -> {
                if (callback != null) {
                    List<AbstractCard> selectedCards = new ArrayList<>(cards);
                    ModHelper.addToTopAbstract(() -> callback.accept(selectedCards));
                }
                for (AbstractCard card : cards) {
                    addToTop(new DiscardToHandAction(card));
                }
            }));
        }
        isDone = true;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(HuTaoMod.makeID(ClairvoirAction.class.getSimpleName()));
        TEXT = uiStrings.TEXT;
    }
}
