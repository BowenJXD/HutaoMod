package hutaomod.cards.uncommon;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.buffs.BreathPower;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class DRMJ extends HuTaoCard {
    public static final String ID = DRMJ.class.getSimpleName();

    public DRMJ() {
        super(ID);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ApplyPowerAction(p, p, new BreathPower(p, magicNumber)));
        if (yyTime > 0 && upgraded) {
            addToBot(new DrawCardAction(magicNumber * yyTime));
        }
    }
}
