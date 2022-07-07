# InputAPI
A stand alone alternative for Spigot's Conversation API made for my other plugins.

## Disclaimer
Feel free to use the code for your own API or use the API itself. Keep in mind this is NOT meant for public use (right now, might change later) since I will mostly use this for my own plugins and change the the way it works whenever needed.

## Example
```java
ChatInput input = new InputBuilder()
      .setExpiry(20) // The time the player has to answer a question
      .addQuestion(new Question("What is your name?").onAnswer((p, s) -> {
          p.sendMessage("Hello " + s);
      })
      ).addQuestion(new Question("Where do you live?").onAnswer((p, s) -> {
          p.sendMessage("You live in " + s);
      })
      ).onExpire((p) -> {
          p.sendMessage("You ran out of time to answer.");
      }).onFinish((p) -> {
          p.sendMessage("You finished, nice!");
      }).onCancel((p) -> {
          p.sendMessage("You cancelled.");
      }).build();

input.start(player);
```
