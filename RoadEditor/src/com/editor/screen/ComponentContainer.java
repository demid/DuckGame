package com.editor.screen;

/**
 * Date: 30.04.12
 * Time: 14:54
 *
 * @author: Alexey
 */
public interface ComponentContainer {
    public void addToSelected(CommandExecutor commandExecutor);

    public void removeFromSelected(CommandExecutor commandExecutor);

}
