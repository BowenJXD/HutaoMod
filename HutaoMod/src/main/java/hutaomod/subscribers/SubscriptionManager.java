package hutaomod.subscribers;

import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.HuTaoPower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Singleton class for managing all the subscribers.
 */
public final class SubscriptionManager {
    private static SubscriptionManager instance = null;

    List<IHuTaoSubscriber> toRemove = new ArrayList<>();
    List<PrePowerTriggerSubscriber> prePowerTriggerSubscribers = new ArrayList<>();
    List<PreCardDamageSubscriber> preCardDamageSubscribers = new ArrayList<>();
    List<CheckYinYangSubscriber> checkYinYangSubscribers = new ArrayList<>();
    List<PostCardMoveSubscriber> postCardMoveSubscribers = new ArrayList<>();

    HashMap<RunnableType, List<IRunnableSubscriber>> runnableSubscribers = new HashMap<>();
    HashMap<NumChangerType, List<INumChangerSubscriber>> numChangerSubscribers = new HashMap<>();

    SubscriptionManager() {
    }

    public static SubscriptionManager getInstance() {
        if (instance == null) {
            instance = new SubscriptionManager();
        }
        return instance;
    }

    public static void subscribe(IHuTaoSubscriber sub) {
        getInstance().subscribeHelper(sub, false);
    }

    public static void subscribe(IHuTaoSubscriber sub, boolean addToFront) {
        getInstance().subscribeHelper(sub, addToFront);
    }

    public void subscribeHelper(ISubscriber sub, boolean addToFront) {
        if (sub instanceof PrePowerTriggerSubscriber && !prePowerTriggerSubscribers.contains(sub)) {
            if (addToFront) prePowerTriggerSubscribers.add(0, (PrePowerTriggerSubscriber) sub);
            else prePowerTriggerSubscribers.add((PrePowerTriggerSubscriber) sub);
        }
        if (sub instanceof PreCardDamageSubscriber && !preCardDamageSubscribers.contains(sub)) {
            if (addToFront) preCardDamageSubscribers.add(0, (PreCardDamageSubscriber) sub);
            else preCardDamageSubscribers.add((PreCardDamageSubscriber) sub);
        }
        if (sub instanceof CheckYinYangSubscriber && !checkYinYangSubscribers.contains(sub)) {
            if (addToFront) checkYinYangSubscribers.add(0, (CheckYinYangSubscriber) sub);
            else checkYinYangSubscribers.add((CheckYinYangSubscriber) sub);
        }
        if (sub instanceof PostCardMoveSubscriber && !postCardMoveSubscribers.contains(sub)) {
            if (addToFront) postCardMoveSubscribers.add(0, (PostCardMoveSubscriber) sub);
            else postCardMoveSubscribers.add((PostCardMoveSubscriber) sub);
        }

        if (sub instanceof IRunnableSubscriber) {
            subscribeRunnableHelper((IRunnableSubscriber) sub, ((IRunnableSubscriber) sub).getSubType());
        }
        if (sub instanceof INumChangerSubscriber) {
            subscribeNumHelper((INumChangerSubscriber) sub, ((INumChangerSubscriber) sub).getSubType());
        }
    }

    void subscribeRunnableHelper(IRunnableSubscriber sub, RunnableType type) {
        if (!runnableSubscribers.containsKey(type)) {
            runnableSubscribers.put(type, new ArrayList<>());
        }
        if (!runnableSubscribers.get(type).contains(sub)) {
            runnableSubscribers.get(type).add(sub);
        }
    }

    void subscribeNumHelper(INumChangerSubscriber sub, NumChangerType type) {
        if (!numChangerSubscribers.containsKey(type)) {
            numChangerSubscribers.put(type, new ArrayList<>());
        }
        if (!numChangerSubscribers.get(type).contains(sub)) {
            numChangerSubscribers.get(type).add(sub);
        }
    }
    
    public static void unsubscribe(IHuTaoSubscriber sub) {
        getInstance().unsubscribeHelper(sub);
    }

    public void unsubscribeHelper(ISubscriber sub) {
        if (sub instanceof PrePowerTriggerSubscriber) prePowerTriggerSubscribers.remove(sub);
        if (sub instanceof PreCardDamageSubscriber) preCardDamageSubscribers.remove(sub);
        if (sub instanceof CheckYinYangSubscriber) checkYinYangSubscribers.remove(sub);
        if (sub instanceof PostCardMoveSubscriber) postCardMoveSubscribers.remove(sub);

        if (sub instanceof IRunnableSubscriber) {
            unsubscribeRunnableHelper((IRunnableSubscriber) sub, ((IRunnableSubscriber) sub).getSubType());
        }
        if (sub instanceof INumChangerSubscriber) {
            unsubscribeNumHelper((INumChangerSubscriber) sub, ((INumChangerSubscriber) sub).getSubType());
        }
    }

    void unsubscribeRunnableHelper(IRunnableSubscriber sub, RunnableType type) {
        if (!runnableSubscribers.containsKey(type)) return;
        runnableSubscribers.get(type).remove(sub);
    }

