package application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    // ------------------------ BUILDER TESTS ------------------------ //
    @ParameterizedTest
    @MethodSource("builderGenerator")
    void cardBuilderTest(Card.Builder builder, String expectedToString) {
        assertThat(builder.build().toString()).isEqualTo(expectedToString);
    }

    private static Stream<Arguments> builderGenerator() {
        return Stream.of(
                Arguments.of(new Card.Builder(1),
                        cardString(1, CardType.NONE, 0, 0)),
                Arguments.of(new Card.Builder(1).setType(CardType.CLUBS),
                        cardString(1, CardType.CLUBS, 0, 0)),
                Arguments.of(new Card.Builder(1).setHp(10),
                        cardString(1, CardType.NONE, 10, 0)),
                Arguments.of(new Card.Builder(1).setDmg(5),
                        cardString(1, CardType.NONE, 0, 5)),
                Arguments.of(new Card.Builder(1).setType(CardType.CLUBS).setHp(10).setDmg(5),
                        cardString(1, CardType.CLUBS, 10, 5))
        );
    }

    @Test
    void negativeHpBuilderTest() {
        assertThatThrownBy(() -> new Card.Builder(1).setHp(-1)) //
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void negativeDmgBuilderTest() {
        assertThatThrownBy(() -> new Card.Builder(1).setDmg(-1)) //
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void negativeIdBuilderTest() {
        assertThatThrownBy(() -> new Card.Builder(-1)) //
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nullCardTypeBuilderTest() {
        assertThatThrownBy(() -> new Card.Builder(1).setType(null)) //
                .isInstanceOf(IllegalArgumentException.class);
    }


    // ------------------------ HELPER METHODS ------------------------ //
    private static String cardString(long id, CardType type, int hp, int dmg) {
        return "Card(id=" + id +
                ", type=" + type +
                ", hp=" + hp +
                ", dmg=" + dmg + ")";
    }

}