package hutaomod.powers.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.subscribers.PostCardMoveSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.ModHelper;

public class HDJPower extends PowerPower implements PostCardMoveSubscriber {
    public static final String POWER_ID = HuTaoMod.makeID(HDJPower.class.getSimpleName());
    
    public HDJPower(int amount) {
        super(POWER_ID, amount);
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        SubscriptionManager.subscribe(this);
        for (ModHelper.FindResult result : ModHelper.findCards(c -> c instanceof HutaoA)) {
            HutaoA hutaoA = (HutaoA) result.card;
            hutaoA.changeToBloodCost(amount);
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        SubscriptionManager.unsubscribe(this);
    }

    @Override
    public void postCardMove(CardGroup group, AbstractCard card, boolean in) {
        if (SubscriptionManager.checkSubscriber(this) 
                && card instanceof HutaoA) {
            if (card.cost > 0) {
                HutaoA hutaoA = (HutaoA) card;
                hutaoA.changeToBloodCost(amount);
            }
            addToBot(new DrawCardAction(1));
        }
    }
}
