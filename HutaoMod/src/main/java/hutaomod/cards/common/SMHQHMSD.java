package hutaomod.cards.common;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.ModHelper;

public class SMHQHMSD extends HuTaoCard {
    public static final String ID = SMHQHMSD.class.getSimpleName();
    
    public SMHQHMSD() {
        super(ID);
        GraveField.grave.set(this, true);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        AbstractMonster m = ModHelper.betterGetRandomMonster();
        if (m == null) return;
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (upgraded) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 1)));
        }
        addToTop(new VFXAction(new ShowCardBrieflyEffect(makeStatEquivalentCopy(),
                Settings.WIDTH * MathUtils.random(0.2f, 0.8f),
                Settings.HEIGHT * MathUtils.random(0.3f, 0.7f))));
        addToTop(new VFXAction(new HemokinesisEffect(CardGroup.DISCARD_PILE_X, CardGroup.DISCARD_PILE_Y, m.hb.cX, m.hb.cY)));
    }
}
