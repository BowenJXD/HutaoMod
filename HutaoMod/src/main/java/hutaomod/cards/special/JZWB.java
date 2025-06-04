package hutaomod.cards.special;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.rare.TDWX;
import hutaomod.characters.HuTao;
import hutaomod.effects.PortraitDisplayEffect;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;
import hutaomod.utils.PathDefine;

import java.util.Objects;

public class JZWB extends HuTaoCard {
    public static final String ID = JZWB.class.getSimpleName();
    int damageCache = 0;
    TDWX.Wubei wubei = null;

    public JZWB() {
        super(ID);
        damageCache = baseDamage;
    }
    
    public JZWB(TDWX.Wubei wubei) {
        super(ID, PathDefine.CARD_PATH + ID + '_' + wubei + ".png", HuTao.PlayerColorEnum.HUTAO_RED);
        this.wubei = wubei;
        damageCache = baseDamage;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new VFXAction(p, new PortraitDisplayEffect(wubei.toString()), 0F, true));
        addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY)));
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, magicNumber)));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int cardCount = ModHelper.findCardsInGroup(c -> Objects.equals(c.cardID, cardID), AbstractDungeon.player.discardPile).size();
        baseDamage = damageCache + cardCount;
        magicNumber = baseMagicNumber + cardCount;
        super.calculateCardDamage(mo);
        if (cardCount > 0) {
            isDamageModified = true;
            isMagicNumberModified = true;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractCard card = super.makeCopy();
        ((CustomCard)card).textureImg = textureImg;
        if (wubei != null) {
            ((JZWB) card).wubei = wubei;
        }
        return card;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (portraitImg == null) {
            portraitImg = ImageMaster.loadImage(textureImg);
        }
        if (portraitImg != null) {
            portrait = new TextureAtlas.AtlasRegion(portraitImg, 0, 0, portraitImg.getWidth(), portraitImg.getHeight());
        }
        super.render(sb);
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        ((CustomCard)card).textureImg = textureImg;
        if (wubei != null) {
            ((JZWB) card).wubei = wubei;
        }
        return card;
    }
}
