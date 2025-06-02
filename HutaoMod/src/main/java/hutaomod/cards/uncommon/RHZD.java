package hutaomod.cards.uncommon;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.ModHelper;

public class RHZD extends HuTaoCard implements OnPlayerDamagedSubscriber {
    public static final String ID = RHZD.class.getSimpleName();

    public RHZD() {
        super(ID);
        selfRetain = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void onEnterHand() {
        super.onEnterHand();
        BaseMod.unsubscribe(this);
        BaseMod.subscribe(this);
    }

    @Override
    public void onLeaveHand() {
        super.onLeaveHand();
        BaseMod.unsubscribe(this);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {}

    @Override
    public int receiveOnPlayerDamaged(int i, DamageInfo damageInfo) {
        if (SubscriptionManager.checkSubscriber(this) && i >= AbstractDungeon.player.currentHealth + AbstractDungeon.player.currentBlock && inHand) {
            addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                int bbCount = ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID);
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new BloodBlossomPower(m, AbstractDungeon.player, bbCount * magicNumber)));
            }
            if (upgraded) {
                addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
            }
            // addToBot(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.exhaustPile, AbstractDungeon.player.exhaustPile.size()));
            return 0;
        }
        return i;
    }
}
