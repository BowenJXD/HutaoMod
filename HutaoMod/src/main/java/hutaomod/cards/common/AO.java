package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;

public class AO extends HuTaoCard {
    public static final String ID = AO.class.getSimpleName();
    
    public AO() {
        super(ID);
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(1));
        int handYY = compareHandYY();
        if (handYY > 0) {
            addToBot(new ClairvoirAction(1, HuTaoCard::isYin));
        } else if (handYY < 0) {
            addToBot(new ClairvoirAction(1, HuTaoCard::isYang));
        } else if (upgraded) {
            exhaust = true;
            addToBot(new ClairvoirAction(1, HuTaoCard::isYin));
            addToBot(new ClairvoirAction(1, HuTaoCard::isYang));
        }
    }
    
    @Override
    public void triggerOnGlowCheck() {
        if (compareHandYY() > 0 && AbstractDungeon.player.discardPile.group.stream().anyMatch(HuTaoCard::isYin)) {
            glowColor = WHITE_BORDER_GLOW_COLOR;
        } else if (compareHandYY() < 0 && AbstractDungeon.player.discardPile.group.stream().anyMatch(HuTaoCard::isYang)){
            glowColor = BLACK_BORDER_GLOW_COLOR;
        } else if (upgraded && AbstractDungeon.player.discardPile.group.stream().anyMatch(c -> (HuTaoCard.isYin(c) || HuTaoCard.isYang(c)))) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        } else {
            glowColor = ORANGE_BORDER_GLOW_COLOR;
        }
    }
}
