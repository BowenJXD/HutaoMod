package hutaomod.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.CardDamageAllAction;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

public class MYWC extends HuTaoCard {
    public static final String ID = MYWC.class.getSimpleName();

    public MYWC() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        int yinCount = CacheManager.getInt(CacheManager.Key.YIN_CARDS);
        int yangCount = CacheManager.getInt(CacheManager.Key.YANG_CARDS);
        if (yinCount != yangCount) {
            int bbCount = ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID);
            int diff = Math.max(yinCount, yangCount) - bbCount;
            if (diff > 0) {
                addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, diff)));
            } else if (diff < 0) {
                addToBot(new ReducePowerAction(m, p, BloodBlossomPower.POWER_ID, -diff));
            }
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (compareHandYY() > 0) {
            glowColor = WHITE_BORDER_GLOW_COLOR;
        } else {
            glowColor = BLACK_BORDER_GLOW_COLOR;
        }
    }
}
