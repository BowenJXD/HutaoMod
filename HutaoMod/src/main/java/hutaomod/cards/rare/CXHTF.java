package hutaomod.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;

public class CXHTF extends HuTaoCard {
    public static final String ID = CXHTF.class.getSimpleName();

    public CXHTF() {
        super(ID);
        exhaust = true;
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new VFXAction(new VerticalAuraEffect(Color.RED.cpy(), p.hb.cX, p.hb.cY)));
        addToBot(new BloodBurnAction(2));
        int energyGain = 2;
        addToBot(new GainEnergyAction(energyGain));
        int draw = magicNumber * yyTime; 
        addToBot(new DrawCardAction(draw));
    }
}
