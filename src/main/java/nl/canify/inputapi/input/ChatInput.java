package nl.canify.inputapi.input;

import nl.canify.inputapi.InputAPI;
import nl.canify.inputapi.question.Question;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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

    private boolean enabled;
    private final List<Player> activePlayers;
    private final Map<Player, BukkitTask> expiryMap;

    public ChatInput(int expiry, Consumer<Player> onCancel, Consumer<Player> onExpire, Consumer<Player> onFinish, List<Question> questions) {
        this.expiry = expiry;
        this.onCancel = onCancel;
        this.onExpire = onExpire;
        this.onFinish = onFinish;
        this.questions = questions;
        enabled = false;
        expiryMap = new HashMap<>();
        activePlayers = new ArrayList<>();
    }

    public void start(Player player) {
        activePlayers.add(player);
        player.sendMessage(questions.get(0).getQuestion());

        if (!enabled) {
            InputAPI.getPlugin().getServer().getPluginManager().registerEvents(this, InputAPI.getPlugin());
            enabled = true;
        }

        expiryMap.put(player, Bukkit.getScheduler().runTaskLater(InputAPI.getPlugin(), () -> {
            onExpire.accept(player);
            activePlayers.remove(player);
        }, expiry * 20L));

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!activePlayers.contains(player)) return;
        activePlayers.remove(player);
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!activePlayers.contains(player)) return;
        event.setCancelled(true);
        if (event.getMessage().equalsIgnoreCase("cancel") || event.getMessage().equalsIgnoreCase("stop")) {
            onCancel.accept(player);
            activePlayers.remove(player);
            expiryMap.get(player).cancel();
            expiryMap.remove(player);
            return;
        }
        Question question = questions.get(0);
        question.onAnswer.accept(player, event.getMessage());
        if (!question.isUnlimited()) { questions.remove(0); }

        if (questions.isEmpty() || event.getMessage().equalsIgnoreCase("finish")) {
            onFinish.accept(player);
            activePlayers.remove(player);
            expiryMap.get(player).cancel();
            expiryMap.remove(player);
        }
    }
}
