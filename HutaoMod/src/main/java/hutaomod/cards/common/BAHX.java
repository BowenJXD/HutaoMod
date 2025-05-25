package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.buffs.BreathPower;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class BAHX extends HuTaoCard {
    public static final String ID = BAHX.class.getSimpleName();
    
    public BAHX() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(magicNumber));
        addToBot(new GainBlockAction(p, p, si + (upgraded ? block : 0)));
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
            if (!ModHelper.check(mon)) continue;
            addToBot(new ApplyPowerAction(mon, p, new BloodBlossomPower(mon, p, magicNumber)));
        }
    }
}
