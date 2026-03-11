package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class RemoveTagCommand extends Command {
    private final Tag tag;
    private final Index index;

    public static final String COMMAND_WORD = "tagrm";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a tag to a contact in the address book.\n" +
        "Parameters: INDEX " + PREFIX_TAG + "TAG\n" +
        "Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + "classmate";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed tag '%1$s' from %2$s";
    public static final String MESSAGE_REMOVE_TAG_FAILURE = "Invalid value: contact does not have this tag.";
    public static final String MESSAGE_INVALID_PERSON= "The person does not exist in the address book.";
    public static final String MESSAGE_INVALID_TAG = "Invalid value: tag must be 1–20 chars (a-z,0-9,-).";

    public RemoveTagCommand(Tag tag, Index index) {
        this.tag = tag;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size() ||  index.getOneBased() <= 0) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }

        Person personAtIndex = lastShownList.get(index.getZeroBased());

        if (!tag.tagName.matches("[a-z0-9\\-]{1,20}")) {
            throw new CommandException(MESSAGE_INVALID_TAG);
        }

        if (personAtIndex.getTags().stream().noneMatch(t -> t.tagName.equals(tag.tagName))) {
            throw new CommandException(MESSAGE_REMOVE_TAG_FAILURE);
        }

        Set<Tag> updatedTags = new HashSet<>(personAtIndex.getTags());
        updatedTags.removeIf(t -> t.tagName.equals(tag.tagName));

        Person editedPerson = new Person(
            personAtIndex.getName(),
            personAtIndex.getPhone(),
            personAtIndex.getEmail(),
            personAtIndex.getAddress(),
            updatedTags
        );

        model.setPerson(personAtIndex, editedPerson);

        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, tag, editedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        RemoveTagCommand otherCommand = (RemoveTagCommand) other;
        return index.equals(otherCommand.index) && tag.equals(otherCommand.tag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("tag", tag)
            .toString();
    }
}
