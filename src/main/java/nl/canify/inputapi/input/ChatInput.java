package nl.canify.inputapi.input;

import nl.canify.inputapi.InputAPI;
import nl.canify.inputapi.question.Question;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatInput implements Listener {

    private final int expiry;
    private final Consumer<Player> onCancel;
    private final Consumer<Player> onExpire;
    private final Consumer<Player> onFinish;
    private final List<Question> questions;

    private final List<Player> activePlayers;

    public ChatInput(int expiry, Consumer<Player> onCancel, Consumer<Player> onExpire, Consumer<Player> onFinish, List<Question> questions) {
        this.expiry = expiry;
        this.onCancel = onCancel;
        this.onExpire = onExpire;
        this.onFinish = onFinish;
        this.questions = questions;
        activePlayers = new ArrayList<>();
        InputAPI.getPlugin().getServer().getPluginManager().registerEvents(this, InputAPI.getPlugin());
    }

    public void start(Player player) {
        activePlayers.add(player);
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!activePlayers.contains(player)) return;
        if (event.getMessage().equalsIgnoreCase("cancel")) {
            onCancel.accept(player);
            activePlayers.remove(player);
            return;
        }
        Question question = questions.get(0);
        question.onAnswer.accept(player, event.getMessage());
        questions.remove(0);
        if (questions.isEmpty()) {
            onFinish.accept(player);
            activePlayers.remove(player);
        }
    }
}