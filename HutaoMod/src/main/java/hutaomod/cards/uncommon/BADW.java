package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class BADW extends HuTaoCard {
    public static final String ID = BADW.class.getSimpleName();

    public BADW() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new BloodBlossomPower(monster, AbstractDungeon.player, magicNumber)));
        }
    }
}
