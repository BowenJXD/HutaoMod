package hutaomod.cards.base;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAllAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.characters.HuTao;
import hutaomod.effects.PortraitDisplayEffect;
import hutaomod.effects.ShadowEffect;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.relics.PapilioCharontis;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

import java.util.concurrent.atomic.AtomicBoolean;

public class HutaoQ extends HuTaoCard {
    public static final String ID = HutaoQ.class.getSimpleName();
    public boolean specialUpgrade = false;
    
    public HutaoQ() {
        super(ID, HuTao.PlayerColorEnum.HUTAO_RED);
        exhaust = true;
        GraveField.grave.set(this, true);
        isMultiDamage = true;
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onEnterHand() {
        super.onEnterHand();
        if (!specialUpgrade) {
            AbstractRelic papilio = AbstractDungeon.player.getRelic(HuTaoMod.makeID(PapilioCharontis.ID));
            if (papilio != null && papilio.counter >= 5) {
                specialUpgrade = true;
            }
        }
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new VFXAction(p, new PortraitDisplayEffect("HuTao"), 0F, true));
        int index = MathUtils.random(0, cardStrings.EXTENDED_DESCRIPTION.length-1);
        ModHelper.playSound("ult_" + (index+1));
        addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[index], 2.0F, 3.0F));
        addToBot(new VFXAction(new BorderFlashEffect(Color.RED)));
        
        int multiplier = 1;
        if (CacheManager.getBool(CacheManager.Key.HALF_HP) && specialUpgrade) multiplier *= 2;
        multiplier *= (int) Math.pow(2, yyTime);
        addToBot(new HealAction(p, p, (magicNumber + si) * multiplier));
        AtomicBoolean triggered = new AtomicBoolean(false);
        addToBot(new CardDamageAllAction(this, (damage + si) * multiplier, AbstractGameAction.AttackEffect.FIRE).setCallback(ci -> {
            if (!triggered.get()) {
                triggered.set(true);
                for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                    if (ModHelper.check(mon)) {
                        Texture img = ReflectionHacks.getPrivate(mon, AbstractMonster.class, "img");
                        if (img != null) {
                            AbstractDungeon.effectList.add(new ShadowEffect(img, mon.hb.cX - mon.hb.width / 4, mon.hb.cY - mon.hb.height / 4, 40f, 1f, Color.RED));
                        }
                    }
                }
            }
        }));
        if (upgraded) {
            for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                addToBot(new ApplyPowerAction(mon, p, new BloodBlossomPower(mon, p, multiplier)));
            }
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        yyTime = checkYinYang(false);
        if (yyTime > 0 && CacheManager.getBool(CacheManager.Key.HALF_HP) && specialUpgrade) {
            glowColor = RED_BORDER_GLOW_COLOR;
        } else if (yyTime > 0 || (CacheManager.getBool(CacheManager.Key.HALF_HP) && specialUpgrade)) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        } else {
            glowColor = ORANGE_BORDER_GLOW_COLOR;
        }
    }
}
