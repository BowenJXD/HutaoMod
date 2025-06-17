package hutaomod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import hutaomod.modcore.HuTaoMod;
import hutaomod.utils.ModHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Scry + Slay 葬送
 */
public class ScrayAction extends AbstractGameAction {
    Predicate<AbstractCard> filter;
    /**
     * Called after discarding cards.
     */
    Consumer<List<AbstractCard>> callback;
    public static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public ScrayAction(int amount, Predicate<AbstractCard> filter) {
        this.amount = amount;
        this.filter = filter;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    
    public ScrayAction(int amount) {
        this(amount, null);
    }
    
    public ScrayAction(Predicate<AbstractCard> filter) {
        this(-1, filter);
    }
    
    public ScrayAction callback(Consumer<List<AbstractCard>> callback) {
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
            addToTop(new BetterMoveCardsAction(p.discardPile, p.drawPile, filter, amount == -1 ? p.drawPile.size() : amount, callback).random(true));
        } else {
            addToTop(new SelectCardsAction(p.drawPile.group, amount, TEXT[0], true, 
                    c -> p.drawPile.group.indexOf(c) >= p.drawPile.group.size() - amount, cards -> {
                if (callback != null) {
                    List<AbstractCard> selectedCards = new ArrayList<>(cards);
                    ModHelper.addToTopAbstract(() -> callback.accept(selectedCards));
                }
                for (AbstractCard card : cards) {
                    addToTop(new DiscardSpecificCardAction(card, p.drawPile));
                }
            }));
        }
        isDone = true;
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(HuTaoMod.makeID(ScrayAction.class.getSimpleName()));
        TEXT = uiStrings.TEXT;
    }
}
