package hutaomod.actions;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BetterMoveCardsAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private CardGroup source;
    private CardGroup destination;
    private Predicate<AbstractCard> predicate;
    private Consumer<List<AbstractCard>> callback;
    private boolean sortCards;
    private boolean random;

    public BetterMoveCardsAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate, int amount, Consumer<List<AbstractCard>> callback) {
        this.sortCards = false;
        this.p = AbstractDungeon.player;
        this.destination = destination;
        this.source = source;
        this.predicate = predicate;
        this.callback = callback;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public BetterMoveCardsAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate, Consumer<List<AbstractCard>> callback) {
        this(destination, source, predicate, 1, callback);
    }

    public BetterMoveCardsAction(CardGroup destination, CardGroup source, int amount, Consumer<List<AbstractCard>> callback) {
        this(destination, source, (c) -> true, amount, callback);
    }

    public BetterMoveCardsAction(CardGroup destination, CardGroup source, Consumer<List<AbstractCard>> callback) {
        this(destination, source, (c) -> true, 1, callback);
    }

    public BetterMoveCardsAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate, int amount) {
        this(destination, source, predicate, amount, null);
    }

    public BetterMoveCardsAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate) {
        this(destination, source, predicate, 1);
    }

    public BetterMoveCardsAction(CardGroup destination, CardGroup source, int amount) {
        this(destination, source, (c) -> true, amount);
    }

    public BetterMoveCardsAction(CardGroup destination, CardGroup source) {
        this(destination, source, (c) -> true, 1);
    }
    
    public BetterMoveCardsAction sort(boolean sortCards) {
        this.sortCards = sortCards;
        return this;
    }
    
    public BetterMoveCardsAction random(boolean random) {
        this.random = random;
        return this;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);

            for(AbstractCard c : this.source.group) {
                if (this.predicate.test(c)) {
                    if (this.source == this.p.drawPile) {
                        tmp.addToRandomSpot(c);
                    } else {
                        tmp.addToTop(c);
                    }
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() == 1) {
                AbstractCard card = tmp.getTopCard();
                if (this.source == this.p.exhaustPile) {
                    card.unfadeOut();
                }

                if (this.destination == this.p.hand && this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                    this.source.moveToDiscardPile(card);
                    this.p.createHandIsFullDialog();
                } else {
                    card.untip();
                    card.unhover();
                    card.lighten(true);
                    card.setAngle(0.0F);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.current_x = CardGroup.DRAW_PILE_X;
                    card.current_y = CardGroup.DRAW_PILE_Y;
                    this.source.removeCard(card);
                    this.destination.addToTop(card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }

                List<AbstractCard> callbackList = new ArrayList();
                callbackList.add(card);
                if (this.callback != null) {
                    this.callback.accept(callbackList);
                }

                this.isDone = true;
            } else if (tmp.size() > this.amount && !random) {
                if (this.sortCards) {
                    tmp.sortAlphabetically(true);
                    tmp.sortByRarityPlusStatusCardType(true);
                }

                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, this.makeText(), false);
                this.tickDuration();
            } else {
                tmp.shuffle();
                List<AbstractCard> callbackList = new ArrayList();

                for(int i = 0; i < Math.min(tmp.size(), amount); ++i) {
                    AbstractCard card = tmp.getNCardFromTop(i);
                    callbackList.add(card);
                    if (this.source == this.p.exhaustPile) {
                        card.unfadeOut();
                    }

                    if (this.destination == this.p.hand && this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                        this.source.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        card.untip();
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        this.source.removeCard(card);
                        this.destination.addToTop(card);
                        this.p.hand.refreshHandLayout();
                        this.p.hand.applyPowers();
                    }
                }

                if (this.callback != null) {
                    this.callback.accept(callbackList);
                }

                this.isDone = true;
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                List<AbstractCard> callbackList = new ArrayList();

                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    callbackList.add(c);
                    c.untip();
                    c.unhover();
                    if (this.source == this.p.exhaustPile) {
                        c.unfadeOut();
                    }

                    if (this.destination == this.p.hand && this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                        this.source.moveToDiscardPile(c);
                        this.p.createHandIsFullDialog();
                    } else {
                        this.source.removeCard(c);
                        this.destination.addToTop(c);
                    }

                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                if (this.callback != null) {
                    this.callback.accept(callbackList);
                }
            }

            this.tickDuration();
        }
    }

    private String makeText() {
        String ret;
        if (this.amount == 1) {
            ret = TEXT[0];
        } else {
            ret = TEXT[1];
        }

        String location = null;
        if (this.destination == this.p.hand) {
            location = (String)uiStrings.TEXT_DICT.get("HAND");
        } else if (this.destination == this.p.drawPile) {
            location = (String)uiStrings.TEXT_DICT.get("DRAW");
        } else if (this.destination == this.p.discardPile) {
            location = (String)uiStrings.TEXT_DICT.get("DISCARD");
        } else if (this.destination == this.p.exhaustPile) {
            location = (String)uiStrings.TEXT_DICT.get("EXHAUST");
        }

        if (location == null) {
            location = "<Unknown>";
        }

        return String.format(ret, location);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("stslib:MoveCardsAction");
        TEXT = uiStrings.TEXT;
    }
}

