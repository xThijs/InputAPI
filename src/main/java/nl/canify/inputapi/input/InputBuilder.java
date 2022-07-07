package nl.canify.inputapi.input;

import nl.canify.inputapi.question.Question;
import org.bukkit.entity.Player;
import org.bukkit.util.Consumer;

import java.util.ArrayList;
import java.util.List;

public class InputBuilder {

    private int expiry;

    private Consumer<Player> onCancel;
    private Consumer<Player> onExpire;
    private Consumer<Player> onFinish;

    private List<Question> questions;

    public InputBuilder() {

        this.expiry = -1;
        this.onCancel = (p) -> {};
        this.onExpire = (p) -> {};
        this.onFinish = (p) -> {};

        questions = new ArrayList<>();
    }

    public InputBuilder setExpiry(int seconds) {
        this.expiry = seconds;
        return this;
    }

    public InputBuilder addQuestion(Question question) {
        questions.add(question);
        return this;
    }

    public InputBuilder onCancel(Consumer<Player> onCancel) {
        this.onCancel = onCancel;
        return this;
    }

    public InputBuilder onExpire(Consumer<Player> onExpire) {
        this.onExpire = onExpire;
        return this;
    }

    public InputBuilder onFinish(Consumer<Player> onFinish) {
        this.onFinish = onFinish;
        return this;
    }

    public ChatInput build() {
        return new ChatInput(expiry, onCancel, onExpire, onFinish, questions);
    }



}