    void unsubscribeNumHelper(INumChangerSubscriber sub, NumChangerType type) {
        if (!numChangerSubscribers.containsKey(type)) return;
        numChangerSubscribers.get(type).remove(sub);
    }

    public void unsubscribeLater(IHuTaoSubscriber sub) {
        toRemove.add(sub);
    }

    private void unsubscribeLaterHelper(Class<? extends ISubscriber> removalClass) {
        for (IHuTaoSubscriber sub : toRemove) {
            if (removalClass.isInstance(sub)) {
                unsubscribe(sub);
            }
        }
        toRemove.clear();
    }

    public void triggerPrePowerTrigger(HuTaoPower power) {
        for (PrePowerTriggerSubscriber sub : prePowerTriggerSubscribers) {
            sub.prePowerTrigger(power);
        }

        unsubscribeLaterHelper(PrePowerTriggerSubscriber.class);
    }
    
    public float triggerPreCardDamage(CardDamageAction cardDamageAction, int dmg) {
        float result = dmg;
        
        for (PreCardDamageSubscriber sub : preCardDamageSubscribers) {
            result = sub.preCardDamage(cardDamageAction, result);
        }
        
        unsubscribeLaterHelper(PreCardDamageSubscriber.class);
        
        return result;
    }
    
    public int triggerCheckYinYang(HuTaoCard card, int yyTime, boolean onUse) {
        int result = yyTime;
        
        for (CheckYinYangSubscriber sub : checkYinYangSubscribers) {
            result = sub.checkYinYang(card, result, onUse);
        }
        
        unsubscribeLaterHelper(CheckYinYangSubscriber.class);
        
        return result;
    }
    
    public void triggerPostCardMove(CardGroup group, AbstractCard card, boolean in) {
        for (PostCardMoveSubscriber sub : postCardMoveSubscribers) {
            sub.postCardMove(group, card, in);
        }

        unsubscribeLaterHelper(PostCardMoveSubscriber.class);
    }

    public void triggerRunnable(RunnableType type) {
        if (!runnableSubscribers.containsKey(type)) return;

        for (IRunnableSubscriber sub : runnableSubscribers.get(type)) {
            sub.run();
        }

        unsubscribeLaterHelper(IRunnableSubscriber.class);
    }

    public float triggerNumChanger(NumChangerType type, float baseNum) {
        float result = baseNum;

        if (!numChangerSubscribers.containsKey(type)) return result;

        for (INumChangerSubscriber sub : numChangerSubscribers.get(type)) {
            result = sub.changeNum(result);
        }

        unsubscribeLaterHelper(INumChangerSubscriber.class);

        return result;
    }

    public static boolean checkSubscriber(HuTaoCard card) {
        boolean result = AbstractDungeon.player.hand.contains(card)
                || AbstractDungeon.player.drawPile.contains(card)
                || AbstractDungeon.player.discardPile.contains(card)
                || AbstractDungeon.player.exhaustPile.contains(card)
                || (AbstractDungeon.actionManager != null
                && AbstractDungeon.actionManager.cardQueue != null
                && AbstractDungeon.actionManager.cardQueue.stream().anyMatch(cqi -> cqi != null && cqi.card != null && cqi.card.cardID.equals(card.cardID)));
        if (!result) {
            if (card instanceof IHuTaoSubscriber) getInstance().unsubscribeLater((IHuTaoSubscriber) card);
            if (card instanceof ISubscriber) BaseMod.unsubscribeLater((ISubscriber) card);
        }
        return result;
    }

    public static boolean checkSubscriber(AbstractPower power) {
        boolean result = (
                power.owner == AbstractDungeon.player
                        && AbstractDungeon.player.powers.contains(power))
                || (
                AbstractDungeon.getMonsters() != null
                        && AbstractDungeon.getMonsters().monsters != null
                        && AbstractDungeon.getMonsters().monsters.contains(power.owner)
        );
        if (!result) {
            if (power instanceof IHuTaoSubscriber) getInstance().unsubscribeLater((IHuTaoSubscriber) power);
            if (power instanceof ISubscriber) BaseMod.unsubscribeLater((ISubscriber) power);
        }
        return result;
    }

    public static boolean checkSubscriber(AbstractMonster monster) {
        boolean result = AbstractDungeon.getMonsters().monsters.contains(monster);
        if (!result) {
            if (monster instanceof IHuTaoSubscriber) getInstance().unsubscribeLater((IHuTaoSubscriber) monster);
            if (monster instanceof ISubscriber) BaseMod.unsubscribeLater((ISubscriber) monster);
        }
        return result;
    }

    public static boolean checkSubscriber(AbstractRelic relic) {
        boolean result = AbstractDungeon.player != null
                && AbstractDungeon.player.relics != null
                && AbstractDungeon.player.relics.contains(relic);
        if (!result) {
            if (relic instanceof IHuTaoSubscriber) getInstance().unsubscribeLater((IHuTaoSubscriber) relic);
            if (relic instanceof ISubscriber) BaseMod.unsubscribeLater((ISubscriber) relic);
        }
        return result;
    }

    public static enum RunnableType {
    }

    public static enum NumChangerType {
    }
}
