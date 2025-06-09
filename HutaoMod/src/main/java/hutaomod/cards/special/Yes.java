package hutaomod.cards.special;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.characters.HuTao;
import hutaomod.modcore.HuTaoMod;
import hutaomod.utils.PathDefine;

@AutoAdd.Ignore
public class Yes extends CustomCard {
    public static final String ID = Yes.class.getSimpleName();
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(HuTaoMod.makeID(ID));
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = PathDefine.CARD_PATH + ID + ".png";
    
    public Yes() {
        super(HuTaoMod.makeID(ID), NAME, IMG_PATH, -2, DESCRIPTION, CardType.STATUS, HuTao.PlayerColorEnum.HUTAO_RED, CardRarity.SPECIAL, CardTarget.NONE);
    }
    
    @Override
    public void upgrade() {}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void onChoseThisOption() {
        super.onChoseThisOption();
    }
}
