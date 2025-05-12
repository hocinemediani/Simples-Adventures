package com.projetlong.gh02;

public interface GameObject {

    /** Renders the game object to the screen.
     * It is called every time it is possible to.
     * @param renderHandler The game's render handler
     * @param scale The desired scaling ratio
     */
    public void render(RenderHandler renderHandler, int scale);


    /** Updates the game object at a fixed rate
     * every second (60 times per seconds).
     * @param game The game
     */
    public void update(GameFrame game);


    /** Applies a transformation to the game object.
     * It is possible to do translations, counter clock-wise
     * rotations and also homotheties.
     * @param dx The x-coordinate step
     * @param dy The y-coordinate step
     * @param dTheta The angle of the rotation
     */
    public void transform(int dx, int dy, int dTheta);


    /** Deletes the game object from the scene.
     * @param game The instance of game being played
     */
    public default void delete(GameFrame game) {
        game.getCurrentScene().deleteGameObject(this);
    }

}
