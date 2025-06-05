package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.CardDamageAllAction;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;

import java.util.Collections;
import java.util.List;

public class TSXJ extends HuTaoCard {
    public static final String ID = TSXJ.class.getSimpleName();
    
    public TSXJ() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        isMultiDamage = true;
        target = CardTarget.ALL_ENEMY;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        List<AbstractMonster> targets = m != null ? Collections.singletonList(m) : AbstractDungeon.getMonsters().monsters;
        if (m != null && !isMultiDamage) {
            addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else {
            addToBot(new CardDamageAllAction(this, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        int handYY = compareHandYY();
        if (handYY > 0 || yyTime > 0) {
            for (AbstractMonster target : targets) {
                addToBot(new ApplyPowerAction(target, p, new VulnerablePower(target, magicNumber, false)));
            }
        }
        if (handYY < 0 || yyTime > 0) {
            for (AbstractMonster target : targets) {
                addToBot(new ApplyPowerAction(target, p, new WeakPower(target, magicNumber, false)));
            }
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (yyTime > 0) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        } else if (compareHandYY() > 0) {
            glowColor = WHITE_BORDER_GLOW_COLOR;
        } else if (compareHandYY() < 0){
            glowColor = BLACK_BORDER_GLOW_COLOR;
        }
    }
}
