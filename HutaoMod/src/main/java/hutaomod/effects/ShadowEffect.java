package hutaomod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ShadowEffect extends AbstractGameEffect {
    private final Sprite shadowSprite;
    private final float speed, sX, sY;

    public ShadowEffect(Texture texture, float x, float y, float speed, float duration, Color color) {
        this.shadowSprite = new Sprite(texture);
        this.shadowSprite.setOriginCenter();
        this.shadowSprite.setPosition(x, y);
        this.shadowSprite.setRotation(rotation);
        this.shadowSprite.setScale(scale * Settings.scale);
        this.sX = x;
        this.sY = y;
        this.speed = speed;

        this.color = color.cpy(); // black shadow with adjustable alpha
        this.startingDuration = this.duration = duration;
        this.renderBehind = true;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < this.startingDuration / 2.0F) {
            this.color.a = Interpolation.fade.apply(this.duration / (this.startingDuration / 2.0F));
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            this.color.a = 0.0F;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        shadowSprite.setColor(color); // Ensure sprite uses the shadow color
        shadowSprite.setPosition(
                sX + Interpolation.pow3In.apply(1, 0, duration / startingDuration) * speed * Settings.scale,
                sY + Interpolation.linear.apply(1, 0, duration / startingDuration) * speed * Settings.scale
        );
        shadowSprite.draw(sb);
    }

    @Override
    public void dispose() {
        // No resource cleanup necessary
    }
}
