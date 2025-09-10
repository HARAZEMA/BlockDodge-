package com.blockdodge.mods;

import com.blockdodge.core.GamePanel;

public interface Mod {
    void onStart(GamePanel game);
    void onUpdate(GamePanel game);
}
