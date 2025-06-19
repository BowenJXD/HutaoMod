package hutaomod.cards.base;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.characters.HuTao;
import hutaomod.modcore.CustomEnum;

public class HutaoE extends HuTaoCard {
    public static final String ID = HutaoE.class.getSimpleName();
    
    public HutaoE() {
        super(ID, HuTao.PlayerColorEnum.HUTAO_RED);
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new GainBlockAction(p, p, block + si));
        if (upgraded && yyTime > 0) {
            addToBot(new AddTemporaryHPAction(p, p, si * yyTime));
        }
    }
}
