package hutaomod.powers.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.utils.GeneralUtil;

import java.util.List;
import java.util.stream.Collectors;

public class WSTPower extends PowerPower {
    public static final String POWER_ID = HuTaoMod.makeID(WSTPower.class.getSimpleName());
    
    public WSTPower(int limit) {
        super(POWER_ID, 1);
        setLimit(limit);
        
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = GeneralUtil.tryFormat(DESCRIPTIONS[0], limit, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card instanceof HuTaoCard) {
            HuTaoCard huTaoCard = (HuTaoCard) card;
            if (huTaoCard.yy == HuTaoCard.YYState.YIN) {
                stackPower(1);
            }
        }
    }

    @Override
    public void onLimitReached() {
        super.onLimitReached();
        addToBot(new ApplyPowerAction(owner, owner, new SiPower(owner, 1)));
        List<AbstractCard> cards = AbstractDungeon.player.hand.group.stream().filter(c -> c instanceof HuTaoCard && ((HuTaoCard)c).yy == HuTaoCard.YYState.YANG && c.costForTurn > 0).collect(Collectors.toList());
        if (!cards.isEmpty()) {
            AbstractCard card = cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
            addToBot(new ReduceCostForTurnAction(card, 1));
        }
        reducePower(amount);
    }
}
