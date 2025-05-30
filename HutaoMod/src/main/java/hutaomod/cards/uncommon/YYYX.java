package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

public class YYYX extends HuTaoCard {
    public static final String ID = YYYX.class.getSimpleName();

    public YYYX() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        int yinCount = CacheManager.getInt(CacheManager.Key.YIN_CARDS);
        int yangCount = CacheManager.getInt(CacheManager.Key.YANG_CARDS);
        if (yinCount == yangCount) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, yinCount)));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, yangCount)));
        }
    }
    
    @Override
    public void triggerOnGlowCheck() {
        if (compareHandYY() == 0) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        } else {
            glowColor = BLUE_BORDER_GLOW_COLOR;
        }
    }
}
