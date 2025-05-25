package hutaomod.utils;

import basemod.BaseMod;
import basemod.interfaces.PostDungeonUpdateSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * util for {@link com.megacrit.cardcrawl.actions.GameActionManager}
 */
public class GAMManager implements PostDungeonUpdateSubscriber {
    private static GAMManager instance = null;

    public HashMap<String, Predicate<AbstractGameAction>> parallelActions = new HashMap<>();
    public AbstractGameAction currentAction;
    
    public AbstractCard currentCard;
    
    private GAMManager() {
        BaseMod.subscribe(this);
    }
    
    public static GAMManager getInstance() {
        if (instance == null) {
            instance = new GAMManager();
        }
        return instance;
    }
    
    public static void addParallelAction(String id, Predicate<AbstractGameAction> action) {
        getInstance().parallelActions.put(id, action);
    }
    
    public static void removeParallelAction(String id) {
        getInstance().parallelActions.remove(id);
    }
    
    @Override
    public void receivePostDungeonUpdate() {
        if (currentAction != AbstractDungeon.actionManager.currentAction) {
            currentAction = AbstractDungeon.actionManager.currentAction;
            if (currentAction != null) {
                System.out.printf("Current action: %s | source: %s | target: %s | amount: %d%n", 
                        currentAction, currentAction.source, currentAction.target, currentAction.amount);
            }
        }
        if (currentAction != null && currentAction.isDone) {
            Iterator<Map.Entry<String, Predicate<AbstractGameAction>>> iterator = parallelActions.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Predicate<AbstractGameAction>> entry = iterator.next();
                if (entry.getValue().test(currentAction)) {
                    System.out.printf("Parallel action %s triggered by %s%n", entry.getKey(), currentAction);
                    iterator.remove();
                    break;
                }
            }
        }
        List<AbstractCard> cards = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        if (!cards.isEmpty() && cards.get(cards.size() - 1) != currentCard) {
            currentCard = cards.get(cards.size() - 1);
        }
    }
}
