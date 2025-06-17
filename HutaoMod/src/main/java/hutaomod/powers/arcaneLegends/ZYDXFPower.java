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

public class ZYDXFPower extends PowerPower implements PostCardMoveSubscriber {
    public static final String ID = HuTaoMod.makeID(ZYDXFPower.class.getSimpleName());
    
    public ZYDXFPower() {
        super(ID);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        SubscriptionManager.subscribe(this);
        AbstractDungeon.player.hand.group.stream().filter(c -> c instanceof HutaoA).forEach(
                c -> processCard((HutaoA) c)
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
                && group.type == CardGroup.CardGroupType.HAND && in
                && card instanceof HutaoA) {
            processCard((HutaoA) card);
        }
    }

    void processCard(HutaoA card) {
        CardModifierManager.addModifier(card, new DYBBModifier());
    }
}
