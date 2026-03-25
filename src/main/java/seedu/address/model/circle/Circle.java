package seedu.address.model.circle;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;

/**
 * Represents a Circle in the address book.
 * Guarantees: immutable.
 */
public class Circle {

    public static final String MESSAGE_CONSTRAINTS = "Circle names can only be"
        + "friend, prospect or lead (case-insensitive).";

    private static final Set<String> ALLOWED_CIRCLES = Set.of("friend", "prospect", "lead");

    public final String circleName;

    /**
     * Constructs a {@code Circle}.
     *
     * @param circleName A valid tag name.
     */
    public Circle(String circleName) {
        requireNonNull(circleName);
        checkArgument(isValidCircleName(circleName), MESSAGE_CONSTRAINTS);
        this.circleName = circleName.trim().toLowerCase();
    }

    /**
     * Returns true if a given string is a valid circle name.
     */
    public static boolean isValidCircleName(String test) {
        requireNonNull(test);
        String normalised = test.trim().toLowerCase();
        return ALLOWED_CIRCLES.contains(normalised);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Circle)) {
            return false;
        }

        Circle otherCircle = (Circle) other;
        return circleName.equals(otherCircle.circleName);
    }

    @Override
    public int hashCode() {
        return circleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return '[' + circleName + ']';
    }

}
