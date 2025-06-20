package hutaomod.powers.arcaneLegends;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.cards.base.HutaoA;
import hutaomod.modcore.HuTaoMod;
import hutaomod.modifiers.DYBBModifier;
import hutaomod.powers.PowerPower;
import hutaomod.subscribers.PostCardMoveSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.ModHelper;

public class ZYDXFPower extends PowerPower implements PostCardMoveSubscriber {
    public static final String POWER_ID = HuTaoMod.makeID(ZYDXFPower.class.getSimpleName());
    
    public ZYDXFPower() {
        super(POWER_ID);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        SubscriptionManager.subscribe(this);
        ModHelper.findCards(c -> c instanceof HutaoA).forEach(
                r -> processCard((HutaoA) r.card)
        );
    }

    @Override
    public void onRemove() {
        super.onRemove();
        SubscriptionManager.unsubscribe(this);
    }

    @Override
    public void postCardMove(CardGroup group, AbstractCard card, boolean in) {
        if (SubscriptionManager.checkSubscriber(this)
                && in
                && card instanceof HutaoA) {
            processCard((HutaoA) card);
        }
    }

    void processCard(HutaoA card) {
        CardModifierManager.addModifier(card, new DYBBModifier());
    }
}
