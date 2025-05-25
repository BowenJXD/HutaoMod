package hutaomod.cards.base;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAllAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.characters.HuTao;
import hutaomod.modcore.CustomEnum;
import hutaomod.relics.PapilioCharontis;
import hutaomod.utils.CacheManager;

public class HutaoQ extends HuTaoCard {
    public static final String ID = HutaoQ.class.getSimpleName();
    
    public HutaoQ() {
        super(ID, HuTao.PlayerColorEnum.HUTAO_RED);
        exhaust = true;
        GraveField.grave.set(this, true);
        isMultiDamage = true;
        tags.add(CustomEnum.YIN_YANG);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        int multiplier = 1;
        if (CacheManager.getBool(CacheManager.Key.HALF_HP)) multiplier *= 2;
        multiplier *= (int) Math.pow(2, yyTime);
        addToBot(new HealAction(p, p, (magicNumber + si) * multiplier));
        addToBot(new CardDamageAllAction(this, (damage + si) * multiplier, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.getRelic(PapilioCharontis.ID).counter >= 5) {
            si *= 2;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        yyTime = checkYinYang(false);
        if (yyTime > 0 && CacheManager.getBool(CacheManager.Key.HALF_HP)) {
            glowColor = ORANGE_BORDER_GLOW_COLOR;
        } else if (yyTime > 0 && CacheManager.getBool(CacheManager.Key.HALF_HP)) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        } else {
            glowColor = BLUE_BORDER_GLOW_COLOR;
        }
    }
}
