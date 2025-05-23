package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.CacheManager;

public class YNYY extends HuTaoCard {
    public static final String ID = YNYY.class.getSimpleName();

    public YNYY() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        int yinCount = CacheManager.getInt(CacheManager.Key.YIN_CARDS);
        int yangCount = CacheManager.getInt(CacheManager.Key.YANG_CARDS);
        int handCount = p.hand.size();
        if (yinCount == yangCount && handCount > 0) {
            addToBot(new CardDamageAction(m, new DamageInfo(p, handCount + (upgraded ? damage : 0)), this, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            addToBot(new GainBlockAction(p, p, handCount + (upgraded ? block : 0)));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        int yinCount = CacheManager.getInt(CacheManager.Key.YIN_CARDS);
        int yangCount = CacheManager.getInt(CacheManager.Key.YANG_CARDS);
        int handCount = AbstractDungeon.player.hand.size();
        if (yinCount == yangCount && handCount > 0) {
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}
