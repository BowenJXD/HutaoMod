package hutaomod.powers.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.subscribers.PreCachedIntGetSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.CacheManager;
import hutaomod.utils.GeneralUtil;

import java.util.List;
import java.util.stream.Collectors;

public class WSTPower extends PowerPower implements PreCachedIntGetSubscriber {
    public static final String POWER_ID = HuTaoMod.makeID(WSTPower.class.getSimpleName());
    
    public int amount2 = 2;
    
    public WSTPower(int limit) {
        super(POWER_ID, 1);
        setLimit(limit);
        
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], amount2, limit, amount);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        SubscriptionManager.subscribe(this, true);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        SubscriptionManager.unsubscribe(this);
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card instanceof HuTaoCard) {
            HuTaoCard huTaoCard = (HuTaoCard) card;
            if (huTaoCard.yy == HuTaoCard.YYState.YIN) {
                stackPower(1);
                updateDescription();
            }
        }
    }

    @Override
    public void onLimitReached() {
        super.onLimitReached();
        List<AbstractCard> cards = AbstractDungeon.player.hand.group.stream().filter(c -> c instanceof HuTaoCard && ((HuTaoCard)c).yy == HuTaoCard.YYState.YANG && c.costForTurn > 0).collect(Collectors.toList());
        if (!cards.isEmpty()) {
            AbstractCard card = cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
            card.flash();
            addToBot(new ReduceCostForTurnAction(card, 1));
        }
        reducePower(amount);
        updateDescription();
    }

    @Override
    public int preCachedIntGet(CacheManager.Key key, int amount) {
        if (SubscriptionManager.checkSubscriber(this) && key == CacheManager.Key.PLAYER_SI) {
            return amount + amount2;
        }
        return amount;
    }
}
