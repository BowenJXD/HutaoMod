package hutaomod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.mod.stslib.patches.CommonKeywordIconsPatches;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.PathDefine;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.Objects;

public class KeywordBadgesPatch {
    public static String yinName = "hutaomod:阴";
    public static String yangName = "hutaomod:阳";
    public static String yinYangName = "hutaomod:阴阳牌";
    public static final Texture yinTexture = ImageMaster.loadImage(PathDefine.UI_PATH + "badges/yin.png");
    public static final Texture yangTexture = ImageMaster.loadImage(PathDefine.UI_PATH + "badges/yang.png");
    public static final Texture yinYangTexture = ImageMaster.loadImage(PathDefine.UI_PATH + "badges/yinyang.png");
    
    @SpirePatch(clz = CommonKeywordIconsPatches.class, method = "addKeywords")
    public static class AddKeywordsPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard c, ArrayList<String> kws) {
            if (!(c instanceof HuTaoCard)) return;
            HuTaoCard card = (HuTaoCard) c;
            if (card.yy == HuTaoCard.YYState.YINYANG) {
                kws.add(yinYangName);
            } else if (card.yy == HuTaoCard.YYState.YIN) {
                kws.add(yinName);
            } else if (card.yy == HuTaoCard.YYState.YANG) {
                kws.add(yangName);
            }
        }
    }

    @SpirePatch(clz = CommonKeywordIconsPatches.class, method = "RenderBadges")
    public static class RenderBadgesPatch {
        @SpireInsertPatch(rloc = 3, localvars = {"offset_y"})
        public static void Insert(SpriteBatch sb, AbstractCard card, @ByRef int[] offset_y) {
            if (!(card instanceof HuTaoCard)) return;
            HuTaoCard c = (HuTaoCard) card;
            Texture texture = null;
            if (c.yy == HuTaoCard.YYState.YINYANG) {
                texture = yinYangTexture;
            } else if (c.yy == HuTaoCard.YYState.YIN) {
                texture = yinTexture;
            } else if (c.yy == HuTaoCard.YYState.YANG) {
                texture = yangTexture;
            }
            if (texture != null) {
                offset_y[0] = (int) (offset_y[0] - renderBadge(sb, card, texture, offset_y[0], card.transparency));
            }
        }
    }
    
    @SpirePatch(clz = CommonKeywordIconsPatches.RenderIconOnTips.class, method = "patch")
    public static class RenderIconOnTipsPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = {"badge"})
        public static void Insert(SpriteBatch sb, String word, float x, float y, AbstractCard ___card, @ByRef Texture[] badge) {
            if (!(___card instanceof HuTaoCard)) return;
            HuTaoCard c = (HuTaoCard) ___card;
            if (c.yy == HuTaoCard.YYState.YINYANG && Objects.equals(word, yinYangName)) {
                badge[0] = yinYangTexture;
            } else if (c.yy == HuTaoCard.YYState.YIN && Objects.equals(word, yinName)) {
                badge[0] = yinTexture;
            } else if (c.yy == HuTaoCard.YYState.YANG && Objects.equals(word, yangName)) {
                badge[0] = yangTexture;
            }
        }
        
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(String.class, "equals");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = CommonKeywordIconsPatches.SingleCardViewRenderIconOnCard.class, method = "patch")
    public static class SingleCardViewRenderIconOnCardPatch {
        @SpireInsertPatch(rloc = 2, localvars = {"offset_y"})
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card, Hitbox ___cardHb, @ByRef int[] offset_y) {
            if (!(___card instanceof HuTaoCard)) return;
            HuTaoCard c = (HuTaoCard) ___card;
            Texture texture = null;
            if (c.yy == HuTaoCard.YYState.YINYANG) {
                texture = yinYangTexture;
            } else if (c.yy == HuTaoCard.YYState.YIN) {
                texture = yinTexture;
            } else if (c.yy == HuTaoCard.YYState.YANG) {
                texture = yangTexture;
            }
            if (texture != null) {
                drawBadge(sb, ___card, ___cardHb, texture, offset_y[0]);
                offset_y[0]++;
            }
        }
    }
    
    @SpirePatch(clz = CommonKeywordIconsPatches.SingleCardViewRenderIconOnTips.class, method = "patch")
    public static class SingleCardViewRenderIconOnTipsPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = {"badge"})
        public static void Insert(float x, float y, SpriteBatch sb, ArrayList<PowerTip> powerTips, PowerTip tip, @ByRef Texture[] badge) {
            if (tip.header.equalsIgnoreCase(yinYangName)) {
                badge[0] = yinYangTexture;
            } else if (tip.header.equalsIgnoreCase(yinName)) {
                badge[0] = yinTexture;
            } else if (tip.header.equalsIgnoreCase(yangName)) {
                badge[0] = yangTexture;
            }
        }
        
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(PowerTip.class, "header");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }
    

    private static float renderBadge(SpriteBatch sb, AbstractCard card, Texture texture, float offset_y, float alpha) {
        Vector2 offset = new Vector2(135.0F, 189.0F + offset_y);
        drawOnCardAuto(sb, card, texture, offset, 64.0F, 64.0F, Color.WHITE, alpha, 1.0F);
        return 38.0F;
    }

    private static void drawOnCardAuto(SpriteBatch sb, AbstractCard card, Texture img, Vector2 offset, float width, float height, Color color, float alpha, float scaleModifier) {
        if (card.angle != 0.0F) {
            offset.rotate(card.angle);
        }

        offset.scl(Settings.scale * card.drawScale);
        drawOnCardCentered(sb, card, new Color(color.r, color.g, color.b, alpha), img, card.current_x + offset.x, card.current_y + offset.y, width, height, scaleModifier);
    }

    private static void drawOnCardCentered(SpriteBatch sb, AbstractCard card, Color color, Texture img, float drawX, float drawY, float width, float height, float scaleModifier) {
        float scale = card.drawScale * Settings.scale * scaleModifier;
        sb.setColor(color);
        sb.draw(img, drawX - width / 2.0F, drawY - height / 2.0F, width / 2.0F, height / 2.0F, width, height, scale, scale, card.angle, 0, 0, img.getWidth(), img.getHeight(), false, false);
    }

    private static void drawBadge(SpriteBatch sb, AbstractCard card, Hitbox hb, Texture img, int offset) {
        float badge_w = (float)img.getWidth();
        float badge_h = (float)img.getHeight();
        sb.draw(img, hb.x + hb.width - badge_w * Settings.scale * 0.66F, hb.y + hb.height - badge_h * Settings.scale * 0.5F - (float)offset * badge_h * Settings.scale * 0.6F, 0.0F, 0.0F, badge_w, badge_h, Settings.scale, Settings.scale, card.angle, 0, 0, (int)badge_w, (int)badge_h, false, false);
    }
}
