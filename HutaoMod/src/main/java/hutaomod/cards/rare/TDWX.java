package hutaomod.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.special.JZWB;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.powers.powers.BBLPower;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

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
        AbstractCard card = new JZWB();
        addToBot(new MakeTempCardInHandAction(card, count));
    }
}
