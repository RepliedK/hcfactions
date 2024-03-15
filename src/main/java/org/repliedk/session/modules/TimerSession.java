package org.repliedk.session.modules;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TimerSession {

    private String name;
    private float time;
    private boolean paused;

    public TimerSession(String key, float time, boolean paused) {
        this.name = key;
        this.time = time;
        this.paused = paused;
    }

    public void onUpdate() {
        if (isPaused()) return;
        time -= 0.1f;
        time = Math.round(time * 10) / 10;
    }

}