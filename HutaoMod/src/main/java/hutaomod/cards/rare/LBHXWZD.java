package hutaomod.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class LBHXWZD extends HuTaoCard {
    public static final String ID = LBHXWZD.class.getSimpleName();

    boolean subscribed;
    
    public LBHXWZD() {
        super(ID);
        GraveField.grave.set(this, true);
        exhaust = true;
        isEthereal = true;
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new VFXAction(new ReaperEffect()));
        addToBot(new CardDamageAction(m, damage + si, this, AbstractGameAction.AttackEffect.SMASH));
        addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, magicNumber)));
        addToBot(new HealAction(p, p, magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        updateVariables();
        super.calculateCardDamage(mo);
    }

    @Override
    public void tookDamage() {
        super.tookDamage();
        updateVariables();
    }
    
    void updateVariables() {
        magicNumber = GameActionManager.hpLossThisCombat;
        if (upgraded) baseDamage = GameActionManager.hpLossThisCombat;
        if (GameActionManager.hpLossThisCombat > 0) {
            isMagicNumberModified = true;
            isDamageModified = true;
        }
    }
}
