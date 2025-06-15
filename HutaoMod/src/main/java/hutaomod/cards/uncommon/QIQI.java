package hutaomod.cards.uncommon;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;
import hutaomod.cards.HuTaoCard;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.ModHelper;
import hutaomod.utils.RelicEventHelper;

import java.util.List;
import java.util.Objects;

public class QIQI extends HuTaoCard implements OnPlayerDamagedSubscriber {
    public static final String ID = QIQI.class.getSimpleName();
    
    int blizzardPotionModCache = 0;
    
    public QIQI() {
        super(ID);
        exhaust = true;
        GraveField.grave.set(this, true);
        BaseMod.subscribe(this);
    }

    @Override
    public void onMove(CardGroup group, boolean in) {
        super.onMove(group, in);
        if (group.type == CardGroup.CardGroupType.MASTER_DECK) {
            if (in) {
                AbstractRoom.blizzardPotionMod += 1000;
                blizzardPotionModCache = AbstractRoom.blizzardPotionMod;
            } else {
                AbstractRoom.blizzardPotionMod = blizzardPotionModCache;
            }
        }
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ObtainPotionAction(PotionHelper.getRandomPotion()));
    }

    @Override
    public int receiveOnPlayerDamaged(int i, DamageInfo damageInfo) {
        if (SubscriptionManager.checkSubscriber(this) && i >= AbstractDungeon.player.currentHealth) {
            addToBot(new VFXAction(new AdrenalineEffect()));
            AbstractDungeon.player.masterDeck.group.stream().filter(c -> Objects.equals(c.uuid, uuid)).findFirst().ifPresent(RelicEventHelper::purgeCards);
            ModHelper.findCards(c -> Objects.equals(c.uuid, uuid)).stream().findFirst().ifPresent(r -> r.group.removeCard(r.card));
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
            return 0;
        }
        return i;
    }
}
