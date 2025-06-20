package hutaomod.characters;

import basemod.abstracts.CustomMultiPageFtue;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import hutaomod.cards.base.HutaoA;
import hutaomod.cards.base.HutaoE;
import hutaomod.cards.base.HutaoQ;
import hutaomod.modcore.HuTaoMod;
import hutaomod.modcore.HuTaoModConfig;
import hutaomod.relics.PapilioCharontis;
import hutaomod.relics.RattanToyHuTao;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;
import hutaomod.utils.PathDefine;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HuTao extends CustomPlayer {
    // 火堆的人物立绘（行动前）
    private static final String MY_CHARACTER_SHOULDER_1 = PathDefine.CHARACTER_PATH + "shoulder1.png";
    // 火堆的人物立绘（行动后）
    private static final String MY_CHARACTER_SHOULDER_2 = PathDefine.CHARACTER_PATH + "shoulder2.png";
    // 人物死亡图像
    private static final String CORPSE_IMAGE = PathDefine.CHARACTER_PATH + "corpse.png";
    // 战斗界面左下角能量图标的每个图层
    private static final String[] ORB_TEXTURES = new String[]{
            PathDefine.UI_PATH + "orb/layer1.png",
            PathDefine.UI_PATH + "orb/layer2.png",
            PathDefine.UI_PATH + "orb/layer3.png",
            PathDefine.UI_PATH + "orb/layer4.png",
            PathDefine.UI_PATH + "orb/layer5.png",
            PathDefine.UI_PATH + "orb/layer6.png",
            PathDefine.UI_PATH + "orb/layer1d.png",
            PathDefine.UI_PATH + "orb/layer2d.png",
            PathDefine.UI_PATH + "orb/layer3d.png",
            PathDefine.UI_PATH + "orb/layer4d.png",
            PathDefine.UI_PATH + "orb/layer5d.png",
    };
    // 每个图层的旋转速度
    private static final float[] LAYER_SPEED = new float[]{
            0.0F,   // Layer5
            -32.0F, // Layer4
            20.0F,  // Layer3
            -20.0F, // Layer2
            0.0F,   // Layer1
            0.0F,   // Layer5d
            -8.0F,  // Layer4d
            5.0F,   // Layer3d
            -5.0F,  // Layer2d
            0.0F    // Layer1d
    };
    // 人物的本地化文本，如卡牌的本地化文本一样，如何书写见下
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(HuTaoMod.makeID(HuTao.class.getSimpleName()));

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(HuTaoMod.makeID("Tutorial"));

    static Texture[] ftues;

    static String[] tutTexts = uiStrings.TEXT;

    public HuTao(String name) {
        super(name, PlayerColorEnum.HUTAO, ORB_TEXTURES, "HuTaoResources/img/UI/orb/vfx.png", LAYER_SPEED, null, null);

        String charImg = null;
        try {
            // this.loadAnimation("HuTaoResources/img/spine/hutao_skin.atlas", "HuTaoResources/img/spine/hutao_skin.json", 3F);
            AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
            e.setTime(e.getEndTime() * MathUtils.random());
            e.setTimeScale(1F);
        } catch (Exception e) {
            System.out.println("HuTao skin load failed");
            System.out.println(e.getMessage());
            charImg = "HuTaoResources/img/char/character.png";
        }

        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);

        // 初始化你的人物，如果你的人物只有一张图，那么第一个参数填写你人物图片的路径。
        this.initializeClass(
                charImg, // 人物图片
                MY_CHARACTER_SHOULDER_2, MY_CHARACTER_SHOULDER_1,
                CORPSE_IMAGE, // 人物死亡图像
                this.getLoadout(),
                0.0F, -100.0F,
                300.0F, 300.0F, // 人物碰撞箱大小，越大的人物模型这个越大
                new EnergyManager(3) // 初始每回合的能量
        );

        // 如果你的人物没有动画，那么这些不需要写
        // this.loadAnimation("HuTaoResources/img/char/character.atlas", "HuTaoResources/img/char/character.json", 1.8F);
        // AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        // e.setTime(e.getEndTime() * MathUtils.random());
        // e.setTimeScale(1.2F);

        try {
            ftues = new Texture[]{
                    ImageMaster.loadImage(PathDefine.UI_PATH + "tutorial/1.png"),
                    ImageMaster.loadImage(PathDefine.UI_PATH + "tutorial/2.png"),
                    ImageMaster.loadImage(PathDefine.UI_PATH + "tutorial/3.png"),
                    ImageMaster.loadImage(PathDefine.UI_PATH + "tutorial/4.png"),
                    ImageMaster.loadImage(PathDefine.UI_PATH + "tutorial/5.png"),
                    ImageMaster.loadImage(PathDefine.UI_PATH + "tutorial/6.png")
            };
        } catch (Exception e) {
            System.out.println("HuTao ftue load failed");
            System.out.println(e.getMessage());
        }
    }

    // 初始卡组的ID，可直接写或引用变量
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(HuTaoMod.makeID(HutaoA.ID));
        retVal.add(HuTaoMod.makeID(HutaoA.ID));
        retVal.add(HuTaoMod.makeID(HutaoA.ID));
        retVal.add(HuTaoMod.makeID(HutaoA.ID));
        retVal.add(HuTaoMod.makeID(HutaoA.ID));
        retVal.add(HuTaoMod.makeID(HutaoE.ID));
        retVal.add(HuTaoMod.makeID(HutaoE.ID));
        retVal.add(HuTaoMod.makeID(HutaoE.ID));
        retVal.add(HuTaoMod.makeID(HutaoE.ID));
        retVal.add(HuTaoMod.makeID(HutaoQ.ID));

        return retVal;
    }

    // 初始遗物的ID，可以先写个原版遗物凑数
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(HuTaoMod.makeID(PapilioCharontis.ID));
        retVal.add(HuTaoMod.makeID(RattanToyHuTao.ID));
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                characterStrings.NAMES[0], // 人物名字
                characterStrings.TEXT[0], // 人物介绍
                75, // 当前血量
                75, // 最大血量
                0, // 初始充能球栏位
                75, // 初始携带金币
                5, // 每回合抽牌数量
                this, // 别动
                this.getStartingRelics(), // 初始遗物
                this.getStartingDeck(), // 初始卡组
                false // 别动
        );
    }

    // 人物名字（出现在游戏左上角）
    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    // 你的卡牌颜色（这个枚举在最下方创建）
    @Override
    public AbstractCard.CardColor getCardColor() {
        return PlayerColorEnum.HUTAO_RED;
    }

    // 翻牌事件出现的你的职业牌（一般设为打击）
    @Override
    public AbstractCard getStartCardForEvent() {
        return new HutaoA();
    }

    // 卡牌轨迹颜色
    @Override
    public Color getCardTrailColor() {
        return HuTaoMod.HUTAO_CORAL;
    }

    // 高进阶带来的生命值损失
    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    // 卡牌的能量字体，没必要修改
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // 人物选择界面点击你的人物按钮时触发的方法，这里为屏幕轻微震动
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    // 碎心图片
    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        // 有两个参数的，第二个参数表示出现图片时播放的音效
        panels.add(new CutscenePanel("HuTaoResources/img/char/Victory1.png", "ATTACK_MAGIC_FAST_1"));
        panels.add(new CutscenePanel("HuTaoResources/img/char/Victory2.png"));
        panels.add(new CutscenePanel("HuTaoResources/img/char/Victory3.png"));
        return panels;
    }

    // 自定义模式选择你的人物时播放的音效
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        int r = MathUtils.random(0, 2);
        return "join_" + r;
    }

    // 游戏中左上角显示在你的名字之后的人物名称
    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    // 创建人物实例，照抄
    @Override
    public AbstractPlayer newInstance() {
        return new HuTao(this.name);
    }

    // 第三章面对心脏说的话（例如战士是“你握紧了你的长刀……”之类的）
    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    // 打心脏的颜色，不是很明显
    @Override
    public Color getSlashAttackColor() {
        return HuTaoMod.HUTAO_CORAL;
    }

    // 吸血鬼事件文本，主要是他（索引为0）和她（索引为1）的区别（机器人另外）
    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    // 卡牌选择界面选择该牌的颜色
    @Override
    public Color getCardRenderColor() {
        return HuTaoMod.HUTAO_CORAL;
    }

    // 第三章面对心脏造成伤害时的特效
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    @Override
    public void playDeathAnimation() {
        super.playDeathAnimation();
        int r = MathUtils.random(0, 2);
        ModHelper.playSound("down_" + r);
    }

    boolean kehuSaid = false;

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        greetings();
        tutorialCheck();
    }

    void greetings() {
        if (AbstractDungeon.floorNum <= 2) {
            // 获取当前时间
            LocalDateTime dateTime = LocalDateTime.now();
            int hour = dateTime.getHour();
            if (hour >= 7 && hour < 11) {
                ModHelper.playSound("morning");
            } else if (hour >= 11 && hour < 14) {
                ModHelper.playSound("noon");
            } else if (hour >= 18 && hour < 22) {
                ModHelper.playSound("evening");
            } else if (hour >= 22 || hour < 6) {
                ModHelper.playSound("night");
            }
        } else if (!kehuSaid && AbstractDungeon.getMonsters().monsters.size() == 3 && MathUtils.random(100) < 10) {
            kehuSaid = true;
            ModHelper.playSound("kehu");
        }
    }
    
    void tutorialCheck() {
        if (AbstractDungeon.floorNum <= 2 && HuTaoModConfig.doShowTutorial) {
            ModHelper.addEffectAbstract(() -> {
                AbstractDungeon.ftue = new CustomMultiPageFtue(ftues, tutTexts);
            });
            HuTaoModConfig.doShowTutorial = false;
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        kehuSaid = false;
    }

    @Override
    public void damage(DamageInfo info) {
        boolean moreThanHalf = currentHealth > maxHealth / 2;
        super.damage(info);
        if (moreThanHalf && currentHealth <= maxHealth / 2 && MathUtils.random(100) < 20) {
            ModHelper.playSound("half");
        } else if (info.output >= 20 && MathUtils.random(40) < info.output) {
            ModHelper.playSound("hurt");
        }
    }

    boolean canTalk = true;

    @Override
    public void updateInput() {
        super.updateInput();
        if (hb.clicked && canTalk
                && AbstractDungeon.currMapNode != null
                && AbstractDungeon.currMapNode.room instanceof ShopRoom) {
            ModHelper.playSound("qqy");
            canTalk = false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    canTalk = true;
                }
            }, 20);
        }
    }

    public static final Texture YY_CIRCLE_WHITE = ImageMaster.loadImage(PathDefine.UI_PATH + "countCircle.png");
    public static float COUNT_CIRCLE_SIZE = 128.0F * Settings.scale;
    public static Color WHITE = Color.WHITE.cpy();
    public static Color BLACK = Color.BLACK.cpy();

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (AbstractDungeon.currMapNode != null
                && AbstractDungeon.currMapNode.room != null
                && AbstractDungeon.currMapNode.room.phase == AbstractRoom.RoomPhase.COMBAT
                && ModHelper.check(this)
                && !AbstractDungeon.currMapNode.room.isBattleOver) {
            int handYin = CacheManager.getInt(CacheManager.Key.YIN_CARDS);
            int handYang = CacheManager.getInt(CacheManager.Key.YANG_CARDS);
            sb.setColor(BLACK);
            sb.draw(ImageMaster.DECK_COUNT_CIRCLE, hb.cX - hb.width * 2 / 3 - COUNT_CIRCLE_SIZE / 2, hb.cY - COUNT_CIRCLE_SIZE / 2, COUNT_CIRCLE_SIZE, COUNT_CIRCLE_SIZE);
            sb.setColor(WHITE);
            sb.draw(YY_CIRCLE_WHITE, hb.cX + hb.width * 2 / 3 - COUNT_CIRCLE_SIZE / 2, hb.cY - COUNT_CIRCLE_SIZE / 2, COUNT_CIRCLE_SIZE, COUNT_CIRCLE_SIZE);
            FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, String.valueOf(handYin), hb.cX - hb.width * 2 / 3, hb.cY, WHITE);
            FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, String.valueOf(handYang), hb.cX + hb.width * 2 / 3, hb.cY, BLACK);
        }
    }

    // 以下为原版人物枚举、卡牌颜色枚举扩展的枚举，需要写，接下来要用

    // 注意此处是在 MyCharacter 类内部的静态嵌套类中定义的新枚举值
    // 不可将该定义放在外部的 MyCharacter 类中，具体原因见《高级技巧 / 01 - Patch / SpireEnum》
    public static class PlayerColorEnum {
        // 修改为你的颜色名称，确保不会与其他mod冲突
        @SpireEnum
        public static PlayerClass HUTAO;

        // ***将CardColor和LibraryType的变量名改为你的角色的颜色名称，确保不会与其他mod冲突***
        // ***并且名称需要一致！***
        @SpireEnum
        public static AbstractCard.CardColor HUTAO_RED;
    }

    public static class PlayerLibraryEnum {
        // ***将CardColor和LibraryType的变量名改为你的角色的颜色名称，确保不会与其他mod冲突***
        // ***并且名称需要一致！***

        // 这个变量未被使用（呈现灰色）是正常的
        @SpireEnum
        public static CardLibrary.LibraryType HUTAO_RED;
    }
}
