package hutaomod.powers.arcaneLegends;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.PostCardMoveSubscriber;
import hutaomod.subscribers.SubscriptionManager;

public class SMXGPower extends PowerPower implements CheckYinYangSubscriber, PostCardMoveSubscriber {
    public static final String POWER_ID = HuTaoMod.makeID(SMXGPower.class.getSimpleName());
    
    public SMXGPower() {
        super(POWER_ID);
        updateDescription();
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
        card.yy = HuTaoCard.YYState.YINYANG;
        if (!card.hasTag(CustomEnum.YIN_YANG))
            card.tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public int checkYinYang(HuTaoCard card, int yyTime, boolean onUse) {
        if (SubscriptionManager.checkSubscriber(this) 
                && card instanceof HutaoA 
                && yyTime > 0 && onUse) {
            card.damage *= (int) Math.pow(2, yyTime);
        }
        return yyTime;
    }
}
