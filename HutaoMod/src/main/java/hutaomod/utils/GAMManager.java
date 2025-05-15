package hutaomod.utils;

import basemod.BaseMod;
import basemod.interfaces.PostUpdateSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * util for {@link com.megacrit.cardcrawl.actions.GameActionManager}
 */
public class GAMManager implements PostUpdateSubscriber {
    private static GAMManager instance = null;
    
    public AbstractGameAction currentAction;
    
    public HashMap<String, Predicate<AbstractGameAction>> parallelActions = new HashMap<>();
    
    private GAMManager() {
        BaseMod.subscribe(this);
    }
    
    public static GAMManager getInstance() {
        if (instance == null) {
            instance = new GAMManager();
        }
        return instance;
    }
    
    public void addParallelAction(String id, Predicate<AbstractGameAction> action) {
        parallelActions.put(id, action);
    }
    
    @Override
    public void receivePostUpdate() {
        if (currentAction != AbstractDungeon.actionManager.currentAction) {
            currentAction = AbstractDungeon.actionManager.currentAction;
            if (currentAction != null) {
                System.out.printf("Current action: %s | source: %s | target: %s | amount: %d%n", 
                        currentAction, currentAction.source, currentAction.target, currentAction.amount);
            }
        }
        if (currentAction != null) {
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
    }
}
