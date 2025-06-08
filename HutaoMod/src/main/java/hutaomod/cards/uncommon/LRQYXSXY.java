package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.utils.GeneralUtil;

import java.util.List;

public class LRQYXSXY extends HuTaoCard {
    public static final String ID = LRQYXSXY.class.getSimpleName();

    public LRQYXSXY() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        if (yyTime > 0) {
            List<AbstractCard> cards = GeneralUtil.getRandomElements(p.hand.group, AbstractDungeon.cardRandomRng, yyTime, c -> {
                return c.hasTag(CustomEnum.YIN_YANG) && c.costForTurn > 0 && c != this;
            });
            for (AbstractCard card : cards) {
                addToBot(new ReduceCostForTurnAction(card, 1));
            }
        }
    }
}
