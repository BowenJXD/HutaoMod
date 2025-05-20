package hutaomod.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.characters.HuTao;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.utils.*;

import java.util.Objects;

public abstract class HuTaoCard extends CustomCard {
    protected int upCost;
    protected String upDescription;
    protected int upDamage;
    protected int upBlock;
    protected int upMagicNumber;

    public int si;
    public boolean isSiModified;
    
    public boolean yin;
    public boolean yang;

    public boolean inHand = false;

    public CardStrings cardStrings;

    public HuTaoCard(String id, String resourcePath, CardColor color){
        super(Objects.equals(resourcePath, PathDefine.CARD_PATH) ? HuTaoMod.makeID(id) : id,
                DataManager.getInstance().getCardData(id, CardDataCol.Name),
                resourcePath + id + ".png",
                DataManager.getInstance().getCardDataInt(id, CardDataCol.Cost),
                DataManager.getInstance().getCardData(id, CardDataCol.Description),
                CardType.valueOf(DataManager.getInstance().getCardData(id, CardDataCol.Type)),
                color,
                CardRarity.valueOf(DataManager.getInstance().getCardData(id, CardDataCol.Rarity)),
                CardTarget.valueOf(DataManager.getInstance().getCardData(id, CardDataCol.Target))
        );
        cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);

        this.damage = this.baseDamage = DataManager.getInstance().getCardDataInt(id, CardDataCol.Damage);
        this.block = this.baseBlock = DataManager.getInstance().getCardDataInt(id, CardDataCol.Block);
        this.magicNumber = this.baseMagicNumber = DataManager.getInstance().getCardDataInt(id, CardDataCol.MagicNumber);
        
        this.upCost = DataManager.getInstance().getCardDataInt(id, CardDataCol.UpgradeCost);
        this.upDescription = DataManager.getInstance().getCardData(id, CardDataCol.UpgradeDescription);
        this.upDamage = DataManager.getInstance().getCardDataInt(id, CardDataCol.UpgradeDamage);
        this.upBlock = DataManager.getInstance().getCardDataInt(id, CardDataCol.UpgradeBlock);
        this.upMagicNumber = DataManager.getInstance().getCardDataInt(id, CardDataCol.UpgradeMagicNumber);
        
        String yinyang = DataManager.getInstance().getCardData(id, CardDataCol.YinYang);
        if (yinyang.contains("阴")) yin = true;
        if (yinyang.contains("阳")) yang = true;
        
        this.exhaust = rawDescription.contains("消耗。");
        this.selfRetain = rawDescription.contains("保留。");
        this.isInnate = rawDescription.contains("固有。");
        this.isEthereal = rawDescription.contains("虚无。");

        CommonKeywordIconsField.useIcons.set(this, true);
        assetUrl = "HuTaoMod/" + id + "_s_p.png";
    }

    public HuTaoCard(String id, CardColor color) {
        this(id, PathDefine.CARD_PATH, color);
    }

    public HuTaoCard(String id) {
        this(id, PathDefine.CARD_PATH, HuTao.PlayerColorEnum.HUTAO_RED);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (upCost != DataManager.NULL_INT) {
                updateCost(upCost - cost);
            }
            if (!Objects.equals(upDescription, "")) {
                this.rawDescription = upDescription;
                initializeDescription();
            }
            if (upDamage != DataManager.NULL_INT) {
                upgradeDamage(upDamage - baseDamage);
            }
            if (upBlock != DataManager.NULL_INT) {
                upgradeBlock(upBlock - baseBlock);
            }
            if (upMagicNumber != DataManager.NULL_INT) {
                upgradeMagicNumber(upMagicNumber - baseMagicNumber);
            }
        }
        this.exhaust = upDescription.contains("消耗。");
        this.selfRetain = upDescription.contains("保留。");
        this.isInnate = upDescription.contains("固有。");
        this.isEthereal = upDescription.contains("虚无。");
    }

    @Override
    public void initializeDescriptionCN() {
        si = CacheManager.getInteger(CacheManager.Key.PLAYER_SI);
        if (si == 0) rawDescription = rawDescription.replaceAll(" !Y! ", "Y");
        super.initializeDescriptionCN();
    }

    public void onEnterHand() { }
    public void onLeaveHand() { }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean yinyang = checkYinYang();
        onUse(p, m, yinyang);
        if (yin && yang) {
            if (CacheManager.getBoolean(CacheManager.Key.DYING)) {
                addToBot(new ApplyPowerAction(p, p, new SiPower(p, -1)));
            } else {
                addToBot(new ApplyPowerAction(p, p, new SiPower(p, 1)));
            }
        } else if (yin) {
            addToBot(new ApplyPowerAction(p, p, new SiPower(p, 1)));
        } else if (yang) {
            addToBot(new ApplyPowerAction(p, p, new SiPower(p, -1)));
        }
    }

    public abstract void onUse(AbstractPlayer p, AbstractMonster m, boolean yinyang);
    
    public boolean checkYinYang() {
        int y = CacheManager.getInteger(CacheManager.Key.PLAYER_SI);
        boolean dying = CacheManager.getBoolean(CacheManager.Key.DYING);
        if (yang && dying != SiPower.isDying(y+1)) return true;
        if (yin && dying != SiPower.isDying(y-1)) return true;
        return SubscriptionManager.getInstance().triggerCheckYinYang(this);
    } 
}
