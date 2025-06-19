package hutaomod.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.CardDataCol;
import hutaomod.utils.DataManager;
import hutaomod.utils.ModHelper;

public class ZN extends HuTaoCard {
    public static final String ID = ZN.class.getSimpleName();

    AbstractMonster monsterCache;
    
    public ZN() {
        super(ID);
    }
    
    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        upgradeDamage(magicNumber);
        upgradeBlock(magicNumber);
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = DataManager.getInstance().getCardData(ID, CardDataCol.Name) + "+" + this.timesUpgraded;
        initializeTitle();
        this.initializeDescription();
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        monsterCache = m;
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new GainBlockAction(p, p, block));
        AbstractMonster m = (ModHelper.check(monsterCache)) ? monsterCache : ModHelper.betterGetRandomMonster();
        monsterCache = null;
        if (m == null) return;
        ModHelper.addEffectAbstract(() -> {
            for(int i = 0; i < 25; ++i) {
                AbstractDungeon.effectsQueue.add(new FireBurstParticleEffect(m.hb.cX, m.hb.cY));
                AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(m.hb.cX, m.hb.cY, Color.ORANGE));
            }
        });
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new UpgradeSpecificCardAction(this));
        addToTop(new VFXAction(new ShowCardBrieflyEffect(makeStatEquivalentCopy(),
                Settings.WIDTH * MathUtils.random(0.2f, 0.8f),
                Settings.HEIGHT * MathUtils.random(0.3f, 0.7f))));
    }
}
