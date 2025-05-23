package hutaomod.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.characters.HuTao;
import hutaomod.modcore.CustomEnum;
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
    
    public int yyTime = 0;
    public YYState yy = YYState.NONE;

    public boolean inHand = false;

    public CardStrings cardStrings;

    public HuTaoCard(String id, String imgPath, CardColor color){
        super(HuTaoMod.makeID(id),
                DataManager.getInstance().getCardData(id, CardDataCol.Name),
                imgPath,
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
        if (yinyang.contains("阴")) yy = YYState.YIN;
        else if (yinyang.contains("阳")) yy = YYState.YANG;
        else if (yinyang.contains("☯️")) yy = YYState.YINYANG;
        else yy = YYState.NONE;
        
        this.exhaust = rawDescription.contains("消耗。");
        this.selfRetain = rawDescription.contains("保留。");
        this.isInnate = rawDescription.contains("固有。");
        this.isEthereal = rawDescription.contains("虚无。");

        CommonKeywordIconsField.useIcons.set(this, true);
        assetUrl = "HuTaoMod/" + id + "_s_p.png";
    }

    public HuTaoCard(String id, CardColor color) {
        this(id, PathDefine.CARD_PATH + id + ".png", color);
    }

    public HuTaoCard(String id) {
        this(id, null, HuTao.PlayerColorEnum.HUTAO_RED);
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
    public void applyPowers() {
        super.applyPowers();
        si = CacheManager.getInt(CacheManager.Key.PLAYER_SI);
        isSiModified = si == 0;
    }

    public void onEnterHand() { }
    public void onLeaveHand() { }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        yyTime = checkYinYang(true);
        onUse(p, m, yyTime);
        useYY(p, m);
    }
    
    public void useYY(AbstractPlayer p, AbstractMonster m) {
        switch (yy) {
            case YIN:
                addToBot(new ApplyPowerAction(p, p, new SiPower(p, 1)));
                break;
            case YANG:
                addToBot(new ApplyPowerAction(p, p, new SiPower(p, -1)));
                break;
            case YINYANG:
                if (CacheManager.getBool(CacheManager.Key.DYING)) {
                    addToBot(new ApplyPowerAction(p, p, new SiPower(p, -1)));
                } else {
                    addToBot(new ApplyPowerAction(p, p, new SiPower(p, 1)));
                }
        }
    }

    public abstract void onUse(AbstractPlayer p, AbstractMonster m, int yyTime);
    
    public int checkYinYang(boolean onUse) {
        int result = 0;
        int y = CacheManager.getInt(CacheManager.Key.PLAYER_SI);
        boolean dying = CacheManager.getBool(CacheManager.Key.DYING);
        if (yy == YYState.YIN && dying != SiPower.isDying(y+1)) result = 1;
        else if (yy == YYState.YANG && dying != SiPower.isDying(y-1)) result = 1;
        else if (yy == YYState.YINYANG && si <= 0) result = 1;
        else if (yy == YYState.YINYANG && dying != SiPower.isDying(y-1)) result = 1;
        return SubscriptionManager.getInstance().triggerCheckYinYang(this, result, onUse);
    }
    
    public void onDieying(boolean in) {}

    @Override   
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        yyTime = checkYinYang(false);
        if (tags.contains(CustomEnum.YIN_YANG) && yyTime > 0) {
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
        } else {
            glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;    
        }
    }

    public static boolean isYin(AbstractCard card) {
        if (card instanceof HuTaoCard) {
            return ((HuTaoCard) card).yy == YYState.YIN;
        }
        return false;
    }
    
    public static boolean isYang(AbstractCard card) {
        if (card instanceof HuTaoCard) {
            return ((HuTaoCard) card).yy == YYState.YANG;
        }
        return false;
    }
    
    public enum YYState {
        NONE,
        YIN,
        YANG,
        YINYANG,
    }
}
