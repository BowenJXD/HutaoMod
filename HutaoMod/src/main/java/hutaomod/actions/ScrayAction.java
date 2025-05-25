package hutaomod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import hutaomod.modcore.HuTaoMod;

import java.util.function.Predicate;

/**
 * Scry + Slay 葬送
 */
public class ScrayAction extends AbstractGameAction {
    Predicate<AbstractCard> filter;
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
        this(AbstractDungeon.player.drawPile.size(), filter);
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            isDone = true;
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (filter != null) {
            addToTop(new SelectCardsAction(p.drawPile.group, amount, TEXT[0], true, filter, cards -> {
                addToTop(new MoveCardsAction(p.discardPile, p.drawPile, cards::contains, amount));
            }));
        } else {
            addToTop(new SelectCardsAction(p.drawPile.group, amount, TEXT[0], true, c -> p.drawPile.group.indexOf(c) < amount, cards -> {
                addToTop(new MoveCardsAction(p.discardPile, p.drawPile, cards::contains, amount));
            }));
        }
        isDone = true;
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(HuTaoMod.makeID(ScrayAction.class.getName()));
        TEXT = uiStrings.TEXT;
    }
}
