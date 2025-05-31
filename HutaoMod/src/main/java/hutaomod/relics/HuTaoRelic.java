package hutaomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import hutaomod.modcore.HuTaoMod;
import hutaomod.utils.ModHelper;
import hutaomod.utils.PathDefine;

import java.util.Objects;

public abstract class HuTaoRelic extends CustomRelic {
    
    public HuTaoRelic(String id, String resourcePath, RelicTier tier) {
        super(Objects.equals(resourcePath, PathDefine.RELIC_PATH) ? HuTaoMod.makeID(id) : id,
                ImageMaster.loadImage(resourcePath + id + ".png"),
                ImageMaster.loadImage(resourcePath + "outline/" + id + ".png"),
                tier,
                LandingSound.FLAT
        );
    }

    public HuTaoRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
    }

    public HuTaoRelic(String id, String imgName, RelicTier tier, LandingSound sfx) {
        super(id, imgName, tier, sfx);
    }
        
    public HuTaoRelic(String id, RelicTier tier){
        this(id, PathDefine.RELIC_PATH, tier);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void setCounter(int counter) {
        super.setCounter(counter);
        if (counter <= -2) {
            this.usedUp();
        }
    }

    @Override
    public void flash() {
        if (!AbstractDungeon.player.relics.contains(this)) {
            AbstractRelic relic = AbstractDungeon.player.getRelic(relicId);
            if (relic != null) {
                relic.flash();
            }
        } else {
            super.flash();
        }
    }

    public boolean reduceCounterAndCheckDestroy() {
        boolean result = false;
        AbstractRelic relic = this;
        if (!AbstractDungeon.player.relics.contains(this)) {
            relic = AbstractDungeon.player.getRelic(relicId);
            if (relic.usedUp) relic = null;
        }
        if (relic != null) {
            relic.setCounter(relic.counter - 1);
            if (relic.counter <= 0) {
                relic.setCounter(-2);
                AbstractDungeon.topLevelEffectsQueue.add(new RelicAboveCreatureEffect(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.6f, relic));
                // AbstractDungeon.topLevelEffectsQueue.add(new BetterWarningSignEffect(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.7f, 4.0f));
                result = true;
            }
        }
        return result;
    }
    
    protected void destroy(){
        AbstractRelic relic = this;
        if (!AbstractDungeon.player.relics.contains(this)) {
            relic = AbstractDungeon.player.getRelic(relicId);
        }
        if (relic != null) {
            relic.setCounter(-2);
            AbstractDungeon.topLevelEffectsQueue.add(new RelicAboveCreatureEffect(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.6f, relic));
            // AbstractDungeon.topLevelEffectsQueue.add(new BetterWarningSignEffect(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.7f, 4.0f));
        }
/*        this.description = "该遗物已损毁。";
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();*/
    }
    
    public void recover() {
        this.grayscale = false;
        this.usedUp = false;
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    
    
    public void updateDescription(String newDescription){
        description = newDescription;
        tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public boolean canSpawn() {
        return !ModHelper.hasRelic(this.relicId);
    }
}
