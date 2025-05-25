package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.CardDamageAllAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class JRZS extends HuTaoCard {
    public static final String ID = JRZS.class.getSimpleName();
    
    public JRZS() {
        super(ID);
        exhaust = true;
        isInnate = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        isMultiDamage = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        if (!upgraded) {
            addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, magicNumber)));
        } else {
            addToBot(new CardDamageAllAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                if (!ModHelper.check(mon)) continue;
                addToBot(new ApplyPowerAction(mon, p, new BloodBlossomPower(mon, p, magicNumber)));
            }
        }
    }
}
