package hutaomod.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.special.JZWB;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.powers.powers.BBLPower;
import hutaomod.utils.CacheManager;
import hutaomod.utils.GeneralUtil;
import hutaomod.utils.ModHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TDWX extends HuTaoCard {
    public static final String ID = TDWX.class.getSimpleName();
    
    

    public TDWX() {
        super(ID);
        exhaust = true;
        GraveField.grave.set(this, true);
        cardsToPreview = new JZWB();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        cardsToPreview.upgrade();
        selfRetain = true;
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new VFXAction(new SpotlightEffect()));
        int[] array = new int[] {si, CacheManager.getInt(CacheManager.Key.YANG_CARDS), ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID)};
        int count = 1;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (i != j && array[i] == array[j]) {
                    count++;
                }
            }
        }
        // AbstractCard card = new JZWB();
        // if (upgraded) card.upgrade();
        // addToBot(new MakeTempCardInHandAction(card, 1));
        List<Wubei> wubeiList = GeneralUtil.getRandomElements(Arrays.asList(Wubei.values()), AbstractDungeon.cardRandomRng, count);
        for (Wubei wubei : wubeiList) {
            AbstractCard card = new JZWB(wubei);
            if (upgraded) card.upgrade();
            addToBot(new MakeTempCardInHandAction(card, 1));
        }
    }
    
    public enum Wubei {
        XL, //XIANGLING,
        XQ, //XINGQIU,
        CY, //CHONGYUN,
        XY, //XINYAN,
        YJ, //YUNJIN,
        GM, //GAMING,
        LY; //LANYAN
    }
}
