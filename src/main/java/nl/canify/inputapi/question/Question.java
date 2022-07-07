package nl.canify.inputapi.question;

import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class Question {

    private final String question;
    private boolean unlimited;
    public BiConsumer<Player, String> onAnswer;

    public Question(String question) {
        this.question = question;
        onAnswer = (p, s) -> {};
    }

    public Question onAnswer(BiConsumer<Player, String> onAnswer) {
        this.onAnswer = onAnswer;
        return this;
    }

    public Question allowUnlimitedAnswers(boolean allow) {
        this.unlimited = allow;
        return this;
    }

    public boolean isUnlimited() {
        return unlimited;
    }
    public String getQuestion() {
        return question;
    }

}
