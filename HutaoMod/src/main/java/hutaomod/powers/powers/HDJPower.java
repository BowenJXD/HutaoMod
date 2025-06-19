package hutaomod.powers.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import hutaomod.cards.base.HutaoA;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.subscribers.PostCardMoveSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GeneralUtil;

public class HDJPower extends PowerPower implements PostCardMoveSubscriber {
    public static final String POWER_ID = HuTaoMod.makeID(HDJPower.class.getSimpleName());
    
    public HDJPower(int amount) {
        super(POWER_ID, amount);
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], amount, amount);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        SubscriptionManager.subscribe(this);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        SubscriptionManager.unsubscribe(this);
    }

    @Override
    public void postCardMove(CardGroup group, AbstractCard card, boolean in) {
        if (SubscriptionManager.checkSubscriber(this) 
                && card instanceof HutaoA 
                && group.type == CardGroup.CardGroupType.HAND 
                && in) {
            flash();
            addToBot(new DrawCardAction(1));
        }
    }
}
