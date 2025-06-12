package hutaomod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.CardTrailEffect;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import com.megacrit.cardcrawl.vfx.RarePotionParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.FlameParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleSEffect;
import hutaomod.utils.PathDefine;

public class ButterflyEffect extends AbstractGameEffect {
    private static TextureRegion[] frames;
    private static final float PARTICLE_INTERVAL = 0.1f; // 每帧持续时间
    private TextureRegion currentFrame;
    private float x, y;
    private float startX, startY;
    private float speed;
    private float timeElapsed;
    private float animationTimer = 0f;
    private float animationSpeed;
    private float particleTimer = 0f;
    private Pool<TrailEffect> trailEffectPool = new Pool<TrailEffect>() {
        protected TrailEffect newObject() {
            return new TrailEffect();
        }
    };

    public ButterflyEffect(float x, float y, float duration, float scale, Color color, float rotation) {
        this.startingDuration = duration;
        this.duration = duration;
        this.scale = scale;
        this.color = color.cpy();
        this.color.a = 0.5f;
        this.rotation = rotation;
        this.renderBehind = MathUtils.randomBoolean();
        this.x = this.startX = x;
        this.y = this.startY = y;
        this.speed = 20;
        this.animationSpeed = 12;

        this.timeElapsed = 0f;

        if (frames == null) {
            frames = new TextureRegion[12];
            for (int i = 0; i < 12; i++) {
                Texture tex = new Texture(Gdx.files.internal(PathDefine.EFFECT_PATH + "butterfly/1_" + (i + 1) + ".png"));
                frames[i] = new TextureRegion(tex);
            }
        }
        currentFrame = frames[0];
    }

    @Override
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        super.update();

        timeElapsed += delta;
        animationTimer += delta;

        int frameIndex = (int)(animationTimer * animationSpeed) % frames.length;
        currentFrame = frames[frameIndex];
        
        rotation += (hashCode()%2==0 ? MathUtils.cos(duration) : -MathUtils.cos(duration)) / 2;

        // 移动方向根据 rotation 决定，rotation为0时朝上
        float angleRad = (rotation + 90f) * MathUtils.degreesToRadians;
        x = startX + MathUtils.cos(angleRad) * speed * timeElapsed;
        y = startY + MathUtils.sin(angleRad) * speed * timeElapsed;
        
        particleTimer += delta;
        if (particleTimer >= PARTICLE_INTERVAL) {
            TrailEffect effect = trailEffectPool.obtain();
            effect.init(x, y, 1, renderBehind, color.cpy(), trailEffectPool);
            AbstractDungeon.effectsQueue.add(effect);
            particleTimer -= PARTICLE_INTERVAL;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (isDone) return;
        sb.setColor(color);
        sb.draw(currentFrame,
                x - currentFrame.getRegionWidth() / 2f,
                y - currentFrame.getRegionHeight() / 2f,
                currentFrame.getRegionWidth() / 2f,
                currentFrame.getRegionHeight() / 2f,
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),
                scale, scale, rotation);
    }

    @Override
    public void dispose() {
        for (TextureRegion region : frames) {
            region.getTexture().dispose();
        }
    }
}
