/*
package hutaomod.cards.common;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.KeywordStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;

public class TLSL extends HuTaoCard {
    public static final String ID = TLSL.class.getSimpleName();

    public TLSL() {
        super(ID);
        exhaust = true;
        isInnate = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ClairvoirAction(c -> c.keywords.contains("stslib:墓地")).callback(list -> {
            addToTop(new GainBlockAction(p, p, list.size()));
        }));
        addToBot(new GainEnergyAction(1));
    }
}
*/
