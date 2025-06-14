package hutaomod.cards.uncommon;

import basemod.BaseMod;
import basemod.interfaces.PostDungeonUpdateSubscriber;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hutaomod.actions.PlayCardAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GeneralUtil;
import hutaomod.utils.RelicEventHelper;
import org.apache.commons.net.daytime.DaytimeUDPClient;

public class ZJXLTS extends HuTaoCard implements PostDungeonUpdateSubscriber {
    public static final String ID = ZJXLTS.class.getSimpleName();
    
    public int goldCache = 0;
    public boolean isUsed = false;
    
    public ZJXLTS() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void onMove(CardGroup group, boolean in) {
        super.onMove(group, in);
        if (group.type == CardGroup.CardGroupType.MASTER_DECK) {
            if (in) BaseMod.subscribe(this);
            else BaseMod.unsubscribe(this);
        }
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new SelectCardsInHandAction(GeneralUtil.tryFormat(RelicEventHelper.SELECT_TEXT, 1), cards -> {
            for (AbstractCard card : cards) {
                card.exhaustOnUseOnce = true;
                addToTop(new PlayCardAction(card, m));
            }
        }));
    }

    @Override
    public void receivePostDungeonUpdate() {
        if (AbstractDungeon.player.masterDeck.contains(this)) {
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP
                    && AbstractDungeon.shopScreen != null
                    && AbstractDungeon.player.gold < goldCache
                    && !isUsed && upgraded) {
                AbstractDungeon.player.gainGold(goldCache - AbstractDungeon.player.gold);
                RelicEventHelper.purgeCards(this);
                isUsed = true;
            }
            goldCache = AbstractDungeon.player.gold;
        } else {
            BaseMod.unsubscribeLater(this);
        }
    }
}
