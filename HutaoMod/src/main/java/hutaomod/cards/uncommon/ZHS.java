package hutaomod.cards.uncommon;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.powers.debuffs.SiPower;

public class ZHS extends HuTaoCard {
    public static final String ID = ZHS.class.getSimpleName();

    public ZHS() {
        super(ID);
        isInnate = true;
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ApplyPowerAction(p, p, new SiPower(p, magicNumber)));
        addToBot(new HealAction(p, p, magicNumber * (upgraded ? 2 : 1)));
        addToBot(new DrawCardAction(1));
    }
}
