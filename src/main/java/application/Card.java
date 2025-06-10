package application;

import java.util.Objects;

public class Card {

    private final long id;
    private final CardType type;
    private final int hp;
    private final int dmg;

    // ------------------------ Object Initialization ------------------------ //
    /**
     * Creates a new instance of a {@link Card} using a provided {@link Card.Builder}.
     *
     * @param builder builder with customized Card attributes.
     */
    private Card(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.hp = builder.hp;
        this.dmg = builder.dmg;
    }

    /**
     * Builder class for the creation of a new {@link Card}.
     */
    public static class Builder {

        private final long id;
        private CardType type = CardType.NONE;
        private int hp;
        private int dmg;

        /**
         * Creates a new instance of a {@link Card.Builder} for the creation of a {@link Card}.
         * To finalize the creation of the new Card, use the {@link #build()} method.
         * <p>
         * Modifying the Card attributes can be done using methods like:
         * <ul>
         *     <li>{@link #setType(CardType)} - for setting the CardType;</li>
         *     <li>{@link #setHp(int)} - for setting a non-negative hp;</li>
         *     <li>{@link #setDmg(int)} - for setting a non-negative dmg;</li>
         * </ul>
         *
         * @param id base identifier of the new Card, cannot be negative.
         * @throws IllegalArgumentException if provided id is negative.
         */
        public Builder(long id) {
            if (id < 0) throw new IllegalArgumentException("Card identifier cannot be negative: " + id);
            this.id = id;
        }

        /**
         * Sets the CardType of the new {@link Card} being built with the {@link Card.Builder}.
         *
         * @param type {@link CardType} for the new Card, cannot be null.
         * @return Current Builder class for the new Card being built.
         *
         * @throws IllegalArgumentException if provided CardType is {@code null}.
         */
        public Builder setType(CardType type) {
            if (type == null) throw new IllegalArgumentException("New CardType cannot be null");
            this.type = type;
            return this;
        }

        /**
         * Sets the hp of the new {@link Card} being built with the {@link Card.Builder}.
         *
         * @param hp hit points for the new Card, cannot negative.
         * @return Current Builder class for the new Card being built.
         *
         * @throws IllegalArgumentException if provided hp is negative.
         */
        public Builder setHp(int hp) {
            if (hp < 0) throw new IllegalArgumentException("New hp cannot be set to a negative value: " + hp);
            this.hp = hp;
            return this;
        }

        /**
         * Sets the dmg of the new {@link Card} being built with the {@link Card.Builder}.
         *
         * @param dmg damage for the new Card, cannot negative.
         * @return Current Builder class for the new Card being built.
         *
         * @throws IllegalArgumentException if provided dmg is negative.
         */
        public Builder setDmg(int dmg) {
            if (dmg < 0) throw new IllegalArgumentException("New damage be set to a negative value: " + dmg);
            this.dmg = dmg;
            return this;
        }

        /**
         * Finalizes the building and creates a new instance of a {@link Card}.
         * @return the newly built Card.
         */
        public Card build() {
            return new Card(this);
        }
    }


    // ------------------------ Getters & Setters ------------------------ //
    public long getId() {
        return id;
    }

    public int getHp() {
        return hp;
    }

    public int getDmg() {
        return dmg;
    }


    // ------------------------ Default Object Methods ------------------------ //
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Card(id=" + id +
                ", type=" + type +
                ", hp=" + hp +
                ", dmg=" + dmg + ")";
    }
}
