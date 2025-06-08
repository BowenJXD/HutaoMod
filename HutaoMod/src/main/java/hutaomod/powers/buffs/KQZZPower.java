package hutaomod.powers.buffs;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerLoseBlockSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.BuffPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.ModHelper;

public class KQZZPower extends BuffPower {
    public static final String POWER_ID = HuTaoMod.makeID(KQZZPower.class.getSimpleName());
    
    public KQZZPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        updateDescription();
    }
    
    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        AbstractMonster monster = ModHelper.betterGetRandomMonster();
        if (monster != null && owner.currentBlock > 0) {
            addToBot(new DamageAction(monster, new DamageInfo(owner, owner.currentBlock * amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}
