package hutaomod.cards.rare;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.ModHelper;
import hutaomod.utils.RelicEventHelper;

import java.util.Objects;

public class YHCS extends HuTaoCard implements OnPlayerDamagedSubscriber {
    public static final String ID = YHCS.class.getSimpleName();

    public YHCS() {
        super(ID);
        GraveField.grave.set(this, true);
        BaseMod.subscribe(this);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {}

    @Override
    public int receiveOnPlayerDamaged(int i, DamageInfo damageInfo) {
        if (SubscriptionManager.checkSubscriber(this) && i >= AbstractDungeon.player.currentHealth) {
            AbstractDungeon.player.masterDeck.group.stream().filter(c -> Objects.equals(c.uuid, uuid)).findFirst().ifPresent(RelicEventHelper::purgeCards);
            int size = AbstractDungeon.player.exhaustPile.size();
            addToBot(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.exhaustPile, size));
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, size * magicNumber));
            ModHelper.findCards(c -> Objects.equals(c.uuid, uuid)).stream().findFirst().ifPresent(r -> r.group.removeCard(r.card));
            return 0;
        }
        return i;
    }
}
