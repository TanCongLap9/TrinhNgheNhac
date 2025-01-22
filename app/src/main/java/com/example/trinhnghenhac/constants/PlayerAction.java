package com.example.trinhnghenhac.constants;

import androidx.annotation.StringDef;

@StringDef({
        PlayerAction.ACTION_PLAY,
        PlayerAction.ACTION_PLAY_CREATE,
        PlayerAction.ACTION_PLAY_ENQUEUE,
        PlayerAction.ACTION_PLAY_PAUSE,
        PlayerAction.ACTION_PREVIOUS,
        PlayerAction.ACTION_NEXT,
        PlayerAction.ACTION_REPEAT,
        PlayerAction.ACTION_SHUFFLE,
        PlayerAction.ACTION_GOTO
})
public @interface PlayerAction {
    String ACTION_PLAY = "trinhnghenhac.action.PLAY";
    String ACTION_PLAY_CREATE = "trinhnghenhac.action.PLAY_CREATE";
    String ACTION_PLAY_ENQUEUE = "trinhnghenhac.action.PLAY_ENQUEUE";
    String ACTION_PLAY_PAUSE = "trinhnghenhac.action.PLAY_PAUSE";
    String ACTION_PREVIOUS = "trinhnghenhac.action.PREVIOUS";
    String ACTION_NEXT = "trinhnghenhac.action.NEXT";
    String ACTION_GOTO = "trinhnghenhac.action.GOTO";
    String ACTION_REPEAT = "trinhnghenhac.action.REPEAT";
    String ACTION_SHUFFLE = "trinhnghenhac.action.SHUFFLE";
}
