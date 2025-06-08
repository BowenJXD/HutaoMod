package hutaomod.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import hutaomod.actions.BounceAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.ModHelper;

public class DYLSQ extends HuTaoCard {
    public static final String ID = DYLSQ.class.getSimpleName();
    int costCache;

    public DYLSQ() {
        super(ID);
        costCache = cost;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        GraveField.grave.set(this, true);
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED.cpy())));
        addToBot(new BounceAction(m, this, magicNumber, mon -> {
            addToTop(new CardDamageAction(mon, (upgraded ? damage : 0) + si, this, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }));
        ModHelper.addToBotAbstract(() -> updateCost(costCache - cost));
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        addToBot(new ReduceCostAction(this));
        addToTop(new VFXAction(new ShowCardBrieflyEffect(makeStatEquivalentCopy(),
                Settings.WIDTH * MathUtils.random(0.2f, 0.8f),
                Settings.HEIGHT * MathUtils.random(0.3f, 0.7f))));
    }
}
