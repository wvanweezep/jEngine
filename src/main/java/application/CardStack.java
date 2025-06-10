package application;

import java.util.*;

public class CardStack {

    private final LinkedList<Card> cards;
    // TODO: Implement a Modifier class to replace the String, to have actual functionality
    private final Set<String> modifiers;

    // ------------------------ Object Initialization ------------------------ //
    /**
     * Creates a new instance of a {@link CardStack} without any {@link Card}s.
     */
    public CardStack() {
        this.cards = new LinkedList<>();
        this.modifiers = new HashSet<>();
    }

    /**
     * Creates a new instance of a {@link CardStack} using a provided {@link CardStack.Builder}.
     *
     * @param builder builder with customized CardStack attributes.
     */
    private CardStack(Builder builder) {
        this.cards = builder.cards;
        this.modifiers = builder.modifiers;
    }

    /**
     * Builder class for the creation of a new {@link CardStack}.
     */
    public static class Builder {

        private final LinkedList<Card> cards;
        private final Set<String> modifiers;

        /**
         * Creates a new instance of a {@link CardStack.Builder} for the creation of
         * a {@link CardStack}. To finalize the creation of a new CardStack, use the
         * {@link #build()} method.
         * <p>
         * Modifying the CardStack attributes can be done using methods like:
         * <ul>
         *     <li>{@link #addCard(Card)} - for adding a new Card to the CardStack;</li>
         *     <li>{@link #addModifier(String)} - for adding a new Modifier to the CardStack;</li>
         * </ul>
         */
        public Builder() {
            this.cards = new LinkedList<>();
            this.modifiers = new HashSet<>();
        }

        /**
         * Adds a new {@link Card} to the top of the {@link CardStack} being built with the {@link CardStack.Builder}.
         *
         * @param card {@link Card} pushed on the CardStack, cannot be null.
         * @return Current Builder class for the new CardStack being built.
         *
         * @throws IllegalArgumentException if provided Card is {@code null}.
         */
        public Builder addCard(Card card) {
            if (card == null) throw new IllegalArgumentException("New Card cannot be null");
            this.cards.add(card);
            return this;
        }

        /**
         * Adds a new {@code Modifier} to the {@link CardStack} being built with the {@link CardStack.Builder}.
         *
         * @param modifier {@code Modifier} added to the CardStack, cannot be null.
         * @return Current Builder class for the new CardStack being built.
         *
         * @throws IllegalArgumentException if provided modifier is {@code null}, or already present.
         */
        public Builder addModifier(String modifier) {
            if (modifier == null) throw new IllegalArgumentException("New Modifier cannot be null");
            if (!this.modifiers.add(modifier))
                throw new IllegalArgumentException("Modifier already present on CardStack: " + modifier);
            return this;
        }

        /**
         * Finalizes the building and creates a new instance of a {@link CardStack}.
         * @return the newly built CardStack.
         */
        public CardStack build() {
            return new CardStack(this);
        }
    }


    // ------------------------ Getters & Setters ------------------------ //
    /**
     * Getter for the {@link Card}s present on the {@link CardStack}. Note that the LinkedList
     * is unmodifiable. However, editing the Cards themselves will affect the CardStack.
     *
     * @return Unmodifiable {@code List<Card>} with all Cards on the CardStack.
     */
    public List<Card> getCards() {
        return Collections.unmodifiableList(this.cards);
    }

    /**
     * Getter for the {@code Modifier}s present on the {@link CardStack}. Note that the Set is
     * unmodifiable. However, editing the Modifiers themselves will affect the CardStack.
     *
     * @return Unmodifiable {@code Set<String>} with all Modifiers on the CardStack.
     */
    public Set<String> getModifiers() {
        return Collections.unmodifiableSet(this.modifiers);
    }


    // ------------------------ Other Methods ------------------------ //
    /**
     * Adds a new {@code Modifier} to the {@link CardStack} modifiers.
     *
     * @param modifier {@code Modifier} added to the CardStack, cannot be null.
     *
     * @throws IllegalArgumentException if provided modifier is {@code null}, or already present.
     */
    public void addModifier(String modifier) {
        if (modifier == null) throw new IllegalArgumentException("New Modifier cannot be null");
        if (!this.modifiers.add(modifier))
            throw new IllegalArgumentException("Modifier already present on CardStack: " + modifier);
    }

    /**
     * Removes a {@code Modifier} from the {@link CardStack} modifiers.
     *
     * @param modifier {@code Modifier} removed to the CardStack, cannot be null.
     * @return {@code true} if the modifiers changed as a result of this operation.
     *
     * @throws IllegalArgumentException if provided modifier is {@code null}.
     */
    public boolean removeModifier(String modifier) {
        if (modifier == null) throw new IllegalArgumentException("Modifier cannot be null");
        return this.modifiers.remove(modifier);
    }

    /**
     * Checks whether a {@code Modifier} is present on the {@link CardStack}.
     *
     * @param modifier {@code Modifier} to check for on the CardStack, cannot be null.
     * @return {@code true} if the modifier is present on the CardStack
     *
     * @throws IllegalArgumentException if provided modifier is {@code null}.
     */
    public boolean hasModifier(String modifier) {
        if (modifier == null) throw new IllegalArgumentException("Modifier cannot be null");
        return this.modifiers.contains(modifier);
    }

    /**
     * Removes all {@code Modifier} from the {@link CardStack}.
     */
    public void clearModifiers() {
        this.modifiers.clear();
    }
}
