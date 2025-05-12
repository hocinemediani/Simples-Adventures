package com.projetlong.gh02;

public abstract class Item implements GameObject {

    // Attributs de la classe Item
    protected String name;
    protected Sprite sprite;
    protected Rectangle rectangle;
    protected int xPos;
    protected int yPos;
    protected SpriteSheet spriteSheet;

    /** Constructeur de la classe Item.
     * @param spriteSheet La feuille de sprites à utiliser pour l'item.
     * @param x La position x de l'item.
     * @param y La position y de l'item.
     */
    public Item(SpriteSheet spriteSheet, int x, int y) {
        this.spriteSheet = spriteSheet;
        this.sprite = spriteSheet.getSprite(0, 0); // Sprite par défaut
        this.xPos = x;
        this.yPos = y;
        this.rectangle = new Rectangle(x, y, SpriteSheet.tileSize, SpriteSheet.tileSize);
    }

    /** Méthode appelée pour dessiner l'item à l'écran.
     * @param renderer Le gestionnaire de rendu.
     * @param scale Le facteur d'échelle pour le rendu.
     */
    @Override
    public void render(RenderHandler renderer, int scale) {
        renderer.loadSprite(sprite, xPos, yPos, scale);
    }

    /** Méthode appelée à chaque mise à jour du jeu. */
    @Override
    public void update(GameFrame game) {
        // Détecter la collision avec le joueur
        for (GameObject obj : game.getCurrentScene().getGameObjects()) {
            if (obj instanceof Player) {
                Player player = (Player) obj;
                if (this.rectangle.intersects(player.playerRectangle)) {
                    onPickup(game, player);
                    this.delete(game);
                    break;
                }
            }
        }
    }

    /** Méthode appelée lorsque l'item est ramassé. */
    //public abstract void onPickup(GameFrame game, Player player);

    /** Méthode appelée lorsque l'item est utilisé. */
    //public abstract void use(Player player);

    /** Méthode appelée lorsque l'item est déplacé. */
    @Override
    public void transform(int dx, int dy, int dTheta) {
        xPos += dx;
        yPos += dy;
        rectangle.moveX(dx);
        rectangle.moveY(dy);
    }

    // Getters et Setters
    public int getX() {
        return xPos;
    }
    public int getY() { 
        return yPos; 
    }
}