package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.buffs.EndTurnClairvoirPower;
import hutaomod.powers.debuffs.BloodBlossomPower;

import java.util.Objects;

public class LMSDYTCZFJ extends HuTaoCard {
    public static final String ID = LMSDYTCZFJ.class.getSimpleName();

    public LMSDYTCZFJ() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        addToTop(new ScrayAction(magicNumber, c -> !Objects.equals(cardID, c.cardID)));
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EndTurnClairvoirPower(AbstractDungeon.player, 1)));
    }
}
