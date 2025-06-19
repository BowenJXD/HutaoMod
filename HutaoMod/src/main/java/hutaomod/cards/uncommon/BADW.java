package hutaomod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.modifiers.DieyingModifier;
import hutaomod.utils.GeneralUtil;

import java.util.List;
import java.util.stream.Collectors;

public class BADW extends HuTaoCard {
    public static final String ID = BADW.class.getSimpleName();

    public BADW() {
        super(ID);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        GraveField.grave.set(this, true);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        if (upgraded) {
            for (AbstractCard card : p.hand.group) {
                if (card != this) {
                    CardModifierManager.addModifier(card, new DieyingModifier());
                }
            }
        }
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        List<AbstractCard> cards = AbstractDungeon.player.hand.group.stream().filter(c -> !CardModifierManager.hasModifier(c, DieyingModifier.ID)).collect(Collectors.toList());
        if (!cards.isEmpty()) {
            AbstractCard card = GeneralUtil.getRandomElement(cards, AbstractDungeon.cardRandomRng);
            if (card != null) {
                CardModifierManager.addModifier(card, new DieyingModifier());
                card.flash();
            }
        }
    }
}
