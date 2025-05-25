package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.utils.ModHelper;

public class SYSS extends HuTaoCard {
    public static final String ID = SYSS.class.getSimpleName();
    
    public SYSS() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        addToBot(new GainBlockAction(p, p, block));
        if (yyTime > 0) {
            for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                if (!ModHelper.check(mon)) continue;
                addToBot(new ApplyPowerAction(mon, p, new WeakPower(p, magicNumber * yyTime, false)));
            }
        }
    }
}
