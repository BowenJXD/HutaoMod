package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class SYAZ extends HuTaoCard {
    public static final String ID = SYAZ.class.getSimpleName();

    public SYAZ() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        ModHelper.addToBotAbstract(() -> {
            int count = 0;
            for (AbstractCard c : p.hand.group) {
                if (c.exhaust) count++;
                else c.exhaust = true;
            }
            if (count > 0) {
                if (yyTime > 0) {
                    addToTop(new HealAction(p, p, count * yyTime));
                }
                addToTop(new CardDamageAction(m, damage + count, this, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        });
    }
}
