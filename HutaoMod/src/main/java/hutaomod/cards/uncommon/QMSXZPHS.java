package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class QMSXZPHS extends HuTaoCard {
    public static final String ID = QMSXZPHS.class.getSimpleName();

    public QMSXZPHS() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ScrayAction(c -> c.type == CardType.SKILL).callback(cards -> {
            addToTop(new GainBlockAction(p, p, block + cards.size()));
        }));
    }
}
