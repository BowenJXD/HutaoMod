package hutaomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import hutaomod.cards.HuTaoCard;
import hutaomod.subscribers.SubscriptionManager;

public class CardGroupPatch {
    @SpirePatch(clz = CardGroup.class, method = "addToHand")
    @SpirePatch(clz = CardGroup.class, method = "addToTop")
    @SpirePatch(clz = CardGroup.class, method = "addToBottom")
    @SpirePatch(clz = CardGroup.class, method = "addToRandomSpot")
    public static class AddPatch {
        public static void Postfix(CardGroup __inst, AbstractCard c) {
            SubscriptionManager.getInstance().triggerPostCardMove(__inst, c, true);
            checkDieying(__inst, c, true);
        }
    }
    
    @SpirePatch(clz = CardGroup.class, method = "removeCard", paramtypez = {AbstractCard.class})
    @SpirePatch(clz = CardGroup.class, method = "resetCardBeforeMoving")
    public static class RemovePatch {
        public static void Postfix(CardGroup __inst, AbstractCard c) {
            SubscriptionManager.getInstance().triggerPostCardMove(__inst, c, false);
            checkDieying(__inst, c, false);
        }
    }
    
    @SpirePatch(clz = CardGroup.class, method = "removeTopCard")
    public static class RemoveTopPatch {
        public static AbstractCard cardCache;
        
        public static void Prefix(CardGroup __inst) {
            cardCache = __inst.getTopCard();
        }
        
        public static void Postfix(CardGroup __inst) {
            if (cardCache != null) {
                SubscriptionManager.getInstance().triggerPostCardMove(__inst, cardCache, false);
                checkDieying(__inst, cardCache, false);
                cardCache = null;
            }
        }
    }
    
    @SpirePatch(clz = CardGroup.class, method = "removeCard", paramtypes = "java.lang.String")
    public static class RemoveByIdPatch {
        public static AbstractCard cardCache;
        
        public static void Prefix(CardGroup __inst, String targetID) {
            cardCache = __inst.findCardById(targetID);
        }
        
        public static void Postfix(CardGroup __inst, String targetID) {
            if (cardCache != null) {
                SubscriptionManager.getInstance().triggerPostCardMove(__inst, cardCache, false);
                checkDieying(__inst, cardCache, false);
                cardCache = null;
            }
        }
    }
    
    public static void checkDieying(CardGroup __inst, AbstractCard card, boolean in) {
        if (card instanceof HuTaoCard) {
            HuTaoCard huTaoCard = (HuTaoCard) card;
            if (__inst.type == CardGroup.CardGroupType.DISCARD_PILE) {
                huTaoCard.onDieying(in);
            }
        }
    }
}
