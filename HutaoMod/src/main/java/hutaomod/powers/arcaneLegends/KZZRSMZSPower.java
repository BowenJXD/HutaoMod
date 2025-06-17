package hutaomod.powers.arcaneLegends;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GAMManager;

public class KZZRSMZSPower extends PowerPower {
    public static final String ID = HuTaoMod.makeID(KZZRSMZSPower.class.getSimpleName());
    
    public KZZRSMZSPower() {
        super(ID);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        GAMManager.addParallelAction(ID, action -> {
            if (action instanceof BloodBurnAction) {
                GAMManager.stopCurrentAction();
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    addToTop(new ApplyPowerAction(monster, action.source, new BloodBlossomPower(monster, action.source, action.amount)));
                }
                addToTop(new ApplyPowerAction(action.target, action.source, new BloodBlossomPower(action.target, action.source, action.amount)));
            }
            return !SubscriptionManager.checkSubscriber(this);
        });
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.baseBlock > 0) {
            BloodBlossomPower.fadePower(owner);
        }
    }
}
